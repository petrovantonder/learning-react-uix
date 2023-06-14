(ns app.details
  (:require
   [uix.core :as uix :refer [defui $]]
   ["react-router-dom" :refer [useParams]]
   ["@tanstack/react-query" :refer [useQuery]]
   [app.fetch-pets :as fetch-pets :refer [fetch-pet]]
   [uix.dom]))

(defui details []
  (let [id (:id (js->clj (useParams) :keywordize-keys true))
        results (useQuery (clj->js ["details" id]) fetch-pet)]
    (println "pets data here " results)
    (println "is loading? " (.-isLoading results))
    (if (.-isLoading results)
      ($ :div {:className "loading-pane"}
         ($ :h2 {:className "loader"} "🤪"))
      (let [pet-data (js->clj (aget (.-pets (.-data results)) 0) :keywordize-keys true)]
        ($ :div {:className "details"}
           ($ :div
              ($ :h1 (:name pet-data))
              ($ :h2 (str (:animal pet-data) " - " (:breed pet-data)
                          " - " (:city pet-data) ", " (:state pet-data)))
              ($ :button (str "Adopt " (:name pet-data)))
              ($ :p (:description pet-data))))))
    ))



