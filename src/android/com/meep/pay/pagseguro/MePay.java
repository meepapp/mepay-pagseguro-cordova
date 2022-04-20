package com.meep.pay.pagseguro;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.meep.pay.pagsseguro.TestLibrary;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

import com.google.gson.Gson;

/**
 * This class echoes a string called from JavaScript.
 */
public class MePay extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

        if (action.equals("sum")) {
            int a = args.getInt(0);
            int b = args.getInt(1);
            this.sum(a, b, callbackContext);
            return true;
        }

        if (action.equals("observable")) {
            this.observable(callbackContext);
            return true;
        }

        return false;
    }

    public void observable(CallbackContext callbackContext) {
        TestLibrary test = new TestLibrary();

        test.testEvent()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(res -> {
                    Gson gson = new Gson();
                    String json = gson.toJson(res);
                    PluginResult result = new PluginResult(PluginResult.Status.OK, json);
                    boolean keepCallback = res.result == null;
                    result.setKeepCallback(keepCallback);

                    callbackContext.sendPluginResult(result);
                });

    }

    public boolean sum(int a, int b, CallbackContext callbackContext) {
        int c = TestLibrary.sum(a, b);
        callbackContext.success(c);
        return true;
    }
}
