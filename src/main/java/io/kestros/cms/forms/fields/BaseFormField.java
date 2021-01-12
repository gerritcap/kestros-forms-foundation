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

import static io.kestros.commons.structuredslingmodels.utils.SlingModelUtils.getChildAsBaseResource;
import static io.kestros.commons.structuredslingmodels.utils.SlingModelUtils.getChildrenAsClosestTypes;

import io.kestros.cms.forms.validators.BaseFormFieldValidator;
import io.kestros.cms.sitebuilding.api.models.BaseComponent;
import io.kestros.commons.structuredslingmodels.BaseResource;
import io.kestros.commons.structuredslingmodels.annotation.KestrosModel;
import io.kestros.commons.structuredslingmodels.annotation.KestrosProperty;
import io.kestros.commons.structuredslingmodels.exceptions.ChildResourceNotFoundException;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.factory.ModelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Baseline field used within {@link io.kestros.cms.forms.BaseForm}.
 */
@KestrosModel
@Model(adaptables = Resource.class,
       resourceType = "kestros/components/forms/fields/base")
public class BaseFormField extends BaseComponent {

  private static final Logger LOG = LoggerFactory.getLogger(BaseFormField.class);

  @OSGiService
  private ModelFactory modelFactory;

  /**
   * Field label.
   *
   * @return Field label.
   */
  @KestrosProperty(description = "Field label.",
                   jcrPropertyName = "label",
                   defaultValue = "",
                   sampleValue = "",
                   configurable = true)
  public String getLabel() {
    return getProperties().get("label", StringUtils.EMPTY);
  }

  /**
   * Field description.
   *
   * @return Field description.
   */
  @KestrosProperty(description = "Field description.",
                   jcrPropertyName = "description",
                   defaultValue = "",
                   sampleValue = "",
                   configurable = true)
  public String getDescription() {
    return getProperties().get("description", StringUtils.EMPTY);
  }

  /**
   * Whether the field is required for valid form submission or not.
   *
   * @return Whether the field is required for valid form submission or not.
   */
  @KestrosProperty(description = "Whether the field is required for valid form submission or not.",
                   jcrPropertyName = "required",
                   defaultValue = "false",
                   sampleValue = "false",
                   configurable = true)
  public boolean isRequired() {
    return getProperties().get("required", Boolean.FALSE);
  }

  /**
   * Key to store the submitted value to.
   *
   * @return Key to store the submitted value to.
   */
  @KestrosProperty(description = "JCR property key to store the submitted value to.",
                   jcrPropertyName = "propertyName",
                   defaultValue = "",
                   sampleValue = "",
                   configurable = true)
  public String getPropertyName() {
    return getProperties().get("propertyName", StringUtils.EMPTY);
  }

  /**
   * Value to set the field to if no other values have been set through the request context.
   *
   * @return Value to set the field to if no other values have been set through the request context.
   */
  @KestrosProperty(description = "Value to set the field to if no other values have been set "
                                 + "through the request context.",
                   jcrPropertyName = "defaultValue",
                   defaultValue = "",
                   sampleValue = "",
                   configurable = true)
  public String getDefaultValue() {
    return getProperties().get("defaultValue", StringUtils.EMPTY);
  }

  /**
   * Whether the field should receive focus when loaded.
   *
   * @return Whether the field should recieve focus when loaded in the DOM.
   */
  @KestrosProperty(description =
                       "Whether this property should immediately come into focus when the "
                       + "form is rendered.",
                   defaultValue = "false",
                   sampleValue = "false",
                   jcrPropertyName = "autofocus",
                   configurable = true)
  public boolean isAutoFocus() {
    return getProperties().get("autofocus", Boolean.FALSE);
  }


  /**
   * Field validators that will check against the fields input.
   *
   * @param <T> extends BaseFormFieldValidator
   * @return Field validators that will check against the fields input.
   */
  @KestrosProperty(description = "Field validators that will check against the fields input.")
  public <T extends BaseFormFieldValidator> List<T> getFieldValidators() {
    try {
      BaseResource validatorsResource = getChildAsBaseResource("validators", this);
      return getChildrenAsClosestTypes(validatorsResource, modelFactory);
    } catch (ChildResourceNotFoundException e) {
      LOG.debug(e.getMessage());
    }
    return Collections.emptyList();
  }

  /**
   * Value to prepopulate the field with. Overrides any contextual value unless null.
   *
   * @return Value to prepopulate the field with.
   */
  @Nullable
  @KestrosProperty(description = "Value to prepopulate the field with. Overrides any contextual "
                                 + "value.",
                   configurable = true,
                   jcrPropertyName = "initialValue",
                   defaultValue = "null")
  public String getInitialValue() {
    return getProperty("initialValue", null);
  }

}
