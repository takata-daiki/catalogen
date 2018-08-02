#!/usr/bin/env python3
import requests, bs4
res = requests.get('https://poi.apache.org/apidocs/allclasses-noframe.html')
res.raise_for_status()
soup = bs4.BeautifulSoup(res.text, "html.parser")
elems = soup.select('.indexContainer a')
for elem in elems:
    e = elem.get('title').split(' ')
    if e[0] == 'class':
        print('{}.{}'.format(e[2], elem.getText()))
