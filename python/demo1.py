# import redis

COREXML_PATH = "/home/rcs/opt/java/playground/corexml"

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

if __name__ == "__main__":
    content = corexml(1999)
    print (content)

