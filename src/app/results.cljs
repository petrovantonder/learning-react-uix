(ns app.results
  (:require
   [uix.core :as uix :refer [defui $]]
   [app.pet :as pet :refer [pet]]
   [uix.dom]))

(defui results [pets]
  ($ :div {:className "search"}
     (if (empty? pets)
       ($ :h1 "No Pets Found")
       (map #($ pet {:name     (:name %)
                     :animal   (:animal %)
                     :breed    (:breed %)
                     :images   (:images %)
                     :location [(:city %) (:state %)]
                     :key      (:id %)}) pets))))



