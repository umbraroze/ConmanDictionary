namespace DictionaryDocument;

public class Generators
{
    /*
     * NOTE: When changing the this, also generate the corresponding XML
     *       using DictTool's test-output command, and stick it in 
     *       DictionaryDocument.Tests/TestFiles/mock_document.xml
     */
    public static Dictionary GetMockDocument()
    {
        var d = new Dictionary();

        d.Definitions[0].Language = "Aybeeseean";
        var left = d.Definitions[0].Entries;
        d.Definitions[1].Language = "English";
        var right = d.Definitions[1].Entries;

        d.NotePad = "This is some random text for the notepad.";

        var wcverb = d.WordClasses.Find(x => x.Name.Equals("Verb"));
        var wcnoun = d.WordClasses.Find(x => x.Name.Equals("Noun"));
        var wcadj = d.WordClasses.Find(x => x.Name.Equals("Adjective"));

        left.Add(new Entry
        {
            Term = "foo",
            Definition = "to pity",
            WordClass = wcverb
        });
        left.Add(new Entry
        {
            Term = "bah",
            Definition = "bad sigh",
            WordClass = wcnoun
        });
        left.Add(new Entry
        {
            Term = "zzbaz",
            Definition = "annoying",
            WordClass = wcadj
        });
        right.Add(new Entry
        {
            Term = "pity",
            Definition = "foo",
            WordClass = wcverb
        });
        right.Add(new Entry
        {
            Term = "sigh",
            Definition = "bah (bad sigh)",
            WordClass = wcnoun
        });
        right.Add(new Entry
        {
            Term = "annoying",
            Definition = "zzbaz",
            WordClass = wcadj
        });

        return d;
    }
}

