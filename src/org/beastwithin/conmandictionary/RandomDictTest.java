package org.beastwithin.conmandictionary;

public class RandomDictTest {
    public static void main(String args[]) {
        Dictionary d = new Dictionary();
        WordClass n = new WordClass("Noun","n.");
        WordClass v = new WordClass("Verb","v.");
        WordClass m = new WordClass("Mystery","m.","A very mysterious word class.");
        d.getWordClasses().add(n);
        d.getWordClasses().add(v);
        d.getWordClasses().add(m);
        Entry e1 = new Entry("foo","A person who knows nothing.",false,n);
        Entry e2 = new Entry("pity","Activity which foos (q.v.) end up receiving",false,v);
        Entry e3 = new Entry("bar","An epic weapon of ultimate smackdown",false,n);
        Entry e4 = new Entry("zplepb","This isn't supposed to be on the list, or something!");
        Entry e5 = new Entry("grrlubub","Your guess is as good or mine, even if it's documented",false,m);
        d.getDefinitions().get(0).add(e1);
        d.getDefinitions().get(0).add(e2);
        d.getDefinitions().get(0).add(e3);
        d.getDefinitions().get(1).add(e4);
        d.getDefinitions().get(1).add(e5);
        try {
            d.save("/tmp/wordclasstest.xml");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
