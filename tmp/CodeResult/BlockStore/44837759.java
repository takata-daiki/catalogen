package co.recloud.ariadne.store;

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 11/21/11
 * Time: 1:36 PM
 * To change this template use File | Settings | File Templates.
 */
public interface BlockStore<T> {
    public T readBlock(Object key);
    public void writeBlock(Object key, T block);
}
