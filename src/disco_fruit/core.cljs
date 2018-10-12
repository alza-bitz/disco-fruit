(ns disco-fruit.core
  (:require [cljs.spec.alpha :as s]
            [disco-fruit.lang :as l]))

(defn fruit-has-matching-noun-and-adjective
  [nouns adjectives matching-fruits fruit]
  (if
   (and
    (some #(= (nth % 0) (nth fruit 0)) nouns)
    (some #(= (nth % 0) (nth fruit 0)) adjectives))
    (conj matching-fruits fruit)
    matching-fruits))

(defn at-least-one-fruit-has-matching-noun-and-adjective
  [parsed-spec]
  (not-empty
   (reduce
    (partial fruit-has-matching-noun-and-adjective (:nouns parsed-spec) (:adjectives parsed-spec))
    nil
    (:fruits parsed-spec))))

(s/def ::at-least-one-fruit-has-matching-noun-and-adjective
  (s/spec at-least-one-fruit-has-matching-noun-and-adjective))

(defn words-starting-with
  [initial matching-words word]
  (if (= (nth word 0) initial)
    (conj matching-words word)
    matching-words))

(s/fdef fruit-phrase
        :args (s/and
               (s/cat :fruits (s/coll-of ::l/word :into #{})
                      :nouns (s/coll-of ::l/word :into #{})
                      :adjectives (s/coll-of ::l/word :into #{}))
               ::at-least-one-fruit-has-matching-noun-and-adjective)
        :ret ::l/phrase
        :fn #(s/or
              :noun-phrase
              (and
               (contains? (-> % :args :fruits) (-> % :ret (get 1)))
               (contains? (-> % :args :nouns) (-> % :ret (get 2))))
              :adjective-phrase
              (and
               (contains? (-> % :args :adjectives) (-> % :ret (get 1)))
               (contains? (-> % :args :fruits) (-> % :ret (get 2))))))

(defn fruit-phrase
  [fruits nouns adjectives]
  (let [matching-fruits (reduce (partial fruit-has-matching-noun-and-adjective nouns adjectives) nil fruits)
        chosen-fruit (rand-nth matching-fruits)
        chosen-kind (rand-nth [:noun :adjective])]
    (case chosen-kind
      :noun (let [matching-nouns (reduce (partial words-starting-with (nth chosen-fruit 0)) nil nouns)
                  chosen-noun (rand-nth matching-nouns)]
              ["The" chosen-fruit chosen-noun])
      :adjective (let [matching-adjectives (reduce (partial words-starting-with (nth chosen-fruit 0)) nil adjectives)
                       chosen-adjective (rand-nth matching-adjectives)]
                   ["The" chosen-adjective chosen-fruit]))))
