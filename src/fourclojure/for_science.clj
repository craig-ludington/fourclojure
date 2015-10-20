(ns fourclojure.for-science
  (:use [clojure.pprint :refer :all]
         [clojure.repl :refer :all]))

;; http://www.4clojure.com/problem/117

;; A mad scientist with tenure has created an experiment tracking mice in
;; a maze. Several mazes have been randomly generated, and you've been
;; tasked with writing a program to determine the mazes in which it's
;; possible for the mouse to reach the cheesy endpoint. Write a function
;; which accepts a maze in the form of a collection of rows, each row is
;; a string where:

;; * spaces represent areas where the mouse can walk freely
;; * hashes (#) represent walls where the mouse can not walk
;; * M represents the mouse's starting point
;; * C represents the cheese which the mouse must reach

;; The mouse is not allowed to travel diagonally in the maze (only
;; up/down/left/right), nor can he escape the edge of the maze. Your
;; function must return true iff the maze is solvable by the mouse.

(comment
  (letfn [(adjacency ;; Nodes reachable in one step from node n.
            [n]
            (cond (zero? n) [2]
                  (odd? n)  [(+ 2 n) (* 2 n)]
                  :else     [(+ 2 n) (* 2 n) (/ n 2)]))
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
              (cond (level goal)   (inc (level goal))
                    (seq frontier) (let [[new-frontier new-level new-parent] (update-for-frontier i frontier level parent adj)]
                                     (recur (inc i)
                                            new-level
                                            new-parent
                                            new-frontier)))))]
    ;; (bfs start end adjacency)
    ))

(comment
  (def __ 
    (fn [maze]
      (letfn []
        (solve? maze)))))




(def maze ["#######"
           "#     #"
           "#  #  #"
           "#M # C#"
           "#######"])

(defn adjacency-map
  [maze]
  (let [n-col      (count (first maze))
        n-row      (count maze)
        max-col    (dec n-col)
        max-row    (dec n-row)
        xy         (fn [row col]
                     (when (and (<= 0 row max-row)
                                (<= 0 col max-col))
                       (nth (nth maze row) col)))
        reachable? (fn [[row col]]
                     (if (#{\space \M \C} (xy row col))
                       [row col]
                       false))
        adjacents  (fn [row col]
                     (if (reachable? [row col]) ;; cells that are not reachable have no adjacents
                       (vec (remove false?
                                    (map #(reachable? %) [[(dec row) col]
                                                          [row       (dec col)]
                                                          [(inc row) col]
                                                          [row       (inc col)]])))
                       []))]
    (reduce into {}
            (for [row (range n-row) col (range n-col)]
              (let [c (xy row col)]
                {[row col] (adjacents row col)})))))

