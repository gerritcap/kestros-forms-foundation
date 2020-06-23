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

import static io.kestros.commons.structuredslingmodels.utils.SlingModelUtils.getAllDescendantsAsClosestType;
import static org.apache.sling.api.servlets.HttpConstants.METHOD_POST;

import io.kestros.cms.forms.fields.BaseFormField;
import io.kestros.cms.foundation.content.BaseComponent;
import io.kestros.commons.structuredslingmodels.BaseResource;
import io.kestros.commons.structuredslingmodels.annotation.KestrosModel;
import io.kestros.commons.structuredslingmodels.annotation.KestrosProperty;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.factory.ModelFactory;

/**
 * Baseline Kestros form model.
 */
@KestrosModel(validationService = BaseFormValidationService.class)
@Model(adaptables = Resource.class,
       resourceType = "kestros/components/forms/form")
public class BaseForm extends BaseComponent {

  @OSGiService
  private ModelFactory modelFactory;

  /**
   * Path to submit the form to.
   *
   * @return Path to submit the form to.
   */
  @KestrosProperty(description = "Path which the form submits to.",
                   configurable = true,
                   defaultValue = "",
                   sampleValue = "")
  public String getSubmitPath() {
    return getProperty("submitPath", StringUtils.EMPTY);
  }

  /**
   * HTTP Method used when submitting the form.
   *
   * @return HTTP Method used when submitting the form.
   */
  @KestrosProperty(description = "HTTP method the form uses to submit data.",
                   configurable = true,
                   defaultValue = "POST",
                   sampleValue = "POST")
  public String getMethod() {
    return getProperty("method", METHOD_POST);
  }

  /**
   * Generic error message to show if no other message is specified.
   *
   * @return Generic error message to show if no other message is specified.
   */
  @KestrosProperty(description = "Generic error message to show if no other message is specified.",
                   configurable = true,
                   defaultValue = "",
                   sampleValue = "")
  public String getDefaultErrorMessage() {
    return getProperty("defaultErrorMessage", StringUtils.EMPTY);
  }

  /**
   * All fields within the form, included nested within components.
   *
   * @param <T> extends BaseFormField.
   * @return All fields within the form, included nested within components.
   */
  @KestrosProperty(description = "All fields that live within the form.")
  public <T extends BaseFormField> List<T> getFields() {
    List<T> fields = new ArrayList<>();
    for (BaseResource descendantResource : getAllDescendantsAsClosestType(this, modelFactory)) {
      if (descendantResource instanceof BaseFormField) {
        fields.add((T) descendantResource);
      }
    }
    return fields;
  }
}