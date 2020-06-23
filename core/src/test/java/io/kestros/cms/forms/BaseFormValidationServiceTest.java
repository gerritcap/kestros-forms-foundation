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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

import io.kestros.commons.structuredslingmodels.validation.ModelValidationMessageType;
import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.testing.mock.sling.junit.SlingContext;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class BaseFormValidationServiceTest {

  @Rule
  public SlingContext context = new SlingContext();

  private BaseFormValidationService validationService;

  private BaseForm form;

  private Resource resource;

  private Map<String, Object> properties = new HashMap<>();

  private Map<String, Object> fieldProperties = new HashMap<>();

  @Before
  public void setUp() throws Exception {
    context.addModelsForPackage("io.kestros");
    fieldProperties.put("sling:resourceType", "kestros/components/forms/fields/textfield");
    validationService = spy(new BaseFormValidationService());
  }

  @Test
  public void testGetModel() {
    resource = context.create().resource("/form", properties);
    form = resource.adaptTo(BaseForm.class);
    doReturn(form).when(validationService).getGenericModel();

    assertNotNull(validationService.getModel());
    assertEquals(form, validationService.getModel());
  }

  @Test
  public void testRegisterBasicValidators() {
    resource = context.create().resource("/form", properties);
    form = resource.adaptTo(BaseForm.class);
    doReturn(form).when(validationService).getGenericModel();
    validationService.registerBasicValidators();

    assertEquals(4, validationService.getBasicValidators().size());

    assertEquals("Has at least one field.",
        validationService.getBasicValidators().get(0).getMessage());
    assertEquals(ModelValidationMessageType.ERROR,
        validationService.getBasicValidators().get(0).getType());

    assertEquals("Has submit path.", validationService.getBasicValidators().get(1).getMessage());
    assertEquals(ModelValidationMessageType.WARNING,
        validationService.getBasicValidators().get(1).getType());

    assertEquals("One of the following is true:",
        validationService.getBasicValidators().get(2).getMessage());
    assertEquals(ModelValidationMessageType.ERROR,
        validationService.getBasicValidators().get(2).getType());

    assertEquals("Has default error message.",
        validationService.getBasicValidators().get(3).getMessage());
    assertEquals(ModelValidationMessageType.WARNING,
        validationService.getBasicValidators().get(3).getType());
  }

  @Test
  public void testRegisterDetailedValidators() {
    resource = context.create().resource("/form", properties);
    form = resource.adaptTo(BaseForm.class);
    doReturn(form).when(validationService).getGenericModel();
    validationService.registerDetailedValidators();

    assertEquals(0, validationService.getDetailedValidators().size());
  }

  @Test
  public void testHasFieldsWhenHasChildFields() {
    resource = context.create().resource("/form", properties);
    context.create().resource("/form/field-1", fieldProperties);
    context.create().resource("/form/field-2", fieldProperties);
    context.create().resource("/form/field-3", fieldProperties);

    form = resource.adaptTo(BaseForm.class);
    doReturn(form).when(validationService).getGenericModel();

    assertEquals(3, form.getFields().size());
    assertTrue(validationService.hasFields().isValid());
  }

  @Test
  public void testHasFieldsWhenHasNoFields() {
    resource = context.create().resource("/form", properties);
    form = resource.adaptTo(BaseForm.class);
    doReturn(form).when(validationService).getGenericModel();

    assertFalse(validationService.hasFields().isValid());
  }

  @Test
  public void testHasSubmitPath() {
    properties.put("submitPath", "/path");
    resource = context.create().resource("/form", properties);

    form = resource.adaptTo(BaseForm.class);
    doReturn(form).when(validationService).getGenericModel();

    assertTrue(validationService.hasSubmitPath().isValid());
  }

  @Test
  public void testHasSubmitWhenMissing() {
    resource = context.create().resource("/form", properties);

    form = resource.adaptTo(BaseForm.class);
    doReturn(form).when(validationService).getGenericModel();

    assertFalse(validationService.hasSubmitPath().isValid());
    assertEquals("Has submit path.", validationService.hasSubmitPath().getMessage());
    assertEquals(ModelValidationMessageType.WARNING, validationService.hasSubmitPath().getType());
  }

  @Test
  public void testIsValidHttpMethod() {
    resource = context.create().resource("/form", properties);
    form = resource.adaptTo(BaseForm.class);
    doReturn(form).when(validationService).getGenericModel();

    assertEquals(3, validationService.isValidHttpMethod().getValidators().size());
    assertEquals("Submits using a valid HTTP method.",
        validationService.isValidHttpMethod().getBundleMessage());
  }

  @Test
  public void testIsValidHttpMethodWhenPost() {
    properties.put("method", "POST");
    resource = context.create().resource("/form", properties);
    form = resource.adaptTo(BaseForm.class);
    doReturn(form).when(validationService).getGenericModel();
    validationService.registerBasicValidators();

    assertTrue(validationService.isValidHttpMethod("POST").isValid());
    assertEquals("Submits using POST HTTP method.",
        validationService.isValidHttpMethod("POST").getMessage());
    assertEquals(ModelValidationMessageType.WARNING,
        validationService.isValidHttpMethod("POST").getType());
    assertFalse(validationService.isValidHttpMethod("PUT").isValid());
    assertFalse(validationService.isValidHttpMethod("DELETE").isValid());
  }

  @Test
  public void testIsValidHttpMethodWhenPut() {
    properties.put("method", "PUT");
    resource = context.create().resource("/form", properties);
    form = resource.adaptTo(BaseForm.class);
    doReturn(form).when(validationService).getGenericModel();
    validationService.registerBasicValidators();

    assertFalse(validationService.isValidHttpMethod("POST").isValid());
    assertTrue(validationService.isValidHttpMethod("PUT").isValid());
    assertFalse(validationService.isValidHttpMethod("DELETE").isValid());
  }

  @Test
  public void testIsValidHttpMethodWhenDelete() {
    properties.put("method", "DELETE");
    resource = context.create().resource("/form", properties);
    form = resource.adaptTo(BaseForm.class);
    doReturn(form).when(validationService).getGenericModel();
    validationService.registerBasicValidators();

    assertFalse(validationService.isValidHttpMethod("POST").isValid());
    assertFalse(validationService.isValidHttpMethod("PUT").isValid());
    assertTrue(validationService.isValidHttpMethod("DELETE").isValid());
  }

  @Test
  public void testIsValidHttpMethodWhenGet() {
    properties.put("method", "GET");
    resource = context.create().resource("/form", properties);
    form = resource.adaptTo(BaseForm.class);
    doReturn(form).when(validationService).getGenericModel();
    validationService.registerBasicValidators();

    assertFalse(validationService.isValidHttpMethod("POST").isValid());
    assertFalse(validationService.isValidHttpMethod("PUT").isValid());
    assertFalse(validationService.isValidHttpMethod("DELETE").isValid());
  }

  @Test
  public void testHasDefaultErrorMessage() {
    properties.put("defaultErrorMessage", "Error Message");
    resource = context.create().resource("/form", properties);

    form = resource.adaptTo(BaseForm.class);
    doReturn(form).when(validationService).getGenericModel();

    assertTrue(validationService.hasDefaultErrorMessage().isValid());
  }

  @Test
  public void testHasDefaultErrorMessageWhenMissing() {
    resource = context.create().resource("/form", properties);

    form = resource.adaptTo(BaseForm.class);
    doReturn(form).when(validationService).getGenericModel();

    assertFalse(validationService.hasDefaultErrorMessage().isValid());
    assertEquals("Has default error message.",
        validationService.hasDefaultErrorMessage().getMessage());
    assertEquals(ModelValidationMessageType.WARNING,
        validationService.hasDefaultErrorMessage().getType());
  }
}