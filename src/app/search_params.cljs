(ns app.search-params
  (:require
   [uix.core :as uix :refer [defui $]]
   [app.results :as results :refer [results]]
   [app.use-breed-list :as use-breed-list :refer [use-breed-list]]
   [app.adopted-pet-context :as adopted-pet-context :refer [adopted-pet-context]]
   ["@tanstack/react-query" :refer [useQuery]]
   [app.fetch-search :refer [fetch-search]]
   [uix.dom]))

(def ANIMALS ["bird", "cat", "dog", "rabbit", "reptile"])

(defui search-params []
  (let [[request-params set-request-params!] (uix/use-state
                                              (clj->js {:location ""
                                                        :animal   ""
                                                        :breed    ""}))
        [adopted-pet] (js->clj (uix/use-context adopted-pet-context))
        [animal set-animal!] (uix/use-state "")
        breeds (first (use-breed-list animal))
        result (useQuery (clj->js ["search-params" request-params]) fetch-search)
        pets (if (.-isLoading result)
               []
               (js->clj (.-pets (.-data result)) :keywordize-keys true))]
    ($ :div {:className "search-params"}
       ($ :form {:onSubmit #(do
                              (.preventDefault %)
                              (let [form-data (js/FormData. (.-target %))
                                    obj {:location (or (.get form-data "location") "")
                                         :animal   (or (.get form-data "animal") "")
                                         :breed    (or (.get form-data "breed") "")}]
                                (set-request-params! obj)))}
          (if (empty? adopted-pet)
            nil
            ($ :div {:className "pet image-container"}
               ($ :img {:src (get (:images adopted-pet) 0)
                        :alt (:name adopted-pet)})))
          ($ :label {:htmlFor "location"}
             "Location"
             ($ :input {:id "location"
                        :name "location"
                        :placeholder "Location"
                        }))
          ($ :label {:htmlFor "animal"}
             "Animal"
             ($ :select {:id "animal"
                         :name "animal"
                         :onChange #(do
                                      (set-animal! (.. % -target -value)))}
                ($ :option {:key "a"}
                   nil)
                (map #($ :option {:key %} %) ANIMALS)))
          ($ :label {:htmlFor "breed"}
             "Breed"
             ($ :select {:disabled (empty? breeds)
                         :id "breed"
                         :name "breed"}
                ($ :option {:key "b"}
                   nil)
                (map #($ :option {:key %} %) breeds)))
          ($ :button "Submit"))
       ($ results {:pets pets})
       )))




