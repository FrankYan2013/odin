package com.huifu.odin.biz.validate.annotation;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author frank
 */
public class ExistsArrayValidator implements ConstraintValidator<ExistsArray, String> {

    private String[] codes;

    public ExistsArrayValidator() {
    }

    @Override
    public void initialize(ExistsArray existsArray) {
        codes = existsArray.codes();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return StringUtils.isBlank(value)|| ArrayUtils.contains(codes,value);
    }
}

