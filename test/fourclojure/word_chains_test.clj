(ns fourclojure.word-chains-test
  (:require [clojure.test :refer :all]
            [fourclojure.word-chains :refer :all]))

(deftest test-__
  (is (= true  (__ #{"hat" "coat" "dog" "cat" "oat" "cot" "hot" "hog"})))
  (is (= true  (__ #{"spout" "do" "pot" "pout" "spot" "dot"})))
  (is (= true  (__ #{"share" "hares" "shares" "hare" "are"})))

  (is (= false (__ #{"cot" "hot" "bat" "fat"})))
  (is (= false (__ #{"to" "top" "stop" "tops" "toss"})))
  (is (= false (__ #{"share" "hares" "hare" "are"}))))

