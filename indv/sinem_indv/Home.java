/**
 * Created by sinem on 09/05/16.
 */
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Outputs the results after the query.Query for year of birth and name can be made. According to the search type
 * it directs to the relevant Sinem.java method.
 */
public class Home extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public Home() {
        super();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        String inname = request.getParameter("name");
        String inyear = request.getParameter("year");

        out.println("<html>");
        out.println("<body>");

        if(inname.equals("")&& !inyear.equals(null)){  //year query

            Sinem.makeQuery(inyear, response);
        }
        else if(!inname.equals(null)&& inyear.equals("")){  //name query
            Sinem.makeQueryName(inname, response);
        }

        out.println("</body>");
        out.println("</html>");
    }
}
