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

package io.kestros.cms.forms.fields.selectfield;

import io.kestros.commons.structuredslingmodels.BaseResource;
import io.kestros.commons.structuredslingmodels.annotation.KestrosProperty;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

/**
 * Option used within a {@link SelectField}.
 */
@Model(adaptables = Resource.class)
public class SelectFieldOption extends BaseResource {

  /**
   * Option display text.
   *
   * @return Option display text.
   */
  @KestrosProperty(description = "Displayed option text.",
                   jcrPropertyName = "text",
                   configurable = true,
                   defaultValue = "",
                   sampleValue = "")
  public String getText() {
    return getProperty("text", StringUtils.EMPTY);
  }

  /**
   * Option value.
   *
   * @return Option value.
   */
  @KestrosProperty(description = "Option value. Should be unique.",
                   jcrPropertyName = "value",
                   configurable = true,
                   defaultValue = "",
                   sampleValue = "")
  public String getValue() {
    return getProperty("value", StringUtils.EMPTY);
  }

  /**
   * Whether the option is selected by default.
   *
   * @return Whether the option is selected by default.
   */
  @KestrosProperty(description = "Whether the option should be selected when field value is "
                                 + "otherwise empty.",
                   jcrPropertyName = "selected",
                   configurable = true,
                   defaultValue = "false",
                   sampleValue = "false")
  protected Boolean isSelectedByDefault() {
    return getProperty("selected", Boolean.FALSE);
  }

}
