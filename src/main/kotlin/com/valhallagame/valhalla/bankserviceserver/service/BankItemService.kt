package com.valhallagame.valhalla.bankserviceserver.service

import com.valhallagame.valhalla.bankserviceserver.model.BankItem
import com.valhallagame.valhalla.bankserviceserver.repository.BankItemRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class BankItemService {
    companion object {
        private val logger = LoggerFactory.getLogger(BankItemService::class.java)
    }

    @Autowired
    private lateinit var bankItemRepository: BankItemRepository

    fun deleteBankItemByCharacterName(characterName: String): Int {
        logger.info("Deleting all bank items for {}", characterName)
        return bankItemRepository.deleteBankItemByCharacterName(characterName)
    }

    fun getBankItems(characterName: String): List<BankItem> {
        logger.info("Getting bank items for {}", characterName)
        val bankItems = bankItemRepository.getBankItemsByCharacterName(characterName)
        logger.info("Bank items gotten: {}", bankItems)
        return bankItems
    }

    fun setBankContents(characterName: String, bankItems: List<BankItemWrapper>): List<BankItem> {
        logger.info("Setting bank content for {} to {}", characterName, bankItems)
        bankItemRepository.deleteBankItemByCharacterName(characterName)
        val createdItems = mutableListOf<BankItem>()
        bankItems.forEach {
            createdItems.add(createBankItem(characterName, it.itemName, it.positionX, it.positionY, it.metaData))
        }

        return createdItems
    }

    fun createBankItem(characterName: String, itemName: String, positionX: Int, positionY: Int, metaData: String?): BankItem {
        logger.info("Creating bank item for {} with name {} and position X: {}, Y: {}, Meta Data {}", characterName, itemName, positionX, positionY, metaData)

        deleteBankItemByPosition(characterName, positionX, positionY)

        return bankItemRepository.save(
                BankItem(
                        characterName = characterName,
                        itemName = itemName,
                        positionX = positionX,
                        positionY = positionY,
                        metaData = metaData
                )
        )
    }

    fun deleteBankItemByPosition(characterName: String, positionX: Int, positionY: Int): Int {
        logger.info("Deleting bank item for {} on position X: {}, Y: {}", characterName, positionX, positionY)
        return bankItemRepository.deleteBankItemByPosition(characterName, positionX, positionY)
    }

    data class BankItemWrapper(
        val itemName: String,
        val positionX: Int,
        val positionY: Int,
        val metaData: String?
    )
}