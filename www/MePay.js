var exec = require("cordova/exec");

/**
 * @constructor
 */
var MePay = {
  /**
   * Checks if the device is activated for app.
   * @param success when ends without error, success is called.
   * @param error when the validation ends with error, error is called.
   *
   * @example
   * ```
   *
   * cordova.plugins.MePay.doCheckActivation(onSuccess, onError);
   *
   * function onSuccess(result) {
   *  //if res.result === 1, the device is activated for app. Else need to call doActivation.
   * }
   *
   * function onError(error) {
   *  //error handler
   * }
   *
   * ```
   */
  doCheckActivation: function (success, error) {
    exec(success, error, "MePay", "doCheckActivation", []);
  },

  /**
   * Activate the device for app.
   * @param password password to activate the device.
   * @param success  when ends without error, success is called.
   * @param error when ends with error, error is called.
   */
  doActivation: function (password, success, error) {
    exec(success, error, "MePay", "doActivation", [password]);
  },

  /**
   * Call this method to start payment flow
   * @param amount value to pay in cents (BRL).
   * @param paymentType payment type.
   * @param userReference unique reference for the purchase max 10 caracters.
   * @param installments number of installments.
   * @param id purchase reference
   * @param success when ends without error, success is called.
   * @param error when ends with error, error is called.
   *
   * @example
   * ```
   *
   * var mepay = cordova.plugins.MePay;
   *
   * function onSuccess(res) {
   *  if(res.type === 1){
   *    //end transaction, the result is on res.result
   *    return;
   *  }
   *
   *  if(res.type === 2) {
   *   //event emitted for the operator, ex: `INSERT_CARD, PROCESSING, REMOVE_CARD...`
   *   return;
   *  }
   * }
   *
   * function onError(err) {
   *  //error handler
   * }
   *
   * ...
   *
   *  mepay.doPayment(100, 1, 'a5s6d7f8g9h0', 1, 'some uuid', onSuccess, onError);
   *
   * ...
   *
   * ```
   */
  doPayment: function (
    amount,
    paymentType,
    userReference,
    installments,
    id,
    success,
    error
  ) {
    exec(success, error, "MePay", "doPayment", [
      amount,
      paymentType,
      userReference,
      installments,
      id,
    ]);
  },

  /**
   * Use this method when you need to get the last aprooved transaction.
   * @param success when ends without error, success is called.
   * @param error when ends with error, error is called.
   */
  doRecoveryLastTransaction: function (success, error) {
    exec(success, error, "MePay", "doRecoveryLastTransaction", []);
  },

  /**
   * Use this method to refund a transaction.
   * @param transaction transaction to be canceled.
   * @param success when ends without error, success is called.
   * @param error when ends with error, error is called.
   *
   * @example
   * ```
   *
   * var mepay = cordova.plugins.MePay;
   *
   * function onSuccess(res) {
   *  if(res.type === 1){
   *    //end transaction, the result is on res.result
   *    return;
   *  }
   *
   *  if(res.type === 2) {
   *   //event emitted for the operator, ex: `INSERT_CARD, PROCESSING, REMOVE_CARD...`
   *   return;
   *  }
   * }
   *
   * function onError(err) {
   *  //error handler
   * }
   *
   * ...
   *
   *  mepay.doRefund(transaction, onSuccess, onError);
   *
   * ...
   *
   * ```
   */
  doRefund: function (transaction, success, error) {
    exec(success, error, "MePay", "doRefund", [transaction]);
  },

  /**
   * Use this method do abort payment or refund methods
   * @param success when ends without error, success is called.
   * @param error when ends with error, error is called.
   */
  doAbort: function (success, error) {
    exec(success, error, "MePay", "doAbort", []);
  },

  /**
   * Use this method do print a image, this methods requires the asset to be printed is on
   * `Environment.getExternalStorageDirectory().getAbsolutePath()` from android.
   * It`s recomended to override the last image to avoid storage issues.
   *
   * @param fileName the name of the file to be printed.
   * @param success when ends without error, success is called.
   * @param error when ends with error, error is called.
   */
  doPrint: function (fileName, success, error) {
    exec(success, error, "MePay", "doPrint", [fileName]);
  },
};

module.exports = MePay;
