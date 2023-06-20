package com.example.demo.dao;

import java.io.ByteArrayOutputStream;



import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpHeaders;
import com.example.demo.entity.MyEntity;
import com.example.demo.repository.ConnectioinJdbc;
import com.example.demo.service.EmailService;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblBorders;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STBorder;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.font.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Repository
@Service
public class UsingStatemetDao {
	  @Autowired
	    private ConnectioinJdbc jdbc;
	  	private MyEntity myEntity;
	  	private UsingStatemetDao usingStatemetDao;
	  	private EmailService emailSrvice;
	  	
	    public void insertData(MyEntity myEntity) {
	    	try (Connection con = jdbc.connect()) {
	    	    Statement stmt = con.createStatement();
	    	    String sql = "INSERT INTO pro (id, name, salary) VALUES ('" + myEntity.getId() + "', '" + myEntity.getName() + "', " + myEntity.getSalary() + ")";
	    	    int update = stmt.executeUpdate(sql);
	    	    if (update > 0) {
	    	        System.out.println("Data Inserted");
	    	        System.out.println(update);
	    	    } else {
	    	        System.out.println("Data Not Inserted"); 
	    	    }
	    	} catch (SQLException e) {
	    	    System.out.println("Failed to insert data: " + e.getMessage()); 
	    	}
   }
	    
	    public List<MyEntity> showData()
	    {
	    	try {
	    	    Statement st = jdbc.connect().createStatement();
	    	    ResultSet rs = st.executeQuery("SELECT * FROM pro");
	    	    
	    	    if (!rs.next()) {
	    	        System.out.println("No rows found in the result set.");
	    	    } else {
	    	        do {
	    	            System.out.println(rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getDouble(3));
	    	        } while (rs.next());
	    	    }
	    	} catch (SQLException e) {
	    	    System.out.println(e);
	    	}
	    	return null;
	    }
	    
	       

