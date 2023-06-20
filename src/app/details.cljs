(ns app.details
  (:require
   [uix.core :as uix :refer [defui $]]
   [app.adopted-pet-context :as adopted-pet-context :refer [adopted-pet-context]]
   ["react-router-dom" :refer [useParams useNavigate]]
   ["@tanstack/react-query" :refer [useQuery]]
   [app.fetch-pets :as fetch-pets :refer [fetch-pet]]
   [app.modal :as modal :refer [modal]]
   [uix.dom]))

(defui details []
  (let [id (:id (js->clj (useParams) :keywordize-keys true))
        results (useQuery (clj->js ["details" id]) fetch-pet)
        [show-modal set-show-modal!] (uix/use-state false)
        [_ set-adopted-pet!] (uix/use-context adopted-pet-context)
        navigate (useNavigate)]
    ;; (println "pets data here " results)
    ;; (println "is loading? " (.-isLoading results))
    (if (.-isLoading results)
      ($ :div {:className "loading-pane"}
         ($ :h2 {:className "loader"} "🤪"))
      (let [pet-data (js->clj (aget (.-pets (.-data results)) 0) :keywordize-keys true)]
        ($ :div {:className "details"}
           ($ :div
              ($ :h1 (:name pet-data))
              ($ :h2 (str (:animal pet-data) " - " (:breed pet-data)
                          " - " (:city pet-data) ", " (:state pet-data)))
              ($ :button {:onClick #(set-show-modal! true)}
                 (str "Adopt " (:name pet-data)))
              ($ :p (:description pet-data))
              (if (true? show-modal)
               ($ modal
                  ($ :div
                     ($ :h1 "Would you like to adopt " (:name pet-data))
                     ($ :div {:className "buttons"}
                        ($ :button {:onClick #(do
                                                (set-adopted-pet! pet-data)
                                               (navigate "/"))}
                           "Yes")
                        ($ :button {:onClick #(set-show-modal! false)}
                           "No"))))
               nil)))))))



