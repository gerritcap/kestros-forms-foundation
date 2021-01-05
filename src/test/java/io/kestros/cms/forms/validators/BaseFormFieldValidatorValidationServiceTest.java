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

package io.kestros.cms.forms.validators;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

import io.kestros.cms.forms.validators.regexvalidator.RegexValidator;
import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.testing.mock.sling.junit.SlingContext;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class BaseFormFieldValidatorValidationServiceTest {

  @Rule
  public SlingContext context = new SlingContext();

  private BaseFormFieldValidatorValidationService validationService;

  private BaseFormFieldValidator fieldValidator;

  private Resource resource;

  private Map<String, Object> properties = new HashMap<>();

  @Before
  public void setUp() throws Exception {
    context.addModelsForPackage("io.kestros");
    validationService = spy(new BaseFormFieldValidatorValidationService());
  }


//
//  @Test
//  public void testRegisterBasicValidators() {
//    resource = context.create().resource("/validator", properties);
//    fieldValidator = resource.adaptTo(RegexValidator.class);
//    doReturn(fieldValidator).when(validationService).getGenericModel();
//    validationService.registerBasicValidators();
//
//    assertEquals(1, validationService.getBasicValidators().size());
//  }
//
//  @Test
//  public void testRegisterDetailedValidators() {
//    resource = context.create().resource("/validator", properties);
//    fieldValidator = resource.adaptTo(RegexValidator.class);
//    doReturn(fieldValidator).when(validationService).getGenericModel();
//    validationService.registerDetailedValidators();
//
//    assertEquals(0, validationService.getDetailedValidators().size());
//  }
//
//  @Test
//  public void testHasMessage() {
//    properties.put("message", "Message");
//    resource = context.create().resource("/validator", properties);
//    fieldValidator = resource.adaptTo(RegexValidator.class);
//    doReturn(fieldValidator).when(validationService).getGenericModel();
//    validationService.registerBasicValidators();
//
//    assertTrue(validationService.hasMessage().isValid());
//  }
//
//  @Test
//  public void testHasMessageWhenNoMessageConfigured() {
//    resource = context.create().resource("/validator", properties);
//    fieldValidator = resource.adaptTo(RegexValidator.class);
//    doReturn(fieldValidator).when(validationService).getGenericModel();
//    validationService.registerBasicValidators();
//
//    assertFalse(validationService.hasMessage().isValid());
//    assertEquals("Has a configured error message.", validationService.hasMessage().getMessage());
//    assertEquals(ModelValidationMessageType.ERROR, validationService.hasMessage().getType());
//  }
}