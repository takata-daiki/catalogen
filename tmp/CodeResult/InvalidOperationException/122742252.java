package com.atlassian.sal.api.i18n;

import com.atlassian.annotations.Internal;
import com.atlassian.annotations.PublicApi;

/**
 * Thrown if the application decides that the attempted operation is not allowed for some reason.
 * <p>
 * This Exception should normally be used for "expected" errors where it is meaningful to display the error message
 * to the end user.
 * <p>
 * Host applications throwing this Exception are expected to localise the message into the locale of the currently
 * logged in user. Consumers catching this Exception are encouraged to use {@link #getLocalizedMessage()} in order
 * to get the translated error message.
 *
 * @since 3.0
 */
@PublicApi
public class InvalidOperationException extends Exception {
    private final String localizedMessage;

    @Internal // Constructor is for host apps, not plugin devs
    public InvalidOperationException(String message, String localizedMessage) {
        super(message);
        this.localizedMessage = localizedMessage;
    }

    /**
     * Returns the error message in the locale of the currently logged in user.
     * <p>
     * If no user is logged in, this should use the default locale for the host application.
     *
     * @return the error message in the locale of the currently logged in user.
     * @see #getMessage()
     */
    @Override
    public String getLocalizedMessage() {
        return localizedMessage;
    }
}
