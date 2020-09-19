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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.kestros.cms.forms.validators.regexvalidator.RegexValidator;
import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.factory.ModelFactory;
import org.apache.sling.testing.mock.sling.junit.SlingContext;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class BaseFormFieldTest {

  @Rule
  public SlingContext context = new SlingContext();

  private ModelFactory modelFactory = mock(ModelFactory.class);

  private BaseFormField baseFormField;

  private Resource resource;

  private Map<String, Object> properties = new HashMap<>();

  private Map<String, Object> validatorProperties = new HashMap<>();

  @Before
  public void setUp() throws Exception {
    context.addModelsForPackage("io.kestros");
    context.registerService(ModelFactory.class, modelFactory);
  }

  @Test
  public void testGetLabel() {
    properties.put("label", "Label");
    resource = context.create().resource("/field", properties);
    baseFormField = resource.adaptTo(BaseFormField.class);

    assertEquals("Label", baseFormField.getLabel());
  }

  @Test
  public void testGetDescription() {
    properties.put("description", "Description");
    resource = context.create().resource("/field", properties);
    baseFormField = resource.adaptTo(BaseFormField.class);

    assertEquals("Description", baseFormField.getDescription());
  }

  @Test
  public void testIsRequired() {
    properties.put("required", "true");
    resource = context.create().resource("/field", properties);
    baseFormField = resource.adaptTo(BaseFormField.class);

    assertTrue(baseFormField.isRequired());
  }

  @Test
  public void testGetPropertyName() {
    properties.put("propertyName", "myPropertyName");
    resource = context.create().resource("/field", properties);
    baseFormField = resource.adaptTo(BaseFormField.class);

    assertEquals("myPropertyName", baseFormField.getPropertyName());
  }

  @Test
  public void testGetDefaultValue() {
    properties.put("defaultValue", "Default Value");
    resource = context.create().resource("/field", properties);
    baseFormField = resource.adaptTo(BaseFormField.class);

    assertEquals("Default Value", baseFormField.getDefaultValue());
  }

  @Test
  public void testIsAutoFocus() {
    properties.put("autofocus", "true");
    resource = context.create().resource("/field", properties);
    baseFormField = resource.adaptTo(BaseFormField.class);

    assertTrue(baseFormField.isAutoFocus());
  }

  @Test
  public void testGetFieldValidators() {
    validatorProperties.put("sling:resourceType", "kestros/cms/validators/regex-validator");
    when(modelFactory.getModelFromResource(any(Resource.class))).thenReturn(
        mock(RegexValidator.class));

    resource = context.create().resource("/field", properties);
    context.create().resource("/field/validators");
    context.create().resource("/field/validators/validator-1", validatorProperties);
    baseFormField = resource.adaptTo(BaseFormField.class);

    assertEquals(1, baseFormField.getFieldValidators().size());
  }

  @Test
  public void testGetFieldValidatorsWhenValidatorsResourceNotFound() {
    resource = context.create().resource("/field", properties);
    baseFormField = resource.adaptTo(BaseFormField.class);

    assertEquals(0, baseFormField.getFieldValidators().size());
  }

  @Test
  public void testGetProperties() {
  }

  @Test
  public void testGetChildFields() {
  }

  @Test
  public void testGetRootComponentType() {
  }

  @Test
  public void testGetRelativePath() {
  }


}