package com.atlassian.plugins.rest.sample.expansion.entity;

import com.atlassian.plugins.rest.common.expand.AbstractRecursiveEntityExpander;
import com.atlassian.plugins.rest.sample.expansion.resource.DataStore;

/**
 * Expands a {com.atlassian.plugins.rest.sample.expansion.entity.SubRecord} by asking the {@link com.atlassian.plugins.rest.sample.expansion.resource.DataStore} to perform the database queries (fake).
 */
public class SubRecordExpander extends AbstractRecursiveEntityExpander<SubRecord> {
    protected SubRecord expandInternal(SubRecord entity) {
        if (entity != null && entity.getPlayerRecord() != null) {
            return DataStore.getInstance().getSubRecord(entity.getPlayerRecord());
        }
        return entity;
    }
}
