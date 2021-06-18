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

    lateinit var lancheDto: LancheDto
    lateinit var lanche: Lanche

    @BeforeEach
    fun setUp() {
        lancheDto = LancheDto("Catupiry empanado","Pao, 2 hamburgueres, catupiry empanado e cebola caramelizada"
        , BigDecimal(24.90)
        )
        lanche = lancheDto.toModel()
    }

        @Test
        fun `deve cadastrar um lanche`() {

            //cenario
            every{service.cadastrar(any())} answers {lanche}
            lancheController = LancheController(service)

            //acao
            val httpResponse = lancheController.cadastraLanche(lancheDto)

            //validacao
            httpResponse.status() shouldBe HttpStatus.CREATED
            httpResponse.body.get() shouldBe lanche

        }
}
