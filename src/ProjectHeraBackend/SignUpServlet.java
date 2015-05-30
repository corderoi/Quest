package ProjectHeraBackend;

import ProjectHeraBackend.UserManager;
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

public class SignUpServlet extends HttpServlet implements Servlet {
	
	public SignUpServlet() {}
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // Forward user to sign up page
        request.getRequestDispatcher("/signup.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        boolean inputIsValid = false;
        
        // Get POST parameters
        final String uid = request.getParameter("uid");
        final String password = request.getParameter("password");
        final String password2 = request.getParameter("password2");
        final String firstName = request.getParameter("firstName");
        final String lastName = request.getParameter("lastName");
        
        // Verify fields of input
        if (uid == null || uid.equals("")) {
            session.setAttribute("notifText", "A valid email is required.");
        } else if (uid.length() > 100) {
            session.setAttribute("notifText", "Your email must be 100 characters or less in length.");
        } else if (!UserManager.verifyEmailFormat(uid)) {
            session.setAttribute("notifText", "Please enter a valid email address.");
        } else if (UserManager.userExistsWithEmail(uid)) {
            session.setAttribute("notifText", "A user with that email already exists. Please register using a different email.");
        } else if (firstName != null && firstName.length() > 30) {
            session.setAttribute("notifText", "Your first name must be 30 characters or less in length.");
        } else if (lastName != null && lastName.length() > 30) {
            session.setAttribute("notifText", "Your last name must be 30 characters or less in length.");
        } else if (password == null || password.equals("")) {
            session.setAttribute("notifText", "A password is required.");
        } else if (password.length() > 50) {
            // Subject to change as passwords should be hashed (changing the length)
            session.setAttribute("notifText", "Your password must be 50 characters or less in length.");
        } else if (password2 == null || !password.equals(password2)) {
            session.setAttribute("notifText", "The passwords you have entered do not match.");
        } else {
            inputIsValid = true;
        }
        
        if (!inputIsValid) {
            // Send user back to signup page
            session.setAttribute("notifColor", "red");
            request.getRequestDispatcher("/signup.jsp").forward(request, response);
        } else {
            try {
                // Register user in the database
                UserManager.addUser(uid, password, firstName, lastName);
                
                session.setAttribute("notifText", "You have successfully signed up for Hera. Welcome! Please try logging in using the email and password you provided.");
                session.setAttribute("notifColor", "green");
                
                // Redirect user to login page
                response.sendRedirect("/Quest/login");
            } catch (SQLException se) {
                // SQL emitted an exception
                request.setAttribute("msg", "Critical error: Encountered SQL Exception.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
        }
    }

}