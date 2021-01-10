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

package io.kestros.cms.forms;


import static io.kestros.commons.validation.ModelValidationMessageType.WARNING;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.kestros.cms.forms.fields.BaseFormField;
import io.kestros.commons.structuredslingmodels.BaseSlingModel;
import io.kestros.commons.validation.ModelValidationMessageType;
import io.kestros.commons.validation.models.BaseModelValidationRegistrationService;
import io.kestros.commons.validation.models.ModelValidator;
import io.kestros.commons.validation.models.ModelValidatorBundle;
import io.kestros.commons.validation.services.ModelValidationService;
import io.kestros.commons.validation.services.ModelValidatorRegistrationHandlerService;
import io.kestros.commons.validation.services.ModelValidatorRegistrationService;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.servlets.HttpConstants;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * Generic validation service for Kestros forms.
 */
public class BaseFormValidationService extends BaseModelValidationRegistrationService
    implements ModelValidatorRegistrationService {

  @Reference
  private ModelValidationService modelValidationService;

  @Reference(cardinality = ReferenceCardinality.OPTIONAL,
             policyOption = ReferencePolicyOption.GREEDY)
  private ModelValidatorRegistrationHandlerService modelValidatorRegistrationHandlerService;

  @Override
  public ModelValidatorRegistrationHandlerService getModelValidatorRegistrationHandlerService() {
    return modelValidatorRegistrationHandlerService;
  }

  @Override
  public Class<? extends BaseSlingModel> getModelType() {
    return BaseForm.class;
  }

  @Override
  public List<ModelValidator> getModelValidators() {
    List<ModelValidator> validators = new ArrayList<>();
    validators.add(hasFields());
    validators.add(hasSubmitPath());
    validators.add(isValidHttpMethod());
    validators.add(hasDefaultErrorMessage());
    validators.add(allFieldsAreValid());
    return validators;
  }

  ModelValidatorBundle allFieldsAreValid() {
    return new ModelValidatorBundle<BaseForm>() {
      @Override
      public void registerValidators() {
        for (final BaseFormField field : getModel().getFields()) {

          for (final ModelValidator validator : modelValidationService.getProcessedValidators(
              field)) {
            if (validator.getType().equals(ModelValidationMessageType.ERROR)
                || validator.getType().equals(WARNING)) {
              addValidator(validator);
            }
          }
        }
      }

      @Override
      public boolean isAllMustBeTrue() {
        return true;
      }

      @Override
      public String getMessage() {
        return "All fields are valid.";
      }

      @Override
      public ModelValidationMessageType getType() {
        return ModelValidationMessageType.ERROR;
      }
    };
  }

  /**
   * Whether the form has input fields.
   *
   * @return Whether the form has input fields.
   */
  public ModelValidator hasFields() {
    return new ModelValidator<BaseForm>() {
      @Override
      public Boolean isValidCheck() {
        return getModel().getFields().size() > 0;
      }

      @Override
      public String getMessage() {
        return "Has at least one field.";
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

  /**
   * Whether a submit path has been configured on a form.
   *
   * @return Whether a submit path has been configured on a form.
   */
  public ModelValidator hasSubmitPath() {
    return new ModelValidator<BaseForm>() {
      @Override
      public Boolean isValidCheck() {
        return StringUtils.isNotEmpty(getModel().getSubmitPath());
      }

      @Override
      public String getMessage() {
        return "Has submit path.";
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

  /**
   * Whether a default error message has been configured on a form.
   *
   * @return Whether a default error message has been configured on a form.
   */
  public ModelValidator hasDefaultErrorMessage() {
    return new ModelValidator<BaseForm>() {
      @Override
      public Boolean isValidCheck() {
        return StringUtils.isNotEmpty(getModel().getDefaultErrorMessage());
      }

      @Override
      public String getMessage() {
        return "Has default error message.";
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

  /**
   * Whether an HTTP Method has been configured and is valid.
   *
   * @return Whether an HTTP Method has been configured and is valid.
   */
  public ModelValidatorBundle isValidHttpMethod() {
    return new ModelValidatorBundle<BaseForm>() {
      @Override
      public void registerValidators() {
        addValidator(isValidHttpMethod(HttpConstants.METHOD_POST));
        addValidator(isValidHttpMethod(HttpConstants.METHOD_PUT));
        addValidator(isValidHttpMethod(HttpConstants.METHOD_DELETE));
      }

      @Override
      public String getMessage() {
        return "Submits using a valid HTTP method.";
      }

      @Override
      public boolean isAllMustBeTrue() {
        return false;
      }


      @Override
      public ModelValidationMessageType getType() {
        return ModelValidationMessageType.ERROR;
      }
    };
  }

  /**
   * Checks if the method property value matches to a specified HTTP Method.
   *
   * @param httpMethod HTTP Method to check against.
   * @return Whether the method property value matches to a specified HTTP Method.
   */
  @SuppressFBWarnings("SIC_INNER_SHOULD_BE_STATIC_ANON")
  public ModelValidator isValidHttpMethod(@Nonnull String httpMethod) {

    return new ModelValidator<BaseForm>() {
      @Override
      public Boolean isValidCheck() {
        return getModel().getMethod().equals(httpMethod);
      }

      @Override
      public String getMessage() {
        return String.format("Submits using %s HTTP method.", httpMethod);
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
