/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ml.core;
import java.util.ArrayList;

/**
 *
 * @author adivecha
 */
public class Synonym {


     private String sui;
     private String aui;
     private String source;

    public Synonym(String sui, String aui, String source) {
        this.sui = sui;
        this.aui = aui;
        this.source = source;
    }

    public String getAui() {
        return aui;
    }

    public String getSource() {
        return source;
    }

    public String getSui() {
        return sui;
    }

     



}
