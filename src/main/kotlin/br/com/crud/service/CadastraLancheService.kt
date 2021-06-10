package br.com.crud.service

import br.com.crud.dto.CriaLancheDto
import br.com.crud.model.Lanche
import br.com.crud.repository.LancheRepository
import io.micronaut.validation.Validated
import javax.inject.Inject
import javax.inject.Singleton
import javax.validation.Valid

@Singleton
@Validated
class CadastraLancheService(@Inject val repository: LancheRepository){
    fun cadastrar(@Valid lanche: CriaLancheDto): Lanche {

        return repository.save(lanche.toModel())
    }

}