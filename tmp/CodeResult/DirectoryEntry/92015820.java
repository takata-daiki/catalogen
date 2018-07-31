package com.kotu.filerenameeditor;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;




final public class DirectoryEntry implements Comparable<DirectoryEntry> {


    @Override
    final public int compareTo(final DirectoryEntry o) {

        if (file.isDirectory() && o.file.isFile()) {
            return -1;
        } else if (file.isFile() && o.file.isDirectory()) {
            return 1;
        } else {
            return file.getName().toUpperCase().compareTo(o.file.getName().toUpperCase());
        }
    }


    final public static String SYMBOL_FOR_DIRECTORIES = "D";
    final public static String SYMBOL_FOR_FILES = "F";
    final public static String SYMBOL_FOR_UNKNOWN = "?";
    final public static String SYMBOL_FOR_NULL = "?";

    final public String fileTypeSymbol;

    private File file = null;
    //    private int undoStepEquivalentToFile = 0;


    final private ArrayList<String> fileNamePerUndoStep = new ArrayList<>();


    public DirectoryEntry(final File file) {

        this.file = file;
        fileNamePerUndoStep.add(file.getName());
        //        undoStepEquivalentToFile = 0;

        fileTypeSymbol = getFileTypeSymbol();
    }


    final private String getFileTypeSymbol() {

        if (file == null) {
            return SYMBOL_FOR_NULL;
        } else if (file.isDirectory()) {
            return SYMBOL_FOR_DIRECTORIES;
        } else if (file.isFile()) {
            return SYMBOL_FOR_FILES;
        } else {
            return SYMBOL_FOR_UNKNOWN;
        }
    }


    final public void createNewUndoStep(final int undoStepOnWhichThisIsBased, final int amountOfUndoStepsThatShouldBeThereInTheEnd) {

        if (undoStepOnWhichThisIsBased < 0 || undoStepOnWhichThisIsBased >= fileNamePerUndoStep.size()) {
            throw new IllegalArgumentException();
        }
        if (amountOfUndoStepsThatShouldBeThereInTheEnd != fileNamePerUndoStep.size() + 1) {
            throw new IllegalStateException("Intended amount of undo steps after this operation: " + amountOfUndoStepsThatShouldBeThereInTheEnd + "; Actual amount of undo steps that would result from this operation: " + fileNamePerUndoStep.size() + 1);
        }
        fileNamePerUndoStep.add(fileNamePerUndoStep.get(undoStepOnWhichThisIsBased));
    }


    final public void deleteUndoStepsToTheEnd(final int firstUndoStepToDelete) {

        if (firstUndoStepToDelete < 0 || firstUndoStepToDelete >= fileNamePerUndoStep.size()) {
            throw new IllegalArgumentException();
        }
        while (fileNamePerUndoStep.size() > firstUndoStepToDelete) {
            fileNamePerUndoStep.remove(firstUndoStepToDelete);
        }
    }


    final public boolean renameAccordingly(final int undoStep) {

        if (undoStep < 0 || undoStep >= fileNamePerUndoStep.size()) {
            throw new IllegalArgumentException();
        }

        final File newFile = new File(file.getParent(), fileNamePerUndoStep.get(undoStep));
        final boolean ret = file.renameTo(newFile);
        if (ret) {
            file = newFile;
            //            undoStepEquivalentToFile = undoStep;
        }

        return ret;
    }


    final public boolean isUndoStepEquivalentToFile(final int undoStep) {

        if (undoStep < 0 || undoStep >= fileNamePerUndoStep.size()) {
            throw new IllegalArgumentException();
        }

        return fileNamePerUndoStep.get(undoStep).equals(file.getName());
    }
    //    final public int getUndoStepEquivalentToFile() {
    //
    //        return undoStepEquivalentToFile;
    //    }


    final public String getFileName(final int undoStep) {

        if (undoStep < 0 || undoStep >= fileNamePerUndoStep.size()) {
            throw new IllegalArgumentException();
        }

        return fileNamePerUndoStep.get(undoStep);
    }


    // DELETES A PORTION OF THE SELECTED UNDOSTEP TEXT LINE
    final public void lineEdit_delete(final int undoStep, final int start, final int length) {

        if (undoStep < 0 || undoStep >= fileNamePerUndoStep.size() || start < 0 || length < 1) {
            throw new IllegalArgumentException("undoStep=" + undoStep + "of" + fileNamePerUndoStep.size() + " start=" + start + " length=" + length);
        }

        final String oldText = fileNamePerUndoStep.get(undoStep);
        final int oldTextLength = oldText.length();

        if (start >= oldTextLength) {
            // THIS WHOLE OPERATION STARTS PAST THIS LINE'S CONTENT, SO JUST RETURN.
            return;
        }

        String newText = "";
        if (start > 0) {
            newText = oldText.substring(0, start);
        }
        if (start + length < oldTextLength) {
            newText += oldText.substring(start + length);
        }

        fileNamePerUndoStep.set(undoStep, newText);
    }


    // INSERTS TEXT INTO THE SELECTED UNDOSTEP TEXT LINE
    final public void lineEdit_insert(final int undoStep, final int start, final String textToInsert) {

        if (undoStep < 0 || undoStep >= fileNamePerUndoStep.size() || start < 0 || textToInsert == null || textToInsert.length() < 1) {
            throw new IllegalArgumentException("undoStep=" + undoStep + "of" + fileNamePerUndoStep.size() + " start=" + start + " charsToInsert=" + textToInsert);
        }

        final String oldText = fileNamePerUndoStep.get(undoStep);
        final int oldTextLength = oldText.length();

        String newText = "";
        if (start > 0) {
            if (start >= oldTextLength) {
                newText = oldText;
            } else {
                newText = oldText.substring(0, start);
            }
            if (newText.length() < start) {
                final char[] fillerCharacters = new char[start - newText.length()];
                Arrays.fill(fillerCharacters, ' ');
                newText += String.valueOf(fillerCharacters);
            }
            newText += textToInsert;
            if (start < oldTextLength) {
                newText += oldText.substring(start);
            }
        } else {
            newText = textToInsert + oldText;
        }

        fileNamePerUndoStep.set(undoStep, newText);
    }


}
