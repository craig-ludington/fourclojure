(ns fourclojure.transitive-closure
  (:use [clojure.pprint :refer :all]
         [clojure.repl :refer :all]))

;; http://www.4clojure.com/problem/84

;; Write a function which generates the transitive closure of a binary
;; relation. The relation will be represented as a set of 2 item vectors.

(def __ 
  (fn [tuples]
    (letfn [(thread [tuples start]
                    (loop [m (into {} tuples)
                           acc []
                           point start]
                      (if point
                        (recur m (conj acc point) (m point))
                        acc)))

            (threads [tuples] (map #(thread tuples (first %)) tuples))
            (closures [v] (map (partial vector (first v)) (rest v)))
            (transitive-closures [tuples] (reduce into #{} (map closures (threads tuples))))]
      (transitive-closures tuples))))
