(ns app.pet
  (:require
   [uix.core :as uix :refer [defui $]]
   [uix.dom]))

(defui pet [{:keys [name animal breed] :as _props}]
  ($ :div
     ($ :h2 "My name: " name)
     ($ :p "I am of breed: " animal)
     ($ :p "I am a: " breed)))


