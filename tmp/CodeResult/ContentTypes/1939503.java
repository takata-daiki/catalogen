package org.etupirkacms.util;

/**
 * Utility for Content Type
 * @author shuji.w6e
 * @since 0.1.0
 */
public class ContentTypes {

    /**
     * ??????????????????
     * @param path
     * @return
     */
    public static String getContentType(String path) {
        if (path == null) throw new IllegalArgumentException("path == null");
        int idx = path.lastIndexOf('.');
        if (idx == -1) return "text/plain";
        String suffix = path.substring(idx).toLowerCase();
        if (suffix.equals(".css")) return "text/css";
        if (suffix.equals(".jpg") || suffix.equals(".jpeg")) return "image/jpeg";
        if (suffix.equals(".gif")) return "image/gif";
        if (suffix.equals(".png")) return "image/png";
        if (suffix.equals(".js")) return "application/x-javascript";
        if (suffix.equals(".html")) return "text/html";
        if (suffix.equals(".xml")) return "text/xml";
        if (suffix.equals(".txt")) return "text/plain";
        return "text/plain";
    }

}
