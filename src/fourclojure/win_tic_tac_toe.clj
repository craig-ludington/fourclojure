(ns fourclojure.win-tic-tac-toe
  (:use [clojure.pprint :refer :all]
         [clojure.repl :refer :all]))

;; http://www.4clojure.com/problem/119

;; As in Problem 73, a tic-tac-toe board is represented by a two
;; dimensional vector. X is represented by :x, O is represented by :o,
;; and empty is represented by :e. Create a function that accepts a game
;; piece and board as arguments, and returns a set (possibly empty) of
;; all valid board placements of the game piece which would result in an
;; immediate win.

;; Board coordinates should be as in calls to get-in. For example, [0 1]
;; is the topmost row, center position.

;; unoccupied cells on the board
(defn available [board] (for [x (range 3) y (range 3) :when (= :e (get-in board [x y]))] [x y]))

(defn make-diag [board diagonal xy] (if ((set diagonal) xy) (map #(get-in board %) diagonal) []))
(defn row [board [x y]] (nth board x))
(defn column [board [x y]] (map #(nth % y) board))
(defn up-diag [board xy] (make-diagonal board [[0 0] [1 1] [2 2]] xy))
(defn down-diag [board xy] (make-diagonal board [[2 0] [1 1] [0 2]] xy))

;; every row, column, or diagonal containing this xy coordinate
(defn containers [board xy] (remove empty? [(row board xy) (column board xy) (up-diag board xy) (down-diag board xy)]))

;; test that a row, column, or diagonal wins for the given playing piece
(defn rcd-wins? [rcd piece] (every? #(= piece %) rcd))

(defn play-wins? [board piece xy] (some #(rcd-wins? % piece) (containers (assoc-in board xy piece) xy)))

(defn winning-moves [piece board] (set (filter #(play-wins? board piece %) (available board))))

(def __ winning-moves)
