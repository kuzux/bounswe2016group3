
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.sql.Date;

@WebServlet("/DeleteData")
public class DeleteData extends HttpServlet {

    public DeleteData() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        out.println ("yavaş gel");

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        response.setContentType("text/html");

        String[] selectedRecords = request.getParameterValues("selected");

        DatabaseConnection dbcon = new DatabaseConnection();

        for (String recordDate: selectedRecords) {
            String date = recordDate;

            dbcon.unsaveData(Date.valueOf(date));
        }

        response.sendRedirect("/CMPE352/MyData");
    }

}
