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

import static io.kestros.commons.validation.ModelValidationMessageType.WARNING;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.kestros.cms.forms.validators.BaseFormFieldValidator;
import io.kestros.commons.structuredslingmodels.BaseSlingModel;
import io.kestros.commons.validation.ModelValidationMessageType;
import io.kestros.commons.validation.models.ModelValidator;
import io.kestros.commons.validation.models.ModelValidatorBundle;
import io.kestros.commons.validation.services.ModelValidationService;
import io.kestros.commons.validation.services.ModelValidatorRegistrationService;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.osgi.service.component.annotations.Reference;

/**
 * Generic validation service for form fields.
 */
public class BaseFormFieldValidationService implements ModelValidatorRegistrationService {

  @Reference
  private ModelValidationService modelValidationService;

  @Override
  public Class<? extends BaseSlingModel> getModelType() {
    return BaseFormField.class;
  }

  @Override
  public List<ModelValidator> getModelValidators() {
    List<ModelValidator> modelValidators = new ArrayList<>();
    modelValidators.add(hasLabel());
    modelValidators.add(allInputValidatorsAreValid());
    return modelValidators;
  }

  @SuppressFBWarnings("SIC_INNER_SHOULD_BE_STATIC_ANON")
  ModelValidatorBundle allInputValidatorsAreValid() {
    return new ModelValidatorBundle<BaseFormField>() {
      @Override
      public void registerValidators() {
        for (final BaseFormFieldValidator fieldValidator : getModel().getFieldValidators()) {
          for (final ModelValidator validator : modelValidationService.getProcessedValidators(
              fieldValidator)) {
            addValidator(validator);
          }
        }
      }

      @Override
      public boolean isAllMustBeTrue() {
        return true;
      }

      @Override
      public String getMessage() {
        return "All input validators are valid.";
      }

      @Override
      public ModelValidationMessageType getType() {
        return ModelValidationMessageType.ERROR;
      }
    };


  }

  /**
   * Whether the form field has a label configured.
   *
   * @return Whether the form field has a label configured.
   */
  @SuppressFBWarnings("SIC_INNER_SHOULD_BE_STATIC_ANON")
  public ModelValidator hasLabel() {
    return new ModelValidator<BaseFormField>() {
      @Override
      public Boolean isValidCheck() {
        return StringUtils.isNotEmpty(getModel().getLabel());
      }

      @Override
      public String getMessage() {
        return "Has a configured label.";
      }

      @Override
      public String getDetailedMessage() {
        return "";
      }

      @Override
      public ModelValidationMessageType getType() {
        return WARNING;
      }
    };
  }


}
