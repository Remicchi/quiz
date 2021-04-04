// To save as "ebookshop\WEB-INF\classes\QueryServlet.java".
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/main")   // Configure the request URL for this servlet (Tomcat 7/Servlet 3.0 upwards)
public class Main extends HttpServlet {

   // The doGet() runs once per HTTP GET request to this servlet.
   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response)
               throws ServletException, IOException {
      int a = 0;
      int b = 0;
      int c = 0;
      int d = 0;
      // Set the MIME type for the response message
      response.setContentType("text/html");
      // Get a output writer to write the response message into the network socket
      PrintWriter out = response.getWriter();
      // Print an HTML page as the output of the query
      try (
         // Step 1: Allocate a database 'Connection' object
         Connection conn = DriverManager.getConnection(
               "jdbc:mysql://localhost:3306/quiz?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
               "myuser", "xxxx");   // For MySQL
               // The format is: "jdbc:mysql://hostname:port/databaseName", "username", "password"

         // Step 2: Allocate a 'Statement' object in the Connection
         Statement stmt = conn.createStatement();
      ) {

        String sqlStr = "SELECT * FROM responses";
        out.println("<h3>Query:</h3>");
        out.println("<div id= 'sqlstatement'> <p>" + sqlStr + "</p> </div>"); // Echo for debugging
        ResultSet rset = stmt.executeQuery(sqlStr);  // Send the query to the server

        // Step 4: Process the query result set
        while (rset.next()) {
          String choice = rset.getString("choice");

          if(choice.equals("a")){
          out.println(choice);
          a++;
          }else if(choice.equals("b")){
          out.println(choice);
          b++;
          }else if(choice.equals("c")){
          out.println(choice);
          c++;
          }else if(choice.equals("d")){
          out.println(choice);
          d++;
          }
          out.println(d);
        }

      out.println("<html>");
      out.println("<head><title>Search</title><link rel='stylesheet' href='styles.css' />");
      out.println("<title>Bar Graph Test</title>");
      out.println("<link rel =\"stylesheet\" href=\"style.css\">");

      out.println("<script type=\"text/javascript\" src=\"https://www.gstatic.com/charts/loader.js\"></script>");
      out.println("<script type=\"text/javascript\">");

      out.println("google.charts.load('current', {packages: ['corechart', 'bar']});");
      out.println("google.charts.setOnLoadCallback(drawMaterial);");

      out.println("function drawMaterial() {");
      out.println("var data = google.visualization.arrayToDataTable([");
      out.println("  ['Option', 'Number of Responses'],");
      out.println("  ['A'," + a +"],");
      out.println("  ['B'," + b +"],");
      out.println("  ['C'," + c +"],");
      out.println("  ['D'," + d +"],");
      out.println("]);");

      out.println("var materialOptions = {");
      out.println(" chart: {");
      out.println("    title: 'Responses'");
      out.println("  },");
      out.println("  hAxis: {");
      out.println("    title: 'Total Responses',");
      out.println("    minValue: 0,");
      out.println("  },");
      out.println("  vAxis: {");
      out.println("    title: 'City'");
      out.println("  },");
      out.println("  bars: 'horizontal'");
      out.println("};");
      out.println("var materialChart = new google.charts.Bar(document.getElementById('chart_div'));");
      out.println("materialChart.draw(data, materialOptions);");
      out.println("}");
      out.println("</script>");
      out.println("  </head>");
      out.println("<body>");

      
         // Step 3: Execute a SQL SELECT query
          // Returns an array of Strings

        out.println("<div id = 'mainTitle'> <div id ='titleText'> <h1 class='centerText'>Yet Another Game Shop</h1> </div> </div>");


        out.println("<div id='mainContainer'>");


        sqlStr = "SELECT * FROM questions";
        out.println("<h3>Query:</h3>");
        out.println("<div id= 'sqlstatement'> <p>" + sqlStr + "</p> </div>"); // Echo for debugging
        rset = stmt.executeQuery(sqlStr);  // Send the query to the server

        // Step 4: Process the query result set
        while (rset.next()) {
          out.println("Question" + rset.getString("questionNo"));
          out.println("Question" + rset.getString("question"));
          out.println("Question" + rset.getString("optionA"));
          out.println("Question" + rset.getString("optionB"));
          out.println("Question" + rset.getString("optionC"));
          out.println("Question" + rset.getString("optionD"));
        }

        
      } catch(Exception ex) {
        out.println("<p>Error: " + ex.getMessage() + "</p>");
        out.println("<p>Check Tomcat console for details.</p>");
        ex.printStackTrace();
      }  // Step 5: Close conn and stmt - Done automatically by try-with-resources (JDK 7)
      

      out.println("<div id=\"chart_div\" style=\"width: 900px; height: 300px;\"></div>");
      out.println("</body></html>");
      out.close();
   }

   public void doPost (HttpServletRequest request, HttpServletResponse response)
                   throws ServletException, IOException {
   doGet(request, response);  // Re-direct POST request to doGet()
   }
}