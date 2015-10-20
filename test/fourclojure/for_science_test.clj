(ns fourclojure.for-science-test
  (:require [clojure.test :refer :all]
            [fourclojure.for-science :refer :all]))

(deftest test-__
  (is (= true  (__ ["M   C"])))

  (is (= false (__ ["M # C"])))

  (is (= true  (__ ["#######"
              "#     #"
              "#  #  #"
              "#M # C#"
              "#######"])))

  (is (= false (__ ["########"
              "#M  #  #"
              "#   #  #"
              "# # #  #"
              "#   #  #"
              "#  #   #"
              "#  # # #"
              "#  #   #"
              "#  #  C#"
              "########"])))

  (is (= false (__ ["M     "
              "      "
              "      "
              "      "
              "    ##"
              "    #C"])))

  (is (= true  (__ ["C######"
              " #     "
              " #   # "
              " #   #M"
              "     # "])))

  (is (= true  (__ ["C# # # #"
              "        "
              "# # # # "
              "        "
              " # # # #"
              "        "
              "# # # #M"]))))
