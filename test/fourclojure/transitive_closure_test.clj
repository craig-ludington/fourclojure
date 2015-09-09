(ns fourclojure.transitive-closure-test
  (:require [clojure.test :refer :all]
            [fourclojure.transitive-closure :refer :all]))

(deftest test-__
  (is (let [divides #{[8 4] [9 3] [4 2] [27 9]}]
        (= (__ divides) #{[4 2] [8 4] [8 2] [9 3] [27 9] [27 3]})))

  (is (let [more-legs
            #{["cat" "man"] ["man" "snake"] ["spider" "cat"]}]
        (= (__ more-legs)
           #{["cat" "man"] ["cat" "snake"] ["man" "snake"]
             ["spider" "cat"] ["spider" "man"] ["spider" "snake"]})))

  (is (let [progeny
            #{["father" "son"] ["uncle" "cousin"] ["son" "grandson"]}]
        (= (__ progeny)
           #{["father" "son"] ["father" "grandson"]
             ["uncle" "cousin"] ["son" "grandson"]})))
  )
