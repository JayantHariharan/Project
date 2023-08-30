import java.util.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/DownloadFile")
public class DownloadFile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	 protected void doGet(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {

	        String fileName = request.getParameter("fileName");
	        String fileMode = request.getParameter("fileMode");

	        String location = "J:\\FileStore\\" + (fileMode.equals("private") ? "PrivateFiles\\" : "PublicFiles\\");
	        String filePath = location + fileName;

	        File file = new File(filePath);
	        if (file.exists()) {
	            response.setContentType("application/octet-stream");
	            response.setContentLength((int) file.length());
	            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

	            try (InputStream inputStream = new FileInputStream(file);
	                 OutputStream outputStream = response.getOutputStream()) {
	                byte[] buffer = new byte[4096];
	                int bytesRead;
	                while ((bytesRead = inputStream.read(buffer)) != -1) {
	                    outputStream.write(buffer, 0, bytesRead);
	                }
	            }
	        } else {
	            response.sendError(HttpServletResponse.SC_NOT_FOUND);
	        }
	    }
}