/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import KAO.Kao;
import java.util.HashSet;
import java.util.Set;
import joint.codegen.types.Question;

/**
 *
 * @author Williams
 */
public class ForumController extends Controller {

    public ForumController() {

    }

    /**
     * this method create  a question
     * @param user
     * @param topic
     * @param message
     */
    public void createQuestion(String user, String topic, String message) {
        
        Kao kaoQuestion = new Kao(Question.class, repositoryURI);
        int countQuestion = kaoQuestion.retrieveAllInstances().size() + 1;
        Question question = kaoQuestion.create("Question_" + countQuestion);
       // question.setHas_creator(user);
    }

    /**
     *
     * @param user
     * @param questionURI
     * @param message
     */
    public void createAnswer(String user,String questionURI, String message){
        //TODO: implementar
    }

    /**
     *This method retrieve messages of a question
     * @param questionURI
     * @return a Set of messages
     */
    public Set<String> retrieveMessages(String questionURI){
        //TODO : implementar recuperação de answers e questions da ontologia
        return new HashSet();
    }
    
    /**
     * This method search question
     * @param query
     * @return a SET of questions
     */
    public Set<String> searchQuestion(String query){
        return new HashSet();
    }
}
