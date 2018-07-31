/*
 * Copyright (c) 2007 BUSINESS OBJECTS SOFTWARE LIMITED
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 *     * Redistributions of source code must retain the above copyright notice,
 *       this list of conditions and the following disclaimer.
 *  
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *  
 *     * Neither the name of Business Objects nor the names of its contributors
 *       may be used to endorse or promote products derived from this software
 *       without specific prior written permission.
 *  
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */


/*
 * CALDocComment.java
 * Creation date: Aug 12, 2005.
 * By: Joseph Wong
 */
package org.openquark.cal.compiler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openquark.cal.internal.serialization.ModuleSerializationTags;
import org.openquark.cal.internal.serialization.RecordInputStream;
import org.openquark.cal.internal.serialization.RecordOutputStream;
import org.openquark.cal.internal.serialization.RecordInputStream.RecordHeaderInfo;


/**
 * The representation of a CALDoc comment as stored by ModuleTypeInfo objects. A
 * CALDoc comment consists of a description block followed by a list of tagged
 * blocks.
 * <p>
 * This class is intended to be constructible only by other classes within this
 * package. Clients should not be able to create instances of this class.
 * Therefore, the compiler can guarantee that a CALDocComment object represents
 * a syntactically and semantically correct CALDoc comment.
 * <p>
 * This class is intended to be immutable, so that objects of this class can be
 * safely returned to clients without worrying about the possibility of these
 * objects being modified.
 * 
 * @author Joseph Wong
 */
public final class CALDocComment {

    /** The serialization schema for CALDoc comments. */
    private static final int serializationSchema = 0; 
    
    /** The summary of the comment. */
    private final TextBlock summary;
    
    /** The description block at the start of the comment. */
    private final TextBlock descriptionBlock;
    
    /** The "@author" blocks in the comment. The array is empty if there are none of these blocks. */
    private final TextBlock[] authorBlocks;
    
    /** The "@version" block in the comment, or null if there is none. */
    private final TextBlock versionBlock;
    
    /** The "@deprecated" block in the comment, or null if there is none. */
    private final TextBlock deprecatedBlock;
    
    /** The "@arg" blocks in the comment. The array is empty if there are none of these blocks. */
    private final ArgBlock[] argBlocks;
    
    /** The "@return" block in the comment, or null if there is none. */
    private final TextBlock returnBlock;
    
    /** The "@see" module references in the comment. The array is empty if there are none of these references. */
    private final ModuleReference[] moduleRefs;
    
    /** The "@see" function and class method references in the comment. The array is empty if there are none of these references. */
    private final ScopedEntityReference[] functionOrClassMethodRefs;
    
    /** The "@see" type constructor references in the comment. The array is empty if there are none of these references. */
    private final ScopedEntityReference[] typeConsRefs;
    
    /** The "@see" data constructor references in the comment. The array is empty if there are none of these references. */
    private final ScopedEntityReference[] dataConsRefs;
    
    /** The "@see" type class references in the comment. The array is empty if there are none of these references. */
    private final ScopedEntityReference[] typeClassRefs;
    
    public static final TextBlock[] NO_TEXT_BLOCKS = new TextBlock[0];
    public static final ArgBlock[] NO_ARG_BLOCKS = new ArgBlock[0];
    public static final ModuleReference[] NO_MODULE_REFS = new ModuleReference[0];
    public static final ScopedEntityReference[] NO_SCOPED_ENTITY_REFS = new ScopedEntityReference[0];
    
    /** The number of spaces per indent in the string generated by the toString() methods. */
    private static final int INDENT_LEVEL = 2;

    /**
     * Represents a reference in a list of references in a CALDoc "@see" block.
     * A reference can either be checked or unchecked. An unchecked reference is
     * represented in source by surrounding the name in double quotes.
     * 
     * @author Joseph Wong
     */
    public static abstract class Reference {
        
        /** Indicates whether the reference is to be statically checked and resolved during compilation. */
        private final boolean checked;
        
        /**
         * How the module name portion of the reference appears in source.
         * Could be the empty string if the reference is unqualified in source.
         */
        private final String moduleNameInSource;
        
        /**
         * Private constructor for this base class for a reference in a list of
         * references in an "@see" block. Intended to be invoked only by subclass
         * constructors.
         * 
         * @param checked
         *          whether the reference is to be statically checked and
         *          resolved during compilation.
         * @param moduleNameInSource how the module name portion of the reference appears in source. Could be the empty string if the reference is unqualified in source.
         */
        private Reference(final boolean checked, final String moduleNameInSource) {
            this.checked = checked;
            
            verifyArg(moduleNameInSource, "moduleNameInSource");
            this.moduleNameInSource = moduleNameInSource;
        }
        
        /**
         * @return true if the reference is to be statically checked and resolved during compilation; false otherwise. 
         */
        public boolean isChecked() {
            return checked;
        }
        
        /**
         * @return how the module name portion of the reference appears in source.
         *         Could be the empty string if the reference is unqualified in source.
         */
        public String getModuleNameInSource() {
            return moduleNameInSource;
        }
    }
    
    /**
     * Represents a (checked/unchecked) module name reference in a CALDoc "@see module = ..." block.
     *
     * @author Joseph Wong
     */
    public static final class ModuleReference extends Reference {
        
        /** The module name encapsulated by this reference. */
        private final ModuleName name;
        
        /**
         * Creates a representation of a module name reference in a CALDoc "@see module = ..." block.
         * 
         * @param name the module name.
         * @param checked whether the name is to be checked and resolved statically during compilation.
         * @param moduleNameInSource how the module name portion of the reference appears in source. Could be the empty string if the reference is unqualified in source.
         */
        ModuleReference(final ModuleName name, final boolean checked, final String moduleNameInSource) {
            super(checked, moduleNameInSource);
            
            verifyArg(name, "name");
            this.name = name;
        }
        
        /**
         * @return the module name encapsulated by this reference.
         */
        public ModuleName getName() {
            return name;
        }
        
        /** @return a string representation of this instance. */
        @Override
        public String toString() {
            return "[ModuleReference " + name + " (" + (isChecked() ? "checked" : "unchecked") + ", module name '" + getModuleNameInSource() + "' in source)]";
        }
        
        /**
         * Write this instance of ModuleReference to the RecordOutputStream.
         * @param s the RecordOutputStream to be written to.
         */
        void write(final RecordOutputStream s) throws IOException {
            s.writeModuleName(name);
            s.writeBoolean(isChecked());
            s.writeUTF(getModuleNameInSource());
        }
        
