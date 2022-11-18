package com.example.demo.tinytypes.validation;

import io.vavr.control.Either;
import java.time.*;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import static java.lang.String.format;

public interface Validator<T> {

    static <R> Validator<R> of(Predicate<R> predicate, String message) {
        return new TinyTypeValidator<>(predicate, message);
    }

    static <R> Validator<R> of(Predicate<R> predicate, Function<R, String> message) {
        return new TinyTypeValidator<>(predicate, message);
    }

    static Validator<Boolean> IsTrue() {
        return Validator.of(Boolean.TRUE::equals, "must be true");
    }

    static Validator<Boolean> IsFalse() {
        return Validator.of(Boolean.FALSE::equals, "must be false");
    }

    static <R> Validator<R> AlwaysValid() {
        return Validator.of(value -> true, "must be valid");
    }

    static Validator<String> NonBlank() {
        return Validator.of(value -> !value.isBlank(), "must not be blank");
    }

    static Validator<String> NonEmpty() {
        return Validator.of(value -> !value.isEmpty(), "must not be empty");
    }

    static Validator<String> MaxLength(Integer max) {
        Objects.requireNonNull(max);
        return Validator.of(value -> value.length() <= max, value -> format("must be less than or equal to %d", max));
    }

    static Validator<String> MinLength(Integer min) {
        Objects.requireNonNull(min);
        return Validator.of(value -> value.length() >= min, value -> format("must be greater than or equal to %d", min));
    }

    static Validator<Integer> Min(Integer min) {
        Objects.requireNonNull(min);
        return Validator.of(value -> value >= min, value -> format("must be greater than or equal to %d", min));
    }

    static Validator<Long> Min(Long min) {
        Objects.requireNonNull(min);
        return Validator.of(value -> value >= min, value -> format("must be greater than or equal to %d", min));
    }

    static Validator<Double> Min(Double min) {
        Objects.requireNonNull(min);
        return Validator.of(value -> value >= min, value -> format("must be greater than or equal to %f", min));
    }

    static Validator<Float> Min(Float min) {
        Objects.requireNonNull(min);
        return Validator.of(value -> value >= min, value -> format("must be greater than or equal to %f", min));
    }

    static Validator<Integer> Max(Integer max) {
        Objects.requireNonNull(max);
        return Validator.of(value -> value <= max, value -> format("must be less than or equal to %d", max));
    }

    static Validator<Long> Max(Long max) {
        Objects.requireNonNull(max);
        return Validator.of(value -> value <= max, value -> format("must be less than or equal to %d", max));
    }

    static Validator<Double> Max(Double max) {
        Objects.requireNonNull(max);
        return Validator.of(value -> value <= max, value -> format("must be less than or equal to %f", max));
    }

    static Validator<Float> Max(Float max) {
        Objects.requireNonNull(max);
        return Validator.of(value -> value <= max, value -> format("must be less than or equal to %f", max));
    }

    static Validator<String> Matches(Pattern pattern) {
        Objects.requireNonNull(pattern);
        return Validator.of(value -> pattern.matcher(value).matches(), value -> format("must match %s", pattern));
    }

    static Validator<String> Matches(String regex) {
        return Validator.Matches(Pattern.compile(Objects.requireNonNull(regex)));
    }

    static TimeValidatorFactory withClock(Clock clock) {
        return new TimeValidatorFactory(clock);
    }

    class TimeValidatorFactory {
        private final Clock clock;

        public TimeValidatorFactory(Clock clock) {
            this.clock = Objects.requireNonNull(clock);
        }

        @SuppressWarnings({"unchecked", "rawtypes"})
        public <T> Validator<T> Future() {
            Predicate predicate = value -> {
                if (value instanceof Instant)
                    return ((Instant) value).isAfter(Instant.now(clock));

                if (value instanceof LocalDate)
                    return ((LocalDate) value).isAfter(LocalDate.now(clock));

                if (value instanceof LocalDateTime)
                    return ((LocalDateTime) value).isAfter(LocalDateTime.now(clock));

                if (value instanceof OffsetTime)
                    return ((OffsetTime) value).isAfter(OffsetTime.now(clock));

                if (value instanceof OffsetDateTime)
                    return ((OffsetDateTime) value).isAfter(OffsetDateTime.now(clock));

                if (value instanceof ZonedDateTime)
                    return ((ZonedDateTime) value).isAfter(ZonedDateTime.now(clock));

                return false;
            };

            return Validator.of(predicate, value -> "must be a future date");
        }

