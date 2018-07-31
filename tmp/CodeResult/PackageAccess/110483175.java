/* $Id$
 * 
 * Copyright 2008 Randy McEoin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openintents.safe;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import android.content.Context;
import android.util.Log;
import android.util.Xml;

public class Backup {

	private static boolean debug = false;
	private static final String TAG = "Backup";
	
	public static int CURRENT_VERSION = 1;
	
	private String result="";
	
	Context myCtx=null;

	public Backup(Context ctx) {
		myCtx=ctx;
	}

    public boolean write(String filename) {
    	if (debug) Log.d(TAG,"write("+filename+",)");
    	
		try {
            FileOutputStream str = new FileOutputStream(filename);
            org.xmlpull.v1.XmlSerializer serializer = Xml.newSerializer();
            serializer.setOutput(str, "utf-8");
            serializer.startDocument(null, Boolean.valueOf(true));
            serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
            serializer.startTag(null, "OISafe");
            
            serializer.attribute(null, "version", Integer.toString(CURRENT_VERSION));
            
            Date today;
            String dateOut;
            DateFormat dateFormatter;

            dateFormatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT,
            					   DateFormat.FULL);
            today = new Date();
            dateOut = dateFormatter.format(today);

            serializer.attribute(null, "date", dateOut);

			DBHelper dbh = new DBHelper(myCtx);

			serializer.startTag(null, "MasterKey");
			serializer.text(dbh.fetchMasterKey());	// Encrypted
			serializer.endTag(null, "MasterKey");

			serializer.startTag(null, "Salt");
			serializer.text(dbh.fetchSalt());
			serializer.endTag(null, "Salt");

			String cvcs = dbh.fetchCryptVCSeed();
			if(cvcs != null && !"".equals(cvcs)) {
				String cvcc = dbh.fetchVCChal();
				assert(cvcc != null && !"".equals(cvcc));

				serializer.startTag(null, "VCSeeds");
				serializer.text(cvcs);
				serializer.endTag(null, "VCSeeds");

				serializer.startTag(null, "VCChal");
				serializer.text(cvcc);
				serializer.endTag(null, "VCChal");

				IntentHandler.rotateVC();
			}

			dbh.close();

			List<CategoryEntry> crows;
			crows = Passwords.getCategoryEntries();
			
			int totalPasswords=0;

			for (CategoryEntry crow : crows) {

				serializer.startTag(null, "Category");
				serializer.attribute(null, "name", crow.name);

				List<PassEntry> rows;
				rows = Passwords.getPassEntries(crow.id, false, false);
	
				for (PassEntry row : rows) {
					totalPasswords++;
					
					serializer.startTag(null, "Entry");

					serializer.startTag(null, "RowID");
					serializer.text(Long.toString(row.id));
					serializer.endTag(null, "RowID");

					serializer.startTag(null, "Description");
					serializer.text(row.description);
					serializer.endTag(null, "Description");
					
					serializer.startTag(null, "Website");
					serializer.text(row.website);
					serializer.endTag(null, "Website");
					
					serializer.startTag(null, "Username");
					serializer.text(row.username);
					serializer.endTag(null, "Username");
					
					serializer.startTag(null, "Password");
					serializer.text(row.password);
					serializer.endTag(null, "Password");

					serializer.startTag(null, "Note");
					serializer.text(row.note);
					serializer.endTag(null, "Note");

					if (row.uniqueName!=null) {
						serializer.startTag(null, "UniqueName");
						serializer.text(row.uniqueName);
						serializer.endTag(null, "UniqueName");
					}
					
					ArrayList<PackageAccessEntry> packageAccess=Passwords.getPackageAccessEntries(row.id);
					if(packageAccess!=null) {
						serializer.startTag(null, "PackageAccess");
						String entry="";
						Iterator<PackageAccessEntry> packIter=packageAccess.iterator();
						while (packIter.hasNext()) {
							if (entry.length()!=0) {
								entry += ",";
							}
							entry += packIter.next().packageAccess;
						}
						entry = "[" + entry + "]";
						serializer.text(entry);
						serializer.endTag(null, "PackageAccess");
					}

					serializer.endTag(null, "Entry");
				}
				serializer.endTag(null, "Category");
			}

			serializer.endTag(null, "OISafe");
			serializer.endDocument();

			result=myCtx.getString(R.string.backup_complete)+" "+
				Integer.toString(totalPasswords);
		} catch (IOException e) {
			e.printStackTrace();
			result=myCtx.getString(R.string.backup_failed)+" "+
				e.getLocalizedMessage();
			return false;
		}
		return true;
    }
    public String getResult() {
    	return result;
    }
}