        /**
         * Load an instance of ModuleReference from the RecordInputStream.
         * @param s the RecordInputStream to be read from.
         * @return a ModuleReference instance deserialized from the stream.
         */
        static ModuleReference load(final RecordInputStream s) throws IOException {
            final ModuleName name = s.readModuleName();
            final boolean checked = s.readBoolean();
            final String moduleNameInSource = s.readUTF();
            return new ModuleReference(name, checked, moduleNameInSource);
        }
    }
    
    /**
     * Represents a (checked/unchecked) scoped entity name reference in a CALDoc "@see" block.
     *
     * @author Joseph Wong
     */
    public static final class ScopedEntityReference extends Reference {
        
        /** The qualified name encapsulated by this reference. */
        private final QualifiedName name;
        
        /**
         * Creates a representation of a scoped entity name reference in a CALDoc "@see" block.
         * 
         * @param name the name of the scoped entity.
         * @param checked whether the name is to be checked and resolved statically during compilation.
         * @param moduleNameInSource how the module name portion of the reference appears in source. Could be the empty string if the reference is unqualified in source.
         */
        ScopedEntityReference(final QualifiedName name, final boolean checked, final String moduleNameInSource) {
            super(checked, moduleNameInSource);
            
            verifyArg(name, "name");
            this.name = name;
        }
        
        /**
         * @return the qualified name encapsulated by this reference.
         */
        public QualifiedName getName() {
            return name;
        }
        
        /** @return a string representation of this instance. */
        @Override
        public String toString() {
            return "[ScopedEntityReference " + name + " (" + (isChecked() ? "checked" : "unchecked") + ", module name '" + getModuleNameInSource() + "' in source)]";
        }
        
        /**
         * Write this instance of ScopedEntityReference to the RecordOutputStream.
         * @param s the RecordOutputStream to be written to.
         */
        void write(final RecordOutputStream s) throws IOException {
            s.writeQualifiedName(name);
            s.writeBoolean(isChecked());
            s.writeUTF(getModuleNameInSource());
        }
        
        /**
         * Load an instance of ScopedEntityReference from the RecordInputStream.
         * @param s the RecordInputStream to be read from.
         * @return a ScopedEntityReference instance deserialized from the stream.
         */
        static ScopedEntityReference load(final RecordInputStream s) throws IOException {
            final QualifiedName name = s.readQualifiedName();
            final boolean checked = s.readBoolean();
            final String moduleNameInSource = s.readUTF();
            return new ScopedEntityReference(name, checked, moduleNameInSource);
        }
    }

    ////=============
    /// Paragraphs
    //
    
    /**
     * Represents a paragraph within a CALDoc comment. A paragraph is simply a
     * block of text that is to be separated from the text that comes before and
     * after it with paragraph breaks.
     * 
     * @author Joseph Wong
     */
    public static abstract class Paragraph {
        
        /**
         * The array of possible record tags used in calls to {@link RecordInputStream#findRecord(short[])} by
         * the {@link #loadWithRecordTag} method.
         */
        private static final short[] PARAGRAPH_RECORD_TAGS = new short[] {
            ModuleSerializationTags.CALDOC_TEXT_PARAGRAPH,
            ModuleSerializationTags.CALDOC_LIST_PARAGRAPH
        };

        /** Private constructor to be called by subclasses only. */
        private Paragraph() {}
        
        /**
         * Accepts the visitation of a visitor, which implements the
         * CALDocCommentTextBlockVisitor interface. This abstract method is to be overridden
         * by each concrete subclass so that the correct visit method on the
         * visitor may be called based upon the type of the element being
         * visited.
         * <p>
         * 
         * As the CALDocCommentTextBlockVisitor follows a more general visitor pattern
         * where arguments can be passed into the visit methods and return
         * values obtained from them, this method passes through the argument
         * into the visit method, and returns as its return value the return
         * value of the visit method.
         * <p>
         * 
         * Nonetheless, for a significant portion of the common cases, the state of the
         * visitation can simply be kept as member variables within the visitor itself,
         * thereby eliminating the need to use the argument and return value of the
         * visit methods. In these scenarios, the recommended approach is to use
         * {@link Void} as the type argument for both <code>T</code> and <code>R</code>, and
         * pass in null as the argument, and return null as the return value.
         * <p>
         * 
         * @param <T> the argument type. If the visitation argument is not used, specify {@link Void}.
         * @param <R> the return type. If the return value is not used, specify {@link Void}.
         * 
         * @param visitor
         *            the visitor.
         * @param arg
         *            the argument to be passed to the visitor's visitXXX method.
         * @return the return value of the visitor's visitXXX method.
         */
        public abstract <T, R> R accept(CALDocCommentTextBlockVisitor<T, R> visitor, T arg);
        
        /** @return a string representation of this instance. */
        @Override
        public final String toString() {
            final StringBuilder result = new StringBuilder();
            toStringBuilder(result, 0);
            return result.toString();
        }
        
        /**
         * Fills the given StringBuilder with a string representation of this instance.
         * @param result the StringBuilder to fill.
         * @param indentLevel the indent level to use in indenting the generated text.
         */
        public abstract void toStringBuilder(StringBuilder result, int indentLevel);
        
        /**
         * Write this instance to the RecordOutputStream.
         * @param s the RecordOutputStream to be written to.
         */
        abstract void writeWithRecordTag(RecordOutputStream s) throws IOException;
        
        /**
         * Load an instance of Paragraph from the RecordInputStream.
         * @param s the RecordInputStream to be read from.
         * @return a Paragraph instance deserialized from the stream.
         */
        static Paragraph loadWithRecordTag(final RecordInputStream s) throws IOException {
            final short[] paragraphRecordTags = PARAGRAPH_RECORD_TAGS;
            
            final RecordHeaderInfo rhi = s.findRecord(paragraphRecordTags);
            if (rhi == null) {
                throw new IOException("Unable to find Paragraph record header.");
            }
            
            Paragraph paragraph;
            
            switch (rhi.getRecordTag()) {
            case ModuleSerializationTags.CALDOC_TEXT_PARAGRAPH:
                paragraph = TextParagraph.load(s);
                break;
            
            case ModuleSerializationTags.CALDOC_LIST_PARAGRAPH:
                paragraph = ListParagraph.load(s);
                break;
            
            default:
                throw new IOException("Unrecognized record tag of " + rhi.getRecordTag() + " for Paragraph.");
            }
            
            s.skipRestOfRecord();
            return paragraph;
        }
    }
    
