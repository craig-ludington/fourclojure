(ns fourclojure.graph-tour-test
  (:require [clojure.test :refer :all]
            [fourclojure.graph-tour :refer :all]))

(deftest test-__
  (is (= true (__ [[:a :b]])))
  (is (= false (__ [[:a :a] [:b :b]])))
  (is (= false (__ [[:a :b] [:a :b] [:a :c] [:c :a] [:a :d] [:b :d] [:c :d]]))) ;; fail
  (is (= true (__ [[1 2] [2 3] [3 4] [4 1]])))
  (is (= true (__ [[:a :b] [:a :c] [:c :b] [:a :e]
                   [:b :e] [:a :d] [:b :d] [:c :e]
                   [:d :e] [:c :f] [:d :f]])))
  (is (= false (__ [[1 2] [2 3] [2 4] [2 5]])))
  )
