/*  EntryList.java: For storing entries in a dictionary.
 * 
 *  Conman's Dictionary, a dictionary application for conlang makers.
 *  Copyright (C) 2006,2007  Urpo Lankinen
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

import javax.swing.*;
import javax.swing.event.*;

import java.util.*;
import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "", propOrder = {
    "entries"
})
@XmlRootElement(name="definitions")
public class EntryList implements ListModel {
	
	@XmlElement(name="entry")
	private List<Entry> entries;
	private ArrayList<ListDataListener> listDataListeners = new ArrayList<ListDataListener>();
	private void notifyAddition(int start, int end) {
		ListDataEvent e = new ListDataEvent(this,ListDataEvent.INTERVAL_ADDED,start,end);
		for(ListDataListener l : listDataListeners) {
			l.intervalAdded(e);
		}
	}
	private void notifyRemoval(int start, int end) {
		ListDataEvent e = new ListDataEvent(this,ListDataEvent.INTERVAL_REMOVED,start,end);
		for(ListDataListener l : listDataListeners) {
			l.intervalRemoved(e);
		}
	}
	/**
	 * Manual call for notifying all ListDataListeners that the contents have to be reloaded.
	 */
	public void refresh() {
		ListDataEvent e = new ListDataEvent(this,ListDataEvent.CONTENTS_CHANGED,0,entries.size()-1);
		for(ListDataListener l : listDataListeners) {
			l.contentsChanged(e);
		}		
	}
	
	public EntryList() {
		entries = Collections.synchronizedList(new ArrayList<Entry>());
	}
	public void add(Entry e) {
		entries.add(e);
		int idx = entries.size()-1;
		notifyAddition(idx,idx);
	}
	public void remove(int index) {
		entries.remove(index);
		notifyRemoval(index,index);
	}
	public void clear() {
		int idx = entries.size()-1;
		if(idx < 0)
			idx = 0;
		entries.clear();
		notifyRemoval(0,idx);
	}
	public Entry get(int index) {
		return entries.get(index);
	}
	public int size() {
		return entries.size();
	}
	public Object[] toArray() {
		return entries.toArray();
	}
	/**
	 * Empties this list, then shallow-copies contents from
	 * the pointed list to this list.
	 *  
	 * @param source List the contents are copied from.
	 */
	public void replicateContentsFrom(final EntryList source) {
		entries.clear();
		entries.addAll(source.getEntries());
		language = source.getLanguage(); 
		refresh();
	}
	
	@Override
	public void addListDataListener(ListDataListener l) {
		listDataListeners.add(l);
	}

	@Override
	public Object getElementAt(int index) {
		// TODO Auto-generated method stub
		return entries.get(index);
	}

	@Override
	public int getSize() { return entries.size(); }

	@Override
	public void removeListDataListener(ListDataListener l) {
		// TODO Auto-generated method stub
		listDataListeners.remove(l);
	}

	@XmlAttribute
	protected String language = "";
	
	public List<Entry> getEntries() {
		if(entries == null)
			entries = Collections.synchronizedList(new ArrayList<Entry>());
		return entries;
	}
	
	public void setLanguage(String title) {
		this.language = title;
	}
	public String getLanguage() {
		return this.language;
	}
	
	// TODO: This is a somewhat ugly way to sort. Is there a more
	// elegant way to do this that didn't involve copying the array?
	public void sort() {		
		Object a[] = entries.toArray();
		Arrays.sort(a);
		entries.clear();
		for(int i = 0; i < a.length; i++) {
			entries.add((Entry)a[i]);
		}
		refresh();
	}
	
	public Entry search(String term) {
		// FIXME: Linear search should be funny enough in this case,
		// since the list is kept sorted and reasonably small...
		
		for(Entry e : entries) {
			if(e.getTerm().contains(term)) 
				return e;
		}
		return null;
	}
	
	@Override
	public String toString() {
		StringBuffer s = new StringBuffer();
		s.append("\n\nLanguage: "+this.language+'\n');
		s.append("List:\n");
		for(Entry e : entries) {
			s.append(e.toDictString());
		}
		return s.toString();
	}
}
