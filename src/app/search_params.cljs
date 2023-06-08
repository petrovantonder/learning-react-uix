(ns app.search-params
  (:require
   [uix.core :as uix :refer [defui $]]
   [app.pet :as pet :refer [pet]]
   [uix.dom]))

(def ANIMALS ["bird", "cat", "dog", "rabbit", "reptile"])

(defn request-pets [set-pets! animal location breed]
  (println "request-pets" animal)
  (let [result (js/fetch (str "http://pets-v2.dev-apis.com/pets?animal=" animal "&location=" location "&breed=" breed))]
    (-> result
        (.then (fn [response]
                 ;;(js/console.log response)
                 (.json response)))
        (.then (fn [json-data]
                 ;; (println "our data" json-data)
                 ;;(js/console.log json-data)
                 (set-pets! (:pets (js->clj json-data :keywordize-keys true))))))))

(defui search-params []
  (let [[location set-location!] (uix/use-state "")
        [animal set-animal!] (uix/use-state "")
        [breed set-breed!] (uix/use-state "")
        [pets set-pets!] (uix/use-state [])
        breeds []]
    ;;(request-pets set-pets! animal location breed)
    (uix/use-effect
     (fn []
       (request-pets set-pets! animal location breed))
     [animal location breed])
    ($ :div {:className "search-params"}
       ($ :form {:onSubmit #(do
                              (.. % -preventDefault)
                              (request-pets set-pets! animal location breed))}
          ($ :label {:htmlFor "location"}
             "Location"
             ($ :input {:id "location"
                        :value location
                        :placeholder "Location"
                        :onChange #(set-location! (.. % -target -value))}))
          ($ :label {:htmlFor "animal"}
             "Animal"
             ($ :select {:id "animal"
                         :value animal
                         :onChange #(do
                                      (set-animal! (.. % -target -value))
                                      (set-breed! ""))}
                ($ :option {:key "a"}
                   nil)
                (map #($ :option {:key %} %) ANIMALS)))
          ($ :label {:htmlFor "breed"}
             "Breed"
             ($ :select {:disabled (empty? breeds)
                         :id "breed"
                         :value breed
                         :onChange #(set-breed! (.. % -target -value))}
                ($ :option {:key "b"}
                   nil)
                (map #($ :option {:key %} %) breeds)))
          ($ :button "Submit"))
       ($ :form
          (map #($ pet {:name (:name %)
                        :animal (:animal %)
                        :breed (:breed %)
                        :key (:id %)}) pets)))))




