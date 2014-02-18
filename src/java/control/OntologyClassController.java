/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package control;

import KAO.Kao;
import joint.codegen.ns4ca56aaf.OntologyClass;

/**
 *
 * @author armando
 */
public class OntologyClassController extends Controller{
    /**
     *
     * @param ontologyClassURI
     * @return a OntologyClass object
     */
    public static OntologyClass getOntologyClassByURI(String ontologyClassURI) {
        Kao ontologyClassKao = new Kao(OntologyClass.class,repositoryURI);
        
        
        StringBuilder query = new StringBuilder();
        query.append("PREFIX repo:<"+repositoryURI+"#>");
        query.append("select ?x where {?x repo:uri '").append(ontologyClassURI).append("'}"); 
        
        try {
            // Recupera classe pela URI
            OntologyClass ontologyClass = (OntologyClass) ontologyClassKao.executeQueryAsSingleResult(query.toString());
            return ontologyClass;
        } catch (Exception e) {
            // Cria classe na base e retorna
            String className = null;
            ontologyClassURI.replace("#","/");
            ontologyClassURI.replace(";","/");
            
            String[] splitedURI = ontologyClassURI.split("/");
            int splitSize = splitedURI.length;
            className = splitedURI[splitSize -1];
            
            int counter = ontologyClassKao.retrieveAllInstances().size() +1;
            
            OntologyClass ontologyClass  = ontologyClassKao.create("ontologyClass_"+counter);
            ontologyClass.setFoafName(className);
            ontologyClass.setUri(ontologyClassURI);
            
            ontologyClassKao.save();
            return ontologyClass;
            //ontologyClassKao.create(siocURI);
        }
    }
}
