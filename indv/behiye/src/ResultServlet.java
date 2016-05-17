import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Behiye on 5/10/2016.
 */
@WebServlet("/ResultServlet")

public class ResultServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static String jsonData = null;
    static final String DRIVER = "com.mysql.jdbc.Driver";
    static final String URL = "jdbc:mysql://localhost:3306/behiye_database";
    static final String user = "root";
    static final String PASS = "";
    static Connection conn = null;
    static Statement stmt = null;

    public ResultServlet(){
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String queryLetter=request.getParameter("name");
        String queryDate=request.getParameter("date");

        try {
            DatabaseUtils.useDatabase();
            ArrayList<String> results=DatabaseUtils.makeQuery(queryLetter,queryDate);

            PrintWriter out = response.getWriter(  );
            response.setContentType("text/html");
            out.println("<h1>Result of the Query</h1>");
            out.println("  <form action=\"SavedDataServlet\" method=\"GET\">\n");
            for (int i = 0; i <results.size() ; i+=3) {
                out.println("<input type=\"checkbox\" name=\"cb\" value=\"Bike\">"+ results.get(i)+" "+results.get(i+1)+" "+results.get(i+2));
                out.println("<br>");
            }
            out.println("<input type=\"submit\" name=\"Save\">");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
