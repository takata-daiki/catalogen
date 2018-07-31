/*
 * IntegerField.java
 *
 * Created on 4 de Janeiro de 2008, 12:27
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.celiosilva.swingDK.dataFields;

import com.celiosilva.swingDK.dataAbstractFields.AbstractNumericField;

/**
 * Classe respons?vel em instanciar objetos que manipulam valores do tipo Integer. Permite somente digita??o de caracteres num?ricos.
 * @author celio@celiosilva.com
 */
public class IntegerField extends AbstractNumericField<Integer> {   
        
    public IntegerField (){
        this.setValidCharacters("[0-9]");
        this.setLength(9);
        this.setMaxValue(999999999);
        this.setInvalidCharacters("[^\\d]");
    }
    
    public Integer getValued() {
        try {
            return Integer.parseInt(this.getText());
        } catch (Exception ex){
            return null;
        }
    }        

    public int getDecimalUnits() {
        return 0;
    }
}
