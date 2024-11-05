package com.APIRestKotlin.APIRestKotlin.adapters.persistence

import com.APIRestKotlin.APIRestKotlin.domain.PdfFile
import org.springframework.data.jpa.repository.JpaRepository

interface PdfFileRepository : JpaRepository<PdfFile, Long>