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

import static io.kestros.commons.structuredslingmodels.utils.SlingModelUtils.getChildAsBaseResource;
import static io.kestros.commons.structuredslingmodels.utils.SlingModelUtils.getChildrenAsBaseResource;

import io.kestros.cms.forms.fields.BaseFormField;
import io.kestros.commons.structuredslingmodels.BaseResource;
import io.kestros.commons.structuredslingmodels.annotation.KestrosProperty;
import io.kestros.commons.structuredslingmodels.exceptions.ChildResourceNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Basic select field.
 */
@Model(adaptables = Resource.class,
       resourceType = "kestros/components/forms/fields/selectfield")
public class SelectField extends BaseFormField {

  private static final Logger LOG = LoggerFactory.getLogger(SelectField.class);

  /**
   * Placeholder option text.
   *
   * @return Placeholder option text.
   */
  @KestrosProperty(description = "Placeholder option text.",
                   jcrPropertyName = "placeholder",
                   configurable = true,
                   defaultValue = "false",
                   sampleValue = "false")
  public String getPlaceholder() {
    return getProperty("placeholder", StringUtils.EMPTY);
  }

  /**
   * Value of the option to be selected by default.
   *
   * @return Value of the option to be selected by default.
   */
  @KestrosProperty(description = "Value of the option to be selected by default, if no context "
                                 + "value is available.")
  public String getDefaultValue() {
    for (SelectFieldOption option : getOptions()) {
      if (option.isSelectedByDefault()) {
        return option.getValue();
      }
    }
    if (!getOptions().isEmpty()) {
      return getOptions().get(0).getValue();
    }
    return StringUtils.EMPTY;
  }

  /**
   * All configured options.
   *
   * @return All configured options.
   */
  @KestrosProperty(description = "Configured options.")
  public List<SelectFieldOption> getOptions() {
    List<SelectFieldOption> options = new ArrayList();
    try {
      for (BaseResource optionResource : getChildrenAsBaseResource(getOptionsRootResource())) {
        SelectFieldOption selectFieldOption = optionResource.getResource().adaptTo(
            SelectFieldOption.class);

        if (selectFieldOption != null) {
          options.add(selectFieldOption);
        }
      }
      return options;
    } catch (ChildResourceNotFoundException e) {
      LOG.warn("Unable to retrieve options for {}. Options root resource not found. {}.", getPath(),
          e.getMessage());
    }
    return Collections.emptyList();
  }

  private BaseResource getOptionsRootResource() throws ChildResourceNotFoundException {
    return getChildAsBaseResource("options", this);
  }

}
