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

package io.kestros.cms.forms.validators;

import static io.kestros.commons.structuredslingmodels.validation.ModelValidationMessageType.ERROR;

import io.kestros.commons.structuredslingmodels.validation.ModelValidationMessageType;
import io.kestros.commons.structuredslingmodels.validation.ModelValidationService;
import io.kestros.commons.structuredslingmodels.validation.ModelValidator;
import org.apache.commons.lang3.StringUtils;

/**
 * Validation service for {@link BaseFormFieldValidator} resources.
 */
public class BaseFormFieldValidatorValidationService extends ModelValidationService {

  @Override
  public BaseFormFieldValidator getModel() {
    return (BaseFormFieldValidator) getGenericModel();
  }

  @Override
  public void registerBasicValidators() {
    addBasicValidator(hasMessage());
  }

  @Override
  public void registerDetailedValidators() {
  }

  /**
   * Checks whether the validator has an error message configured.
   *
   * @return Checks whether the validator has an error message configured.
   */
  public ModelValidator hasMessage() {
    return new ModelValidator() {
      @Override
      public boolean isValid() {
        return StringUtils.isNotEmpty(getModel().getMessage());
      }

      @Override
      public String getMessage() {
        return "Has a configured error message.";
      }

      @Override
      public ModelValidationMessageType getType() {
        return ERROR;
      }
    };
  }
}
