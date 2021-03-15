package com.rincentral.test.utils.validator;

public interface RequestValidator<T> {
   void validate(T request);
}
