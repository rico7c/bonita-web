/**
 * Copyright (C) 2011 BonitaSoft S.A.
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
package org.bonitasoft.web.toolkit.client.data.item.attribute.validator;

import static org.bonitasoft.web.toolkit.client.common.i18n.AbstractI18n._;

/**
 * @author Séverin Moussel
 * 
 */
public class NumericMoreThanValidator extends AbstractNumericComparisonValidator {

    public NumericMoreThanValidator(final String secondAttributeName) {
        super(secondAttributeName);
    }

    @Override
    protected void _check(final Double attributeValue, final Double secondAttributeValue) {
        if (attributeValue.compareTo(secondAttributeValue) <= 0) {
            addError(_("%attribute% must be more than %secondAttribute%"));
        }
    }

}
