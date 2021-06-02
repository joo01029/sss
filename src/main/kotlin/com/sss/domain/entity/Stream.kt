package com.sss.domain.entity

import java.util.*
import javax.persistence.*

@Entity
@Table
class Stream {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var idx : Long? = null

    @Column(nullable = false)
    var name : String? = null;

    @ManyToOne
    @JoinColumn(nullable = false)
    var streamer : User? = null;

    @Column(nullable = false)
    var streamDate : Date = Date();

    @Column(nullable = false)
    var end : Boolean = false;
}