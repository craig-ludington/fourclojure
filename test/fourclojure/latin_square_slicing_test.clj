(ns fourclojure.latin-square-slicing-test
  (:require [clojure.test :refer :all]
            [fourclojure.latin-square-slicing :refer :all]))

(deftest test-__
  (is (= (__ '[[A B C D]
               [A C D B]
               [B A D C]
               [D C A B]])
         {}))

  (is (= (__ '[[A B C D E F]
               [B C D E F A]
               [C D E F A B]
               [D E F A B C]
               [E F A B C D]
               [F A B C D E]])
         {6 1}))

  (is (= (__ '[[A B C D]
               [B A D C]
               [D C B A]
               [C D A B]])
         {4 1, 2 4}))

  (is (= (__ '[[B D A C B]
               [D A B C A]
               [A B C A B]
               [B C A B C]
               [A D B C A]])
         {3 3}))

  (is (= (__ [  [2 4 6 3]
                [3 4 6 2]
                [6 2 4]  ])
         {}))

  (is (= (__ [[1]
              [1 2 1 2]
              [2 1 2 1]
              [1 2 1 2]
              []       ])
         {2 2}))

  (is (= (__ [[3 1 2]
              [1 2 3 1 3 4]
              [2 3 1 3]    ])
         {3 1, 2 2}))

  (is (= (__ [[8 6 7 3 2 5 1 4]
              [6 8 3 7]
              [7 3 8 6]
              [3 7 6 8 1 4 5 2]
              [1 8 5 2 4]
              [8 1 2 4 5]])
         {4 1, 3 1, 2 7}))
  )
