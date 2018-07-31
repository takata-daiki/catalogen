package mobi.handytec.data.model.parameter;

import mobi.handytec.core.message.Item;
import mobi.handytec.data.field.parameter.RateField;
import mobi.handytec.data.model.EntityModel;

/**
 * Entity model for the RATE database table.
 * 
 * @author Handytec
 * @version 2.1
 */
public class Rate extends EntityModel {

    public static final String ID = "Rate";

    /**
     * Rate Id
     *
     * @NotNull
     */
    private Long pk_rateId;

    /**
     * Name of rate
     *
     * @NotNull
     * @Size(max=40)
     */
    private String name;

    public Rate() {
    }

    public Rate(Long pk_rateId, String name) {
        this.pk_rateId = pk_rateId;
        this.name = name;
    }

    public Rate(Item item) {
        setPk_rateId(item.getField(RateField.pk_rateId).getLongValue());
        setName(item.getField(RateField.name).getValue());
    }

    public Long getPk_rateId() {
        return this.pk_rateId;
    }

    public void setPk_rateId(Long pk_rateId) {
        this.pk_rateId = pk_rateId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public Object create(Item item) {
        if (item == null) {
            return new Rate();
        }
        return new Rate(item);
    }

    @Override
    public Item toItem(int number) {
        Item item = new Item(number);
        item.setField(RateField.pk_rateId, getPk_rateId());
        item.setField(RateField.name, getName());
        return item;
    }
}
