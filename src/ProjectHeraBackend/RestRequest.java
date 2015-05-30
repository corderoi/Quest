//
// Quest
// RestServlet.java
//
// Created by Ian Cordero, Paul Grad, Michael Bonifacio, and Tim Taylor on 23 May 2015
// Copyright (C) 2015 Ian Cordero, Paul Grad, Michael Bonifacio, and Tim Taylor. All rights reserved.
//
package ProjectHeraBackend;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * RestRequest
 * Process URL for a REST API implementation.
 */
public class RestRequest
{
    private Pattern regExAllPattern;
    private Pattern regExIdPattern;
    
    private Integer id;
    
    public RestRequest(String pathInfo, String storeName, boolean numericalOnly) throws ServletException {
        final String regExIDMatcher = (numericalOnly) ? "[0-9]" : ".";
        regExAllPattern = Pattern.compile("/" + storeName);
        regExIdPattern = Pattern.compile("/" + storeName + "/(" + regExIDMatcher + "*)");
        
        // regex parse pathInfo
        Matcher matcher;
        
        // Check for ID case first, since the All pattern would also match
        matcher = regExIdPattern.matcher(pathInfo);
        if (matcher.find()) {
            id = Integer.parseInt(matcher.group(1));
            return;
        }
        
        matcher = regExAllPattern.matcher(pathInfo);
        if (matcher.find()) return;
        
        throw new ServletException("Invalid URI: " + pathInfo);
    }
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
}