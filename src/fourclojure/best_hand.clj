(ns fourclojure.best-hand
  (:use [clojure.pprint :refer :all]
         [clojure.repl :refer :all]))

;; http://www.4clojure.com/problem/178

;; Following on from Recognize Playing Cards, determine the best poker
;; hand that can be made with five cards. The hand rankings are listed
;; below for your convenience.

;; Straight flush    All cards in the same suit, and in sequence
;; Four of a kind    Four of the cards have the same rank
;; Full House        Three cards of one rank, the other two of another rank
;; Flush             All cards in the same suit
;; Straight          All cards in sequence (aces can be high or low, but not both at once)
;; Three of a kind   Three of the cards have the same rank
;; Two pair          Two pairs of cards have the same rank
;; Pair              Two cards have the same rank
;; High card         None of the above conditions are met


(def __
  (fn [cards]
    (letfn [(suit [card]  ({\D :diamond, \H :heart, \C :club, \S :spade} (nth card 0)))
            (rank [card]  ({\2 0, \3 1, \4 2, \5 3, \6 4, \7 5, \8 6, \9 7, \T 8, \J 9, \Q 10, \K 11, \A 12} (nth card 1)))
            (id   [card]  {:suit (suit card) :rank (rank card)})
            (ids  [cards] (map id cards))
            (strict-order? [ns]
              (let [lo (apply min ns)
                    hi (apply max ns)
                    r  (range lo (+ lo 5))]
                (cond (not= 5 (count (keys (group-by identity ns)))) false
                      (= (sort ns) (range lo (+ lo 5))) true
                      (= 12 hi) (strict-order? (conj (remove #(= 12 %) ns) -1)))))]
      (let [hand          (ids cards)
            suits         (group-by :suit hand)
            ranks         (group-by :rank hand)
            flush?        (= 1 (count (keys suits)))
            straight?     (strict-order? (keys ranks))
            four-kind?    (some #(= 4 %) (map count (vals ranks)))
            three-kind?   (some #(= 3 %) (map count (vals ranks)))
            pair?         (some #(= 2 %) (map count (vals ranks)))
            two-pair?     (= 2 (count (filter #(= 2 (count %))  (vals ranks))))]
        (cond (and straight? flush?)  :straight-flush
              four-kind?              :four-of-a-kind
              (and three-kind? pair?) :full-house
              flush?                  :flush
              straight?               :straight
              three-kind?             :three-of-a-kind
              two-pair?               :two-pair
              pair?                   :pair
              :else                   :high-card)))))
