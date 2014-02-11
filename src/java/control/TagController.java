/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package control;

import KAO.Kao;
import joint.codegen.types.Tag;

/**
 *
 * @author armando
 */
public class TagController extends Controller{

    /**
     *This method retrieve or create a tag
     * @param tagName
     * @return a Tag object
     */
    public static Tag getTagByTagName(String tagName){
        Kao tagKao = new Kao(Tag.class,siocURI);
        
        StringBuilder query = new StringBuilder();
        query.append("PREFIX sioc:<"+siocURI+"#>");
        query.append("select ?x where {?x sioc:name '").append(tagName).append("'}"); 
        
        try {
            Tag tag = (Tag) tagKao.executeQueryAsSingleResult(query.toString());
            return tag;
        } catch (Exception e) {
            Tag tag = tagKao.create(tagName);
            return tag;
        }
    }
}
