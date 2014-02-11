/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import KAO.Kao;
import java.util.HashSet;
import java.util.Set;
import joint.codegen.ns4ca56aaf.Ontology;
import joint.codegen.ns4ca56aaf.OntologyClass;
import joint.codegen.sioc.UserAccount;

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
        Ontology ontology = ontologyKao.create("ontology" + contador);
        //setando suas propriedades
        //setando nome
        ontology.setFoafName(name);
        
        //setando uri
        ontology.setUri(ontologyURI);
        
        //setando versao
        //ontology.setVersion();
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
        System.out.println(setClass);
        //ontology.setHas_class(setClass);
        //setando tags (area)
        //ontology.setHas_tag(tags);
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
