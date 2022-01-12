#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import itertools
import math
import sys
import subprocess
import time

"""Sudoku"""

def var(i,j,k):
    """Return the literal Xijk.
    """
    return (1,i,j,k)

def neg(l):
    """Return the negation of the literal l.
    """
    (s,i,j,k) = l
    return (-s,i,j,k)

def initial_configuration():
    """Return the initial configuration of the example in td6.pdf
    
    >>> cnf = initial_configuration()
    >>> (1, 1, 4, 4) in cnf
    True
    >>> (1, 2, 1, 2) in cnf
    True
    >>> (1, 2, 3, 1) in cnf
    False
    """
    return [var(1, 4, 4), var(2, 1, 2), var(3, 2, 1), var(4, 3, 1)]

def at_least_one(L):
    """Return a cnf that represents the constraint: at least one of the
    literals in the list L is true.
    
    >>> lst = [var(1, 1, 1), var(2, 2, 2), var(3, 3, 3)]
    >>> cnf = at_least_one(lst)
    >>> len(cnf)
    1
    >>> clause = cnf[0]
    >>> len(clause)
    3
    >>> clause.sort()
    >>> clause == [var(1, 1, 1), var(2, 2, 2), var(3, 3, 3)]
    True
    """
    return [L]

def at_most_one(L):
    """Return a cnf that represents the constraint: at most one of the
    literals in the list L is true
    
    >>> lst = [var(1, 1, 1), var(2, 2, 2), var(3, 3, 3)]
    >>> cnf = at_most_one(lst)
    >>> len(cnf)
    3
    >>> cnf[0].sort()
    >>> cnf[1].sort()
    >>> cnf[2].sort()
    >>> cnf.sort()
    >>> cnf == [[neg(var(1,1,1)), neg(var(2,2,2))], \
    [neg(var(1,1,1)), neg(var(3,3,3))], \
    [neg(var(2,2,2)), neg(var(3,3,3))]]
    True
    """
    return [[neg(x), neg(y)] for (x, y) in itertools.combinations(L, 2)]

def assignment_rules(N):
    """Return a list of clauses describing the rules for the assignment (i,j) -> k.
    """
    cnf = []
    for i in range(1,N+1):
        for j in range(1,N+1):
            tmp = [var(i, j, k) for k in range(1, N+1)]
            cnf.append(at_least_one(tmp)[0])
            cnf.extend([lst for lst in at_most_one(tmp)])
    return cnf

def row_rules(N):
    """Return a list of clauses describing the rules for the rows.
    """
    k = [i for i in range(1, N + 1)]
    cnf = list()
    for i in range(1, N + 1):
        for l in range(1, N + 1):
            tmp = [var(i, j, k[l - 1]) for j in range(1, N + 1)]
            cnf.append(at_least_one(tmp)[0])
            cnf.extend(at_most_one(tmp))
    return cnf

def column_rules(N):
    """Return a list of clauses describing the rules for the columns.
    """
    k = [i for i in range(1, N + 1)]
    cnf = list()
    for i in range(1, N + 1):
        for l in range(1, N + 1):
            tmp = [var(j, i, k[l - 1]) for j in range(1, N + 1)]
            cnf.append(at_least_one(tmp)[0])
            cnf.extend(at_most_one(tmp))
    return cnf

def subgrid_rules(N):
    """Return a list of clauses describing the rules for the subgrids.
    """
    res, cnf = [], []
    sq = int(math.sqrt(N))

    for a in range(sq) :
        for b in range(sq) :
            lst = []
            for i in range(a * sq, (a + 1) * sq) :
                for j in range(b * sq, (b + 1) * sq) :
                    lst.append((i + 1, j + 1))
            res.append(lst)

    for lst in res :
        for k in range(1 , N + 1) :
            cnf.extend(at_least_one([var(i, j, k) for i, j in lst]))
            cnf.extend(at_most_one([var(i, j, k) for i, j in lst]))
        
    return cnf

def generate_rules(N):
    """Return a list of clauses describing the rules of the game.
    """
    start_time = time.time()
    cnf = []
    cnf.extend(assignment_rules(N))
    cnf.extend(row_rules(N))
    cnf.extend(column_rules(N))
    cnf.extend(subgrid_rules(N))
    interval = time.time() - start_time
    print("generation clauses : ", interval, "secondes")
    return cnf

def literal_to_integer(l, N):
    """Return the external representation of the literal l.

    >>> literal_to_integer(var(1,2,3), 4)
    7
    >>> literal_to_integer(neg(var(3,2,1)), 4)
    -37
    """
    return l[0] * (N**2 * (l[1] - 1) + N * (l[2] - 1) + l[3]) 

def generate_cnf_file(liste, N, file_name):
    file = open(file_name, 'w')
    file.write('p cnf ' + str(N ** 3) + ' ' + str(len(liste)) + '\n')
    
    for i in liste:
        for j in i:
            file.write(str(literal_to_integer(j, N)) + ' ')
        file.write('0\n')

    file.close()
    
def parse_sudoku_txt_to_cnf(file_name):
    file = open(file_name, 'r')
    datas = [data.strip() for data in file.readlines()]
    length = int(datas[0])
    datas = [data.split(' ') for data in datas[1:]]
    liste = []
    
    for i, lst in enumerate(datas):
        for j, elem in enumerate(lst):
            if int(elem) != 0:
                liste.append([var(i + 1, j + 1, int(elem))])

    liste += generate_rules(length)
    generate_cnf_file(liste, length, 'sudoku.cnf')
    file.close()
    return length
    
    
def execute_minisat(file_name, length):
    subprocess.call(['minisat', file_name, file_name[:-3] + 'out'])
    result = open('sudoku.out', 'r')
    datas = result.readlines()
    
    if datas[0] == 'SAT\n':
        numbers = datas[1:]
        asArr = numbers[0].split(' ')

        for i in range(len(asArr)):
            asArr[i] = int(asArr[i])

        for y in range(length):
            line = ''
            for x in range(length):
                for z in range(length):
                    if(asArr[y * (length * length) + length * x + z] >= 0):
                        line = line + str(z + 1) + ' '
                        break
            print(line)
        
def main():
    import doctest
    doctest.testmod()

if __name__ == "__main__":
    main()
    
    if len(sys.argv) == 2:
        length = parse_sudoku_txt_to_cnf(sys.argv[1])
        execute_minisat('sudoku.cnf', length)