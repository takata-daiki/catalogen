package org.pw.groovy.jpa;

import groovy.lang.Closure;
import groovy.lang.GroovyRuntimeException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.stmt.Statement;
import org.pw.groovy.jpa.visitors.SelectItemVisitor;
import org.pw.groovy.jpa.visitors.SinglePropertyVisitor;

/**
 * <code>GroovyDAO</code> is a powerful helper class for using the JPA. It
 * provides a generic DAO for JPA entities.
 * <code>GroovyDAO</code> based on the idea of
 * <code>groovy.sql.DataSet</code> and is able to transform Groovy Closures in
 * JPQL 2.0 statements. The Groovy source code for any accessed Closure must be
 * on the classpath at runtime.
 *
 * <h6>Examples:</h6>
 *
 *
 * <pre>
 *
 * def em = // an instance of javax.persistence.EntityManager
 * def dao = em.getDAO(Customer.class) // created a DAO for example Customer entity
 *
 * // simple examples
 * dao.find{ it.firstname == 'Bob' }
 * dao.findAll{ it.firstname.like('B') }
 * dao.count { it.address.street.equalsIgnoreCase('street') }
 * ...
 *
 * // binding local variables
 * def name = 'Bob'
 * dao.findAll{ !it.newsletter && it.firstname == name }
 *
 * // Joins and Subqueries :-)
 * dao.findAll{ it.orders.orderItems.any() }
 * dao.findAll{ it.orders.every{it.orderItems.any{ it.quantity > 2 }} }
 * dao.findAll{ it.orderItems.quantity.min() > 1 }
 * dao.findAll{ it.orders.count{it.orderItems.isEmpty()} > 1 }
 * ...
 *
 * // using of Ranges
 * def date1, date2 = // two Date parameters
 * dao.findAll{ it.birthday in date1..date2 }
 *
 * // a more complex example:
 * dao.where{ it.orders.any() && it.email }
 *          .orderBy{it.surname}
 *          .take(100)
 *          .each{
 *              println "$it.firstname $it.surname"
 *          }
 *
 * </pre>
 *
 * @param <E> the type of Entity
 * @author Paul Weinhold
 */
public class GroovyDAO<E> {

    private GroovyDAO<E> parent;
    private Class<E> entityClass;
    private String orderBy, identificationVariable;
    private EntityManager entityManager;
    private Context context;
    private QueryParameters parameters;
    private Integer skip, take;

    private GroovyDAO(GroovyDAO<E> parent) {
        this.parent = parent;
        if (parent != null && parent.parameters != null) {
            parameters = parent.parameters.clone();
        }
    }

    /**
     * Constructs a
     * <code>GroovyDAO</code> for the given entity class. Alternatively you can
     * use the supplied extension method for the
     * {@link javax.persistence.EntityManager}:
     *
     * <pre>
     *  def dao = entityManager.getDAO(entityClass)
     * </pre>
     *
     * @see EntityManagerExtension
     * @param entityClass the type of the Entity
     * @param entityManager the EntityManager
     */
    public GroovyDAO(Class<E> entityClass, EntityManager entityManager) {
        this.entityClass = entityClass;
        this.entityManager = entityManager;
        parameters = new QueryParameters();
    }

    /**
     * Checks whether there are any elements of the entity.
     *
     * @return {@code true} if there are elements, else {@code false}
     */
    public boolean any() {
        return count() > 0;
    }

    /**
     * Checks if there are matching elements for the {@code closure}.
     *
     * @param closure the condition
     * @return {@code true} if there are matching elements, else {@code false}
     */
    public boolean any(Closure closure) {
        return where(closure).any();
    }

    /**
     * Returns the average value of a selected property. This function operates with
     * the JPQL aggregate function AVG. <h6>Examples:</h6>
     * <pre>
     *  def dao = // a GroovyDAO for an exemplary OrderItem entity
     *  def avgQuantity = dao.avg{ it.quantity }
     * </pre>
     *
     * @param closure the selected property
     * @return the average of a selected property
     */
    public double avg(Closure closure) {
        return (double) aggregateFunction(closure, "AVG");
    }

