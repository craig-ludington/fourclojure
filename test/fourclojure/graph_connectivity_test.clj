(ns fourclojure.graph-connectivity-test
  (:require [clojure.test :refer :all]
            [fourclojure.graph-connectivity :refer :all]))

(deftest test-__
  (is (= true (__ #{[:a :a]})))
  (is (= true (__ #{[:a :b]}))) ;; fail
  (is (= false (__ #{[1 2] [2 3] [3 1]
                     [4 5] [5 6] [6 4]})))
  (is (= true (__ #{[1 2] [2 3] [3 1]
                    [4 5] [5 6] [6 4] [3 4]}))) ;; fail
  (is (= false (__ #{[:a :b] [:b :c] [:c :d]
                     [:x :y] [:d :a] [:b :e]})))
  (is (= true (__ #{[:a :b] [:b :c] [:c :d]
                    [:x :y] [:d :a] [:b :e] [:x :a]})))
  )
