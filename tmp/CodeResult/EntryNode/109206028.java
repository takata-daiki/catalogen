/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *  
 *    http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License. 
 *  
 */
package org.apache.directory.server.core.partition.impl.btree.gui;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.tree.TreeNode;

import org.apache.directory.server.core.partition.impl.btree.BTreePartition;
import org.apache.directory.server.xdbm.ForwardIndexEntry;
import org.apache.directory.server.xdbm.IndexCursor;
import org.apache.directory.server.xdbm.IndexEntry;
import org.apache.directory.server.xdbm.search.Evaluator;
import org.apache.directory.server.xdbm.search.SearchEngine;
import org.apache.directory.shared.ldap.entry.Entry;
import org.apache.directory.shared.ldap.filter.ExprNode;
import org.apache.directory.shared.ldap.name.DN;


/**
 * A node representing an entry.
 * 
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 */
public class EntryNode implements TreeNode
{
    private final BTreePartition partition;
    private final EntryNode parent;
    private final Entry entry;
    private final ArrayList<TreeNode> children;
    private final Long id;


    public EntryNode( Long id, EntryNode parent, BTreePartition partition, Entry entry, Map<Long, EntryNode> map )
    {
        this( id, parent, partition, entry, map, null, null );
    }


    public EntryNode( Long id, EntryNode parent, BTreePartition db, Entry entry, Map<Long, EntryNode> map,
        ExprNode exprNode, SearchEngine engine )
    {
        this.partition = db;
        this.id = id;
        this.entry = entry;
        children = new ArrayList<TreeNode>();

        if ( parent == null )
        {
            this.parent = this;
        }
        else
        {
            this.parent = parent;
        }

        try
        {
            List<ForwardIndexEntry> recordForwards = new ArrayList<ForwardIndexEntry>();
            IndexCursor<Long, Entry, Long> childList = db.list( id );

            while ( childList.next() )
            {
                IndexEntry old = childList.get();
                ForwardIndexEntry newRec = new ForwardIndexEntry();
                newRec.copy( old );
                recordForwards.add( newRec );
            }

            childList.close();

            Iterator list = recordForwards.iterator();

            while ( list.hasNext() )
            {
                IndexEntry rec = ( IndexEntry ) list.next();

                if ( engine != null && exprNode != null )
                {
                    if ( db.getChildCount( rec.getId() ) == 0 )
                    {
                        Evaluator evaluator = engine.evaluator( exprNode );
                        if ( evaluator.evaluateId( rec.getId() ) )
                        {
                            Entry newEntry = db.lookup( rec.getId() );
                            EntryNode child = new EntryNode( ( Long ) rec.getId(), this, db, newEntry, map, exprNode,
                                engine );
                            children.add( child );
                        }
                        else
                        {
                            continue;
                        }
                    }
                    else
                    {
                        Entry newEntry = db.lookup( rec.getId() );
                        EntryNode child = new EntryNode( ( Long ) rec.getId(), this, db, newEntry, map, exprNode,
                            engine );
                        children.add( child );
                    }
                }
                else
                {
                    Entry newEntry = db.lookup( ( Long ) rec.getId() );
                    EntryNode child = new EntryNode( ( Long ) rec.getId(), this, db, newEntry, map );
                    children.add( child );
                }
            }
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }

        map.put( id, this );
    }


    public Enumeration<TreeNode> children()
    {
        return Collections.enumeration( children );
    }


    public boolean getAllowsChildren()
    {
        return true;
    }


    public TreeNode getChildAt( int childIndex )
    {
        return ( TreeNode ) children.get( childIndex );
    }


    public int getChildCount()
    {
        return children.size();
    }


    public int getIndex( TreeNode child )
    {
        return children.indexOf( child );
    }


    public TreeNode getParent()
    {
        return parent;
    }


    public boolean isLeaf()
    {
        return children.size() <= 0;
    }


    public String getEntryDn() throws Exception
    {
        return partition.getEntryDn( id ).getNormName();
    }


    public String toString()
    {
        StringBuffer buf = new StringBuffer();

        try
        {
            DN dn = partition.getEntryDn( id );
            buf.append( "(" ).append( id ).append( ") " );
            buf.append( dn.getRdn() );
        }
        catch ( Exception e )
        {
            buf.append( "ERROR: " + e.getLocalizedMessage() );
        }

        if ( children.size() > 0 )
        {
            buf.append( " [" ).append( children.size() ).append( "]" );
        }

        return buf.toString();
    }


    public Entry getLdapEntry()
    {
        return entry;
    }


    public Long getEntryId()
    {
        return id;
    }
}
