package com.example.demo.dao;

import java.io.FileOutputStream;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class writeWordDocument {
    public void writeWordDocument(String fileName, String content) {
        try {
            // Create a new Word document
            XWPFDocument document = new XWPFDocument();

            // Create a paragraph and a run
            XWPFParagraph paragraph = document.createParagraph();
            XWPFRun run = paragraph.createRun();

            // Set the text content for the run
            run.setText(content);

            // Save the document to a file
            FileOutputStream fileOut = new FileOutputStream(fileName);
            document.write(fileOut);
            fileOut.close();

            System.out.println("Word document created successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
