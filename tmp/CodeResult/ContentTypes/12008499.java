
package apibasej.http;

import java.io.File;
import java.util.Hashtable;

/**
 * http://code.google.com/p/apibasej/
 * 
 * @author Julio Azevedo Lessa - apibasej@gmail.com
 * 
 */
@SuppressWarnings({"unchecked","rawtypes"})
public class TypeMap {

	private static final String DEFAULT_CONTENT_TYPE = "application/octet-stream";
	private Hashtable contentTypes = new Hashtable();
	private static TypeMap mapper = null;

	// CRIAR ENUM  !!!!
	
	/**
	 * Creates a new ContentTypeMapper.
	 */
	private TypeMap() {
		contentTypes.put("323", "text/h323");
		contentTypes.put("acx", "application/internet-property-stream");
		contentTypes.put("ai", "application/postscript");
		contentTypes.put("aif", "audio/x-aiff");
		contentTypes.put("aifc", "audio/x-aiff");
		contentTypes.put("aiff", "audio/x-aiff");
		contentTypes.put("asf", "video/x-ms-asf");
		contentTypes.put("asr", "video/x-ms-asf");
		contentTypes.put("asx", "video/x-ms-asf");
		contentTypes.put("au", "audio/basic");
		contentTypes.put("avi", "video/x-msvideo");
		contentTypes.put("axs", "application/olescript");
		contentTypes.put("bas", "text/plain");
		contentTypes.put("bcpio", "application/x-bcpio");
		contentTypes.put("bin", "application/octet-stream");
		contentTypes.put("bmp", "image/bmp");
		contentTypes.put("c", "text/plain");
		contentTypes.put("cat", "application/vnd.ms-pkiseccat");
		contentTypes.put("cdf", "application/x-cdf");
		contentTypes.put("cer", "application/x-x509-ca-cert");
		contentTypes.put("class", "application/octet-stream");
		contentTypes.put("clp", "application/x-msclip");
		contentTypes.put("cmx", "image/x-cmx");
		contentTypes.put("cod", "image/cis-cod");
		contentTypes.put("cpio", "application/x-cpio");
		contentTypes.put("crd", "application/x-mscardfile");
		contentTypes.put("crl", "application/pkix-crl");
		contentTypes.put("crt", "application/x-x509-ca-cert");
		contentTypes.put("csh", "application/x-csh");
		contentTypes.put("css", "text/css");
		contentTypes.put("dcr", "application/x-director");
		contentTypes.put("der", "application/x-x509-ca-cert");
		contentTypes.put("dir", "application/x-director");
		contentTypes.put("dll", "application/x-msdownload");
		contentTypes.put("dms", "application/octet-stream");
		contentTypes.put("doc", "application/msword");
		contentTypes.put("dot", "application/msword");
		contentTypes.put("dvi", "application/x-dvi");
		contentTypes.put("dxr", "application/x-director");
		contentTypes.put("eps", "application/postscript");
		contentTypes.put("etx", "text/x-setext");
		contentTypes.put("evy", "application/envoy");
		contentTypes.put("exe", "application/octet-stream");
		contentTypes.put("fif", "application/fractals");
		contentTypes.put("flr", "x-world/x-vrml");
		contentTypes.put("gif", "image/gif");
		contentTypes.put("gtar", "application/x-gtar");
		contentTypes.put("gz", "application/x-gzip");
		contentTypes.put("h", "text/plain");
		contentTypes.put("hdf", "application/x-hdf");
		contentTypes.put("hlp", "application/winhlp");
		contentTypes.put("hqx", "application/mac-binhex40");
		contentTypes.put("hta", "application/hta");
		contentTypes.put("htc", "text/x-component");
		contentTypes.put("htm", "text/html");
		contentTypes.put("html", "text/html");
		contentTypes.put("htt", "text/webviewhtml");
		contentTypes.put("ico", "image/x-icon");
		contentTypes.put("ief", "image/ief");
		contentTypes.put("iii", "application/x-iphone");
		contentTypes.put("ins", "application/x-internet-signup");
		contentTypes.put("isp", "application/x-internet-signup");
		contentTypes.put("jfif", "image/pipeg");
		contentTypes.put("jpe", "image/jpeg");
		contentTypes.put("jpeg", "image/jpeg");
		contentTypes.put("jpg", "image/jpeg");
		contentTypes.put("js", "application/x-javascript");
		contentTypes.put("latex", "application/x-latex");
		contentTypes.put("lha", "application/octet-stream");
		contentTypes.put("lsf", "video/x-la-asf");
		contentTypes.put("lsx", "video/x-la-asf");
		contentTypes.put("lzh", "application/octet-stream");
		contentTypes.put("m13", "application/x-msmediaview");
		contentTypes.put("m14", "application/x-msmediaview");
		contentTypes.put("m3u", "audio/x-mpegurl");
		contentTypes.put("man", "application/x-troff-man");
		contentTypes.put("mdb", "application/x-msaccess");
		contentTypes.put("me", "application/x-troff-me");
		contentTypes.put("mht", "message/rfc822");
		contentTypes.put("mhtml", "message/rfc822");
		contentTypes.put("mid", "audio/mid");
		contentTypes.put("mny", "application/x-msmoney");
		contentTypes.put("mov", "video/quicktime");
		contentTypes.put("movie", "video/x-sgi-movie");
		contentTypes.put("mp2", "video/mpeg");
		contentTypes.put("mp3", "audio/mpeg");
		contentTypes.put("mpa", "video/mpeg");
		contentTypes.put("mpe", "video/mpeg");
		contentTypes.put("mpeg", "video/mpeg");
		contentTypes.put("mpg", "video/mpeg");
		contentTypes.put("mpp", "application/vnd.ms-project");
		contentTypes.put("mpv2", "video/mpeg");
		contentTypes.put("ms", "application/x-troff-ms");
		contentTypes.put("mvb", "application/x-msmediaview");
		contentTypes.put("nws", "message/rfc822");
		contentTypes.put("oda", "application/oda");
		contentTypes.put("p10", "application/pkcs10");
		contentTypes.put("p12", "application/x-pkcs12");
		contentTypes.put("p7b", "application/x-pkcs7-certificates");
		contentTypes.put("p7c", "application/x-pkcs7-mime");
		contentTypes.put("p7m", "application/x-pkcs7-mime");
		contentTypes.put("p7r", "application/x-pkcs7-certreqresp");
		contentTypes.put("p7s", "application/x-pkcs7-signature");
		contentTypes.put("pbm", "image/x-portable-bitmap");
		contentTypes.put("pdf", "application/pdf");
		contentTypes.put("pfx", "application/x-pkcs12");
		contentTypes.put("pgm", "image/x-portable-graymap");
		contentTypes.put("pko", "application/ynd.ms-pkipko");
		contentTypes.put("pma", "application/x-perfmon");
		contentTypes.put("pmc", "application/x-perfmon");
		contentTypes.put("pml", "application/x-perfmon");
		contentTypes.put("pmr", "application/x-perfmon");
		contentTypes.put("pmw", "application/x-perfmon");
		contentTypes.put("pnm", "image/x-portable-anymap");
		contentTypes.put("pot", "application/vnd.ms-powerpoint");
		contentTypes.put("ppm", "image/x-portable-pixmap");
		contentTypes.put("pps", "application/vnd.ms-powerpoint");
		contentTypes.put("ppt", "application/vnd.ms-powerpoint");
		contentTypes.put("prf", "application/pics-rules");
		contentTypes.put("ps", "application/postscript");
		contentTypes.put("pub", "application/x-mspublisher");
		contentTypes.put("qt", "video/quicktime");
		contentTypes.put("ra", "audio/x-pn-realaudio");
		contentTypes.put("ram", "audio/x-pn-realaudio");
		contentTypes.put("ras", "image/x-cmu-raster");
		contentTypes.put("rgb", "image/x-rgb");
		contentTypes.put("rmi", "audio/mid");
		contentTypes.put("roff", "application/x-troff");
		contentTypes.put("rtf", "application/rtf");
		contentTypes.put("rtx", "text/richtext");
		contentTypes.put("scd", "application/x-msschedule");
		contentTypes.put("sct", "text/scriptlet");
		contentTypes.put("setpay", "application/set-payment-initiation");
		contentTypes.put("setreg", "application/set-registration-initiation");
		contentTypes.put("sh", "application/x-sh");
		contentTypes.put("shar", "application/x-shar");
		contentTypes.put("sit", "application/x-stuffit");
		contentTypes.put("snd", "audio/basic");
		contentTypes.put("spc", "application/x-pkcs7-certificates");
		contentTypes.put("spl", "application/futuresplash");
		contentTypes.put("src", "application/x-wais-source");
		contentTypes.put("sst", "application/vnd.ms-pkicertstore");
		contentTypes.put("stl", "application/vnd.ms-pkistl");
		contentTypes.put("stm", "text/html");
		contentTypes.put("sv4cpio", "application/x-sv4cpio");
		contentTypes.put("sv4crc", "application/x-sv4crc");
		contentTypes.put("t", "application/x-troff");
		contentTypes.put("tar", "application/x-tar");
		contentTypes.put("tcl", "application/x-tcl");
		contentTypes.put("tex", "application/x-tex");
		contentTypes.put("texi", "application/x-texinfo");
		contentTypes.put("texinfo", "application/x-texinfo");
		contentTypes.put("tgz", "application/x-compressed");
		contentTypes.put("tif", "image/tiff");
		contentTypes.put("tiff", "image/tiff");
		contentTypes.put("tr", "application/x-troff");
		contentTypes.put("trm", "application/x-msterminal");
		contentTypes.put("tsv", "text/tab-separated-values");
		contentTypes.put("txt", "text/plain");
		contentTypes.put("uls", "text/iuls");
		contentTypes.put("ustar", "application/x-ustar");
		contentTypes.put("vcf", "text/x-vcard");
		contentTypes.put("vrml", "x-world/x-vrml");
		contentTypes.put("wav", "audio/x-wav");
		contentTypes.put("wcm", "application/vnd.ms-works");
		contentTypes.put("wdb", "application/vnd.ms-works");
		contentTypes.put("wks", "application/vnd.ms-works");
		contentTypes.put("wmf", "application/x-msmetafile");
		contentTypes.put("wps", "application/vnd.ms-works");
		contentTypes.put("wri", "application/x-mswrite");
		contentTypes.put("wrl", "x-world/x-vrml");
		contentTypes.put("wrz", "x-world/x-vrml");
		contentTypes.put("xaf", "x-world/x-vrml");
		contentTypes.put("xbm", "image/x-xbitmap");
		contentTypes.put("xla", "application/vnd.ms-excel");
		contentTypes.put("xlc", "application/vnd.ms-excel");
		contentTypes.put("xlm", "application/vnd.ms-excel");
		contentTypes.put("xls", "application/vnd.ms-excel");
		contentTypes.put("xlt", "application/vnd.ms-excel");
		contentTypes.put("xlw", "application/vnd.ms-excel");
		contentTypes.put("xml", "text/xml");
		contentTypes.put("xof", "x-world/x-vrml");
		contentTypes.put("xpm", "image/x-xpixmap");
		contentTypes.put("xwd", "image/x-xwindowdump");
		contentTypes.put("z", "application/x-compress");
		contentTypes.put("zip", "application/zip");
	}


	public String getContentType(File file) {

		String fileName = file.getName();

		// determine extension
		int index = fileName.lastIndexOf(".");

		if (index == -1) {
			return DEFAULT_CONTENT_TYPE;
		} else {

			if (index == fileName.length()) {
				return DEFAULT_CONTENT_TYPE;
			} else {

				Object contentType = contentTypes.get(fileName.substring(index + 1).toLowerCase());

				if (contentType != null) {
					return contentType.toString();
				} else {
					return DEFAULT_CONTENT_TYPE;
				}
			}
		}
	}


	public static TypeMap getInstance() {

		if (mapper == null) {
			synchronized (TypeMap.class) {
				if (mapper == null) {
					mapper = new TypeMap();
				}
			}
		}

		return mapper;
	}
}
