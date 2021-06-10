package br.com.crud.model

import java.math.BigDecimal
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
class Lanche(
    @Column(nullable = false)
    @field:NotBlank
    val nome: String,
    @Column(nullable = false)
    @field:NotBlank
    val ingredientes: String,
    @Column(nullable = false)
    @field:NotNull
    val preco: BigDecimal
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    override fun toString(): String {
        return "Lanche(nome='$nome', ingredientes=$ingredientes, preco=$preco, id=$id)"
    }


}