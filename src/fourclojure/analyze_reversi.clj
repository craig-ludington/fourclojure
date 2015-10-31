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
    (letfn [(n  [[x y]] [(dec x) y])
            (s  [[x y]] [(inc x) y])
            (e  [[x y]] [x (inc y)])
            (w  [[x y]] [x (dec y)])]
      (let [ne (comp n e)
            nw (comp n w)
            se (comp s e)
            sw (comp s w)]
        (letfn [;; A lazy seq of coordinates starting at [x y] in direction (n, s, e, w, ne, nw, se, sw).
                (path
                  [xy direction]
                  (cons xy (lazy-seq (path (direction xy) direction))))
                ;; Starting at [x y], explore direction until the goal color is found or the search is exhausted.
                ;; If successful, return a vector of the starting coordinates and a set of the intervening coordinates.
                (explore
                  [board xy direction color]
                  (loop [p (drop 1 (path xy direction))
                         r []]
                    (when-let [v (and (seq p) (get-in board (first p)))]
                      (condp = v
                        color [xy (set r)]
                        'e    nil
                        (recur (next p) (conj r (first p)))))))
                (explore-8
                  [board xy color]
                  (reduce into []
                          (filter #(not (empty? (second %)))
                                  (map #(explore board xy % color) [n s e w ne nw se sw]))))
                (empty-squares
                  [b]
                  (let [r (range (count b))] (for [x r, y r :when (= 'e (get-in b [x y]))] [x y])))
                (solve
                  [board color]
                  (let [x (partition 2 (reduce into [] (remove empty? (map #(explore-8 board % color) (empty-squares board)))))]
                    (zipmap (map first x) (map second x))))]
          (solve board color))))))
