/**
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.swssf.ext;

import javax.xml.stream.events.XMLEvent;

/**
 * Parseable interface to parse and validate xml
 *
 * @author $Author: giger $
 * @version $Revision: 281 $ $Date: 2011-01-04 21:15:27 +0100 (Tue, 04 Jan 2011) $
 */
public interface Parseable {

    /**
     * @param xmlEvent The XMLEvent to parse
     * @return true when current Element is finished
     * @throws ParseException in the case of an unexpected element
     */
    public boolean parseXMLEvent(XMLEvent xmlEvent) throws ParseException;

    /**
     * Validates the XML-Object structure
     *
     * @throws ParseException thrown when the the object-structure is invalid
     */
    public void validate() throws ParseException;
}
