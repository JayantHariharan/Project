

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FolderList
 */
@WebServlet("/FolderList")
public class FolderList extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


		String fileMode = request.getParameter("fileMode");
        String location;
        if ("private".equals(fileMode)) {
            location = "J:\\FileStore\\PrivateFiles\\";
        } else if ("public".equals(fileMode)) {
            location = "J:\\FileStore\\PublicFiles\\";
        } else {
            throw new IllegalArgumentException("Invalid file mode");
        }

        File folder = new File(location);
        File[] files = folder.listFiles();

        List<String> fileNames = Arrays.stream(files)
                .map(File::getName)
                .toList();

        // Set response content type to JSON
        response.setContentType("application/json");
        JSONArray jsonArray = new JSONArray(fileNames);
        String allFiles = jsonArray.toString();
        PrintWriter out = response.getWriter();
        out.print(allFiles);
        out.flush();
    }

}
