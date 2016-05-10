
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Busra
 */
@WebServlet("/kaan")
public class Kaan extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        PrintWriter pTool = response.getWriter();
        String queryTerm = request.getParameter("queryTerm");
        if(queryTerm == null){
            System.out.println("Welcome Page");
            request.getRequestDispatcher("/kaan.jsp").forward(request, response);
        }
    }

}