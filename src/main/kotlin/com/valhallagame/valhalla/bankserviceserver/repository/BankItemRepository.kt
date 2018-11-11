package com.valhallagame.valhalla.bankserviceserver.repository

import com.valhallagame.valhalla.bankserviceserver.model.BankItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import javax.transaction.Transactional

interface BankItemRepository: JpaRepository<BankItem, Long> {
    @Modifying
    @Transactional
    fun deleteBankItemByCharacterName(characterName: String): Long
}