package com.nls.myrewards;

public class MyRewardsServerException extends MyRewardsException {
    private final int statusCode;
    private final String statusMessage;
    private final MyRewardsError error;

    public MyRewardsServerException(int statusCode, String statusMessage, MyRewardsError error) {
        super(buildMessage(statusCode, statusMessage, error));
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.error = error;
    }

    /**
     * Get the HTTP status code
     *
     * @return the status code
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * Get the HTTP status message
     *
     * @return the status message
     */
    public String getStatusMessage() {
        return statusMessage;
    }

    /**
     * Get the error returned by the server
     *
     * @return the list of errors
     */
    public MyRewardsError getError() {
        return error;
    }

    private static String buildMessage(int statusCode, String statusMessage, MyRewardsError error) {
        StringBuilder sb = new StringBuilder();
        sb.append(statusCode).append(": ").append(statusMessage);

        if (error != null) {
            sb.append(" - ").append(error.getMessage());
        }

        return sb.toString();
    }
}
