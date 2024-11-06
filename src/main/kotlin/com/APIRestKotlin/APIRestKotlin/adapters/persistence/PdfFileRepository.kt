package com.APIRestKotlin.APIRestKotlin.adapters.persistence

import com.APIRestKotlin.APIRestKotlin.domain.PdfFile
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface PdfFileRepository : JpaRepository<PdfFile, Long>{
    fun findByIsDeletedFalse(pageable: Pageable): Page<PdfFile>
    fun findByIdAndIsDeletedFalse(id: Long): PdfFile?
}