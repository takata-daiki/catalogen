package pk;

/**
 * Created with IntelliJ IDEA.
 * User: Tolstov
 * Date: 29.11.12
 * Time: 18:59
 * To change this template use File | Settings | File Templates.
 */
public class Child extends Base<Long> {
    private Long id;
    private String str;

    protected Long getId() {
        return id;
    }

    @Override
    protected void setId(Long id) {
        this.id = id;
    }

    protected Child() {
    }

    public static Child restore(Long id, String str) {
        Child ch = new Child();
        ch.id = id;
        ch.str = str;
        return ch;
    }

    public String getStr() {
        return str;
    }
}
