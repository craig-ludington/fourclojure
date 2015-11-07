(ns fourclojure.latin-square-slicing
  (:use [clojure.pprint :refer :all]
         [clojure.repl :refer :all]))

;; http://www.4clojure.com/problem/152
;; 
;; A Latin square of order n is an n x n array that contains n different
;; elements, each occurring exactly once in each row, and exactly once in
;; each column. For example, among the following arrays only the first
;; one forms a Latin square:
;; 
;; A B C    A B C    A B C
;; B C A    B C A    B D A
;; C A B    C A C    C A B
;;  
;; Let V be a vector of such vectors that they may differ in length. We
;; will say that an arrangement of vectors of V in consecutive rows is an
;; alignment (of vectors) of V if the following conditions are satisfied:
;; 
;; All vectors of V are used.  Each row contains just one vector.  The
;; order of V is preserved.  All vectors of maximal length are
;; horizontally aligned each other.  If a vector is not of maximal length
;; then all its elements are aligned with elements of some subvector of a
;; vector of maximal length.
;; 
;; Let L denote a Latin square of order 2 or greater. We will say that L
;; is included in V or that V includes L iff there exists an alignment of
;; V such that contains a subsquare that is equal to L.
;; 
;; For example, if V equals [[1 2 3][2 3 1 2 1][3 1 2]] then there are
;; nine alignments of V (brackets omitted):
;; 
;;         1              2              3
;;  
;;       1 2 3          1 2 3          1 2 3
;;   A   2 3 1 2 1    2 3 1 2 1    2 3 1 2 1
;;       3 1 2        3 1 2        3 1 2
;;  
;;       1 2 3          1 2 3          1 2 3
;;   B   2 3 1 2 1    2 3 1 2 1    2 3 1 2 1
;;         3 1 2        3 1 2        3 1 2
;;  
;;       1 2 3          1 2 3          1 2 3
;;   C   2 3 1 2 1    2 3 1 2 1    2 3 1 2 1
;;           3 1 2        3 1 2        3 1 2
;;  
;; Alignment A1 contains Latin square [[1 2 3][2 3 1][3 1 2]], alignments
;; A2, A3, B1, B2, B3 contain no Latin squares, and alignments C1, C2, C3
;; contain [[2 1][1 2]]. Thus in this case V includes one Latin square of
;; order 3 and one of order 2 which is included three times.
;; 
;; Our aim is to implement a function which accepts a vector of vectors V
;; as an argument, and returns a map which keys and values are
;; integers. Each key should be the order of a Latin square included in
;; V, and its value a count of different Latin squares of that order
;; included in V. If V does not include any Latin squares an empty map
;; should be returned. In the previous example the correct output of such
;; a function is {3 1, 2 1} and not {3 1, 2 3}.

(def __
  (fn [matrix]
    (letfn [(square?
              [matrix]
              (let [c (count matrix)]
                (and (<= 2 c)
                     (every? #(= c %) (map count matrix)))))

            (filled-in?
              [matrix]
              (and (not (empty? matrix))
                   (every? true? (map #(not-any? nil? %) matrix))))

            (rows-distinct?
              [matrix]
              (every? true? (map #(apply distinct? %) matrix)))

            (rotate
              [matrix]
              (vec (apply map vector matrix)))

            (latin-square?
              [matrix]
              (and (square? matrix)
                   (filled-in? matrix)
                   (rows-distinct? matrix)
                   (apply = (into (map set matrix) (map set (rotate matrix))))))

            (max-width [matrix] (apply max (map count matrix)))

            (pad-left [row n] (into (vec (repeat n nil)) row))
            (pad-right [row n] (into row (vec (repeat n nil))))
            (pad-to-width [row width offset] (pad-left (pad-right row (- width (count row) offset)) offset))

            ;; Progressively shift row into width, padded with nil
            ;; (padded-rows 4 [1 2]) => [[nil 1 2 nil] [1 2 nil nil] [nil nil 1 2]]
            (padded-rows [width row]
              (vec (set (for [n (range (inc (- width (count row))))]
                          (pad-to-width row width n)))))

            ;; http://stackoverflow.com/a/18262146/4113987
            (cartesian-product
              ([] '(()))
              ([xs & more]
               (mapcat #(map (partial cons %)
                             (apply cartesian-product more))
                       xs)))

            ;; All combinations of row alignments, returned as square matrixes.  Rows may be padded with nil so the matrix won't be ragged.
            (alignments
              [matrix]
              (vec (map vec (apply cartesian-product (map (partial padded-rows (max-width matrix)) matrix)))))

            (sub-matrix
              [matrix [x y :as origin] size]
              (vec (map #(subvec % y (+ y size)) (subvec matrix x (+ x size)))))
            
            ;; [[x y] size] ... ] for every origin in matrix that produces a square sub-matrix of any size
            (origins
              ([height width size] (for [x (range 0 (- height (dec size)))
                                         y (range 0 (- width  (dec size)))]
                                     [[x y] size]))
              ([height width]      (reduce into [] (for [size (range 2 (inc (min height width)))]
                                                     (origins height width size))))
              ([matrix]            (origins (count matrix) (apply max (map count matrix)))))

            (squares
              [matrix]
              (vec (map (fn [[origin size]] (sub-matrix matrix origin size)) (origins matrix))))

            (all-squares
              [matrix]
              (reduce into [] (map squares (alignments matrix))))
            
            ;; Unique latin squares found in matrix
            (latin-squares
              [matrix]
              (into #{} (vec (filter latin-square? (all-squares matrix)))))

            ;; A map of latin square order to number found
            (solve
              [matrix]
              (into {} (map (fn [[k v]] {k (count v)}) (group-by #(count (first %)) (latin-squares matrix)))))]
      (solve matrix))))

