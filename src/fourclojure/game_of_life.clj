(ns fourclojure.game-of-life)

(def __
  (fn [world]
    (letfn [(neighborhood
              [[x y]]
              (map (fn vector-add [[xx yy]] (vector (+ x xx) (+ y yy)))
                   [[-1 -1] [-1 0] [-1 1] [0 -1] [0 1] [1 -1] [1 0] [1 1]]))

            (neighbors
              [world coordinate]
              (filter world (neighborhood coordinate)))

            (pregnant?
              [world coordinate]
              (when (and (not (world coordinate))
                         (= 3 (count (neighbors world coordinate))))
                coordinate))

            (spawn
              [world]
              (set (filter (partial pregnant? world)
                           (reduce into [] (map neighborhood world)))))

            (healthy?
              [world cell]
              (when (and (world cell)
                         (#{2 3} (count (neighbors world cell)))) 
                cell))

            (survivors
              [world]
              (set (filter (partial healthy? world) world)))

            (new-world
              [world]
              (into (survivors world) (spawn world)))

            (to-coords
              [world]
              (set (filter vector?
                           (reduce into [] (map #(for [x [%] y (range (count (first world)))]
                                                   (if (= \# (nth (nth world x) y))
                                                     [x y]))
                                                (range (count world)))))))

            (from-coords
              [coords rows cols]
              (vec (map #(apply str %)
                        (partition cols (for [x (range rows) y (range cols)]
                                          (if (coords [x y])
                                            \#
                                            \space))))))
            (main
              [world]
              (let [rows (count world)
                    cols (count (first world))]
                (from-coords (new-world (to-coords world)) rows cols)))]

      (main world))))

