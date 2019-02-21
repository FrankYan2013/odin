package com.huifu.odin.biz.validate.annotation;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Amt2PointValidator implements ConstraintValidator<Amt2Point, String> {

    private String regExFeeAmt = "^(([1-9]{1}\\d{0,12})|([0]{1}))(\\.(\\d){1,2})?$";
    private Pattern patternFeeAmt = Pattern.compile(regExFeeAmt);

    @Override
    public void initialize(Amt2Point constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isBlank(value)) {
            return true;
        }

        Matcher matcherFeeAmt = patternFeeAmt.matcher(value);
        return matcherFeeAmt.matches();
    }
}
