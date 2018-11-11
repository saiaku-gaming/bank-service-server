package com.valhallagame.valhalla.bankserviceserver.rabbitmq

import com.valhallagame.common.rabbitmq.NotificationMessage
import com.valhallagame.valhalla.bankserviceserver.service.BankItemService
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class BankConsumer
    @Autowired
    constructor(
            private val bankItemService: BankItemService
    ){
    @RabbitListener(queues = ["#{bankCharacterDeleteQueue.name}"])
    fun receivedCharacterDeleteNotification(notificationMessage: NotificationMessage) {
        val characterName = notificationMessage.data["characterName"] as String
        bankItemService.deleteBankItemByCharacterName(characterName)
    }
}