    /**
     * Represents a text paragraph within a CALDoc comment, i.e. a simple paragraph of text.
     *
     * @author Joseph Wong
     */
    public static final class TextParagraph extends Paragraph {
        
        /**
         * The segments which constitute this paragraph.
         */
        private final Segment[] segments;
        public static final Segment[] NO_SEGMENTS = new Segment[0];
        
        /**
         * Creates a representation of a text paragraph in a CALDoc comment.
         * 
         * @param segments the segments which constitute this paragraph.
         */
        TextParagraph(final Segment[] segments) {
            
            verifyArrayArg(segments, "segments");
            if (segments.length == 0) {
                this.segments = NO_SEGMENTS;
            } else {
                this.segments = segments.clone();
            }
        }
        
        /**
         * @return the number of segments in this paragraph.
         */
        public int getNSegments() {
            return segments.length;
        }
        
        /**
         * Returns the segment at the specified position in this paragraph.
         * @param index the index of the segment to return.
         * @return the segment at the specified position in this paragraph.
         */
        public Segment getNthSegment(final int index) {
            return segments[index];
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public <T, R> R accept(final CALDocCommentTextBlockVisitor<T, R> visitor, final T arg) {
            return visitor.visitTextParagraph(this, arg);
        }
        
        /**
         * Fills the given StringBuilder with a string representation of this instance.
         * @param result the StringBuilder to fill.
         * @param indentLevel the indent level to use in indenting the generated text.
         */
        @Override
        public void toStringBuilder(final StringBuilder result, final int indentLevel) {
            addSpacesToStringBuilder(result, indentLevel);
            result.append("[TextParagraph\n");
            
            for (final Segment segment : segments) {
                segment.toStringBuilder(result, indentLevel + INDENT_LEVEL);
            }
            
            addSpacesToStringBuilder(result, indentLevel);
            result.append("]\n");
        }
        
        /**
         * Write this instance of TextParagraph to the RecordOutputStream.
         * @param s the RecordOutputStream to be written to.
         */
        @Override
        void writeWithRecordTag(final RecordOutputStream s) throws IOException {
            s.startRecord(ModuleSerializationTags.CALDOC_TEXT_PARAGRAPH, serializationSchema);
            write(s);
            s.endRecord();
        }
        
        /**
         * Write this instance of TextParagraph to the RecordOutputStream.
         * @param s the RecordOutputStream to be written to.
         */
        void write(final RecordOutputStream s) throws IOException {
            s.writeIntCompressed(segments.length);
            for (final Segment segment : segments) {
                segment.writeWithRecordTag(s);
            }
        }
        
        /**
         * Load an instance of TextParagraph from the RecordInputStream.
         * @param s the RecordInputStream to be read from.
         * @return a TextParagraph instance deserialized from the stream.
         */
        static TextParagraph load(final RecordInputStream s) throws IOException {
            final int nSegments = s.readIntCompressed();
            
            final Segment[] segments = new Segment[nSegments];
            for (int i = 0; i < nSegments; i++) {
                segments[i] = Segment.loadWithRecordTag(s);
            }
            
            return new TextParagraph(segments);
        }
    }
    
    /**
     * Represents a list (either ordered or unordered) within a CALDoc comment.
     *
     * @author Joseph Wong
     */
    public static final class ListParagraph extends Paragraph {
        
        /**
         * Whether the list is ordered or unordered.
         */
        private final boolean isOrdered;
        
        /**
         * The list items which constitute this list.
         */
        private final ListItem[] items;
        public static final ListItem[] NO_ITEMS = new ListItem[0];
        
        /**
         * Creates a representation of a list in a CALDoc comment.
         * 
         * @param isOrdered whether the list is ordered or unordered.
         * @param items the list items which constitute this list.
         */
        ListParagraph(final boolean isOrdered, final ListItem[] items) {
            this.isOrdered = isOrdered;
            
            verifyArrayArg(items, "items");
            if (items.length == 0) {
                this.items = NO_ITEMS;
            } else {
                this.items = items; // no cloning because this constructor is package-scoped, and we count on the caller to have done the appropriate cloning already
            }
        }
        
        /**
         * @return whether the list is ordered or unordered.
         */
        public boolean isOrdered() {
            return isOrdered;
        }
        
        /**
         * @return the number of items in this list.
         */
        public int getNItems() {
            return items.length;
        }
        
        /**
         * Returns the item at the specified position in this list.
         * @param index the index of the item to return.
         * @return the item at the specified position in this list.
         */
        public ListItem getNthItem(final int index) {
            return items[index];
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public <T, R> R accept(final CALDocCommentTextBlockVisitor<T, R> visitor, final T arg) {
            return visitor.visitListParagraph(this, arg);
        }
        
        /**
         * Fills the given StringBuilder with a string representation of this instance.
         * @param result the StringBuilder to fill.
         * @param indentLevel the indent level to use in indenting the generated text.
         */
        @Override
        public void toStringBuilder(final StringBuilder result, final int indentLevel) {
            addSpacesToStringBuilder(result, indentLevel);
            if (isOrdered) {
                result.append("[Ordered ListParagraph\n");
            } else {
                result.append("[Unordered ListParagraph\n");
            }
            
            for (final ListItem item : items) {
                item.toStringBuilder(result, indentLevel + INDENT_LEVEL);
            }
            
            addSpacesToStringBuilder(result, indentLevel);
            result.append("]\n");
        }
        
        /**
         * Write this instance of ListParagraph to the RecordOutputStream.
         * @param s the RecordOutputStream to be written to.
         */
        @Override
        void writeWithRecordTag(final RecordOutputStream s) throws IOException {
            s.startRecord(ModuleSerializationTags.CALDOC_LIST_PARAGRAPH, serializationSchema);
            
            s.writeBoolean(isOrdered);
            
            s.writeIntCompressed(items.length);
            for (final ListItem item : items) {
                item.write(s);
            }
            
            s.endRecord();
        }
        
        /**
         * Load an instance of ListParagraph from the RecordInputStream.
         * @param s the RecordInputStream to be read from.
         * @return a ListParagraph instance deserialized from the stream.
         */
        static ListParagraph load(final RecordInputStream s) throws IOException {
            final boolean isOrdered = s.readBoolean();
            
            final int nItems = s.readIntCompressed();
            
            final ListItem[] items = new ListItem[nItems];
            for (int i = 0; i < nItems; i++) {
                items[i] = ListItem.load(s);
            }
            
            return new ListParagraph(isOrdered, items);
        }
    }
    
