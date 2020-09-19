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

package io.kestros.cms.forms.validators.regexvalidator;

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

public class RegexValidatorValidationServiceTest {

  @Rule
  public SlingContext context = new SlingContext();

  private RegexValidatorValidationService validationService;

  private RegexValidator regexValidator;

  private Resource resource;

  private Map<String, Object> properties = new HashMap<>();

  @Before
  public void setUp() throws Exception {
    context.addModelsForPackage("io.kestros");
    validationService = spy(new RegexValidatorValidationService());
  }

  @Test
  public void testGetModel() {
    resource = context.create().resource("/validator", properties);
    regexValidator = resource.adaptTo(RegexValidator.class);
    doReturn(regexValidator).when(validationService).getGenericModel();

    assertNotNull(validationService.getModel());
    assertEquals(regexValidator, validationService.getModel());
  }

  @Test
  public void testRegisterBasicValidators() {
    resource = context.create().resource("/validator", properties);
    regexValidator = resource.adaptTo(RegexValidator.class);
    doReturn(regexValidator).when(validationService).getGenericModel();
    validationService.registerBasicValidators();

    assertEquals(2, validationService.getBasicValidators().size());
    assertEquals("Has a configured error message.",
        validationService.getBasicValidators().get(0).getMessage());
    assertEquals("Has a regex pattern configured.",
        validationService.getBasicValidators().get(1).getMessage());
  }

  @Test
  public void testHasPattern() {
    properties.put("pattern", "***");
    resource = context.create().resource("/validator", properties);
    regexValidator = resource.adaptTo(RegexValidator.class);
    doReturn(regexValidator).when(validationService).getGenericModel();
    validationService.registerBasicValidators();

    assertTrue(validationService.hasPattern().isValid());
  }

  @Test
  public void testHasPatternWhenNoPattern() {
    resource = context.create().resource("/validator", properties);
    regexValidator = resource.adaptTo(RegexValidator.class);
    doReturn(regexValidator).when(validationService).getGenericModel();
    validationService.registerBasicValidators();

    assertFalse(validationService.hasPattern().isValid());
    assertEquals("Has a regex pattern configured.", validationService.hasPattern().getMessage());
    assertEquals(ModelValidationMessageType.ERROR, validationService.hasPattern().getType());
  }
}