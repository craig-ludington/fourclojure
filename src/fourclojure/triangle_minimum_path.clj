(ns fourclojure.triangle-minimum-path)

;; http://www.4clojure.com/problem/79

;; Write a function which calculates the sum of the minimal path through
;; a triangle. The triangle is represented as a collection of
;; vectors. The path should start at the top of the triangle and move to
;; an adjacent number on the next row until the bottom of the triangle is
;; reached.

(def __ 
  (fn
    [tri]
    (let [nodes (let [max-x (dec (count tri))
                      left  (fn [x y] (when (< x max-x) [(inc x) y]))
                      right (fn [x y] (when (< x max-x) [(inc x) (inc y)]))
                      ns    (reduce into {} (map #(for [x [%] y (range (inc %))]
                                                    {[x y] {:v (nth (nth tri x) y) :l (left x y) :r (right x y)}})
                                                 (range (count tri))))]
                  ns)
          acc (atom [])]
      (letfn [(p [node path]
                (when node
                  (if (and (not (:l node)) (not (:r node)))
                    (swap! acc conj (conj path (:v node)))
                    (let [ps (conj path (:v node))]
                      (p (nodes (:l node)) ps)
                      (p (nodes (:r node)) ps)))))]
        (p (nodes [0 0]) [])
        (apply min (map #(apply + %) @acc))))))
