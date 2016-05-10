
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.ArrayList;


@WebServlet("/ShowResults")
public class ShowResults extends HttpServlet {

    public ShowResults() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        int minyear, maxyear;

        if (request.getParameter("minyear") != "" && request.getParameter("minyear") != null ) {
            minyear = Integer.parseInt(request.getParameter("minyear"));
        } else {
            minyear = 0;
        }

        if (request.getParameter("maxyear") != "" && request.getParameter("maxyear") != null) {
            maxyear = Integer.parseInt(request.getParameter("maxyear"));
        } else {
            maxyear = 2017;
        }

        ArrayList<DataObject> data = getWithYear(minyear, maxyear);

        PrintWriter out = response.getWriter();

        out.println (
                "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" +" +
                        "http://www.w3.org/TR/html4/loose.dtd\">" +
                        "<html>" +
                        "<head>" +
                        "<meta http-equiv=\"Content-Type\" content=\"text/html; " +
                        "charset=ISO-8859-1\">" +
                        "<title> Academy Award Winners  </title>" +
                        "</head>" +
                        "<body> <div align='center'>" +
                        "<form action='/CMPE352/SaveData' method='post'>" +
                        "<h1>Filter Results</h1>"+
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
                    "<input type='checkbox' name='selected' value='"+i+"'>"+
                    "<input type='hidden' name='date"+i+"' value='"+dataelement.date+"'>"+
                    "<input type='hidden' name='name"+i+"' value='"+dataelement.name+"'>"+
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
                "<input type='submit' value='Save'>"+
                "</form>"+
                "</body>"+
                "</html>"
        );


    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    public ArrayList<DataObject> getWithYear(int minyear, int maxyear) {

        DatabaseConnection dbcon = new DatabaseConnection();

        return dbcon.makeQuery(minyear,maxyear);

    }

}
