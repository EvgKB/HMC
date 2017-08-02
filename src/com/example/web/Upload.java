package com.example.web;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.example.model.DBTables;
import com.example.model.JDBCUtilities;


public class Upload extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Random random = new Random();
	private String path;
	private String description;
	        
    public Upload() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//��������� �������� �� ���������� ������ multipart/form-data
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (!isMultipart) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
 
		// ������ ����� ������� 
		DiskFileItemFactory factory = new DiskFileItemFactory();
 
		// ������������ ������ ������ � ������,
		// ��� ��� ���������� ������ ������ ������������ �� ���� �� ��������� ����������
		// ������������� ���� ��������
		factory.setSizeThreshold(1024*1024);
		
		// ������������� ��������� ����������
		File tempDir = (File)getServletContext().getAttribute("javax.servlet.context.tempdir");
		factory.setRepository(tempDir);
 
		//������ ��� ���������
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		//������������ ������ ������ ������� ��������� ��������� � ������
		//�� ��������� -1, ��� �����������. ������������� 10 ��������. 
		upload.setSizeMax(1024 * 1024 * 10);
 
		try {
			List items = upload.parseRequest(request);
			Iterator iter = items.iterator();
			
			while (iter.hasNext()) {
			    FileItem item = (FileItem) iter.next();
 
			    if (item.isFormField()) {
			    	//���� ����������� ����� ������ �������� ����� �����			    	
			        processFormField(item);
			    } else {
			    	//� ��������� ������ ������������� ��� ����
			        processUploadedFile(item);
			        
			    }
			}
			
			JDBCUtilities util = new JDBCUtilities("root","root");
	    	Connection conn = util.getConnection();
			DBTables.insertMediaRow(conn, "hmcatalog", description, path);
			util.closeConnection(conn);

			request.setAttribute("imagePath", path);
	        RequestDispatcher view = request.getRequestDispatcher("Result.jsp");
			view.forward(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}		
	}
	
	/**
	 * ��������� ���� �� �������, � ����� upload.
	 * ���� ����� ������ ���� ��� �������. 
	 * 
	 * @param item
	 * @throws Exception
	 */
	private void processUploadedFile(FileItem item) throws Exception {
		File uploadetFile = null;
		//�������� ����� ��� ���� �� ����� ���������
		do{
			path = getServletContext().getRealPath("/upload/"+random.nextInt() + item.getName()); 
			uploadetFile = new File(path);		
		}while(uploadetFile.exists());
		
		//������ ����
		uploadetFile.createNewFile();
		//���������� � ���� ������
		item.write(uploadetFile);
		System.out.println(path);
				
	}
 
	/**
	 * ������� �� ������� ��� ��������� � ��������
	 * @param item
	 */
	private void processFormField(FileItem item) {
		System.out.println(item.getFieldName()+"="+item.getString());
		this.description = item.getString();
	}

}
