(defproject steam "0.1.0-SNAPSHOT"
  :description "Enlive style templating with Hiccup output"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :profiles {:dev {:main user
                   :source-paths ["dev"]
                   :plugins [[lein-cljsbuild "1.0.5"]]
                   :dependencies [[org.clojure/clojurescript "0.0-3126"]
                                  [hickory "0.5.4"]
                                  ]}}
  :dependencies [[org.clojure/clojure "1.6.0"]]

  :source-paths ["src"]
  :resource-paths ["resources"]

  :cljsbuild {
    :builds [{:id "dev"
              :source-paths ["src"]
              :compiler {
                :output-to "app.js"
                :pretty-print true
                :optimizations :simple
                }}]})