    /**
     * Represents a list item within a CALDoc comment, either in a ordered list or
     * an unordered list.
     *
     * @author Joseph Wong
     */
    public static final class ListItem {
        
        /**
         * The text block which constitute the content of this list item.
         */
        private final TextBlock content;
        
        /**
         * Creates a representation of a list item in a CALDoc comment.
         * 
         * @param content the text block which constitute the content of this list item.
         */
        ListItem(final TextBlock content) {
            verifyArg(content, "content");
            this.content = content;
        }
        
        /**
         * @return the text block which constitute the content of this list item.
         */
        public TextBlock getContent() {
            return content;
        }
        
        /**
         * Accepts the visitation of a visitor, which implements the
         * CALDocCommentTextBlockVisitor interface.
         * <p>
         * 
         * As the CALDocCommentTextBlockVisitor follows a more general visitor pattern
         * where arguments can be passed into the visit methods and return
         * values obtained from them, this method passes through the argument
         * into the visit method, and returns as its return value the return
         * value of the visit method.
         * <p>
         * 
         * Nonetheless, for a significant portion of the common cases, the state of the
         * visitation can simply be kept as member variables within the visitor itself,
         * thereby eliminating the need to use the argument and return value of the
         * visit methods. In these scenarios, the recommended approach is to use
         * {@link Void} as the type argument for both <code>T</code> and <code>R</code>, and
         * pass in null as the argument, and return null as the return value.
         * <p>
         * 
         * @param <T> the argument type. If the visitation argument is not used, specify {@link Void}.
         * @param <R> the return type. If the return value is not used, specify {@link Void}.
         * 
         * @param visitor
         *            the visitor.
         * @param arg
         *            the argument to be passed to the visitor's visitListItem method.
         * @return the return value of the visitor's visitListItem method.
         */
        public <T, R> R accept(final CALDocCommentTextBlockVisitor<T, R> visitor, final T arg) {
            return visitor.visitListItem(this, arg);
        }
        
        /** @return a string representation of this instance. */
        @Override
        public final String toString() {
            final StringBuilder result = new StringBuilder();
            toStringBuilder(result, 0);
            return result.toString();
        }
        
        /**
         * Fills the given StringBuilder with a string representation of this instance.
         * @param result the StringBuilder to fill.
         * @param indentLevel the indent level to use in indenting the generated text.
         */
        public void toStringBuilder(final StringBuilder result, final int indentLevel) {
            addSpacesToStringBuilder(result, indentLevel);
            result.append("[ListItem\n");
            
            content.toStringBuilder(result, indentLevel + INDENT_LEVEL);
            
            addSpacesToStringBuilder(result, indentLevel);
            result.append("]\n");
        }
        
        /**
         * Write this instance of ListItem to the RecordOutputStream.
         * @param s the RecordOutputStream to be written to.
         */
        void write(final RecordOutputStream s) throws IOException {
            content.write(s);
        }
        
        /**
         * Load an instance of ListItem from the RecordInputStream.
         * @param s the RecordInputStream to be read from.
         * @return a ListItem instance deserialized from the stream.
         */
        static ListItem load(final RecordInputStream s) throws IOException {
            final TextBlock content = TextBlock.load(s);
            return new ListItem(content);
        }
    }
    
    ////=============
    /// Segments
    //
    
    /**
     * Represents a text segment in a CALDoc comment. A segment is simply
     * a unit of text, and a text paragraph is represented as a collection
     * of such segments.
     *
     * @author Joseph Wong
     */
    public static abstract class Segment {
        
        /**
         * The array of possible record tags used in calls to {@link RecordInputStream#findRecord(short[])} by
         * the {@link #loadWithRecordTag} method.
         */
        private static final short[] SEGMENT_RECORD_TAGS = new short[] {
            ModuleSerializationTags.CALDOC_PLAIN_TEXT_SEGMENT,
            ModuleSerializationTags.CALDOC_URL_SEGMENT,
            ModuleSerializationTags.CALDOC_MODULE_LINK_SEGMENT,
            ModuleSerializationTags.CALDOC_FUNCTION_OR_CLASS_METHOD_LINK_SEGMENT,
            ModuleSerializationTags.CALDOC_TYPE_CONS_LINK_SEGMENT,
            ModuleSerializationTags.CALDOC_DATA_CONS_LINK_SEGMENT,
            ModuleSerializationTags.CALDOC_TYPE_CLASS_LINK_SEGMENT,
            ModuleSerializationTags.CALDOC_CODE_SEGMENT,
            ModuleSerializationTags.CALDOC_EMPHASIZED_SEGMENT,
            ModuleSerializationTags.CALDOC_STRONGLY_EMPHASIZED_SEGMENT,
            ModuleSerializationTags.CALDOC_SUPERSCRIPT_SEGMENT,
            ModuleSerializationTags.CALDOC_SUBSCRIPT_SEGMENT
        };

        /** Private constructor to be called by subclasses only. */
        private Segment() {}
        
        /**
         * Accepts the visitation of a visitor, which implements the
         * CALDocCommentTextBlockVisitor interface. This abstract method is to be overridden
         * by each concrete subclass so that the correct visit method on the
         * visitor may be called based upon the type of the element being
         * visited.
         * <p>
         * 
         * As the CALDocCommentTextBlockVisitor follows a more general visitor pattern
         * where arguments can be passed into the visit methods and return
         * values obtained from them, this method passes through the argument
         * into the visit method, and returns as its return value the return
         * value of the visit method.
         * <p>
         * 
         * Nonetheless, for a significant portion of the common cases, the state of the
         * visitation can simply be kept as member variables within the visitor itself,
         * thereby eliminating the need to use the argument and return value of the
         * visit methods. In these scenarios, the recommended approach is to use
         * {@link Void} as the type argument for both <code>T</code> and <code>R</code>, and
         * pass in null as the argument, and return null as the return value.
         * <p>
         * 
         * @param <T> the argument type. If the visitation argument is not used, specify {@link Void}.
         * @param <R> the return type. If the return value is not used, specify {@link Void}.
         * 
         * @param visitor
         *            the visitor.
         * @param arg
         *            the argument to be passed to the visitor's visitXXX method.
         * @return the return value of the visitor's visitXXX method.
         */
        public abstract <T, R> R accept(CALDocCommentTextBlockVisitor<T, R> visitor, T arg);
        
