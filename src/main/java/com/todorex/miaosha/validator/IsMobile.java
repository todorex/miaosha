package com.todorex.miaosha.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @Author rex
 * 2018/10/26
 */
@Documented
@Constraint(
        validatedBy = {IsMobileValidator.class}
)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsMobile {
    /**
     * 默认不需要
     * @return
     */
    boolean required() default false;

    /**
     * 校验不通过的信息
     * @return
     */
    String message() default "手机号码有误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
