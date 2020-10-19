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


import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.kestros.cms.forms.validators.BaseFormFieldValidatorValidationService;
import io.kestros.commons.structuredslingmodels.BaseSlingModel;
import io.kestros.commons.validation.ModelValidationMessageType;
import io.kestros.commons.validation.models.ModelValidator;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 * Validation service for {@link RegexValidatorValidationService}.
 */
public class RegexValidatorValidationService extends BaseFormFieldValidatorValidationService {


  @Override
  public Class<? extends BaseSlingModel> getModelType() {
    return RegexValidator.class;
  }

  @Override
  public List<ModelValidator> getModelValidators() {
    List<ModelValidator> modelValidators = new ArrayList<>();
    modelValidators.addAll(super.getModelValidators());
    modelValidators.add(hasPattern());
    return modelValidators;
  }

  /**
   * Checks if the regex validator has a pattern configured.
   *
   * @return Whether the regex validator has a pattern configured.
   */
  @SuppressFBWarnings("SIC_INNER_SHOULD_BE_STATIC_ANON")
  public ModelValidator hasPattern() {
    return new ModelValidator<RegexValidator>() {
      @Override
      public Boolean isValidCheck() {
        return StringUtils.isNotEmpty(getModel().getPattern());
      }

      @Override
      public String getMessage() {
        return "Has a regex pattern configured.";
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
