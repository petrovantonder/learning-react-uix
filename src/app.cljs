(ns app
  (:require
   [cljs.spec.alpha :as s]
   [clojure.edn :as edn]
   [uix.core :as uix :refer [defui $]]
   [uix.dom]))

(defonce root
  (uix.dom/create-root (js/document.getElementById "root")))

(defn render []
  (uix.dom/render-root ($ app) root))

(defn ^:export init []
  (render))
