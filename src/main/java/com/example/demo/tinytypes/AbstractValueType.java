package com.example.demo.tinytypes;

import com.example.demo.tinytypes.validation.ValidationException;
import com.example.demo.tinytypes.validation.Validator;
import java.util.Objects;
import java.util.function.Function;

public abstract class AbstractValueType<T extends Comparable<? super T>> implements Comparable<AbstractValueType<T>> {

    protected final T value;
    protected final Function<T, String> showFn;
    protected final Validator<T> validator;

    public AbstractValueType(T value,
                             Validator<T> validator,
                             Function<T, String> showFn) {
        Objects.requireNonNull(value);
        Objects.requireNonNull(showFn);
        Objects.requireNonNull(validator).isValid(value).peekLeft(it -> {
            throw new ValidationException(this.getClass().getSimpleName(), it);
        });

        this.validator = validator;
        this.showFn = showFn;
        this.value = value;
    }

    public String show() {
        return showFn.apply(value);
    }

    public T unwrap() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractValueType<?> that = (AbstractValueType<?>) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return show();
    }

    @Override
    public int compareTo(AbstractValueType<T> other) {
        return value.compareTo(other.value);
    }
}
