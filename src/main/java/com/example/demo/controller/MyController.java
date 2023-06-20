package com.example.demo.controller;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.entity.MyEntity;
import com.example.demo.repository.ConnectioinJdbc;
import com.example.demo.service.EmailService;
import com.example.demo.service.MyService;

import jakarta.mail.MessagingException;
@RestController
@RequestMapping("/con")
public class MyController {
	@Autowired
	private MyService myService;
	private ConnectioinJdbc connectionJdbc;
	private EmailService emailService;
	public MyController(){	
	}
	@PostMapping("/add")
	public MyEntity insertData(@RequestBody MyEntity myEntity)
	{
		myService.insertData(myEntity);
		return myEntity;
	}
	@GetMapping("/showData")
	public List<MyEntity> showData()
	{
		return myService.showData();
	}
	@GetMapping("/getData")
	public ResponseEntity<byte[]> getAllData()
	{
		return myService.getAllData();
	}
	@GetMapping("/getWord")
	public ResponseEntity<byte[]> getAllWord()
	{
		return myService.getAllWord();
	}
	@GetMapping("/getPdf")
	public ResponseEntity<byte[]> getAllPDF() {
		
		return myService.getAllPDF();
	}
	 @PutMapping("/put/{id}")
	public MyEntity updateData(@PathVariable int id,@RequestBody MyEntity myEntity)
	{
		myService.updateData(myEntity);
		return myEntity;
	}
	 @DeleteMapping("/delete/{id}")
	 public MyEntity deleteData(@PathVariable int id,@RequestBody MyEntity myEntity)
	 {
		 myService.deleteData(id);
		 return myEntity;
	 }
//	 @GetMapping("/share")
//	    public ResponseEntity<String> shareExcelFile()  {
//			return shareExcelFile();
//	       
//	 }
}
