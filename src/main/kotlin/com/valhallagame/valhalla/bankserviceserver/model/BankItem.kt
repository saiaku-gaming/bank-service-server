package com.valhallagame.valhalla.bankserviceserver.model

import javax.persistence.*

@Entity
@Table(name = "bank_item")
data class BankItem(
        @Id
        @SequenceGenerator(name = "bank_item_bank_item_id_seq", sequenceName = "bank_item_bank_item_id_seq", allocationSize = 1)
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bank_item_bank_item_id_seq")
        @Column(name = "bank_item_id")
        val id: Long? = null,

        @Column(name = "character_name")
        val characterName: String,

        @Column(name = "item_name")
        val itemName: String,

        @Column(name = "position_x")
        val positionX: Int,

        @Column(name = "position_y")
        val positionY: Int,

        @Column(name = "meta_data")
        val metaData: String?
)