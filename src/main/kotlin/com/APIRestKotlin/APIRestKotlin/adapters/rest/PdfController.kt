package com.APIRestKotlin.APIRestKotlin.adapters.rest

import com.APIRestKotlin.APIRestKotlin.application.service.PdfService
import com.APIRestKotlin.APIRestKotlin.domain.PdfFile
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile


@RestController
@RequestMapping("/pdfs")
class PdfController(private val pdfService: PdfService) {

    @PostMapping("/upload")
    fun uploadPdf(@RequestParam("file") file: MultipartFile): ResponseEntity<String> {
        val uploadedFile = pdfService.uploadPdf(file)
        return ResponseEntity.status(HttpStatus.CREATED).body("File uploaded with ID: ${uploadedFile.id}")
    }

    @GetMapping("/download/{id}")
    fun downloadPdf(@PathVariable id: Long): ResponseEntity<ByteArray>{
        val pdfFile: PdfFile? = pdfService.downloadPdf(id)
        return if (pdfFile != null) {
            ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"${pdfFile.fileName}\"")
                .contentType(MediaType.parseMediaType(pdfFile.contentType))
                .body(pdfFile.fileData)
        }else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
        }
    }
}