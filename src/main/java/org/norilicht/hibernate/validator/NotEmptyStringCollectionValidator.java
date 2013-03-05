package org.norilicht.hibernate.validator;

import java.beans.Introspector;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.ValidatorContext;

import org.norilicht.hibernate.validator.annotation.NotEmtpyStringCollection;
import org.norilicht.hibernate.validator.bean.NotEmptyStringCollectionElementBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotEmptyStringCollectionValidator implements ConstraintValidator<NotEmtpyStringCollection, Collection<?>>, ValidatorContextAwareConstraintValidator {
	/** Logger */
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private ValidatorContext validatorContext;

	private Class<?>[] constraints;
	private boolean allViolationMessages;

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

			for(Class<?> constraint : constraints) {
				String propertyName = constraint.getSimpleName();
				propertyName = Introspector.decapitalize(propertyName);
				violations.addAll(validator.validateValue(NotEmptyStringCollectionElementBean.class, "value", element));
			}

			if(!violations.isEmpty()) {
				valid = false;
			}
			for(ConstraintViolation<?> violation : violations) {
				logger.debug(violation.getMessage());
				ConstraintViolationBuilder violationBuilder = context.buildConstraintViolationWithTemplate(violation.getMessage());
				violationBuilder.addConstraintViolation();
			}

		}

		return valid;
	}
}
