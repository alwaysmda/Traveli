package ui.template

import main.ApplicationClass
import ui.base.BaseEvent

sealed class TemplateEvents : BaseEvent() {
    class Rebind(val app: ApplicationClass) : TemplateEvents()
    class Snack(val message: String) : TemplateEvents()
}