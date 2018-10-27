/*
 * 
 * This application exists mostly to try out various concepts during the development
 * without the overhead of the main application. In other words, I don't know a thing
 * about C#, I'm just fooling around here, this is not the real app, please go away.
 *
 */

using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Xml.Serialization;

namespace TryConcept
{
    class Program
    {
        static void SerializeDictionary(DictionaryDocument.Dictionary dictionary, string fileName)
        {
            var ser = new XmlSerializer(typeof(DictionaryDocument.Dictionary));
            var serout = new StreamWriter(fileName);
            ser.Serialize(serout, dictionary);
            serout.Close();
        }

        static void Main(string[] args)
        {
            Console.WriteLine("Start of the app");

            var d = new DictionaryDocument.Dictionary();

            Console.WriteLine("Fiddling.");

            d.NotePad = "This is some random text for the notepad.";

            d.Definitions[0].Language = "Aybeeseean";
            var left = d.Definitions[0].Entries;
            d.Definitions[1].Language = "English";
            var right = d.Definitions[1].Entries;

            var wcverb = d.WordClasses.Find(x => x.Name.Equals("Verb"));
            var wcnoun = d.WordClasses.Find(x => x.Name.Equals("Noun"));
            var wcadj = d.WordClasses.Find(x => x.Name.Equals("Adjective"));

            left.Add(new DictionaryDocument.Entry {
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

            Console.WriteLine("Dumping to test.xml");
            SerializeDictionary(d, "test.xml");

            Console.WriteLine("End of the app");
        }
    }
}
