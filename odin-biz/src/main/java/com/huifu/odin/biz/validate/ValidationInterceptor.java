package com.huifu.odin.biz.validate;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.hibernate.validator.HibernateValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.executable.ExecutableValidator;
import java.util.Set;
import java.util.TreeSet;


/**
 * @author frank
 */
@Aspect
@Component
public class ValidationInterceptor {

    private Logger logger = LoggerFactory.getLogger(ValidationInterceptor.class);

    @Around("execution(public * *(..)) && @within(com.huifu.odin.biz.validate.SaturnValidating)")
    public Object validateMethodInvocation(ProceedingJoinPoint pjp) throws Throwable {
        Object result;
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        ValidatorFactory factory = Validation.byProvider(HibernateValidator.class).configure().buildValidatorFactory();

        ExecutableValidator executableValidator = factory.getValidator().forExecutables();
        Set<ConstraintViolation<Object>> constraintViolations = executableValidator.validateParameters(pjp.getTarget(), signature.getMethod(), pjp.getArgs()
        );
        if (!constraintViolations.isEmpty()) {
            logger.info("validate失败,请求参数{}，结果{}", pjp.getArgs(), constraintViolations);
            TreeSet<String> treeSet = new TreeSet<>();
            StringBuilder stringBuilder = new StringBuilder();
            for (ConstraintViolation<Object> o : constraintViolations) {
                treeSet.add(o.getMessage());
                stringBuilder.append(o.getMessage());
                if (treeSet.size() > 1) {
                    stringBuilder.append(";");
                }
            }

            throw new SaturnValidateException("100", treeSet.first(), stringBuilder.toString(), treeSet);
        }
        result = pjp.proceed();
        return result;
    }
}
