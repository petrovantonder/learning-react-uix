(ns app.pet
  (:require
   [uix.core :as uix :refer [defui $]]
   [uix.dom]))

(defui pet [props]
  ($ :div
     ($ :h2 "My name is: " (:name props))
     ($ :p "I am of breed: " (:animal props))
     ($ :p "I am a: " (:breed props))))


