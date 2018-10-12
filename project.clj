(defproject disco-fruit "0.1.0-SNAPSHOT"
  :description "Disco Fruit"
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/clojurescript "1.10.339"]]
  :plugins [[lein-cljsbuild "1.1.7"]]
  :cljsbuild {:builds [{:source-paths ["src"]
                        :compiler {:target :nodejs
                                   :main disco-fruit.main
                                   :output-to "target/main.js"
                                   :source-map true}}
                       {:id "test"
                        :source-paths ["src" "test"]
                        :compiler {:target :nodejs
                                   :main disco-fruit.all-tests
                                   :output-to "target/all-tests.js"
                                   :source-map true}}]
              :test-commands
              {"all-tests" ["node" "target/all-tests.js"]}}
  :profiles {:dev {:dependencies [[org.clojure/test.check "0.9.0"]]
                   :source-paths ["src"]
                   :repl-options {:nrepl-middleware [cider.piggieback/wrap-cljs-repl]}}
             :repl {:plugins [[cider/cider-nrepl "0.18.0-SNAPSHOT"]]
                    :dependencies [[cider/piggieback "0.3.9"]
                                   [org.clojure/tools.nrepl "0.2.13"]
                                   [proto-repl "0.3.1"]
                                   [org.clojure/test.check "0.9.0"]]
                    :source-paths ["dev" "src"]
                    :repl-options {:nrepl-middleware [cider.piggieback/wrap-cljs-repl]}}})
