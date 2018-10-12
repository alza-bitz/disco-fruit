(ns user
  (:require
   [clojure.tools.nrepl] ; https://github.com/nrepl/piggieback/issues/94
   [cider.piggieback]
   [cljs.repl.node]))

(defn cljs-repl []
  (cider.piggieback/cljs-repl (cljs.repl.node/repl-env) :source-map true))