	    //Download Excell File API
	    public ResponseEntity<byte[]> getAllData() {
	        List<MyEntity> entities = new ArrayList<>();

	        try {
	            Statement st = jdbc.connect().createStatement();
	            ResultSet rs = st.executeQuery("SELECT * FROM pro");

	            Workbook workbook = new XSSFWorkbook();
	            Sheet sheet = workbook.createSheet("Data");

	            int rowNum = 0;
	            Row headerRow = sheet.createRow(rowNum++);
	            headerRow.createCell(0).setCellValue("ID");
	            headerRow.createCell(1).setCellValue("Name");
	            headerRow.createCell(2).setCellValue("Salary");

	            while (rs.next()) {
	                int id = rs.getInt("id");
	                String name = rs.getString("name");
	                double salary = rs.getDouble("salary");

	                Row dataRow = sheet.createRow(rowNum++);
	                dataRow.createCell(0).setCellValue(id);
	                dataRow.createCell(1).setCellValue(name);
	                dataRow.createCell(2).setCellValue(salary);
	            }

	            // Create an in-memory byte array output stream
	            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	            workbook.write(byteArrayOutputStream);

	            // Set the appropriate headers for file download
	            HttpHeaders headers = new HttpHeaders();
	            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	            headers.setContentDispositionFormData("attachment", "data.xlsx");

	            // Create a ResponseEntity with the workbook's data and headers
	            ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(byteArrayOutputStream.toByteArray(), headers, HttpStatus.OK);

	            workbook.close();

	            return responseEntity;
	        } catch (SQLException | IOException e) {
	            e.printStackTrace();
	            // Handle the exception appropriately and return an error response
	            HttpHeaders errorHeaders = new HttpHeaders();
	            errorHeaders.setContentType(MediaType.TEXT_PLAIN);
	            return new ResponseEntity<>("Error occurred while generating the Excel file".getBytes(), errorHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
	    //Download Word File API
	    public ResponseEntity<byte[]> getAllWord() {
//	        List<MyEntity> entities = new ArrayList<>();

	        try {
	            Statement st = jdbc.connect().createStatement();
	            ResultSet rs = st.executeQuery("SELECT * FROM pro");

	            XWPFDocument document = new XWPFDocument();
	            XWPFTable table = document.createTable();

	            // Set the table width based on your requirements
	            String tableWidth = "10000"; // Adjust the value to fit your needs
	            table.setWidth(tableWidth);
	            
	            // Set the table borders
	            CTTblBorders borders = table.getCTTbl().getTblPr().addNewTblBorders();
	            borders.addNewTop().setVal(STBorder.SINGLE);
	            borders.addNewBottom().setVal(STBorder.SINGLE);
	            borders.addNewLeft().setVal(STBorder.SINGLE);
	            borders.addNewRight().setVal(STBorder.SINGLE);

	            XWPFTableRow headerRow = table.getRow(0);
	            headerRow.getCell(0).setText("ID");
	            headerRow.addNewTableCell().setText("Name");
	            headerRow.addNewTableCell().setText("Salary");

	            while (rs.next()) {
	                XWPFTableRow dataRow = table.createRow();
	                dataRow.getCell(0).setText(String.valueOf(rs.getInt("id")));
	                dataRow.getCell(1).setText(rs.getString("name"));
	                dataRow.getCell(2).setText(String.valueOf(rs.getDouble("salary")));
	            }

	            // Create an in-memory byte array output stream
	            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	            document.write(byteArrayOutputStream);

	            // Set the appropriate headers for file download
	            HttpHeaders headers = new HttpHeaders();
	            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	            headers.setContentDispositionFormData("attachment", "data.docx");

	            // Create a ResponseEntity with the document's data and headers
	            ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(byteArrayOutputStream.toByteArray(), headers, HttpStatus.OK);

	            document.close();

	            return responseEntity;
	        } catch (SQLException | IOException e) {
	            e.printStackTrace();
	            // Handle the exception appropriately and return an error response
	            HttpHeaders errorHeaders = new HttpHeaders();
	            errorHeaders.setContentType(MediaType.TEXT_PLAIN);
	            return new ResponseEntity<>("Error occurred while generating the Word file".getBytes(), errorHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
	 
	    //Download PDF File API 
	    public ResponseEntity<byte[]> getAllPDF() {
	        try {
	            // Create a temporary list of entities for testing
	            List<MyEntity> entities = new ArrayList<>();
	            
	            try (Connection con = jdbc.connect()) {
	                String sql = "SELECT * FROM pro";
	                PreparedStatement pstmt = con.prepareStatement(sql);
	                ResultSet rs = pstmt.executeQuery();

	                while (rs.next()) {
	                    int id = rs.getInt("id");
	                    String name = rs.getString("name");
	                    double salary = rs.getDouble("salary");

	                    MyEntity entity = new MyEntity(id, name, salary);
	                    entities.add(entity);
	                }
	                rs.close();
	                pstmt.close();
	            } catch (SQLException e) {
	                System.out.println(e.getMessage());
	                e.printStackTrace();
	            }
	           
	            if (entities.isEmpty()) {
	                // Handle the case when there are no entities to display
	                HttpHeaders emptyHeaders = new HttpHeaders();
	                emptyHeaders.setContentType(MediaType.TEXT_PLAIN);
	                return new ResponseEntity<>("No data found".getBytes(), emptyHeaders, HttpStatus.NOT_FOUND);
	            }

	            // Create a new document
	            try (PDDocument document = new PDDocument()) {
	                PDPage page = new PDPage();
	                document.addPage(page);

	                // Define table parameters
	                float margin = 50;
	                float yStart = page.getMediaBox().getHeight() - 2 * margin;
	                float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
	                float yPosition = yStart;
	                float bottomMargin = 70;
	                float yStartNewPage = page.getMediaBox().getHeight() - margin - bottomMargin;
	                float lineHeight = 15;
	                int rowsPerPage = 10;

	                // Create an in-memory byte array output stream
	                try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
	                    // Create table headers
	                    try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
	                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
	                        contentStream.beginText();
	                        contentStream.newLineAtOffset(margin, yStart);
	                        contentStream.showText("ID");
	                        contentStream.newLineAtOffset(tableWidth * 0.2f, 0);
	                        contentStream.showText("Name");
	                        contentStream.newLineAtOffset(tableWidth * 0.4f, 0);
	                        contentStream.showText("Salary");
	                        contentStream.endText();

	                        // Draw table data
	                        contentStream.setFont(PDType1Font.HELVETICA, 12);
	                        float yPositionData = yStart - lineHeight;
	                        int count = 0;
	                        for (MyEntity entity : entities) {
	                            if (count >= rowsPerPage) {
	                                contentStream.close();
	                                page = new PDPage();
	                                document.addPage(page);
	                                contentStream.setFont(PDType1Font.HELVETICA, 12);
	                                contentStream.moveTo(margin, yStart);
	                                contentStream.showText("ID");
	                                contentStream.newLineAtOffset(tableWidth * 0.2f, 0);
	                                contentStream.showText("Name");
	                                contentStream.newLineAtOffset(tableWidth * 0.4f, 0);
	                                contentStream.showText("Salary");
	                                contentStream.endText();
	                                contentStream.moveTo(margin, yPositionData);
	                                yPositionData = yStartNewPage - lineHeight;
	                                count = 0;
	                            }
	                            count++;

	                            contentStream.beginText();
	                            contentStream.newLineAtOffset(margin, yPositionData);
	                            contentStream.showText(String.valueOf(entity.getId()));
	                            contentStream.newLineAtOffset(tableWidth * 0.2f, 0);
	                            contentStream.showText(entity.getName());
	                            contentStream.newLineAtOffset(tableWidth * 0.4f, 0);
	                            contentStream.showText(String.valueOf(entity.getSalary()));
	                            contentStream.endText();

	                            yPositionData -= lineHeight;
	                        }
	                    }

	                    // Save the document to the byte array output stream
	                    document.save(byteArrayOutputStream);

	                    // Set the appropriate headers for file download
	                    HttpHeaders headers = new HttpHeaders();
	                    headers.setContentType(MediaType.APPLICATION_PDF);
	                    headers.setContentDispositionFormData("attachment", "data.pdf");

	                    // Create a ResponseEntity with the document's data and headers
	                    return ResponseEntity.ok()
	                            .headers(headers)
	                            .body(byteArrayOutputStream.toByteArray());
	                }
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	            // Handle the exception appropriately and return an error response
	            HttpHeaders errorHeaders = new HttpHeaders();
	            errorHeaders.setContentType(MediaType.TEXT_PLAIN);
	            return new ResponseEntity<>("Error occurred while generating the PDF file".getBytes(), errorHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }

	    public void updateData(MyEntity myEntity)
	    {
	    
	    	 try (Connection con = jdbc.connect()) {
	    		    PreparedStatement pt = con.prepareStatement("UPDATE pro SET name=?, salary=? WHERE id=?");
	    		    pt.setString(1, myEntity.getName());
	    		    pt.setDouble(2, myEntity.getSalary());
	    		    pt.setInt(3, myEntity.getId());

	    		    int update = pt.executeUpdate();
	    		    if (update > 0) {
	    		        System.out.println("Data Updated");
	    		    } else {
	    		        System.out.println("Data Not Updated");
	    		    }
	    		} catch (Exception e) {
	    		    System.out.println(e.getMessage());
	    		    e.printStackTrace();
	    		}
	    }
	    public void deleteData(int id) {
	    	try (Connection con = jdbc.connect()) {
	    	    Statement stmt = con.createStatement();
	    	    String sql = "UPDATE pro SET name='" + myEntity.getName() + "', salary=" + myEntity.getSalary() + " WHERE id=" + myEntity.getId();
	    	    
	    	    int update = stmt.executeUpdate(sql);
	    	    if (update > 0) {
	    	        System.out.println("Data Updated");
	    	    } else {
	    	        System.out.println("Data Not Updated");
	    	    }
	    	} catch (SQLException e) {
	    	    System.out.println(e.getMessage());
	    	    e.printStackTrace();
	    	}

	    }
	    public ResponseEntity<String> shareExcelFile() throws javax.mail.MessagingException {
	        List<MyEntity> entities = new ArrayList<>();

	        try(Connection con = jdbc.connect()) {
	            Statement st = con.createStatement();
	            ResultSet rs = st.executeQuery("SELECT * FROM pro");

	            Workbook workbook = new XSSFWorkbook();
	            org.apache.poi.ss.usermodel.Sheet sheet = workbook.createSheet("Data");

	            int rowNum = 0;
	            Row headerRow = sheet.createRow(rowNum++);
	            headerRow.createCell(0).setCellValue("ID");
	            headerRow.createCell(1).setCellValue("Name");
	            headerRow.createCell(2).setCellValue("Salary");

	            while (rs.next()) {
	                int id = rs.getInt("id");
	                String name = rs.getString("name");
	                double salary = rs.getDouble("salary");

	                Row dataRow = sheet.createRow(rowNum++);
	                dataRow.createCell(0).setCellValue(id);
	                dataRow.createCell(1).setCellValue(name);
	                dataRow.createCell(2).setCellValue(salary);
	            }

	            // Create an in-memory byte array output stream
	            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	            workbook.write(byteArrayOutputStream);

	            // Get the byte array representing the workbook's data
	            byte[] workbookData = byteArrayOutputStream.toByteArray();

	            // Generate a unique attachment name
	            String attachmentName = "data.xlsx";
	           
	            // Send the email with attachment
	           
	            try {
	            	//emailSrvice.sendEmailWithAttachment("satishbarate098@gmail.com", " getAllData()", "Please find the attached Excel file.", workbookData, attachmentName);
	                return ResponseEntity.ok("Email sent successfully.");
	            } finally {
	                workbook.close();
	            }
	        } catch (SQLException | IOException e) {
	            e.printStackTrace();
	            // Handle the exception appropriately and return an error response
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while generating the Excel file");
	        }
	 }


}
