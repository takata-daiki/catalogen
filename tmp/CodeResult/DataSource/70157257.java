/**
 * DataSource
 *
 * @license Dual licensed under the MIT or GPL Version 2 licenses.
 * @author xxxzxxx
 * Copyright 2013, Primitive, inc.
 * The MIT License (http://opensource.org/licenses/mit-license.php)
 * GPL Version 2 licenses (http://www.gnu.org/licenses/gpl-2.0.html)
 */

package com.primitive.library.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

import com.primitive.library.common.log.Logger;

public class DataSource {
	protected final Table[] tables;
	private final OpenHelper openHelper;
	private final Context context;
	final String dataBaseName;
	final int dataBaseVersion;
	final CursorFactory cursorFactory;

	public DataSource(
			final Context context,
			final Table[] tables,
			final String dataBaseName,
			final int dataBaseVersion,
			final CursorFactory cursorFactory
			) {
		long start = Logger.start();
		this.context = context;
		this.tables = tables;
		this.dataBaseName = dataBaseName;
		this.dataBaseVersion = dataBaseVersion;
		this.cursorFactory = cursorFactory;

		openHelper = new OpenHelper(this);
		Logger.end(start);
	}

	public boolean hasTable(Table table){
		for(Table tbl : this.tables){
			if(tbl.equals(table)){
				return true;
			}
		}
		return false;
	}
	public OpenHelper getOpenHelper() {
		return this.openHelper;
	}

	public Context getContext() {
		return this.context;
	}
}
