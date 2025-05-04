package org.deodev.validation;

public interface Validator<T> {
    void validate(T dto);
}
