(ns fourclojure.tic-tac-toe
  (:use [clojure.pprint :refer :all]
         [clojure.repl :refer :all]))

;; http://www.4clojure.com/problem/73

;; A tic-tac-toe board is represented by a two dimensional vector. X is
;; represented by :x, O is represented by :o, and empty is represented
;; by :e. A player wins by placing three Xs or three Os in a horizontal,
;; vertical, or diagonal row. Write a function which analyzes a
;; tic-tac-toe board and returns :x if X has won, :o if O has won, and
;; nil if neither player has won.

(def __
  (fn [board]
    (letfn [(xy [b x y] ((b x) y))
            (candidates
              [b]
              (loop [coords [[[0 0] [1 0] [2 0]] ;; first column
                             [[0 1] [1 1] [2 1]] ;; second column
                             [[0 2] [1 2] [2 2]] ;; third column
                             [[0 0] [1 1] [2 2]] ;; first diagonal
                             [[2 0] [1 1] [0 2]] ;; second diagonal
                             ]
                     b b]
                (if (seq coords)
                  (recur (rest coords)
                         (let [make (fn [[c0 c1 c2]]
                                      [(apply (partial xy b) c0)
                                       (apply (partial xy b) c1)
                                       (apply (partial xy b) c2)])]
                           (conj b (make (first coords)))))
                  b)))
            (who-wins?
              [boxes]
              (let [p (first boxes)]
                (and (every? #(= p %) boxes)
                     p)))
            (winner
              [board]
              (first (remove #(= :e %) (filter keyword? (map who-wins? (candidates board))))))]
      (winner board))))
