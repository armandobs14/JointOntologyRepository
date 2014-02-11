/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package control;

import KAO.Kao;
import joint.codegen.sioc.UserAccount;
import system.Cryptography;

/**
 *
 * @author armando
 */
public class UserController extends Controller{
    
    /**
     *
     * @param name
     * @param email
     * @param password
     * @return
     */
    public boolean signup(String name, String email,String password){
        
        Kao userAccountKao = new Kao(UserAccount.class,siocURI);
        
        StringBuilder query = new StringBuilder();
        query.append("PREFIX repo:<"+repositoryURI+"#>");
        query.append("select ?x where {?x repo:email '").append(email).append("'}");
        
        try {
            UserAccount user = (UserAccount) userAccountKao.executeQueryAsSingleResult(query.toString());
            return false;
        } catch (Exception e) {
            
             int counter = userAccountKao.retrieveAllInstances().size()+1;
            UserAccount account = userAccountKao.create("UserAccount_"+counter);

            //adicionando e-mail
            account.setEmail(email);

            //adicionando FOAF name
            account.setFoafName(name);

            //adiciona senha 
            account.setEmail_sha1(Cryptography.SHA1(password));

            userAccountKao.save();
            
            return true;
        }
    }
    
    /**
     *
     * @param email
     * @param password
     * @return
     */
    public UserAccount signin(String email, String password){
        Kao userAccountKao = new Kao(UserAccount.class, siocURI);
        
        // busca instancia com email e pa        
        StringBuilder query = new StringBuilder();
        query.append("PREFIX sioc:<"+siocURI+"#>");
        query.append("PREFIX repo:<"+repositoryURI+"#>");
        query.append("select ?x where {?x repo:email '").append(email).append("'."); 
        query.append("?x sioc:email_sha1 '").append(Cryptography.SHA1(password)).append("'");
        query.append("}");
        
        return (UserAccount) userAccountKao.executeQueryAsSingleResult(query.toString());
    }
}