    /**
     * Transforms each entity into a new value using the {@code transform}
     * closure returning a list of transformed values. This function operates
     * with the JPQL SELECT Clause. <h6>Examples:</h6>
     * <pre>
     *  def dao = // a GroovyDAO for an exemplary Customer entity
     *
     *  List surnames = dao.collect{ it.surname }
     *  List surnamesUpperCase = dao.collect{ it.surname.toUpperCase() }
     *  List<Name> names = dao.collect{ new Name(it.firstname, it.surname) }
     * </pre>
     *
     * @param transform the closure used to transform each entity
     * @return a list of transformed values
     */
    public List collect(Closure transform) {
        Context c = new Context(new ClosureContext(transform), getEntityClass(), getIdentificationVariable(), getEntityManager().getMetamodel(), getParameters().clone());
        SelectItemVisitor visitor = new SelectItemVisitor(c);
        getStatement(transform).visit(visitor);
        return getResultList(Object.class, getQuery(null, c.getClause()));
    }

    /**
     * Transforms each entity into a new value using the {@code transform}
     * closure returning a list of transformed values and adding it to the
     * supplied {@code collector}.
     *
     * @see GroovyDAO#collect(groovy.lang.Closure)
     * @param collector the Collection to which the transformed values are added
     * @param transfrom the closure used to transform each entity
     * @return the collector with all transformed values added to it
     */
    public Collection collect(Collection collector, Closure transfrom) {
        collector.addAll(collect(transfrom));
        return collector;
    }

    /**
     * Counts the number of entities. This function operates with the JPQL
     * aggregate function COUNT.
     *
     * @return the count of entities
     */
    public long count() {
        return getSingleResult(Long.class, getQuery("COUNT"));
    }

    /**
     * Counts the number of entities that satisfy the condition specified
     * {@code closure}. This function operates with the JPQL aggregate function
     * COUNT.
     *
     * @param closure a closure condition
     * @return the number of occurrences
     */
    public long count(Closure closure) {
        return where(closure).count();
    }

    /**
     * Make an instance managed and persistent.
     *
     * @see EntityManager#persist(java.lang.Object)
     * @param entity entity instance
     */
    public void create(E entity) {
        getEntityManager().persist(entity);
    }

    /**
     * Remove the entity instance.
     *
     * @see EntityManager#remove(java.lang.Object)
     * @param entity entity instance
     */
    public void delete(E entity) {
        getEntityManager().remove(entity);
    }

    /**
     * Remove all entity instances.
     */
    public void deleteAll() {
        for (E entity : findAll()) {
            delete(entity);
        }
    }

    /**
     * Remove all entity instances that satisfy the condition specified.
     *
     * @param closure a closure condition
     */
    public void deleteAll(Closure closure) {
        where(closure).deleteAll();
    }

    /**
     * Used to determine if the given predicate closure is valid.
     *
     * @param closure the closure predicate used for matching.
     * @return {@code true} if every iteration of the object matches the closure
     * predicate
     */
    public boolean every(Closure closure) {
        return count() == where(closure).count();
    }

    /**
     * Finds all entities.
     *
     * @see Query#getResultList()
     * @return a List of the entities found
     */
    public List<E> findAll() {
        return getResultList(getEntityClass(), getQuery());
    }

    /**
     * Finds all entities matching the closure condition.
     *
     * @param closure a closure condition
     * @return a List of the entities found
     */
    public List<E> findAll(Closure closure) {       
        return where(closure).findAll();
    }

    /**
     * Execute a SELECT query that returns a single result.
     *
     * @return a single result
     */
    public E find() {
        return getSingleResult(getEntityClass(), getQuery());
    }

