(ns app.use-breed-list
  (:require
   [uix.core :as uix :refer [defui $]]
   [uix.dom]))

(defui use-breed-list [animal]
  (let [[breed-list set-breed-list!] (uix/use-state [])
        [status set-status!] (use/use-state "")]))


