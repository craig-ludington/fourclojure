(ns fourclojure.win-tic-tac-toe-test
  (:require [clojure.test :refer :all]
            [fourclojure.win-tic-tac-toe :refer :all]))

(deftest test-__
  (is (= (__ :x [[:o :e :e] 
                 [:o :x :o] 
                 [:x :x :e]])
         #{[2 2] [0 1] [0 2]}))
  (is (= (__ :x [[:x :o :o] 
                 [:x :x :e] 
                 [:e :o :e]])
         #{[2 2] [1 2] [2 0]}))
  (is (= (__ :x [[:x :e :x] 
                 [:o :x :o] 
                 [:e :o :e]])
         #{[2 2] [0 1] [2 0]}))
  (is (= (__ :x [[:x :x :o] 
                 [:e :e :e] 
                 [:e :e :e]])
         #{}))
  (is (= (__ :o [[:x :x :o] 
                 [:o :e :o] 
                 [:x :e :e]])
         #{[2 2] [1 1]})))
