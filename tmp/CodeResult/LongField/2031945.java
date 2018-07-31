/*
 * LongField.java
 *
 * Created on 4 de Janeiro de 2008, 12:27
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.celiosilva.swingDK.dataFields;

import com.celiosilva.swingDK.dataAbstractFields.AbstractNumericField;

/**
 * Classe respons?vel em instanciar objetos que manipulam valores do tipo Long. Permite somente digita??o de caracteres num?ricos.
 * @author celio@celiosilva.com
 */
public class LongField extends AbstractNumericField<Long> {   
        
    public LongField (){
        this.setValidCharacters("[0-9]");
        this.setLength(18);       
        this.setMaxValue(999999999999999999L);        
        this.setInvalidCharacters("[^\\d]");
    }
    
    public Long getValued() {
        try {
            return Long.parseLong(this.getText());
        } catch (Exception ex){
            return 0L;
        }
    }   

    public int getDecimalUnits() {
        return 0;
    }
    
}