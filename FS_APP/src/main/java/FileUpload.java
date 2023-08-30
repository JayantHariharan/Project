import java.util.*;
import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
@WebServlet("/FileUpload")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
                 maxFileSize = 1024 * 1024 * 10,     // 10MB
                 maxRequestSize = 1024 * 1024 * 50) // 50MB
public class FileUpload extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Part filePart = request.getPart("file");
        String fileMode = request.getParameter("fileMode");
        String fileName = filePart.getSubmittedFileName();
        
        // Determine the storage location based on fileMode
        String location = "J:\\FileStore\\" + (fileMode.equals("private") ? "PrivateFiles\\" : "PublicFiles\\");
        
        String absolutePath = location + fileName;
        try {
            filePart.write(absolutePath);
            System.out.println("File saved to: " + absolutePath);
        } catch (IOException e) {
            System.err.println("Error saving file: " + e.getMessage());
            e.printStackTrace();
        }
        response.sendRedirect("filepage.html");
    }
}