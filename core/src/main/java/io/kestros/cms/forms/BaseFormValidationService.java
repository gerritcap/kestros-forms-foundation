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

import static io.kestros.commons.structuredslingmodels.validation.CommonValidators.getFailedErrorValidators;
import static io.kestros.commons.structuredslingmodels.validation.CommonValidators.getFailedWarningValidators;
import static io.kestros.commons.structuredslingmodels.validation.ModelValidationMessageType.ERROR;
import static io.kestros.commons.structuredslingmodels.validation.ModelValidationMessageType.WARNING;

import io.kestros.cms.forms.fields.BaseFormField;
import io.kestros.commons.structuredslingmodels.validation.ModelValidationMessageType;
import io.kestros.commons.structuredslingmodels.validation.ModelValidationService;
import io.kestros.commons.structuredslingmodels.validation.ModelValidator;
import io.kestros.commons.structuredslingmodels.validation.ModelValidatorBundle;
import javax.annotation.Nonnull;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.servlets.HttpConstants;

/**
 * Generic validation service for Kestros forms.
 */
public class BaseFormValidationService extends ModelValidationService {

  @Override
  public BaseForm getModel() {
    return (BaseForm) getGenericModel();
  }

  @Override
  public void registerBasicValidators() {
    addBasicValidator(hasFields());
    addBasicValidator(hasSubmitPath());
    addBasicValidator(isValidHttpMethod());
    addBasicValidator(hasDefaultErrorMessage());
  }

  @Override
  public void registerDetailedValidators() {
    for (final BaseFormField field : getModel().getFields()) {
      field.doDetailedValidation();
      for (final ModelValidator validator : getFailedErrorValidators(field)) {
        addDetailedValidator(validator);
      }
      for (final ModelValidator validator : getFailedWarningValidators(field)) {
        addDetailedValidator(validator);
      }
    }
  }

  /**
   * Whether the form has input fields.
   *
   * @return Whether the form has input fields.
   */
  public ModelValidator hasFields() {
    return new ModelValidator() {
      @Override
      public boolean isValid() {
        return getModel().getFields().size() > 0;
      }

      @Override
      public String getMessage() {
        return "Has at least one field.";
      }

      @Override
      public ModelValidationMessageType getType() {
        return ERROR;
      }
    };
  }

  /**
   * Whether a submit path has been configured on a form.
   *
   * @return Whether a submit path has been configured on a form.
   */
  public ModelValidator hasSubmitPath() {
    return new ModelValidator() {
      @Override
      public boolean isValid() {
        return StringUtils.isNotEmpty(getModel().getSubmitPath());
      }

      @Override
      public String getMessage() {
        return "Has submit path.";
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
    return new ModelValidator() {
      @Override
      public boolean isValid() {
        return StringUtils.isNotEmpty(getModel().getDefaultErrorMessage());
      }

      @Override
      public String getMessage() {
        return "Has default error message.";
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
    return new ModelValidatorBundle() {
      @Override
      public void registerValidators() {
        addBasicValidator(isValidHttpMethod(HttpConstants.METHOD_POST));
        addBasicValidator(isValidHttpMethod(HttpConstants.METHOD_PUT));
        addBasicValidator(isValidHttpMethod(HttpConstants.METHOD_DELETE));
      }

      @Override
      public String getBundleMessage() {
        return "Submits using a valid HTTP method.";
      }

      @Override
      public boolean isAllMustBeTrue() {
        return false;
      }

      @Override
      public ModelValidationMessageType getType() {
        return ERROR;
      }
    };
  }

  /**
   * Checks if the method property value matches to a specified HTTP Method.
   *
   * @param httpMethod HTTP Method to check against.
   * @return Whether the method property value matches to a specified HTTP Method.
   */
  public ModelValidator isValidHttpMethod(@Nonnull String httpMethod) {

    return new ModelValidator() {
      @Override
      public boolean isValid() {
        return getModel().getMethod().equals(httpMethod);
      }

      @Override
      public String getMessage() {
        return String.format("Submits using %s HTTP method.", httpMethod);
      }

      @Override
      public ModelValidationMessageType getType() {
        return WARNING;
      }
    };
  }
}
