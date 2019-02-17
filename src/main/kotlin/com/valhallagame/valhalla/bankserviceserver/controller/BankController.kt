package com.valhallagame.valhalla.bankserviceserver.controller

import com.fasterxml.jackson.databind.JsonNode
import com.valhallagame.bankserviceclient.message.AddBankItemParameter
import com.valhallagame.bankserviceclient.message.DeleteBankItemParameter
import com.valhallagame.bankserviceclient.message.GetBankItemsParameter
import com.valhallagame.bankserviceclient.message.SetBankItemContentsParameter
import com.valhallagame.common.JS
import com.valhallagame.valhalla.bankserviceserver.service.BankItemService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import javax.validation.Valid

@Controller
@RequestMapping(path = ["/v1/bank"])
class BankController {
    companion object {
        private val logger = LoggerFactory.getLogger(BankController::class.java)
    }

    @Autowired
    private lateinit var bankItemService: BankItemService

    @PostMapping("/get-bank-items")
    @ResponseBody
    fun getBankItems(@Valid @RequestBody input: GetBankItemsParameter): ResponseEntity<JsonNode> {
        logger.info("Get Bank Item called with {}", input)
        return JS.message(HttpStatus.OK, bankItemService.getBankItems(input.characterName))
    }

    @PostMapping("/add-bank-item")
    @ResponseBody
    fun addBankItem(@Valid @RequestBody input: AddBankItemParameter): ResponseEntity<JsonNode> {
        logger.info("Add Bank Item called with {}", input)
        return JS.message(HttpStatus.OK, bankItemService.createBankItem(input.characterName, input.itemName, input.positionX, input.positionY))
    }

    @PostMapping("delete-bank-item")
    @ResponseBody
    fun deleteBankItem(@Valid @RequestBody input: DeleteBankItemParameter): ResponseEntity<JsonNode> {
        logger.info("Delete Bank Item called with {}", input)
        return JS.message(HttpStatus.OK, "Deleted ${bankItemService.deleteBankItemByPosition(input.characterName, input.positionX, input.positionY)} items")
    }

    @PostMapping("/set-bank-contents")
    @ResponseBody
    fun setBankContents(@Valid @RequestBody input: SetBankItemContentsParameter): ResponseEntity<JsonNode> {
        logger.info("Set Bank Item called with {}", input)
        return JS.message(HttpStatus.OK, bankItemService.setBankContents(input.characterName, input.items.map {
            BankItemService.BankItemWrapper(it.itemName, it.positionX, it.positionY)
        }))
    }
}