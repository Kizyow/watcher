package fr.kizyow.bot.utils;

import java.util.function.Supplier;

public class Optional {
    private Object value;
    private boolean present;

    public Optional() {
        this.present = false;
    }

    public Optional(final Object value) {
        this.value = value;
        this.present = true;
    }

    public static Optional of(final Object value) {
        return new Optional(value);
    }

    public static Optional empty() {
        return new Optional();
    }

    public static Optional ofNullable(final Object value) {
        if (value == null) {
            return empty();
        }
        return new Optional(value);
    }

    public static Optional ofNullable(final Object value, final Supplier supplier) {
        if (value == null) {
            return empty();
        }
        return new Optional(value);
    }

    public static Optional ofNullable(final Object value, final Supplier supplier, final Throwable throwable) throws Throwable {
        if (value == null) {
            return empty();
        }
        return new Optional(value);
    }

    public static Optional ofNullable(final Object value, final Supplier supplier, final Throwable throwable, final Object... objects) throws Throwable {
        if (value == null) {
            return empty();
        }
        return new Optional(value);
    }

    public boolean isPresent() {
        return this.present;
    }

    public Object get() {
        return this.value;
    }

    public void set(final Object value) {
        this.value = value;
        this.present = true;
    }

    public void clear() {
        this.present = false;
    }

    public Object orElse(final Object value) {
        if (this.present) {
            return this.value;
        }
        return value;
    }

    public Object orElseGet(final Supplier supplier) {
        if (this.present) {
            return this.value;
        }
        return supplier.get();
    }

    public Object orElseThrow(final Throwable throwable) throws Throwable {
        if (this.present) {
            return this.value;
        }
        throw throwable;
    }

}