    /**
     * Find by primary key. If the entity instance is contained in the
     * persistence context, it is returned from there.
     *
     * @see EntityManager#find(java.lang.Class, java.lang.Object)
     * @param primaryKey the primary key
     * @return the found entity instance or {@code null} if the entity does not
     * exist
     */
    public E find(Object primaryKey) {
        return getEntityManager().find(getEntityClass(), primaryKey);
    }

    /**
     * Find by closure condition. If the entity instance is contained in the
     * persistence context, it is returned from there.
     *
     * @param closure the closure condition
     * @return the found entity instance or {@code null} if the entity does not
     * exist
     */
    public E find(Closure closure) {
        return where(closure).find();
    }

    /**
     * Allows the subscript operator to be used to find a entity by primary key.
     * If the entity instance is contained in the persistence context, it is
     * returned from there.
     *
     * @see GroovyDAO#find(java.lang.Object)
     * @param primaryKey
     * @return the found entity instance or {@code null} if the entity does not
     * exist
     */
    public E getAt(Object primaryKey) {
        return find(primaryKey);
    }

    /**
     * Returns a Iterator for the given GroovyDAO.
     *
     * @return a Iterator for the given GroovyDAO
     */
    public Iterator<E> iterator() {
        return findAll().iterator();
    }

    /**
     * Returns the maximum value of a selected property. This function operates
     * with the JPQL aggregate function MAX. <h6>Examples:</h6>
     * <pre>
     *  def dao = // a GroovyDAO for an exemplary OrderItem entity
     *  def maxQuantity = dao.max{ it.quantity }
     * </pre>
     *
     * @param closure the selected property
     * @return the maximum value of a selected property
     */
    public Object max(Closure closure) {
        return aggregateFunction(closure, "MAX");
    }

    /**
     * Returns the minimum value of a selected property. This function operates
     * with the JPQL aggregate function MIN. <h6>Examples:</h6>
     * <pre>
     *  def dao = // a GroovyDAO for an exemplary OrderItem entity
     *  def minQuantity = dao.min{ it.quantity }
     * </pre>
     *
     * @param closure the selected property
     * @return the minimum value of a selected property
     */
    public Object min(Closure closure) {
        return aggregateFunction(closure, "MIN");
    }

    /**
     * Returns a new {@code GroovyDAO} with a ORDER BY ASC clause. All
     * conditions of the original {@code GroovyDAO}s be combined with the
     * new.<h6>Examples:</h6>
     * <pre>
     *  def dao = // a GroovyDAO for an exemplary Customer entity
     * 
     *  // all customer sorted by first name and email
     *  def customers = dao.orderBy{it.firstname}.orderBy{it.email}.findAll()
     * </pre>
     *
     * @param closure the selected property
     * @return a new GroovyDAO with a ORDER BY ASC clause
     */
    public GroovyDAO<E> orderBy(Closure closure) {
        return orderBy(closure, "ASC");
    }

    /**
     * Returns a new {@code GroovyDAO} with a ORDER BY DESC clause. All
     * conditions of the original {@code GroovyDAO}s be combined with the new.
     *
     * @see GroovyDAO#orderBy(groovy.lang.Closure)
     * @param closure the selected property
     * @return a new GroovyDAO with a ORDER BY DESC clause
     */
    public GroovyDAO<E> orderDescBy(Closure closure) {
        return orderBy(closure, "DESC");
    }

    private GroovyDAO<E> orderBy(Closure closure, String keyword) {
        GroovyDAO<E> dao = new GroovyDAO<>(this);
        SinglePropertyVisitor visitor = new SinglePropertyVisitor(getIdentificationVariable());
        getStatement(closure).visit(visitor);
        dao.orderBy = visitor.getClause() + " " + keyword;
        dao.context = null;
        return dao;
    }

