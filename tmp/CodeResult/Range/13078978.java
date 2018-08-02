package com.FSS.File;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ListIterator;
import java.util.Observable;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.Vector;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import com.FSS.FileList.DefaultList;
import com.FSS.FileList.FileList;

import com.FSS.config.Config;
import com.FSS.util.FileListUtil;
import com.FSS.util.FilesChangeMonitor;

public class FilesGatherer extends Observable {
	//
	private FileList allIFiles;
	private FileList formalFiles;
	private FileList inFormalFiles;

	//
	public FilesGatherer() {
		super();
		File file = new File(Config.DATA_LOCATION);
		if (file.exists())
			allIFiles = FileListUtil.loadFromDisk(file.getAbsolutePath());

		else
			allIFiles = new DefaultList();
		file = new File(Config.getValue("FORMAL"));
		if (file.exists()) {
			formalFiles = FileListUtil.loadFromDisk(file.getAbsolutePath());
		} else
			formalFiles = new DefaultList();
		file = new File(Config.getValue("INFORMAL"));
		if (file.exists()) {
			inFormalFiles= FileListUtil.loadFromDisk(file.getAbsolutePath());
		} else
			inFormalFiles = new DefaultList();
	}

	public FileList getFormalFiles() {
		return formalFiles;
	}

	public FileList getInFormalFiles() {
		return inFormalFiles;
	}

	public void gatherFiles(Vector<String> fileNames) {
		for (String name : fileNames) {
			IFile ifile = new DefaultFile();
			ifile.setName(name);
			this.setIFile(ifile);
			allIFiles.add(ifile);
			if (ifile.getType() == 0)
				formalFiles.add(ifile);
			else
				inFormalFiles.add(ifile);
		}
		setChanged();
		super.notifyObservers();
	}

	public void removeIFiles(Vector<String> fileNames) {
		for (String name : fileNames) {
			ListIterator<IFile> itr = allIFiles.listIterator();
			while (itr.hasNext()) {
				IFile ifile = itr.next();
				if (ifile.getName().equals(name)) {
					itr.remove();
					break;
				}
			}
			itr = formalFiles.listIterator();
			while (itr.hasNext()) {
				IFile ifile = itr.next();
				if (ifile.getName().equals(name)) {
					itr.remove();
					break;
				}
			}
			itr = inFormalFiles.listIterator();
			while (itr.hasNext()) {
				IFile ifile = itr.next();
				if (ifile.getName().equals(name)) {
					itr.remove();
					break;
				}
			}
		}
		setChanged();
		super.notifyObservers();
	}

	private void setIFile(IFile f) {
		f.setForm(".doc");
		File file = new File(Config.DOC_LOCATION + "\\" + f.getName());
		long time = file.lastModified();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String tsForm = formatter.format(new Date(time));
		int year = Integer.valueOf(tsForm.substring(0, 4));
		f.setYear(year);
		int month = Integer.valueOf(tsForm.substring(5, 7));
		f.setMonth(month);
		HWPFDocument doc = null;
		try {
			doc = new HWPFDocument(new FileInputStream(Config.DOC_LOCATION
					+ "\\" + f.getName()));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(f.getName());
		}

		Range r = doc.getRange(); // ??word?????
		int lenParagraph = r.numParagraphs();// ?????
		// Paragraph p;
		String firstP = null;
		StringBuffer secondP = new StringBuffer();// ????????;
		int count = 0;
		for (int x = 0; x < lenParagraph; x++) {
			Paragraph p = r.getParagraph(x);
			String text = p.text();

			if (text.trim().length() == 0) {
				if (count >= 2)
					break;
				continue;
			}

			else {
				count++;
				if (count == 1)
					firstP = text;
				else {
					secondP.append(text.substring(0, text.length() - 1));
					if (text.trim().matches(".*[??][\\s]*"))
						break;
				}

			}
		}

		try {
			f.setDepartment(firstP.substring(firstP.indexOf('?'), firstP
					.indexOf('?')));
			int num = Integer.valueOf(firstP.substring(firstP.indexOf('?') + 1,
					firstP.indexOf('?')));
			f.setNum(num);
			// ??????????
			f.setTitle(secondP.toString().replace("[ |?]*", "")
					.replace(" ", ""));
		} catch (Exception e) {
			// ?????
			f.setType(1);
			f.setTitle(f.getName());
			f.setDepartment("???");
		}
		// ?????
		String line = null;
		for (int x = 0; x < lenParagraph; x++) {
			Paragraph p = r.getParagraph(x);
			String text = p.text();
			if (text.matches(".*???.*\\s")) {
				line = text;
				break;
			}
		}
		if (line != null) {
			StringTokenizer st = new StringTokenizer(line, " ?");
			String temp = null;
			st.nextToken();
			while (st.hasMoreTokens() && (temp = st.nextToken()) != null) {
				f.addKeyWord(temp.trim());
			}
		}
	}

	public FileList getAllFiles() {
		return allIFiles;
	}

	public static void main(String args[]) {
		FilesGatherer fg = new FilesGatherer();
		Timer t = new Timer();
		t.schedule(new FilesChangeMonitor(fg), 1000, 5000);
	}
}
