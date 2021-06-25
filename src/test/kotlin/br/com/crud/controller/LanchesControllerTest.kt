package br.com.crud.controller

import br.com.crud.dto.LancheDto
import br.com.crud.entity.Lanche
import br.com.crud.repository.LancheRepository
import br.com.crud.service.LancheServiceImpl
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.micronaut.http.HttpStatus
import io.micronaut.test.extensions.kotest.annotation.MicronautTest
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import java.math.BigDecimal
import java.util.*
import javax.inject.Inject
import javax.validation.ConstraintViolationException

@MicronautTest
class LanchesControllerTest: AnnotationSpec() {

    val service = mockk<LancheServiceImpl>()

    @Inject
    lateinit var lancheController: LancheController

    lateinit var dto: LancheDto
    lateinit var lanche: Lanche
    lateinit var lanche2: Lanche

    @BeforeEach
    fun setUp() {
        dto = LancheDto("Catupiry empanado","Pao, 2 hamburgueres, catupiry empanado e cebola caramelizada"
        , 24.90
        )
        lanche = Lanche(dto.nome,dto.ingredientes,dto.preco, UUID.randomUUID())
        lanche2 = Lanche("X-Tudo","Tudo",20.00, UUID.randomUUID())
    }

        @Test
        fun `deve cadastrar um lanche`() {

            //cenario
            every{service.cadastrar(any())} answers {lanche}
            lancheController = LancheController(service)

            //acao
            val httpResponse = lancheController.cadastraLanche(dto)

            //validacao
            httpResponse.status() shouldBe HttpStatus.CREATED
            httpResponse.body.get() shouldBe lanche

        }

        @Test
        fun `deve mostrar todos os lanches cadastrados`(){
            //cenario
            val listaLanches = listOf(lanche,lanche2)
            every{service.listar()} answers {listaLanches}
            //acao
            val httpResponse = lancheController.mostraCardapio()
            //validacao
            httpResponse.status shouldBe HttpStatus.OK
            httpResponse.body() shouldBe listaLanches
            httpResponse.body().get(1) shouldBe lanche2

        }

        @Test
        fun `deve listar um lanche por id`(){
            //cenario
            every {service.listaPorId(any())} answers{ Optional.of(lanche2)}
            //acao
            val httpResponse = lancheController.mostraLanchePorId(lanche2.id!!)
            // validacao
            httpResponse.status shouldBe HttpStatus.OK
            httpResponse.body.get() shouldBe lanche2
        }

        @Test
        fun `deve retornar erro 404 ao tentar listar um lanche com id invalido`(){
            //cenario
            every {service.listaPorId(any())} answers{ Optional.empty()}
            //acao
            val httpResponse = lancheController.mostraLanchePorId(lanche2.id!!)
            // validacao
            httpResponse.status shouldBe HttpStatus.NOT_FOUND
            httpResponse.body shouldBe Optional.empty()
        }
        @Test
        fun `deve atualizar um lanche`(){
            //cenario
            lancheController = LancheController(service)
            every{service.listaPorId(any())} answers { Optional.of(lanche)}
            every {service.atualizar(any(),any())} answers {lanche}
            //acao
            val httpResponse = lancheController.atualizarLanche(UUID.randomUUID(),dto)
            //validacao
            httpResponse.status shouldBe HttpStatus.OK
            httpResponse.body.get() shouldBe lanche
        }
        @Test
        fun `deve retornar erro 404 ao tentar atualizar um lanche com id invalido`(){
            //cenario
            every{service.listaPorId(any())} answers { Optional.empty()}
            every {service.atualizar(any(),any())} answers {lanche}
            //acao
            val httpResponse = lancheController.atualizarLanche(UUID.randomUUID(),dto)
            //validacao
            httpResponse.status shouldBe HttpStatus.NOT_FOUND
            httpResponse.body shouldBe Optional.empty()
        }

        @Test
        fun `deve deletar um lanche`(){
            //cenario
            every{service.listaPorId(any())} answers { Optional.of(lanche)}
            every {service.deletar(any())} answers {}
            //acao
            val httpResponse = lancheController.deletarLanche(lanche.id!!)
            //validacao
            httpResponse.status shouldBe HttpStatus.OK
            httpResponse.body shouldBe Optional.of("Lanche de id ${lanche.id} deletado")

        }
        @Test
        fun `deve retornar erro 404 ao tentar deletar um lanche com id invalido`(){
            //cenario
            every{service.listaPorId(any())} answers { Optional.empty()}
            every {service.deletar(any())} answers {}
            //acao
            val httpResponse = lancheController.deletarLanche(UUID.randomUUID())
            //validacao
            httpResponse.status shouldBe HttpStatus.NOT_FOUND
            httpResponse.body shouldBe Optional.empty()
        }
}
