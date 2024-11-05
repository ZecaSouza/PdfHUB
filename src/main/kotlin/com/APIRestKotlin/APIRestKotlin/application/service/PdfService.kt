package com.APIRestKotlin.APIRestKotlin.application.service

import com.APIRestKotlin.APIRestKotlin.adapters.persistence.PdfFileRepository
import com.APIRestKotlin.APIRestKotlin.domain.PdfFile
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class PdfService(private val pdfFileRepository: PdfFileRepository) {

    fun uploadPdf(file: MultipartFile, description: String?): PdfFile{
        val pdfFile = PdfFile(
            fileName = file.originalFilename ?: "unknow.pdf",
            contentType = file.contentType ?: "application/pdf",
            fileData = file.bytes,
            description = description
        )

        return pdfFileRepository.save(pdfFile)
    }

    fun downloadPdf(id: Long): PdfFile? {
        return pdfFileRepository.findById(id).orElse(null)
    }
}