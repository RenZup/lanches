package br.com.crud.service

import br.com.crud.dto.LancheDto
import br.com.crud.entity.Lanche
import java.util.*
import javax.inject.Singleton
@Singleton
interface LancheService {
    fun cadastrar(lanche: LancheDto): Lanche
    fun listar(): List<Lanche>
    fun atualizar(form: LancheDto, lanche: Lanche): Lanche
    fun listaPorId(id:Long): Optional<Lanche>
    fun deletar(id: Long)
}