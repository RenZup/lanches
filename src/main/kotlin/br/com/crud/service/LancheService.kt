package br.com.crud.service

import br.com.crud.dto.LancheDto
import br.com.crud.model.Lanche
import java.util.*
import javax.validation.Valid

interface LancheService {
    fun cadastrar(@Valid lanche: LancheDto): Lanche
    fun listar(): List<Lanche>
    fun atualizar(form: LancheDto, lanche: Lanche): Lanche
    fun listaPorId(id:Long): Optional<Lanche>
    fun deletar(id: Long)
}