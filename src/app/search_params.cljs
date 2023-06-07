(ns app.search-params
  (:require
   [uix.core :as uix :refer [defui $]]
   [uix.dom]))

(defui search-params [{:keys [location]}]
  (let [[location set-location!] (uix/use-state "")]
    ($ :div {:className "location"}
       ($ :form
          ($ :label {:htmlFor location}
             "Location"
             ($ :input {:id "location"
                        :value location
                        :placeholder "Location"
                        :onChange #(set-location! (.. % -target -value))}))
          ($ :button.button "Submit")))))

