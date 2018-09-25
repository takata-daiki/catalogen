#!/usr/bin/env python3
import glob
import json
import os

import pandas as pd
from sklearn.feature_extraction.text import TfidfVectorizer

df = pd.read_csv('cluster_v5.csv').set_index(['directory', 'filename'])


def homepage():
    text = '---\nlayout: default\n---\n\n' \
           '<input type="text" id="myInput" onkeyup="myFunction()" placeholder="Search for any class.."' \
           ' title="Type in a name">\n\n***\n\n' \
           '<ul id="myUL">\n'

    li = os.listdir('CodeExample')
    for line in sorted(li):
        text += '<li><a class="page-scroll" href="./{}/">{}</a></li>\n'.format(line, line)
    text += '</ul>'

    with open('docs/index.md', 'w') as f:
        f.write(text)


def represent():
    try:
        li = os.listdir('CodeExample')
    except FileNotFoundError:
        return

    for name in sorted(li):
        is_ok = True
        rep_cluster_arr = []
        try:
            sample = os.listdir('CodeExampleJson/{}'.format(name))
        except FileNotFoundError:
            continue
        tmp = os.listdir('CodeExample/{}'.format(name))
        sample = [s for s in sample if '{}.txt'.format(s[:-5]) in tmp]
        # sample.sort()
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

        tfidf_vectorizer = TfidfVectorizer(input='filename', max_df=0.5, min_df=1, max_features=3, norm='l2')
        for i in range(sz + 1):
            try:
                tmp = [s[:-5] for s in sample if i == int(df.at[(name, '{}.txt'.format(s[:-5])), 'cluster'])]

                files = ['CodeExample/{}/{}.txt'.format(name, t) for t in tmp]
                if not files:
                    is_ok = False
                    break
                try:
                    tfidf = tfidf_vectorizer.fit_transform(files)
                except ValueError:
                    tfidf_vectorizer = TfidfVectorizer(input='filename', max_df=1.0, min_df=1, max_features=3, norm='l2')
                    tfidf = tfidf_vectorizer.fit_transform(files)
                feature = tfidf_vectorizer.get_feature_names()

                y = len(tmp)
                for j in range(len(tmp)):
                    x = tmp[j]
                    try:
                        with open('CodeAst/_{}_{}.txt'.format(name, x), 'r') as f:
                            rd = f.read()
                    except FileNotFoundError:
                        continue
                    p = 0
                    with open('ast_seqs.txt', 'r') as f:
                        for k, comm in enumerate(f):
                            if comm == rd:
                                p = k
                                break
                    with open('jd_comms.txt', 'r') as f:
                        z = f.read().split('\n')[p]
                    break
                else:
                    x = tmp[0]

            except KeyError:
                x = sample[0][:-5]
                y = 1
                try:
                    with open('CodeAst/_{}_{}.txt'.format(name, x), 'r') as f:
                        rd = f.read()
                except FileNotFoundError:
                    continue
                p = 0
                with open('ast_seqs.txt', 'r') as f:
                    for k, comm in enumerate(f):
                        if comm == rd:
                            p = k
                            break
                with open('jd_comms.txt', 'r') as f:
                    z = f.read().split('\n')[p]

            try:
                rep_cluster_arr.append({'id': x, 'num': y, 'comment': z, 'feature': feature})
            except UnboundLocalError:
                is_ok = False
                break

        if not is_ok:
            continue

        text = '# {}\n\n***\n\n'.format(name)
        for i, dic in enumerate(rep_cluster_arr):
            with open('CodeExampleJson/{}/{}.json'.format(name, dic['id']), 'r') as f:
                data = json.load(f)
                if not data['lines']:
                    continue
                try:
                    text += '## [Cluster {} ({}, {}, {})](./{})\n'.format(i + 1,
                                                                      dic['feature'][0],
                                                                      dic['feature'][1],
                                                                      dic['feature'][2],
                                                                      i + 1)
                except IndexError:
                    text += '## [Cluster {}](./{})\n'.format(i + 1, i + 1)
                text += '{} results\n'.format(dic['num'])
                text += '> {}\n'.format(dic['comment'])
                text += '{% highlight java %}\n'
                for j, line in data['lines'].items():
                    text += '{0}. {1}\n'.format(j, line.split('\n')[0])
                text += '{% endhighlight %}\n\n***\n\n'

            catalog(name, i, dic['feature'])

        os.makedirs('docs/{}'.format(name), exist_ok=True)
        with open('docs/{}/index.md'.format(name), 'w') as f:
            f.write(text)

        print(text)


def catalog(classname, n, feature):
    try:
        sample = os.listdir('CodeExampleJson/{}'.format(classname))
    except FileNotFoundError:
        return
    # sample.sort()
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
            try:
                with open('CodeAst/_{}_{}.txt'.format(classname, x), 'r') as f2:
                    rd = f2.read()
            except FileNotFoundError:
                continue
            p = 0
            with open('ast_seqs.txt', 'r') as f2:
                for k, comm in enumerate(f2):
                    if comm == rd:
                        p = k
                        break
            with open('jd_comms.txt', 'r') as f2:
                z = f2.read().split('\n')[p]

            for d in data['results']:
                s = x.split('_')[0]
                if str(d['id']) == s:
                    files.append([x, d['filename'], z])

    try:
        text = '# {} @Cluster {} ({}, {}, {})\n\n***\n\n'.format(classname, n + 1,
                                                feature[0],
                                                feature[1],
                                                feature[2])
    except IndexError:
        text = '# {} @Cluster {}\n\n***\n\n'.format(classname, n + 1)

    for k, v, c in files:
        with open('CodeExampleJson/{}/{}.json'.format(classname, k), 'r') as f:
            data = json.load(f)
            if not data['lines']:
                continue
            text += '### [{}](https://searchcode.com/codesearch/view/{}/)\n'.format(v, k.split('_')[0])
            text += '> {}\n'.format(c)
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
