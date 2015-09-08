(ns fourclojure.game-of-life)



(defn neighborhood
  "A vector of neighboring coordinates for a coordinate."
  [[x y]]
  (map (fn vector-add [[xx yy]] (vector (+ x xx) (+ y yy)))
       [[-1 -1] [-1 0] [-1 1] [0 -1] [0 1] [1 -1] [1 0] [1 1]]))

(defn neighbors
  "A vector of (live) cells neighboring a coordinate."
  [world coordinate]
  (filter world (neighborhood coordinate)))

(defn pregnant?
  "True if a coordinate has no live cell and has exactly three neighbors."
  [world coordinate]
  (when (and (not (world coordinate))
	     (= 3 (count (neighbors world coordinate))))
    coordinate))

(defn spawn
  "Return all the new live cells in the world."
  [world]
  (set (filter (partial pregnant? world)
	       (reduce into [] (map neighborhood world)))))

(defn healthy?
  "Return cell if it is healthy enough to survive."
  [world cell]
  (when (and (world cell)
	     (#{2 3} (count (neighbors world cell)))) 
    cell))

(defn survivors
  "Return all the living cells that survive into the next world."
  [world]
  (set (filter (partial healthy? world) world)))

(defn new-world
  "Return a new world."
  [world]
  (into (survivors world) (spawn world)))


(def input
  ["     "
   "     "
   " ### "
   "     "
   "     "])

(def output
  ["     "
   "  #  "
   "  #  "
   "  #  "
   "     "])
(defn to-coords
  [world]
  (set (filter vector?
               (reduce into [] (map #(for [x [%] y (range (count (first world)))]
                                       (if (= \# (nth (nth world x) y))
                                         [x y]))
                                    (range (count world)))))))

(defn from-coords
  [coords rows cols]
  (vec (map #(apply str %)
            (partition cols (for [x (range rows) y (range cols)]
                              (if (coords [x y])
                                \#
                                \space))))))

(defn __
  [world]
  (let [rows (count world)
        cols (count (first world))]
    (from-coords (new-world (to-coords world)) rows cols)))

