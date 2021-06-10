package br.com.crud.service

import br.com.crud.model.Lanche
import br.com.crud.repository.LancheRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ListaLancheService(@Inject val repository: LancheRepository) {
    fun listar(): List<Lanche> {
        return repository.findAll().toList()
    }

}