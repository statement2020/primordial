(ns primordial.core-test
  (:require [clojure.test :refer :all]
            [primordial.core :refer :all]))

(deftest test-map-apply
  (testing "That when a key exists the fn is applied"
    (is (= {:a 2} (map-apply {:a inc} {:a 1}))))
  (testing "That when a key doesn't exist nothing happens"
    (is (= {:b 1} (map-apply {:a inc} {:b 1})))))

(deftest test-map-vals
  (testing "That an fn works"
    (is (= {:a 2} (map-vals inc {:a 1}))))
  (testing "That an unavail fn gets thrown"
    (is (thrown? Exception (map-vals inc {:a "a"})))))

(deftest test-map-keys
  (testing "That an fn works"
    (is (= {2 1} (map-keys inc {1 1}))))
  (testing "That an unavail fn gets thrown"
    (is (thrown? Exception (map-keys inc {:a "a"})))))
