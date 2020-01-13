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
        self.variants = []

    def add_variant(self,model):
        self.variants.append(model)

    def print_me(self):
        print ("\t%s" % self)
        for v in self.variants:
            v.print_me()

    @staticmethod
    def create(row):
        return Model(row[1],row[2])

class Brand(Base):
    def __init__(self,id,name):
        Base.__init__(self,id,name)
        self.models = []

    def add_model(self,model):
        self.models.append(model)

    def print_me(self):
        print (self)
        for m in self.models:
            m.print_me()

    @staticmethod
    def create(row):
        return Brand(row[1],row[2])

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
            cur_brand = Brand.create(c)
            result.append(cur_brand) 
        elif c[0] == "Model":
            cur_model = Model.create(c)
            cur_brand.add_model(cur_model)
        elif c[0] == "Variant":
            cur_variant = Variant.create(c)
            cur_model.add_variant(cur_variant)
    return result

def redis_conn():
    return redis.Redis(host="localhost", port=6379, db=0)

def insert_brand_names(brands):
    conn = redis_conn()
    for b in brands:
        b.print_me()
        conn.hsetnx("brand:name", b.id,b.name)

def insert_brand_year(year,brands):
    conn = redis_conn()
    brand_year = set()
    for b in brands:
        brand_year.add(b.id)
    conn.sadd("brand:%d" % year, *brand_year) 

def insert_model_names(brands):
    conn = redis_conn()
    for b in brands:
        b.print_me()
        bid = b.id
        for m in b.models:
            mid = "%s:%s" % (bid,m.id)
            print (mid)
            conn.hsetnx("model:name", mid,m.name)


def print_brands(brands):
    for b in brands:
        b.print_me()

def check_unique_variant_ids():
    brands_list = []
    for year in range(1999,2020):
        print (year)
        content = corexml(year)
        brands = create_brands(year,content)
        brands_list.append(brands)
    print (len(brands_list))
    key_list = []
    key_set = set()
    for brand_item in brands_list:
        for b in brand_item: 
            for m in b.models:
                for v in m.variants:
                    key_list.append(v.id)
                    key_set.add(v.id)
    print ("List: %d, set: %d" % (len(key_list),len(key_set)))

def insert():
    year = 1999
    content = corexml(year)
    brands = create_brands(year,content)
    insert_model_names(brands)
    #print_brands(brands)
    #insert_brand_names(brands)
    #insert_brand_year(conn,year,brands)
    print (len(brands))

if __name__ == "__main__":
    check_unique_variant_ids()