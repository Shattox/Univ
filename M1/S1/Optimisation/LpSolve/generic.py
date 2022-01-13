import sys
import subprocess
import re
import time

def parse_file(file):
    dico = dict()
    
    lines = file.readlines()
    size_max = 0
    dico["benefices"] = list()
    dico["names"] = list()
    
    for i, line in enumerate(lines):
        elements = line.split(" ")
        if i == 0:
            size_max = int(elements[0])
        elif i == 1:
            elements[-1] = elements[-1].replace('\n', '')
            dico["max_ressources"] = elements
        else:
            element = elements[-1].replace('\n', '')
            dico["benefices"].append(element + "" + elements[0])
            dico["names"].append(elements[0])
            dico[elements[0]] = elements[1:-1]            
  
    benef = " + ".join(dico["benefices"])
  
    return dico, size_max

def write_lp_solve(file, dico, size_max):
    file.write("max: ")

    benefice = " + ".join(dico["benefices"])
                          
    file.write(benefice + ";\n")
    
    keys = dico["names"]
    
    for i in range(size_max):
        line = ""
        for c, name in enumerate(keys):
            if c == (len(keys) - 1):
                line += dico[name][i] + "" + name
            else:
                line += dico[name][i] + "" + name + " + "
        file.write(line + " <= " + dico["max_ressources"][i] + ";\n")

    if len(sys.argv) == 4 and sys.argv[3] == "-int":
        variables = ", ".join(dico["names"])
        file.write("\nint " + variables + ";") 
        
def parse_lp_solution(data, dico): 
    for i, key in enumerate(dico["names"]):
        print(key + " = " + data[i + 1])
    
    print("opt = " + data[0])
       
if __name__ == '__main__':
    file_in = open(sys.argv[1], "r")
    file_out = open(sys.argv[2], "w")

    dico, size_max = parse_file(file_in)
    write_lp_solve(file_out, dico, size_max)
   
    file_in.close()
    file_out.close()

    start_time = time.time()
    proc = subprocess.check_output(['lp_solve', sys.argv[2]])
    interval = time.time() - start_time
    print("time : ", interval, "secondes")
    
    regex = " (\d+\.{0,1}\d*)"
    data = re.findall(regex, proc.decode("utf8"))

    parse_lp_solution(data, dico)
    