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

    text = '# XSSFWorkbook\n'
    for n in arr:
        with open('CodeExample/' + n + '.json') as f:
            data = json.load(f)
            if not data['lines']: continue
            text += '### [{0}]({1})\n'.format(data['filename'], data['url'])
            text += '{% highlight java %}\n'
            for i, line in data['lines'].items():
                text += '{0}. {1}\n'.format(i, line.split('\n')[0])
            text += '{% endhighlight %}\n'
            text += '---\n'
    # body = md.convert(text)
    # html = '<html lang="en">\n<head>\n<meta charset="utf-8">\n<link rel="stylesheet" type="text/css" href="../styles.css">\n</head>\n<body>\n'+body+'\n</body>\n</html>'
    # print(html)
    print(text)


if __name__ == '__main__':
    input()
    # print(md.convert(text))

