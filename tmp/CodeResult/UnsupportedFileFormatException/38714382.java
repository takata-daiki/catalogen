/*
 * CADOculus, 3D in the Web
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the MIT Licens as published at
 * http://opensource.org/licenses/mit-license.php
 *
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the MIT Licens
 * along with this program.  If not, see <* http://opensource.org/licenses/mit-license.php>
 *
 */
package de.cadoculus.conversion;

import java.util.Set;
import javax.jcr.Node;


/**
 * Thrown if an unsupported file format was found.
 *
 * @author  cz
 */
public class UnsupportedFileFormatException extends ConversionException {
    private final Set< Node > unsupportedNodes;

    /**
     * Constructs an instance of <code>ConversionException</code> with the specified detail message.
     *
     * @param  msg               the detail message.
     * @param  unsupportedNodes  a set containing the nodes where no file detection was possible
     */
    public UnsupportedFileFormatException( String msg, Set< Node > unsupportedNodes ) {
        super( msg );
        this.unsupportedNodes = unsupportedNodes;
    }

    /**
     * Get a set of nodes which were not detected
     *
     * @return
     */
    public Set< Node > getUnsupportedNodes() {
        return unsupportedNodes;
    }
}
