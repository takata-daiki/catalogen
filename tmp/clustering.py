import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
from scipy.cluster.hierarchy import linkage, dendrogram, fcluster
from pyclustering.cluster.xmeans import kmeans_plusplus_initializer, xmeans
from itertools import chain

data = np.load('vectorize_v2.npz')
df = pd.read_csv('cluster.csv')
df_mi = df.set_index(['directory', 'filename'])


def show():
    for i in range(len(data['vector'])):
        print(data['vector'][i], data['directory'][i], data['filename'][i])


def maxdiff(Z):  # divided max gap of two adjacent distances
    mx = 0
    for i in range(len(Z))[:-1]:
        if Z[i + 1][2] - Z[i][2] > Z[mx + 1][2] - Z[mx][2]:
            mx = i
    threshold = (Z[mx + 1][2] + Z[mx][2]) * 0.5
    return fcluster(Z, threshold, criterion='distance')


def inconsistent(Z):  # divided by using inconsistency method
    return fcluster(Z, 8, depth=10)


def elbow(Z):  # divided by using elbow method
    last = Z[:, 2]
    k = np.diff(last, 2)[::-1].argmax() + 2
    return fcluster(Z, k, criterion='maxclust'), k


def xmean(X):
    initial_centers = kmeans_plusplus_initializer(X, 2).initialize()
    xmeans_instance = xmeans(X, initial_centers, ccore=False)
    xmeans_instance.process()
    tmp = xmeans_instance.get_clusters()
    flat = list(chain.from_iterable(tmp))
    clusters = [None] * len(flat)
    for i in range(len(tmp)):
        for x in tmp[i]:
            clusters[x] = i
    return clusters


if __name__ == '__main__':
    n = 0
    df2 = pd.DataFrame({'filename': [],
                        'cleanup content': [],
                        'directory': [],
                        'cluster': []})

    for dirname in list(dict.fromkeys(data['directory'])):
        X = np.array([data['vector'][i] for i in range(len(data['vector'])) if data['directory'][i] == dirname])
        # Z = linkage(X, method='average', metric='cosine')

        # clusters = maxdiff(Z)
        # clusters = inconsistent(Z)
        # clusters, k = elbow(Z)
        # print('clusters', k)

        clusters = xmean(X.tolist())

        # print(Z)
        print(clusters)

        for i in range(len(clusters)):
            arr = [data['filename'][n],
                   df_mi.at[(dirname, data['filename'][n]), 'cleanup content'],
                   dirname,
                   clusters[i]]
            df2.loc[n] = arr
            n += 1

            print(arr)

        # plot dendrogram
        # plt.figure(num=None, figsize=(25, 10))
        # dendrogram(Z, labels=data['filename'], leaf_rotation=90, leaf_font_size=8.)
        # plt.show()

        # # plot elbow curve
        # idxs = np.arange(1, len(last) + 1)
        # plt.plot(idxs, last[::-1])
        # plt.show()

    df2.to_csv('cluster_v5.csv', index=None)
