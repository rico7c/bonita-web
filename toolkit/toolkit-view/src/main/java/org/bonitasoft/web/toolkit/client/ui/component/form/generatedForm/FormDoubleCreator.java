/**
 * Copyright (C) 2013 BonitaSoft S.A.
 * BonitaSoft, 32 rue Gustave Eiffel - 38000 Grenoble
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2.0 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.bonitasoft.web.toolkit.client.ui.component.form.generatedForm;

import org.bonitasoft.web.toolkit.client.data.item.attribute.ItemAttribute;
import org.bonitasoft.web.toolkit.client.data.item.attribute.validator.IsNumericValidator;
import org.bonitasoft.web.toolkit.client.ui.JsId;
import org.bonitasoft.web.toolkit.client.ui.component.form.FormNode;
import org.bonitasoft.web.toolkit.client.ui.component.form.entry.Text;

/**
 * @author Nicolas Tith
 * 
 */
public class FormDoubleCreator implements FormNodeCreator {

    public FormNode create(ItemBinding item) {
        return create(item, ItemAttribute.MAX_LENGTH_DOUBLE);
    }

    public FormNode create(ItemBinding item, int maxLength) {
        Text text = new Text(new JsId(item.getAttributeName()), item.getLabel(), item.getTooltip(), item.getValue());
        text.addValidator(new IsNumericValidator());
        text.setMaxLength(maxLength);
        return text;
    }
}
