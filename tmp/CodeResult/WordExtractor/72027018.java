package org.jackysoft.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 有关各种文档的操作工具 目前有word,excel,pdf,txt.... 更多的文档添加中....
 */
public class TextUtils {
	private static final Log logger = LogFactory.getLog(TextUtils.class);

	/**
	 * @return word文档的摘要
	 * @读取word文档，截取部分摘要内容
	 * @param wordFile
	 *            word文件名
	 * @param wordCount
	 *            要截取的字数
	 */
	/*public static String getWordDocumentSegment(String wordFile, int wordCount) {
		String segment = "";
		try {
			File worf = new File(wordFile);
			FileInputStream in = new FileInputStream(worf);
			WordExtractor extractor = new WordExtractor();
			String str = extractor.extractText(in);
			// segment = str.substring(0, wordCount);
			segment = str.length() >= wordCount ? str.substring(0, wordCount)
					: str;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return segment;
	}

	*//**
	 * @return 读取word文档，截取部分摘要内容
	 * @param content
	 *            word内容
	 * @param wordCount
	 *            要截取的字数
	 *//*
	public static String getWordDocumentSegment(byte[] content, int wordCount) {
		String segment = "";
		InputStream in = new ByteArrayInputStream(content);
		WordExtractor extractor = new WordExtractor();
		String str = "";
		try {
			str = extractor.extractText(in);
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		segment = str.length() >= wordCount ? str.substring(0, wordCount) : str;
		return segment;
	}

	*//**
	 * @
	 * 
	 *//*
	public static byte[] mergeExcels(List<InputStream> ilist) {
		ByteArrayOutputStream sout = new ByteArrayOutputStream();
		Set<String> names = new HashSet<String>();
		ArrayList<Sheet> talSheets = new ArrayList<Sheet>(ilist.size());
		try {
			for (InputStream ins : ilist) {
				Workbook bookonce = Workbook.getWorkbook(ins);
				for (Sheet t : bookonce.getSheets()) {
					if (t.getRows() == 0 || t.getColumns() == 0)
						continue;
					talSheets.add(t);
					names.add(t.getName());
				}

			}

			// 上面代码主要填充talSheets的sheet数组
			// log.info(talSheets.size());

			WritableWorkbook rww = Workbook.createWorkbook(sout);
			// 遍历所有工作表
			for (int sindex = 0; sindex < talSheets.size(); sindex++) {
				Sheet ss = talSheets.get(sindex);
				// names.remove(ss.getName());

				int rs = ss.getRows();
				// 对应每一个源工作表在目的工作薄中创建一个工作表
				// log.info("当前工作表行数 "+rs);
				WritableSheet ds = null;
				if (names.contains(ss.getName()))
					ds = rww.createSheet(
							ss.getName() + ClassUtils.generateRString(3),
							sindex);
				else {
					ds = rww.createSheet(ss.getName(), sindex);
				}
				// log.info(ss.getName());
				for (int r = 0; r < rs; r++) {
					// 依次创建单元格
					ss.getRowView(r).setAutosize(true);
					Cell[] ces = ss.getRow(r);

					// log.info(ces.length);
					for (int c = 0; c < ces.length; c++) {
						Cell ce = ces[c];
						Label cl = new Label(c, r, ce.getContents());

						ds.addCell(cl);
					}
				}
			}
			rww.write();
			rww.close();

		} catch (BiffException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		} catch (RowsExceededException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}

		return sout.toByteArray();
	}*/

	

}
