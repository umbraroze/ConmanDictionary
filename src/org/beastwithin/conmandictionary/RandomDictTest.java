package org.beastwithin.conmandictionary;

public class RandomDictTest {
    public static void main(String args[]) {
        Dictionary d = new Dictionary();
        WordClass n = new WordClass("Noun","n.");
        WordClass v = new WordClass("Verb","v.");
        d.getWordClasses().getClasses().add(n);
        d.getWordClasses().getClasses().add(v);
        d.setCurrentFile(new java.io.File("/tmp/wordclasstest.xml"));
        try {
            d.saveDocument();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
