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

import io.kestros.commons.structuredslingmodels.BaseResource;
import io.kestros.commons.structuredslingmodels.annotation.KestrosModel;
import io.kestros.commons.structuredslingmodels.annotation.KestrosProperty;
import org.apache.commons.lang3.StringUtils;

/**
 * Abstract form field validator. Used for validating field input on the frontend (handled by JS)
 * and backend.
 */
@KestrosModel
public abstract class BaseFormFieldValidator extends BaseResource {

  /**
   * Message to show on the field if validation fails.
   *
   * @return Message to show on the field if validation fails.
   */
  @KestrosProperty(description = "Message to be shown when field has an invalid input.",
                   configurable = true,
                   defaultValue = "",
                   sampleValue = "")
  public String getMessage() {
    return getProperty("message", StringUtils.EMPTY);
  }

}
