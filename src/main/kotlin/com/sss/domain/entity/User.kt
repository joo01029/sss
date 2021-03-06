package com.sss.domain.entity

import java.util.*
import javax.persistence.*
import kotlin.collections.ArrayList

@Entity
@Table
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var idx : Long? = null

    @Column(unique = true, length = 30)
    var id : String? = null

    @Column(nullable = false, length = 60)
    var password : String? = null

    @Column(nullable = false, length = 30)
    var name : String? = null

    @Column(nullable = false)
    var createAt: Date = Date()

    @Column
    var image : String? = null

    @OneToMany(mappedBy = "streamer")
    var streams : MutableList<Stream> = ArrayList()


    fun add(stream: Stream){
        streams.add(stream)
        stream.streamer = this
    }

}