package org.norilicht.hibernate.validator;

import javax.validation.ValidatorContext;

public interface ValidatorContextAwareConstraintValidator {
	void setValidatorContext(ValidatorContext validatorContext);
}
