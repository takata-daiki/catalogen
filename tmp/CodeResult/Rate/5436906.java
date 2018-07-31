package autoflow;

import com.googlecode.struts2webflow.annotations.FlowIn;
import com.opensymphony.xwork2.ActionSupport;

/**
 * This action retrieves the rate from the session and
 * calculates a insurance rate for the user.
 */
public class Rate extends ActionSupport {
    @FlowIn
    private Integer age;

    /**
     * The calculated insurance rate.
     */
    private int rate;

    public String execute() throws Exception {
        rate = age.intValue() * 2;
        return SUCCESS;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
