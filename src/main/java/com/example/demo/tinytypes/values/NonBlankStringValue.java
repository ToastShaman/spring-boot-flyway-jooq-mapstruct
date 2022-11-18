package com.example.demo.tinytypes.values;

import com.example.demo.tinytypes.validation.Validator;

public class NonBlankStringValue extends StringValue {

    public NonBlankStringValue(String value, Validator<String> validator) {
        super(value, Validator.NonBlank().and(validator), Object::toString);
    }
}
