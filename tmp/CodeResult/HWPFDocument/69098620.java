package com.loveplusplus.demo.print.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Map;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import android.content.Context;
import android.util.Log;

public class WordUtil {

	private static final String TAG = "WordUtil";

	public static void word(Context context, File distFile,String templateName,String pref,Map<String,String> map) {
		try {
			// 模板文件
			InputStream tempInputStream = context.getAssets().open(templateName);
			POIFSFileSystem fs = new POIFSFileSystem(tempInputStream);
			HWPFDocument doc = new HWPFDocument(fs);
			Range range = doc.getRange();
			for (int i = 0; i < range.numCharacterRuns(); i++) {
				CharacterRun run = range.getCharacterRun(i);
				String text = run.text();
				Log.d(TAG, text);
				// 替换自定义标记
				if (text.startsWith(pref)&&map.containsKey(text)) {
					run.replaceText(map.get(text), true);
				} 
			}
			doc.write(new FileOutputStream(distFile));
		} catch (Exception e) {
			Log.d(TAG, "", e);
		}
	}


}
