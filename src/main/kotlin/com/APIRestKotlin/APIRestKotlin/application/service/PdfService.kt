package com.APIRestKotlin.APIRestKotlin.application.service

import com.APIRestKotlin.APIRestKotlin.adapters.persistence.PdfFileRepository
import com.APIRestKotlin.APIRestKotlin.domain.PdfFile
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
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

    fun getAllPdfs(page: Int, size: Int): Page<PdfFile> {
        val pageable = PageRequest.of(page, size)
        return pdfFileRepository.findByIsDeletedFalse(pageable)
    }


    fun deletePdf(id: Long):Boolean{
        val pdfFile = pdfFileRepository.findByIdAndIsDeletedFalse(id)
        return if (pdfFile != null){
            pdfFile.isDeleted = true
            pdfFileRepository.save(pdfFile)
            true
        }else{
            false
        }
    }

    fun getById(id: Long): Map<String, Any>? {
        val pdfFile = pdfFileRepository.findByIdAndIsDeletedFalse(id)
        return pdfFile?.let {
            mapOf(
                "id" to it.id,
                "fileName" to it.fileName,
                "description" to (it.description ?: ""),
                "contentType" to it.contentType
            )
        }
    }
}