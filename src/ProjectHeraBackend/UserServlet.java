//
// Quest
// UserServlet.java
//
// Created by Ian Cordero, Paul Grad, Michael Bonifacio, and Tim Taylor on 23 May 2015
// Copyright (C) 2015 Ian Cordero, Paul Grad, Michael Bonifacio, and Tim Taylor. All rights reserved.
//
package ProjectHeraBackend;

import ProjectHeraBackend.RestRequest;
import ProjectHeraBackend.UserManager;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.SQLException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserServlet extends HttpServlet implements Servlet {
    
    public UserServlet() {}
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        
        response.setContentType("application/json");
        
        //out.println("GET request handling");
        //out.println(request.getPathInfo());
        //out.println(request.getParameterMap());
        final String uid = request.getPathInfo().replace("/", "");
        /*try {
            RestRequest resourceValues = new RestRequest(request.getPathInfo(), "users", false);
            out.println(resourceValues.getId());
        } catch (ServletException e) {
            response.setStatus(400);
            response.resetBuffer();
            e.printStackTrace();
            out.println(e.toString());
        }*/
        if (uid.equals("")) {
            
        } else {
            try {
                // Verify user exists
                if (!UserManager.userExistsWithEmail(uid)) {
                    throw new SQLException();
                }
                
                // Prepare user information
                final String[] userInfo = UserManager.getUserInfo(uid);
                
                // Return JSON containing user information
                out.println("{ \"uid\": \"" + uid + "\", \"firstName\": \"" + userInfo[0] + "\", \"lastName\": \"" + userInfo[1] + "\" }");
            } catch (SQLException se) {
                response.setStatus(400);
                response.resetBuffer();
                out.println("null");
            }
        }
        
        out.close();
    }
    
}