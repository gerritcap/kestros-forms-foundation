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

package io.kestros.cms.forms.fields.textarea;


import static io.kestros.commons.validation.ModelValidationMessageType.WARNING;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.kestros.cms.forms.fields.BaseFormFieldValidationService;
import io.kestros.commons.structuredslingmodels.BaseSlingModel;
import io.kestros.commons.validation.ModelValidationMessageType;
import io.kestros.commons.validation.models.ModelValidator;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 * Validation service for {@link TextArea}.
 */
public class TextAreaValidationService extends BaseFormFieldValidationService {

  @Override
  public Class<? extends BaseSlingModel> getModelType() {
    return TextArea.class;
  }

  @Override
  public List<ModelValidator> getModelValidators() {
    List<ModelValidator> modelValidators = new ArrayList<>();
    modelValidators.addAll(super.getModelValidators());
    modelValidators.add(isRowsPositiveNumber());
    modelValidators.add(isRowsPropertyValueIsInteger());
    return modelValidators;
  }

  /**
   * Checks whether the configured number of rows is a positive integer.
   *
   * @return Checks whether the configured number of rows is a positive integer.
   */
  @SuppressFBWarnings("SIC_INNER_SHOULD_BE_STATIC_ANON")
  public ModelValidator isRowsPositiveNumber() {
    return new ModelValidator<TextArea>() {
      @Override
      public Boolean isValidCheck() {
        return Integer.parseInt(getModel().getProperty("rows", StringUtils.EMPTY)) > 0;
      }

      @Override
      public String getMessage() {
        return "Configured number of rows is positive.";
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
   * Checks whether the configured rows property is an integer.
   *
   * @return Checks whether the configured rows property is an integer.
   */
  @SuppressFBWarnings("SIC_INNER_SHOULD_BE_STATIC_ANON")
  public ModelValidator isRowsPropertyValueIsInteger() {
    return new ModelValidator<TextArea>() {
      @Override
      public Boolean isValidCheck() {
        try {
          Integer.parseInt(getModel().getProperty("rows", StringUtils.EMPTY));
        } catch (Exception e) {
          return false;
        }
        return true;
      }

      @Override
      public String getMessage() {
        return "Configured number of rows is an integer.";
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
