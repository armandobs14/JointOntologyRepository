/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import br.ufal.ic.joint.module.kao.RepositoryFactory;
import br.ufal.ic.joint.module.ontology.operations.OntologyCompiler;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.http.HTTPRepository;

/**
 *
 * @author Williams
 */
public class main {

    public static void main(String[] args) {
        
        String jar = "jar/OntologyRepository.jar";
        String ontologia1 = "ontologias/sioc.owl";
        String ontologia2 = "ontologias/OntologyRepository.owl";
        List<String> lista = new ArrayList<String>();
        lista.add(ontologia1);
        lista.add(ontologia2);
        OntologyCompiler compiler = new OntologyCompiler(jar, lista);
        compiler.compile();
        
    }

}
