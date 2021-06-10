package br.com.crud.model

import br.com.crud.dto.LancheDto
import java.math.BigDecimal
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
class Lanche(
    @Column(nullable = false)
    @field:NotBlank
    var nome: String,
    @Column(nullable = false)
    @field:NotBlank
    var ingredientes: String,
    @Column(nullable = false)
    @field:NotNull
    var preco: BigDecimal
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    override fun toString(): String {
        return "Lanche(nome='$nome', ingredientes=$ingredientes, preco=$preco, id=$id)"
    }

    fun atualiza(dto: LancheDto){
        this.ingredientes = dto.ingredientes
        this.nome = dto.nome
        this.preco = dto.preco
    }

}