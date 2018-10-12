(ns disco-fruit.spec
  (:require
   [clojure.string          :as str]
   #?(:clj [clojure.spec.alpha      :as s] :cljs [cljs.spec.alpha      :as s])
   #?(:clj [clojure.spec.test.alpha      :as stest] :cljs [cljs.spec.test.alpha      :as stest])
   #?(:clj [clojure.test :as test] :cljs [cljs.test :as test :include-macros true])
 ))

(defn report-results [check-results]
  (let [checks-passed? (->> check-results (map :failure) (every? nil?))]
    (if checks-passed?
      (test/do-report {:type    :pass
                       :message (str "Generative tests pass for "
                                     (str/join ", " (map :sym check-results)))})
      (doseq [failed-check (filter :failure check-results)]
        (let [r       (stest/abbrev-result failed-check)
              failure (:failure r)]
          (test/do-report
           {:type     :fail
            :message  (with-out-str (s/explain-out failure))
            :expected (->> r :spec rest (apply hash-map) :ret)
            :actual   (if (instance? #?(:clj Throwable :cljs js/Error) failure)
                        failure
                        (::stest/val failure))}))))
    checks-passed?))

(defmacro defspec-test
  ([name sym-or-syms] `(defspec-test ~name ~sym-or-syms nil))
  ([name sym-or-syms opts]
    `(defn ~(vary-meta name assoc :test
             `(fn [] (report-results (cljs.spec.test.alpha/check ~sym-or-syms ~opts))))
       [] (cljs.test/test-var (var ~name)))))
