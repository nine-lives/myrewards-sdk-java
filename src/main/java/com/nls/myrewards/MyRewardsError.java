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

//    public Map<String, List<String>> getFieldErrors() {
////        Map<String, String> map = new LinkedHashMap<>();
////        if (error instanceof Map) {
////            for (Map.Entry entry : ((Map<Object, Object>) error).entrySet()) {
////                map.put(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
////            }
////        }
////        return map;
//        return error instanceof Map ? (Map<String, List<String>>) error : Collections.emptyMap();
//    }


    public List<String> getErrors() {
        return errors;
    }
}
