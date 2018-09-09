#!/usr/bin/env python3
import glob
import json
import os

import pandas as pd

df = pd.read_csv('cluster_v4.csv').set_index(['directory', 'filename'])


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
    os.chdir('CodeExampleJson/')
    li = glob.glob('*')
    li = [line for line in sorted(li)]
    os.chdir('../')

    for name in li:
        arr = []
        try:
            os.chdir('CodeExampleJson/{}/'.format(name))
        except FileNotFoundError:
            continue

        sample = glob.glob('*')
        sample.sort()
        os.chdir('../../')
        try:
            sz = int(max([df.at[(name, '{}.txt'.format(s[:-5])), 'cluster'] for s in sample]))
        except KeyError:
            sz = 1

        for i in range(1, sz + 1):
            try:
                x = [s[:-5] for s in sample if i == int(df.at[(name, '{}.txt'.format(s[:-5])), 'cluster'])][0]
            except KeyError:
                x = sample[0][:-5]
            arr.append(x)

        text = '# {}\n\n***\n\n'.format(name)
        for i, x in enumerate(arr):
            with open('CodeExampleJson/{}/{}.json'.format(name, x), 'r') as f:
                data = json.load(f)
                if not data['lines']:
                    continue
                text += '### [Cluster {}](./{})\n'.format(i + 1, i + 1)
                text += '{% highlight java %}\n'
                for j, line in data['lines'].items():
                    text += '{0}. {1}\n'.format(j, line.split('\n')[0])
                text += '{% endhighlight %}\n\n***\n\n'

            catalog(name, i + 1)

        os.makedirs('docs/{}'.format(name), exist_ok=True)
        with open('docs/{}/index.md'.format(name), 'w') as f:
            f.write(text)

        print(text)


def catalog(classname, n):
    try:
        os.chdir('CodeExampleJson/{}/'.format(classname))
    except FileNotFoundError:
        return

    sample = glob.glob('*')
    sample.sort()
    os.chdir('../../')
    try:
        li = [s[:-5] for s in sample if n == int(df.at[(classname, '{}.txt'.format(s[:-5])), 'cluster'])]
    except KeyError:
        li = [sample[0][:-5]]

    files = {}
    with open('CodeIndex/{}.json'.format(classname), 'r') as f:
        data = json.load(f)
        for d in data['results']:
            for x in li:
                s = x.split('_')[0]
                if str(d['id']) == s:
                    files[x] = d['filename']

    text = '# {} @Cluster {}\n\n***\n\n'.format(classname, n)
    for k, v in files.items():
        with open('CodeExampleJson/{}/{}.json'.format(classname, k), 'r') as f:
            data = json.load(f)
            if not data['lines']:
                continue
            text += '### [{}](https://searchcode.com/codesearch/view/{}/)\n'.format(v, k.split('_')[0])
            text += '{% highlight java %}\n'
            for i, line in data['lines'].items():
                text += '{0}. {1}\n'.format(i, line.split('\n')[0])
            text += '{% endhighlight %}\n\n***\n\n'

    os.makedirs('docs/{}/{}'.format(classname, n), exist_ok=True)
    with open('docs/{}/{}/index.md'.format(classname, n), 'w') as f:
        f.write(text)

    print(text)


if __name__ == '__main__':
    represent()
