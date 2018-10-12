(ns disco-fruit.all-tests
  (:require
   [cljs.test]
   [disco-fruit.core-test]))

(cljs.test/run-tests 'disco-fruit.core-test)
