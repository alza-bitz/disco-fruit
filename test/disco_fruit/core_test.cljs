(ns disco-fruit.core-test
  (:require
   [cljs.test :refer-macros [deftest is testing run-tests]]
   [disco-fruit.core :as c]
   [disco-fruit.spec :refer-macros [defspec-test]]))

(defspec-test fruit-phrase-test `c/fruit-phrase {:clojure.test.check/opts {:num-tests 2}})