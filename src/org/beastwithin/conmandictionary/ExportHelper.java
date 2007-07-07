/*  ExportHelper.java: Code for exporting dictionary in other formats.
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

import java.io.*;
import javax.swing.*;


public abstract class ExportHelper {
	/**
	 * Exports the dictionaries in dictd (plain text) format.
	 * 
	 * Needs fileNameBase; will use [filenamebase].[language].txt
	 * for the two files.
	 * 
	 * @param fileNameBase the base file name.
	 */
	public static void exportAsDictd(String fileNameBase) {
		LanguagePanel lp = ConmanDictionary.getMainWindow().getLeftLanguagePanel();
		LanguagePanel rp = ConmanDictionary.getMainWindow().getRightLanguagePanel();
		String lFileName, rFileName;
		if(lp.getLanguage().compareTo(rp.getLanguage())==0) {
			// The names are same, so let's come up with something...
			lFileName = "left";
			rFileName = "right";
		} else {
			lFileName = lp.getLanguage();
			rFileName = rp.getLanguage();
		}
		File leftFile = new File(fileNameBase + "."+lFileName+".txt");
		File rightFile = new File(fileNameBase + "."+rFileName+".txt");
		try {
			PrintWriter lf = new PrintWriter(new BufferedWriter(new FileWriter(leftFile)));
			PrintWriter rf = new PrintWriter(new BufferedWriter(new FileWriter(rightFile)));
			
			for (Object o : lp.getEntryList().toArray()) {
				Entry e = (Entry) o;
				lf.println(e.toDictString());
			}
			lf.close();
			for (Object o : rp.getEntryList().toArray()) {
				Entry e = (Entry) o;
				rf.println(e.toDictString());
			}
			rf.close();
		} catch(IOException ioe) {
			JOptionPane.showMessageDialog(ConmanDictionary.getMainWindow(),
					"File error occurred when exporting the file:\n"+
					ioe.getMessage(),
					"Error exporting the file.", JOptionPane.ERROR_MESSAGE);
		}
	}
}
