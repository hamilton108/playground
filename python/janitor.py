import redis

# COREXML_PATH = "/home/rcs/opt/java/playground/corexml"
COREXML_PATH = "../corexml"

class Base:
    def __init__(self,id,name):
        self.id = id
        self.name = name
    def __str__(self):
        return "[%s %s] - %s" % (type(self).__name__,self.id,self.name)

class Variant(Base):
    def __init__(self,id,name):
        Base.__init__(self,id,name)

    def print_me(self):
        print ("\t\t%s" % self)

    @staticmethod
    def create(row):
        return Variant(row[1],row[2])

class Model(Base):
    def __init__(self,id,name):
        Base.__init__(self,id,name)
        self.variants = set()

    def add_variant(self,model):
        self.variants.add(model)

    def print_me(self):
        print ("\t%s" % self)
        for v in self.variants:
            v.print_me()

    @staticmethod
    def create(row):
        return Model(row[1],row[2])

class Brand(Base):
    def __init__(self,year,id,name):
        Base.__init__(self,id,name)
        self.models = set()
        self.year = year

    def add_model(self,model):
        self.models.add(model)

    def print_me(self):
        print (self)
        for m in self.models:
            m.print_me()

    @staticmethod
    def create(year,row):
        return Brand(year,row[1],row[2])

def redis_conn(db):
    return redis.Redis(host="localhost", port=6379, db=db)

def print_brands(brands):
    for b in brands:
        b.print_me()

def file_name_for(year):
    return "carsearch-%d.txt" % year

def corexml_content(year):
    fname = "%s/%s" % (COREXML_PATH, file_name_for(year))
    print (fname)
    f = open(fname)
    lx = f.readlines()
    f.close()
    return lx

def normalize(line):
    s1 = line.strip().split(":")
    s2 = s1[1].strip().split("=>") 
    return [s1[0],s2[0].strip(),s2[1].strip()]

def corexml(year):
    result = []
    content = corexml_content(year)
    for l in content:
        result.append(normalize(l))
    return result 

def create_brands(year,items):
    result = []
    cur_brand = None
    cur_model = None
    for c in items:
        if c[0] == "Brand":
            cur_brand = Brand.create(year,c)
            result.append(cur_brand) 
        elif c[0] == "Model":
            cur_model = Model.create(c)
            cur_brand.add_model(cur_model)
        elif c[0] == "Variant":
            cur_variant = Variant.create(c)
            cur_model.add_variant(cur_variant)
    return result

def insert_brand_year(year,brands,conn):
    brand_year = set()
    for b in brands:
        brand_year.add(b.id)
    conn.sadd("brand:%d" % year, *brand_year) 

def insert_brand_names(brands,conn):
    for b in brands:
        conn.hsetnx("brand:name", b.id,b.name)

def insert_model_names(brands,conn):
    for b in brands:
        bid = b.id
        for m in b.models:
            mid = "%s:%s" % (bid,m.id)
            conn.hsetnx("model:name", mid,m.name)
        
def insert_variant_names(brands,conn):
    for b in brands:
        for m in b.models:
            for v in m.variants:
                conn.hsetnx("variant:name", v.id,v.name)

def insert_brand_models(brands,conn):
    for b in brands:
        items = set()
        for m in b.models:
            items.add("%s:%s" % (b.id,m.id))
        if len(items) > 0:
            conn.sadd("brand:models:%s" % b.id, *items) 

def insert_models_variants(brands,conn):
    for b in brands:
        for m in b.models:
            items = set()
            for v in m.variants:
                items.add(v.id)
                if len(items) > 0:
                    conn.sadd("%s:%s" % (b.id,m.id), *items) 

def all_brands():
    all_years = range(1999,2020)
    result = {}
    for year in all_years:
        items = corexml(year)
        brands = create_brands(year,items)
        result[year] = brands
    return result

def sunion_brands(conn):
    all_years = range(1999,2020)
    all_brands = set()
    for x in all_years:
        all_brands.add("brand:%d" % x)
    conn.sunionstore("brand:all", *all_brands)

def enchillada(db):
    ab = all_brands()
    conn = redis_conn(db)
    for k,v in ab.items(): 
        insert_brand_year(k,v,conn)
    sunion_brands(conn)
    for brands in ab.values():
        insert_brand_names(brands,conn)
        insert_model_names(brands,conn)
        insert_variant_names(brands,conn)
        insert_brand_models(brands,conn)
        insert_models_variants(brands,conn)

if __name__ == "__main__":
    enchillada(0)