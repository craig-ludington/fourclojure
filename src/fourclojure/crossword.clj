(ns fourclojure.crossword
  (:use [clojure.pprint :refer :all]
         [clojure.repl :refer :all]))

;; http://www.4clojure.com/problem/111

;; Write a function that takes a string and a partially-filled crossword
;; puzzle board, and determines if the input string can be legally placed
;; onto the board.

;; The crossword puzzle board consists of a collection of
;; partially-filled rows. Empty spaces are denoted with an
;; underscore (_), unusable spaces are denoted with a hash symbol (#),
;; and pre-filled spaces have a character in place. The whitespace
;; characters are for legibility and should be ignored.

;; For a word to be legally placed on the board: 
;; - It may use empty spaces (underscores) 
;; - It may use but must not conflict with any pre-filled characters. 
;; - It must not use any unusable spaces (hashes). 
;; - There must be no empty spaces (underscores) or extra characters before or after the word
;;   (the word may be bound by unusable spaces though). 
;; - Characters are not case-sensitive. 
;; - Words may be placed vertically (proceeding top-down only), or horizontally (proceeding left-right only).


(def __ 
  (fn [word board]
    (letfn [(rows
              [b]
              (map #(remove (partial = \space) %) b))
            (columns
              [b]
              (remove empty? (for [col (range (count (first b)))]
                               (remove (partial = \space) (map #(nth % col) b)))))
            (available
              [b]
              (reduce into []
                      (map #(clojure.string/split % #"#")
                           (map #(apply str %)
                                (into (rows b) (columns b))))))
            (fits?
              [place s]
              (let [cp (count place)
                    cs (count s)]
                (and (= cp cs) (every? true? (for [x (range cp)]
                                               (let [p (nth place x)
                                                     s (nth s x)]
                                                 (or (= p \_) (= p s))))))))
            (can-play?
              [s board]
              (or (some true? (map #(fits? % s) (available board))) false))]
      (can-play? word board))))





