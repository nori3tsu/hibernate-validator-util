package org.norilicht.hibernate.validator;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.ValidatorContext;

import org.norilicht.hibernate.validator.annotation.NotEmtpyStringCollection;
import org.norilicht.hibernate.validator.bean.NotEmptyStringCollectionElementBean;

public class NotEmptyStringCollectionValidator implements ConstraintValidator<NotEmtpyStringCollection, Collection<?>>, ValidatorContextAwareConstraintValidator {
	private ValidatorContext validatorContext;

	public void setValidatorContext(ValidatorContext validatorContext) {
		this.validatorContext = validatorContext;
	}

	public void initialize(NotEmtpyStringCollection annotation) {
	}

	public boolean isValid(Collection<?> collection, ConstraintValidatorContext context) {
		boolean valid = true;

		if(collection == null || collection.size() == 0) {
			return true;
		}

		Validator validator = validatorContext.getValidator();

		for(Object element : collection) {
			Set<ConstraintViolation<?>> violations = new HashSet<ConstraintViolation<?>> ();

			violations.addAll(validator.validateValue(NotEmptyStringCollectionElementBean.class, "value", element));

			if(!violations.isEmpty()) {
				valid = false;
			}
		}

		return valid;
	}
}
