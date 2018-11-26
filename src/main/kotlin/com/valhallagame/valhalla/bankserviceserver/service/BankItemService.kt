package com.valhallagame.valhalla.bankserviceserver.service

import com.valhallagame.valhalla.bankserviceserver.model.BankItem
import com.valhallagame.valhalla.bankserviceserver.repository.BankItemRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class BankItemService {
    @Autowired
    private lateinit var bankItemRepository: BankItemRepository

    fun deleteBankItemByCharacterName(characterName: String): Int
            = bankItemRepository.deleteBankItemByCharacterName(characterName)

    fun getBankItems(characterName: String): List<BankItem>
            = bankItemRepository.getBankItemsByCharacterName(characterName)

    fun setBankContents(characterName: String, bankItems: List<BankItemWrapper>): List<BankItem> {
        bankItemRepository.deleteBankItemByCharacterName(characterName)
        val createdItems = mutableListOf<BankItem>()
        bankItems.forEach {
            createdItems.add(createBankItem(characterName, it.itemName, it.positionX, it.positionY))
        }

        return createdItems
    }

    fun createBankItem(characterName: String, itemName: String, positionX: Int, positionY: Int): BankItem
            = bankItemRepository.save(
                BankItem(
                    characterName = characterName,
                    itemName = itemName,
                    positionX = positionX,
                    positionY = positionY
                )
            )

    fun deleteBankItemByPosition(characterName: String, positionX: Int, positionY: Int): Int
            = bankItemRepository.deleteBankItemByPosition(characterName, positionX, positionY)

    data class BankItemWrapper(
        val itemName: String,
        val positionX: Int,
        val positionY: Int
    )
}