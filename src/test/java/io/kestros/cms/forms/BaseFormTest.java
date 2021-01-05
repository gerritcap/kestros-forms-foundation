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

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.testing.mock.sling.junit.SlingContext;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class BaseFormTest {

  @Rule
  public SlingContext context = new SlingContext();

  private BaseForm baseForm;

  private Resource resource;
  private Map<String, Object> properties = new HashMap<>();
  private Map<String, Object> containerProperties = new HashMap<>();
  private Map<String, Object> fieldProperties = new HashMap<>();
  private Map<String, Object> componentProperties = new HashMap<>();

  @Before
  public void setUp() throws Exception {
    context.addModelsForPackage("io.kestros");

    fieldProperties.put("sling:resourceType", "kestros/components/forms/fields/textfield");
    componentProperties.put("sling:resourceType", "kestros/commons/components/text");
  }

  @Test
  public void testGetSubmitPath() {
    properties.put("submitPath", "submit-path");
    resource = context.create().resource("/form", properties);

    baseForm = resource.adaptTo(BaseForm.class);

    assertEquals("submit-path", baseForm.getSubmitPath());
  }

  @Test
  public void testGetMethod() {
    properties.put("method", "method");
    resource = context.create().resource("/form", properties);

    baseForm = resource.adaptTo(BaseForm.class);

    assertEquals("method", baseForm.getMethod());
  }

  @Test
  public void testGetDefaultErrorMessage() {
    properties.put("defaultErrorMessage", "error message");
    resource = context.create().resource("/form", properties);

    baseForm = resource.adaptTo(BaseForm.class);

    assertEquals("error message", baseForm.getDefaultErrorMessage());
  }

  @Test
  public void testFieldsWhenHasChildren() {
    resource = context.create().resource("/form", properties);
    context.create().resource("/form/field-1", fieldProperties);
    context.create().resource("/form/field-2", fieldProperties);
    context.create().resource("/form/field-3", fieldProperties);

    baseForm = resource.adaptTo(BaseForm.class);

    assertEquals(3, baseForm.getFields().size());
  }

  @Test
  public void testFieldsWhenHasNonFieldComponentChildren() {
    resource = context.create().resource("/form", properties);
    context.create().resource("/form/component-1", componentProperties);
    context.create().resource("/form/field-1", fieldProperties);
    context.create().resource("/form/component-2", componentProperties);
    context.create().resource("/form/field-2", fieldProperties);
    context.create().resource("/form/component-3", componentProperties);
    context.create().resource("/form/field-3", fieldProperties);

    baseForm = resource.adaptTo(BaseForm.class);

    assertEquals(3, baseForm.getFields().size());
  }

  @Test
  public void testFieldsWhenNestedInContainer() {
    resource = context.create().resource("/form", properties);

    context.create().resource("/form/container-1", containerProperties);
    context.create().resource("/form/container-1/field-1", fieldProperties);
    context.create().resource("/form/container-2", containerProperties);
    context.create().resource("/form/container-2/field-2", fieldProperties);
    context.create().resource("/form/container-3", containerProperties);
    context.create().resource("/form/container-3/field-3", fieldProperties);

    baseForm = resource.adaptTo(BaseForm.class);

    assertEquals(3, baseForm.getFields().size());
  }
}