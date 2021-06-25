package br.com.crud.dto

import br.com.crud.entity.Lanche
import io.micronaut.core.annotation.Introspected
import java.math.BigDecimal
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Introspected
data class LancheDto(
    @field:NotBlank
    val nome: String,
    @field:NotBlank
    val ingredientes: String,
    @field:NotNull
    val preco: Double
) {
fun toModel(): Lanche{
    return Lanche(nome,ingredientes,preco)
}
}
