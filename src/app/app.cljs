(ns app.app
  (:require
   [cljs.spec.alpha :as s]
   [clojure.edn :as edn]
   [uix.core :as uix :refer [defui $]]
   [app.pet :as pet :refer [pet]]
   [app.search-params :as search-params :refer [search-params]]
   [uix.dom]))

(def pet-props
  {:dog {:name "Lizzy"
         :breed "Border Collie"
         :animal "Dog"}
   :cat {:name "Tom"
         :breed "Pavement Special"
         :animal "Cat"}
   :rabbit {:name "Robbie"
            :breed "Vlakhaas"
            :animal "Hare"}})

(defui app []
  ($ :div
     ($ :h1 "Adopt Me!")
     ($ search-params {:location "Seatle, WA"})
     ;; ($ pet (:dog pet-props))
     ;; ($ pet (:cat pet-props))
     ;; ($ pet (:rabbit pet-props))
     ))

(defonce root
  (uix.dom/create-root (js/document.getElementById "root")))

(defn render []
  (uix.dom/render-root ($ app) root))

(defn ^:export init []
  (render))
