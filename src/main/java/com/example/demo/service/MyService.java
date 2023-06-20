package com.example.demo.service;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.dao.PDFGenerator;
import com.example.demo.dao.UsingStatemetDao;
import com.example.demo.dao.addData;
import com.example.demo.entity.MyEntity;
@Service
public class MyService {
	@Autowired
	private UsingStatemetDao usingStatementDao;
	
public void insertData(MyEntity myEntity) {
	usingStatementDao.insertData(myEntity);
}
public List<MyEntity> showData()
{
	return usingStatementDao.showData();
}
public ResponseEntity<byte[]> getAllData()
{
	return usingStatementDao.getAllData();
}
public ResponseEntity<byte[]> getAllWord() {
	return usingStatementDao.getAllWord();
	
}
public ResponseEntity<byte[]> getAllPDF() {
	return usingStatementDao.getAllPDF() ;
	
}
//public ResponseEntity<byte[]> getAllPDF() {
//    List<MyEntity> entities = usingStatementDao.getAllWord();
//    // Rest of the code
//}
public void updateData(MyEntity myEntity) {
	usingStatementDao.updateData(myEntity);
}
public void deleteData( int  id) {
	usingStatementDao.deleteData(id);
}

}
