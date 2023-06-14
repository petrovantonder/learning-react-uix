(ns app.pet
  (:require
   [uix.core :as uix :refer [defui $]]
   ["react-router-dom" :refer [Link]]
   [uix.dom]))

(defui pet [{:keys [name animal breed images location id] :as _props}]
  (let [hero (if (empty? images)
               (str "http://pets-images.dev-apis.com/pets/none.jpg")
               (get images 0))]
    ($ Link {:to        (str "/details/" id)
             :className "pet"}
       ($ :div {:className "image-container"}
          ($ :img {:src hero
                   :alt name}))
       ($ :div {:className "info"}
          ($ :h1 name)
          ($ :h2 (str animal " - " breed " - " location))))))


