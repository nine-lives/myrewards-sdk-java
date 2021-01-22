package com.nls.myrewards.client;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonTokenId;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.nls.myrewards.MyRewardsPermissionState;

import java.io.IOException;

public class MyRewardsPermissionStateDeserializer extends StdScalarDeserializer<MyRewardsPermissionState> {
    MyRewardsPermissionStateDeserializer() {
        super(MyRewardsPermissionState.class);
    }

    @Override
    public MyRewardsPermissionState deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (p.getCurrentToken().id() == JsonTokenId.ID_STRING) {
            String text = p.getText().trim();
            if (_isEmptyOrTextualNull(text)) {
                return getNullValue(ctxt);
            }

            try {
                return MyRewardsPermissionState.fromValue(text);
            } catch (IllegalArgumentException iae) {
                return (MyRewardsPermissionState) ctxt.handleWeirdStringValue(_valueClass, text, "not a valid permission state");
            }
        }

        return (MyRewardsPermissionState) ctxt.handleUnexpectedToken(_valueClass, p);
    }
}
