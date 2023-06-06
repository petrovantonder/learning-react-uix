(ns pet
  (:require
   [uix.core :as uix :refer [defui $]]
   [uix.dom]))

(defui pet [props]
  ($ :div
     ($ :h1 (:name props))
     ($ :h2 (:animal props))
     ($ :h2 (:breed props))))


