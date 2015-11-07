(ns fourclojure.language-of-a-dfa)

;; http://www.4clojure.com/problem/164

;; A deterministic finite automaton (DFA) is an abstract machine that
;; recognizes a regular language. Usually a DFA is defined by a 5-tuple,
;; but instead we'll use a map with 5 keys:
;; 
;; :states is the set of states for the DFA.
;; :alphabet is the set of symbols included in the language recognized by the DFA.
;; :start is the start state of the DFA.
;; :accepts is the set of accept states in the DFA.
;; :transitions is the transition function for the DFA, mapping :states ⨯ :alphabet onto :states.
;; 
;; Write a function that takes as input a DFA definition (as described
;; above) and returns a sequence enumerating all strings in the language
;; recognized by the DFA.
;; 
;; Note: Although the DFA itself is finite and only recognizes
;; finite-length strings it can still recognize an infinite set of
;; finite-length strings. And because stack space is finite, make sure
;; you don't get stuck in an infinite loop that's not producing results
;; every so often!

(def __
  (fn []
    (letfn []
      )))

