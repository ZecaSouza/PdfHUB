package com.APIRestKotlin.APIRestKotlin.application.service
import com.APIRestKotlin.APIRestKotlin.adapters.persistence.PdfFileRepository
import com.APIRestKotlin.APIRestKotlin.domain.PdfFile
import com.github.database.rider.core.api.dataset.DataSet
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.mock.web.MockMultipartFile
import java.util.*

class PdfServiceTest {

    private val pdfFileRepository: PdfFileRepository = mock(PdfFileRepository::class.java)
    private val pdfService = PdfService(pdfFileRepository)

    @BeforeEach
    @DataSet("pdf_file.yml")
    fun setUpPdfFiles() {
    }

    @Test
    fun `should upload a PDF and save it in the repository`() {
        val mockFile = MockMultipartFile("file", "test.pdf", "application/pdf", "dummy content".toByteArray())
        val description = "Test PDF"

        val savedPdf = PdfFile(
            id = 1L,
            fileName = mockFile.originalFilename!!,
            contentType = mockFile.contentType!!,
            fileData = mockFile.bytes,
            description = description
        )

        `when`(pdfFileRepository.save(any(PdfFile::class.java))).thenReturn(savedPdf)

        val result = pdfService.uploadPdf(mockFile, description)

        Assertions.assertNotNull(result)
        Assertions.assertEquals("test.pdf", result.fileName)
        Assertions.assertEquals("application/pdf", result.contentType)
        Assertions.assertEquals(description, result.description)
        verify(pdfFileRepository, times(1)).save(any(PdfFile::class.java))
    }

    @Test
    fun `should return a PDF by its ID`() {
        val pdfId = 1L
        val pdfFile = PdfFile(
            id = pdfId,
            fileName = "test.pdf",
            contentType = "application/pdf",
            fileData = byteArrayOf(1, 2, 3),
            description = "Test PDF"
        )

        `when`(pdfFileRepository.findById(pdfId)).thenReturn(Optional.of(pdfFile))

        val result = pdfService.downloadPdf(pdfId)

        Assertions.assertNotNull(result)
        Assertions.assertEquals(pdfId, result?.id)
        Assertions.assertEquals("test.pdf", result?.fileName)
    }

    @Test
    fun `should return paginated PDFs`() {
        val pageable = PageRequest.of(0, 10)
        val pdfFileList = listOf(
            PdfFile(
                id = 1L,
                fileName = "test1.pdf",
                contentType = "application/pdf",
                fileData = byteArrayOf(),
                description = "Test PDF 1"
            ),
            PdfFile(
                id = 2L,
                fileName = "test2.pdf",
                contentType = "application/pdf",
                fileData = byteArrayOf(),
                description = "Test PDF 2"
            )
        )

        val pdfPage: Page<PdfFile> = PageImpl(pdfFileList, pageable, pdfFileList.size.toLong())

        `when`(pdfFileRepository.findByIsDeletedFalse(pageable)).thenReturn(pdfPage)

        val result = pdfService.getAllPdfs(0, 10)

        Assertions.assertNotNull(result)
        Assertions.assertEquals(2, result.content.size)
        Assertions.assertEquals("test1.pdf", result.content[0].fileName)
        Assertions.assertEquals("test2.pdf", result.content[1].fileName)
    }

    @Test
    fun `should mark a PDF as deleted`() {
        val pdfId = 1L
        val pdfFile = PdfFile(
            id = pdfId,
            fileName = "test.pdf",
            contentType = "application/pdf",
            fileData = byteArrayOf(1, 2, 3),
            description = "Test PDF",
            isDeleted = false
        )

        `when`(pdfFileRepository.findByIdAndIsDeletedFalse(pdfId)).thenReturn(pdfFile)

        val result = pdfService.deletePdf(pdfId)

        Assertions.assertTrue(result)
        pdfFile.isDeleted?.let { Assertions.assertTrue(it) }
        verify(pdfFileRepository, times(1)).save(pdfFile)
    }

    @Test
    fun `should return PDF details by ID`() {
        val pdfId = 1L
        val pdfFile = PdfFile(
            id = pdfId,
            fileName = "test.pdf",
            contentType = "application/pdf",
            fileData = byteArrayOf(1, 2, 3),
            description = "Test PDF",
            isDeleted = false
        )

        `when`(pdfFileRepository.findByIdAndIsDeletedFalse(pdfId)).thenReturn(pdfFile)

        val result = pdfService.getById(pdfId)

        Assertions.assertNotNull(result)
        Assertions.assertEquals(pdfId, result?.get("id"))
        Assertions.assertEquals("test.pdf", result?.get("fileName"))
        Assertions.assertEquals("Test PDF", result?.get("description"))
        Assertions.assertEquals("application/pdf", result?.get("contentType"))
    }
}
