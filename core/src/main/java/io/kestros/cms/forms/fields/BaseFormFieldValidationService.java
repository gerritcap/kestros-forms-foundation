/*
 *      Copyright (C) 2020  Kestros, Inc.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package io.kestros.cms.forms.fields;

import static io.kestros.commons.structuredslingmodels.validation.CommonValidators.getFailedErrorValidators;
import static io.kestros.commons.structuredslingmodels.validation.CommonValidators.getFailedWarningValidators;
import static io.kestros.commons.structuredslingmodels.validation.ModelValidationMessageType.WARNING;

import io.kestros.cms.forms.validators.BaseFormFieldValidator;
import io.kestros.commons.structuredslingmodels.validation.ModelValidationMessageType;
import io.kestros.commons.structuredslingmodels.validation.ModelValidationService;
import io.kestros.commons.structuredslingmodels.validation.ModelValidator;
import org.apache.commons.lang3.StringUtils;

/**
 * Generic validation service for form fields.
 */
public class BaseFormFieldValidationService extends ModelValidationService {

  @Override
  public BaseFormField getModel() {
    return (BaseFormField) getGenericModel();
  }

  @Override
  public void registerBasicValidators() {
    addBasicValidator(hasLabel());
  }

  @Override
  public void registerDetailedValidators() {
    for (final BaseFormFieldValidator fieldValidator : getModel().getFieldValidators()) {
      fieldValidator.doDetailedValidation();
      for (final ModelValidator validator : getFailedErrorValidators(fieldValidator)) {
        addDetailedValidator(validator);
      }
      for (final ModelValidator validator : getFailedWarningValidators(fieldValidator)) {
        addDetailedValidator(validator);
      }
    }
  }

  /**
   * Whether the form field has a label configured.
   *
   * @return Whether the form field has a label configured.
   */
  public ModelValidator hasLabel() {
    return new ModelValidator() {
      @Override
      public boolean isValid() {
        return StringUtils.isNotEmpty(getModel().getLabel());
      }

      @Override
      public String getMessage() {
        return "Has a configured label.";
      }

      @Override
      public ModelValidationMessageType getType() {
        return WARNING;
      }
    };
  }
}
