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

package io.kestros.cms.forms.fields.textfield;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.testing.mock.sling.junit.SlingContext;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class TextfieldTest {

  @Rule
  public SlingContext context = new SlingContext();

  private Textfield textfield;

  private Resource resource;

  private Map<String, Object> properties = new HashMap<>();

  @Before
  public void setUp() throws Exception {
    context.addModelsForPackage("io.kestros");
  }

  @Test
  public void testGetPlaceholder() {
    properties.put("placeholder", "Placeholder");
    resource = context.create().resource("/textfield", properties);
    textfield = resource.adaptTo(Textfield.class);

    assertEquals("Placeholder", textfield.getPlaceholder());
  }
}