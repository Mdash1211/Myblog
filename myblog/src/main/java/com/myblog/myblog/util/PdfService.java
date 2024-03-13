package com.myblog.myblog.util;

import org.springframework.stereotype.Service;
import com.myblog.myblog.payload.PostDto;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import java.io.ByteArrayOutputStream;

@Service
public class PdfService {

    public ByteArrayOutputStream generatePdf(PostDto postDto) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try (PdfWriter writer = new PdfWriter(outputStream);
             PdfDocument pdfDocument = new PdfDocument(writer)) {

            Document document = new Document(pdfDocument);
            document.add(new Paragraph("Post ID: " + postDto.getPostId()));
            document.add(new Paragraph("Title: " + postDto.getTitle()));
            document.add(new Paragraph("Description: " + postDto.getDescription()));
            document.add(new Paragraph("Content: " + postDto.getContent()));

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return outputStream;
    }
}


