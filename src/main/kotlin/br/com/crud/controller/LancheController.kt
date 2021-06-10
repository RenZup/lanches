package br.com.crud.controller

import br.com.crud.dto.CriaLancheDto
import br.com.crud.model.Lanche
import br.com.crud.service.CadastraLancheService
import br.com.crud.service.ListaLancheService
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.validation.Validated
import java.net.http.HttpResponse
import javax.inject.Inject
import javax.transaction.Transactional
import javax.validation.Valid

@Controller("/lanches")
@Validated
class LancheController(@Inject val cadastraLancheService: CadastraLancheService,
                       @Inject val listaLancheService: ListaLancheService) {

    @Post
    @Transactional
    fun cadastraLanche(@Body @Valid form: CriaLancheDto): Lanche {
        return cadastraLancheService.cadastrar(form)
    }
    @Get
    fun mostraCardapio(): List<Lanche> {
        return listaLancheService.listar()
    }
}