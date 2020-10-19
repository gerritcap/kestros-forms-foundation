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

public class BaseFormFieldValidationServiceTest {

  @Rule
  public SlingContext context = new SlingContext();

  private BaseFormFieldValidationService validationService;

  private BaseFormField field;

  private Resource resource;

  private Map<String, Object> properties = new HashMap<>();

  @Before
  public void setUp() throws Exception {
    context.addModelsForPackage("io.kestros");
    validationService = spy(new BaseFormFieldValidationService());
  }


  @Test
  public void testHasLabel() {
    properties.put("label", "Label");
    resource = context.create().resource("/field", properties);
    field = resource.adaptTo(BaseFormField.class);
    ModelValidator validator = validationService.hasLabel();
    validator.setModel(field);

    assertTrue(validator.isValidCheck());
  }

  @Test
  public void testHasLabelWhenLabelNotConfigured() {
    resource = context.create().resource("/field", properties);
    field = resource.adaptTo(BaseFormField.class);
    ModelValidator validator = validationService.hasLabel();
    validator.setModel(field);

    assertFalse(validationService.hasLabel().isValid());
    assertEquals("Has a configured label.", validationService.hasLabel().getMessage());
    assertEquals(ModelValidationMessageType.WARNING, validationService.hasLabel().getType());
  }
}