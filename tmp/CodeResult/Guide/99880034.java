package org.yth.youthtechhealth.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Guides")
public class Guide extends ParseObject {

    public String getName() {
        return getString("name");
    }

    public Boolean getHasQuestions() { return getBoolean("has_questions"); }

    public Guide getParent() {
        return (Guide)getParseObject("parent");
    }

}
