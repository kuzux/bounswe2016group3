/**
 * Created by sinem on 10/05/16.
 */
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Directs to saving page. Saves the data selected by the user.
 */
public class Actors extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public Actors() {

            super();

    }

    /**
     * Implements the handling of directing to methods in Sinem.java.
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        String res=Sinem.save(response,request);

        out.println("<html>");
        out.println("<body>");
        out.println("<p> Saved! </p>");
        out.println("</body>");
        out.println("</html>");
    }
}
