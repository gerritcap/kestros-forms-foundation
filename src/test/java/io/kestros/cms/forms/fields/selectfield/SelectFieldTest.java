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

package io.kestros.cms.forms.fields.selectfield;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.testing.mock.sling.junit.SlingContext;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class SelectFieldTest {

  @Rule
  public SlingContext context = new SlingContext();

  private SelectField selectField;

  private Resource resource;

  private Map<String, Object> properties = new HashMap<>();
  private Map<String, Object> optionProperties = new HashMap<>();

  @Before
  public void setUp() throws Exception {
    context.addModelsForPackage("io.kestros");
  }

  @Test
  public void testGetPlaceholder() {
    properties.put("placeholder", "Placeholder");
    resource = context.create().resource("/select", properties);

    selectField = resource.adaptTo(SelectField.class);

    assertEquals("Placeholder", selectField.getPlaceholder());
  }

  @Test
  public void testGetInitialValue() {
    resource = context.create().resource("/select", properties);
    context.create().resource("/select/options");

    optionProperties.put("text", "Option 1");
    optionProperties.put("value", "opt-1");
    context.create().resource("/select/options/option-1", optionProperties);

    optionProperties.put("text", "Option 2");
    optionProperties.put("value", "opt-2");
    context.create().resource("/select/options/option-2", optionProperties);

    optionProperties.put("text", "Option 3");
    optionProperties.put("value", "opt-3");
    context.create().resource("/select/options/option-3", optionProperties);

    selectField = resource.adaptTo(SelectField.class);

    assertEquals("opt-1", selectField.getDefaultValue());
  }

  @Test
  public void testGetInitialValueWhenOptionIsSelectedByDefault() {
    resource = context.create().resource("/select", properties);
    context.create().resource("/select/options");

    optionProperties.put("text", "Option 1");
    optionProperties.put("value", "opt-1");
    context.create().resource("/select/options/option-1", optionProperties);

    optionProperties.put("text", "Option 2");
    optionProperties.put("value", "opt-2");
    context.create().resource("/select/options/option-2", optionProperties);

    optionProperties.put("text", "Option 3");
    optionProperties.put("value", "opt-3");
    optionProperties.put("selected", "true");
    context.create().resource("/select/options/option-3", optionProperties);

    selectField = resource.adaptTo(SelectField.class);

    assertEquals("opt-3", selectField.getDefaultValue());
  }

  @Test
  public void testGetInitialValueWhenNotConfigured() {
    resource = context.create().resource("/select", properties);
    selectField = resource.adaptTo(SelectField.class);

    assertEquals("", selectField.getDefaultValue());
  }

  @Test
  public void testGetOptions() {
    resource = context.create().resource("/select", properties);
    context.create().resource("/select/options");

    optionProperties.put("text", "Option 1");
    optionProperties.put("value", "opt-1");
    context.create().resource("/select/options/option-1", optionProperties);

    optionProperties.put("text", "Option 2");
    optionProperties.put("value", "opt-2");
    context.create().resource("/select/options/option-2", optionProperties);

    optionProperties.put("text", "Option 3");
    optionProperties.put("value", "opt-3");
    optionProperties.put("selected", "true");
    context.create().resource("/select/options/option-3", optionProperties);

    selectField = resource.adaptTo(SelectField.class);

    assertEquals(3, selectField.getOptions().size());
    assertEquals("Option 1", selectField.getOptions().get(0).getText());
    assertEquals("Option 2", selectField.getOptions().get(1).getText());
    assertEquals("Option 3", selectField.getOptions().get(2).getText());
  }
}