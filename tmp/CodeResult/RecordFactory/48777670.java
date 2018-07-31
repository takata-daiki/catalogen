package net.toften.jlips2.record;
import net.toften.jlips2.BaseFactory;

/**
 * @created 28-Sep-2005 14:06:53
 * @author thomaslarsen
 * @version 1.0
 */
public interface RecordFactory extends BaseFactory {
	public static final String PROP_RECORDFACTORY = "net.toften.jlips2.record.RecordFactory";

	/**
	 * 
	 * @param tableName    tableName
	 * @return
	 */
	public RecordManager getRecordManger(String tableName);

	/**
	 * @param tableName
	 * @return
	 */
	public CollectionManager getCollectionManager(String tableName);
	
}