(ns app.results
  (:require
   [uix.core :as uix :refer [defui $]]
   [app.pet :as pet :refer [pet]]
   [uix.dom]))

(defui results [{:keys [pets]}]
  ($ :div {:className "search"}
     (if (empty? pets)
       ($ :h1 "No Pets Found")
       (map #($ pet {:name     (:name %)
                     :animal   (:animal %)
                     :breed    (:breed %)
                     :images   (:images %)
                     :location (str (:city %) ", " (:state %))
                     :key      (:id %)
                     :id       (:id %)}) pets))))



