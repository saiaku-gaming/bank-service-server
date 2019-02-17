package com.valhallagame.valhalla.bankserviceserver.rabbitmq

import com.valhallagame.common.rabbitmq.NotificationMessage
import com.valhallagame.valhalla.bankserviceserver.service.BankItemService
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class BankConsumer
    @Autowired
    constructor(
            private val bankItemService: BankItemService
    ){

    companion object {
        private val logger = LoggerFactory.getLogger(BankConsumer::class.java)
    }

    @RabbitListener(queues = ["#{bankCharacterDeleteQueue.name}"])
    fun receivedCharacterDeleteNotification(notificationMessage: NotificationMessage) {
        logger.info("Received Character Delete Notification: {}", notificationMessage)

        val characterName = notificationMessage.data["characterName"] as String
        bankItemService.deleteBankItemByCharacterName(characterName)
    }
}