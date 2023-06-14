(ns app.app
  (:require
   [uix.core :as uix :refer [defui $]]
   [app.details :as details :refer [details]]
   [app.search-params :as search-params :refer [search-params]]
   ["react-router-dom" :refer [Link BrowserRouter Routes Route]]
   ["@tanstack/react-query" :refer [QueryClient QueryClientProvider]]
   [uix.dom]))

(def query-client
  (QueryClient.
   {:defaultOptions
    {:queries {:staleTime js/Infinity
               :cacheTime js/Infinity}}}))

(defui app []
  ($ :div
     ($ BrowserRouter
        ($ QueryClientProvider {:client query-client}
         ($ :header
            ($ Link {:to "/"} "Adopt Me!"))
         ;; ($ :h1 "Adopt Me!")
         ($ Routes
            ($ Route {:path "/details/:id"
                      :element ($ details)})
            ($ Route {:path "/"
                      :element ($ search-params)}))))))

;; (defui app []
;;   (let [adopted-pet (uix/use-state nil)]
;;     ($ :div

;;        ($ BrowserRouter
;;           ($ QueryClientProvider {:client query-client}
;;              ($ (.-Provider adopted-pet-context)
;;                 {:value adopted-pet}
;;                 ($ :header
;;                    ($ Link {:to "/"} "Adopt Me!" ))
;;              ($ Routes
;;                 ($ Route {:path "/details/:id" :element [($ details)]})
;;                 ($ Route {:path "/" :element [($ search-parameters)]}))))
;;           )))
;;   )



(defonce root
  (uix.dom/create-root (js/document.getElementById "root")))

(defn render []
  (uix.dom/render-root ($ app) root))

(defn ^:export init []
  (render))
