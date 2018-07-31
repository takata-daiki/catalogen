package com.atlassian.confluence.plugins.macros.advanced.recentupdate;

import java.util.List;

/**
 * Represents a grouping of update items.
 */
public interface Grouping
{
    List<UpdateItem> getUpdateItems();

    /**
     * @return the number of update items in this grouping.
     */
    int size();

    /**
     * @param updateItem update item
     * @return true if the specified update item can be added to this grouping, false otherwise. 
     */
    boolean canAdd(UpdateItem updateItem);

    void addUpdateItem(UpdateItem updateItem);
}
