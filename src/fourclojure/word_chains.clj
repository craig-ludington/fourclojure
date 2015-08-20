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
                    (+ (distance s (dec len-s) t (dec len-t))  (if (= (nth s (dec len-s)) (nth t (dec len-t))) 0 1)))))
  ([s t] (distance s (count s) t (count t))))

(defn close-enough? [xs ys] (= 1 (distance xs ys)))

(defn graph [xs] (reduce into {} (map (fn [x] {x (set (filter (partial close-enough? x) xs))}) xs)))

(defn depth-first-traversal
  [graph start]
  (loop [result  [start]
         visited #{start}
         stack   (list start)]
    (if (empty? stack)
      result
      (let [next (first (sort (remove visited (graph (peek stack)))))]
        (if next
          (recur (conj result next) (conj visited next) (conj stack next))
          result ;; Stop at first dead end -- don't pop the stack to visit everything (recur result visited (pop stack))
          )))))

(defn has-chain?
  [words]
  (let [g (graph words)
        wc (count words)
        pass #(= wc (count (depth-first-traversal g %)))]
    (if (some pass words)
      true
      false)))
