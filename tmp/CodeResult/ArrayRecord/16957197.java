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

package org.jooq.impl;

import static org.jooq.SQLDialect.ASE;
import static org.jooq.SQLDialect.CUBRID;
import static org.jooq.SQLDialect.DB2;
import static org.jooq.SQLDialect.DERBY;
import static org.jooq.SQLDialect.H2;
import static org.jooq.SQLDialect.HSQLDB;
import static org.jooq.SQLDialect.INGRES;
import static org.jooq.SQLDialect.MYSQL;
import static org.jooq.SQLDialect.ORACLE;
import static org.jooq.SQLDialect.POSTGRES;
import static org.jooq.SQLDialect.SQLITE;
import static org.jooq.SQLDialect.SQLSERVER;
import static org.jooq.SQLDialect.SYBASE;
import static org.jooq.conf.SettingsTools.getRenderMapping;
import static org.jooq.impl.Util.combine;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;
import javax.xml.bind.JAXB;

import org.jooq.AggregateFunction;
import org.jooq.ArrayRecord;
import org.jooq.Attachable;
import org.jooq.Batch;
import org.jooq.BatchBindStep;
import org.jooq.BindContext;
import org.jooq.Case;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Cursor;
import org.jooq.DataType;
import org.jooq.DatePart;
import org.jooq.DeleteQuery;
import org.jooq.DeleteWhereStep;
import org.jooq.ExecuteContext;
import org.jooq.ExecuteListener;
import org.jooq.FactoryOperations;
import org.jooq.Field;
import org.jooq.FieldProvider;
import org.jooq.GroupConcatOrderByStep;
import org.jooq.Insert;
import org.jooq.InsertQuery;
import org.jooq.InsertSetStep;
import org.jooq.InsertValuesStep;
import org.jooq.LoaderOptionsStep;
import org.jooq.MergeUsingStep;
import org.jooq.Name;
import org.jooq.OrderedAggregateFunction;
import org.jooq.Param;
import org.jooq.Query;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.RenderContext;
import org.jooq.Result;
import org.jooq.ResultQuery;
import org.jooq.SQLDialect;
import org.jooq.Schema;
import org.jooq.Select;
import org.jooq.SelectQuery;
import org.jooq.SelectSelectStep;
import org.jooq.Sequence;
import org.jooq.SimpleSelectQuery;
import org.jooq.SimpleSelectWhereStep;
import org.jooq.Support;
import org.jooq.Table;
import org.jooq.TableLike;
import org.jooq.TableRecord;
import org.jooq.Truncate;
import org.jooq.UDT;
import org.jooq.UDTRecord;
import org.jooq.UpdatableRecord;
import org.jooq.UpdateQuery;
import org.jooq.UpdateSetStep;
import org.jooq.WindowIgnoreNullsStep;
import org.jooq.WindowOverStep;
import org.jooq.conf.RenderNameStyle;
import org.jooq.conf.Settings;
import org.jooq.conf.SettingsTools;
import org.jooq.exception.DataAccessException;
import org.jooq.exception.InvalidResultException;
import org.jooq.exception.SQLDialectNotSupportedException;
import org.jooq.tools.JooqLogger;
import org.jooq.tools.csv.CSVReader;
import org.jooq.types.DayToSecond;

/**
 * A factory providing implementations to the org.jooq interfaces
 * <p>
 * This factory is the main entry point for client code, to access jOOQ classes
 * and functionality. Here, you can instanciate all of those objects that cannot
 * be accessed through other objects. For example, to create a {@link Field}
 * representing a constant value, you can write:
 * <p>
 * <code><pre>
 * Field&lt;String&gt; field = Factory.val("Hello World")
 * </pre></code>
 * <p>
 * Also, some SQL clauses cannot be expressed easily with DSL, for instance the
 * EXISTS clause, as it is not applied on a concrete object (yet). Hence you
 * should write
 * <p>
 * <code><pre>
 * Condition condition = Factory.exists(new Factory().select(...));
 * </pre></code>
 * <p>
 * A <code>Factory</code> holds a reference to a JDBC {@link Connection} and
 * operates upon that connection. This means, that a <code>Factory</code> is
 * <i>not</i> thread-safe, since a JDBC Connection is not thread-safe either.
 *
 * @author Lukas Eder
 */
public class Factory implements FactoryOperations {

    /**
     * Generated UID
     */
    private static final long            serialVersionUID  = 2681360188806309513L;
    private static final JooqLogger      log               = JooqLogger.getLogger(Factory.class);

    private static final Factory[]       DEFAULT_INSTANCES = new Factory[SQLDialect.values().length];

    private transient Connection         connection;
    private transient DataSource         datasource;
    private final SQLDialect             dialect;

    @SuppressWarnings("deprecation")
    private final org.jooq.SchemaMapping mapping;
    private final Settings               settings;
    private final Map<String, Object>    data;

    // -------------------------------------------------------------------------
    // XXX Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a factory with a connection and a dialect configured.
     *
     * @param connection The connection to use with objects created from this
     *            factory
     * @param dialect The dialect to use with objects created from this factory
     */
    public Factory(Connection connection, SQLDialect dialect) {
        this(null, connection, dialect, null, null, null);
    }

    /**
     * Create a factory with a data source and a dialect configured.
     *
     * @param datasource The data source to use with objects created from this
     *            factory
     * @param dialect The dialect to use with objects created from this factory
     */
    public Factory(DataSource datasource, SQLDialect dialect) {
        this(datasource, null, dialect, null, null, null);
    }

    /**
     * Create a factory with a dialect configured.
     * <p>
     * Without a connection or data source, this factory cannot execute queries.
     * Use it to render SQL only.
     *
     * @param dialect The dialect to use with objects created from this factory
     */
    public Factory(SQLDialect dialect) {
        this(null, null, dialect, null, null, null);
    }

    /**
     * Create a factory with a connection, a dialect and a schema mapping
     * configured.
     *
     * @param connection The connection to use with objects created from this
     *            factory
     * @param dialect The dialect to use with objects created from this factory
     * @param mapping The schema mapping to use with objects created from this
     *            factory
     * @deprecated - 2.0.5 - Use
     *             {@link #Factory(Connection, SQLDialect, Settings)} instead
     */
    @Deprecated
    public Factory(Connection connection, SQLDialect dialect, org.jooq.SchemaMapping mapping) {
        this(null, connection, dialect, null, null, null);
    }

    /**
     * Create a factory with a connection, a dialect and settings configured.
     *
     * @param connection The connection to use with objects created from this
     *            factory
     * @param dialect The dialect to use with objects created from this factory
     * @param settings The runtime settings to apply to objects created from
     *            this factory
     */
    @SuppressWarnings("deprecation")
    public Factory(Connection connection, SQLDialect dialect, Settings settings) {
        this(null, connection, dialect, settings, new org.jooq.SchemaMapping(settings), null);
    }

