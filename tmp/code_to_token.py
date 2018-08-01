#!/usr/bin/env python3
import os
import subprocess
import multiprocessing


def getter(classname):
    res = subprocess.check_output(['ls', '-1', 'CodeResult/{}/'.format(classname)]).decode('utf-8')
    li = res.split('\n')[:-1]
    return li


def setter(classname, arg):
    os.makedirs('CodeToken/' + classname, exist_ok=True)
    for x in arg:
        idname = x[:-5]
        with open('CodeToken/{}/{}.token'.format(classname, idname), 'w') as f:
            subprocess.Popen(['grun', 'Java8', 'tokens', '../CodeResult/{}/{}.java'.format(classname, idname), '-tokens'], cwd='./antlr', stdout=f)
        # print('{0}.java -> {0}.token'.format(idname))


def f(arg):
    classname = arg
    print('Download:', classname)
    res = getter(classname)
    setter(classname, res)


if __name__ == '__main__':
    res = subprocess.check_output(['ls', '-1', 'CodeResult/']).decode('utf-8')
    li = res.split('\n')[:-1]
    with multiprocessing.Pool(1) as p:
        p.map(f, li)
