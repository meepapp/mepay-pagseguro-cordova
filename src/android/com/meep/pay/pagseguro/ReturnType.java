package com.meep.pay.pagseguro;

enum ResultType {
    RESULT(1),
    EVENT(2);

    private int code;

    ResultType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
