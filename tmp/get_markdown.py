#!/usr/bin/env python3
import glob
import json
import os

import pandas as pd

df = pd.read_csv('cluster_v5.csv').set_index(['directory', 'filename'])


def homepage():
    text = '---\nlayout: default\n---\n\n' \
           '<input type="text" id="myInput" onkeyup="myFunction()" placeholder="Search for any class.."' \
           ' title="Type in a name">\n\n***\n\n' \
           '<ul id="myUL">\n'

    os.chdir('CodeExampleJson/')
    li = glob.glob('*')
    for line in sorted(li):
        text += '<li><a class="page-scroll" href="./{}/">{}</a></li>\n'.format(line, line)
    os.chdir('../')
    text += '</ul>'

    with open('docs/index.md', 'w') as f:
        f.write(text)


def represent():
    try:
        li = os.listdir('CodeExampleJson')
    except FileNotFoundError:
        return
    # os.chdir('CodeExampleJson/')
    # li = glob.glob('*')
    li = [line for line in sorted(li)]
    # os.chdir('../')

    for name in li:
        rep_cluster_arr = []
        # try:
        #     os.chdir('CodeExampleJson/{}/'.format(name))
        # except FileNotFoundError:
        #     continue
        #
        # sample = glob.glob('*')
        # # sample.sort()
        # os.chdir('../../')
        try:
            sample = os.listdir('CodeExampleJson/{}'.format(name))
        except FileNotFoundError:
            return
        tmp = []
        for s in sample:
            with open('CodeExampleJson/{}/{}'.format(name, s), 'r') as f:
                data = json.load(f)
                tmp.append([len(data['lines']), s])
        tmp.sort(key=lambda x: x[0])
        sample = [t[1] for t in tmp]
        try:
            sz = max([int(df.at[(name, '{}.txt'.format(s[:-5])), 'cluster']) for s in sample])
        except KeyError:
            sz = 0

        for i in range(sz + 1):
            try:
                tmp = [s[:-5] for s in sample if i == int(df.at[(name, s[:-5]), 'cluster'])]
                y = len(tmp)
                for j in range(len(tmp)):
                    x = tmp[j]
                    try:
                        with open('CodeAst/_{}_{}.txt'.format(name, x), 'r') as f:
                            rd = f.read()
                        p = 0
                        with open('ast_seqs.txt', 'r') as f:
                            for k, comm in enumerate(f):
                                if comm == rd:
                                    p = k
                                    break
                        with open('jd_comms.txt', 'r') as f:
                            z = f.read().split('\n')[p]
                        break
                    except FileNotFoundError:
                        continue
                else:
                    x = tmp[0]
                    z = 'this comment could not be generated...'

            except KeyError:
                x = sample[0][:-5]
                y = 1
                try:
                    with open('CodeAst/_{}_{}.txt'.format(name, x), 'r') as f:
                        rd = f.read()
                    p = 0
                    with open('ast_seqs.txt', 'r') as f:
                        for k, comm in enumerate(f):
                            if comm == rd:
                                p = k
                                break
                    with open('jd_comms.txt', 'r') as f:
                        z = f.read().split('\n')[p]
                except FileNotFoundError:
                    z = 'this comment could not be generated...'

        rep_cluster_arr.append({'id': x, 'num': y, 'comment': z})

        text = '# {}\n\n***\n\n'.format(name)
        for i, dic in enumerate(rep_cluster_arr):
            with open('CodeExampleJson/{}/{}.json'.format(name, dic['id']), 'r') as f:
                data = json.load(f)
                if not data['lines']:
                    continue
                text += '## [Cluster {}](./{})\n'.format(i + 1, i + 1)
                text += '{} results\n'.format(dic['num'])
                text += '> {}\n'.format(dic['comment'])
                text += '{% highlight java %}\n'
                for j, line in data['lines'].items():
                    text += '{0}. {1}\n'.format(j, line.split('\n')[0])
                text += '{% endhighlight %}\n\n***\n\n'

            catalog(name, i)

        os.makedirs('docs/{}'.format(name), exist_ok=True)
        with open('docs/{}/index.md'.format(name), 'w') as f:
            f.write(text)

        print(text)


def catalog(classname, n):
    # try:
    #     os.chdir('CodeExampleJson/{}/'.format(classname))
    # except FileNotFoundError:
    #     return
    #
    # sample = glob.glob('*')
    # # sample.sort()
    # os.chdir('../../')
    try:
        sample = os.listdir('CodeExampleJson/{}'.format(classname))
    except FileNotFoundError:
        return
    tmp = []
    for s in sample:
        with open('CodeExampleJson/{}/{}'.format(classname, s), 'r') as f:
            data = json.load(f)
            tmp.append([len(data['lines']), s])
    tmp.sort(key=lambda x: x[0])
    sample = [t[1] for t in tmp]
    try:
        li = [s[:-5] for s in sample if n == int(df.at[(classname, '{}.txt'.format(s[:-5])), 'cluster'])]
    except KeyError:
        li = [sample[0][:-5]]

    files = []
    with open('CodeIndex/{}.json'.format(classname), 'r') as f:
        data = json.load(f)
        for x in li:
            for d in data['results']:
                s = x.split('_')[0]
                if str(d['id']) == s:
                    files.append([x, d['filename']])
        # for d in data['results']:
        #     for x in li:
        #         s = x.split('_')[0]
        #         if str(d['id']) == s:
        #             files[x] = d['filename']

    text = '# {} @Cluster {}\n\n***\n\n'.format(classname, n + 1)
    for k, v in files:
        with open('CodeExampleJson/{}/{}.json'.format(classname, k), 'r') as f:
            data = json.load(f)
            if not data['lines']:
                continue
            text += '### [{}](https://searchcode.com/codesearch/view/{}/)\n'.format(v, k.split('_')[0])
            text += '{% highlight java %}\n'
            for i, line in data['lines'].items():
                text += '{0}. {1}\n'.format(i, line.split('\n')[0])
            text += '{% endhighlight %}\n\n***\n\n'

    os.makedirs('docs/{}/{}'.format(classname, n + 1), exist_ok=True)
    with open('docs/{}/{}/index.md'.format(classname, n + 1), 'w') as f:
        f.write(text)

    print(text)


if __name__ == '__main__':
    represent()
