#!/usr/bin/env python3

import shutil

try:
    shutil.rmtree('minimal/cluster')
except FileNotFoundError:
    pass

text = '''---\nlayout: default\n---\n
> Catalogen generates a catalog of code examples collected from OSS projects.

### Techniques

1. Code Example Collection
2. Code Example Categorization
3. Code Comment generation

### Data Sources

* Source codes are hosted on [searchcode.com](https://search.com)
* Code Comments are learned by a corpus of Java methods and their [Javadoc](https://poi.apache.org/apidocs/overview-summary.html) comments

### Members
<dl>
    <dt>Nara Institute of Science and Technology (Japan):</dt>
    <dd>Daiki Takata, Maipradit Rungroj, Hideaki Hata, Takashi Ishio and Kenichi Matsumoto</dd>
    <dt>University of Wollongong (Australia):</dt>
    <dd>Abdulaziz Alhefdhi, Hoa Khanh Dam</dd> 
</dl>
'''

with open('minimal/index.md', 'w') as f:
    f.write(text)
