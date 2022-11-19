package com.example.demo.tinytypes.values;

import com.example.demo.tinytypes.AbstractValueType;
import com.example.demo.tinytypes.validation.Validator;
import java.time.Instant;
import java.util.function.Function;

public class InstantValue extends AbstractValueType<Instant> {

    public InstantValue(Instant value, Validator<Instant> validator, Function<Instant, String> showFn) {
        super(value, validator, showFn);
    }
}
