package com.imooc.lib_base.event


/**
 * FileName: MessageEvent
 * Founder: LiuGuiLin
 * Profile: 事件对象
 */
class MessageEvent(val type: Int) {
    var stringValue: String = ""
    var intValue: Int = 0
    var booleanValue: Boolean = false
}