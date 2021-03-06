#!/usr/bin/env python3
import json
import requests
import multiprocessing

java = '23'
googlecode = '1'
github = '2'
bitbucket = '3'
sourceforge = '4'
gitlab = '13'
url  = 'https://searchcode.com/api/codesearch_I/'


def request(query):
    r = requests.get(url, params=query)
    try:
        data = r.json()
        if data['total'] > 0:
            print('Download:', query['q'], '({})'.format(data['total']))
    except json.decoder.JSONDecodeError:
        data = {}

    return data


def wrapper(arg):
    dic = {'q': arg, 'lan': java, 'src' : [googlecode, github, bitbucket, sourceforge, gitlab] }
    return request(dic)


def getter():
    li = []
    with open('fully_qualified_class_name_list.txt', 'r') as f:
        for line in f:
            classname = line.split('\n')[0]
            li.append(classname)

    arr = []
    with multiprocessing.Pool(30) as p:
        data = p.map(wrapper, li)
        arr.extend(data)

    return arr


def setter(arg):
    try:
        if arg['total'] == 0:
            return
        with open('CodeIndex/{}.json'.format(arg['query'].split('.')[-1]), 'w') as f:
            json.dump(arg, f, indent=2)
    except KeyError:
        pass


if __name__ == '__main__':
    res = getter()
    with multiprocessing.Pool(30) as p:
        p.map(setter, res)
