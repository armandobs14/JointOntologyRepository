/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package KAO;

import br.ufal.ic.joint.module.kao.AbstractKAO;

/**
 *
 * @author Judson
 */
public class Kao extends AbstractKAO{
    
     public <T> Kao(Class<T> classe, String ontologyURI) {
        super(classe, ontologyURI);
    }
    
}
