(ns fourclojure.levenshtein-distance)

;; http://www.4clojure.com/problem/101

;; Given two sequences x and y, calculate the Levenshtein distance of x and y,
;; i. e. the minimum number of edits needed to transform x into y. The allowed edits are:
;; 
;; - insert a single item
;; - delete a single item
;; - replace a single item with another item
;; 
;; WARNING: Some of the test cases may timeout if you write an inefficient solution!

;;    S = kitten
;;    T = sitting
;;
;;         k i t t e n
;;     +--------------
;;     | 0 1 2 3 4 5 6
;;    s| 1 1 2 3 4 5 6
;;    i| 2 2 1 2 3 4 5
;;    t| 3 3 2 1 2 3 4
;;    t| 4 4 3 2 1 2 3
;;    i| 5 5 4 3 2 2 3
;;    n| 6 6 5 4 3 3 2
;;    g| 7 7 6 5 4 4 3 

;; Wagner-Fischer algorithm
;; https://en.wikipedia.org/wiki/Wagner%E2%80%93Fischer_algorithm
(def __
  (fn
    [string-s string-t]
    (letfn [(lookup [v2d s t]
              ;; (println "lookup" v2d s t)
              ((v2d t) s))
            (value [v2d s t]
              (cond (zero? s) t
                    (zero? t) s
                    :else     (if (= (nth string-s (dec s)) (nth string-t (dec t)))
                                (lookup v2d (dec s) (dec t))
                                (let [del (lookup v2d (dec s) t)
                                      ins (lookup v2d s (dec t))
                                      sub (lookup v2d (dec s) (dec t))
                                      ]
                                  (inc (min del ins sub))))))]

      (let [count-s (count string-s)
            count-t (count string-t)]

        (loop [t 0 outer []]
          (if (> t count-t)
            (last (last outer))
            (recur (inc t)
                   (conj outer
                         (loop [s 0 inner []]
                           (if (> s count-s)
                             inner
                             (recur (inc s)
                                    (conj inner (value (conj outer inner) s t))))
                           )))))))))
