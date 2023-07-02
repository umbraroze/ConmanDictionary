using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Xml.Linq;

namespace DictionaryDocument
{
    public class Dictionary
    {
        public string NotePad = "";

        public List<string> ToDoItems;

        public List<Category> Categories;

        public List<WordClass> WordClasses;

        public List<EntryList> Definitions;

        /*
         * Construct an empty dictionary document with default content.
         */
        public Dictionary()
        {
            ToDoItems = new List<string>();
            Categories = new List<Category>();
            // Populate default word classes
            WordClasses = new List<WordClass>();
            WordClasses.Add(new WordClass { Name = "Noun", Abbreviation = "n" });
            WordClasses.Add(new WordClass { Name = "Verb", Abbreviation = "v" });
            WordClasses.Add(new WordClass { Name = "Adjective", Abbreviation = "a" });
            // Populate default entry lists
            Definitions = new List<EntryList>();
            Definitions.Add(new EntryList { Language = "Language 1" });
            Definitions.Add(new EntryList { Language = "Language 2" });
        }
        public override string ToString()
        {
            return "[Dictionary]";
        }

        public XElement ToXml()
        {
            XElement r = new XElement("dictionarydatabase",
                new XElement("notepad", NotePad),
                new XElement("todo", ToDoItems),
                new XElement("categories", Categories.Select(cat => cat.ToXml())),
                new XElement("wordclasses", WordClasses.Select(wc => wc.ToXml())),
                new XElement("definitions", Definitions.Select(d => d.ToXml())));
            return r;
        }
    }

    public class EntryList
    {
        public string Language { get; set; }

        public List<Entry> Entries = new List<Entry>();

        public XElement ToXml()
        {
            return new XElement("entries",
                new XAttribute("language", Language),
                Entries.Select(entry => entry.ToXml()));
        }
    }

    public class Entry
    {
        public string Term { get; set; }

        public string Definition { get; set; }

        public bool Flagged { get; set; } = false;

        public WordClass WordClass { get; set; }

        public Category Category { get; set; }

        public override string ToString()
        {
            return $"{Term} ({WordClass.Abbreviation}.): {Definition}";
        }

        public XElement ToXml()
        {
            return new XElement("entry",
                new XElement("term", Term),
                new XElement("definition", Definition),
                new XElement("flagged", Flagged),
                new XElement("class", WordClass?.Name),
                new XElement("category", Category?.Name));
        }
    }

    public class WordClass
    {
        public string Name { get; set; } = "";

        public string Abbreviation { get; set; } = "";

        public string Description { get; set; } = "";

        public bool Flagged { get; set; } = false;
        public XElement ToXml()
        {
            return new XElement("category",
                new XElement("name", Name),
                new XElement("abbreviation", Abbreviation),
                new XElement("description", Description),
                new XElement("flagged", Flagged));
        }
    }

    public class Category
    {

        public string Name { get; set; }

        public string Description { get; set; }

        [DefaultValue(false)]
        public bool Flagged { get; set; } = false;

        public XElement ToXml()
        {
            return new XElement("category",
                new XElement("name", Name),
                new XElement("description", Description),
                new XElement("flagged", Flagged));
        }
    }
}
