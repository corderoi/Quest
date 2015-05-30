//
// Quest
// UserServlet.java
//
// Created by Ian Cordero, Paul Grad, Michael Bonifacio, and Tim Taylor on 23 May 2015
// Copyright (C) 2015 Ian Cordero, Paul Grad, Michael Bonifacio, and Tim Taylor. All rights reserved.
//
package ProjectHeraBackend;

import ProjectHeraBackend.CategoryManager;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.SQLException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CategoryServlet extends HttpServlet implements Servlet {
    
    public CategoryServlet() {}
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Fetch GET parameters
        final String uid = request.getParameter("uid");
        
        PrintWriter out = response.getWriter();
        
        response.setContentType("application/json");
        
        final String uid = request.getPathInfo().replace("/", "");
        
        if (uid.equals("")) {
            
        } else {
            try {
                // Get user categories
                final int[] categoryIDs = CategoryManager.getUserCategories(uid);
                
                // Prepare user information
                final String[] userInfo = UserManager.getUserInfo(uid);
                
                // Return JSON containing user information
                out.println("{ \"uid\": \"" + uid + "\", \"firstName\": \"" + userInfo[0] + "\", \"lastName\": \"" + userInfo[1] + "\" }");
            } catch (SQLException se)
            {
                response.setStatus(400);
                response.resetBuffer();
                out.println("null");
            }
        }
        
        out.close();
    }
    
}