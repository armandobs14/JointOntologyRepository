/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import KAO.Kao;
import br.ufal.ic.joint.RepositoryFacade;
import com.google.gson.JsonObject;
import fr.inrialpes.exmo.align.impl.method.StringDistAlignment;
import fr.inrialpes.exmo.align.impl.renderer.OWLAxiomsRendererVisitor;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import joint.codegen.ns4ca56aaf.Ontology;
import joint.codegen.ns4ca56aaf.OntologyClass;
import joint.codegen.sioc.UserAccount;
import joint.codegen.types.Tag;
import org.semanticweb.owl.align.Alignment;
import org.semanticweb.owl.align.AlignmentException;
import org.semanticweb.owl.align.AlignmentProcess;
import org.semanticweb.owl.align.AlignmentVisitor;

/**
 *
 * @author Judson
 */
public class OntologyController extends Controller{
    private int contador;
    private Properties params = new Properties();
    private Alignment alignment = null;
    private RepositoryFacade facade = new RepositoryFacade();
    
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
     * @throws org.semanticweb.owl.align.AlignmentExcepStringDistAlignmenttion
     */
        public void saveOntology(String name, String ontologyURI, String filepath, Set<String> comment, Set<String> developersEmail, Set<String> classes, Set<String> tags) throws AlignmentException{
        //abrindo conexao
        //Testando
        Kao ontologyKao = new Kao(Ontology.class,repositoryURI); 
        //contador para a criacao do nome na instancia
        List<Ontology> ontologies= ontologyKao.retrieveAllInstances();
        
        int counter = 0;
        for (Ontology ontology : ontologies) {
            counter++;
            AlignmentProcess process = new StringDistAlignment();
            try {
                process.init(new URI(ontology.getFile_path()),new URI(filepath));
                params.setProperty("stringFunction","smoaDistance");
                params.setProperty("noinst","1");
                process.align( (Alignment)null, params );
                alignment = process;
            } catch (URISyntaxException ex) {
                Logger.getLogger(OntologyController.class.getName()).log(Level.SEVERE, null, ex);
            }

            PrintWriter writer = null;
            File merged = null;
            try {
                //merged = File.createTempFile( "MyApp-results",".owl");
                //merged.deleteOnExit();
                //merged = new File("/home/armando/Desktop/Alignment_"+counter+".json");
                merged = new File("/home/armando/Desktop/Alignment_"+counter+".owl");
                writer = new PrintWriter ( new FileWriter(merged, false ), true );
                
                System.out.println(merged);                
                
                //AlignmentVisitor renderer = new OWLAxiomsRendererVisitor(writer);
                
                AlignmentVisitor renderer = new OWLAxiomsRendererVisitor(writer,alignmentURI);
                //AlignmentVisitor renderer = new JSONRendererVisitor(writer);
                alignment.render(renderer);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if ( writer != null ) {
                    writer.flush();
                    writer.close();
                    
                    facade.addOntology(merged.getAbsolutePath(),alignmentURI);
                }
            } 
        }
        //classe Ontology da ontologia do repositorio
        Ontology ontology = ontologyKao.create("ontology_" + counter);
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
        query.append("PREFIX foaf:<"+foafURI+">");
        query.append("select ?x where {?x foaf:name '").append(nameOntology).append("'}"); 
        
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
