package br.com.crud.controller

import br.com.crud.dto.LancheDto
import br.com.crud.entity.Lanche
import br.com.crud.repository.LancheRepository
import io.micronaut.http.HttpStatus
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import java.math.BigDecimal
import javax.inject.Inject
import javax.validation.ConstraintViolationException

@MicronautTest
class LanchesControllerTest {

    @Inject
    lateinit var repository: LancheRepository

    @Inject
    lateinit var lancheController: LancheController

    @BeforeEach
    fun limpeza() {
        repository.deleteAll()
    }

    @Nested
    inner class Cadastro {
        @Test
        fun `deve cadastrar um lanche`() {
            //cenario
            val nome = "Catupiry empanado"
            val ingredientes = "Pao, 2 hamburgueres, catupiry empanado e cebola caramelizada"
            val preco = BigDecimal(25.90)

            val dto = LancheDto(nome, ingredientes, preco)
            //acao
            val httpResponse = lancheController.cadastraLanche(dto)
            //validacao
            val banco = repository.findAll()
            assertNotNull(httpResponse)
            assertEquals(HttpStatus.CREATED, httpResponse.status)
            assertTrue(banco.isNotEmpty())
            assertEquals(1, banco.size)
            assertEquals(nome, banco[0].nome)
            assertEquals(preco, banco[0].preco)
            assertEquals(ingredientes, banco[0].ingredientes)
        }

        @Test
        fun `nao deve cadastrar um lanche com campos vazios`() {
            //cenario
            val nome = ""
            val ingredientes = ""
            val preco = BigDecimal(25.90)

            val dto = LancheDto(nome, ingredientes, preco)
            //acao


            val exception = assertThrows<ConstraintViolationException> {
                lancheController.cadastraLanche(dto)
            }
            //validacao

            assertTrue(exception.constraintViolations.size == 2)
            assertTrue(repository.findAll().isEmpty())


        }
    }
    @Nested
    inner class Listagem(){
        @Test
        fun `deve listar todos os lanches`(){
            //cenario
            repository.save(LancheDto("Lanche 1", "Pao hamburguer e queijo", BigDecimal(9.99)).toModel())
            repository.save(LancheDto("Lanche 2","Pao hamburguer presunto catupiry e bacon",BigDecimal(15.90)).toModel())
            //acao
            val cardapio = lancheController.mostraCardapio()
            //validacao
            assertNotNull(cardapio)
            assertTrue(cardapio.body.get().size == 2)
            assertEquals(HttpStatus.OK,cardapio.status)


        }
        @Test
        fun `deve listar por id`(){
            //cenario
            val lanche1 = repository.save(LancheDto("Lanche 1", "Pao hamburguer e queijo", BigDecimal(9.99)).toModel())
            val lanche2 = repository.save(LancheDto("Lanche 2", "Pao hamburguer presunto catupiry e bacon", BigDecimal(15.90),).toModel())
            //acao
            val mostraLanchePorId = lancheController.mostraLanchePorId(lanche2.id!!)
            //validacao
            assertEquals(mostraLanchePorId.body.get(),lanche2)
            assertEquals(HttpStatus.OK,mostraLanchePorId.status)

            with(mostraLanchePorId.body.get()){
                assertEquals(lanche2.nome,nome)
                assertEquals(lanche2.ingredientes,ingredientes)
                assertEquals(lanche2.preco,preco)
            }


        }
        @Test
        fun `deve retornar status 404 quando nao encontrar o id`(){
            //cenario
            //acao
            val mostraLanchePorId = lancheController.mostraLanchePorId(1)
            //validacao
            assertEquals(HttpStatus.NOT_FOUND,mostraLanchePorId.status)

        }

    }
    @Nested
    inner class Atualizacao(){
        @Test
        fun `deve atualizar um lanche`(){
            //cenario
            val lanche = repository.save(Lanche("X-Tudo", "Tudo", BigDecimal(20)))
            //acao
            val lancheAtualizado = LancheDto("Tudo","Tudo exceto X",BigDecimal(19.99))
            val atualizarLanche = lancheController.atualizarLanche(lanche.id!!, lancheAtualizado)
            //validacao
            assertEquals(HttpStatus.OK,atualizarLanche.status)
            with(atualizarLanche.body.get()){
                assertEquals("Tudo",nome)
                assertEquals("Tudo exceto X",ingredientes)
                assertEquals(BigDecimal(19.99),preco)
            }
        }
        @Test
        fun `deve retornar status 404 quando lanche nao existir`(){
            //cenario
            //acao
            val atualizarLanche =
                lancheController.atualizarLanche(1, LancheDto("Atualizado", "Atualizado", BigDecimal(1)))
            //validacao
            assertEquals(HttpStatus.NOT_FOUND,atualizarLanche.status)
        }
        @Test
        fun `nao deve atualizar um lanche com dados invalidos`(){
            //cenario
            val lanche = repository.save(Lanche("X-Tudo", "Tudo", BigDecimal(15.9)))
            val lancheDtoErrado = LancheDto("","", BigDecimal(0))
            //acao
            assertThrows<ConstraintViolationException> {
                val atualizarLanche = lancheController.atualizarLanche(lanche.id!!, lancheDtoErrado)
            }
            //validacao
        }

    }
    @Nested
    inner class Deletar(){
        @Test
        fun `deve deletar um lanche`(){
            //cenario
            val lanche = repository.save(Lanche("X-Tudo","Tudo", BigDecimal(15.9)))
            //acao
            val deletarLanche = lancheController.deletarLanche(lanche.id!!)
            val findAll = repository.findAll()
            //validacao
            assertEquals(HttpStatus.OK,deletarLanche.status)
            assertTrue(findAll.isEmpty())
        }
        @Test
        fun `deve retornar status 404 quando nao encontrar o id`(){
            //cenario
            //acao
            val deletarLanche = lancheController.deletarLanche(1)
            //validacao
            assertEquals(HttpStatus.NOT_FOUND,deletarLanche.status)
        }
    }

}
