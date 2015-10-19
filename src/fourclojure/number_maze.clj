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
    ))

(defn adjacency
  "Nodes reachable in one step from node n."
  [n]
  (vec (into #{} (cond (zero? n) [2]
                       (odd? n)  [(+ 2 n) (* 2 n)]
                       :else     [(+ 2 n) (* 2 n) (/ n 2)]))))
(comment
  (update-for-frontier-node 4           ;; start node
                            1           ;; i
                            {4 0}       ;; level
                            {4 :none}   ;; parents
                            adjacency)) ;; function
;; [[6 2 8]
;;  {8 1, 2 1, 6 1, 4 0}
;;  {8 4, 2 4, 6 4, 4 :none}]

(defn update-for-frontier-node
  [node i level parents adj]
  (loop [nodes   (filter #(not (level %)) (adj node))
         next    []
         level   level
         parents parents]
    (if (seq nodes)
      (let [n (first nodes)]
        (recur (rest nodes)
               (conj next n)
               (assoc level n i)
               (assoc parents n node)))
      [next level parents])))

(comment
  (update-for-frontier 1           ;; i
                       [4]         ;; frontier
                       {4 0}       ;; level
                       {4 :none}   ;; parents
                       adjacency)) ;; function
;;
;; [[6 2 8]                    ;; new frontier
;;  {4 0, 8 1, 2 1, 6 1}       ;; new level
;;  {4 :none, 8 4, 2 4, 6 4}]  ;; new parents

(defn update-for-frontier
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

(defn bfs
  [s adj]
  (loop [i        1
         level    {s 0}
         parent   {s :none}
         frontier [s]]
    (if (and (seq frontier)
             (< i 3))
      (let [[new-frontier new-level new-parents] (update-for-frontier i frontier level parent adj)]
        (recur (inc i)
               new-level
               new-parents
               new-frontier))
      {:level level :parent parent})))



(= 1 (__ 1 1))  ; 1
(= 3 (__ 3 12)) ; 3 6 12
(= 3 (__ 12 3)) ; 12 6 3
(= 3 (__ 5 9))  ; 5 7 9
(= 9 (__ 9 2))  ; 9 18 20 10 12 6 8 4 2
(= 5 (__ 9 12)) ; 9 11 22 24 12
