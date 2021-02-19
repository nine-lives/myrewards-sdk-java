package com.nls.myrewards;

import java.util.Collections;
import java.util.List;

public final class MyRewardsError {

    private List<String> errors;

    public MyRewardsError() {
    }

    public MyRewardsError(String message) {
        this.errors = Collections.singletonList(message);
    }

    public String getMessage() {
        return String.join(", ", errors);
    }

    public List<String> getErrors() {
        return errors;
    }
}
