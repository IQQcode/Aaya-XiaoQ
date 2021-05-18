package com.imooc.module_chat.bean

/**
 * @Author: iqqcode
 * @Date: 2021-05-18 18:37
 * @Description:
 */


/**
 *
 * @property message String - 消息的内容
 * @property state Int - 消息的状态（是接收还是发送）
 * @constructor
 */
data class MessageBean(var message: String, var state: Int) {

    constructor() : this("你好小Q", 2)

    companion object {
        const val SEND_TO = 1 // 发送的消息
        const val RECEIVE_FROM = 2 // 接受的消息
    }
}