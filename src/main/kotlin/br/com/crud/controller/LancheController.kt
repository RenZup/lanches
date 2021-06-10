package br.com.crud.controller

import br.com.crud.dto.LancheDto
import br.com.crud.model.Lanche
import br.com.crud.service.LancheService
import io.micronaut.http.HttpResponse
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.*
import io.micronaut.validation.Validated

import javax.inject.Inject
import javax.transaction.Transactional
import javax.validation.Valid

@Controller("/lanches")
@Validated
class LancheController(@Inject val service: LancheService) {

    @Post
    @Transactional
    fun cadastraLanche(@Body @Valid form: LancheDto): Lanche {
        return service.cadastrar(form)
    }
    @Get
    fun mostraCardapio(): List<Lanche> {
        return service.listar()
    }

    @Get("/{id}")
    fun mostraLanchePorId(@PathVariable id: Long): MutableHttpResponse<Lanche>? {
        val possivelLanche = service.listaPorId(id)
        if(possivelLanche.isEmpty){
            return HttpResponse.notFound()
        }
        return HttpResponse.ok(possivelLanche.get())

    }

    @Put("/{id}")
    @Transactional
    fun atualizarLanche(@PathVariable id:Long, @Body @Valid form: LancheDto):MutableHttpResponse<Lanche>?{
        val possivelLanche = service.listaPorId(id)
        if(possivelLanche.isEmpty) return HttpResponse.notFound()

        return HttpResponse.ok(service.atualizar(form,possivelLanche.get()))
    }

    @Delete("/{id}")
    @Transactional
    fun deletarLanche(@PathVariable id:Long): MutableHttpResponse<String> {
        val possivelLanche = service.listaPorId(id)
        if(possivelLanche.isEmpty) return HttpResponse.notFound()
        service.deletar(id)
        return HttpResponse.ok("Lanche de id $id deletado")
    }

}