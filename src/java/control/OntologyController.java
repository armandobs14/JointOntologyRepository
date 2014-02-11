/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import KAO.Kao;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
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
        System.out.println(name);
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
        Set<Tag> setTag = new HashSet<Tag>();        
        for (String tagName : tags) {
            setTag.add(TagController.getTagByTagName(tagName.trim()));
        }
        ontology.setHas_tag(setTag);
        //fechando conexao
        ontologyKao.save();
    }

    
    /**
     * 
     * @param nameOntology
     * @return A Ontology Object
     */
    public Set<String> retrieveOntologiesByName(String nameOntology) {
        Kao ontologyKao = new Kao(Ontology.class,repositoryURI);
        Set<String> ontologies = new HashSet<String>();
        StringBuilder query = new StringBuilder();
        
        query.append("PREFIX repo:<"+repositoryURI+"#>");
        query.append("PREFIX foaf:<"+foafURI+"#>");
        query.append("select ?x where {?x foaf:name '").append(nameOntology.toLowerCase()).append("'}"); 
        
        System.out.println(query.toString());
        
        List<Object> ontologiesList = ontologyKao.executeQueryAsList(query.toString());
        
        for (Object object : ontologiesList) {
            Ontology onto = (Ontology) object;
            JsonObject obj = new JsonObject();
            
            obj.addProperty("name",onto.getFoafName());
            obj.addProperty("ontologyURI",onto.getUri());
            obj.addProperty("propertyURI",onto.toString());
            obj.addProperty("filePath",onto.getFile_path());
            ontologies.add(obj.toString());
        }
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