    /**
     * Create a factory with a data source, a dialect and settings configured.
     *
     * @param datasource The data source to use with objects created from this
     *            factory
     * @param dialect The dialect to use with objects created from this factory
     * @param settings The runtime settings to apply to objects created from
     *            this factory
     */
    @SuppressWarnings("deprecation")
    public Factory(DataSource datasource, SQLDialect dialect, Settings settings) {
        this(datasource, null, dialect, settings, new org.jooq.SchemaMapping(settings), null);
    }

    /**
     * Create a factory with a dialect and settings configured
     * <p>
     * Without a connection or data source, this factory cannot execute queries.
     * Use it to render SQL only.
     *
     * @param dialect The dialect to use with objects created from this factory
     * @param settings The runtime settings to apply to objects created from
     *            this factory
     */
    @SuppressWarnings("deprecation")
    public Factory(SQLDialect dialect, Settings settings) {
        this(null, null, dialect, settings, new org.jooq.SchemaMapping(settings), null);
    }

    /**
     * Do the instanciation
     */
    @SuppressWarnings("deprecation")
    private Factory(DataSource datasource, Connection connection, SQLDialect dialect, Settings settings, org.jooq.SchemaMapping mapping, Map<String, Object> data) {
        this.connection = connection;
        this.datasource = datasource;
        this.dialect = dialect;
        this.settings = settings != null ? settings : SettingsTools.defaultSettings();
        this.mapping = mapping != null ? mapping : new org.jooq.SchemaMapping(this.settings);
        this.data = data != null ? data : new HashMap<String, Object>();
    }

