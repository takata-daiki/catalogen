package com.ls.upload;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ImageToExcel {

	private HSSFWorkbook wb = null;

	private HSSFSheet sheet = null;

	private HSSFPatriarch patriarch = null;

	public ImageToExcel() {

		wb = new HSSFWorkbook();
		sheet = wb.createSheet("mysheets1");
		sheet.setDefaultColumnWidth((short) 2);
		patriarch = sheet.createDrawingPatriarch();
	}

	// 这个是从网上找到的方法

	private void setImageToCell(short col1, int row1, short col2, int row2, String imagePath) {

		BufferedImage bufferImg = null;
		ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();

		try {
			bufferImg = ImageIO.read(new File(imagePath));
			ImageIO.write(bufferImg, "jpg", byteArrayOut);

			// 能否写多张图片，关键在与HSSFPatriarch patriarch 这个变量。写多张图片时，

			// HSSFPatriarch patriarch 应该时一个对象，不应该每次多新创建对象。
			// HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
			HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 1023, 255, col1, row1, col2, row2);
			anchor.setAnchorType(2);

			patriarch.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void createExcel() {

		FileOutputStream fileOut = null;

		try {

			setImageToCell((short) 1, 1, (short) 8, 6, "D://20140703100023310.jpg");
			setImageToCell((short) 1, 8, (short) 8, 13, "D://20140703100020943.jpg");
			setImageToCell((short) 1, 15, (short) 8, 20, "D://20140703100018827.jpg");

			fileOut = new FileOutputStream("D:\\test.xlsx");
			wb.write(fileOut);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			if (fileOut != null) {
				try {
					fileOut.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {

		ImageToExcel excel = new ImageToExcel();
		excel.createExcel();
	}
}