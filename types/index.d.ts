interface Plugins {
  MePay: IMePay;
}

export interface IMePay {
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
  doCheckActivation(
    success: SuccessCallbackType,
    error: ErrorCallbackType
  ): void;

  /**
   * Activate the device for app.
   * @param password password to activate the device.
   * @param success  when ends without error, success is called.
   * @param error when ends with error, error is called.
   */
  doActivation(
    password: string,
    success: SuccessCallbackType,
    error: ErrorCallbackType
  ): void;

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
  doPayment(
    amount: number,
    paymentType: number,
    userReference: string,
    installments: number,
    id: string,
    success: SuccessCallbackType,
    error: ErrorCallbackType
  ): void;

  /**
   * Use this method when you need to get the last aprooved transaction.
   * @param success when ends without error, success is called.
   * @param error when ends with error, error is called.
   */
  doRecoveryLastTransaction(
    success: SuccessCallbackType,
    error: ErrorCallbackType
  ): void;

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
  doRefund(
    transaction: string,
    success: SuccessCallbackType,
    error: ErrorCallbackType
  ): void;

  /**
   * Use this method do abort payment or refund methods
   * @param success when ends without error, success is called.
   * @param error when ends with error, error is called.
   */
  doAbort(success: SuccessCallbackType, error: ErrorCallbackType): void;

  /**
   * Use this method do print a image, this methods requires the asset to be printed is on
   * `Environment.getExternalStorageDirectory().getAbsolutePath()` from android.
   * It`s recomended to override the last image to avoid storage issues.
   *
   * @param fileName the name of the file to be printed.
   * @param success when ends without error, success is called.
   * @param error when ends with error, error is called.
   */
  doPrint(
    fileName: string,
    success: SuccessCallbackType,
    error: ErrorCallbackType
  ): void;
}

type SuccessCallbackType = (data: IResult) => void;
type ErrorCallbackType = (error: string) => void;

export interface IResult {
  type: ResultType;
  result: string | number;
}

export enum ResultType {
  RESULT = 1,
  EVENT = 2,
}
