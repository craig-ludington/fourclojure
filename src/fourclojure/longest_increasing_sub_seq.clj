(ns fourclojure.longest-increasing-sub-seq)

;; http://www.4clojure.com/problem/53

;; Given a vector of integers, find the longest consecutive sub-sequence
;; of increasing numbers. If two sub-sequences have the same length, use
;; the one that occurs first. An increasing sub-sequence must have a
;; length of 2 or greater to qualify.

(defn increasing? [s] (apply < s))

(defn increasing-subseq
  [s]
  (loop [s s]
    (if (seq s)
      (if (increasing? s)
        (vec s)
        (recur (take (dec (count s)) s)))
      [])))

(defn all-increasing-subseqs
  [s]
  (loop [s s
         acc []]
    (if (seq s)
      (recur (rest s)
             (conj acc (increasing-subseq s)))
      acc)))

(defn best-increasing-subseq
  [s]
  (loop [qualifiers (all-increasing-subseqs s)
         best nil]
    (if (seq qualifiers)
      (recur (rest qualifiers)
             (if (> (count (first qualifiers))
                    (count best))
               (first qualifiers)
               best))
      (if (> (count best) 1)
        best
        []))))

(def __ best-increasing-subseq)

