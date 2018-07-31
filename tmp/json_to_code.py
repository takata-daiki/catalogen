#!/usr/bin/env python3
import os
import json
import requests
import subprocess
import multiprocessing

url = 'https://searchcode.com/api/result/{}/'


def request(path):
    r = requests.get(url.format(path))
    try:
        data = r.json()
    except json.decoder.JSONDecodeError:
        data = {}
    return data


def getter(classname):
    dic = {}
    with open('CodeIndex/{}.json'.format(classname), 'r') as f:
        jsn = json.load(f)
        for q in jsn['results']:
            data = request(q['id'])
            try:
                dic[q['id']] = data['code']
            except KeyError:
                pass
    return dic


def setter(classname, arg):
    os.makedirs('CodeResult/' + classname, exist_ok=True)
    for key, value in arg.items():
        with open('CodeResult/{}/{}.java'.format(classname, key), 'w') as f:
            f.write(value)


def f(arg):
    classname = arg[:-5]
    print('Download:', classname)
    res = getter(classname)
    setter(classname, res)


if __name__ == '__main__':
    res = subprocess.check_output(['ls', '-1', 'CodeIndex/']).decode('utf-8')
    li = res.split('\n')[:-1]
    with multiprocessing.Pool(50) as p:
        p.map(f, li)
