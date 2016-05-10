
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.ArrayList;


@WebServlet("/MyData")
public class MyData extends HttpServlet {

    public MyData() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        DatabaseConnection dbcon = new DatabaseConnection();

        ArrayList<DataObject> data = dbcon.getSavedData();

        PrintWriter out = response.getWriter();

        out.println (
                "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" +" +
                        "http://www.w3.org/TR/html4/loose.dtd\">" +
                        "<html>" +
                        "<head>" +
                        "<meta http-equiv=\"Content-Type\" content=\"text/html; " +
                        "charset=ISO-8859-1\">" +
                        "<title> Academy Award Winners </title>" +
                        "</head>" +
                        "<body> <div align='center'>" +
                        "<p><a href='/CMPE352'>Homepage</a></p>"+
                        "<h1>My Saved Data</h1>"+
                        "<form action='/CMPE352/DeleteData' method='post'>" +
                        "<table>"+
                        "<tr>"+
                        "<th></th>"+
                        "<th>Award Winner</th>"+
                        "<th>Date</th>"+
                        "</tr>"
        );
        int i=0;
        for (DataObject dataelement:data) {
            i++;
            out.println(
                    "<tr>"+
                            "<td>"+
                            "<input type='checkbox' name='selected' value='"+dataelement.date+"'>"+
                            "</td>"+
                            "<td>"+
                            dataelement.name+
                            "</td>"+
                            "<td>"+
                            dataelement.date+
                            "</td>"+
                            "</tr>"
            );
        }

        out.println(
                "</table>"+
                        "<input type='submit' value='Delete'>"+
                        "</form>"+
                        "</body>"+
                        "</html>"
        );


    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

}
