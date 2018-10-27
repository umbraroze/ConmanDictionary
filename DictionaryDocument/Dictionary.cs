using System;
using System.Collections.Generic;
using System.Xml.Serialization;

namespace DictionaryDocument
{
    [XmlRoot("dictionarydatabase")]
    public class Dictionary
    {
        [XmlElement("notepad")]
        public string NotePad = "";

        [XmlArrayItem("item")]
        [XmlArray("todo")]
        public List<string> ToDoItems;

        [XmlArray(ElementName = "categories")]
        public List<Category> Categories;

        [XmlArrayItem(ElementName = "class")]
        [XmlArray("wordclasses")]
        public List<WordClass> WordClasses;

        [XmlElement("definitions")]
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
    }

    public class EntryList
    {
        [XmlAttribute("language")]
        public string Language { get; set; }

        [XmlElement(ElementName = "entry")]
        public List<Entry> Entries = new List<Entry>();
    }

    [XmlRoot("entry")]
    public class Entry
    {
        [XmlElement("term")]
        public string Term { get; set; }

        [XmlElement("definition")]
        public string Definition { get; set; }

        [XmlAttribute("flagged")]
        public bool Flagged { get; set; }

        // FIXME: REFERENCE
        // [XmlAttribute("class")]
        public WordClass WordClass { get; set; }

        // FIXME: REFERENCE
        // [XmlAttribute("category")]
        public Category Category { get; set; }

        public override string ToString()
        {
            return Term + " (" + WordClass.Abbreviation + ".): " + Definition;
        }
    }

    [XmlRoot("class")]
    public class WordClass
    {
        [XmlAttribute("name")]
        public string Name { get; set; } = "";

        [XmlAttribute("abbreviation")]
        public string Abbreviation { get; set; } = "";

        [XmlAttribute("description")]
        public string Description { get; set; } = "";

        [XmlAttribute("flagged")]
        public bool   Flagged { get; set; } = false;
    }

    [XmlRoot("category")]
    public class Category
    {

        [XmlAttribute("name")]
        public string  Name { get; set; }

        [XmlAttribute("description")]
        public string  Description { get; set; }

        [XmlAttribute("flagged")]
        public bool    Flagged { get; set; }
    }
}
