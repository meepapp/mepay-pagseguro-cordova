package com.meep.pay.pagseguro;

import androidx.annotation.Nullable;

import com.meep.pay.pagsseguro.Manager;
import com.meep.pay.pagsseguro.interfaces.IManager;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * This class echoes a string called from JavaScript.
 */
public class MePay extends CordovaPlugin {

    private static final String ACTION_CHECK_ACTIVATION = "doCheckActivation";
    private static final String ACTION_ACTIVATION = "doActivation";
    private static final String ACTION_PAYMENT = "doPayment";
    private static final String ACTION_RECOVERY_TRANSACTION = "doRecoveryLastTransaction";
    private static final String ACTION_REFUND = "doRefund";
    private static final String ACTION_ABORT = "doAbort";
    private static final String ACTION_PRINT = "doPrint";

    private IManager manager;
    private Disposable subscriber;
    private CallbackContext callback;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

        if (manager == null)
            manager = new Manager(this.cordova.getActivity().getApplicationContext());

        this.callback = callbackContext;

        if (action.equals(ACTION_CHECK_ACTIVATION)) {
            doCheckActivation();
            return true;
        }

        if (action.equals(ACTION_ACTIVATION)) {
            String password = args.getString(0);
            doActivation(password);
            return true;
        }

        if (action.equals(ACTION_PAYMENT)) {
            int amount = args.getInt(0);
            int paymentType = args.getInt(1);
            String userReference = args.getString(2);
            int installments = args.getInt(3);
            String id = args.getString(4);

            doPayment(amount, paymentType, userReference, installments, id);
            return true;
        }

        if (action.equals(ACTION_RECOVERY_TRANSACTION)) {
            doRecoveryLastTransaction();
            return true;
        }

        if (action.equals(ACTION_REFUND)) {
            String transaction = args.getString(0);
            doRefund(transaction);
            return true;
        }

        if (action.equals(ACTION_ABORT)) {
            doAbort();
            return true;
        }

        if (action.equals(ACTION_PRINT)) {
            String fileName = args.getString(0);
            doPrint(fileName, 4, 30);
            return true;
        }

        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (subscriber != null && !subscriber.isDisposed())
            subscriber.dispose();
    }

    private void sendPluginResult(ResultType type, int res, boolean keepCallback) throws JSONException {
        JSONObject object = new JSONObject();
        object.put("type", type.getCode());
        object.put("result", res);

        PluginResult result = new PluginResult(PluginResult.Status.OK, object);
        result.setKeepCallback(keepCallback);

        if (!callback.isFinished())
            callback.sendPluginResult(result);
    }

    private void sendPluginResult(ResultType type, String res, boolean keepCallback) throws JSONException {
        JSONObject object = new JSONObject();
        object.put("type", type.getCode());
        object.put("result", res);

        PluginResult result = new PluginResult(PluginResult.Status.OK, object);
        result.setKeepCallback(keepCallback);

        if (!callback.isFinished())
            callback.sendPluginResult(result);
    }

    private void doPrint(String fileName, @Nullable Integer quality, @Nullable Integer step) {
        subscriber = manager.getPrinterInstance().print(fileName, quality, step)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(res -> sendPluginResult(ResultType.RESULT, res.getResult(), false),
                        err -> callback.error(err.getMessage()));
    }

    private void doCheckActivation() {
        subscriber = manager.getPayInstance().isAuthenticated()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(res -> sendPluginResult(ResultType.RESULT, res ? 1 : 0, false),
                        err -> callback.error(err.getMessage()));
    }

    private void doActivation(String password) {
        // TODO: validate param
        subscriber = manager.getPayInstance().doActivation(password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(res -> sendPluginResult(ResultType.RESULT, 1, false),
                        err -> callback.error(err.getMessage()));
    }

    private void doPayment(int amount, int paymentType, String userReference, int installments, String id) {
        // TODO: validate params
        subscriber = manager.getPayInstance().doPayment(amount, paymentType, userReference, installments, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(res -> {
                    if (res.getTransaction() != null)
                        sendPluginResult(ResultType.RESULT, res.getTransaction(), false);
                    else if (res.getEventCode() != null)
                        sendPluginResult(ResultType.EVENT, res.getEventCode().name(), true);
                }, error -> callback.error(error.getMessage()));
    }

    private void doRecoveryLastTransaction() {
        subscriber = manager.getPayInstance().getLastApprovedTransaction()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(res -> sendPluginResult(ResultType.RESULT, res.getTransaction(), false),
                        error -> callback.error(error.getMessage()));
    }

    private void doRefund(String transaction) {
        subscriber = manager.getPayInstance().doRefund(transaction)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(res -> {
                    if (res.getTransaction() != null)
                        sendPluginResult(ResultType.RESULT, res.getTransaction(), false);
                    else if (res.getEventCode() != null)
                        sendPluginResult(ResultType.EVENT, res.getEventCode().name(), true);
                }, error -> callback.error(error.getMessage()));
    }

    private void doAbort() {
        subscriber = manager.getPayInstance().doAbort()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(res -> sendPluginResult(ResultType.RESULT, res.getResult(), false),
                        error -> callback.error(error.getMessage()));
    }
}
