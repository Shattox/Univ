from os import write
import sys
import subprocess
import re


def generate_cutting(bar_length, lengths, res=[]):
    
    tmp = [0 for i in range(len(lengths))]
    i = 0
    counter = 0
    while (i < len(lengths)):
        if counter + lengths[i] > bar_length:
            i+=1
            continue
        else:
            counter += lengths[i]
            tmp[i] += 1
            if tmp in res:
                counter-= lengths[i]
                tmp[i] -= 1
                i+=1
    
    if tmp  == [0 for i in range(len(lengths))]:
        return res
    
    res.append(tmp)
    return generate_cutting(bar_length, lengths, res=res)



def remove_sub_list_less_counter(lst):
    tmp_cpy = []
    # create real copy
    for i in range(len(lst)):
        tmp_cpy.append([])
        for j in range(len(lst[i])):
            tmp_cpy[i].append(lst[i][j])
    
    # remove sublist that exist with just less counter
    for i in range(len(tmp_cpy)):
        for j in range(len(tmp_cpy[i])):
            prev_tmp = [z for z in tmp_cpy[i]]
            while tmp_cpy[i][j] >= 0:
                tmp_cpy[i][j] = tmp_cpy[i][j] - 1
                if tmp_cpy[i] in lst:
                    lst.remove(tmp_cpy[i])
            tmp_cpy[i] = prev_tmp
    return lst



def write_lp_solve(file, lst_nb_elements_wanted, lst_counters):
    file.write("min: ")

    for i in range(len(lst_counters)):
        file.write("x"+str(i+1))
        if (i < len(lst_counters)-1):
            file.write("+")
    file.write(";\n")

    # read lst_nb_elements_wanted verticaly
    for i in range(len(lst_counters[0])):
        for j in range(len(lst_counters)):
            file.write(str(lst_counters[j][i]) + "x" + str(j+1))
            if (j == len(lst_counters) - 1):
                file.write(" >= " + str(lst_nb_elements_wanted[i]) + ";\n")
            else:
                file.write(" + ")
    



if __name__ == '__main__':

    if (len(sys.argv) % 2 != 0):
        sys.stderr.write("You must give 2 elements by 2\n")
        sys.exit()

    bar_length = int(sys.argv[1])

    lengths = []
    lst_nb_elements_wanted = []

    for i in range(2, len(sys.argv)):
        if i % 2 == 0:
            lst_nb_elements_wanted.append(int(sys.argv[i]))
        else:
            lengths.append(int(sys.argv[i]))
            
    result = generate_cutting(bar_length, lengths)
    result = remove_sub_list_less_counter(result)

    with open('test.lp', "w") as file:
        write_lp_solve(file, lst_nb_elements_wanted, result)

    proc = subprocess.check_output(['lp_solve', "test.lp"])

    regex = " (\d+\.{0,1}\d*)"
    data = re.findall(regex, proc.decode("utf8"))

    for sol in result:
        print(sol)
    print("une solution optimale utilise " + data[0].split(".")[0] + " barres de longueur " + str(bar_length) + " cm.")
    