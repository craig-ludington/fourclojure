(ns fourclojure.number-maze-test
  (:require [clojure.test :refer :all]
            [fourclojure.number-maze :refer :all]))

(deftest test-__
  (is (= 1 (__ 1 1)))  ;; 1
  (is (= 3 (__ 3 12))) ;; 3 6 12
  (is (= 3 (__ 12 3))) ;; 12 6 3
  (is (= 3 (__ 5 9)))  ;; 5 7 9
  (is (= 9 (__ 9 2)))  ;; 9 18 20 10 12 6 8 4 2
  (is (= 5 (__ 9 12))) ;; 9 11 22 24 12
  )
