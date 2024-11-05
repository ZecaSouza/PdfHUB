package com.APIRestKotlin.APIRestKotlin.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id


@Entity
data class PdfFile(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val fileName: String,
    val contentType: String,
    val fileData: ByteArray,
    val description: String? = null
)
