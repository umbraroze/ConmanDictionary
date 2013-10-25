/*  OptionalBooleanAdapter.java: For not saving boolean attributes when they're false.
 * 
 *  Conman's Dictionary, a dictionary application for conlang makers.
 *  Copyright (C) 2006,2007,2008,2009,2010  Urpo Lankinen
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *  
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.beastwithin.conmandictionary.ui;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * This is used with @XmlJavaTypeAdapter to not saving boolean attributes
 * when they're false.
 * 
 * @author wwwwolf
 */
public class OptionalBooleanAdapter extends XmlAdapter<String, Boolean> {
    public Boolean unmarshal(String val) throws Exception {
        if(val.equalsIgnoreCase("true"))
            return true;
        if(val.equalsIgnoreCase("false"))
            return false;
        throw new JAXBException("Invalid boolean value");
    }
    public String marshal(Boolean val) throws Exception {
        if(val)
            return "true";
        else
            return null; // Falses are nulls.
    }
}
