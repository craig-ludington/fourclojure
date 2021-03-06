(ns fourclojure.graph-tour)

;; http://www.4clojure.com/problem/89

;; Starting with a graph you must write a function that returns true if
;; it is possible to make a tour of the graph in which every edge is
;; visited exactly once.

;; The graph is represented by a vector of tuples, where each tuple represents a single edge.

;; The rules are:

;; - You can start at any node.
;; - You must visit each edge exactly once.
;; - All edges are undirected.


(def __
  (fn [tuples]
    (letfn [(make-graph [ts]
              (loop [m {}
                     ts ts]
                (if (seq ts)
                  (let [[x y] (first ts)
                        m'  (assoc m  x (conj (or (m  x) []) y))
                        m'' (assoc m' y (conj (or (m' y) []) x))]
                    (recur m''
                           (rest ts)))
                  m)))
            (depth-first-traversal
              [graph start]
              (loop [result  [start]
                     visited #{start}
                     stack   (list start)]
                (if (empty? stack)
                  result
                  (let [next (first (sort (remove visited (graph (peek stack)))))]
                    (if next
                      (recur (conj result next) (conj visited next) (conj stack next))
                      result
                      )))))
            (connected?
              []
              (let [g (make-graph tuples)
                    vertices (reduce into #{} tuples)
                    num (count vertices)
                    paths (map #(depth-first-traversal g %)
                               (map first tuples))
                    pass #(= num (count %))]
                (some pass paths)))
            (degree-ok? []
              (let [num-odd (count (filter odd? (map count (vals (group-by identity (reduce into [] tuples))))))]
                (or (zero? num-odd)
                    (= 2 num-odd))))]
      (if (and (degree-ok?) (connected?))
        true
        false))))
