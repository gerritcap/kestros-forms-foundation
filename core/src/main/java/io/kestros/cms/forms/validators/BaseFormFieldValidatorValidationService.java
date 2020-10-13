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


import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.kestros.commons.structuredslingmodels.BaseSlingModel;
import io.kestros.commons.validation.ModelValidationMessageType;
import io.kestros.commons.validation.models.ModelValidator;
import io.kestros.commons.validation.services.ModelValidatorRegistrationService;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 * Validation service for {@link BaseFormFieldValidator} resources.
 */
public class BaseFormFieldValidatorValidationService implements ModelValidatorRegistrationService {


  @Override
  public Class<? extends BaseSlingModel> getModelType() {
    return BaseFormFieldValidator.class;
  }

  @Override
  public List<ModelValidator> getModelValidators() {
    return Collections.singletonList(hasMessage());
  }

  /**
   * Checks whether the validator has an error message configured.
   *
   * @return Checks whether the validator has an error message configured.
   */
  @SuppressFBWarnings("SIC_INNER_SHOULD_BE_STATIC_ANON")
  public ModelValidator hasMessage() {
    return new ModelValidator<BaseFormFieldValidator>() {
      @Override
      public Boolean isValidCheck() {
        return StringUtils.isNotEmpty(getModel().getMessage());
      }

      @Override
      public String getMessage() {
        return "Has a configured error message.";
      }

      @Override
      public String getDetailedMessage() {
        return "";
      }

      @Override
      public ModelValidationMessageType getType() {
        return ModelValidationMessageType.ERROR;
      }
    };
  }

}
