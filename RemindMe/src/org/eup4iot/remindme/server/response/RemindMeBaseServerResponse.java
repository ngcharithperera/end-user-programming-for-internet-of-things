package org.eup4iot.remindme.server.response;

public class RemindMeBaseServerResponse {

    private RemindMeResponseError error;

    /**
     * @return the error
     */
    public RemindMeResponseError getError() {
        return error;
    }

    /**
     * @param error the error to set
     */
    public void setError(RemindMeResponseError error) {
        this.error = error;
    }
}
