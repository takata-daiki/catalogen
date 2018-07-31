import sys
from antlr4 import *
from Java8Lexer import Java8Lexer
from Java8Listener import Java8Listener
from Java8Parser import Java8Parser

def main(argv):
    istream = FileStream(argv[1])
    lexer = Java8Lexer(istream)
    stream = CommonTokenStream(lexer)
    parser = Java8Parser(stream)
    tree = parser.compilationUnit()
    print(tree.toStringTree(recog=parser))

if __name__ == '__main__':
    main(sys.argv)

