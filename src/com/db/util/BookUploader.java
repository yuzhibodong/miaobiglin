package com.db.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.sql.Blob;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.db.entity.*;

public class BookUploader {
	private Session session;
	private Transaction transaction;

	private int num = 0;
	public BookUploader() {

	}

	public void Init() {
		this.session = HibernateSessionFactory.getSession();
		this.transaction = session.beginTransaction();
	}

	public void Destroy() {
		transaction.commit();
		
		session.close();
	}

	private void SaveByFile(File file) {
		Book book = new Book();
		InputStream inputStream=null;
		String fileNameString = "";
		try {
			fileNameString = file.getName().replaceAll("['\\\"]", "");
			

			String bookName = fileNameString.substring(0,
					fileNameString.lastIndexOf("."));
			if (bookName.length()>95) {
				return;
			}
			if (new BookFinder().isExist(bookName)) {
				return;
			}
			book.setName(bookName);
			book.setType(fileNameString.substring(fileNameString
					.lastIndexOf(".")));

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			inputStream = new FileInputStream(file);
			//大于10M的文件跳过
			if (inputStream.available()>10000000) {
				return;
			}
			Blob fileBlob = Hibernate.getLobCreator(session).createBlob(
					inputStream, inputStream.available());
			book.setFile(fileBlob);
			System.out.println(num++ +":"+fileNameString);
			session.save(book);
			transaction.commit();
			transaction = session.beginTransaction();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			try {
				if (inputStream!=null) {
					inputStream.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void Save(String path) {

		File file = new File(path);
		if (!file.exists()) {
			return;
		} else if (file.isFile()) {
			SaveByFile(file);
		} else {
			for (File deepFile : file.listFiles()) {
				Save(deepFile.getAbsolutePath());
			}
		}

	}

}
