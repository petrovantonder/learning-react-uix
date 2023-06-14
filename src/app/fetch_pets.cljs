(ns app.fetch-pets
  (:require
   [shadow.cljs.modern :refer [js-await]]))

(defn fetch-pet [context]
  (let [query (.-queryKey context)
        id (aget query 1)
        ;; result (js/fetch (str "http://pets-v2.dev-apis.com/pets?id=" id))
        ]
    (js-await [fetch-result (js/fetch (str "http://pets-v2.dev-apis.com/pets?id=" id))]
              (if (not (.-ok fetch-result))
                (js/throw (js/Error. (str "details/" id " fetch not ok")))
                (.json fetch-result)))))
