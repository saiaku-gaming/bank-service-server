package com.valhallagame.valhalla.bankserviceserver.repository

import com.valhallagame.valhalla.bankserviceserver.model.BankItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import javax.transaction.Transactional

interface BankItemRepository: JpaRepository<BankItem, Long> {
    @Modifying
    @Transactional
    fun deleteBankItemByCharacterName(characterName: String): Int

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM bank_item WHERE character_name = :characterName AND position_x = :positionX AND position_y = :positionY", nativeQuery = true)
    fun deleteBankItemByPosition(@Param("characterName") characterName: String, @Param("positionX") positionX: Int, @Param("positionY") positionY: Int): Int

    fun getBankItemsByCharacterName(characterName: String): List<BankItem>
}