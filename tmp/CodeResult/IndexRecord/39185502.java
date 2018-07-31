/*$Id$*/
package at.jku.sii.sqlitereader.model.master;

import at.jku.sii.sqlitereader.SqliteDataBase;
import at.jku.sii.sqlitereader.btree.BTreePage;
import at.jku.sii.sqlitereader.btree.BTreePages;
import at.jku.sii.sqlitereader.btree.IndexCell;
import at.jku.sii.sqlitereader.model.Index;
import at.jku.sii.sqlitereader.record.Record;

public class IndexRecord extends MasterTableRecord {
	private Index index;

	public IndexRecord(Record c) {
		super(c);
	}

	@Override
	public SqliteType getType() {
		return SqliteType.INDEX;
	}

	@Override
	void resolve(SqliteDataBase db) {
		if (this.rootpage <= 0)
			return;
		@SuppressWarnings("unchecked")
		BTreePage<IndexCell> tree = (BTreePage<IndexCell>) BTreePages.read(db, this.rootpage);
		assert (!tree.isTable()); // must be a table

		tree.resolve(db);
		this.index = new Index(this, tree);
	}

	public Index getIndex() {
		return this.index;
	}

	@Override
	public Index getObject() {
		return this.getIndex();
	}

	@Override
	public String toString() {
		return String.format("IndexRecord [index=%s, %s]", this.index, this.toStringAttr());
	}

	@Override
	public void dump(StringBuilder b) {
		b.append("Index Record\n");
		this.dumpAttr(b);
		this.index.dump(b);
	}

}
