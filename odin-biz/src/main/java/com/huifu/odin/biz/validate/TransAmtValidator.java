package com.huifu.odin.biz.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author frank
 */
public class TransAmtValidator implements ConstraintValidator<TransAmt, String> {

    @Override
    public void initialize(TransAmt constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {	    
	    String regExZero = "0|0.0|0.00";	    
	    String regExTransAmt =  "^(([1-9]{1}\\d{0,10})|([0]{1}))(\\.(\\d){0,2})?$";	    
	    Pattern patternZero = Pattern.compile(regExZero);
	    Matcher matcherZero = patternZero.matcher(value);	    
	    Pattern patternTransAmt = Pattern.compile(regExTransAmt);
	    Matcher matcherTransAmt = patternTransAmt.matcher(value);
	    return matcherTransAmt.matches() && !matcherZero.matches();
    }
}
