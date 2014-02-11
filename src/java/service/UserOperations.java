/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package service;

import com.google.gson.JsonObject;
import control.UserController;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import joint.codegen.sioc.UserAccount;

/**
 *
 * @author armando
 */
@WebService(serviceName = "UserOperations")
public class UserOperations {
    private UserController userController = new UserController();

    /**
     * This is a sample web service operation
     * @param name
     * @param email
     * @param password
     */
    @WebMethod(operationName = "signup")
    public boolean signup(@WebParam(name="name") String name,@WebParam( name="email") String email,@WebParam(name="password") String password){     
        return userController.signup(name, email, password);
    }
    
    @WebMethod(operationName = "signin")
    public String signin(@WebParam( name="email") String email, @WebParam(name="password") String password){
        try {
            UserAccount account = userController.signin(email,password);
            
            JsonObject response = new JsonObject(); 
            response.addProperty("name", account.getFoafName());
           response.addProperty("email",account.getEmail());
           response.addProperty("resource",account.toString());
            return response.toString();
        } catch (Exception e) {
            return null;
        }
    }
    
    @WebMethod(operationName = "signout")
    public void signout(){
        
    }
        
    
}
