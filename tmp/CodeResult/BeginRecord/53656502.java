package com.rpath.bamboo.plugins.repository;

import com.atlassian.bamboo.author.AuthorImpl;
import com.atlassian.bamboo.command.CommandException;
import com.atlassian.bamboo.commit.Commit;
import com.atlassian.bamboo.commit.CommitFile;
import com.atlassian.bamboo.commit.CommitFileImpl;
import com.atlassian.bamboo.commit.CommitImpl;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * User: Dmitriy Akishin
 * Date: 05.08.2009
 * Time: 19:16:25
 */
public class ChangeLogParser {
    private static String beginRecord = "begin-record:";
    private static String endRecord = "end-record:";
    private static String revision = "revision:";
    private static String changeSet = "changeset:";
    private static String user = "user:";
    private static String date = "date:";
    private static String files = "files:";
    private static String beginDescription = "begin-description:";
    private static String endDescription = "end-description:";
    private static final Logger log = Logger.getLogger(ChangeLogParser.class);
    private int tokenIndex = 0;
    private String line;
    private String[] tokens;


    public ChangeLogParser(String line) {
        tokens = line.trim().split(" |\t");
        this.line = line;
    }

    private ChangeLogParser() {
    }

    public List<Commit> parse() throws CommandException {
        List<Commit> commits = new ArrayList<Commit>();
        commits.addAll(record());
        return commits;
    }


    private List<Commit> record() throws CommandException {
        List<Commit> commits = new ArrayList<Commit>();
        if (getToken() == null) {
            return commits;
        }
        if (!beginRecord.equals(getToken())) {
            throwCanNotParse();
        }
        nextToken();

        CommitImpl commit = new CommitImpl();

        String revision = revision();
        changeSet();
        String user = user();
        Date date = date();
        List<String> fileNames = files();
        String[] files = new String[fileNames.size()];
        fileNames.toArray(files);
        String desctiption = desctiption();


        setLogAuthor(commit, user);
        setFiles(commit, files, revision);
        commit.setComment(desctiption);
        commit.setDate(date);
        commits.add(commit);

        if (tokenIndex < tokens.length) {
            nextToken();
            nextToken();
            commits.addAll(record());
        }

        return commits;
    }

    private void throwCanNotParse() {
        throw new RuntimeException("invalid format token:" + getToken());
    }

    private void nextToken() {
        tokenIndex++;
        if ("".equals(getToken())) {
            nextToken();
        }
    }

    private String getToken() {
        if (tokenIndex > tokens.length - 1) {
            return null;
        }
        return tokens[tokenIndex].trim();
    }


    private String desctiption() {
        if (!beginDescription.endsWith(getToken())) {
            throwCanNotParse();
        }
        nextToken();
        String description = "";

        while (!endDescription.equals(getToken())) {
            if (getToken() == null) {
                throwCanNotParse();
            }
            description = description + " " + getToken();
            nextToken();
        }

        return description;
    }

    private List<String> files() {
        List<String> fileNames = new ArrayList<String>();
        if (!files.equals(getToken())) {
            throwCanNotParse();
        }
        nextToken();
        while (!beginDescription.equals(getToken())) {
            fileNames.add(getToken());
            nextToken();
        }
        return fileNames;
    }


    private Date date() throws CommandException {
        if (!date.endsWith(getToken())) {
            throwCanNotParse();
        }
        nextToken();
        String dateStr = getDate();

        DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy", Locale.UK);

        Date date = null;
        try {
            date = dateFormat.parse(dateStr);
        } catch (ParseException e) {
            log.error("", e);
            throw new CommandException("can't parse date:" + dateStr, e);
        }
        nextToken();
        return date;
    }

    private String getDate() {
        String dateStr = getToken();
        nextToken();
        dateStr += " " + getToken();
        nextToken();
        dateStr += " " + getToken();
        nextToken();
        dateStr += " " + getToken();
        nextToken();
        dateStr += " " + getToken();
        nextToken();
        return dateStr;
    }


    private String user() {
        if (!user.equals(getToken())) {
            throwCanNotParse();
        }
        nextToken();
        String user = getToken();
        nextToken();
        return user;
    }


    private String changeSet() {
        if (!changeSet.endsWith(getToken())) {
            throwCanNotParse();
        }
        nextToken();
        String changeSet = getToken();
        nextToken();
        return changeSet;
    }

    private String revision() {
        if (!revision.equals(getToken())) {
            throwCanNotParse();
        }
        nextToken();
        String revision = getToken();
        nextToken();
        return revision;
    }

    private void setLogAuthor(Commit commit, String user) {
        AuthorImpl author = new AuthorImpl();
        author.setName(user);
        commit.setAuthor(author);
    }

    private void setFiles(Commit commit, String[] fileNames, String revision) {
        List<CommitFile> fileList = new ArrayList<CommitFile>();

        for (int index = 0; index < fileNames.length; index++) {
            String fileName = (String) fileNames[index];
            if (StringUtils.isNotBlank(fileName)) {
                log.info("Hg Changes: " + fileName);

                String parsedFileName = fileName.trim();

                if (StringUtils.isNotBlank(parsedFileName)) {
                    CommitFileImpl modifiedFile = new CommitFileImpl(parsedFileName);

                    if (revision != null) {
                        modifiedFile.setRevision(revision);
                    }

                    fileList.add(modifiedFile);

                }
            }
        }
        commit.setFiles(fileList);
    }


}
