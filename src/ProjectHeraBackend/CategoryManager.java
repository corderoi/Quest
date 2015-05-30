//
// Quest
// CategoryManager.java
//
// Created by Ian Cordero, Paul Grad, Michael Bonifacio, and Tim Taylor on 23 May 2015
// Copyright (C) 2015 Ian Cordero, Paul Grad, Michael Bonifacio, and Tim Taylor. All rights reserved.
//
package ProjectHeraBackend;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.security.*;
import java.util.List;
import java.util.ArrayList;

/**
* CategoryManager
* Adapter for using the MySQL database. Deals with categories specifically.
*/
public class CategoryManager extends DbManager
{
	private CategoryManager() {}
    
    /**
    * Creates a new category represented in the database.
    * @param uid The user ID (currently the email).
    * @param name The category name.
    * @throws SQLException
    */
    public static void createCategory(String uid, String name) throws SQLException
    {
        Connection connection = null;
        PreparedStatement statement = null;
        
        connection = getConnection(false);
        String query = "INSERT INTO Categories (uid, name) VALUES (?, ?);";
        statement = connection.prepareStatement(query);
        statement.setString(1, uid);
        statement.setString(2, name);
        statement.execute();
        
        connection.close();
    }
    
    /**
     * Removes the category with ID cid from the database.
     * @param cid The unique category ID.
     * @throws SQLException
     */
    public static void removeCategory(int cid) throws SQLException
    {
        Connection connection = null;
        PreparedStatement statement = null;
        connection = getConnection(false);
        
        // Remove quests from category
        String query = "DELETE FROM Quests WHERE cid=" + cid + ";";
        statement = connection.prepareStatement(query);
        statement.executeUpdate();
        
        // Remove category itself
        query = "DELETE FROM Categories WHERE cid=" + cid + ";";
        statement = connection.prepareStatement(query);
        statement.executeUpdate();
        connection.close();
    }
    
    /**
     * Fetches an array of user's category IDs based on a search using a given unique ID.
     * @param uid user's email
     * @return An array of user's category IDs
     * @throws SQLException
     */
    public static int[] getUserCategories(String uid) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet results;
        List<Integer> categoryIDs = new ArrayList<Integer>();
        
        connection = getConnection(true);
        String query = "SELECT cid FROM Categories WHERE uid='" + uid + "';";
        statement = connection.prepareStatement(query);
        results = statement.executeQuery();
        
        while (results.next()) {
            categoryIDs.add(results.getInt("cid"));
        }
        
        connection.close();
        
        Integer[] returnSet = new Integer[categoryIDs.size()];
        categoryIDs.toArray(returnSet);
        int[] realReturnSet = new int[categoryIDs.size()];
        for (int i = 0; i < categoryIDs.size(); i++) {
            realReturnSet[i] = returnSet[i].intValue();
        }
        return realReturnSet;
    }
    
    /**
     * Fetches an array of category's information based on a search using a given unique ID
     * @param cid category's ID
     * @return An array of category's information
     * @throws SQLException
     */
    public static String[] getCategoryInfo(int cid) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet results;
        String categoryName = "";
        String categoryEmail = "";
        
        connection = getConnection(true);
        String query = "SELECT * FROM Categories WHERE cid=" + cid + ";";
        statement = connection.prepareStatement(query);
        results = statement.executeQuery();
        if (results.next()) {
            categoryName = results.getString("name");
            categoryEmail = results.getString("uid");
        }
        connection.close();
        return new String[]{ categoryName, categoryEmail };
    }
}