        /** @return a string representation of this instance. */
        @Override
        public final String toString() {
            final StringBuilder result = new StringBuilder();
            toStringBuilder(result, 0);
            return result.toString();
        }
        
        /**
         * Fills the given StringBuilder with a string representation of this instance.
         * @param result the StringBuilder to fill.
         * @param indentLevel the indent level to use in indenting the generated text.
         */
        public abstract void toStringBuilder(StringBuilder result, int indentLevel);
        
        /**
         * Unescapes the given text that appears in the source of a CALDoc comment.
         * @param text the text to be unescaped.
         * @return the unescaped text.
         */
        static String caldocUnescape(String text) {
            text = text.replaceAll("\\\\\\{@", "{@"); // '\{@' -> '{@'
            text = text.replaceAll("\\\\@", "@");     // '\@'  -> '@'

            return text;
        }
        
        /**
         * Write this instance to the RecordOutputStream.
         * @param s the RecordOutputStream to be written to.
         */
        abstract void writeWithRecordTag(RecordOutputStream s) throws IOException;
        
        /**
         * Load an instance of Segment from the RecordInputStream.
         * @param s the RecordInputStream to be read from.
         * @return a Segment instance deserialized from the stream.
         */
        static Segment loadWithRecordTag(final RecordInputStream s) throws IOException {
            final short[] paragraphRecordTags = SEGMENT_RECORD_TAGS;
            
            final RecordHeaderInfo rhi = s.findRecord(paragraphRecordTags);
            if (rhi == null) {
                throw new IOException("Unable to find Segment record header.");
            }
            
            Segment segment;
            
            switch (rhi.getRecordTag()) {
            case ModuleSerializationTags.CALDOC_PLAIN_TEXT_SEGMENT:
                segment = PlainTextSegment.load(s);
                break;
                
            case ModuleSerializationTags.CALDOC_URL_SEGMENT:
                segment = URLSegment.load(s);
                break;
                
            case ModuleSerializationTags.CALDOC_MODULE_LINK_SEGMENT:
                segment = ModuleLinkSegment.load(s);
                break;
                
            case ModuleSerializationTags.CALDOC_FUNCTION_OR_CLASS_METHOD_LINK_SEGMENT:
                segment = FunctionOrClassMethodLinkSegment.load(s);
                break;
                
            case ModuleSerializationTags.CALDOC_TYPE_CONS_LINK_SEGMENT:
                segment = TypeConsLinkSegment.load(s);
                break;
                
            case ModuleSerializationTags.CALDOC_DATA_CONS_LINK_SEGMENT:
                segment = DataConsLinkSegment.load(s);
                break;
                
            case ModuleSerializationTags.CALDOC_TYPE_CLASS_LINK_SEGMENT:
                segment = TypeClassLinkSegment.load(s);
                break;
                
            case ModuleSerializationTags.CALDOC_CODE_SEGMENT:
                segment = CodeSegment.load(s);
                break;
                
            case ModuleSerializationTags.CALDOC_EMPHASIZED_SEGMENT:
                segment = EmphasizedSegment.load(s);
                break;
                
            case ModuleSerializationTags.CALDOC_STRONGLY_EMPHASIZED_SEGMENT:
                segment = StronglyEmphasizedSegment.load(s);
                break;
                
            case ModuleSerializationTags.CALDOC_SUPERSCRIPT_SEGMENT:
                segment = SuperscriptSegment.load(s);
                break;
                
            case ModuleSerializationTags.CALDOC_SUBSCRIPT_SEGMENT:
                segment = SubscriptSegment.load(s);
                break;
                
            default:
                throw new IOException("Unrecognized record tag of " + rhi.getRecordTag() + " for Segment.");
            }
            
            s.skipRestOfRecord();
            return segment;
        }
    }
    
    /**
     * Represents a simple piece of text in a CALDoc comment.
     *
     * @author Joseph Wong
     */
    public static final class PlainTextSegment extends Segment {
        
        /**
         * The text encapsulated by this instance.
         */
        private final String text;
        
        /**
         * Creates a representation of a simple piece of text in a CALDoc comment.
         * 
         * @param text the text encapsulated by this instance.
         */
        PlainTextSegment(final String text) {
            verifyArg(text, "text");
            this.text = text;
        }
        
        /**
         * Factory method for constructing a representation of a simple piece of text in a CALDoc comment
         * with a piece of escaped text (as in the format used in the source code).
         * 
         * @param escapedText the escaped text.
         * @return a new instance of this class.
         */
        static PlainTextSegment makeWithEscapedText(final String escapedText) {
            return new PlainTextSegment(caldocUnescape(escapedText));
        }
        
