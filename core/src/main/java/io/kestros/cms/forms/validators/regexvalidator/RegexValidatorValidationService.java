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

package io.kestros.cms.forms.validators.regexvalidator;

import static io.kestros.commons.structuredslingmodels.validation.ModelValidationMessageType.ERROR;

import io.kestros.cms.forms.validators.BaseFormFieldValidatorValidationService;
import io.kestros.commons.structuredslingmodels.validation.ModelValidationMessageType;
import io.kestros.commons.structuredslingmodels.validation.ModelValidator;
import org.apache.commons.lang3.StringUtils;

/**
 * Validation service for {@link RegexValidatorValidationService}.
 */
public class RegexValidatorValidationService extends BaseFormFieldValidatorValidationService {

  @Override
  public RegexValidator getModel() {
    return (RegexValidator) getGenericModel();
  }

  @Override
  public void registerBasicValidators() {
    super.registerBasicValidators();
    addBasicValidator(hasPattern());
  }

  /**
   * Checks if the regex validator has a pattern configured.
   *
   * @return Whether the regex validator has a pattern configured.
   */
  public ModelValidator hasPattern() {
    return new ModelValidator() {
      @Override
      public boolean isValid() {
        return StringUtils.isNotEmpty(getModel().getPattern());
      }

      @Override
      public String getMessage() {
        return "Has a regex pattern configured.";
      }

      @Override
      public ModelValidationMessageType getType() {
        return ERROR;
      }
    };
  }
}
