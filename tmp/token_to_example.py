#!/usr/bin/env python3
import re
import os
import json
import subprocess
import multiprocessing
from pprint import pprint

def getter(classname, idname):
    dic = {}
    with open('CodeToken/{}/{}.token'.format(classname, idname), 'r') as f:
        dep = 0
        for data in f:
            arr = data.split(',')
            try:
                pos = int(arr[0][2:])
                ID = re.search(r"'(.+)'", arr[1]).group(1)
                token = re.search(r'<(.+)>', arr[2]).group(1)
                line = re.search(r'(.+):', arr[3]).group(1)

                if ID == '}':
                    dep -= 1

                dic[pos] = [ID, token, line, dep]

                if ID == '{':
                    dep += 1

            except AttributeError as e:
                dic[pos] = [',', "','", line, dep]

    # pprint(dic)
    return dic

def extract(classname, dic):
    catalog = []
    for keyS, valueS in dic.items():
        if keyS < 1 or dic[keyS - 1][0] != classname or dic[keyS][1] != 'Identifier':
            continue

        # Now, the instance should be determined whether it is function arguments or not
        isArgs = False
        for keyT, valueT in dic.items():
            if keyS >= keyT:
                continue
            if valueT[0] == ';':
                break
            if valueT[0] == ')':
                isArgs = True
                break

        # The following <Identifier> is specified with 'classname <Identifier>'
        # Find all instance methods
        tokens = set([keyS])
        instancename = valueS[0]
        for keyT, valueT in dic.items():
            if keyS >= keyT:
                continue
            if dic[keyT][1] == 'Identifier' and dic[keyT + 1][0] == instancename:
                break
            if valueT[1] == '}' and valueS[3] + int(isArgs) > valueT[3]:
                break

            if dic[keyT][0] == instancename and dic[keyT + 1][0] == '.':
                tokens.add(keyT)

        print(tokens)

        # Search lines including the instane methods
        lines = set([dic[keyT][2]])
        for n in tokens:
            for i in range(n)[::-1]: # front
                if dic[i][0] == '(':
                    lines.add(dic[i][2])
                    break
                if dic[i][0] == ';' or dic[i][0] == '{' or dic[i][0] == '}':
                    lines.add(dic[i + 1][2])
                    break

            for i in range(len(dic))[n + 1:]:
                if dic[i][0] == ';':
                    lines.add(dic[i][2])
                    break

        catalog.append(sorted(list(lines)))

    return catalog


def setter(catalog, classname, idname):
    li = []
    with open('CodeResult/{}/{}.java'.format(classname, idname), 'r') as f:
        for example in catalog:
            for i, line in enumerate(f):
                if str(i + 1) in example:
                    li.append([str(i + 1), line])

    seen = []
    li = [x for x in li if x[1] not in seen and not seen.append(x[1])]

    jsn = {'id': idname, 'lines': {}}
    for x in li:
        print(x[0], x[1].split('\n')[0])
        jsn['lines'][x[0]] = x[1]

    os.makedirs('CodeExample/' + classname, exist_ok=True)
    with open('CodeExample/{}/{}.json'.format(classname, idname), 'w') as f:
        json.dump(jsn, f, indent=2)
    print('\n')


def f(arg):
    classname = arg
    print('Download:', classname)
    res = subprocess.check_output(['ls', '-1', 'CodeToken/{}/'.format(classname)]).decode('utf-8')
    li = res.split('\n')[:-1]
    for x in li:
        idname = x[:-6]
        res = getter(classname, idname)
        data = extract(classname, res)
        setter(data, classname, idname)


if __name__ == '__main__':
    res = subprocess.check_output(['ls', '-1', 'CodeToken/']).decode('utf-8')
    li = res.split('\n')[:-1]
    f('XSSFWorkbook')
    # with multiprocessing.Pool(10) as p:
    #     p.map(f, li)
