/*  LanguagePanel.java: Dictionary list and entry editor panel in main window.
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

import java.util.Vector;
import javax.swing.DefaultComboBoxModel;

/*
 * Credit for original implementation: Peter Lang at stackoverflow
 * http://stackoverflow.com/questions/2175600/swing-combobox-with-the-choice-of-none-of-the-below
 */
public class ComboBoxModelWithNullChoice extends DefaultComboBoxModel {

    public final static String NULL_ELEMENT = "(None)";

    public ComboBoxModelWithNullChoice(Vector v) {
        super(v);
    }

    @Override
    public int getSize() {
        return super.getSize() + 1;
    }

    @Override
    public Object getElementAt(int index) {
        if (index == 0) {
            return NULL_ELEMENT;
        }
        return super.getElementAt(index - 1);
    }
}
