(ns fourclojure.best-hand-test
  (:require [clojure.test :refer :all]
            [fourclojure.best-hand :refer :all]))

(deftest test-__
  (is (= :high-card (__ ["HA" "D2" "H3" "C9" "DJ"])))  ;;
  (is (= :pair (__ ["HA" "HQ" "SJ" "DA" "HT"])))
  (is (= :two-pair (__ ["HA" "DA" "HQ" "SQ" "HT"])))
  (is (= :three-of-a-kind (__ ["HA" "DA" "CA" "HJ" "HT"])))
  (is (= :straight (__ ["HA" "DK" "HQ" "HJ" "HT"])))
  (is (= :straight (__ ["HA" "H2" "S3" "D4" "C5"]))) ;; 
  (is (= :flush (__ ["HA" "HK" "H2" "H4" "HT"]))) ;;
  (is (= :full-house (__ ["HA" "DA" "CA" "HJ" "DJ"])))
  (is (= :four-of-a-kind (__ ["HA" "DA" "CA" "SA" "DJ"])))
  (is (= :straight-flush (__ ["HA" "HK" "HQ" "HJ" "HT"])))
  )
