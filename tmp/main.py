#!/usr/bin/env python3
import json
import os
import sys

import numpy as np
import pandas as pd
from sklearn.feature_extraction.text import TfidfVectorizer

from clustering import xmean


def make_cluster(class_name):
    data = np.load('vectorize_v2.npz')
    sample = os.listdir('CodeExample/{}'.format(class_name))
    xm = np.array([data['vector'][i] for i in range(len(data['vector'])) if
                   data['directory'][i] == class_name and data['filename'][i] in sample])
    files = np.array(
        [data['filename'][i][:-4] for i in range(len(data['vector'])) if
         data['directory'][i] == class_name and data['filename'][i] in sample])
    clusters = xmean(xm.tolist())

    df = pd.DataFrame({'filename': [], 'directory': [], 'cluster': []})
    for i in range(len(clusters)):
        df.loc[i] = [files[i], class_name, clusters[i]]

    return df.set_index(['directory', 'filename'])


def get_md(class_name, df):
    rep_cluster_arr = []
    try:
        sample = os.listdir('CodeExampleJson/{}'.format(class_name))
    except FileNotFoundError:
        return
    tmp = os.listdir('CodeExample/{}'.format(class_name))
    sample = [s for s in sample if '{}.txt'.format(s[:-5]) in tmp]
    # sample.sort()
    tmp = []
    for s in sample:
        with open('CodeExampleJson/{}/{}'.format(class_name, s), 'r') as f:
            data = json.load(f)
            tmp.append([len(data['lines']), s])
    tmp.sort(key=lambda x: x[0])
    sample = [t[1] for t in tmp]
    try:
        sz = max([int(df.at[(class_name, s[:-5]), 'cluster']) for s in sample])
    except KeyError:
        sz = 0

    tfidf_vectorizer = TfidfVectorizer(input='filename', max_df=0.5, min_df=1, max_features=3, norm='l2')
    for i in range(sz + 1):
        try:
            tmp = [s[:-5] for s in sample if i == int(df.at[(class_name, s[:-5]), 'cluster'])]

            files = ['CodeExample/{}/{}.txt'.format(class_name, t) for t in tmp]
            try:
                tfidf = tfidf_vectorizer.fit_transform(files)
            except ValueError:
                tfidf_vectorizer = TfidfVectorizer(input='filename', max_df=1.0, min_df=1, max_features=3, norm='l2')
                tfidf = tfidf_vectorizer.fit_transform(files)
            feature = tfidf_vectorizer.get_feature_names()

            y = len(tmp)
            for j in range(len(tmp)):
                x = tmp[j]
                with open('CodeAst/_{}_{}.txt'.format(class_name, x), 'r') as f:
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
            else:
                x = tmp[0]

        except KeyError:
            x = sample[0][:-5]
            y = 1
            with open('CodeAst/_{}_{}.txt'.format(class_name, x), 'r') as f:
                rd = f.read()
            p = 0
            with open('ast_seqs.txt', 'r') as f:
                for k, comm in enumerate(f):
                    if comm == rd:
                        p = k
                        break
            with open('jd_comms.txt', 'r') as f:
                z = f.read().split('\n')[p]

        rep_cluster_arr.append({'id': x, 'num': y, 'comment': z, 'feature': feature})

    text = '---\nlayout: default\n---\n\n# {}\n\n***\n\n'.format(class_name)
    for i, dic in enumerate(rep_cluster_arr):
        with open('CodeExampleJson/{}/{}.json'.format(class_name, dic['id']), 'r') as f:
            data = json.load(f)
            if not data['lines']:
                continue
            text += '## [Cluster {} ({}, {}, {})](./cluster/{}/index.html)\n'.format(i + 1,
                                                                                     dic['feature'][0],
                                                                                     dic['feature'][1],
                                                                                     dic['feature'][2],
                                                                                     i + 1)
            text += '{} results\n'.format(dic['num'])
            text += '> {}\n'.format(dic['comment'])
            text += '{% highlight java %}\n'
            for j, line in data['lines'].items():
                text += '{0}. {1}\n'.format(j, line.split('\n')[0])
            text += '{% endhighlight %}\n\n***\n\n'

        catalog(class_name, i, df, dic['feature'])

    with open('minimal/index.md', 'w') as f:
        f.write(text)


def catalog(class_name, n, df, feature):
    try:
        sample = os.listdir('CodeExampleJson/{}'.format(class_name))
    except FileNotFoundError:
        return
    tmp = os.listdir('CodeExample/{}'.format(class_name))
    sample = [s for s in sample if '{}.txt'.format(s[:-5]) in tmp]
    # sample.sort()
    tmp = []
    for s in sample:
        with open('CodeExampleJson/{}/{}'.format(class_name, s), 'r') as f:
            data = json.load(f)
            tmp.append([len(data['lines']), s])
    tmp.sort(key=lambda x: x[0])
    sample = [t[1] for t in tmp]
    try:
        li = [s[:-5] for s in sample if n == int(df.at[(class_name, s[:-5]), 'cluster'])]
    except KeyError:
        li = [sample[0][:-5]]

    files = []
    with open('CodeIndex/{}.json'.format(class_name), 'r') as f:
        data = json.load(f)
        for x in li:
            with open('CodeAst/_{}_{}.txt'.format(class_name, x), 'r') as f2:
                rd = f2.read()
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

    text = '---\nlayout: default\n---\n\n# {} @Cluster {} ({}, {}, {})\n\n***\n\n'.format(class_name, n + 1,
                                                                                          feature[0],
                                                                                          feature[1],
                                                                                          feature[2])
    for k, v, c in files:
        with open('CodeExampleJson/{}/{}.json'.format(class_name, k), 'r') as f:
            data = json.load(f)
            if not data['lines']:
                continue
            text += '### [{}](https://searchcode.com/codesearch/view/{}/)\n'.format(v, k.split('_')[0])
            text += '> {}\n'.format(c)
            text += '{% highlight java %}\n'
            for i, line in data['lines'].items():
                text += '{0}. {1}\n'.format(i, line.split('\n')[0])
            text += '{% endhighlight %}\n\n***\n\n'

    os.makedirs('minimal/cluster/{}'.format(n + 1), exist_ok=True)
    with open('minimal/cluster/{}/index.md'.format(n + 1), 'w') as f:
        f.write(text)


def main(class_name):
    print('> Making clusters...')
    df = make_cluster(class_name)
    print('> Making markdown files and dependencies...')
    get_md(class_name, df)
    print('>>> Done!')


if __name__ == '__main__':
    main(sys.argv[1])
