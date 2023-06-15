(ns app.fetch-search
  (:require
   [shadow.cljs.modern :refer [js-await]]))

(defn fetch-search [context]
  ;; (js/console.log "this is fetch-search context " context)
  (let [query (.-queryKey context)
        {:keys [location animal breed]} (js->clj (aget query 1) :keywordize-keys true)]
    ;; (println "query data for fetch-search" data)
    (js-await [result (js/fetch (str "http://pets-v2.dev-apis.com/pets?animal=" animal "&location=" location "&breed=" breed))]
              ;; (js/console.log "animal result from fetch " result)
              (if (not (.-ok result))
                (js/throw (js/Error. (str "pet search not okay: " animal ", " location ", " breed)))
                (.json result))
              )
    ))
