(ns app.modal
  (:require
   [uix.core :as uix :refer [defui $]]
   [uix.dom :as dom]))

(defui modal [{:keys [children]}]
(js/console.log "this is the children " children)
  (let [el-ref (uix/use-ref nil)]
    (when (nil? @el-ref)
      (reset! el-ref (js/document.createElement "div")))
    (uix/use-effect
     (fn []
       (let [modal-root (js/document.getElementById "modal")]
         (.appendChild modal-root @el-ref)
         #(.removeChild modal-root @el-ref)
         ))
     [])
    (dom/create-portal
     ($ :div children) @el-ref)))




