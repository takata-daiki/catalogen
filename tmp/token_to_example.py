#!/usr/bin/env python3
import json
import multiprocessing
import os
import re
import subprocess

blocks = ''
block_num = {}


def getter(class_name, id_name):
    global blocks
    dic = {}
    with open('CodeToken/{}/{}.token'.format(class_name, id_name), 'r') as f:
        dep = 0
        for data in f:
            arr = data.split(',')
            try:
                pos = int(arr[0][2:])
                id_ = re.search(r"'(.+)'", arr[1]).group(1)
                token = re.search(r'<(.+)>', arr[2]).group(1)
                line = re.search(r'(.+):', arr[3]).group(1)

                if id_ == '}' or id_ == ')':
                    dep -= 1

                dic[pos] = [id_, token, line, dep]

                if id_ == '{' or id_ == '(':
                    dep += 1

                if id_ == '{' or id_ == '}':
                    blocks += id_

            except AttributeError as e:
                dic[pos] = [',', "','", line, dep]

            block_num[line] = len(blocks)
    # pprint(dic)
    return dic


def extract(class_name, dic):
    catalog = []
    for s in range(len(dic)):
        if s < 1 or dic[s - 1][0] != class_name or dic[s][1] != 'Identifier' or dic[s + 1][0] == '(':
            continue

        # Now, the instance should be determined whether it is function arguments or not
        # is_args = False
        # for i in range(s)[::-1]:
        #     if dic[i][2] != dic[s][2]:
        #         break
        #     if dic[i][0] == '(':
        #         is_args = True
        #
        # isArgs = False
        # for t in range(len(dic)):
        #     if s >= t:
        #         continue
        #     if dic[t][0] == ';':
        #         break
        #     if dic[t][0] == ')':
        #         isArgs = True
        #         break

        # The following <Identifier> is specified with 'class_name <Identifier>'
        # Find all instance methods
        token_num = set([s])
        instance_name = dic[s][0]
        for t in range(len(dic)):
            if s >= t:
                continue
            if dic[t][1] == 'Identifier' and dic[t + 1][0] == instance_name and dic[s][3] == dic[t][3]:
                break
            if dic[t][0] == '}' and dic[s][3] > dic[t][3]:
                break

            if dic[t][0] == instance_name and dic[t + 1][0] == '.':
                token_num.add(t)

        # Search lines including the instance methods
        line_num = set([dic[s][2]])
        #     if i > 1 and dic[i - 1][0] == ')' and dic[i][0] == '{':
        #         st = set()
        #         for j in range(i - 1)[::-1]:
        #             st.add(dic[j][2])
        #             if dic[j][0] == '(':
        #                 if dic[j - 2][0] != '.' and dic[j - 1][1] == 'Identifier':
        #                     line_num |= st
        #                 break
        #         if len(line_num) > 1:
        #             break

        now_cnt = 1
        for n in token_num:
            line_num.add(dic[n][2])
            for i in range(n)[::-1]:  # front
                if dic[i][2] == dic[n][2]:
                    continue
                if dic[i][0] == ';' or dic[i][0] == '{' or dic[i][0] == '}':
                    break
                line_num.add(dic[i][2])

            for i in range(len(dic))[n + 1:]:  # back
                line_num.add(dic[i][2])
                if dic[i][0] == ';':
                    break
            if n == s:
                now_cnt = len(line_num)

        if len(line_num) > now_cnt:
            catalog.append(list(line_num))

    # print(catalog)
    return catalog


def removed(s):
    for i in range(len(s)):
        s = s.replace('{}', '')
    return s


def setter(catalog, class_name, id_name):
    li = []
    for example in catalog:
        tmp = []
        with open('CodeResult/{}/{}.java'.format(class_name, id_name), 'r') as f:
            for i, line in enumerate(f):
                if str(i + 1) in example:
                    tmp.append([str(i + 1), line.replace('\t', '  ')])
        seen = []
        tmp = [x for x in tmp if x[1] not in seen and not seen.append(x[1])]
        li.append(tmp)
    # print([[v[0] for v in x] for x in li])

    jsn = {'id': id_name, 'lines': []}
    for x in li:
        lines = {}
        for v in x:
            # print(v[0], v[1].split('\n')[0])
            lines[v[0]] = v[1]
        jsn['lines'].append(lines)

    if jsn['lines']:
        # os.makedirs('CodeExampleJson/' + class_name, exist_ok=True)
        os.makedirs('CodeExample/' + class_name, exist_ok=True)
    # for idx, val in enumerate(jsn['lines']):
    #     tmp = {'id': id_name, 'lines': {}}
    #     with open('CodeExampleJson/{}/{}_{}.json'.format(class_name, id_name, idx + 1), 'w') as f:
    #         mn = min([re.match('(\t| )*', v).end() for v in val.values()])
    #         for i, v in val.items():
    #             tmp['lines'][i] = v[mn:]
    #         json.dump(tmp, f, indent=2)
    for idx, val in enumerate(jsn['lines']):
        with open('CodeExample/{}/{}_{}.txt'.format(class_name, id_name, idx + 1), 'w') as f:
            prev = None
            is_args = False
            block_s_cnt = 0
            block_t_cnt = 0
            for cur, v in val.items():
                if prev is None:
                    if '(' in v[:v.find(class_name)]:
                        if 'for' not in v:
                            is_args = True
                    if not is_args:
                        f.write('public void wrapperMethod() {\n')
                        block_s_cnt += 1
                    f.write(v)
                else:
                    left = block_num[prev]
                    right = block_num[cur]
                    right -= v.count('{') + v.count('}')
                    if left < right:
                        tmp = removed(blocks[left:right])
                        f.write('{}\n'.format(tmp))
                        block_s_cnt += tmp.count('{')
                        block_t_cnt += tmp.count('}')
                    f.write(v)
                block_s_cnt += v.count('{')
                block_t_cnt += v.count('}')
                prev = cur

            if not is_args:
                f.write('}')
                block_t_cnt += 1
            f.write('}' * max([0, block_s_cnt - block_t_cnt]))

            # for v in val.values():
            #     f.write(v)


def f(arg):
    class_name = arg
    print('Download:', class_name)
    res = subprocess.check_output(['ls', '-1', 'CodeToken/{}/'.format(class_name)]).decode('utf-8')
    li = res.split('\n')[:-1]
    for x in li:
        id_name = x[:-6]
        # print(id_name)
        res = getter(class_name, id_name)
        data = extract(class_name, res)
        setter(data, class_name, id_name)


def main():
    res = subprocess.check_output(['ls', '-1', 'CodeToken/']).decode('utf-8')
    li = res.split('\n')[:-1]
    # f('XSSFWorkbook')
    with multiprocessing.Pool(10) as p:
        p.map(f, li)


if __name__ == '__main__':
    main()
