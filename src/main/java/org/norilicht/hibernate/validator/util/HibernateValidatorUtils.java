package org.norilicht.hibernate.validator.util;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public final class HibernateValidatorUtils {
	private static final String MESSAGE_FORMAT = "[%s] %s : %s";

	private HibernateValidatorUtils() { }

	public static final void validate(Object object) throws ValidationException {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();

		Set<ConstraintViolation<Object>> violations = validator.validate(object);

		if (violations.size() > 0) {
			String message = makeErrorMessage(violations);
			throw new ValidationException(message);
		}
	}

	public static final void validate(List<?> list) throws ValidationException {
		for (Object object : list) {
			validate(object);
		}
	}

	private static final String makeErrorMessage(Set<ConstraintViolation<Object>> violations) {
		StringBuilder messages = new StringBuilder();

		messages.append("invalid request params: ");

		Iterator<ConstraintViolation<Object>> iViolation = violations.iterator();
		while (iViolation.hasNext()) {
			ConstraintViolation<Object> violation = iViolation.next();

			String property = violation.getPropertyPath().toString();
			Object invalidValue = violation.getInvalidValue();
			String message = violation.getMessage();

			messages.append(String.format(MESSAGE_FORMAT, property, message, invalidValue));

			if (iViolation.hasNext()) {
				messages.append(", ");
			}
		}

		return messages.toString();
	}

}
