package ui.template

import ui.base.BaseAction

interface TemplateAction : BaseAction {
    fun onStart()
    fun onButtonClick()
}