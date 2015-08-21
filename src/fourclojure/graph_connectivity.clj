(ns fourclojure.graph-connectivity)
;; http://www.4clojure.com/problem/91

;; Given a graph, determine whether the graph is connected. 
;; A connected graph is such that a path exists between any two given nodes.
;; 
;; -Your function must return true if the graph is connected and false otherwise.
;; 
;; -You will be given a set of tuples representing the edges of a graph.
;;  Each member of a tuple being a vertex/node in the graph.
;; 
;; -Each edge is undirected (can be traversed either direction). 

(def __
  (fn [set-of-tuples]
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
                      (recur result visited (pop stack))
                      )))))
            (has-chain?
              []
              (let [g (make-graph set-of-tuples)
                    vertices (reduce into #{} set-of-tuples)
                    num (count vertices)
                    paths (map #(depth-first-traversal g %)
                               (map first set-of-tuples))
                    pass #(= num (count %))]
                (if (some pass paths)
                  true
                  false)
                ))]
      (has-chain?))))

