package com.mercadolivro.book

import com.mercadolivro.customer.Customer
import java.math.BigDecimal
import java.text.DecimalFormat
import javax.persistence.*

@Entity
class Book(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column
    var name: String,

    @Column
    var price: BigDecimal,

    @ManyToOne
    @JoinColumn(name = "customer_id")
    var customer: Customer? = null
){
    @Column
    @Enumerated(EnumType.STRING)
    var status: BookStatus? = null
        set(value){
            if(field == BookStatus.CALLED_OFF || field == BookStatus.DELETED){
                throw Exception("Não é possível alterar livro com status ${field}")
            }
            field = value
        }

    constructor(id: Int? = null,
                name: String,
                price: BigDecimal,
                customer: Customer? = null,
                status: BookStatus?): this(id, name, price, customer){
                    this.status = status
                }
}