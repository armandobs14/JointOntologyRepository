/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;


import com.google.gson.JsonObject;
import control.OntologyController;
import java.util.Set;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import joint.codegen.ns4ca56aaf.Ontology;



/**
 *
 * @author Judson
 */
@WebService(serviceName = "ontologyoperations")
public class OntologyOperations {
    
    private OntologyController ontologyController = new OntologyController();

    /**
     *This method retrieve ontologyURI
     * @param ontologyName
     * @return Set of ontologyURI
     */
    @WebMethod(operationName = "retrieveOntologiesByName")
    public Set<String> retrieveOntologiesByName(@WebParam(name = "ontologyName") String ontologyName)
    {
        return ontologyController.retrieveOntologiesByName(ontologyName);
    }
    
    /**
     *
     * @param ontologyName
     * @param ontologyURI
     * @param ontologyFilePath
     * @param comment
     * @param developersEmail
     * @param has_class
     * @param tags
     */
    @WebMethod(operationName = "saveOntology")
    public void saveOntology(@WebParam(name = "ontologyName") String ontologyName,@WebParam(name="ontologyURI") String ontologyURI,@WebParam(name="ontologyFilePath") String ontologyFilePath,@WebParam(name="ontologyComment") Set<String> comment ,@WebParam(name="developersEmail") Set<String> developersEmail,@WebParam(name="has_class") Set<String> has_class,@WebParam(name="tags") Set<String> tags) {
        ontologyController.saveOntology(ontologyName,ontologyURI ,ontologyFilePath, comment, developersEmail , has_class, tags);
    }
    
    @WebMethod(operationName = "checkOntology")
    public void checkOntology(@WebParam(name = "ontologyName") String ontologyURI) {
        ontologyController.checkOntologyByURI(ontologyURI);
    }
    
    @WebMethod(operationName = "getOntologyComments")
    public void getOntologyComments(@WebParam(name = "ontologyURI") String ontologyURI) {
        ontologyController.getOntologyComments(ontologyURI);
    }
    
    
    @WebMethod(operationName = "retirveOntologyByURI")
    public String retrieveOntologyByURI(String ontologyURI){
       return ontologyController.retrieveOntologyByURI(ontologyURI);
    }
    
}
