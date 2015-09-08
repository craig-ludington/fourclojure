(ns fourclojure.game-of-life-test
  (:require [clojure.test :refer :all]
            [fourclojure.game-of-life :refer :all]))

(deftest test-__
  (is (= (__ ["      "  
              " ##   "
              " ##   "
              "   ## "
              "   ## "
              "      "])
         ["      "  
          " ##   "
          " #    "
          "    # "
          "   ## "
          "      "]))

  (is (= (__ ["     "
              "     "
              " ### "
              "     "
              "     "])
         ["     "
          "  #  "
          "  #  "
          "  #  "
          "     "]))

  (is (= (__ ["      "
              "      "
              "  ### "
              " ###  "
              "      "
              "      "])
         ["      "
          "   #  "
          " #  # "
          " #  # "
          "  #   "
          "      "])))
