/*  WordClass.java: Bean for word classes.
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

package org.beastwithin.conmandictionary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.ListModel;
import javax.swing.event.*;
import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "", propOrder = { "classes" })
@XmlRootElement(name = "wordclasses")
public class WordClassList implements ListModel {

    @XmlElement(name="class")
    private List<WordClass> classes;
    
    @XmlTransient
    private ArrayList<ListDataListener> listDataListeners = new ArrayList<ListDataListener>();
    
    public WordClassList() {
        classes = Collections.synchronizedList(new ArrayList<WordClass>());
    }
    
    public List<WordClass> getClasses() {
        return classes;
    }

    public void setClasses(List<WordClass> classes) {
        this.classes = classes;
    }    

    public void addListDataListener(ListDataListener l) {
        listDataListeners.add(l);
    }

    public void removeListDataListener(ListDataListener l) {
        // TODO Auto-generated method stub
        listDataListeners.remove(l);
    }

    public Object getElementAt(int n) {
        return classes.get(n);
    }

    public int getSize() {
        return classes.size();
    }
}
