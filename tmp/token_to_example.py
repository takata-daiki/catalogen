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

                if ID == '}' or ID == ')':
                    dep -= 1

                dic[pos] = [ID, token, line, dep]

                if ID == '{' or ID == '(':
                    dep += 1

            except AttributeError as e:
                dic[pos] = [',', "','", line, dep]

    # pprint(dic)
    return dic


def extract(classname, dic):
    catalog = []
    for s in range(len(dic)):
        if s < 1 or dic[s - 1][0] != classname or dic[s][1] != 'Identifier' or dic[s + 1][0] == '(':
            continue

        # Now, the instance should be determined whether it is function arguments or not
        # isArgs = False
        # for t in range(len(dic)):
        #     if s >= t:
        #         continue
        #     if dic[t][0] == ';':
        #         break
        #     if dic[t][0] == ')':
        #         isArgs = True
        #         break

        # The following <Identifier> is specified with 'classname <Identifier>'
        # Find all instance methods
        tokenNum = set([s])
        instancename = dic[s][0]
        for t in range(len(dic)):
            if s >= t:
                continue
            if dic[t][1] == 'Identifier' and dic[t + 1][0] == instancename and dic[s][3] == dic[t][3]:
                break
            if dic[t][0] == '}' and dic[s][3] > dic[t][3]:
                break

            if dic[t][0] == instancename and dic[t + 1][0] == '.':
                tokenNum.add(t)

        # Search lines including the instance methods
        lineNum = set([dic[s][2]])
        for n in tokenNum:
            lineNum.add(dic[n][2])
        #     for i in range(n)[::-1]: # front
        #         if dic[i][0] == '(':
        #             lineNum.add(dic[i][2])
        #             break
        #         if dic[i][0] == ';' or dic[i][0] == '{' or dic[i][0] == '}':
        #             lineNum.add(str(int(dic[i][2]) + 1))
        #             break
        #
        #     for i in range(len(dic))[n + 1:]:
        #         if dic[i][0] == ';':
        #             lineNum.add(dic[i][2])
        #             break

        if len(lineNum) >= 2:
            catalog.append(list(lineNum))

    # print(catalog)
    return catalog


def setter(catalog, classname, idname):
    li = []
    for example in catalog:
        tmp = []
        with open('CodeResult/{}/{}.java'.format(classname, idname), 'r') as f:
            for i, line in enumerate(f):
                if str(i + 1) in example:
                    tmp.append([str(i + 1), line.replace('\t', '  ')])
        seen = []
        tmp = [x for x in tmp if x[1] not in seen and not seen.append(x[1])]
        li.append(tmp)
    # print([[v[0] for v in x] for x in li])

    jsn = {'id': idname, 'lines': []}
    for x in li:
        lines = {}
        for v in x:
            # print(v[0], v[1].split('\n')[0])
            lines[v[0]] = v[1]
        jsn['lines'].append(lines)

    if jsn['lines']:
        # os.makedirs('CodeExample/' + classname, exist_ok=True)
        os.makedirs('CodeExampleJson/' + classname, exist_ok=True)
    for idx, val in enumerate(jsn['lines']):
        tmp = {'id': idname, 'lines': {}}
        with open('CodeExampleJson/{}/{}_{}.json'.format(classname, idname, idx + 1), 'w') as f:
            mn = min([re.match('(\t| )*', v).end() for v in val.values()])
            for i, v in val.items():
                tmp['lines'][i] = v[mn:]
            json.dump(tmp, f, indent=2)
    # for idx, val in enumerate(jsn['lines']):
    #     with open('CodeExample/{}/{}_{}.txt'.format(classname, idname, idx + 1), 'w') as f:
    #         for v in val.values():
    #             f.write(v)


def f(arg):
    classname = arg
    print('Download:', classname)
    res = subprocess.check_output(['ls', '-1', 'CodeToken/{}/'.format(classname)]).decode('utf-8')
    li = res.split('\n')[:-1]
    for x in li:
        idname = x[:-6]
        # print(idname)
        res = getter(classname, idname)
        data = extract(classname, res)
        setter(data, classname, idname)


if __name__ == '__main__':
    res = subprocess.check_output(['ls', '-1', 'CodeToken/']).decode('utf-8')
    li = res.split('\n')[:-1]
    # f('XSSFWorkbook')
    with multiprocessing.Pool(10) as p:
        p.map(f, li)
