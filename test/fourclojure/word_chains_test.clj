(ns fourclojure.word-chains-test
  (:require [clojure.test :refer :all]
            [fourclojure.word-chains :refer :all]))

(deftest test-has-chain?
  (is (= true  (has-chain? #{"hat" "coat" "dog" "cat" "oat" "cot" "hot" "hog"})))
  (is (= true  (has-chain? #{"spout" "do" "pot" "pout" "spot" "dot"})))
  (is (= true  (has-chain? #{"share" "hares" "shares" "hare" "are"})))

  (is (= false (has-chain? #{"cot" "hot" "bat" "fat"})))
  (is (= false (has-chain? #{"to" "top" "stop" "tops" "toss"})))
  (is (= false (has-chain? #{"share" "hares" "hare" "are"}))))

