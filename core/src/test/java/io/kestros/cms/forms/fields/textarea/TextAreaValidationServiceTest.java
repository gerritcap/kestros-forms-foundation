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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;

import io.kestros.commons.validation.ModelValidationMessageType;
import io.kestros.commons.validation.models.ModelValidator;
import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.testing.mock.sling.junit.SlingContext;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class TextAreaValidationServiceTest {

  @Rule
  public SlingContext context = new SlingContext();

  private TextAreaValidationService validationService;

  private TextArea textArea;

  private Resource resource;

  private Map<String, Object> properties = new HashMap<>();

  @Before
  public void setUp() throws Exception {
    context.addModelsForPackage("io.kestros");
    validationService = spy(new TextAreaValidationService());
  }


  @Test
  public void testIsRowsPositiveNumber() {
    properties.put("rows", 2);
    resource = context.create().resource("/field", properties);
    textArea = resource.adaptTo(TextArea.class);

    ModelValidator validator = validationService.isRowsPositiveNumber();
    validator.setModel(textArea);

    assertTrue(validator.isValidCheck());
  }

  @Test
  public void testIsRowsPositiveNumberWhenNegative() {
    properties.put("rows", -2);
    resource = context.create().resource("/field", properties);
    textArea = resource.adaptTo(TextArea.class);

    ModelValidator validator = validationService.isRowsPositiveNumber();
    validator.setModel(textArea);

    assertFalse(validator.isValidCheck());
    assertEquals("Configured number of rows is positive.", validator.getMessage());
    assertEquals(ModelValidationMessageType.WARNING, validator.getType());
  }

  @Test
  public void testIsRowsPropertyValueIsInteger() {
    properties.put("rows", 2);
    resource = context.create().resource("/field", properties);
    textArea = resource.adaptTo(TextArea.class);

    ModelValidator validator = validationService.isRowsPositiveNumber();
    validator.setModel(textArea);

    assertTrue(validator.isValidCheck());
  }

  @Test
  public void testIsRowsPropertyValueIsIntegerString() {
    properties.put("rows", "2");
    resource = context.create().resource("/field", properties);
    textArea = resource.adaptTo(TextArea.class);

    ModelValidator validator = validationService.isRowsPropertyValueIsInteger();
    validator.setModel(textArea);

    assertTrue(validator.isValidCheck());
  }

  @Test
  public void testIsRowsPropertyValueIsIntegerWhenString() {
    properties.put("rows", "string value");
    resource = context.create().resource("/field", properties);
    textArea = resource.adaptTo(TextArea.class);

    ModelValidator validator = validationService.isRowsPropertyValueIsInteger();
    validator.setModel(textArea);

    assertFalse(validator.isValidCheck());
    assertEquals("Configured number of rows is an integer.", validator.getMessage());
    assertEquals(ModelValidationMessageType.WARNING, validator.getType());
  }
}