/*$Id$*/
package at.jku.sii.sqlitereader.model.master;

import at.jku.sii.sqlitereader.SqliteDataBase;
import at.jku.sii.sqlitereader.btree.BTreePage;
import at.jku.sii.sqlitereader.btree.BTreePages;
import at.jku.sii.sqlitereader.btree.TableCell;
import at.jku.sii.sqlitereader.model.Table;
import at.jku.sii.sqlitereader.record.Record;

public class TableRecord extends MasterTableRecord {
	private Table table;

	public TableRecord(Record c) {
		super(c);
	}

	@Override
	public SqliteType getType() {
		return SqliteType.TABLE;
	}

	@Override
	void resolve(SqliteDataBase db) {
		if (this.rootpage <= 0)
			return;
		@SuppressWarnings("unchecked")
		BTreePage<TableCell> tree = (BTreePage<TableCell>) BTreePages.read(db, this.rootpage);
		assert (tree.isTable()); // must be a table

		tree.resolve(db);
		this.table = new Table(this, tree);
	}

	public Table getTable() {
		return this.table;
	}

	@Override
	public Table getObject() {
		return this.getTable();
	}

	@Override
	public String toString() {
		return String.format("TableRecord [table=%s, %s]", this.table, this.toStringAttr());
	}

	@Override
	public void dump(StringBuilder b) {
		b.append("Table Record\n");
		this.dumpAttr(b);
		this.table.dump(b);
	}

}
