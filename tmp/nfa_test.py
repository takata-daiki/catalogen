#!/usr/bin/env python3

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

    def run(self, s):
        states = {self.init_state}
        for c in s:
            if c not in self.alphabets:
                print('ERROR : invalid character \'%s\'' % c)
                return false
            else:
                states = self.next_states(states, c)
        return len(states.intersection(self.final_states)) != 0

    def next_states(self, states, c):
        next_states = set()
        for state in states:
            next_states = next_states.union(self.transitions[state].get(c, frozenset()))
        return frozenset(next_states)


a, b, c, d = range(4)
states = {a, b, c, d}
alphabets = {"0", "1"}
transitions = {
    a: {"0": {a, b}, "1": {b}},
    b: {"1": {c}},
    c: {"0": {d}},
    d: {"0": {d}, "1": {d}},
}
init_state = a
final_states = {d}
has_010 = NFA(states, alphabets, transitions, init_state, final_states)
