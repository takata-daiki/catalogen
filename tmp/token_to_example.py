#!/usr/bin/env python3
import json
import re

classID = 'XSSFWorkbook'
inv = {}

def input(name):
    dic = {}
    with open(name) as f:
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
                inv[line] = dep

                if ID == '{':
                    dep += 1

            except AttributeError as e:
                dic[pos] = [',', "','", line, dep]

    return dic


def extract(dic):
    ans = []
    for keyS, valueS in dic.items():
        if valueS[0] != classID: continue
        mx = -1
        keyP = keyS + 1
        valueP = dic[keyP]
        if valueP[1] != 'Identifier': continue

        # The following <Identifier> is specified with 'classID <Identifier>'
        # Find all instance methods
        for keyN, valueN in dic.items():
            if valueN[1] != 'Identifier' or keyP >= keyN: continue
            if dic[keyN][0] != classID and dic[keyN + 1][0] == valueP[0]: break
            if valueP[0] == valueN[0] and dic[keyN + 1][0] == '.':
                mx = max([mx, keyN])
        if mx == -1: continue

        # Search a last token in the end line
        for keyN, valueN in dic.items():
            if mx >= keyN: continue
            if valueN[0] == ';':
                involved = []
                last = '0'
                for i in range(len(dic)):
                    if i < keyS or keyN < i: continue

                    if dic[i][0] == valueP[0]:
                        # if involved: print(inv[involved[-1]], dic[i][3], last)
                        involved.append(dic[i][2])

                ans.append(involved)
                break

    # print(ans)
    return ans


def output(ans, name):
    arr = []
    with open(name) as f:
        for i, data in enumerate(f):
            if str(i + 1) in ans[0]:
                arr.append([str(i + 1), data])

    seen = []
    arr2 = [x for x in arr if x[1] not in seen and not seen.append(x[1])]

    for x in arr2:
        print(x[0] + x[1].split('\n')[0])

    return arr2

if __name__ == '__main__':
    data = input('code_results_to_tokens')
    res = extract(data)
    ans = output(res, 'code_results.java')

