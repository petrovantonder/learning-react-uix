(ns app.use-breed-list
  (:require
   ["@tanstack/react-query" :refer [useQuery]]
   [app.fetch-breed-list :as fetch-breed-list :refer [fetch-breed-list]]
   [uix.dom]))

;; (defn request-breed-list [animal set-breed-list! set-status! set-local-cache! local-cache]
;;   (set-status! "loading")
;;   (let [result (js/fetch (str "http://pets-v2.dev-apis.com/breeds?animal=" animal))]
;;     (-> result
;;         (.then (fn [response]
;;                  (js/console.log response)
;;                  (.json response)))
;;         (.then (fn [json]
;;                  ;; (println "our data" json-data)
;;                  (js/console.log json)
;;                  (let [json-data (js->clj json :keywordize-keys true)]
;;                    (set-breed-list! (or (:breeds json-data) []))
;;                    (set-local-cache! (assoc local-cache animal (:breeds json-data)))
;;                    (set-status! "loaded")))))))

;; (defn use-breed-list-v1 [animal]
;;   (let [[breed-list set-breed-list!]   (uix/use-state [])
;;         [status set-status!]           (uix/use-state "unloaded")
;;         [local-cache set-local-cache!] (uix/use-state {})]
;;     (uix/use-effect
;;      (fn []
;;        (cond
;;          (empty? animal) (set-breed-list! [])
;;          (get local-cache animal) (set-breed-list! (get local-cache animal))
;;          :else (request-breed-list animal set-breed-list! set-status! set-local-cache! local-cache)))
;;      [animal])
;;     [breed-list status]))

(defn use-breed-list [animal]
  (let [results (useQuery (clj->js ["breeds" animal]) fetch-breed-list)
        data    (js->clj (.-data results) :keywordize-keys true)
        status  (js->clj (.-status results))]
    ;;(js/console.log "use-breed-list results")
    ;; (println "use-breed-list results " (:breeds data))
   (if (empty? animal)
     [[] status]
     [(:breeds data) status])
   )
  )


