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

import io.kestros.cms.forms.fields.textfield.Textfield;
import io.kestros.commons.structuredslingmodels.annotation.KestrosModel;
import io.kestros.commons.structuredslingmodels.annotation.KestrosProperty;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

/**
 * TextArea form field model.
 */
@KestrosModel
@Model(adaptables = Resource.class,
       resourceType = "kestros/components/forms/fields/textarea")
public class TextArea extends Textfield {

  /**
   * Number of rows to display in the textarea.
   *
   * @return Number of rows to display in the textarea.
   */
  @KestrosProperty(description = "Number of visible rows",
                   jcrPropertyName = "rows",
                   configurable = true,
                   defaultValue = "3",
                   sampleValue = "3")
  public int getRows() {
    Integer rows = getProperties().get("rows", 3);
    if (rows > 0) {
      return rows;
    }
    return 3;
  }
}
