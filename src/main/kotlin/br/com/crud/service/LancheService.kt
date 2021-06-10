package br.com.crud.service

import br.com.crud.dto.LancheDto
import br.com.crud.model.Lanche
import br.com.crud.repository.LancheRepository
import io.micronaut.validation.Validated
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import javax.validation.Valid

@Singleton
@Validated
class LancheService(@Inject val repository: LancheRepository){
    fun cadastrar(@Valid lanche: LancheDto): Lanche {

        return repository.save(lanche.toModel())
    }
    fun listar(): List<Lanche> {
        return repository.findAll().toList()
    }
    fun atualizar(form:LancheDto, lanche:Lanche): Lanche {
        lanche.atualiza(form)
        return repository.update(lanche)
    }

    fun listaPorId(id:Long): Optional<Lanche> {
        return repository.findById(id)
    }

    fun deletar(id: Long) {
        return repository.deleteById(id)
    }

}