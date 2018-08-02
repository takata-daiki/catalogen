package rjzjh.tech.common.report.excel.jxls;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import rjzjh.tech.common.apiext.DateUtil;
import rjzjh.tech.common.apiext.StringUtil;
import rjzjh.tech.common.exception.ExceptAll;
import rjzjh.tech.common.exception.ProjectException;

/**
 * @ClassName: ReportAbstract
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 周俊辉
 * @date 2010-10-29 下午05:18:19
 * 
 */
public abstract class ReportAbstract {
	public static Logger logger = Logger.getLogger(ReportAbstract.class);
	private static Random random = new Random();
	private String srcFilePath = "";
	private String srcFileName = "";
	private String destFilePath="";
	private String destFileName = "tempFileName";
	public HttpServletRequest request;
	private Workbook resultWorkBook;//如果不在服务器生成文件，导出的结果会放到这个结果里。

	public ReportAbstract(HttpServletRequest request) {
		this.request = request;
	}

	public String exportExcel(boolean isSaveFile) throws Exception {
		initParam();
		if(StringUtil.isNull(srcFilePath)||StringUtil.isNull(srcFileName)){
			throw new ProjectException(ExceptAll.EXCEL_00004);
		}
		if(request==null) throw new ProjectException(ExceptAll.EXCEL_00005);
		if(StringUtil.isNull(destFilePath)){
			destFilePath = request.getContextPath();
		}
		File tempFile = new File(srcFilePath+ srcFileName);
		if (!tempFile.exists()) throw new ProjectException(ExceptAll.EXCEL_00004);

		File exportDir = new File(destFilePath);
		if (!exportDir.exists()) {
			exportDir.mkdir();
		}
		String exportFileName = Integer.toString(Math.abs(random.nextInt()));
		XLSTransformer transformer = new XLSTransformer();
		Map<String, Object> inputMap = getInputMap();
		String srcFileUrl = srcFilePath+ srcFileName;
		String destFileUrl = exportDir.getPath() + File.separator+ StringUtil.fileNameDelPostfix(destFileName)+ DateUtil.YYYYMMDD.format(new Date())+ exportFileName + ".xls";
		logger.debug("inputMap=" + inputMap + "  srcFileUrl = " + srcFileUrl+ " destFileUrl=" + destFileUrl);
		InputStream is = new BufferedInputStream(new FileInputStream(srcFileUrl));
		Workbook resultWorkBook = transformer.transformXLS(is, inputMap);
		is.close();
		this.resultWorkBook = resultWorkBook;
		if (isSaveFile) {
			OutputStream os = new BufferedOutputStream(new FileOutputStream(destFileUrl));
			resultWorkBook.write(os);
			os.flush();
			os.close();
			return destFileUrl;
		}
		return "";
	}
	public abstract Map<String, Object> getInputMap();
	public abstract void initParam() throws ProjectException;
	
	public String getSrcFilePath() {
		return srcFilePath;
	}

	public void setSrcFilePath(String srcFilePath) {
		this.srcFilePath = srcFilePath;
	}

	public String getSrcFileName() {
		return srcFileName;
	}

	public void setSrcFileName(String srcFileName) {
		this.srcFileName = srcFileName;
	}

	public String getDestFilePath() {
		return destFilePath;
	}

	public void setDestFilePath(String destFilePath) {
		this.destFilePath = destFilePath;
	}

	public String getDestFileName() {
		return destFileName;
	}

	public void setDestFileName(String destFileName) {
		this.destFileName = destFileName;
	}

	public Workbook getResultWorkBook() {
		return resultWorkBook;
	}
}
