package com.nls.myrewards;

public class MyRewardsException extends RuntimeException {

    public MyRewardsException(String message) {
        super(message);
    }

    public MyRewardsException(Throwable e) {
        super(e);
    }
}
