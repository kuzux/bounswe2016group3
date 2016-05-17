import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.Statement;

/**
 * Created by Behiye on 5/10/2016.
 */
@WebServlet("/SavedDataServlet")
public class SavedDataServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;
    private static String jsonData = null;
    static final String DRIVER = "com.mysql.jdbc.Driver";
    static final String URL = "jdbc:mysql://localhost:3306/behiye_database";
    static final String user = "root";
    static final String PASS = "";
    static Connection conn = null;
    static Statement stmt = null;

    public SavedDataServlet(){
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response){
       // request.getParameter()
    }
}
