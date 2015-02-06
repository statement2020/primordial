(ns primordial.core
  (:require [clojure.string :as strs]))

(defn map-apply
  "Takes a map 'ref' of key->fn
  and applies that fn to the
  corresponding value in the map 'm'"
  [ref m]
  (into {}
        (map (fn [[k v]]
               (if-let [f (k ref)]
                 {k (f v)}
                 {k v})) m)))

(defn map-vals
  "Takes a function 'f' and applies
  it to all the values in the map 'm'"
  [f m]
  (into {}
        (map (fn [[k v]] [k (f v)]) m)))

(defn map-keys
  "Takes a function 'f' and applies
  it to all the keys in the map 'm'"
  [f m]
  (into {}
        (map (fn [[k v]]
               [(f k) v]) m)))

(defn idiomize-key
  "Takes a value and replace undercores with hyphens"
  [k]
  (if (keyword? k)
    (-> k name idiomize-key)
    (-> (strs/replace k "_" "-") strs/lower-case)))

(defn keywordize
  "Takes a value, if it's already keyword returns it, if not
  turns it into a keyword before returning"
  [k]
  (if (keyword? k)
    k
    (keyword k)))

(defn prettify-keys
  "Takes any map, and makes the keys keywordy by
  idiomizing, them, and then keywording them."
  [m]
  (map-keys (comp keywordize idiomize-key) m))

(def not-nil?
  "Opposite of nil?"
  (complement nil?))

(defn sum
  "Adds a collection of numbers together.
  Not nil safe"
  [coll]
  (reduce + coll))

(defn sum-safe
  "Nil safe version of 'sum'."
  [coll]
  (->> (filter not-nil? coll)
       sum))

(defn minus
  "Subtracts all the numbers in a collection.
  Not nil safe."
  [coll]
  (reduce - coll))

(defn minus-safe
  "Nil safe version of minus."
  [coll]
  (->> (filter not-nil? coll)
       minus))

(defn multiply
  "Multiplies all the numbers in a collection.
  Not nil safe."
  [coll]
  (reduce * coll))

(defn multiply-safe
  "Nil safe version of multiply."
  [coll]
  (->> (filter not-nil? coll)
       multiply))

(defn denominators
  [collr]
  (map denominator collr))

(defn safe-subs
  "Like subs but does not throw a StringIndexOutOfBoundsException.
  e.g. (safe-subs \"Hi\" 3) => \"\""
  ([^String s start]
    (let [c (.length s)]
      (safe-subs s start c)))
  ([^String s start end]
    (let [c (.length s)]
      (subs s (min c start) (min c end)))))