    // -------------------------------------------------------------------------
    // XXX Configuration API
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public final SQLDialect getDialect() {
        return dialect;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final DataSource getDataSource() {
        return datasource;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDataSource(DataSource datasource) {
        this.datasource = datasource;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Connection getConnection() {

        // SQL-builder only Factory
        if (connection == null && datasource == null) {
            return null;
        }

        // [#1424] DataSource-enabled Factory with no Connection yet
        else if (connection == null && datasource != null) {
            return new DataSourceConnection(datasource, null, settings);
        }

        // Factory clone
        else if (connection.getClass() == DataSourceConnection.class) {
            return connection;
        }

        // Factory clone
        else if (connection.getClass() == ConnectionProxy.class) {
            return connection;
        }

        // [#1424] Connection-based Factory
        else {
            return new DataSourceConnection(null, new ConnectionProxy(connection, settings), settings);
        }
    }

    @Override
    public final void setConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Deprecated
    public final org.jooq.SchemaMapping getSchemaMapping() {
        return mapping;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Settings getSettings() {
        return settings;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Map<String, Object> getData() {
        return data;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Object getData(String key) {
        return data.get(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Object setData(String key, Object value) {
        return data.put(key, value);
    }

    // -------------------------------------------------------------------------
    // XXX RenderContext and BindContext accessors
    // -------------------------------------------------------------------------

    /**
     * Get a new {@link RenderContext} for the context of this factory
     * <p>
     * This will return an initialised render context as such:
     * <ul>
     * <li> <code>{@link RenderContext#declareFields()} == false</code></li>
     * <li> <code>{@link RenderContext#declareTables()} == false</code></li>
     * <li> <code>{@link RenderContext#inline()} == false</code></li>
     * <li> <code>{@link RenderContext#namedParams()} == false</code></li>
     * </ul>
     * <p>
     * RenderContext for JOOQ INTERNAL USE only. Avoid referencing it directly
     */
    public final RenderContext renderContext() {
        return new DefaultRenderContext(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String render(QueryPart part) {
        return renderContext().render(part);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String renderNamedParams(QueryPart part) {
        return renderContext().namedParams(true).render(part);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String renderInlined(QueryPart part) {
        return renderContext().inline(true).render(part);
    }

    /**
     * Get a new {@link BindContext} for the context of this factory
     * <p>
     * This will return an initialised bind context as such:
     * <ul>
     * <li> <code>{@link RenderContext#declareFields()} == false</code></li>
     * <li> <code>{@link RenderContext#declareTables()} == false</code></li>
     * </ul>
     * <p>
     * RenderContext for JOOQ INTERNAL USE only. Avoid referencing it directly
     */
    public final BindContext bindContext(PreparedStatement stmt) {
        return new DefaultBindContext(this, stmt);
    }

    /**
     * Get a new {@link BindContext} for the context of this factory
     * <p>
     * This will return an initialised bind context as such:
     * <ul>
     * <li> <code>{@link RenderContext#declareFields()} == false</code></li>
     * <li> <code>{@link RenderContext#declareTables()} == false</code></li>
     * </ul>
     * <p>
     * RenderContext for JOOQ INTERNAL USE only. Avoid referencing it directly
     */
    public final int bind(QueryPart part, PreparedStatement stmt) {
        return bindContext(stmt).bind(part).peekIndex();
    }

    // -------------------------------------------------------------------------
    // XXX Attachable and Serializable API
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public final void attach(Attachable... attachables) {
        attach(Arrays.asList(attachables));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void attach(Collection<Attachable> attachables) {
        for (Attachable attachable : attachables) {
            attachable.attach(this);
        }
    }

    // -------------------------------------------------------------------------
    // XXX Access to the loader API
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public final <R extends TableRecord<R>> LoaderOptionsStep<R> loadInto(Table<R> table) {
        return new LoaderImpl<R>(this, table);
    }

    // -------------------------------------------------------------------------
    // XXX Conversion of objects into tables
    // -------------------------------------------------------------------------

    /**
     * A synonym for {@link Select#asTable()}. It might look a bit more fluent
     * like this, to some users
     *
     * @see Select#asTable()
     */
    public static <R extends Record> Table<R> table(Select<R> select) {
        return select.asTable();
    }

    /**
     * A synonym for {@link #unnest(List)}
     *
     * @see #unnest(List)
     */
    @Support
    public static Table<?> table(List<?> list) {
        return table(list.toArray());
    }

    /**
     * A synonym for {@link #unnest(Object[])}
     *
     * @see #unnest(Object[])
     */
    @Support
    public static Table<?> table(Object[] array) {
        return table(val(array));
    }

    /**
     * A synonym for {@link #unnest(ArrayRecord)}
     *
     * @see #unnest(ArrayRecord)
     */
    @Support(ORACLE)
    public static Table<?> table(ArrayRecord<?> array) {
        return table(val(array));
    }

    /**
     * A synonym for {@link #unnest(Field)}
     *
     * @see #unnest(Field)
     */
    @Support({ H2, HSQLDB, POSTGRES, ORACLE })
    public static Table<?> table(Field<?> cursor) {
        return unnest(cursor);
    }

    /**
     * Create a table from a list of values
     * <p>
     * This is equivalent to the <code>TABLE</code> function for H2, or the
     * <code>UNNEST</code> function in HSQLDB and Postgres
     * <p>
     * For Oracle, use {@link #table(ArrayRecord)} instead, as Oracle knows only
     * typed arrays
     * <p>
     * In all other dialects, unnesting of arrays is simulated using several
     * <code>UNION ALL</code> connected subqueries.
     */
    @Support
    public static Table<?> unnest(List<?> list) {
        return unnest(list.toArray());
    }

    /**
     * Create a table from an array of values
     * <p>
     * This is equivalent to the <code>TABLE</code> function for H2, or the
     * <code>UNNEST</code> function in HSQLDB and Postgres
     * <p>
     * For Oracle, use {@link #table(ArrayRecord)} instead, as Oracle knows only
     * typed arrays
     * <p>
     * In all other dialects, unnesting of arrays is simulated using several
     * <code>UNION ALL</code> connected subqueries.
     */
    @Support
    public static Table<?> unnest(Object[] array) {
        return unnest(val(array));
    }

    /**
     * Create a table from an array of values
     * <p>
     * This wraps the argument array in a <code>TABLE</code> function for
     * Oracle. Currently, only Oracle knows typed arrays
     */
    @Support(ORACLE)
    public static Table<?> unnest(ArrayRecord<?> array) {
        return unnest(val(array));
    }

    /**
     * Create a table from a field. The supplied field can have any of these
     * types:
     * <ul>
     * <li> {@link Result}: For <code>CURSOR</code> or <code>REF CURSOR</code>
     * fields, typically fetched from stored functions or from nested tables</li>
     * <li> {@link ArrayRecord}: For Oracle-style <code>VARRAY</code> types.</li>
     * <li> {@link Object}[]: Array types, for other RDBMS's ARRAY types (e.g.
     * H2, HSQLDB, and Postgres)</li>
     * <li> {@link Object}: Any other type that jOOQ will try to convert in an
     * array first, before converting that array into a table</li>
     * </ul>
     * <p>
     * This functionality has only limited scope when used in H2, as ARRAY types
     * involved with stored functions can only be of type <code>Object[]</code>.
     * Such arrays are converted into <code>VARCHAR</code> arrays by jOOQ.
     * <p>
     * In all dialects where arrays are not supported, unnesting of arrays is
     * simulated using several <code>UNION ALL</code> connected subqueries.
     */
    @Support({ H2, HSQLDB, POSTGRES, ORACLE })
    public static Table<?> unnest(Field<?> cursor) {
        if (cursor == null) {
            throw new IllegalArgumentException();
        }

        // The field is an actual CURSOR or REF CURSOR returned from a stored
        // procedure or from a NESTED TABLE
        else if (cursor.getType() == Result.class) {
            return new FunctionTable<Record>(cursor);
        }

        // The field is an Oracle-style VARRAY constant
        else if (ArrayConstant.class.isAssignableFrom(cursor.getClass())) {
            return new ArrayTable(cursor);
        }

        // The field is an Oracle-style VARRAY field
        else if (ArrayRecord.class.isAssignableFrom(cursor.getDataType().getType())) {
            return new ArrayTable(cursor);
        }

        // The field is a regular array
        else if (cursor.getType().isArray() && cursor.getType() != byte[].class) {
            return new ArrayTable(cursor);
        }

        // The field has any other type. Try to make it an array
        throw new SQLDialectNotSupportedException("Converting arbitrary types into array tables is currently not supported");
    }

    // -------------------------------------------------------------------------
    // XXX SQL identifiers
    // -------------------------------------------------------------------------

    /**
     * Create a new SQL identifier using a qualified name.
     * <p>
     * Use this method to construct syntax-safe, SQL-injection-safe SQL
     * identifiers for use in plain SQL where {@link QueryPart} objects are
     * accepted. For instance, this can be used with any of these methods:
     * <ul>
     * <li> {@link #field(String, QueryPart...)}</li>
     * <li> {@link #field(String, Class, QueryPart...)}</li>
     * <li> {@link #field(String, DataType, QueryPart...)}</li>
     * </ul>
     * <p>
     * An example: <code><pre>
     * // This qualified name here
     * name("book", "title");
     *
     * // ... will render this SQL on SQL Server with RenderNameStyle.QUOTED set
     * [book].[title]
     * </pre></code>
     *
     * @param qualifiedName The SQL identifier's qualified name parts
     * @return A {@link QueryPart} that will render the SQL identifier
     */
    public static Name name(String... qualifiedName) {
        return new NameImpl(qualifiedName);
    }

    // -------------------------------------------------------------------------
    // XXX Plain SQL object factory
    // -------------------------------------------------------------------------

    /**
     * A PlainSQLTable is a table that can contain user-defined plain SQL,
     * because sometimes it is easier to express things directly in SQL, for
     * instance complex, but static subqueries or tables from different schemas.
     * <p>
     * Example
     * <p>
     * <code><pre>
     * String sql = "SELECT * FROM USER_TABLES WHERE OWNER = 'MY_SCHEMA'";
     * </pre></code>
     * <p>
     * The provided SQL must evaluate as a table whose type can be dynamically
     * discovered using JDBC's {@link ResultSetMetaData} methods. That way, you
     * can be sure that calling methods, such as {@link Table#getFields()} will
     * list the actual fields returned from your result set.
     * <p>
     * <b>NOTE</b>: When inserting plain SQL into jOOQ objects, you must
     * guarantee syntax integrity. You may also create the possibility of
     * malicious SQL injection. Be sure to properly use bind variables and/or
     * escape literals when concatenated into SQL clauses!
     *
     * @param sql The SQL
     * @return A table wrapping the plain SQL
     */
    @Support
    public static Table<Record> table(String sql) {
        return table(sql, new Object[0]);
    }

    /**
     * A PlainSQLTable is a table that can contain user-defined plain SQL,
     * because sometimes it is easier to express things directly in SQL, for
     * instance complex, but static subqueries or tables from different schemas.
     * There must be as many binding variables contained in the SQL, as passed
     * in the bindings parameter
     * <p>
     * Example
     * <p>
     * <code><pre>
     * String sql = "SELECT * FROM USER_TABLES WHERE OWNER = ?";
     * Object[] bindings = new Object[] { "MY_SCHEMA" };
     * </pre></code>
     * <p>
     * The provided SQL must evaluate as a table whose type can be dynamically
     * discovered using JDBC's {@link ResultSetMetaData} methods. That way, you
     * can be sure that calling methods, such as {@link Table#getFields()} will
     * list the actual fields returned from your result set.
     * <p>
     * <b>NOTE</b>: When inserting plain SQL into jOOQ objects, you must
     * guarantee syntax integrity. You may also create the possibility of
     * malicious SQL injection. Be sure to properly use bind variables and/or
     * escape literals when concatenated into SQL clauses!
     *
     * @param sql The SQL
     * @return A table wrapping the plain SQL
     */
    @Support
    public static Table<Record> table(String sql, Object... bindings) {
        return new SQLTable(sql, bindings);
    }

    /**
     * Create a qualified table, given its table name
     * <p>
     * This constructs a table reference given the table's qualified name. jOOQ
     * will render the table name according to your
     * {@link Settings#getRenderNameStyle()} settings. Choose
     * {@link RenderNameStyle#QUOTED} to prevent syntax errors and/or SQL
     * injection.
     * <p>
     * Example: <code><pre>
     * // This table...
     * tableName("MY_SCHEMA", "MY_TABLE");
     *
     * // ... will render this SQL on SQL Server with RenderNameStyle.QUOTED set
     * [MY_SCHEMA].[MY_TABLE]
     * </pre></code>
     *
     * @param qualifiedName The various parts making up your table's reference
     *            name.
     * @return A table referenced by <code>tableName</code>
     */
    @Support
    public static Table<Record> tableByName(String... qualifiedName) {
        return new QualifiedTable(qualifiedName);
    }

    /**
     * A custom SQL clause that can render arbitrary SQL elements.
     * <p>
     * This is useful for constructing more complex SQL syntax elements wherever
     * <code>Field</code> types are expected. An example for this is MySQL's
     * <code>GROUP_CONCAT</code> aggregate function, which has MySQL-specific
     * keywords that are hard to reflect in jOOQ's DSL: <code><pre>
     * GROUP_CONCAT([DISTINCT] expr [,expr ...]
     *       [ORDER BY {unsigned_integer | col_name | expr}
     *           [ASC | DESC] [,col_name ...]]
     *       [SEPARATOR str_val])
     *       </pre></code>
     * <p>
     * The above MySQL function can be expressed as such: <code><pre>
     * field("GROUP_CONCAT(DISTINCT {0} ORDER BY {1} ASC DEPARATOR '-')", expr1, expr2);
     * </pre></code>
     * <p>
     * <b>NOTE</b>: When inserting plain SQL into jOOQ objects, you must
     * guarantee syntax integrity. You may also create the possibility of
     * malicious SQL injection. Be sure to properly use bind variables and/or
     * escape literals when concatenated into SQL clauses! One way to escape
     * literals is to use {@link #name(String...)} and similar methods
     *
     * @param sql The SQL clause, containing {numbered placeholders} where query
     *            parts can be injected
     * @param parts The {@link QueryPart} objects that are rendered at the
     *            {numbered placeholder} locations
     * @return A field wrapping the plain SQL
     */
    public static Field<Object> field(String sql, QueryPart... parts) {
        return new SQLField<Object>(sql, SQLDataType.OTHER, parts);
    }

    /**
     * A custom SQL clause that can render arbitrary SQL elements.
     * <p>
     * This is useful for constructing more complex SQL syntax elements wherever
     * <code>Field</code> types are expected. An example for this is MySQL's
     * <code>GROUP_CONCAT</code> aggregate function, which has MySQL-specific
     * keywords that are hard to reflect in jOOQ's DSL: <code><pre>
     * GROUP_CONCAT([DISTINCT] expr [,expr ...]
     *       [ORDER BY {unsigned_integer | col_name | expr}
     *           [ASC | DESC] [,col_name ...]]
     *       [SEPARATOR str_val])
     *       </pre></code>
     * <p>
     * The above MySQL function can be expressed as such: <code><pre>
     * field("GROUP_CONCAT(DISTINCT {0} ORDER BY {1} ASC DEPARATOR '-')", expr1, expr2);
     * </pre></code>
     * <p>
     * <b>NOTE</b>: When inserting plain SQL into jOOQ objects, you must
     * guarantee syntax integrity. You may also create the possibility of
     * malicious SQL injection. Be sure to properly use bind variables and/or
     * escape literals when concatenated into SQL clauses! One way to escape
     * literals is to use {@link #name(String...)} and similar methods
     *
     * @param sql The SQL clause, containing {numbered placeholders} where query
     *            parts can be injected
     * @param type The field type
     * @param parts The {@link QueryPart} objects that are rendered at the
     *            {numbered placeholder} locations
     * @return A field wrapping the plain SQL
     */
    public static <T> Field<T> field(String sql, Class<T> type, QueryPart... parts) {
        return new SQLField<T>(sql, getDataType(type), parts);
    }

    /**
     * A custom SQL clause that can render arbitrary SQL elements.
     * <p>
     * This is useful for constructing more complex SQL syntax elements wherever
     * <code>Field</code> types are expected. An example for this is MySQL's
     * <code>GROUP_CONCAT</code> aggregate function, which has MySQL-specific
     * keywords that are hard to reflect in jOOQ's DSL: <code><pre>
     * GROUP_CONCAT([DISTINCT] expr [,expr ...]
     *       [ORDER BY {unsigned_integer | col_name | expr}
     *           [ASC | DESC] [,col_name ...]]
     *       [SEPARATOR str_val])
     *       </pre></code>
     * <p>
     * The above MySQL function can be expressed as such: <code><pre>
     * field("GROUP_CONCAT(DISTINCT {0} ORDER BY {1} ASC DEPARATOR '-')", expr1, expr2);
     * </pre></code>
     * <p>
     * <b>NOTE</b>: When inserting plain SQL into jOOQ objects, you must
     * guarantee syntax integrity. You may also create the possibility of
     * malicious SQL injection. Be sure to properly use bind variables and/or
     * escape literals when concatenated into SQL clauses! One way to escape
     * literals is to use {@link #name(String...)} and similar methods
     *
     * @param sql The SQL clause, containing {numbered placeholders} where query
     *            parts can be injected
     * @param type The field type
     * @param parts The {@link QueryPart} objects that are rendered at the
     *            {numbered placeholder} locations
     * @return A field wrapping the plain SQL
     */
    public static <T> Field<T> field(String sql, DataType<T> type, QueryPart... parts) {
        return new SQLField<T>(sql, type, parts);
    }

    /**
     * A PlainSQLField is a field that can contain user-defined plain SQL,
     * because sometimes it is easier to express things directly in SQL, for
     * instance complex proprietary functions. There must not be any binding
     * variables contained in the SQL.
     * <p>
     * Example:
     * <p>
     * <code><pre>
     * String sql = "DECODE(MY_FIELD, 1, 100, 200)";
     * </pre></code>
     * <p>
     * <b>NOTE</b>: When inserting plain SQL into jOOQ objects, you must
     * guarantee syntax integrity. You may also create the possibility of
     * malicious SQL injection. Be sure to properly use bind variables and/or
     * escape literals when concatenated into SQL clauses!
     *
     * @param sql The SQL
     * @return A field wrapping the plain SQL
     */
    @Support
    public static Field<Object> field(String sql) {
        return field(sql, new Object[0]);
    }

    /**
     * A PlainSQLField is a field that can contain user-defined plain SQL,
     * because sometimes it is easier to express things directly in SQL, for
     * instance complex proprietary functions. There must be as many binding
     * variables contained in the SQL, as passed in the bindings parameter
     * <p>
     * Example:
     * <p>
     * <code><pre>
     * String sql = "DECODE(MY_FIELD, ?, ?, ?)";
     * Object[] bindings = new Object[] { 1, 100, 200 };</pre></code>
     * <p>
     * <b>NOTE</b>: When inserting plain SQL into jOOQ objects, you must
     * guarantee syntax integrity. You may also create the possibility of
     * malicious SQL injection. Be sure to properly use bind variables and/or
     * escape literals when concatenated into SQL clauses!
     *
     * @param sql The SQL
     * @param bindings The bindings for the field
     * @return A field wrapping the plain SQL
     */
    @Support
    public static Field<Object> field(String sql, Object... bindings) {
        return field(sql, Object.class, bindings);
    }

    /**
     * A PlainSQLField is a field that can contain user-defined plain SQL,
     * because sometimes it is easier to express things directly in SQL, for
     * instance complex proprietary functions. There must not be any binding
     * variables contained in the SQL.
     * <p>
     * Example:
     * <p>
     * <code><pre>
     * String sql = "DECODE(MY_FIELD, 1, 100, 200)";
     * </pre></code>
     * <p>
     * <b>NOTE</b>: When inserting plain SQL into jOOQ objects, you must
     * guarantee syntax integrity. You may also create the possibility of
     * malicious SQL injection. Be sure to properly use bind variables and/or
     * escape literals when concatenated into SQL clauses!
     *
     * @param sql The SQL
     * @param type The field type
     * @return A field wrapping the plain SQL
     */
    @Support
    public static <T> Field<T> field(String sql, Class<T> type) {
        return field(sql, type, new Object[0]);
    }

    /**
     * A PlainSQLField is a field that can contain user-defined plain SQL,
     * because sometimes it is easier to express things directly in SQL, for
     * instance complex proprietary functions. There must be as many binding
     * variables contained in the SQL, as passed in the bindings parameter
     * <p>
     * Example:
     * <p>
     * <code><pre>
     * String sql = "DECODE(MY_FIELD, ?, ?, ?)";
     * Object[] bindings = new Object[] { 1, 100, 200 };</pre></code>
     * <p>
     * <b>NOTE</b>: When inserting plain SQL into jOOQ objects, you must
     * guarantee syntax integrity. You may also create the possibility of
     * malicious SQL injection. Be sure to properly use bind variables and/or
     * escape literals when concatenated into SQL clauses!
     *
     * @param sql The SQL
     * @param type The field type
     * @param bindings The bindings for the field
     * @return A field wrapping the plain SQL
     */
    @Support
    public static <T> Field<T> field(String sql, Class<T> type, Object... bindings) {
        return field(sql, getDataType(type), bindings);
    }

    /**
     * A PlainSQLField is a field that can contain user-defined plain SQL,
     * because sometimes it is easier to express things directly in SQL, for
     * instance complex proprietary functions. There must not be any binding
     * variables contained in the SQL.
     * <p>
     * Example:
     * <p>
     * <code><pre>
     * String sql = "DECODE(MY_FIELD, 1, 100, 200)";
     * </pre></code>
     * <p>
     * <b>NOTE</b>: When inserting plain SQL into jOOQ objects, you must
     * guarantee syntax integrity. You may also create the possibility of
     * malicious SQL injection. Be sure to properly use bind variables and/or
     * escape literals when concatenated into SQL clauses!
     *
     * @param sql The SQL
     * @param type The field type
     * @return A field wrapping the plain SQL
     */
    @Support
    public static <T> Field<T> field(String sql, DataType<T> type) {
        return field(sql, type, new Object[0]);
    }

    /**
     * A PlainSQLField is a field that can contain user-defined plain SQL,
     * because sometimes it is easier to express things directly in SQL, for
     * instance complex proprietary functions. There must be as many binding
     * variables contained in the SQL, as passed in the bindings parameter
     * <p>
     * Example:
     * <p>
     * <code><pre>
     * String sql = "DECODE(MY_FIELD, ?, ?, ?)";
     * Object[] bindings = new Object[] { 1, 100, 200 };</pre></code>
     * <p>
     * <b>NOTE</b>: When inserting plain SQL into jOOQ objects, you must
     * guarantee syntax integrity. You may also create the possibility of
     * malicious SQL injection. Be sure to properly use bind variables and/or
     * escape literals when concatenated into SQL clauses!
     *
     * @param sql The SQL
     * @param type The field type
     * @param bindings The bindings for the field
     * @return A field wrapping the plain SQL
     */
    @Support
    public static <T> Field<T> field(String sql, DataType<T> type, Object... bindings) {
        return new SQLField<T>(sql, type, bindings);
    }

    /**
     * Create a qualified field, given its (qualified) field name.
     * <p>
     * This constructs a field reference given the field's qualified name. jOOQ
     * will render the field name according to your
     * {@link Settings#getRenderNameStyle()} settings. Choose
     * {@link RenderNameStyle#QUOTED} to prevent syntax errors and/or SQL
     * injection.
     * <p>
     * Example: <code><pre>
     * // This field...
     * fieldName("MY_SCHEMA", "MY_TABLE", "MY_FIELD");
     *
     * // ... will render this SQL on SQL Server with RenderNameStyle.QUOTED set
     * [MY_SCHEMA].[MY_TABLE].[MY_FIELD]
     * </pre></code>
     * <p>
     * Another example: <code><pre>
     * create.select(field("length({1})", Integer.class, fieldByName("TITLE")))
     *       .from(tableByName("T_BOOK"))
     *       .fetch();
     *
     * // ... will execute this SQL on SQL Server:
     * select length([TITLE]) from [T_BOOK]
     * </pre></code>
     *
     * @param qualifiedName The various parts making up your field's reference
     *            name.
     * @return A field referenced by <code>fieldName</code>
     */
    @Support
    public static Field<Object> fieldByName(String... qualifiedName) {
        return fieldByName(Object.class, qualifiedName);
    }

    /**
     * Create a qualified field, given its (qualified) field name.
     * <p>
     * This constructs a field reference given the field's qualified name. jOOQ
     * will render the field name according to your
     * {@link Settings#getRenderNameStyle()} settings. Choose
     * {@link RenderNameStyle#QUOTED} to prevent syntax errors and/or SQL
     * injection.
     * <p>
     * Example: <code><pre>
     * // This field...
     * fieldName("MY_SCHEMA", "MY_TABLE", "MY_FIELD");
     *
     * // ... will render this SQL on SQL Server with RenderNameStyle.QUOTED set
     * [MY_SCHEMA].[MY_TABLE].[MY_FIELD]
     * </pre></code>
     * <p>
     * Another example: <code><pre>
     * create.select(field("length({1})", Integer.class, fieldByName("TITLE")))
     *       .from(tableByName("T_BOOK"))
     *       .fetch();
     *
     * // ... will execute this SQL on SQL Server:
     * select length([TITLE]) from [T_BOOK]
     * </pre></code>
     *
     * @param qualifiedName The various parts making up your field's reference
     *            name.
     * @param type The type of the returned field
     * @return A field referenced by <code>fieldName</code>
     */
    @Support
    public static <T> Field<T> fieldByName(Class<T> type, String... qualifiedName) {
        return fieldByName(getDataType(type), qualifiedName);
    }

    /**
     * Create a qualified field, given its (qualified) field name.
     * <p>
     * This constructs a field reference given the field's qualified name. jOOQ
     * will render the field name according to your
     * {@link Settings#getRenderNameStyle()} settings. Choose
     * {@link RenderNameStyle#QUOTED} to prevent syntax errors and/or SQL
     * injection.
     * <p>
     * Example: <code><pre>
     * // This field...
     * fieldName("MY_SCHEMA", "MY_TABLE", "MY_FIELD");
     *
     * // ... will render this SQL on SQL Server with RenderNameStyle.QUOTED set
     * [MY_SCHEMA].[MY_TABLE].[MY_FIELD]
     * </pre></code>
     * <p>
     * Another example: <code><pre>
     * create.select(field("length({1})", Integer.class, fieldByName("TITLE")))
     *       .from(tableByName("T_BOOK"))
     *       .fetch();
     *
     * // ... will execute this SQL on SQL Server:
     * select length([TITLE]) from [T_BOOK]
     * </pre></code>
     *
     * @param qualifiedName The various parts making up your field's reference
     *            name.
     * @param type The type of the returned field
     * @return A field referenced by <code>fieldName</code>
     */
    @Support
    public static <T> Field<T> fieldByName(DataType<T> type, String... qualifiedName) {
        return new QualifiedField<T>(type, qualifiedName);
    }

    /**
     * <code>function()</code> can be used to access native functions that are
     * not yet or insufficiently supported by jOOQ
     * <p>
     * <b>NOTE</b>: When inserting plain SQL into jOOQ objects, you must
     * guarantee syntax integrity. You may also create the possibility of
     * malicious SQL injection. Be sure to properly use bind variables and/or
     * escape literals when concatenated into SQL clauses!
     *
     * @param name The function name (without parentheses)
     * @param type The function return type
     * @param arguments The function arguments
     */
    @Support
    public static <T> Field<T> function(String name, Class<T> type, Field<?>... arguments) {
        return function(name, getDataType(type), nullSafe(arguments));
    }

    /**
     * <code>function()</code> can be used to access native functions that are
     * not yet or insufficiently supported by jOOQ
     * <p>
     * <b>NOTE</b>: When inserting plain SQL into jOOQ objects, you must
     * guarantee syntax integrity. You may also create the possibility of
     * malicious SQL injection. Be sure to properly use bind variables and/or
     * escape literals when concatenated into SQL clauses!
     *
     * @param name The function name (without parentheses)
     * @param type The function return type
     * @param arguments The function arguments
     */
    @Support
    public static <T> Field<T> function(String name, DataType<T> type, Field<?>... arguments) {
        return new Function<T>(name, type, nullSafe(arguments));
    }

    /**
     * Create a new condition holding plain SQL. There must not be any binding
     * variables contained in the SQL
     * <p>
     * Example:
     * <p>
     * <code><pre>
     * String sql = "(X = 1 and Y = 2)";</pre></code>
     * <p>
     * <b>NOTE</b>: When inserting plain SQL into jOOQ objects, you must
     * guarantee syntax integrity. You may also create the possibility of
     * malicious SQL injection. Be sure to properly use bind variables and/or
     * escape literals when concatenated into SQL clauses!
     *
     * @param sql The SQL
     * @return A condition wrapping the plain SQL
     */
    @Support
    public static Condition condition(String sql) {
        return condition(sql, new Object[0]);
    }

    /**
     * Create a new condition holding plain SQL. There must be as many binding
     * variables contained in the SQL, as passed in the bindings parameter
     * <p>
     * Example:
     * <p>
     * <code><pre>
     * String sql = "(X = ? and Y = ?)";
     * Object[] bindings = new Object[] { 1, 2 };</pre></code>
     * <p>
     * <b>NOTE</b>: When inserting plain SQL into jOOQ objects, you must
     * guarantee syntax integrity. You may also create the possibility of
     * malicious SQL injection. Be sure to properly use bind variables and/or
     * escape literals when concatenated into SQL clauses!
     *
     * @param sql The SQL
     * @param bindings The bindings
     * @return A condition wrapping the plain SQL
     */
    @Support
    public static Condition condition(String sql, Object... bindings) {
        return new SQLCondition(sql, bindings);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Query query(String sql) {
        return query(sql, new Object[0]);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Query query(String sql, Object... bindings) {
        return new SQLQuery(this, sql, bindings);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Query query(String sql, QueryPart... parts) {
        return new SQLQuery(this, sql, parts);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Result<Record> fetch(String sql) throws DataAccessException {
        return resultQuery(sql).fetch();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Result<Record> fetch(String sql, Object... bindings) throws DataAccessException {
        return resultQuery(sql, bindings).fetch();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Result<Record> fetch(String sql, QueryPart... parts) throws DataAccessException {
        return resultQuery(sql, parts).fetch();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Cursor<Record> fetchLazy(String sql) throws DataAccessException {
        return resultQuery(sql).fetchLazy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Cursor<Record> fetchLazy(String sql, Object... bindings) throws DataAccessException {
        return resultQuery(sql, bindings).fetchLazy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Cursor<Record> fetchLazy(String sql, QueryPart... parts) throws DataAccessException {
        return resultQuery(sql, parts).fetchLazy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final List<Result<Record>> fetchMany(String sql) throws DataAccessException {
        return resultQuery(sql).fetchMany();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final List<Result<Record>> fetchMany(String sql, Object... bindings) throws DataAccessException {
        return resultQuery(sql, bindings).fetchMany();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final List<Result<Record>> fetchMany(String sql, QueryPart... parts) throws DataAccessException {
        return resultQuery(sql, parts).fetchMany();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Record fetchOne(String sql) throws DataAccessException {
        return resultQuery(sql).fetchOne();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Record fetchOne(String sql, Object... bindings) throws DataAccessException {
        return resultQuery(sql, bindings).fetchOne();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Record fetchOne(String sql, QueryPart... parts) throws DataAccessException {
        return resultQuery(sql, parts).fetchOne();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int execute(String sql) throws DataAccessException {
        return query(sql).execute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int execute(String sql, Object... bindings) throws DataAccessException {
        return query(sql, bindings).execute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int execute(String sql, QueryPart... parts) throws DataAccessException {
        return query(sql, parts).execute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final ResultQuery<Record> resultQuery(String sql) {
        return resultQuery(sql, new Object[0]);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final ResultQuery<Record> resultQuery(String sql, Object... bindings) {
        return new SQLResultQuery(this, sql, bindings);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final ResultQuery<Record> resultQuery(String sql, QueryPart... parts) {
        return new SQLResultQuery(this, sql, parts);
    }

    // -------------------------------------------------------------------------
    // XXX JDBC convenience methods
    // -------------------------------------------------------------------------

    /**
     * Fetch all data from a JDBC {@link ResultSet} and transform it to a jOOQ
     * {@link Result}. After fetching all data, the JDBC ResultSet will be
     * closed.
     *
     * @param rs The JDBC ResultSet to fetch data from
     * @return The resulting jOOQ Result
     */
    @Override
    public final Result<Record> fetch(ResultSet rs) {
        ExecuteContext ctx = new DefaultExecuteContext(this);
        ExecuteListener listener = new ExecuteListeners(ctx);

        try {
            FieldProvider fields = new MetaDataFieldProvider(this, rs.getMetaData());

            ctx.resultSet(rs);
            return new CursorImpl<Record>(ctx, listener, fields).fetch();
        }
        catch (SQLException e) {
            ctx.sqlException(e);
            listener.exception(ctx);
            throw ctx.exception();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Result<Record> fetchFromCSV(String string) {
        return fetchFromCSV(string, ',');
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Result<Record> fetchFromCSV(String string, char delimiter) {
        CSVReader reader = new CSVReader(new StringReader(string), delimiter);
        List<String[]> all = null;

        try {
            all = reader.readAll();
        }
        catch (IOException e) {
            throw new DataAccessException("Could not read the CSV string", e);
        }

        FieldList fields = new FieldList();

        if (all.size() == 0) {
            return new ResultImpl<Record>(this, fields);
        }
        else {
            for (String name : all.get(0)) {
                fields.add(fieldByName(String.class, name));
            }

            Result<Record> result = new ResultImpl<Record>(this, fields);

            if (all.size() > 1) {
                for (String[] values : all.subList(1, all.size())) {
                    Record record = new RecordImpl(fields);

                    for (int i = 0; i < Math.min(values.length, fields.size()); i++) {
                        Util.setValue(record, fields.get(i), values[i]);
                    }

                    result.add(record);
                }
            }

            return result;
        }
    }

    // -------------------------------------------------------------------------
    // XXX Global Condition factory
    // -------------------------------------------------------------------------

    /**
     * Return a <code>Condition</code> that will always evaluate to true
     */
    @Support
    public static Condition trueCondition() {
        return new TrueCondition();
    }

    /**
     * Return a <code>Condition</code> that will always evaluate to false
     */
    @Support
    public static Condition falseCondition() {
        return new FalseCondition();
    }

    /**
     * Create an exists condition.
     * <p>
     * <code>EXISTS ([query])</code>
     */
    @Support
    public static Condition exists(Select<?> query) {
        return new SelectQueryAsExistsCondition(query, ExistsOperator.EXISTS);
    }

    /**
     * Create a not exists condition.
     * <p>
     * <code>NOT EXISTS ([query])</code>
     */
    @Support
    public static Condition notExists(Select<?> query) {
        return new SelectQueryAsExistsCondition(query, ExistsOperator.NOT_EXISTS);
    }

    // -------------------------------------------------------------------------
    // XXX Global Query factory
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public final <R extends Record> SimpleSelectWhereStep<R> selectFrom(Table<R> table) {
        return new SimpleSelectImpl<R>(this, table);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final SelectSelectStep select(Field<?>... fields) {
        return new SelectImpl(this).select(fields);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final SelectSelectStep selectZero() {
        return new SelectImpl(this).select(zero());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final SelectSelectStep selectOne() {
        return new SelectImpl(this).select(one());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final SelectSelectStep selectCount() {
        return new SelectImpl(this).select(count());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final SelectSelectStep selectDistinct(Field<?>... fields) {
        return new SelectImpl(this, true).select(fields);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final SelectSelectStep select(Collection<? extends Field<?>> fields) {
        return new SelectImpl(this).select(fields);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final SelectSelectStep selectDistinct(Collection<? extends Field<?>> fields) {
        return new SelectImpl(this, true).select(fields);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final SelectQuery selectQuery() {
        return new SelectQueryImpl(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final <R extends Record> SimpleSelectQuery<R> selectQuery(TableLike<R> table) {
        return new SimpleSelectQueryImpl<R>(this, table);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final <R extends Record> InsertQuery<R> insertQuery(Table<R> into) {
        return new InsertQueryImpl<R>(this, into);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final <R extends Record> InsertSetStep<R> insertInto(Table<R> into) {
        return new InsertImpl<R>(this, into, Collections.<Field<?>>emptyList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final <R extends Record> InsertValuesStep<R> insertInto(Table<R> into, Field<?>... fields) {
        return new InsertImpl<R>(this, into, Arrays.asList(fields));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final <R extends Record> InsertValuesStep<R> insertInto(Table<R> into, Collection<? extends Field<?>> fields) {
        return new InsertImpl<R>(this, into, fields);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Deprecated
    public final <R extends Record> Insert<R> insertInto(Table<R> into, Select<?> select) {
        return new InsertSelectQueryImpl<R>(this, into, into.getFields(), select);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final <R extends Record> UpdateQuery<R> updateQuery(Table<R> table) {
        return new UpdateQueryImpl<R>(this, table);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final <R extends Record> UpdateSetStep<R> update(Table<R> table) {
        return new UpdateImpl<R>(this, table);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final <R extends Record> MergeUsingStep<R> mergeInto(Table<R> table) {
        return new MergeImpl<R>(this, table);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final <R extends Record> DeleteQuery<R> deleteQuery(Table<R> table) {
        return new DeleteQueryImpl<R>(this, table);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final <R extends Record> DeleteWhereStep<R> delete(Table<R> table) {
        return new DeleteImpl<R>(this, table);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Batch batch(Query... queries) {
        return new BatchMultiple(this, queries);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Batch batch(Collection<? extends Query> queries) {
        return batch(queries.toArray(new Query[queries.size()]));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final BatchBindStep batch(Query query) {
        return new BatchSingle(this, query);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Batch batchStore(UpdatableRecord<?>... records) {
        return new BatchStore(this, records);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Batch batchStore(Collection<? extends UpdatableRecord<?>> records) {
        return batchStore(records.toArray(new UpdatableRecord[records.size()]));
    }

    // -------------------------------------------------------------------------
    // XXX DDL Statements
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public final <R extends Record> Truncate<R> truncate(Table<R> table) {
        return new TruncateImpl<R>(this, table);
    }

    // -------------------------------------------------------------------------
    // XXX Other queries for identites and sequences
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public final BigInteger lastID() {
        switch (getDialect()) {
            case DERBY: {
                Field<BigInteger> field = field("identity_val_local()", BigInteger.class);
                return select(field).fetchOne(field);
            }

            case H2:
            case HSQLDB: {
                Field<BigInteger> field = field("identity()", BigInteger.class);
                return select(field).fetchOne(field);
            }

            case INGRES: {
                Field<BigInteger> field = field("last_identity()", BigInteger.class);
                return select(field).fetchOne(field);
            }

            case CUBRID:
            case MYSQL: {
                Field<BigInteger> field = field("last_insert_id()", BigInteger.class);
                return select(field).fetchOne(field);
            }

            case SQLITE: {
                Field<BigInteger> field = field("last_insert_rowid()", BigInteger.class);
                return select(field).fetchOne(field);
            }

            case ASE:
            case SQLSERVER:
            case SYBASE: {
                Field<BigInteger> field = field("@@identity", BigInteger.class);
                return select(field).fetchOne(field);
            }

            default:
                throw new SQLDialectNotSupportedException("identity functionality not supported by " + getDialect());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final <T extends Number> T nextval(Sequence<T> sequence) {
        Field<T> nextval = sequence.nextval();
        return select(nextval).fetchOne(nextval);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final <T extends Number> T currval(Sequence<T> sequence) {
        Field<T> currval = sequence.currval();
        return select(currval).fetchOne(currval);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("deprecation")
    @Override
    public final int use(Schema schema) {
        int result = 0;

        try {
            String schemaName = render(schema);

            switch (dialect) {
                case DB2:
                case DERBY:
                case H2:
                case HSQLDB:
                    result = query("set schema " + schemaName).execute();
                    break;

                case ASE:
                case MYSQL:
                case SYBASE:
                    result = query("use " + schemaName).execute();
                    break;

                case ORACLE:
                    result = query("alter session set current_schema = " + schemaName).execute();
                    break;

                case POSTGRES:
                    result = query("set search_path = " + schemaName).execute();
                    break;

                // SQL Server do not support such a syntax for selecting
                // schemata, only for selecting databases
                case SQLSERVER:
                    break;

                // CUBRID and SQLite don't have any schemata
                case CUBRID:
                case SQLITE:
                    break;
            }
        }
        finally {
            getRenderMapping(settings).setDefaultSchema(schema.getName());
            mapping.use(schema);
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int use(String schema) {
        return use(new SchemaImpl(schema));
    }

    // -------------------------------------------------------------------------
    // XXX Global Record factory
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public final <R extends UDTRecord<R>> R newRecord(UDT<R> type) {
        return Util.newRecord(type, this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final <R extends TableRecord<R>> R newRecord(Table<R> table) {
        return Util.newRecord(table, this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final <R extends TableRecord<R>> R newRecord(Table<R> table, Object source) {
        R result = newRecord(table);
        result.from(source);
        return result;
    }

    // -------------------------------------------------------------------------
    // XXX Global Field and Function factory
    // -------------------------------------------------------------------------

    /**
     * Initialise a {@link Case} statement. Decode is used as a method name to
     * avoid name clashes with Java's reserved literal "case"
     *
     * @see Case
     */
    @Support
    public static Case decode() {
        return new CaseImpl();
    }

    /**
     * Gets the Oracle-style
     * <code>DECODE(expression, search, result[, search , result]... [, default])</code>
     * function
     *
     * @see #decode(Field, Field, Field, Field[])
     */
    @Support
    public static <Z, T> Field<Z> decode(T value, T search, Z result) {
        return decode(value, search, result, new Object[0]);
    }

    /**
     * Gets the Oracle-style
     * <code>DECODE(expression, search, result[, search , result]... [, default])</code>
     * function
     *
     * @see #decode(Field, Field, Field, Field[])
     */
    @Support
    public static <Z, T> Field<Z> decode(T value, T search, Z result, Object... more) {
        return decode(val(value), val(search), val(result), vals(more).toArray(new Field[0]));
    }

    /**
     * Gets the Oracle-style
     * <code>DECODE(expression, search, result[, search , result]... [, default])</code>
     * function
     *
     * @see #decode(Field, Field, Field, Field[])
     */
    @Support
    public static <Z, T> Field<Z> decode(Field<T> value, Field<T> search, Field<Z> result) {
        return decode(nullSafe(value), nullSafe(search), nullSafe(result), new Field[0]);
    }

    /**
     * Gets the Oracle-style
     * <code>DECODE(expression, search, result[, search , result]... [, default])</code>
     * function
     * <p>
     * Returns the dialect's equivalent to DECODE:
     * <ul>
     * <li>Oracle <a
     * href="http://www.techonthenet.com/oracle/functions/decode.php">DECODE</a></li>
     * </ul>
     * <p>
     * Other dialects: <code><pre>
     * CASE WHEN [this = search] THEN [result],
     *     [WHEN more...         THEN more...]
     *     [ELSE more...]
     * END
     * </pre></code>
     *
     * @param value The value to decode
     * @param search the mandatory first search parameter
     * @param result the mandatory first result candidate parameter
     * @param more the optional parameters. If <code>more.length</code> is even,
     *            then it is assumed that it contains more search/result pairs.
     *            If <code>more.length</code> is odd, then it is assumed that it
     *            contains more search/result pairs plus a default at the end.     *
     */
    @Support
    public static <Z, T> Field<Z> decode(Field<T> value, Field<T> search, Field<Z> result, Field<?>... more) 