    /**
     * Returns a new {@code GroovyDAO} that skips a specified number of
     * entities. This function operates with the
     * {@link Query#setFirstResult(int) } method. <h6>Examples:</h6>
     * <pre>
     *  def dao = // a GroovyDAO for an exemplary Customer entity
     *  def customers = dao.skip(10).findAll()
     * </pre>
     *
     * @param count the number of entities that are to be skipped
     * @return a new GroovyDAO that skips a specified number of entities
     */
    public GroovyDAO<E> skip(int count) {
        GroovyDAO<E> dao = new GroovyDAO<>(this);
        dao.skip = count;
        return dao;
    }

    /**
     * Returns the sum of a selected property. This function operates with the
     * JPQL aggregate function SUM. <h6>Examples:</h6>
     * <pre>
     *  def dao = // a GroovyDAO for an exemplary OrderItem entity
     *  def sumQuantity = dao.sum{ it.quantity }
     * </pre>
     *
     * @param closure the selected property
     * @return the sum of a selected property
     */
    public Object sum(Closure closure) {
        return aggregateFunction(closure, "SUM");
    }

    /**
     * Returns a new {@code GroovyDAO} that takes a specified number of
     * entities. This function operates with the
     * {@link Query#setMaxResults(int)} method. <h6>Examples:</h6>
     * <pre>
     *  def dao = // a GroovyDAO for an exemplary Customer entity
     *  def customers = dao.take(20).findAll()
     * </pre>
     *
     * @param count the number of entities that are to be taken
     * @return a new GroovyDAO that takes a specified number of entities
     */
    public GroovyDAO<E> take(int count) {
        GroovyDAO<E> dao = new GroovyDAO<>(this);
        dao.take = count;
        return dao;
    }

    /**
     * Merge the state of the given entity.
     *
     * @see EntityManager#merge(java.lang.Object)
     * @param entity entity instance
     * @return the managed instance that the state was merged to
     */
    public E update(E entity) {
        return getEntityManager().merge(entity);
    }

    /**
     * Creates a new {@code GroovyDAO} with the specified condition. All
     * conditions of the original {@code GroovyDAO}s be combined with the new.
     * <h6>Examples:</h6>
     * <pre>
     *  def dao = // a GroovyDAO for an exemplary Customer entity
     *  def customers = dao.where{it.firstname == 'Bob'}.where{it.email}.findAll()
     *  assert customers == dao.findAll{it.firstname == 'Bob' && it.email}
     * </pre>
     *
     * @param closure the closure condition
     * @return a new GroovyDAO with the specified condition
     */
    public GroovyDAO<E> where(Closure closure) {
        GroovyDAO<E> dao = new GroovyDAO<>(this);
        Context c = new Context(new ClosureContext(closure), getEntityClass(), getIdentificationVariable(), getEntityManager().getMetamodel(), dao.getParameters());
        c.parse(getStatement(closure));
        dao.identificationVariable = c.getIdentificationVariable();
        dao.context = c;
        dao.orderBy = null;
        return dao;
    }

    private String getQuery() {
        return getQuery(null, null);
    }

    private String getQuery(String aggregateFunction) {
        return getQuery(aggregateFunction, null);
    }

    private String getQuery(String aggregateFunction, String selectItem) {
        String iv = getIdentificationVariable();
        selectItem = selectItem == null ? iv : selectItem;
        String select = aggregateFunction != null ? aggregateFunction + "(" + selectItem + ")" : selectItem;
        String ordBy = getOrderBy();
        String where = getWhere();
        String joins = getJoins();
        return "SELECT DISTINCT "
                + select
                + " FROM "
                + getTableName() + " " + iv
                + (joins.isEmpty() ? "" : joins)
                + (where.isEmpty() ? "" : " WHERE " + where)
                + (ordBy.isEmpty() ? "" : " ORDER BY " + ordBy);
    }

    private String getJoins() {
        String joins = "";
        if (parent != null) {
            joins = parent.getJoins();
        }
        if (context != null && context.getJoins() != null) {
            joins += context.getJoins();
        }
        return joins;
    }

