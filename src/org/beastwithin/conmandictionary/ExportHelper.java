package org.beastwithin.conmandictionary;

import java.io.*;
import javax.swing.*;


public abstract class ExportHelper {
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
