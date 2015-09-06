(ns fourclojure.triangle-minimum-path-test
  (:require [clojure.test :refer :all]
            [fourclojure.triangle-minimum-path :refer :all]))

(deftest test-__
  (is (= 7 (__ '([1]
                 [2 4]
                 [5 1 4]
                 [2 3 4 5]))) ; 1->2->1->3)
      )
  (is (= 20 (__ '([3]
                  [2 4]
                  [1 9 3]
                  [9 9 2 4]
                  [4 6 6 7 8]
                  [5 7 3 5 1 4]))) ; 3->4->3->2->7->1)
      ))
