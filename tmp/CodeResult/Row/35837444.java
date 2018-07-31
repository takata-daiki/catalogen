package com.atlassian.pageobjects.aui.component.restfultable;

import com.atlassian.pageobjects.PageBinder;
import com.atlassian.pageobjects.elements.PageElement;
import org.openqa.selenium.By;

import javax.inject.Inject;

import static com.atlassian.pageobjects.elements.query.Poller.waitUntilFalse;

public class Row
{
    private final PageElement row;

    @Inject
    private PageBinder binder;

    public Row(final PageElement row)
    {
        this.row = row;
    }

    public String getValue(String name)
    {
        final PageElement editableArea = row.find(By.cssSelector(".aui-restfultable-editable[data-field-name=" + name + "]"));
        return editableArea.getText();
    }

    public void delete()
    {
        row.find(By.className("aui-resfultable-delete")).click();
        waitUntilFalse(row.timed().isPresent());
    }

    public EditRow edit(String fieldName)
    {
        getEditableRegion(fieldName).click();
        return binder.bind(EditRow.class, row);
    }

    private PageElement getEditableRegion(final String fieldName) {
        return row.find(By.cssSelector(".aui-restfultable-editable[data-field-name=" + fieldName + "]"));
    }

}
