/**
 *      Index.java
 *
 *      Copyright (C) 2009 Guillaume Bouffard <guillaume.bouffard02@etu.unilim.fr>
 *      Copyright (C) 2009 Julien Boutet <julien.boutet@etu.unilim.fr>
 *      Copyright (C) 2012 Julien Iguchi-Cartigny <julien.cartigny@xlim.fr>
 *
 *      Xlim - UniversitĂŠ de Limoges
 *
 *      This program is free software; you can redistribute it and/or modify
 *      it under the terms of the GNU General Public License as published by
 *      the Free Software Foundation; either version 3 of the License, or
 *      any later version.
 *
 *      This program is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU General Public License for more details.
 *
 *      You should have received a copy of the GNU General Public License
 *      along with this program; if not, write to the Free Software
 *      Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 *      MA 02110-1301, USA.
 */

package fr.xlim.ssd.capmanipulator.library.bytecodereader;

/**
 * @author Guillaume Bouffard
 * @author Julien Boutet
 */
public class Index extends Argument {

    private Destination destination;

    public Index(byte[] value, short size, Destination destination) {
        super(value, size, false);
        this.destination = destination;
    }

    @Override
    public Object clone() {
        Argument a = (Argument) super.clone();
        return new Index(a.getValue(), a.getSize(), destination);
    }

    /**
     * @return the destination
     */
    public Destination getDestination() {
        return destination;
    }

    /**
     * @param destination the destination to set
     */
    public void setDestination(Destination destination) {
        this.destination = destination;
    }
}
