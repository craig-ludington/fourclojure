(ns fourclojure.number-maze
  (:require [clojure.repl :refer :all]
            [clojure.pprint :refer :all]))

;; http://www.4clojure.com/problem/106


;; Given a pair of numbers, the start and end point, find a path between the two using only three possible operations:
;;   double
;;   halve (odd numbers cannot be halved)
;;   add 2 
;; Find the shortest path through the "maze". Because there are
;; multiple shortest paths, you must return the length of the shortest
;; path, not the path itself.

(def __
  (fn [start end]
    (letfn [(adjacency ;; Nodes reachable in one step from node n.
              [n]
              (vec (into #{} (cond (zero? n) [2]
                                   (odd? n)  [(+ 2 n) (* 2 n)]
                                   :else     [(+ 2 n) (* 2 n) (/ n 2)]))))
            (update-for-frontier-node ;; Return next nodes and updated level and parent maps for one node on the frontier.
              [node i level parents adj]
              (loop [nodes   (adj node)
                     next    []
                     level   level
                     parents parents]
                (if (seq nodes)
                  (let [n (first nodes)]
                    (if (level n)
                      (recur (rest nodes) next level parents)
                      (recur (rest nodes) (conj next n) (assoc level n i) (assoc parents n node))))
                  [next level parents])))
            (update-for-frontier ;; Return next nodes and updated level and parent maps for all nodes on the frontier.
              [i frontier level parents adj]
              (loop [frontier frontier
                     next     []
                     level    level
                     parents  parents]
                (if (seq frontier)
                  (let [n (first frontier)
                        [new-nodes new-level new-parents] (update-for-frontier-node n i level parents adj)]
                    (recur (rest frontier)
                           (into next new-nodes)
                           (into level new-level)
                           (into parents new-parents)))
                  [next level parents])))
            (bfs ;; Return the number of steps to reach goal from starting node s with the adjacency function adj.
              [s goal adj]
              (loop [i        1
                     level    {s 0}
                     parent   {s :none}
                     frontier [s]]
                (cond (> i 10)       (throw (Throwable. "Too many iterations"))
                      (level goal)   (inc (level goal))
                      (seq frontier) (let [[new-frontier new-level new-parent] (update-for-frontier i frontier level parent adj)]
                                       (recur (inc i)
                                              new-level
                                              new-parent
                                              new-frontier))
                      :else          (throw (Throwable. "Logic error")))))]
      (bfs start end adjacency))))
