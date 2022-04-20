var exec = require("cordova/exec");

exports.coolMethod = function (arg0, success, error) {
  exec(success, error, "MePay", "coolMethod", [arg0]);
};

exports.sum = function (arg0, success, error) {
  exec(success, error, "MePay", "sum", [arg0]);
};

exports.observable = function (arg0, success, error){
  exec(success, error, "MePay", "observable", [arg0]);
}
