/*
 * FIXME:
 * Random old stackoverflow stuff says System.Xml.Serialization can't handle ID/IDREF correctly
 * (despite xsd.exe spitting out code that specifies exactly that).
 * The correct way is supposedly to use System.Runtime.Serialization.DataContractSerializer.
 * https://docs.microsoft.com/en-us/dotnet/api/system.runtime.serialization.datacontractserializer
 * https://docs.microsoft.com/en-us/dotnet/framework/wcf/feature-details/using-data-contracts
 * Read up and change the code?
 */

using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Runtime.Serialization;

namespace DictionaryDocument
{
    [DataContract(Name = "dictionarydatabase")]
    public class Dictionary
    {
        [DataMember(Name = "notepad")]
        public string NotePad = "";

        [DataMember(Name = "todo")] // Array = todo, ArrayItem = item
        public List<string> ToDoItems;

        [DataMember(Name = "categories")]
        public List<Category> Categories;

        [DataMember(Name = "wordclasses")] // Array = wordclasses, ArrayItem = class
        public List<WordClass> WordClasses;

        [DataMember(Name = "definitions")]
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

    [DataContract(Name = "entrylist")] // FIXME: name???
    public class EntryList
    {
        [DataMember(Name = "language")] // FIXME: Attribute
        public string Language { get; set; }

        [DataMember(Name = "entries")]
        public List<Entry> Entries = new List<Entry>();
    }

    [DataContract(Name = "entry")]
    public class Entry
    {
        [DataMember(Name = "term")]
        public string Term { get; set; }

        [DataMember(Name = "definition")]
        public string Definition { get; set; }

        [DataMember(Name = "flagged")]
        [DefaultValue(false)]
        public bool Flagged { get; set; }

        [DataMember(Name = "class")] // FIXME: Reference by Name
        public WordClass WordClass { get; set; }

        [DataMember(Name = "category")] // FIXME: Reference by Name
        public Category Category { get; set; }

        public override string ToString()
        {
            return $"{Term} ({WordClass.Abbreviation}.): {Definition}";
        }
    }

    [DataContract(Name = "class", IsReference = true)] // FIXME: Referenced by Name
    public class WordClass
    {
        [DataMember(Name = "name")]
        public string Name { get; set; } = "";

        [DataMember(Name = "abbreviation")]
        public string Abbreviation { get; set; } = "";

        [DataMember(Name = "description")]
        public string Description { get; set; } = "";

        [DataMember(Name = "flagged")]
        [DefaultValue(false)]
        public bool Flagged { get; set; } = false;
    }

    [DataContract(Name = "category", IsReference = true)] // FIXME: Referenced by Name
    public class Category
    {

        [DataMember(Name = "name")]
        public string  Name { get; set; }

        [DataMember(Name = "description")]
        public string Description { get; set; }

        [DataMember(Name = "flagged")]
        [DefaultValue(false)]
        public bool Flagged { get; set; } = false;
    }
}
