import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import org.deckfour.xes.extension.std.XConceptExtension;
import org.deckfour.xes.in.XMxmlParser;
import org.deckfour.xes.info.XLogInfo;
import org.deckfour.xes.info.XLogInfoFactory;
import org.deckfour.xes.model.XLog;
import org.deckfour.xes.model.XTrace;
import org.deckfour.xes.storage.softstrings.XSoftStringMap;
import org.deckfour.xes.util.XTimer;

/*
 * OpenXES
 * 
 * The reference implementation of the XES meta-model for event 
 * log data management.
 * 
 * Copyright (c) 2009 Christian W. Guenther (christian@deckfour.org)
 * 
 * 
 * LICENSE:
 * 
 * This code is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA
 * 
 * EXEMPTION:
 * 
 * The use of this software can also be conditionally licensed for
 * other programs, which do not satisfy the specified conditions. This
 * requires an exemption from the general license, which may be
 * granted on a per-case basis.
 * 
 * If you want to license the use of this software with a program
 * incompatible with the LGPL, please contact the author for an
 * exemption at the following email address: 
 * christian@deckfour.org
 * 
 */

/**
 * @author Christian W. Guenther (christian@deckfour.org)
 *
 */
public class Quicktest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Random rnd = new Random();
		
		File logfiles[] = new File[] {
				new File("/Users/christian/Desktop/Logs/asmlUNIFIED.mxml.gz"),
				new File("/Users/christian/Desktop/Logs/Unstructured_example.mxml.gz"),
				new File("/Users/christian/Desktop/Logs/ApplicationsPerClinicalPatients.mxml.gz"),
				new File("/Users/christian/Desktop/Logs/2004_Swan_Ganz_131.xml")
		};
		
		ArrayList<XLog> logs = new ArrayList<XLog>();
		
		XMxmlParser parser = new XMxmlParser();
		
		XTimer timer = new XTimer();
		
		int events = 0;
		
		for(int i=0; i<10; i++) {
			try {
				timer.start();
				//XLog log = parser.parse(logfiles[rnd.nextInt(logfiles.length)]).iterator().next();
				//XLog log = parser.parse(new File("/Users/christian/Desktop/Logs/kenny/FD10_Europe and Africa_NOT_Commands.mxml.gz")).iterator().next();
				XLog log = parser.parse(new File("/Users/christian/Desktop/Logs/asmlUNIFIED.mxml.gz")).iterator().next();
				//XLog log = (new XesBinaryParser()).parse(new File("/Users/christian/Desktop/kennylarge.xbz"));
				System.out.println("read log in " + timer.getDurationString());
				timer.start();
				XLogInfo info = XLogInfoFactory.createLogInfo(log);
				events += info.getNumberOfEvents();
				System.out.println("derived summary in " + timer.getDurationString() + " (" + events + " events)\n");
				System.out.println("directory size: " + XSoftStringMap.instance().size());
				timer.start();
				logs.add(log);
				for(XTrace trace : log) {
					System.out.println("trace: " + XConceptExtension.instance().extractName(trace));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
