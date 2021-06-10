package br.com.crud.repository

import br.com.crud.model.Lanche
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository

@Repository
interface LancheRepository: JpaRepository<Lanche,Long>{
}