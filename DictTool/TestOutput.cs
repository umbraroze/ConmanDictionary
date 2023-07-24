﻿/*
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
using System.Xml.Linq;
using System.Xml.Serialization;

namespace DictTool
{
    class DictToolUtility
    {
        public static DictionaryDocument.Dictionary GetMockDocument()
        {
            var d = new DictionaryDocument.Dictionary();

            d.NotePad = "This is some random text for the notepad.";

            d.Definitions[0].Language = "Aybeeseean";
            var left = d.Definitions[0].Entries;
            d.Definitions[1].Language = "English";
            var right = d.Definitions[1].Entries;

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

        private static void SerializeDictionary(DictionaryDocument.Dictionary dictionary, FileInfo fileName)
        {
            FileStream serout = new FileStream(fileName.FullName, FileMode.OpenOrCreate);
            XmlSerializer ser = new XmlSerializer(typeof(XElement));
            ser.Serialize(serout, dictionary.ToXml());
            serout.Close();
        }

        public static void TestOutput(FileInfo outputFile)
        {
            Console.WriteLine("Start of the app");

            Console.WriteLine("Fiddling.");
            var d = GetMockDocument();

            Console.WriteLine($"Dumping to {outputFile.FullName}");
            SerializeDictionary(d, outputFile);

            Console.WriteLine("End of the app");
        }
    }
}
