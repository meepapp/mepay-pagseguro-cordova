package com.meep.pay.pagseguro;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.meep.pay.pagsseguro.TestLibrary;

/**
 * This class echoes a string called from JavaScript.
 */
public class MePay extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("coolMethod")) {
            String message = args.getString(0);
            this.coolMethod(message, callbackContext);
            return true;
        }

        if (action.equals("sum")) {
            int a = args.getInt(0);
            int b = args.getInt(1);
            this.sum(a, b, callbackContext);
            return true;
        }

        return false;
    }

    public boolean sum(int a, int b, CallbackContext callbackContext) {
        int c = TestLibrary.sum(a, b);
        callbackContext.success(c);
        return true;
    }

    private void coolMethod(String message, CallbackContext callbackContext) {
        if (message != null && message.length() > 0) {
            callbackContext.success(message);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }
}
