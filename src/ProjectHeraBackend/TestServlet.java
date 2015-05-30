package ProjectHeraBackend;

import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ProjectHeraBackend.UserManager;
import ProjectHeraBackend.CategoryManager;
import ProjectHeraBackend.QuestManager;
import java.sql.SQLException;

public class TestServlet extends HttpServlet implements Servlet {
       
    public TestServlet() {}
    
    private String testDBManagers()
    {
        String debugMessage = "";
        
    //UserManager
        // addUser()
        try {
            UserManager.addUser("icord3028@yahoo.com", "password1", "Ian", "Cordero");
        } catch (SQLException se) {
            return "Failed addUser() test.; ";
        }
        
        // userIsValid()
        try {
            if (!UserManager.userIsValid("icord3028@yahoo.com", "password1")) {
                throw new SQLException();
            }
        } catch (SQLException se) {
            return "Failed userIsValid() test.; ";
        }
        
        // userExistsWithEmail()
        try {
            if(!UserManager.userExistsWithEmail("icord3028@yahoo.com")){
                throw new SQLException();
            }
            
        } catch (SQLException se) {
            return "Failed userExistsWithEmail() test.; ";
        }

        //getUserInfo()
        try {
            final String[] userInfo = UserManager.getUserInfo("icord3028@yahoo.com");
            debugMessage += "User Info: ";
            for (int i = 0; i < userInfo.length; i++) {
                debugMessage += userInfo[i];
            }
        } catch(SQLException se) {
            return "Failed getUserInfo() test.; ";
        }

    //CategoryManager
        //createCategory()
        try {
            CategoryManager.createCategory("icord3028@yahoo.com", "PlayPokemon");
        } catch (SQLException se) {
            return "Failed createCategory() test.; ";
        }

        //removeCategory()
        try{
            CategoryManager.removeCategory(1);
            CategoryManager.createCategory("icord3028@yahoo.com", "PlayPokemon");
        } catch (SQLException se) {
            return "Failed removeCategory() test.; "; //checks first category
        }

        //getUserCategories()
        try{
            CategoryManager.getUserCategories("icord3028@yahoo.com");
        } catch (SQLException se) {
            return "Failed getUserCategories() test.; ";
        }

        //getCategoryInfo()
        try{
            final String[] categoryInfo = CategoryManager.getCategoryInfo(2); //checks first category
            debugMessage += " categoryInfo: ";
            for (int i = 0; i < categoryInfo.length; i++) {
                debugMessage += categoryInfo[i];
            }
        } catch (SQLException se) {
            return "Failed getCategoryInfo() test.; ";
        }

    //QuestManager
        //addQuestToCategory()
        try{
            QuestManager.addQuestToCategory(2, "BattleRed", "urgent", null, null, null, null);
        } catch (SQLException se) {
            return "Failed addQuestToCategory() test. " + se.getMessage() + se.getCause();
        }

        //removeQuestFromCategory()
        try{
            QuestManager.removeQuest(1);
            QuestManager.addQuestToCategory(2, "BattleRed", "urgent", null, null, null, null);
        }catch(SQLException se){
            return "Failed removeQuestFromCategory() test.";
        }

        //getCategoryQuests()
        try{
            QuestManager.getCategoryQuests(2);
        }catch(SQLException se){
            return "Failed getCategoryQuests() test.";
        }

        //getQuestInfo()
        try{
            final String[] questInfo = QuestManager.getQuestInfo(2);
            debugMessage += " questInfo: ";
            for (int i = 0; i < questInfo.length; i++) {
                debugMessage += questInfo[i];
            }
        }catch(SQLException se){
            return "Failed getQuestInfo() test.";
        }
        
        // Clean up
        /*try {
            
        } catch (SQLException se) {
            
        }*/
        
        debugMessage += " Passed all tests!";
        
        return debugMessage;
    }
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // Test DB Managers
        final String debugMessage = testDBManagers();
        
        // Set variables in request
        request.setAttribute("debugMessage", debugMessage);
        
        // Forward page
        request.getRequestDispatcher("/test.jsp").forward(request, response);
    }
}