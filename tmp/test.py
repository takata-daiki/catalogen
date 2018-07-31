#!/usr/bin/env python3
import subprocess
import json
import requests

api = 'https://searchcode.com/api/result/{codeid}/'

def request(codeid):
    url = api.format(codeid=codeid)
    r = requests.get(url)

    try:
        data = json.loads(r.text)
    except json.decoder.JSONDecodeError as e:
        pass

    return data


def input():
    try:
        with open('class_name.json', 'r') as fin:
            data = json.load(fin)
            print(data[2350]['query'])  # XSSFWorkbook
            # print(json.dumps(data[2350]['results']['id'], sort_keys=True, indent=4))
            print(data[2350]['results'][0]['id'])
            # fout = open('code_results.java', 'w')
            # res = request(data[2350]['results'][0]['id'])['code']
            res = request('91974007')['code']
            # for line in res:
            #     fout.write(line)

    except json.JSONDecodeError as e:
        print('JSONDecodeError: ', e)

    fin.close()
    # fout.close()


def output(data, f):
    json.dump(data, f, indent=2)


if __name__ == '__main__':
    input()
    subprocess.check_call(['cp', './code_results.java', './antlr'])
    p = subprocess.Popen(['grun', 'Java8', 'tokens', './code_results.java', '-tokens'], cwd='./antlr')
    out, err = p.communicate()
    print(out)
