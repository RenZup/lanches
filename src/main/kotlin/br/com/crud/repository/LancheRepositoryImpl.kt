package br.com.crud.repository

import br.com.crud.entity.Lanche
import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.core.cql.SimpleStatement
import java.util.*
import javax.inject.Singleton

@Singleton
class LancheRepositoryImpl(private val cqlSession: CqlSession): LancheRepository {
    override fun save(lanche: Lanche): Lanche {
        cqlSession.execute(
            SimpleStatement
                .newInstance(
                    "INSERT INTO mydata.lanche(id,nome,ingredientes,preco) VALUES(?,?,?,?)",
                    UUID.randomUUID(),
                    lanche.nome,
                    lanche.ingredientes,
                    lanche.preco
                )
        )
        return lanche
    }

    override fun findAll(): List<Lanche> {
        TODO("Not yet implemented")
    }

    override fun findById(id: Long): Optional<Lanche> {
        TODO("Not yet implemented")
    }

    override fun deleteById(id: Long) {
        TODO("Not yet implemented")
    }

    override fun update(lanche: Lanche): Lanche {
        TODO("Not yet implemented")
    }

}