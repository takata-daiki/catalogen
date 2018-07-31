#!/usr/bin/env python3
import json
import markdown
from markdown.extensions.codehilite import CodeHiliteExtension

md = markdown.Markdown(extensions=[CodeHiliteExtension(linenums=True)])

def input():
    arr = []
    with open('id_list.json', 'r') as f:
        data = json.load(f)
        for dic in data['XSSFWorkbook']:
            arr.append(dic['id'])

    text = '## XSSFWorkbook\n'
    for n in arr:
        with open('CodeExample/' + n + '.json') as f:
            data = json.load(f)
            if not data['lines']: continue
            text += '### [{0}]({1})\n'.format(data['filename'], data['url'])
            text += '\n'
            text += '    :::java'
            for line in data['lines'].values():
                text += '{0}\n'.format(line.split('\n')[0])
            text += '\n'
            text += '---'
    body = md.convert(text)
    html = '<html lang="en">\n<head>\n<meta charset="utf-8">\n<link rel="stylesheet" type="text/css" href="../styles.css">\n</head>\n<body>\n'+body+'\n</body>\n</html>'
    print(html)


if __name__ == '__main__':
    input()
    # print(md.convert(text))

