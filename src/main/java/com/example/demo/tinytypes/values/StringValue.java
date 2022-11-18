package com.example.demo.tinytypes.values;

import com.example.demo.tinytypes.AbstractValueType;
import com.example.demo.tinytypes.validation.Validator;
import java.util.function.Function;

public class StringValue extends AbstractValueType<String> {

    public StringValue(String value, Validator<String> validator, Function<String, String> showFn) {
        super(value, validator, showFn);
    }
}