        /**
         * @return the text encapsulated by this instance.
         */
        public String getText() {
            return text;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public <T, R> R accept(final CALDocCommentTextBlockVisitor<T, R> visitor, final T arg) {
            return visitor.visitPlainTextSegment(this, arg);
        }
        
        /**
         * Fills the given StringBuilder with a string representation of this instance.
         * @param result the StringBuilder to fill.
         * @param indentLevel the indent level to use in indenting the generated text.
         */
        @Override
        public void toStringBuilder(final StringBuilder result, final int indentLevel) {
            addSpacesToStringBuilder(result, indentLevel);
            result.append("[PlainTextSegment\n");
            
            addSpacesToStringBuilder(result, indentLevel + INDENT_LEVEL - 1);
            result.append('\"');
            result.append(text.replaceAll("(\r\n|\n|\r)", "\n" + spaces(indentLevel + INDENT_LEVEL))).append("\"\n");
            
            addSpacesToStringBuilder(result, indentLevel);
            result.append("]\n");
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        void writeWithRecordTag(final RecordOutputStream s) throws IOException {
            s.startRecord(ModuleSerializationTags.CALDOC_PLAIN_TEXT_SEGMENT, serializationSchema);
            s.writeUTF(text);
            s.endRecord();
        }
        
        /**
         * Load an instance of PlainTextSegment from the RecordInputStream.
         * @param s the RecordInputStream to be read from.
         * @return a PlainTextSegment instance deserialized from the stream.
         */
        static PlainTextSegment load(final RecordInputStream s) throws IOException {
            final String text = s.readUTF();
            return new PlainTextSegment(text);
        }
    }
    
    /**
     * Represents an inline tag segment in a CALDoc comment, i.e. segments appearing
     * in the source code as "{at-tagName ...at-}". 
     *
     * @author Joseph Wong
     */
    public static abstract class InlineTagSegment extends Segment {
        
        /** Private constructor to be called by subclasses only. */
        private InlineTagSegment() {}
    }
    
    /**
     * Represents a hyperlinkable URL in a CALDoc comment.
     *
     * @author Joseph Wong
     */
    public static final class URLSegment extends InlineTagSegment {
        
        /**
         * The URL encapsulated by this instance.
         */
        private final String url;
        
        /**
         * Creates a representation of a hyperlinkable URL in a CALDoc comment.
         * 
         * @param url the URL encapsulated by this instance.
         */
        URLSegment(final String url) {
            verifyArg(url, "url");
            this.url = url;
        }
        
        /**
         * Factory method for constructing a representation of a hyperlinkable URL in a CALDoc comment
         * with a piece of escaped text (as in the format used in the source code).
         * 
         * @param escapedText the escaped text.
         * @return a new instance of this class.
         */
        static URLSegment makeWithEscapedText(final String escapedText) {
            return new URLSegment(caldocUnescape(escapedText).trim()); // trim any excess whitespace
        }
        
        /**
         * @return the URL encapsulated by this instance.
         */
        public String getURL() {
            return url;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public <T, R> R accept(final CALDocCommentTextBlockVisitor<T, R> visitor, final T arg) {
            return visitor.visitURLSegment(this, arg);
        }
        
        /**
         * Fills the given StringBuilder with a string representation of this instance.
         * @param result the StringBuilder to fill.
         * @param indentLevel the indent level to use in indenting the generated text.
         */
        @Override
        public void toStringBuilder(final StringBuilder result, final int indentLevel) {
            addSpacesToStringBuilder(result, indentLevel);
            result.append("[URLSegment ").append(url).append("]\n");
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        void writeWithRecordTag(final RecordOutputStream s) throws IOException {
            s.startRecord(ModuleSerializationTags.CALDOC_URL_SEGMENT, serializationSchema);
            s.writeUTF(url);
            s.endRecord();
        }
        
        /**
         * Load an instance of URLSegment from the RecordInputStream.
         * @param s the RecordInputStream to be read from.
         * @return a URLSegment instance deserialized from the stream.
         */
        static URLSegment load(final RecordInputStream s) throws IOException {
            final String url = s.readUTF();
            return new URLSegment(url);
        }
    }
    
    /**
     * Represents an inline, hyperlinkable cross-reference in a CALDoc comment.
     *
     * @author Joseph Wong
     */
    public static abstract class LinkSegment extends InlineTagSegment {
        
        /** Private constructor to be called by subclasses only. */
        private LinkSegment() {}
    }
    
    /**
     * Represents an inline, hyperlinkable cross-reference to a module in a CALDoc comment.
     *
     * @author Joseph Wong
     */
    public static final class ModuleLinkSegment extends LinkSegment {
        
        /**
         * The cross-reference encapsulated by this link segment.
         */
        private final ModuleReference reference;
        
        /**
         * Creates a representation of an inline, hyperlinkable cross-reference to a module in a CALDoc comment.
         * 
         * @param reference the cross-reference encapsulated by this link segment.
         */
        ModuleLinkSegment(final ModuleReference reference) {
            verifyArg(reference, "reference");
            this.reference = reference;
        }
        
        /**
         * @return the cross-reference encapsulated by this link segment.
         */
        public ModuleReference getReference() {
            return reference;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public <T, R> R accept(final CALDocCommentTextBlockVisitor<T, R> visitor, final T arg) {
            return visitor.visitModuleLinkSegment(this, arg);
        }
        
        /**
         * Fills the given StringBuilder with a string representation of this instance.
         * @param result the StringBuilder to fill.
         * @param indentLevel the indent level to use in indenting the generated text.
         */
        @Override
        public void toStringBuilder(final StringBuilder result, final int indentLevel) {
            addSpacesToStringBuilder(result, indentLevel);
            result.append("[ModuleLinkSegment ").append(reference).append("]\n");
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        void writeWithRecordTag(final RecordOutputStream s) throws IOException {
            s.startRecord(ModuleSerializationTags.CALDOC_MODULE_LINK_SEGMENT, serializationSchema);
            reference.write(s);
            s.endRecord();
        }
        
        /**
         * Load an instance of ModuleLinkSegment from the RecordInputStream.
         * @param s the RecordInputStream to be read from.
         * @return a ModuleLinkSegment instance deserialized from the stream.
         */
        static ModuleLinkSegment load(final RecordInputStream s) throws IOException {
            final ModuleReference reference = ModuleReference.load(s);
            return new ModuleLinkSegment(reference);
        }
    }
    
    /**
     * Represents an inline, hyperlinkable cross-reference to a scoped entity in a CALDoc comment.
     *
     * @author Joseph Wong
     */
    public static abstract class ScopedEntityLinkSegment extends LinkSegment {
        
        /**
         * The cross-reference encapsulated by this link segment.
         */
        private final ScopedEntityReference reference;
        
        /**
         * Private constructor to be called by subclasses only.
         * @param reference the cross-reference encapsulated by this link segment.
         */
        private ScopedEntityLinkSegment(final ScopedEntityReference reference) {
            verifyArg(reference, "reference");
            this.reference = reference;
        }
        
        /**
         * @return the cross-reference encapsulated by this link segment.
         */
        public ScopedEntityReference getReference() {
            return reference;
        }
    }
    
    /**
     * Represents an inline, hyperlinkable cross-reference to a function or class method in a CALDoc comment.
     *
     * @author Joseph Wong
     */
    public static final class FunctionOrClassMethodLinkSegment extends ScopedEntityLinkSegment {
        
        /**
         * Creates a representation of an inline, hyperlinkable cross-reference to a function or class method in a CALDoc comment.
         * 
         * @param reference the cross-reference encapsulated by this link segment.
         */
        FunctionOrClassMethodLinkSegment(final ScopedEntityReference reference) {
            super(reference);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public <T, R> R accept(final CALDocCommentTextBlockVisitor<T, R> visitor, final T arg) {
            return visitor.visitFunctionOrClassMethodLinkSegment(this, arg);
        }
        
        /**
         * Fills the given StringBuilder with a string representation of this instance.
         * @param result the StringBuilder to fill.
         * @param indentLevel the indent level to use in indenting the generated text.
         */
        @Override
        public void toStringBuilder(final StringBuilder result, final int indentLevel) {
            addSpacesToStringBuilder(result, indentLevel);
            result.append("[FunctionOrClassMethodLinkSegment ").append(getReference()).append("]\n");
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        void writeWithRecordTag(final RecordOutputStream s) throws IOException {
            s.startRecord(ModuleSerializationTags.CALDOC_FUNCTION_OR_CLASS_METHOD_LINK_SEGMENT, serializationSchema);
            getReference().write(s);
            s.endRecord();
        }
        
        /**
         * Load an instance of FunctionOrClassMethodLinkSegment from the RecordInputStream.
         * @param s the RecordInputStream to be read from.
         * @return a FunctionOrClassMethodLinkSegment instance deserialized from the stream.
         */
        static FunctionOrClassMethodLinkSegment load(final RecordInputStream s) throws IOException {
            final ScopedEntityReference reference = ScopedEntityReference.load(s);
            return new FunctionOrClassMethodLinkSegment(reference);
        }
    }
    
    /**
     * Represents an inline, hyperlinkable cross-reference to a type constructor in a CALDoc comment.
     *
     * @author Joseph Wong
     */
    public static final class TypeConsLinkSegment extends ScopedEntityLinkSegment {
        
        /**
         * Creates a representation of an inline, hyperlinkable cross-reference to a type constructor in a CALDoc comment.
         * 
         * @param reference the cross-reference encapsulated by this link segment.
         */
        TypeConsLinkSegment(final ScopedEntityReference reference) {
            super(reference);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public <T, R> R accept(final CALDocCommentTextBlockVisitor<T, R> visitor, final T arg) {
            return visitor.visitTypeConsLinkSegment(this, arg);
        }
        
        /**
         * Fills the given StringBuilder with a string representation of this instance.
         * @param result the StringBuilder to fill.
         * @param indentLevel the indent level to use in indenting the generated text.
         */
        @Override
        public void toStringBuilder(final StringBuilder result, final int indentLevel) {
            addSpacesToStringBuilder(result, indentLevel);
            result.append("[TypeConsLinkSegment ").append(getReference()).append("]\n");
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        void writeWithRecordTag(final RecordOutputStream s) throws IOException {
            s.startRecord(ModuleSerializationTags.CALDOC_TYPE_CONS_LINK_SEGMENT, serializationSchema);
            getReference().write(s);
            s.endRecord();
        }
        
        /**
         * Load an instance of TypeConsLinkSegment from the RecordInputStream.
         * @param s the RecordInputStream to be read from.
         * @return a TypeConsLinkSegment instance deserialized from the stream.
         */
        static TypeConsLinkSegment load(final RecordInputStream s) throws IOException {
            final ScopedEntityReference reference = ScopedEntityReference.load(s);
            return new TypeConsLinkSegment(reference);
        }
    }
    
    /**
     * Represents an inline, hyperlinkable cross-reference to a data constructor in a CALDoc comment.
     *
     * @author Joseph Wong
     */
    public static final class DataConsLinkSegment extends ScopedEntityLinkSegment {
        
        /**
         * Creates a representation of an inline, hyperlinkable cross-reference to a data constructor in a CALDoc comment.
         * 
         * @param reference the cross-reference encapsulated by this link segment.
         */
        DataConsLinkSegment(final ScopedEntityReference reference) {
            super(reference);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public <T, R> R accept(final CALDocCommentTextBlockVisitor<T, R> visitor, final T arg) {
            return visitor.visitDataConsLinkSegment(this, arg);
        }
        
        /**
         * Fills the given StringBuilder with a string representation of this instance.
         * @param result the StringBuilder to fill.
         * @param indentLevel the indent level to use in indenting the generated text.
         */
        @Override
        public void toStringBuilder(final StringBuilder result, final int indentLevel) {
            addSpacesToStringBuilder(result, indentLevel);
            result.append("[DataConsLinkSegment ").append(getReference()).append("]\n");
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        void writeWithRecordTag(final RecordOutputStream s) throws IOException {
            s.startRecord(ModuleSerializationTags.CALDOC_DATA_CONS_LINK_SEGMENT, serializationSchema);
            getReference().write(s);
            s.endRecord();
        }
        
        /**
         * Load an instance of DataConsLinkSegment from the RecordInputStream.
         * @param s the RecordInputStream to be read from.
         * @return a DataConsLinkSegment instance deserialized from the stream.
         */
        static DataConsLinkSegment load(final RecordInputStream s) throws IOException {
            final ScopedEntityReference reference = ScopedEntityReference.load(s);
            return new DataConsLinkSegment(reference);
        }
    }
    
    /**
     * Represents an inline, hyperlinkable cross-reference to a type class in a CALDoc comment.
     *
     * @author Joseph Wong
     */
    public static final class TypeClassLinkSegment extends ScopedEntityLinkSegment {
        
        /**
         * Creates a representation of an inline, hyperlinkable cross-reference to a type class in a CALDoc comment.
         * 
         * @param reference the cross-reference encapsulated by this link segment.
         */
        TypeClassLinkSegment(final ScopedEntityReference reference) {
            super(reference);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public <T, R> R accept(final CALDocCommentTextBlockVisitor<T, R> visitor, final T arg) {
            return visitor.visitTypeClassLinkSegment(this, arg);
        }
        
        /**
         * Fills the given StringBuilder with a string representation of this instance.
         * @param result the StringBuilder to fill.
         * @param indentLevel the indent level to use in indenting the generated text.
         */
        @Override
        public void toStringBuilder(final StringBuilder result, final int indentLevel) {
            addSpacesToStringBuilder(result, indentLevel);
            result.append("[TypeClassLinkSegment ").append(getReference()).append("]\n");
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        void writeWithRecordTag(final RecordOutputStream s) throws IOException {
            s.startRecord(ModuleSerializationTags.CALDOC_TYPE_CLASS_LINK_SEGMENT, serializationSchema);
            getReference().write(s);
            s.endRecord();
        }
        
        /**
         * Load an instance of TypeClassLinkSegment from the RecordInputStream.
         * @param s the RecordInputStream to be read from.
         * @return a TypeClassLinkSegment instance deserialized from the stream.
         */
        static TypeClassLinkSegment load(final RecordInputStream s) throws IOException {
            final ScopedEntityReference reference = ScopedEntityReference.load(s);
            return new TypeClassLinkSegment(reference);
        }
    }
    
    /**
     * Represents a block of source code in a CALDoc comment. In generating formatted output, the
     * whitespace contained within the text is respected.
     *
     * @author Joseph Wong
     */
    public static final class CodeSegment extends InlineTagSegment {
        
        /**
         * The text of the block of source code.
         */
        private final TextBlock content;
        
        /**
         * Creates a representation of a block of source code in a CALDoc comment.
         * 
         * @param content the text of the block of source code.
         */
        CodeSegment(final TextBlock content) {
            verifyArg(content, "content");
            this.content = content;
        }
        
        /**
         * @return the text of the block of source code.
         */
        public TextBlock getContent() {
            return content;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public <T, R> R accept(final CALDocCommentTextBlockVisitor<T, R> visitor, final T arg) {
            return visitor.visitCodeSegment(this, arg);
        }
        
        /**
         * Fills the given StringBuilder with a string representation of this instance.
         * @param result the StringBuilder to fill.
         * @param indentLevel the indent level to use in indenting the generated text.
         */
        @Override
        public void toStringBuilder(final StringBuilder result, final int indentLevel) {
            addSpacesToStringBuilder(result, indentLevel);
            result.append("[CodeSegment\n");
            
            content.toStringBuilder(result, indentLevel + INDENT_LEVEL);
            
            addSpacesToStringBuilder(result, indentLevel);
            result.append("]\n");
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        void writeWithRecordTag(final RecordOutputStream s) throws IOException {
            s.startRecord(ModuleSerializationTags.CALDOC_CODE_SEGMENT, serializationSchema);
            content.write(s);
            s.endRecord();
        }
        
        /**
         * Load an instance of CodeSegment from the RecordInputStream.
         * @param s the RecordInputStream to be read from.
         * @return a CodeSegment instance deserialized from the stream.
         */
        static CodeSegment load(final RecordInputStream s) throws IOException {
            final TextBlock content = TextBlock.load(s);
            return new CodeSegment(content);
        }
    }
    
    /**
     * Represents a piece of text in a CALDoc comment that has formatting applied to it.
     *
     * @author Joseph Wong
     */
    public static abstract class FormattedSegment extends InlineTagSegment {
        
        /**
         * The content encapsulated by this formatted segment.
         */
        private final TextParagraph content;
        
        /**
         * Private constructor to be called by subclasses only.
         * @param content the content encapsulated by this formatted segment.
         */
        private FormattedSegment(final TextParagraph content) {
            verifyArg(content, "content");
            this.content = content;
        }
        
        /**
         * @return the content encapsulated by this formatted segment.
         */
        public TextParagraph getContent() {
            return content;
        }
    }
    
    /**
     * Represents a piece of text in a CALDoc comment that is emphasized.
     *
     * @author Joseph Wong
     */
    public static final class EmphasizedSegment extends FormattedSegment {
        
        /**
         * Creates a representation of a piece of text in a CALDoc comment that is emphasized.
         * 
         * @param content the content encapsulated by this segment.
         */
        EmphasizedSegment(final TextParagraph content) {
            super(content);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public <T, R> R accept(final CALDocCommentTextBlockVisitor<T, R> visitor, final T arg) {
            return visitor.visitEmphasizedSegment(this, arg);
        }
        
        /**
         * Fills the given StringBuilder with a string representation of this instance.
         * @param result the StringBuilder to fill.
         * @param indentLevel the indent level to use in indenting the generated text.
         */
        @Override
        public void toStringBuilder(final StringBuilder result, final int indentLevel) {
            addSpacesToStringBuilder(result, indentLevel);
            result.append("[EmphasizedSegment\n");
            
            getContent().toStringBuilder(result, indentLevel + INDENT_LEVEL);
            
            addSpacesToStringBuilder(result, indentLevel);
            result.append("]\n");
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        void writeWithRecordTag(final RecordOutputStream s) throws IOException {
            s.startRecord(ModuleSerializationTags.CALDOC_EMPHASIZED_SEGMENT, serializationSchema);
            getContent().write(s);
            s.endRecord();
        }
        
        /**
         * Load an instance of EmphasizedSegment from the RecordInputStream.
         * @param s the RecordInputStream to be read from.
         * @return a EmphasizedSegment instance deserialized from the stream.
         */
        static EmphasizedSegment load(final RecordInputStream s) throws IOException {
            final TextParagraph content = TextParagraph.load(s);
            return new EmphasizedSegment(content);
        }
    }
    
    /**
     * Represents a piece of text in a CALDoc comment that is strongly emphasized.
     *
     * @author Joseph Wong
     */
    public static final class StronglyEmphasizedSegment extends FormattedSegment {
        
        /**
         * Creates a representation of a piece of text in a CALDoc comment that is strongly emphasized.
         * 
         * @param content the content encapsulated by this segment.
         */
        StronglyEmphasizedSegment(final TextParagraph content) {
            super(content);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public <T, R> R accept(final CALDocCommentTextBlockVisitor<T, R> visitor, final T arg) {
            return visitor.visitStronglyEmphasizedSegment(this, arg);
        }
        
        /**
         * Fills the given StringBuilder with a string representation of this instance.
         * @param result the StringBuilder to fill.
         * @param indentLevel the indent level to use in indenting the generated text.
         */
        @Override
        public void toStringBuilder(final StringBuilder result, final int indentLevel) {
            addSpacesToStringBuilder(result, indentLevel);
            result.append("[StronglyEmphasizedSegment\n");
            
            getContent().toStringBuilder(result, indentLevel + INDENT_LEVEL);
            
            addSpacesToStringBuilder(result, indentLevel);
            result.append("]\n");
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        void writeWithRecordTag(final RecordOutputStream s) throws IOException {
            s.startRecord(ModuleSerializationTags.CALDOC_STRONGLY_EMPHASIZED_SEGMENT, serializationSchema);
            getContent().write(s);
            s.endRecord();
        }
        
        /**
         * Load an instance of StronglyEmphasizedSegment from the RecordInputStream.
         * @param s the RecordInputStream to be read from.
         * @return a StronglyEmphasizedSegment instance deserialized from the stream.
         */
        static StronglyEmphasizedSegment load(final RecordInputStream s) throws IOException {
            final TextParagraph content = TextPar