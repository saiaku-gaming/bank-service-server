package com.valhallagame.valhalla.bankserviceserver.service

import com.valhallagame.valhalla.bankserviceserver.repository.BankItemRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class BankItemService {
    @Autowired
    private lateinit var bankItemRepository: BankItemRepository

    fun deleteBankItemByCharacterName(characterName: String): Long = bankItemRepository.deleteBankItemByCharacterName(characterName)
}