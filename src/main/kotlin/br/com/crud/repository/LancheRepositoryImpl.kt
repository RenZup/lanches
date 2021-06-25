package br.com.crud.repository

import br.com.crud.entity.Lanche
import com.datastax.oss.driver.api.core.CqlIdentifier
import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.core.cql.Row
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
        val result = cqlSession.execute(
            SimpleStatement
                .newInstance(
                    "SELECT * FROM mydata.lanche"
                )
        )

        val response:MutableList<Lanche> = ArrayList<Lanche>()
        for (row: Row in result){
            val id:UUID = row.getUuid("id") ?: UUID.randomUUID()
            val nome:String =row.getString("nome") ?: ""
            val ingredientes:String =row.getString("ingredientes") ?: ""
            val preco:Double =row.getDouble("preco")
            response.add(Lanche(nome = nome,ingredientes= ingredientes,id = id,preco = preco))
        }
        return response
    }

    override fun findById(id: UUID): Optional<Lanche> {
        val result = cqlSession.execute(
            SimpleStatement
                .newInstance(
                    "SELECT * FROM mydata.lanche WHERE id = ?", id
                )
        )

       val response:MutableList<Optional<Lanche>> = ArrayList<Optional<Lanche>>()
        for (row: Row in result){
            val uuid:UUID = row.getUuid("id") ?: UUID.randomUUID()
            val nome:String =row.getString("nome") ?: ""
            val ingredientes:String =row.getString("ingredientes") ?: ""
            val preco:Double =row.getDouble("preco")
            response.add(Optional.of(Lanche(nome = nome,ingredientes= ingredientes,id = uuid,preco = preco)))
        }
        if(response.isEmpty()){
            return Optional.empty()
        }
        return response.get(0)
    }

    override fun deleteById(id: UUID) {
            cqlSession.execute(
                SimpleStatement
                    .newInstance(
                        "DELETE FROM mydata.lanche WHERE id = ?", id
                    )
            )
    }

    override fun update(lanche: Lanche): Lanche {
        cqlSession.execute(
            SimpleStatement
                .newInstance("UPDATE mydata.lanche SET " +
                        "nome = ?" +
                        ",ingredientes = ?" +
                        ",preco = ?" +
                        "WHERE id = ? IF EXISTS",lanche.nome,lanche.ingredientes,lanche.preco,lanche.id)
        )
        return lanche
    }

}