//
// Quest
// UserManager.java
//
// Created by Ian Cordero, Paul Grad, Michael Bonifacio, and Tim Taylor on 23 May 2015
// Copyright (C) 2015 Ian Cordero, Paul Grad, Michael Bonifacio, and Tim Taylor. All rights reserved.
//
package ProjectHeraBackend;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
* UserManager
* Adapter for using the MySQL database. Deals with user login specifically.
*/
public class UserManager extends DbManager
{
	private UserManager() {}
    
    /**
    * Hashes the provided password using the SHA-2 encryption algorithm.
    * @param password The password to be hashed
    * @return String representing the hashed password. Empty string if failure occurs.
    */
    private static String hashPassword(String password) {
        // NONE OF THIS WORKS
        /*MessageDigest sha256;
        String hashedPassword = "";
        try {
            sha256 = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = sha256.digest(password.getBytes("UTF-8"));
            for (byte b : hashedBytes) {
                hashedPassword += (char) b;
            }
        } catch (Exception e) {
        }
        return hashedPassword;*/
        return password;
    }
    
	/**
    * Adds the specified user information to the database.
    * @param uid New user's email address
	* @param firstName New user's first name
	* @param lastName New user's last name
	* @param password New user's password
	* @return void
	* @throws SQLException
    */
    public static void addUser(String uid, String password, String firstName, String lastName) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
		
		connection = getConnection(false);
		String query = "INSERT INTO Users (uid, password, firstName, lastName) VALUES (?, ?, ?, ?);";
		statement = connection.prepareStatement(query);
		statement.setString(1, uid);
        statement.setString(2, hashPassword(password));
		statement.setString(3, firstName);
		statement.setString(4, lastName);
		statement.execute();
		connection.close();
    }
    
	/**
    * Returns true if the user email and password are valid, false otherwise.
    * @param uid user's email
	* @param password user's password
	* @return if given email matches given password
	* @throws SQLException
    */
    public static boolean userIsValid(String uid, String password) throws SQLException {
        boolean isValid = false;
        Connection connection = null;
		PreparedStatement statement = null;
		ResultSet results;
		
		connection = getConnection(true);
		String query = "SELECT password FROM Users WHERE uid='" + uid + "'";
		statement = connection.prepareStatement(query);
		results = statement.executeQuery();
		if (results.next()) {
            if (hashPassword(password).equals(results.getString("password"))) {
				isValid = true;
            }
		}
		connection.close();
        return isValid;
    }
    
    /**
    * Determines if a user with the specified email already exists in the database.
    * @param uid User's email
    * @return true if a user with the email exists in the database, false otherwise.
    */
    public static boolean userExistsWithEmail(String uid) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet results;
        
        try {
            connection = getConnection(true);
            String query = "SELECT * FROM Users WHERE uid='" + uid + "'";
            statement = connection.prepareStatement(query);
            results = statement.executeQuery();
            if (results.next()) {
                return true;
            }
            connection.close();
        } catch (SQLException se) {
        }
        
        return false;
    }
    
    /**
     * Verify the format of a given email string.
     * Virtually every email I know of has 1 or more characters, then an '@' character,
     * then 1 or more characters, a '.', then 1 or more characters. So this check
     * will assume the string is formatted as such. Note that '.' can actually appear
     * before '@' -- we just want at least one '.' to occur after the '@'.
     * @param email The string to be verified
     * @return true if the email string is formatted soundly, false otherwise.
     */
    public static boolean verifyEmailFormat(String email)
    {
        boolean encounteredOneChar = false;
        boolean encounteredAt = false;
        boolean encounteredAnotherChar = false;
        boolean encounteredDot = false;
        boolean encounteredLastChar = false;
        
        for (char c : email.toCharArray()) { // for (int i = 0; i < email.length; i++) { char c = email[i];
            switch (c) {
                case '@':
                    if (!encounteredOneChar) {
                        return false;
                    } else if (!encounteredAt) {
                        encounteredAt = true;
                    }
                    break;
                case '.':
                    if (encounteredAt) {
                        if (encounteredAnotherChar) {
                            encounteredDot = true;
                        } else {
                            return false;
                        }
                    }
                    break;
                default:
                    if (!encounteredOneChar) {
                        encounteredOneChar = true;
                    } else if (encounteredAt && !encounteredAnotherChar) {
                        encounteredAnotherChar = true;
                    } else if (encounteredDot && !encounteredLastChar) {
                        encounteredLastChar = true;
                    }
                    break;
            }
        }
        
        return (encounteredOneChar && encounteredAt && encounteredAnotherChar && encounteredDot && encounteredLastChar);
    }
    
	/**
    * Fetches an array of user's information based on a search using a given unique ID
    * @param uid user's email
	* @return An array of user's informations
	* @throws SQLException
    */
    public static String[] getUserInfo(String uid) throws SQLException {
        Connection connection = null;
		PreparedStatement statement = null;
		ResultSet results;
        String firstName = "";
        String lastName = "";
        
		connection = getConnection(true);
		String query = "SELECT * FROM Users WHERE uid='" + uid + "'";
		statement = connection.prepareStatement(query);
		results = statement.executeQuery();
		if(results.next()) {
			firstName = results.getString("firstName");
			lastName = results.getString("lastName");
		}
		connection.close();
        return new String[]{ firstName, lastName };
    }
}