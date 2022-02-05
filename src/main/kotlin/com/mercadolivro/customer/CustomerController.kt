package com.mercadolivro.customer

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("customers")
class CustomerController (
    val customerService: CustomerService
) {


    @GetMapping
    fun getAll(@RequestParam name: String?, @PageableDefault(page = 0, size = 10) pageable: Pageable): Page<CustomerResponse> {
        return customerService.getAll(name, pageable).map { it.toResponse() }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createCustomer(@RequestBody @Valid request: CustomerRequest) {
        customerService.createCustomer(request.toCostumer())
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') || #id == authentication.principal.id")
    fun findById (@PathVariable id: Int): CustomerResponse {
       return customerService.findById(id).toResponse()
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun update(@PathVariable id: Int, @RequestBody @Valid requestPut: PutCustomerRequest){
        val customerSaved = customerService.findById(id)
        customerService.update(id, requestPut.toCostumer(customerSaved))
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Int){
        customerService.delete(id)
    }


}