package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.repository.ConnectioinJdbc;
@SpringBootApplication
public class SpringBootCrudVerySimpleApplication implements CommandLineRunner {
	@Autowired
	private ConnectioinJdbc connectionJdbc;
	@Autowired
	//private Oprations oprations;
	public static void main(String[] args) {
		SpringApplication.run(SpringBootCrudVerySimpleApplication.class, args);
	}
		@Override
		public void run(String... args) throws Exception {
		
			System.out.println(this.connectionJdbc.connect());
		
		//System.out.println(this.adddata.insertData(21, "Error", 7854.02));
		//System.out.println(this.oprations.insertData() );
	}
}
