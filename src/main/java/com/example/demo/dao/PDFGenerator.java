package com.example.demo.dao;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import com.example.demo.entity.MyEntity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class PDFGenerator {

    public ResponseEntity<byte[]> getAllPDF(List<MyEntity> entities) {
        try {
            // Create a new PDF document
            PDDocument document = new PDDocument();

            // Create a new page
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            // Create a content stream for the page
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Set font and font size
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

            // Set the table width and cell height
            float margin = 50;
            float yStart = page.getMediaBox().getHeight() - margin;
            float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
            float yPosition = yStart;
            float tableHeight = 20f;

            // Create a list to store the table rows
            List<String[]> rows = generateTableData(entities);

            // Draw the table
            for (String[] row : rows) {
                drawTableRow(contentStream, tableWidth, yPosition, tableHeight, row);
                yPosition -= tableHeight;
            }

            // Close the content stream
            contentStream.close();

            // Create an in-memory byte array output stream
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            // Save the PDF document to the output stream
            document.save(byteArrayOutputStream);

            // Set the appropriate headers for file download
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "data.pdf");

            // Create a ResponseEntity with the document's data and headers
            ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(byteArrayOutputStream.toByteArray(), headers, HttpStatus.OK);

            // Close the PDF document
            document.close();

            return responseEntity;
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception appropriately and return an error response
            HttpHeaders errorHeaders = new HttpHeaders();
            errorHeaders.setContentType(MediaType.TEXT_PLAIN);
            return new ResponseEntity<>("Error occurred while generating the PDF file".getBytes(),
                    errorHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private List<String[]> generateTableData(List<MyEntity> entities) {
        // Create a list to store the table rows
        List<String[]> rows = new ArrayList<>();

        // Add table headers
        rows.add(new String[]{"ID", "Name", "Salary"});

        // Add table rows with data
        for (MyEntity entity : entities) {
            rows.add(new String[]{
                    String.valueOf(entity.getId()),
                    entity.getName(),
                    String.valueOf(entity.getSalary())
            });
        }

        return rows;
    }

    private void drawTableRow(PDPageContentStream contentStream, float tableWidth,
            float y, float rowHeight, String[] rowData) throws IOException {
float x = 50;
float cellMargin = 5;

for (int i = 0; i < rowData.length; i++) {
contentStream.beginText();
contentStream.moveTextPositionByAmount(x, y);
contentStream.drawString(rowData[i]);
contentStream.endText();

x += tableWidth / 3;
}
}
}







