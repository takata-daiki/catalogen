#!/usr/bin/env python3
import json
import requests
import get_json
import json_to_tokens
import tokens_to_codes

# get_json.input()
# json_to_tokens.input()

with open('id_list.json') as f:
    data = json.load(f)
    for val in data['XSSFWorkbook']:
        n = val['id']
        print(n)
        ans = []
        try:
            input_data = tokens_to_codes.input('CodeExample/' + n + '.token')
            res = tokens_to_codes.extract(input_data)
            ans = tokens_to_codes.output(res, 'CodeExample/' + n + '.java')

        except IndexError as e:
            pass
        except KeyError as e:
            pass

        val['lines'] = {}
        for v in ans:
            val['lines'][v[0]] = v[1]
        with open('CodeExample/' + n + '.json', 'w') as ff:
            get_json.output(val, ff)
