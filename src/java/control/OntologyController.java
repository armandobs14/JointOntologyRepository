/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import KAO.Kao;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import joint.codegen.ns4ca56aaf.Ontology;
import joint.codegen.ns4ca56aaf.OntologyClass;
import joint.codegen.sioc.UserAccount;
import joint.codegen.types.Tag;

/**
 *
 * @author Judson
 */
public class OntologyController extends Controller{
    private int contador;
    
    //metodo que salva uma nova ontologia no banco

    /**
     * This method save a ontology on database
     * @param name
     * @param ontologyURI
     * @param filepath
     * @param comment
     * @param developersEmail
     * @param classes
     * @param tags
     */
        public void saveOntology(String name, String ontologyURI, String filepath, Set<String> comment, Set<String> developersEmail, Set<String> classes, Set<String> tags){
        //abrindo conexao
        //Testando
        Kao ontologyKao = new Kao(Ontology.class,repositoryURI); 
        //contador para a criacao do nome na instancia
        contador = ontologyKao.retrieveAllInstances().size();
        contador++;
        //classe Ontology da ontologia do repositorio
        Ontology ontology = ontologyKao.create("ontology_" + contador);
        //setando suas propriedades
        //setando nome
        ontology.setFoafName(name);
        
        //setando uri
        ontology.setUri(ontologyURI);
        
        //setando versao
        StringBuilder query = new StringBuilder();
        query.append("PREFIX repo:<"+repositoryURI+"#>");
        query.append("select ?x where {?x repo:uri '").append(ontologyURI).append("'}"); 
        List ontologiesList = ontologyKao.executeQueryAsList(query.toString());
        
        ontology.setVersion(ontologiesList.size());
        
        //setando caminho
        ontology.setFile_path(filepath);
        //setando comentario
        ontology.setComment(comment);
        //setando criador da ontologia    
        
        // TODO: implementar busca de usuários
        Set<UserAccount> setUserAccount = new HashSet<UserAccount>();
        ontology.setHas_creator(setUserAccount);
        //setando conjunto de classes
        Set<OntologyClass> setClass = new HashSet<OntologyClass>();
        for (String classURI : classes) {
            setClass.add(OntologyClassController.getOntologyClassByURI(classURI));
        }
        ontology.setHas_class(setClass);
        //setando tags (area)
        //Set<Tag> setTag = new HashSet<Tag>();
        //String[] tagList = tags.split(",");
        //    for (String tagName : tagList) {
        //        setTag.add(TagController.getTagByTagName(tagName));        
        //}
        //ontology.setHas_tag(setTag);
        System.out.println(tags);
        //fechando conexao
        ontologyKao.save();
    }

    
    /**
     * 
     * @param nameOntology
     * @return A Ontology Object
     */
    public Set<String> retrieveOntologiesByName(String nameOntology) {
//TODO: retornar todas as ontologias com aquele nome. Utilizar o retrieve all instances.
        Set<String> ontologies = new HashSet<String>();
        ontologies.add("onto1");
        ontologies.add("onto2");
        //return "ontology retrieved!!!!";
        return ontologies;
     }
    
    /**
     *This method retrieve the filepath of a ontology
     * @param ontologyURI
     * @return
     */
    public String retrieveOntologyByURI(String ontologyURI){
        return "ontology";
    }
    
    
    public String checkOntologyByURI(String ontologyURI){
//TODO: verificar a consistência da ontologia. Utilizar outra ferramenta sem ser o joint.        
        return "Está consistente!";
    }
    

    public Set<String> getOntologyComments(String ontologyURI) {
//TODO: retornar todos os comentarios daquela ontologia buscada pelo nome.
       return new HashSet();
    }
    
    
    
}
