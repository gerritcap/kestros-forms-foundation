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
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;

import io.kestros.commons.validation.ModelValidationMessageType;
import io.kestros.commons.validation.models.ModelValidator;
import io.kestros.commons.validation.models.ModelValidatorBundle;
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
  public void testHasFieldsWhenHasChildFields() {
    resource = context.create().resource("/form", properties);
    context.create().resource("/form/field-1", fieldProperties);
    context.create().resource("/form/field-2", fieldProperties);
    context.create().resource("/form/field-3", fieldProperties);

    form = resource.adaptTo(BaseForm.class);

    ModelValidator validator = validationService.hasFields();
    validator.setModel(form);

    assertEquals(3, form.getFields().size());
    assertTrue(validator.isValidCheck());
  }

  @Test
  public void testHasFieldsWhenHasNoFields() {
    resource = context.create().resource("/form", properties);
    form = resource.adaptTo(BaseForm.class);

    ModelValidator validator = validationService.hasFields();
    validator.setModel(form);

    assertFalse(validator.isValidCheck());
  }

  @Test
  public void testHasSubmitPath() {
    properties.put("submitPath", "/path");
    resource = context.create().resource("/form", properties);

    form = resource.adaptTo(BaseForm.class);

    ModelValidator validator = validationService.hasSubmitPath();
    validator.setModel(form);

    assertTrue(validator.isValidCheck());
  }

  @Test
  public void testHasSubmitWhenMissing() {
    resource = context.create().resource("/form", properties);

    form = resource.adaptTo(BaseForm.class);

    ModelValidator validator = validationService.hasSubmitPath();
    validator.setModel(form);

    assertFalse(validator.isValidCheck());
    assertEquals("Has submit path.", validator.getMessage());
    assertEquals(ModelValidationMessageType.WARNING, validator.getType());
  }

  @Test
  public void testIsValidHttpMethod() {
    resource = context.create().resource("/form", properties);
    form = resource.adaptTo(BaseForm.class);

    ModelValidatorBundle bundle = validationService.isValidHttpMethod();
    bundle.setModel(form);

    assertEquals(3, bundle.getValidators().size());
    assertEquals("Submits using a valid HTTP method.", bundle.getMessage());
  }

  @Test
  public void testIsValidHttpMethodWhenPost() {
    properties.put("method", "POST");
    resource = context.create().resource("/form", properties);
    form = resource.adaptTo(BaseForm.class);

    ModelValidator bundle = validationService.isValidHttpMethod();
    bundle.setModel(form);
    ModelValidator bundlePost = validationService.isValidHttpMethod("POST");
    bundlePost.setModel(form);
    ModelValidator bundlePut = validationService.isValidHttpMethod("PUT");
    bundlePut.setModel(form);
    ModelValidator bundleDelete = validationService.isValidHttpMethod("DELETE");
    bundleDelete.setModel(form);

    assertTrue(bundle.isValidCheck());
    assertEquals("Submits using POST HTTP method.", bundlePost.getMessage());
    assertEquals(ModelValidationMessageType.WARNING, bundlePost.getType());
    assertFalse(bundlePut.isValidCheck());
    assertFalse(bundleDelete.isValidCheck());
  }

  @Test
  public void testIsValidHttpMethodWhenPut() {
    properties.put("method", "PUT");
    resource = context.create().resource("/form", properties);
    form = resource.adaptTo(BaseForm.class);

    ModelValidator bundlePost = validationService.isValidHttpMethod("POST");
    bundlePost.setModel(form);
    ModelValidator bundlePut = validationService.isValidHttpMethod("PUT");
    bundlePut.setModel(form);
    ModelValidator bundleDelete = validationService.isValidHttpMethod("DELETE");
    bundleDelete.setModel(form);

    assertFalse(bundlePost.isValidCheck());
    assertTrue(bundlePut.isValidCheck());
    assertFalse(bundleDelete.isValidCheck());
  }

  @Test
  public void testIsValidHttpMethodWhenDelete() {
    properties.put("method", "DELETE");
    resource = context.create().resource("/form", properties);
    form = resource.adaptTo(BaseForm.class);

    ModelValidator bundlePost = validationService.isValidHttpMethod("POST");
    bundlePost.setModel(form);
    ModelValidator bundlePut = validationService.isValidHttpMethod("PUT");
    bundlePut.setModel(form);
    ModelValidator bundleDelete = validationService.isValidHttpMethod("DELETE");
    bundleDelete.setModel(form);

    assertFalse(bundlePost.isValidCheck());
    assertFalse(bundlePut.isValidCheck());
    assertTrue(bundleDelete.isValidCheck());
  }

  @Test
  public void testIsValidHttpMethodWhenGet() {
    properties.put("method", "GET");
    resource = context.create().resource("/form", properties);
    form = resource.adaptTo(BaseForm.class);

    ModelValidator bundlePost = validationService.isValidHttpMethod("POST");
    bundlePost.setModel(form);
    ModelValidator bundlePut = validationService.isValidHttpMethod("PUT");
    bundlePut.setModel(form);
    ModelValidator bundleDelete = validationService.isValidHttpMethod("DELETE");
    bundleDelete.setModel(form);

    assertFalse(bundlePost.isValidCheck());
    assertFalse(bundlePut.isValidCheck());
    assertFalse(bundleDelete.isValidCheck());
  }

  @Test
  public void testHasDefaultErrorMessage() {
    properties.put("defaultErrorMessage", "Error Message");
    resource = context.create().resource("/form", properties);

    form = resource.adaptTo(BaseForm.class);

    ModelValidator validator = validationService.hasDefaultErrorMessage();
    validator.setModel(form);

    assertTrue(validator.isValidCheck());
  }

  @Test
  public void testHasDefaultErrorMessageWhenMissing() {
    resource = context.create().resource("/form", properties);

    form = resource.adaptTo(BaseForm.class);

    ModelValidator validator = validationService.hasDefaultErrorMessage();
    validator.setModel(form);

    assertFalse(validator.isValidCheck());
    assertEquals("Has default error message.", validator.getMessage());
    assertEquals(ModelValidationMessageType.WARNING, validator.getType());
  }
}