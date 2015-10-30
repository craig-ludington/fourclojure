(ns fourclojure.analyze-reversi
  (:use [clojure.pprint :refer :all]
         [clojure.repl :refer :all]))

;; http://www.4clojure.com/problem/124

;; Reversi is normally played on an 8 by 8 board. In this problem, a 4 by
;; 4 board is represented as a two-dimensional vector with black, white,
;; and empty pieces represented by 'b, 'w, and 'e, respectively. Create a
;; function that accepts a game board and color as arguments, and returns
;; a map of legal moves for that color. Each key should be the
;; coordinates of a legal move, and its value a set of the coordinates of
;; the pieces flipped by that move.

;; Board coordinates should be as in calls to get-in. For example, [0 1]
;; is the topmost row, second column from the left.

(def __
  (fn [board color]
   (let []
     (letfn []
       )
      )))

(def x '[[e e e e]
         [e w b e]
         [e b w e]
         [e e e e]])

(defn empty-squares [b] (let [r (range (count b))] (for [x r, y r :when (= 'e (get-in b [x y]))] [x y])))

(defn adjacent-squares
  [[x y]]
  (remove #(= % [x y]) (mapcat (fn [x] (map (fn [y] [x y]) [(dec y) y (inc y)])) [(dec x) x (inc x)])))

(defn adjacent-squares-where
  [b [x y] where]
  (filter identity (map (fn [[x' y']] (let [v (get-in b [x' y'])] (and (where v) [x' y'])))
                        (adjacent-squares [x y]))))
