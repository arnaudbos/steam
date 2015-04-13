(ns user
  (require [hickory.core :as hickory]
           [hickory.zip :as hzip]
           [hickory.select :as hs]))

(def html (slurp "resources/main.html"))

html

(def htree (-> html
               hickory/parse
               hickory/as-hickory))

htree

(first (hs/select (hs/id "navigation") htree))
(hzip/hickory-zip (first (hs/select (hs/id "navigation") htree)))
(hs/select-next-loc (hs/tag "a") (hzip/hickory-zip (first (hs/select (hs/id "navigation") htree))))

(def data (atom ["foo" "bar" "baz" "qux"]))

(defn css-to-hickory
  [sel]
  {:pre [(every? keyword sel)] :post [(fn? %)]}
  (hs/class "content"))

()

(defmacro defsnippet [sym path sel args & trans]
  {:pre [(symbol? sym) (string? path) (every? keyword sel) (every? symbol? args) (seq? trans) (even? (count trans))]}
  (let [html (slurp (str "resources/" path))
        htree (-> html hickory/parse hickory/as-hickory)
        sel (if (empty? sel) [:body] sel)
        selector (css-to-hickory sel)
        root (-> selector (hs/select htree) hzip/hickory-zip)
        transformations (partition 2 trans)
        tree (loop [transformations transformations tree root]
               (if-let [transformation (first transformations)]
                 (let [[sel trans] transformation
                       [trans & params] trans]
                   (println trans)
                   (println (type trans))
                   (recur (rest transformations) tree))
                 tree))
        ]
    `(defn ~sym ~args
       ~@args)))

(defn my-header []
  [:header "This is my header"])

(defsnippet item "main.html" [:.nav-item]
            [text]
            [:a] (hs/content text))

(item "toto")

(defsnippet my-page "main.html" [:body]
            []
            [:header] (user/replace [my-header])
            [:#navigation] (user/content (for [content @data]
                                      [item content])))
