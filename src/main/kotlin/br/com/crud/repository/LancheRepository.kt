package br.com.crud.repository

import br.com.crud.entity.Lanche
import java.util.*
import javax.inject.Singleton

@Singleton
interface LancheRepository{
    fun save(lanche: Lanche): Lanche
    fun findAll():List<Lanche>
    fun findById(id:Long): Optional<Lanche>
    fun deleteById(id:Long)
    fun update(lanche:Lanche): Lanche
}