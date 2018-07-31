#!/usr/bin/env python3
import re

# yuki67
class Automata(object):
    def __init__(self, states, alphabets, transitions, init_state, final_states):
        self.states = frozenset(states)
        self.alphabets = frozenset(alphabets)
        self.transitions = transitions
        self.init_state = init_state
        self.final_states = frozenset(final_states)

    def __eq__(self, other):
        return self.states == other.states and \
            self.alphabets == other.alphabets and \
            self.transitions == other.transitions and \
            self.init_state == other.init_state and \
            self.final_states == other.final_states

    def __repr__(self):
        str_trans = ""
        for key, val in self.transitions.items():
            str_trans += str(key).replace("\n", "") + " : " + str(val).replace("\n", "") + "\n                  "
        str_trans = str_trans[:-19]

        ans = """Automata
    states      : %s
    alphabets   : %s
    transitions : %s
    init_state  : %s
    final_states: %s""" % (str(self.states).replace("\n", ""),
                           str(self.alphabets).replace("\n", ""),
                           str_trans,
                           str(self.init_state).replace("\n", ""),
                           str(self.final_states).replace("\n", ""))
        ans = ans.replace("frozenset()", "frozenset({})")
        ans = ans.replace("frozenset({", "{")
        ans = ans.replace("})", "}")
        return ans


class NFA(Automata):
    def __init__(self, states, alphabets, transitions, init_state, final_states):
        self.states = states
        self.alphabets = alphabets
        self.transitions = transitions
        self.init_state = init_state
        self.final_states = final_states

    def run(self):
        states = {self.init_state}

        f = open('code_results_to_tokens')
        line = f.readline()
        while line:
            arr = line.split(',')
            num = int(arr[0][2:])
            match1 = re.search(r"'(.+)'", arr[1])
            match2 = re.search(r'<(.+)>', arr[2])
            try:
                # print(arr[0][2:], match1.group(1), match2.group(1))
                if match2 not in self.alphabets:
                    print('ERROR : invalid character \'%s\'' % match2)
                    return false
                else:
                    states = self.next_states(states, num, match1, match2)
                if len(states.intersection(self.final_states)) != 0:
            except AttributeError as e:
                pass

            line = f.readline()
        return
        f.close()


    def next_states(self, states, n, ID, token):
        next_states = set()
        for state in states:
            next_states = next_states.union(self.transitions[state].get(token, frozenset()))

        if state == c and 
        if state == b and token == Id:
            dollarID[n - 1] = ID
            next_states = next_states.union({c})
        if state == a and ID == classID:
            next_states = next_states.union({b})

        return frozenset(next_states)


Id = "Identifier"
literalNames = [ "<INVALID>",
    "'abstract'", "'assert'", "'boolean'", "'break'", "'byte'",·
    "'case'", "'catch'", "'char'", "'class'", "'const'", "'continue'",·
    "'default'", "'do'", "'double'", "'else'", "'enum'", "'extends'",·
    "'final'", "'finally'", "'float'", "'for'", "'if'", "'goto'",·
    "'implements'", "'import'", "'instanceof'", "'int'", "'interface'",·
    "'long'", "'native'", "'new'", "'package'", "'private'", "'protected'",·
    "'public'", "'return'", "'short'", "'static'", "'strictfp'",·
    "'super'", "'switch'", "'synchronized'", "'this'", "'throw'",·
    "'throws'", "'transient'", "'try'", "'void'", "'volatile'",·
    "'while'", "'null'", "'('", "')'", "'{'", "'}'", "'['", "']'",·
    "';'", "','", "'.'", "'='", "'>'", "'<'", "'!'", "'~'", "'?'",·
    "':'", "'=='", "'<='", "'>='", "'!='", "'&&'", "'||'", "'++'",·
    "'--'", "'+'", "'-'", "'*'", "'/'", "'&'", "'|'", "'^'", "'%'",·
    "'->'", "'::'", "'+='", "'-='", "'*='", "'/='", "'&='", "'|='",·
    "'^='", "'%='", "'<<='", "'>>='", "'>>>='", "'@'", "'...'",·
    "'??'", "'_'", Id ]

a, b, c, d = range(4)
states = {a, b, c, d}
alphabets = set(literalNames)
transitions = {
    a: {x: {a} for x in literalNames},
    c: {x: {c} for x in literalNames},
    d: {x: {d} for x in literalNames},
}
init_state = a
final_states = {d}

classID = "AbortableHSSFListener"
dollarID = {}

# has_010 = NFA(states, alphabets, transitions, init_state, final_states)
