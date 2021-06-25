package br.com.crud.service

import br.com.crud.dto.LancheDto
import br.com.crud.entity.Lanche
import br.com.crud.repository.LancheRepository
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.micronaut.test.extensions.kotest.annotation.MicronautTest
import io.mockk.every
import io.mockk.mockk
import java.math.BigDecimal
import java.util.*

@MicronautTest
class LancheServiceTest: AnnotationSpec() {

    val repository = mockk<LancheRepository>()
    val service = LancheServiceImpl(repository)

    lateinit var dto: LancheDto
    lateinit var lanche: Lanche
    @BeforeEach
    fun setUp(){
        dto = LancheDto("X-Tudo","Tudo", 21.99)
        lanche = Lanche(dto.nome,dto.ingredientes,dto.preco, UUID.randomUUID())
    }

    @Test
    fun `deve cadastrar um lanche`(){
        //cenario
        every { repository.save(any())} answers {lanche}
        //acao
        val cadastrar: Lanche = service.cadastrar(dto)
        //validacao
        cadastrar shouldBe lanche
    }

    @Test
    fun `deve listar todos os lanches`(){
        //cenario
        val listaVazia= mutableListOf<Lanche>()
        listaVazia.add(lanche)
        every{repository.findAll()} answers {listaVazia.toList() }
        //acao
        val listar = service.listar()
        //validacao
        listar shouldBe listaVazia.toList()
        listar.last() shouldBe lanche

    }
    @Test
    fun `deve atualizar um lanche`(){
        //cenario
        val lanche2 = LancheDto("X","X sem tudo", 1.0)
        every{repository.update(any())} answers {lanche2.toModel()}
        //acao
        val atualizar: Lanche = service.atualizar(lanche2, lanche)
        //validacao
        atualizar shouldBe lanche2.toModel()
    }

    @Test
    fun `deve listar por um id existente`(){
        //cenario
        val possivelLanche: Optional<Lanche> = Optional.of(lanche)
        every{repository.findById(lanche.id!!)} answers {possivelLanche}
        //acao
        val listaPorId = service.listaPorId(lanche.id!!)
        //validacao
        listaPorId shouldBe possivelLanche
        listaPorId.get() shouldBe lanche
    }

    @Test
    fun`deve listar por um id inexistente`(){
        //cenario
        val possivelLanche: Optional<Lanche> = Optional.empty()
        every{repository.findById(any())} answers {possivelLanche}
        //acao
        val listaPorId = service.listaPorId(lanche.id!!)
        //validacao
        listaPorId shouldBe possivelLanche
        listaPorId.isEmpty shouldBe true
    }


    /* a funcao de deletar tem retorno void. Faz sentido testar ela ?*/

}