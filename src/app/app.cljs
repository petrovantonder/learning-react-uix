(ns app.app
  (:require
   [uix.core :as uix :refer [defui $]]
   [app.details :as details :refer [details]]
   [app.search-params :as search-params :refer [search-params]]
   [app.adopted-pet-context :as adopted-pet-context :refer [adopted-pet-context]]
   ["react-router-dom" :refer [Link BrowserRouter Routes Route]]
   ["@tanstack/react-query" :refer [QueryClient QueryClientProvider]]
   [uix.dom]))

(def query-client
  (QueryClient.
   {:defaultOptions
    {:queries {:staleTime js/Infinity
               :cacheTime js/Infinity}}}))

(defui app []
  (let [adopted-pet (uix/use-state nil)]
    ($ :div
       ($ BrowserRouter
          ($ (.-Provider adopted-pet-context)
             {:value adopted-pet}
             ($ QueryClientProvider {:client query-client}
                ($ :header
                   ($ Link {:to "/"} "Adopt Me!"))
                ;; ($ :h1 "Adopt Me!")
                ($ Routes
                   ($ Route {:path "/details/:id"
                             :element ($ details)})
                   ($ Route {:path "/"
                             :element ($ search-params)}))))))))

(defonce root
  (uix.dom/create-root (js/document.getElementById "root")))

(defn render []
  (uix.dom/render-root ($ app) root))

(defn ^:export init []
  (render))
