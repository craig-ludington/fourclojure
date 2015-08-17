(ns fourclojure.word-chains-test
  (:require [clojure.test :refer :all]
            [fourclojure.word-chains :refer :all]))

(deftest test-distance
  (testing "0 for equal strings"
    (is (= 0 (distance "" "")))
    (is (= 0 (distance "a" "a")))
    (is (= 0 (distance "abc" "abc"))))

  (testing "insert one character"
    (testing "at end"
      (is (= 1 (distance "" "a")))
      (is (= 1 (distance "a" "ab"))))
    (testing "in the middle"
      (is (= 1 (distance "ac" "abc"))))
    (testing "at beginning"
      (is (= 1 (distance "cat" "at")))))

  (testing "delete one character"
    (testing "at end"
      (is (= 1 (distance "abc" "ab"))))
    (testing "in the middle"
      (is (= 1 (distance "abc" "ac"))))
    (testing "at beginning"
      (is (= 1 (distance "cat" "at")))))

  (testing "substitute one character"
    (testing "at end"
      (is (= 1 (distance "cat" "car"))))
    (testing "in the middle"
      (is (= 1 (distance "cat" "cot"))))
    (testing "at beginning"
      (is (= 1 (distance "cat" "bat")))))

  (testing "two letter changes"
    (testing "at end"
      (is (= 2 (distance "bit" "bob"))))
    (testing "in the middle"
      (is (= 2 (distance "bite" "tate"))))
    (testing "at beginning"
      (is (= 2 (distance "bit" "tat")))))

  (testing "things I missed"
    (is (= 1 (distance "oat" "coat")))
    (is (= 1 (distance "cot" "coat")))
    (is (= 2 (distance "coat" "hot")))))

(deftest test-has-chain?
  (is (= true  (has-chain? #{"hat" "coat" "dog" "cat" "oat" "cot" "hot" "hog"})))
  (is (= true  (has-chain? #{"spout" "do" "pot" "pout" "spot" "dot"})))
  (is (= true  (has-chain? #{"share" "hares" "shares" "hare" "are"})))

  (is (= false (has-chain? #{"cot" "hot" "bat" "fat"})))
  (is (= false (has-chain? #{"to" "top" "stop" "tops" "toss"})))
  (is (= false (has-chain? #{"share" "hares" "hare" "are"}))))

