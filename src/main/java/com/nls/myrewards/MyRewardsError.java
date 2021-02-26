package com.nls.myrewards;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class MyRewardsError {

    private List<String> errors;

    public MyRewardsError() {
    }

    public MyRewardsError(String message) {
        this.errors = Collections.singletonList(message);
    }

    public String getMessage() {
        return errors == null
                ? ""
                : errors.stream().filter(Objects::nonNull).collect(Collectors.joining(", "));
    }

    public List<String> getErrors() {
        return errors;
    }
}