    private String getWhere() {
        String parentWhere = null;
        if (parent != null) {
            parentWhere = parent.getWhere();
        }
        String where = null;
        if (context != null) {
            where = context.getClause();
        }
        boolean existParentWhere = parentWhere != null && !parentWhere.isEmpty();
        return (existParentWhere ? "(" + parentWhere + ")" : "")
                + (existParentWhere && where != null ? " AND " : "")
                + (where != null ? where : "");
    }

    private String getOrderBy() {
        String parentOrderBy = null;
        if (parent != null) {
            parentOrderBy = parent.getOrderBy();
        }
        boolean existParentOrder = parentOrderBy != null && !parentOrderBy.isEmpty();

        return (existParentOrder ? parentOrderBy : "")
                + (existParentOrder && orderBy != null ? ", " : "")
                + (orderBy != null ? orderBy : "");
    }

    private String getIdentificationVariable() {
        if (identificationVariable != null) {
            return identificationVariable;
        }
        if (parent != null) {
            return parent.getIdentificationVariable();
        } else {
            identificationVariable = IdentificationVariableCreator.createIdentificationVariable();
            return identificationVariable;
        }
    }

    private Statement getStatement(Closure closure) {
        if (closure != null) {
            ClassNode classNode = closure.getMetaClass().getClassNode();
            if (classNode == null) {
                throw new GroovyRuntimeException(
                        "GroovyDAO unable to evaluate expression. AST not available for closure: " + closure.getMetaClass().getTheClass().getName()
                        + ". Is the source code on the classpath?");
            }
            List methods = classNode.getDeclaredMethods("doCall");
            if (!methods.isEmpty()) {
                MethodNode method = (MethodNode) methods.get(0);
                if (method != null) {
                    Statement statement = method.getCode();
                    if (statement != null) {
                        return statement;
                    }
                }
            }
        }
        throw new GroovyRuntimeException("GroovyDAO unable to evaluate expression.");
    }

    private Object aggregateFunction(Closure closure, String operator) {
        SinglePropertyVisitor visitor = new SinglePropertyVisitor(getIdentificationVariable());
        getStatement(closure).visit(visitor);
        return getSingleResult(Object.class, getQuery(operator, visitor.getClause()));
    }

    private Integer getSkip() {
        if (skip != null) {
            return skip;
        }
        if (parent != null) {
            return parent.getSkip();
        } else {
            return null;
        }
    }

    private Integer getTake() {
        if (take != null) {
            return take;
        }
        if (parent != null) {
            return parent.getTake();
        } else {
            return null;
        }
    }

    private Class<E> getEntityClass() {
        return getRoot().entityClass;
    }

    private QueryParameters getParameters() {
        return parameters;
    }

    private EntityManager getEntityManager() {
        return getRoot().entityManager;
    }

    private GroovyDAO<E> getRoot() {
        if (parent != null) {
            return parent.getRoot();
        } else {
            return this;
        }
    }

    private <R> List<R> getResultList(Class<R> type, String query) {
        TypedQuery<R> typedQuery = createTypedQuery(type, query);
        setRange(typedQuery);
        return typedQuery.getResultList();
    }

    private <R> R getSingleResult(Class<R> type, String query) {
        TypedQuery<R> typedQuery = createTypedQuery(type, query);
        return typedQuery.getSingleResult();
    }

    private <R> TypedQuery<R> createTypedQuery(Class<R> type, String query) {
        TypedQuery<R> typedQuery = getEntityManager().createQuery(query, type);
        initParameters(typedQuery);
        return typedQuery;
    }

    private void initParameters(Query query) {
        int i = 1;
        for (Object parameter : getParameters()) {
            query.setParameter(i++, parameter);
        }
    }

    private String getTableName() {
        return getEntityManager().getMetamodel().entity(getEntityClass()).getName();
    }

    private void setRange(Query query) {
        Integer firstResult = getSkip();
        Integer maxResult = getTake();
        if (firstResult != null) {
            query.setFirstResult(firstResult);
        }
        if (maxResult != null) {
            query.setMaxResults(maxResult);
        }
    }
}
