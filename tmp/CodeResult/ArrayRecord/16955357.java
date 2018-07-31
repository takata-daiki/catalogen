/**
 * Copyright (c) 2009-2012, Lukas Eder, lukas.eder@gmail.com
 * All rights reserved.
 *
 * This software is licensed to you under the Apache License, Version 2.0
 * (the "License"); You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * . Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 * . Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * . Neither the name "jOOQ" nor the names of its contributors may be
 *   used to endorse or promote products derived from this software without
 *   specific prior written permission.
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

package org.jooq.test;

import static org.jooq.test.postgres.generatedclasses.Tables.T_639_NUMBERS_TABLE;
import static org.jooq.test.postgres.generatedclasses.Tables.T_658_REF;
import static org.jooq.test.postgres.generatedclasses.Tables.T_725_LOB_TEST;
import static org.jooq.test.postgres.generatedclasses.Tables.T_785;
import static org.jooq.test.postgres.generatedclasses.Tables.T_959;
import static org.jooq.test.postgres.generatedclasses.Tables.T_ARRAYS;
import static org.jooq.test.postgres.generatedclasses.Tables.T_AUTHOR;
import static org.jooq.test.postgres.generatedclasses.Tables.T_BOOK;
import static org.jooq.test.postgres.generatedclasses.Tables.T_BOOK_STORE;
import static org.jooq.test.postgres.generatedclasses.Tables.T_BOOK_TO_BOOK_STORE;
import static org.jooq.test.postgres.generatedclasses.Tables.T_BOOLEANS;
import static org.jooq.test.postgres.generatedclasses.Tables.T_DATES;
import static org.jooq.test.postgres.generatedclasses.Tables.T_IDENTITY;
import static org.jooq.test.postgres.generatedclasses.Tables.T_IDENTITY_PK;
import static org.jooq.test.postgres.generatedclasses.Tables.T_TRIGGERS;
import static org.jooq.test.postgres.generatedclasses.Tables.V_AUTHOR;
import static org.jooq.test.postgres.generatedclasses.Tables.V_BOOK;
import static org.jooq.test.postgres.generatedclasses.Tables.V_LIBRARY;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.util.List;

import org.jooq.ArrayRecord;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UDTRecord;
import org.jooq.UpdatableTable;
import org.jooq.conf.Settings;
import org.jooq.impl.Factory;
import org.jooq.test._.converters.Boolean_10;
import org.jooq.test._.converters.Boolean_TF_LC;
import org.jooq.test._.converters.Boolean_TF_UC;
import org.jooq.test._.converters.Boolean_YES_NO_LC;
import org.jooq.test._.converters.Boolean_YES_NO_UC;
import org.jooq.test._.converters.Boolean_YN_LC;
import org.jooq.test._.converters.Boolean_YN_UC;
import org.jooq.test.postgres.generatedclasses.Routines;
import org.jooq.test.postgres.generatedclasses.Sequences;
import org.jooq.test.postgres.generatedclasses.enums.U_959;
import org.jooq.test.postgres.generatedclasses.tables.records.TArraysRecord;
import org.jooq.test.postgres.generatedclasses.tables.records.TAuthorRecord;
import org.jooq.test.postgres.generatedclasses.tables.records.TBookRecord;
import org.jooq.test.postgres.generatedclasses.tables.records.TBookStoreRecord;
import org.jooq.test.postgres.generatedclasses.tables.records.TBookToBookStoreRecord;
import org.jooq.test.postgres.generatedclasses.tables.records.TBooleansRecord;
import org.jooq.test.postgres.generatedclasses.tables.records.TDatesRecord;
import org.jooq.test.postgres.generatedclasses.tables.records.TIdentityPkRecord;
import org.jooq.test.postgres.generatedclasses.tables.records.TIdentityRecord;
import org.jooq.test.postgres.generatedclasses.tables.records.TTriggersRecord;
import org.jooq.test.postgres.generatedclasses.tables.records.T_639NumbersTableRecord;
import org.jooq.test.postgres.generatedclasses.tables.records.T_658RefRecord;
import org.jooq.test.postgres.generatedclasses.tables.records.T_725LobTestRecord;
import org.jooq.test.postgres.generatedclasses.tables.records.T_785Record;
import org.jooq.test.postgres.generatedclasses.tables.records.VLibraryRecord;
import org.jooq.test.postgres.generatedclasses.tables.records.XUnusedRecord;
import org.jooq.test.postgres.generatedclasses.udt.UAddressType;
import org.jooq.test.postgres.generatedclasses.udt.UStreetType;
import org.jooq.tools.unsigned.UByte;
import org.jooq.tools.unsigned.UInteger;
import org.jooq.tools.unsigned.ULong;
import org.jooq.tools.unsigned.UShort;
import org.jooq.util.postgres.PostgresDataType;
import org.jooq.util.postgres.PostgresFactory;

import org.junit.Test;


/**
 * @author Lukas Eder
 */
