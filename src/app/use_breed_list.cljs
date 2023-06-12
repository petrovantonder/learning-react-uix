(ns app.use-breed-list
  (:require
   [uix.core :as uix]
   [uix.dom]))

(defn request-breed-list [animal set-breed-list! set-status! set-local-cache! local-cache]
  (set-status! "loading")
  (let [result (js/fetch (str "http://pets-v2.dev-apis.com/breeds?animal=" animal))]
    (-> result
        (.then (fn [response]
                 (js/console.log response)
                 (.json response)))
        (.then (fn [json]
                 ;; (println "our data" json-data)
                 (js/console.log json)
                 (let [json-data (js->clj json :keywordize-keys true)]
                   (set-breed-list! (or (:breeds json-data) []))
                   (set-local-cache! (assoc local-cache animal (:breeds json-data)))
                   (set-status! "loaded")))))))

(defn use-breed-list [animal]
  (let [[breed-list set-breed-list!]   (uix/use-state [])
        [status set-status!]           (uix/use-state "unloaded")
        [local-cache set-local-cache!] (uix/use-state {})]
    (uix/use-effect
     (fn []
       (cond
         (empty? animal) (set-breed-list! [])
         (get local-cache animal) (set-breed-list! (get local-cache animal))
         :else (request-breed-list animal set-breed-list! set-status! set-local-cache! local-cache)))
     [animal])
    [breed-list status]))


