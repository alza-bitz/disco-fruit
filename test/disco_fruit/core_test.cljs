(ns disco-fruit.core-test
  (:require
   [disco-fruit.core :as c]
   [plumula.mimolette.alpha :refer [defspec-test]]))

(defspec-test 
  fruit-phrase
  `c/fruit-phrase
  {:opts {:num-tests 100}})