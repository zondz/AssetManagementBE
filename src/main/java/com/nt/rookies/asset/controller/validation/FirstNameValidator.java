package com.nt.rookies.asset.controller.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FirstNameValidator implements
		ConstraintValidator<FirstNameValid, String> {
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return false;
	}
}
