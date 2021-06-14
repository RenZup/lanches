package br.com.crud.service

import br.com.crud.dto.LancheDto
import br.com.crud.entity.Lanche
import br.com.crud.repository.LancheRepository
import java.util.*
import javax.inject.Singleton

@Singleton
class LancheServiceImpl(val repository: LancheRepository): LancheService{
    override fun cadastrar(lanche: LancheDto): Lanche {

        return repository.save(lanche.toModel())
    }
    override fun listar(): List<Lanche> {
        return repository.findAll().toList()
    }
    override fun atualizar(form:LancheDto, lanche:Lanche): Lanche {
        lanche.atualiza(form)
        return repository.update(lanche)
    }

    override fun listaPorId(id:Long): Optional<Lanche> {
        return repository.findById(id)
    }

    override fun deletar(id: Long) {
        return repository.deleteById(id)
    }

}