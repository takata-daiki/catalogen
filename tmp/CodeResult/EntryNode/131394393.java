/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.terramenta.syndication.feedreader.nodes;

import com.terramenta.syndication.actions.GotoEntryAction;
import com.terramenta.syndication.actions.ViewEntryAction;
import com.terramenta.syndication.beans.Entry;
import java.beans.IntrospectionException;
import javax.swing.Action;
import org.openide.actions.PropertiesAction;
import org.openide.nodes.BeanNode;
import org.openide.nodes.Children;
import org.openide.util.actions.SystemAction;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author chris.heidt
 */
public class EntryNode extends BeanNode {

    static final long serialVersionUID = 1L;

    public EntryNode(Entry e) throws IntrospectionException {
        super(e, Children.LEAF, Lookups.singleton(e));
        this.setName(e.getTitle());
        if (e.getSummary() != null) {
            this.setShortDescription(e.getSummary());
        }
        this.setIconBaseWithExtension("images/note.png");
    }

    @Override
    public Action getPreferredAction() {
        return SystemAction.get(ViewEntryAction.class);
    }

    @Override
    public Action[] getActions(boolean context) {
        Action[] actions = new Action[]{
            SystemAction.get(ViewEntryAction.class),
            SystemAction.get(GotoEntryAction.class),
            null,
            SystemAction.get(PropertiesAction.class)
        };
        return actions;
    }
}
