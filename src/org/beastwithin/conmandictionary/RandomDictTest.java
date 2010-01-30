package org.beastwithin.conmandictionary;

public class RandomDictTest {
    public static void main(String args[]) {
        Dictionary d = new Dictionary();
        WordClass n = new WordClass("Noun","n.");
        WordClass v = new WordClass("Verb","v.");
        d.getWordClasses().add(n);
        d.getWordClasses().add(v);
        try {
            d.save("/tmp/wordclasstest.xml");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
