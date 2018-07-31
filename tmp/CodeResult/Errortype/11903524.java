package com.parrot.portal.exception;


/**
 * @author tajzivit
 */
public enum ErrorType {
    

    /**
     * Define that is some problem with bean. Missing bean definition, too much definition of the
     * same bean type etc.
     */
    BEAN_ERROR("BEAN_ERROR");
    
    /**
     * Creates a valueOf that accepts aliases in addition to the enum constant name.
     * 
     * @param alias
     *                text alias
     * @return OrderType value
     */
    public static ErrorType valueOfAlias(String alias) {
        for (ErrorType type : ErrorType.values()) {
            if (type.alias.equalsIgnoreCase(alias) || type.toString().equalsIgnoreCase(alias)) {
                return type;
            }
        }
        // fell out the bottom of search over all enums and aliases give up.
        throw new IllegalArgumentException("Unknown ErrorType: " + alias);
        
    }
    
    private String alias;
    
    private ErrorType(String code) {
        this.alias = code;
    }
}
