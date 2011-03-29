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

/**
 * Unchecked Exception to mark uncaught-exceptions in threads
 *
 * @author $Author: giger $
 * @version $Revision: 281 $ $Date: 2011-01-04 21:15:27 +0100 (Tue, 04 Jan 2011) $
 */
public class UncheckedWSSecurityException extends RuntimeException {

    public UncheckedWSSecurityException(String message) {
        super(message);
    }

    public UncheckedWSSecurityException(String message, Throwable cause) {
        super(message, cause);
    }

    public UncheckedWSSecurityException(Throwable cause) {
        super(cause);
    }
}
