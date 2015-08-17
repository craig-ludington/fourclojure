(ns fourclojure.word-chains)

;; http://www.4clojure.com/problem/82

;; A word chain consists of a set of words ordered so that each word differs by only one letter 
;; from the words directly before and after it. The one letter difference can be either an insertion,
;; a deletion, or a substitution. Here is an example word chain:

;; cat -> cot -> coat -> oat -> hat -> hot -> hog -> dog

;; Write a function which takes a sequence of words, and returns true if they can be arranged into one continous word chain,
;; and false if they cannot.

(defn distance
  ([s len-s t len-t]
   (cond (zero? len-s) len-t
         (zero? len-t) len-s
         :else (min (+ (distance s (dec len-s) t len-t)        1)
                    (+ (distance s len-s       t (dec len-t))  1)
                    (+ (distance s (dec len-s) t (dec len-t))  (if (= (nth s (dec len-s))
                                                                      (nth t (dec len-t)))
                                                                 0
                                                                 1)))))
  ([s t] (distance s (count s) t (count t))))

(defn close-enough? [xs ys] (= 1 (distance xs ys)))

(defn graph [xs] (reduce into {} (map (fn [x] {x (set (filter (partial close-enough? x) xs))}) xs)))

(defn chain?
  [path]
  (loop [p path]
    (let [[a b] p]
      (cond (not b) path
            (close-enough? a b) (recur (rest p))
            :else false))))

(defn traverse
  [graph seen head]
  {:pre [(map? graph) (vector? seen) (string? head)]}
  (let [seen (conj seen head)
        candidates (remove (set seen) (graph head))]
    (if (empty? candidates)
      seen
      (traverse graph seen (first candidates)))))

(defn traversals [g] (map (partial traverse g []) (keys g)))

(defn chains [g] (filter #(= (count (keys g)) (count %)) (traversals g)))

(defn has-chain? [words] (if (seq (chains (graph words))) true false))
