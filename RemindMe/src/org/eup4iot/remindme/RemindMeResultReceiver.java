package org.eup4iot.remindme;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

/**
 * @author test
 * 
 */
public class RemindMeResultReceiver extends ResultReceiver {

    private Receiver mReceiver;

    /**
     * @param handler
     */
    public RemindMeResultReceiver(Handler handler) {
        super(handler);
        // TODO Auto-generated constructor stub
    }

    public interface Receiver {
        public void onReceiveResult(int resultCode, Bundle resultData);
    }

    /**
     * @param mReceiver the mReceiver to set
     */
    public void setReceiver(Receiver receiver) {
        this.mReceiver = receiver;
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.os.ResultReceiver#onReceiveResult(int, android.os.Bundle)
     */
    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        // super.onReceiveResult(resultCode, resultData);
        if (mReceiver != null) {
            mReceiver.onReceiveResult(resultCode, resultData);
        }
    }

}
