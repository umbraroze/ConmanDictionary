namespace DictionaryDocument.Tests;

public class Generators
{
    public static DictionaryDocument.Dictionary GetMockDocument()
    {
        var d = new DictionaryDocument.Dictionary();

        d.Definitions[0].Language = "Aybeeseean";
        var left = d.Definitions[0].Entries;
        d.Definitions[1].Language = "English";
        var right = d.Definitions[1].Entries;

        d.NotePad = "This is some random text for the notepad.";

        var wcverb = d.WordClasses.Find(x => x.Name.Equals("Verb"));
        var wcnoun = d.WordClasses.Find(x => x.Name.Equals("Noun"));
        var wcadj = d.WordClasses.Find(x => x.Name.Equals("Adjective"));

        left.Add(new DictionaryDocument.Entry
        {
            Term = "foo",
            Definition = "to pity",
            WordClass = wcverb
        });
        left.Add(new DictionaryDocument.Entry
        {
            Term = "bah",
            Definition = "bad sigh",
            WordClass = wcnoun
        });
        left.Add(new DictionaryDocument.Entry
        {
            Term = "zzbaz",
            Definition = "annoying",
            WordClass = wcadj
        });
        right.Add(new DictionaryDocument.Entry
        {
            Term = "pity",
            Definition = "foo",
            WordClass = wcverb
        });
        right.Add(new DictionaryDocument.Entry
        {
            Term = "sigh",
            Definition = "bah (bad sigh)",
            WordClass = wcnoun
        });
        right.Add(new DictionaryDocument.Entry
        {
            Term = "annoying",
            Definition = "zzbaz",
            WordClass = wcadj
        });

        return d;
    }
}

