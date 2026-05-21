/*  CommandLineImporter.java: Imports simple textual material to a dictx dictionary.
 *
 *  Conman's Dictionary, a dictionary application for conlang makers.
 *  Copyright © 2006,2007,2008,2009,2010,2011 Urpo Lankinen.
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

import org.beastwithin.conmandictionary.document.Entry;
import org.beastwithin.conmandictionary.document.Dictionary;
import org.beastwithin.conmandictionary.document.WordClass;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.xml.bind.JAXBException;

/**
 * The command line importer parses new entries in textual format
 * and either appends them to an existing dictx database, or
 * creates a new bare-bones database to house the information.
 *
 * @author wwwwolf
 */
public class CommandLineImporter {
    private static enum ListSide {LEFT, RIGHT};
    private static enum Reading {COMMENTS, LISTSIDE, HEADWORD, WORDCLASS, DEFINITION};

    /**
     * The main program.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        File newEntriesFile;
        File outputFile;
        Dictionary dictionary = new Dictionary();

        // === PARSE & SANITYCHECK ARGUMENTS ===

        // Okay, let's see our arguments. Two or three?
        if(args.length < 2 || args.length > 3) {
            System.err.println("Usage: CommandLineImporter newentries.txt output.dictx [dictionary.dictx]");
            System.exit(1);
        }
        // First argument is file name. Does it exist? Is it readable at all?
        newEntriesFile = new File(args[0]);
        if(!newEntriesFile.exists()) {
            System.err.println(newEntriesFile + " doesn't exist.");
            System.exit(1);
        }
        if(!newEntriesFile.canRead()) {
            System.err.println(newEntriesFile + " can't be read.");
            System.exit(1);
        }
        outputFile = new File(args[1]);
        if(outputFile.exists()) {
            System.err.println(outputFile + " already exists.");
            System.exit(1);
        }

        // === LOAD/CREATE DICTIONARY  ===

        // Let's figure out where our dictionary is.
        if(args.length == 3) {
            // We have a third argument, so let's load the file indicated.
            File existingDictionaryFile = new File(args[2]);
            try {
                dictionary = Dictionary.loadDocument(existingDictionaryFile);
            } catch (JAXBException jaxbe) {
                System.err.println("XML formatting error when loading " + existingDictionaryFile + "; malformed file?");
                System.exit(1);
            } catch (IOException ioe) {
                System.err.println("File read error when loading " + existingDictionaryFile + ".");
                System.exit(1);
            }
        } else {
            // A brand new one!
            dictionary = new Dictionary();
            // Add some very bare-bones stuff in the dictionary.
            dictionary.ensureBareBonesWordClasses();
        }

        // === PARSE INPUT FILE ===
        try {
            Reading currentPhase = Reading.COMMENTS;
            ListSide currentListSide = null;
            Entry newEntry = null;

            BufferedReader newEntries =
                    new BufferedReader(new FileReader(newEntriesFile));
            String readLine = "";
            StringBuilder def = new StringBuilder();
            while(readLine != null) {
                readLine = newEntries.readLine();
                if(readLine == null)
                    break;
                switch(currentPhase) {
                    case COMMENTS:
                        // Comments are encountered only in the beginning of
                        // the file. Will stay in comments mode until anything
                        // is hit.
                        if(readLine.equals("A") || readLine.equals("B"))
                            currentPhase = Reading.LISTSIDE;
                        // fall through
                    case LISTSIDE:
                        // Interpret list side code.
                        if (readLine.equals("A")) {
                            currentListSide = ListSide.LEFT;
                        } else if(readLine.equals("B"))  {
                            currentListSide = ListSide.RIGHT;
                        } else {
                            System.err.println("Unknown list side " + readLine);
                            System.exit(1);
                        }
                        // First line of the definition, so we initialise
                        // a brand new entry.
                        newEntry = new Entry();
                        // We're going to the headword phase next.
                        currentPhase = Reading.HEADWORD;
                        break;
                    case HEADWORD:
                        // Reading the headword is quite straightforward.
                        newEntry.setTerm(readLine);
                        currentPhase = Reading.WORDCLASS;
                        break;
                    case WORDCLASS:
                        WordClass c = dictionary.getWordClassWithAbbreviation(readLine);
                        if(c != null) {
                            newEntry.setWordClass(c);
                        } else {
                            System.err.println("Unknown word class " + readLine);
                            System.exit(1);
                        }
                        currentPhase = Reading.DEFINITION;
                        break;
                    case DEFINITION:
                        // The definition is a multi-line string, termminated
                        // with an empty line.
                        if(!readLine.equals("")) {
                            def.append(readLine);
                            def.append("\n");
                        } else {
                            // Ended!
                            // Set the definition...
                            newEntry.setDefinition(def.toString());
                            // Put the definition to the correct list side
                            if(currentListSide == ListSide.LEFT) {
                                dictionary.getDefinitions().get(0).add(newEntry);
                            } else if(currentListSide == ListSide.RIGHT) {
                                dictionary.getDefinitions().get(1).add(newEntry);
                            } else {
                                System.err.println("Unable to interpret list side (internal error)");
                                System.exit(1);
                            }
                            // Blank slate
                            def = new StringBuilder();
                            currentPhase = Reading.LISTSIDE;
                        }
                        break;
                }
            }
        } catch (IOException ioe) {
            System.err.println("File read error when reading " + newEntriesFile + ".");
            System.exit(1);
        }

        // === TIDY UP ===

        // Sort the lists.
        dictionary.getDefinitions().get(0).sort();
        dictionary.getDefinitions().get(1).sort();

        // === SAVE OUTPUT FILE ===
        try {
            dictionary.save(outputFile);
        } catch (JAXBException jaxbe) {
            System.err.println("XML generation error when saving to "+outputFile);
            System.exit(1);
        } catch (IOException ioe) {
            System.err.println("File writing error when saving to "+outputFile);
            System.exit(1);
        }
    }
}
