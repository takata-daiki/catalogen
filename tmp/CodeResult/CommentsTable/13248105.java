/*
 * Copyright (c) 2007-2011, Petr Panteleyev <petr@panteleyev.org>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice,
 *       this list of conditions and the following disclaimer.
 *    2. Redistributions in binary form must reproduce the above copyright notice,
 *       this list of conditions and the following disclaimer in the documentation
 *       and/or other materials provided with the distribution.
 *    3. The name of the author may not be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY
 * AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHOR
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN
 * IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.panteleyev.gclient;

import java.awt.BorderLayout;
import java.awt.Window;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.JEditorPane;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import org.panteleyev.blogclient.BloggerFrame;
import org.panteleyev.blogclient.Icon;
import org.panteleyev.blogclient.MainClass;
import org.panteleyev.blogclient.Options;
import org.panteleyev.swing.BaseTable;
import org.panteleyev.swing.SwingFactory;
import org.panteleyev.swing.SwingWorkerEx;
import org.panteleyev.swing.TableUtil;

public class GBCommentsFrame extends BloggerFrame<GBUserProfile> {
    private final String[] _commentsTableHeaderNames = {
        L10N.COMMENTS_FRAME_TABLE_DATE.s(),
        L10N.COMMENTS_FRAME_TABLE_AUTHOR.s(),
        L10N.COMMENTS_FRAME_TABLE_SUBJECT.s()
    };

    private GBEntry                 event;
    private GBAccount               account;
    private AbstractTableModel      tableModel;
    private ArrayList<GBComment>    comments = new ArrayList<GBComment>();

    private AbstractAction          addCommentAction;
    private AbstractAction          deleteCommentAction;

    static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy kk:mm");

    private JEditorPane             commentView = new JEditorPane();
    private JTable                  commentsTable = new BaseTable();
    private JScrollPane             tableScroll = new JScrollPane(commentsTable);

    private abstract class CommentHandlingThread extends SwingWorkerEx<Object,Object> {
        CommentHandlingThread(Window comp) {
            super(comp);
        }

        @Override
        protected void done() {
            if (getException() != null) {
                handleException(getException());
            } else {
                tableModel.fireTableDataChanged();
                TableUtil.adjustTableColumnSizes(commentsTable, 2, tableScroll);
                onCommentSelected();
            }

            super.done();
        }
    }

    private class LoadCommentsThread extends CommentHandlingThread {
        LoadCommentsThread(Window comp) {
            super(comp);
        }

        @Override
        protected Object doInBackground() throws Exception {
            try {
                comments = account.getComments(event);
            } catch (Exception e) {
                setException(e);
            }

            return null;
        }

        @Override
        protected void done() {
            if (getException() != null) {
                handleException(getException());
                closeFrame();
            } else {
                super.done();
            }
        }
    }

    private class AddNewCommentThread extends CommentHandlingThread {
        GBComment _comment;

        AddNewCommentThread(Window comp, GBComment comment) {
            super(comp);
            _comment = comment;
        }

        @Override
        protected Object doInBackground() throws Exception {
            try {
                account.createComment(_comment);
                comments = account.getComments(event);
            } catch (Exception e) {
                setException(e);
            }

            return null;
        }

    }

    private class DeleteCommentThread extends CommentHandlingThread {

        DeleteCommentThread(Window comp) {
            super(comp);
        }

        @Override
        protected Object doInBackground() throws Exception {
            try {
                int[] rows = commentsTable.getSelectedRows();
                for (int row : rows) {
                    GBComment comment = comments.get(row);
                    if (comment != null) {
                        account.deleteEntry(comment);
                    }
                }

                comments = account.getComments(event);
            } catch (Exception e) {
                setException(e);
            }

            return null;
        }
    }

    public GBCommentsFrame(GBUserProfile profile, GBEntry event) throws GBException {
        super(profile);

        this.account = getProfile().getAccount();
        this.event = event;

        createActions();
        initComponents();
        createMenuBar();

        createTableModel();

        StringBuilder b = new StringBuilder(L10N.COMMENTS_FRAME_TITLE.s())
            .append(" \"").append(event.getSubject()).append("\" ");
        setTitle(b.toString());

        loadComments();
        Options.getBounds(this);
    }

    private void initComponents() {
        getContentPane().add(
            SwingFactory.createToolBarPanel(
                SwingFactory.createToolBar(false, true,
                    addCommentAction,
                    deleteCommentAction
                ),
                Box.createHorizontalStrut(5),
                this.createProfileToolBar(false, true),
                Box.createHorizontalGlue()
            ), BorderLayout.NORTH
        );

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        split.setDividerLocation(200);
        getContentPane().add(split, BorderLayout.CENTER);

        split.setTopComponent(tableScroll);

        tableScroll.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent evt) {
                TableUtil.adjustTableColumnSizes(commentsTable, 2, tableScroll);
            }
        });


        split.setBottomComponent(new JScrollPane(commentView));
        commentView.setContentType("text/html");
        commentView.setEditable(false);
    }

    private void createActions() {
        addCommentAction = new AbstractAction(L10N.COMMENTS_FRAME_ADD_COMMENT_ACTION.s(), Icon.COMMENT_EDIT.i()) {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onAddComment();
            }
        };
        addCommentAction.putValue(Action.LARGE_ICON_KEY, Icon.COMMENT_EDIT.I());

        deleteCommentAction = new AbstractAction(L10N.COMMENTS_FRAME_DELETE_COMMENT_ACTION.s(), Icon.COMMENT_DELETE.i()) {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onDeleteComment();
            }
        };
        deleteCommentAction.putValue(Action.LARGE_ICON_KEY, Icon.COMMENT_DELETE.I());
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        menuBar.add(createFileMenu());
        menuBar.add(createProfileMenu());
        menuBar.add(createToolsMenu());
        menuBar.add(createWindowMenu());
        menuBar.add(createHelpMenu());

        setJMenuBar(menuBar);
    }

    public GBEntry getPost() { return event; }

    private void onAddComment() {
        getProfile().openCommentEditWindow(getPost());
    }

    private void onDeleteComment() {
        if (commentsTable.getSelectedRows().length == 0) {
            return;
        }

        if (MainClass.showConfirmDialog(this)) {
            new DeleteCommentThread(this).execute();
        }
    }

    private void createTableModel() {
        tableModel = new AbstractTableModel() {
            @Override
            public int getColumnCount() {
                return _commentsTableHeaderNames.length;
            }

            @Override
            public int getRowCount() {
                return comments.size();
            }

            @Override
            public Object getValueAt(int row, int col) {
                GBComment comm = comments.get(row);
                if (comm == null) {
                    return null;
                }

                switch (col) {
                    case 0:
                        return dateFormat.format(comm.getDate());
                    case 1:
                        GBPerson author = comm.getAuthor();
                        return (author == null)? null : author.getName();
                    case 2:
                        return comm.getSubject();
                    default:
                        return null;
                }
            }

            @Override
            public String getColumnName(int column) {
                return _commentsTableHeaderNames[column];
            }
        };

        commentsTable.setModel(tableModel);

        commentsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                onCommentSelected();
            }
        });
    }

    private void loadComments() throws GBException {
        new LoadCommentsThread(this).execute();
    }

    public void addNewComment(GBComment comment) {
        new AddNewCommentThread(this, comment).execute();
    }

    private void onCommentSelected() {
        int index = commentsTable.getSelectedRow();
        if (index != -1) {
            GBComment comment = comments.get(index);
            if (comment != null) {
                showComment(comment);
            }
        } else {
            commentView.setText(null);
        }
    }

    private void showComment(GBComment comm) {
        StringBuilder b = new StringBuilder("<html><body bgcolor='white'>")
            .append("<table border='0' width='100%'>")
            .append("<tr bgcolor='#C0C0C0' valign='top'><td>");

        GBPerson author = comm.getAuthor();
        String user = (author == null)? "" : author.getName();
        b.append("<b>").append(user).append("</b>");

        if (comm.getDate() != null) {
            b.append("<br>").append(dateFormat.format(comm.getDate()));
        }

        b.append("<td>");
        String subj = comm.getSubject();
        if (subj == null) {
            b.append(L10N.COMMENTS_FRAME_NO_SUBJECT.s());
        } else {
            b.append(subj);
        }


        b.append("<tr><td colspan='2'>");

        if (comm.getBody() != null) {
            b.append(comm.getBody().replaceAll("\n", "<br>"));
        }

        b.append("</table></body></html>");
        commentView.setText(b.toString());
    }

}
