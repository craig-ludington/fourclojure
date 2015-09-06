(ns fourclojure.triangle-minimum-path)

;; http://www.4clojure.com/problem/79

;; Write a function which calculates the sum of the minimal path through
;; a triangle. The triangle is represented as a collection of
;; vectors. The path should start at the top of the triangle and move to
;; an adjacent number on the next row until the bottom of the triangle is
;; reached.


;; 7
;; 1->2->1->3
;;
(def sample-1 '(   [1]
                  [2 4]
                 [5 1 4]
                [2 3 4 5]))

;; 20
;; 3->4->3->2->7->1
;;
(def sample-2 '(     [3]
                    [2 4]
                   [1 9 3]
                  [9 9 2 4]
                 [4 6 6 7 8]
                [5 7 3 5 1 4]))
(defn nodes
  [tri]
  (let [max-x (dec (count tri))
        left  (fn [x y] (when (< x max-x) [(inc x) y]))
        right (fn [x y] (when (< x max-x) [(inc x) (inc y)]))
        ns    (reduce into {} (map #(for [x [%] y (range (inc %))]
                                      {[x y] {:v (nth (nth tri x) y) :l (left x y) :r (right x y)}})
                                   (range (count tri))))]
    ns))

;; The first type of traversal is pre-order whose code looks like the following:

;; sub P(TreeNode)
;;    Output(TreeNode.value)
;;    If LeftPointer(TreeNode) != NULL Then
;;       P(TreeNode.LeftNode)
;;    If RightPointer(TreeNode) != NULL Then
;;       P(TreeNode.RightNode)
;; end sub
;; This can be summed up as

;; Visit the root node (generally output this)
;; Traverse to left subtree
;; Traverse to right subtree

(defn preorder
  [tri]
  (let [nds (nodes tri)]
    (letfn [(p [node]
              (println (:v node))
              (if (:l node)
                (p (nds (:l node))))
              (if (:r node)
                (p (nds (:r node)))))]
      (p (nds [0 0])))))

(def __ (fn
          [tri]
          (let [nds (nodes tri)
                acc (atom [])]
            (letfn [(p [node path]
                      (when node
                        (if (and (not (:l node)) (not (:r node)))
                          (swap! acc conj (conj path (:v node)))
                          (let [ps (conj path (:v node))]
                            (p (nds (:l node)) ps)
                            (p (nds (:r node)) ps)))))]
              (p (nds [0 0]) [])
              (apply min (map #(apply + %) @acc))))))
