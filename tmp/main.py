#!/usr/bin/env python3
import json
import os
import sys
from collections import OrderedDict

import numpy as np
import pandas as pd

from clustering import xmean


def make_cluster(class_name):
    data = np.load('vectorize_v2.npz')
    xm = np.array([data['vector'][i] for i in range(len(data['vector'])) if data['directory'][i] == class_name])
    files = np.array([data['filename'][i] for i in range(len(data['vector'])) if data['directory'][i] == class_name])
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
    # sample.sort()
    tmp = []
    for s in sample:
        with open('CodeExampleJson/{}/{}'.format(class_name, s), 'r') as f:
            data = json.load(f)
            tmp.append([len(data['lines']), s])
    tmp.sort(key=lambda x: x[0])
    sample = [t[1] for t in tmp]
    try:
        sz = max([int(df.at[(class_name, '{}.txt'.format(s[:-5])), 'cluster']) for s in sample])
    except KeyError:
        sz = 0

    for i in range(sz + 1):
        try:
            tmp = [s[:-5] for s in sample if i == int(df.at[(class_name, '{}.txt'.format(s[:-5])), 'cluster'])]
            x = tmp[0]
            y = len(tmp)
        except KeyError:
            x = sample[0][:-5]
            y = 1
        rep_cluster_arr.append({'id': x, 'num': y})

    text = '---\nlayout: default\n---\n\n# {}\n\n***\n\n'.format(class_name)
    for i, dic in enumerate(rep_cluster_arr):
        with open('CodeExampleJson/{}/{}.json'.format(class_name, dic['id']), 'r') as f:
            data = json.load(f)
            if not data['lines']:
                continue
            text += '## [Cluster {}](./cluster/{}/index.html)\n'.format(i + 1, i + 1)
            text += '{} results\n'.format(dic['num'])
            text += '> code comments is here.\n'  # add comments
            text += '{% highlight java %}\n'
            for j, line in data['lines'].items():
                text += '{0}. {1}\n'.format(j, line.split('\n')[0])
            text += '{% endhighlight %}\n\n***\n\n'

        catalog(class_name, i, df)

    with open('minimal/index.md'.format(class_name), 'w') as f:
        f.write(text)

    print('done.')


def catalog(class_name, n, df):
    try:
        sample = os.listdir('CodeExampleJson/{}'.format(class_name))
    except FileNotFoundError:
        return
    # sample.sort()
    tmp = []
    for s in sample:
        with open('CodeExampleJson/{}/{}'.format(class_name, s), 'r') as f:
            data = json.load(f)
            tmp.append([len(data['lines']), s])
    tmp.sort(key=lambda x: x[0])
    sample = [t[1] for t in tmp]
    try:
        li = [s[:-5] for s in sample if n == int(df.at[(class_name, '{}.txt'.format(s[:-5])), 'cluster'])]
    except KeyError:
        li = [sample[0][:-5]]

    files = []
    with open('CodeIndex/{}.json'.format(class_name), 'r') as f:
        data = json.load(f)
        for x in li:
            for d in data['results']:
                s = x.split('_')[0]
                if str(d['id']) == s:
                    files.append([x, d['filename']])

    text = '---\nlayout: default\n---\n\n# {} @Cluster {}\n\n***\n\n'.format(class_name, n + 1)
    for k, v in files:
        with open('CodeExampleJson/{}/{}.json'.format(class_name, k), 'r') as f:
            data = json.load(f)
            if not data['lines']:
                continue
            text += '### [{}](https://searchcode.com/codesearch/view/{}/)\n'.format(v, k.split('_')[0])
            text += '{% highlight java %}\n'
            for i, line in data['lines'].items():
                text += '{0}. {1}\n'.format(i, line.split('\n')[0])
            text += '{% endhighlight %}\n\n***\n\n'

    os.makedirs('minimal/cluster/{}'.format(n + 1), exist_ok=True)
    with open('minimal/cluster/{}/index.md'.format(n + 1), 'w') as f:
        f.write(text)


def main(class_name):
    df = make_cluster(class_name)
    get_md(class_name, df)


if __name__ == '__main__':
    main(sys.argv[1])
