(ns disco-fruit.lang
  (:require [cljs.spec.alpha :as s]
            [cljs.spec.gen.alpha :as gen]))

(s/def ::word
  (s/with-gen (s/and string? #(re-matches #"[A-za-z]+" %))
    (fn []
      (->>
       (gen/char-alpha)
       (gen/vector)
       (gen/such-that #(not-empty %))
       (gen/fmap #(apply str %))))))

(defn same-first-letter
  [str1-kw str2-kw parsed-spec]
  (=
   (nth (get parsed-spec str1-kw) 0)
   (nth (get parsed-spec str2-kw) 0)))

(s/def ::phrase
  (s/and
   (s/cat
    :the #{"The"}
    :adjective-or-fruit ::word
    :noun-or-fruit ::word)
   (s/spec (partial same-first-letter :adjective-or-fruit :noun-or-fruit))))
