//
// Quest
// QuestManager.java
//
// Created by Ian Cordero, Paul Grad, Michael Bonifacio, and Tim Taylor on 23 May 2015
// Copyright (C) 2015 Ian Cordero, Paul Grad, Michael Bonifacio, and Tim Taylor. All rights reserved.
//
package ProjectHeraBackend;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.sql.Timestamp;
import java.security.*;
import java.util.List;
import java.util.ArrayList;
import java.sql.Types.*;

/**
* QuestManager
* Adapter for using the MySQL database. Deals with quests specifically.
*/
public class QuestManager extends DbManager
{
	private QuestManager() {}
    
    /**
     * Adds a quest to a category represented in the database. The category must
     * already exist.
     * @param cid The category ID.
     * @param name The quest name
     * @param urgency A String describing the urgency of the task
     * @param dueDate Timestamp representing the due date, or null
     * @param timeLimit Milliseconds until task is due, or null
     * @param nqid Next Quest ID, or null
     * @param cqid Child Quest ID, or null
     * @throws SQLException
     */
    public static void addQuestToCategory(int cid, String name, String urgency, Date date, Integer timeLimit, Integer nqid, Integer cqid) throws SQLException
    {
        Connection connection = null;
        PreparedStatement statement = null;
        
        connection = getConnection(false);
        String query = "INSERT INTO Quests (cid, name, urgency, dueDate, timeLimit, nqid, cqid) VALUES (?, ?, ?, ?, ?, ?, ?);";
        statement = connection.prepareStatement(query);
        statement.setInt(1, cid);
        statement.setString(2, name);
        statement.setString(3, urgency);
        statement.setTimestamp(4, (date == null) ? null : new java.sql.Timestamp(date.getTime()));
        if (timeLimit == null) { statement.setNull(5, java.sql.Types.INTEGER); } else { statement.setInt(5, timeLimit); }
        if (nqid == null) { statement.setNull(6, java.sql.Types.INTEGER); } else { statement.setInt(6, nqid); };
        if (cqid == null) { statement.setNull(7, java.sql.Types.INTEGER); } else { statement.setInt(7, cqid); };
        statement.execute();
        
        connection.close();
    }
    
    /**
     * Removes the quest matching quest ID qid.
     * @param qid The quest ID.
     * @throws SQLException
     */
    public static void removeQuest(int qid) throws SQLException
    {
        Connection connection = null;
        PreparedStatement statement = null;
        
        connection = getConnection(false);
        String query = "DELETE FROM Quests WHERE qid='" + qid + "';";
        statement = connection.prepareStatement(query);
        statement.executeUpdate();
        connection.close();
    }
    
    /**
     * Fetches an array of category's quest IDs based on a search using a given unique category ID.
     * @param cid category's id
     * @return An array of category's quest IDs
     * @throws SQLException
     */
    public static int[] getCategoryQuests(int cid) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet results;
        List<Integer> questIDs = new ArrayList<Integer>();
        
        connection = getConnection(true);
        String query = "SELECT qid FROM Quests WHERE cid='" + cid + "';";
        statement = connection.prepareStatement(query);
        results = statement.executeQuery();
        
        while (results.next()) {
            questIDs.add(results.getInt("qid"));
        }
        
        connection.close();
        
        Integer[] returnSet = new Integer[questIDs.size()];
        questIDs.toArray(returnSet);
        int[] realReturnSet = new int[questIDs.size()];
        for (int i = 0; i < questIDs.size(); i++) {
            realReturnSet[i] = returnSet[i].intValue();
        }
        return realReturnSet;
    }
    
    /**
     * Fetches an array of quest's information based on a search using a given unique ID
     * @param qid quest's ID
     * @return An array of quest's information
     * @throws SQLException
     */
    public static String[] getQuestInfo(int qid) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet results;
        final int returnSetSize = 9;
        String[] returnSet = new String[returnSetSize];
        
        connection = getConnection(true);
        String query = "SELECT * FROM Quests WHERE qid=" + qid + ";";
        statement = connection.prepareStatement(query);
        results = statement.executeQuery();
        if (results.next()) {
            final String[] returnSetCols = new String[]{ "qid", "cid", "name", "updated", "urgency", "dueDate", "timeLimit", "nqid", "cqid" };
            for (int i = 0; i < returnSetSize; i++) {
                returnSet[i] = results.getString(returnSetCols[i]);
            }
        }
        connection.close();
        return returnSet;
    }
}