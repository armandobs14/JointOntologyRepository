/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import control.ForumController;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author Williams
 */
@WebService(serviceName = "ForumOperations")
public class ForumOperations {
    
    private ForumController forumController = new ForumController();

    /**
     * This method creates a question in the Forum.
     * @param userAccount
     * @param message
     */
    @WebMethod(operationName = "createQuestion")
    public void createQuestion(@WebParam(name = "userAccount") String userAccount, @WebParam(name = "message") String message) {
    }

    /**
     * This method retrive the messages of a question
     * @param questionURI
     * @return
     */
    @WebMethod(operationName = "retrieveMessages")
    public ArrayList<String> retrieveMessages(@WebParam(name = "questionURI") String questionURI) {
        ArrayList<String> messages = new ArrayList<String>();
        return messages;
    }
    
    /**
     * This method create a answer
     * @param userAccount
     * @param questionURI
     * @param message
     */
    @WebMethod(operationName = "createAnswer")
    public void createAnswer(@WebParam(name = "userAccount") String userAccount,@WebParam(name = "questionURI") String questionURI,@WebParam(name = "message") String message){
    }
    
    /**
     * This method return a set of questions
     * @param query
     * @return
     */
    @WebMethod(operationName = "searchQuestion")
    public Set<String> searchQuestion(@WebParam(name="query") String query){
        return  new HashSet<String>();
    }
}
