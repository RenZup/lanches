package br.com.crud.entity

import br.com.crud.dto.LancheDto
import io.micronaut.core.annotation.Introspected
import java.math.BigDecimal
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Introspected
data class Lanche(
    var nome: String,
    var ingredientes: String,
    var preco: Double,
    val id: UUID? = null
) {

    fun atualiza(dto: LancheDto){
        this.ingredientes = dto.ingredientes
        this.nome = dto.nome
        this.preco = dto.preco
    }

}