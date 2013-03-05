package org.norilicht.hibernate.validator.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import org.norilicht.hibernate.validator.NotEmptyStringCollectionValidator;

@Target( { ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotEmptyStringCollectionValidator.class)
public @interface NotEmtpyStringCollection {
	String message() default "{NotEmtpyStringCollection.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
