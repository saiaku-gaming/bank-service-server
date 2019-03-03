package com.valhallagame.valhalla.bankserviceserver.rabbitmq

import com.valhallagame.common.rabbitmq.NotificationMessage
import com.valhallagame.valhalla.bankserviceserver.service.BankItemService
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class BankConsumer
    @Autowired
    constructor(
            private val bankItemService: BankItemService
    ){

    companion object {
        private val logger = LoggerFactory.getLogger(BankConsumer::class.java)
    }

    @Value("\${spring.application.name}")
    private val appName: String? = null

    @RabbitListener(queues = ["#{bankCharacterDeleteQueue.name}"])
    fun receivedCharacterDeleteNotification(notificationMessage: NotificationMessage) {
        MDC.put("service_name", appName)
        MDC.put("request_id", notificationMessage.data["requestId"] as String? ?: UUID.randomUUID().toString())

        logger.info("Received Character Delete Notification: {}", notificationMessage)

        try {
            val characterName = notificationMessage.data["characterName"] as String
            bankItemService.deleteBankItemByCharacterName(characterName)
        } finally {
            MDC.clear()
        }
    }
}