public class jOOQPostgresTest extends jOOQAbstractTest<
        TAuthorRecord,
        Object,
        TBookRecord,
        TBookStoreRecord,
        TBookToBookStoreRecord,
        XUnusedRecord,
        VLibraryRecord,
        TArraysRecord,
        TDatesRecord,
        TBooleansRecord,
        XUnusedRecord,
        TTriggersRecord,
        XUnusedRecord,
        TIdentityRecord,
        TIdentityPkRecord,
        T_658RefRecord,
        T_725LobTestRecord,
        T_639NumbersTableRecord,
        T_785Record> {

    @Override
    protected Factory create(Settings settings) {
        return new PostgresFactory(getConnection(), settings);
    }

    @Override
    protected UpdatableTable<TAuthorRecord> TAuthor() {
        return T_AUTHOR;
    }

    @Override
    protected TableField<TAuthorRecord, String> TAuthor_LAST_NAME() {
        return T_AUTHOR.LAST_NAME;
    }

    @Override
    protected TableField<TAuthorRecord, String> TAuthor_FIRST_NAME() {
        return T_AUTHOR.FIRST_NAME;
    }

    @Override
    protected TableField<TAuthorRecord, Date> TAuthor_DATE_OF_BIRTH() {
        return T_AUTHOR.DATE_OF_BIRTH;
    }

    @Override
    protected TableField<TAuthorRecord, Integer> TAuthor_YEAR_OF_BIRTH() {
        return T_AUTHOR.YEAR_OF_BIRTH;
    }

    @Override
    protected TableField<TAuthorRecord, Integer> TAuthor_ID() {
        return T_AUTHOR.ID;
    }

    @Override
    protected TableField<TAuthorRecord, ? extends UDTRecord<?>> TAuthor_ADDRESS() {
        return T_AUTHOR.ADDRESS;
    }

    @Override
    protected UpdatableTable<TBookRecord> TBook() {
        return T_BOOK;
    }

    @Override
    protected TableField<TBookRecord, Integer> TBook_ID() {
        return T_BOOK.ID;
    }

    @Override
    protected TableField<TBookRecord, Integer> TBook_AUTHOR_ID() {
        return T_BOOK.AUTHOR_ID;
    }

    @Override
    protected TableField<TBookRecord, String> TBook_TITLE() {
        return T_BOOK.TITLE;
    }

    @Override
    protected UpdatableTable<TBookStoreRecord> TBookStore() {
        return T_BOOK_STORE;
    }

    @Override
    protected TableField<TBookStoreRecord, String> TBookStore_NAME() {
        return T_BOOK_STORE.NAME;
    }

    @Override
    protected UpdatableTable<TBookToBookStoreRecord> TBookToBookStore() {
        return T_BOOK_TO_BOOK_STORE;
    }

    @Override
    protected UpdatableTable<XUnusedRecord> TBookSale() {
        return null;
    }

    @Override
    protected TableField<TBookToBookStoreRecord, Integer> TBookToBookStore_BOOK_ID() {
        return T_BOOK_TO_BOOK_STORE.BOOK_ID;
    }

    @Override
    protected TableField<TBookToBookStoreRecord, String> TBookToBookStore_BOOK_STORE_NAME() {
        return T_BOOK_TO_BOOK_STORE.BOOK_STORE_NAME;
    }

    @Override
    protected TableField<TBookToBookStoreRecord, Integer> TBookToBookStore_STOCK() {
        return T_BOOK_TO_BOOK_STORE.STOCK;
    }

    @Override
    protected Table<T_725LobTestRecord> T725() {
        return T_725_LOB_TEST;
    }

    @Override
    protected TableField<T_725LobTestRecord, Integer> T725_ID() {
        return T_725_LOB_TEST.ID;
    }

    @Override
    protected TableField<T_725LobTestRecord, byte[]> T725_LOB() {
        return T_725_LOB_TEST.LOB;
    }

    @Override
    protected Table<T_785Record> T785() {
        return T_785;
    }

    @Override
    protected TableField<T_785Record, Integer> T785_ID() {
        return T_785.ID;
    }

    @Override
    protected TableField<T_785Record, String> T785_NAME() {
        return T_785.NAME;
    }

    @Override
    protected TableField<T_785Record, String> T785_VALUE() {
        return T_785.VALUE;
    }

    @Override
    protected Table<XUnusedRecord> TUnsigned() {
        return null;
    }

    @Override
    protected TableField<XUnusedRecord, UByte> TUnsigned_U_BYTE() {
        return null;
    }

    @Override
    protected TableField<XUnusedRecord, UShort> TUnsigned_U_SHORT() {
        return null;
    }

    @Override
    protected TableField<XUnusedRecord, UInteger> TUnsigned_U_INT() {
        return null;
    }

    @Override
    protected TableField<XUnusedRecord, ULong> TUnsigned_U_LONG() {
        return null;
    }

    @Override
    protected Table<TDatesRecord> TDates() {
        return T_DATES;
    }

    @Override
    protected UpdatableTable<TBooleansRecord> TBooleans() {
        return T_BOOLEANS;
    }

    @Override
    protected TableField<TBooleansRecord, Integer> TBooleans_ID() {
        return T_BOOLEANS.ID;
    }

    @Override
    protected TableField<TBooleansRecord, Boolean_10> TBooleans_BOOLEAN_10() {
        return T_BOOLEANS.ONE_ZERO;
    }

    @Override
    protected TableField<TBooleansRecord, Boolean_TF_LC> TBooleans_Boolean_TF_LC() {
        return T_BOOLEANS.TRUE_FALSE_LC;
    }

    @Override
    protected TableField<TBooleansRecord, Boolean_TF_UC> TBooleans_Boolean_TF_UC() {
        return T_BOOLEANS.TRUE_FALSE_UC;
    }

    @Override
    protected TableField<TBooleansRecord, Boolean_YN_LC> TBooleans_Boolean_YN_LC() {
        return T_BOOLEANS.Y_N_LC;
    }

    @Override
    protected TableField<TBooleansRecord, Boolean_YN_UC> TBooleans_Boolean_YN_UC() {
        return T_BOOLEANS.Y_N_UC;
    }

    @Override
    protected TableField<TBooleansRecord, Boolean_YES_NO_LC> TBooleans_Boolean_YES_NO_LC() {
        return T_BOOLEANS.YES_NO_LC;
    }

    @Override
    protected TableField<TBooleansRecord, Boolean_YES_NO_UC> TBooleans_Boolean_YES_NO_UC() {
        return T_BOOLEANS.YES_NO_UC;
    }

    @Override
    protected TableField<TBooleansRecord, Boolean> TBooleans_VC() {
        return T_BOOLEANS.VC_BOOLEAN;
    }

    @Override
    protected TableField<TBooleansRecord, Boolean> TBooleans_C() {
        return T_BOOLEANS.C_BOOLEAN;
    }

    @Override
    protected TableField<TBooleansRecord, Boolean> TBooleans_N() {
        return T_BOOLEANS.N_BOOLEAN;
    }

    @Override
    protected Table<T_658RefRecord> T658() {
        return T_658_REF;
    }

    @Override
    protected Table<T_639NumbersTableRecord> T639() {
        return T_639_NUMBERS_TABLE;
    }

    @Override
    protected TableField<T_639NumbersTableRecord, Integer> T639_ID() {
        return T_639_NUMBERS_TABLE.ID;
    }

    @Override
    protected TableField<T_639NumbersTableRecord, BigDecimal> T639_BIG_DECIMAL() {
        return T_639_NUMBERS_TABLE.BIG_DECIMAL;
    }

    @Override
    protected TableField<T_639NumbersTableRecord, BigInteger> T639_BIG_INTEGER() {
        return T_639_NUMBERS_TABLE.BIG_INTEGER;
    }

    @Override
    protected TableField<T_639NumbersTableRecord, Byte> T639_BYTE() {
        return null;
    }

    @Override
    protected TableField<T_639NumbersTableRecord, Byte> T639_BYTE_DECIMAL() {
        return T_639_NUMBERS_TABLE.BYTE_DECIMAL;
    }

    @Override
    protected TableField<T_639NumbersTableRecord, Short> T639_SHORT() {
        return T_639_NUMBERS_TABLE.SHORT;
    }

    @Override
    protected TableField<T_639NumbersTableRecord, Short> T639_SHORT_DECIMAL() {
        return T_639_NUMBERS_TABLE.SHORT_DECIMAL;
    }

    @Override
    protected TableField<T_639NumbersTableRecord, Integer> T639_INTEGER() {
        return T_639_NUMBERS_TABLE.INTEGER;
    }

    @Override
    protected TableField<T_639NumbersTableRecord, Integer> T639_INTEGER_DECIMAL() {
        return T_639_NUMBERS_TABLE.INTEGER_DECIMAL;
    }

    @Override
    protected TableField<T_639NumbersTableRecord, Long> T639_LONG() {
        return T_639_NUMBERS_TABLE.LONG;
    }

    @Override
    protected TableField<T_639NumbersTableRecord, Long> T639_LONG_DECIMAL() {
        return T_639_NUMBERS_TABLE.LONG_DECIMAL;
    }

    @Override
    protected TableField<T_639NumbersTableRecord, Double> T639_DOUBLE() {
        return T_639_NUMBERS_TABLE.DOUBLE;
    }

    @Override
    protected TableField<T_639NumbersTableRecord, Float> T639_FLOAT() {
        return T_639_NUMBERS_TABLE.FLOAT;
    }

    @Override
    protected Table<TArraysRecord> TArrays() {
        return T_ARRAYS;
    }

    @Override
    protected TableField<TArraysRecord, Integer> TArrays_ID() {
        return T_ARRAYS.ID;
    }

    @Override
    protected TableField<TArraysRecord, String[]> TArrays_STRING() {
        return T_ARRAYS.STRING_ARRAY;
    }

    @Override
    protected TableField<TArraysRecord, Integer[]> TArrays_NUMBER() {
        return T_ARRAYS.NUMBER_ARRAY;
    }

    @Override
    protected TableField<TArraysRecord, Date[]> TArrays_DATE() {
        return T_ARRAYS.DATE_ARRAY;
    }

    @Override
    protected TableField<TArraysRecord, ? extends UDTRecord<?>[]> TArrays_UDT() {
        return T_ARRAYS.UDT_ARRAY;
    }

    @Override
    protected TableField<TArraysRecord, ArrayRecord<String>> TArrays_STRING_R() {
        return null;
    }

    @Override
    protected TableField<TArraysRecord, ArrayRecord<Integer>> TArrays_NUMBER_R() {
        return null;
    }

    @Override
    protected TableField<TArraysRecord, ArrayRecord<Date>> TArrays_DATE_R() {
        return null;
    }

    @Override
    protected TableField<TArraysRecord, ArrayRecord<Long>> TArrays_NUMBER_LONG_R() {
        return null;
    }

    @Override
    protected TableField<TBookRecord, ? extends Enum<?>> TBook_LANGUAGE_ID() {
        return T_BOOK.LANGUAGE_ID;
    }

    @Override
    protected TableField<TBookRecord, String> TBook_CONTENT_TEXT() {
        return T_BOOK.CONTENT_TEXT;
    }

    @Override
    protected TableField<TBookRecord, Integer> TBook_PUBLISHED_IN() {
        return T_BOOK.PUBLISHED_IN;
    }

    @Override
    protected TableField<TBookRecord, byte[]> TBook_CONTENT_PDF() {
        return T_BOOK.CONTENT_PDF;
    }

    @Override
    protected TableField<TBookRecord, ? extends Enum<?>> TBook_STATUS() {
        return T_BOOK.STATUS;
    }

    @Override
    protected Table<VLibraryRecord> VLibrary() {
        return V_LIBRARY;
    }

    @Override
    protected TableField<VLibraryRecord, String> VLibrary_TITLE() {
        return V_LIBRARY.TITLE;
    }

    @Override
    protected TableField<VLibraryRecord, String> VLibrary_AUTHOR() {
        return V_LIBRARY.AUTHOR;
    }

    @Override
    protected Table<?> VAuthor() {
        return V_AUTHOR;
    }

    @Override
    protected Table<?> VBook() {
        return V_BOOK;
    }

    @Override
    protected UpdatableTable<XUnusedRecord> TDirectory() {
        return null;
    }

    @Override
    protected TableField<XUnusedRecord, Integer> TDirectory_ID() {
        return null;
    }

    @Override
    protected TableField<XUnusedRecord, Integer> TDirectory_PARENT_ID() {
        return null;
    }

    @Override
    protected TableField<XUnusedRecord, Integer> TDirectory_IS_DIRECTORY() {
        return null;
    }

    @Override
    protected TableField<XUnusedRecord, String> TDirectory_NAME() {
        return null;
    }

    @Override
    protected UpdatableTable<TTriggersRecord> TTriggers() {
        return T_TRIGGERS;
    }

    @Override
    protected TableField<TTriggersRecord, Integer> TTriggers_ID_GENERATED() {
        return T_TRIGGERS.ID_GENERATED;
    }

    @Override
    protected TableField<TTriggersRecord, Integer> TTriggers_ID() {
        return T_TRIGGERS.ID;
    }

    @Override
    protected TableField<TTriggersRecord, Integer> TTriggers_COUNTER() {
        return T_TRIGGERS.COUNTER;
    }

    @Override
    protected Table<TIdentityRecord> TIdentity() {
        return T_IDENTITY;
    }

    @Override
    protected TableField<TIdentityRecord, Integer> TIdentity_ID() {
        return T_IDENTITY.ID;
    }

    @Override
    protected TableField<TIdentityRecord, Integer> TIdentity_VAL() {
        return T_IDENTITY.VAL;
    }

    @Override
    protected UpdatableTable<TIdentityPkRecord> TIdentityPK() {
        return T_IDENTITY_PK;
    }

    @Override
    protected TableField<TIdentityPkRecord, Integer> TIdentityPK_ID() {
        return T_IDENTITY_PK.ID;
    }

    @Override
    protected TableField<TIdentityPkRecord, Integer> TIdentityPK_VAL() {
        return T_IDENTITY_PK.VAL;
    }

    @Override
    protected Field<? extends Number> FAuthorExistsField(String authorName) {
        return Routines.fAuthorExists(authorName);
    }

    @Override
    protected Field<? extends Number> FOneField() {
        return Routines.fOne();
    }

    @Override
    protected Field<? extends Number> FNumberField(Number n) {
        return Routines.fNumber((Integer) n);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Field<? extends Number> FNumberField(Field<? extends Number> n) {
        return Routines.fNumber((Field<Integer>) n);
    }

    @Override
    protected Field<? extends Number> F317Field(Number n1, Number n2, Number n3, Number n4) {
        return Routines.f317((Integer) n1, (Integer) n2, (Integer) n3, (Integer) n4);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Field<? extends Number> F317Field(Field<? extends Number> n1, Field<? extends Number> n2,
        Field<? extends Number> n3, Field<? extends Number> n4) {
        return Routines.f317((Field<Integer>) n1, (Field<Integer>) n2, (Field<Integer>) n3, (Field<Integer>) n4);
    }

    @Override
    protected Field<Result<Record>> FGetOneCursorField(Integer[] array) {
        return Routines.fGetOneCursor(array);
    }

    @Override
    protected Field<Integer[]> FArrays1Field(Field<Integer[]> array) {
        return Routines.fArrays1(array);
    }

    @Override
    protected Field<Long[]> FArrays2Field(Field<Long[]> array) {
        return Routines.fArrays2(array);
    }

    @Override
    protected Field<String[]> FArrays3Field(Field<String[]> array) {
        return Routines.fArrays3(array);
    }

    @Override
    protected <T extends ArrayRecord<Integer>> Field<T> FArrays1Field_R(Field<T> array) {
        return null;
    }

    @Override
    protected <T extends ArrayRecord<Long>> Field<T> FArrays2Field_R(Field<T> array) {
        return null;
    }

    @Override
    protected <T extends ArrayRecord<String>> Field<T> FArrays3Field_R(Field<T> array) {
        return null;
    }

    @Override
    protected Class<? extends UDTRecord<?>> cUAddressType() {
        return UAddressType.U_ADDRESS_TYPE.getRecordType();
    }

    @Override
    protected Class<? extends UDTRecord<?>> cUStreetType() {
        return UStreetType.U_STREET_TYPE.getRecordType();
    }

    @Override
    protected Class<?> cRoutines() {
        return Routines.class;
    }

    @Override
    protected boolean supportsOUTParameters() {
        return true;
    }

    @Override
    protected boolean supportsReferences() {
        return true;
    }

    @Override
    protected boolean supportsRecursiveQueries() {
        return false;
    }

    @Override
    protected Class<?> cLibrary() {
        return null;
    }

    @Override
    protected Class<?> cSequences() {
        return Sequences.class;
    }

    @Override
    protected DataType<?>[] getCastableDataTypes() {
        return new DataType<?>[] {
            PostgresDataType.ACLITEM,
            PostgresDataType.BIGINT,
            PostgresDataType.BIT,
            PostgresDataType.BITVARYING,
            PostgresDataType.BOOL,
            PostgresDataType.BOOLEAN,
            PostgresDataType.BYTEA,
            PostgresDataType.CHAR,
            PostgresDataType.CHARACTER,
            PostgresDataType.CHARACTERVARYING,
            PostgresDataType.CID,
            PostgresDataType.DATE,
            PostgresDataType.DECIMAL,
            PostgresDataType.DOUBLEPRECISION,
            PostgresDataType.FLOAT4,
            PostgresDataType.FLOAT8,
            PostgresDataType.INT,
            PostgresDataType.INT2,
            PostgresDataType.INT4,
            PostgresDataType.INT8,
            PostgresDataType.INTEGER,
            PostgresDataType.MONEY,
            PostgresDataType.NAME,
            PostgresDataType.NUMERIC,
            PostgresDataType.OID,
            PostgresDataType.OIDVECTOR,
            PostgresDataType.REAL,
            PostgresDataType.REGPROC,
            PostgresDataType.SMALLINT,
            PostgresDataType.TEXT,
            PostgresDataType.TID,
            PostgresDataType.TIME,
            PostgresDataType.TIMESTAMP,
            PostgresDataType.TIMESTAMPTZ,
            PostgresDataType.TIMESTAMPWITHOUTTIMEZONE,
            PostgresDataType.TIMESTAMPWITHTIMEZONE,
            PostgresDataType.TIMETZ,
            PostgresDataType.TIMEWITHOUTTIMEZONE,
            PostgresDataType.TIMEWITHTIMEZONE,
        };
    }

    @Test
    public void testPostgresJavaKeywordEnums() throws Exception {
        reset = false;

        assertEquals(3,
        create().insertInto(T_959)
                .set(T_959.JAVA_KEYWORDS, U_959.public_)
                .newRecord()
                .set(T_959.JAVA_KEYWORDS, U_959.abstract_)
                .newRecord()
                .set(T_959.JAVA_KEYWORDS, U_959.class_)
                .execute());

        List<U_959> result =
        create().selectFrom(T_959)
                .orderBy(T_959.JAVA_KEYWORDS)
                .fetch(T_959.JAVA_KEYWORDS);

        assertEquals(3, result.size());
        assertEquals(U_959.abstract_, result.get(0));
        assertEquals(U_959.class_, result.get(1));
        assertEquals(U_959.public_, result.get(2));
    }

    @Test
    public void testPostgresArrayOperations() throws Exception {

        // [#1107] The contains operator @> is implemented in Field.contains()
        // -------------------------------------------------------------------
        assertEquals(0,
        create().selectCount()
                .from(T_ARRAYS)
                .where(T_ARRAYS.NUMBER_ARRAY.contains((Integer[])null))
                .fetchOne(0));

        assertEquals(3,
        create().selectCount()
                .from(T_ARRAYS)
                .where(T_ARRAYS.NUMBER_ARRAY.contains(new Integer[0]))
                .fetchOne(0));

        assertEquals(2,
        create().selectCount()
                .from(T_ARRAYS)
                .where(T_ARRAYS.NUMBER_ARRAY.contains(new Integer[] { 1 }))
                .fetchOne(0));

        assertEquals(1,
        create().selectCount()
                .from(T_ARRAYS)
                .where(T_ARRAYS.NUMBER_ARRAY.contains(new Integer[] { 1, 2 }))
                .fetchOne(0));

        assertEquals(0,
        create().selectCount()
                .from(T_ARRAYS)
                .where(T_ARRAYS.NUMBER_ARRAY.contains(new Integer[] { 1, 2, 3 }))
                .fetchOne(0));

    }
}
