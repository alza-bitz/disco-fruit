(ns disco-fruit.cli (:require
                      [disco-fruit.core :as c]
                      [disco-fruit.data :as d]
                      [clojure.string :as s]
                      [cljs.spec.test.alpha]))

(defn -main [& args]
  (cljs.spec.test.alpha/instrument `c/fruit-phrase)
  (println (s/join " " (c/fruit-phrase d/fruits d/nouns d/adjectives))))

(set! *main-cli-fn* -main)
