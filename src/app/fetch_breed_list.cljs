(ns app.fetch-breed-list
  (:require
   [shadow.cljs.modern :refer [js-await]]))

(defn fetch-breed-list [context]
;;(js/console.log "this is fetch-breed-list context " context)
  (let [query (.-queryKey context)
        animal (aget query 1)]
    ;;(js/console.log "animal from context " context)
    (if (empty? animal)
      []
      (js-await [result (js/fetch (str "http://pets-v2.dev-apis.com/breeds?animal=" animal))]
              ;; (js/console.log "animal result from fetch " (.json result))
              (if (not (.-ok result))
               (js/throw (js/Error. (str "breeds " animal " fetch not ok")))
               (.json result))))
    ))