        @SuppressWarnings({"unchecked", "rawtypes"})
        public <T> Validator<T> FutureOrPresent() {
            Predicate predicate = value -> {
                if (value instanceof Instant)
                    return ((Instant) value).isAfter(Instant.now(clock)) || value.equals(Instant.now(clock));

                if (value instanceof LocalDate)
                    return ((LocalDate) value).isAfter(LocalDate.now(clock)) || value.equals(LocalDate.now(clock));

                if (value instanceof LocalDateTime)
                    return ((LocalDateTime) value).isAfter(LocalDateTime.now(clock)) || value.equals(LocalDateTime.now(clock));

                if (value instanceof OffsetTime)
                    return ((OffsetTime) value).isAfter(OffsetTime.now(clock)) || value.equals(OffsetTime.now(clock));

                if (value instanceof OffsetDateTime)
                    return ((OffsetDateTime) value).isAfter(OffsetDateTime.now(clock)) || value.equals(OffsetDateTime.now(clock));

                if (value instanceof ZonedDateTime)
                    return ((ZonedDateTime) value).isAfter(ZonedDateTime.now(clock)) || value.equals(ZonedDateTime.now(clock));

                return false;
            };

            return Validator.of(predicate, value -> "must be a date in the present or in the future");
        }

        @SuppressWarnings({"unchecked", "rawtypes"})
        public <T> Validator<T> Past() {
            Predicate predicate = value -> {
                if (value instanceof Instant)
                    return ((Instant) value).isBefore(Instant.now(clock));

                if (value instanceof LocalDate)
                    return ((LocalDate) value).isBefore(LocalDate.now(clock));

                if (value instanceof LocalDateTime)
                    return ((LocalDateTime) value).isBefore(LocalDateTime.now(clock));

                if (value instanceof OffsetTime)
                    return ((OffsetTime) value).isBefore(OffsetTime.now(clock));

                if (value instanceof OffsetDateTime)
                    return ((OffsetDateTime) value).isBefore(OffsetDateTime.now(clock));

                if (value instanceof ZonedDateTime)
                    return ((ZonedDateTime) value).isBefore(ZonedDateTime.now(clock));

                return false;
            };

            return Validator.of(predicate, value -> "must be a past date");
        }

        @SuppressWarnings({"unchecked", "rawtypes"})
        public <T> Validator<T> PastOrPresent() {
            Predicate predicate = value -> {
                if (value instanceof Instant)
                    return ((Instant) value).isBefore(Instant.now(clock)) || value.equals(Instant.now(clock));

                if (value instanceof LocalDate)
                    return ((LocalDate) value).isBefore(LocalDate.now(clock)) || value.equals(LocalDate.now(clock));

                if (value instanceof LocalDateTime)
                    return ((LocalDateTime) value).isBefore(LocalDateTime.now(clock)) || value.equals(LocalDateTime.now(clock));

                if (value instanceof OffsetTime)
                    return ((OffsetTime) value).isBefore(OffsetTime.now(clock)) || value.equals(OffsetTime.now(clock));

                if (value instanceof OffsetDateTime)
                    return ((OffsetDateTime) value).isBefore(OffsetDateTime.now(clock)) || value.equals(OffsetDateTime.now(clock));

                if (value instanceof ZonedDateTime)
                    return ((ZonedDateTime) value).isBefore(ZonedDateTime.now(clock)) || value.equals(ZonedDateTime.now(clock));

                return false;
            };

            return Validator.of(predicate, value -> "must be a date in the past or in the present");
        }
    }

    default Validator<T> and(Validator<T> other) {
        return new CompositeAndValidator<>(this, other);
    }

    default Validator<T> or(Validator<T> other) {
        return new CompositeOrValidator<>(this, other);
    }

    Either<List<String>, T> isValid(T t);
}
