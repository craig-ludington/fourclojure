(ns fourclojure.levenshtein-distance-test
  (:require [clojure.test :refer :all]
            [fourclojure.levenshtein-distance :refer :all]))

(deftest test-__
  (is (= (__ "kitten" "sitting") 3))
  (is (= (__ "closure" "clojure") (__ "clojure" "closure") 1))
  (is (= (__ "xyx" "xyyyx") 2))
  (is (= (__ "" "123456") 6))
  (is (= (__ "Clojure" "Clojure") (__ "" "") (__ [] []) 0))
  (is (= (__ [1 2 3 4] [0 2 3 4 5]) 2))
  (is (= (__ '(:a :b :c :d) '(:a :d)) 2))
  (is (= (__ "ttttattttctg" "tcaaccctaccat") 10))
  (is (= (__ "gaattctaatctc" "caaacaaaaaattt") 9))
  )
