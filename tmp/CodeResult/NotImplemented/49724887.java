package com.stoneriver.toast.exceptions.http;

/**
 * Raise if the application does not support the action requested by the browser.
 */
public class NotImplemented extends HttpException {
    private static final long serialVersionUID = 1L;

    private static String defaultMessage = "The server does not support the action requested by the browser.";
    private static int code = 501;

    public NotImplemented() {
        super(code, defaultMessage);
    }

    public NotImplemented(String message) {
        super(code, (message == null ? defaultMessage : message));
    }
}
