
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.sql.Date;


@WebServlet("/SaveData")
public class SaveData extends HttpServlet {

    public SaveData() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        out.println ("ERROR");

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        String[] selectedRecords = request.getParameterValues("selected");

        DatabaseConnection dbcon = new DatabaseConnection();

        for (String recordId: selectedRecords) {
            String name = request.getParameter("name"+recordId);
            String date = request.getParameter("date"+recordId);

            DataObject dataobj = new DataObject(name, Date.valueOf(date));

            dbcon.saveData(dataobj);
        }

        response.sendRedirect("/CMPE352/MyData");
    }

}
