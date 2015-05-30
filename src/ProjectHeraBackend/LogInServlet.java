package ProjectHeraBackend;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import ProjectHeraBackend.UserManager;
import ProjectHeraBackend.CategoryManager;

public class LogInServlet extends HttpServlet implements Servlet
{
	public LogInServlet() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // Forward user to login page
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        
        // Get POST parameters
        final String uid = request.getParameter("uid");
        final String password = request.getParameter("password");
        
        try {
            if (UserManager.userIsValid(uid, password)) {
                // Set session UID to indicate the user being logged in
                session.setAttribute("uid", uid);
                
                // Redirect user to home page
                response.sendRedirect("/Quest/");
            } else {
                // Incorrect login -- set notifText
                session.setAttribute("notifText", "The username or password is incorrect.");
                session.setAttribute("notifColor", "red");
                
                // Forward user back to login page
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
        } catch (SQLException se) {
            request.setAttribute("errorMessage", "Critical error: Encountered SQL exception.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
        
    }
}