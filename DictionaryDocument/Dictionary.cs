using System;
using System.Collections.Generic;

namespace DictionaryDocument
{
    public class Dictionary
    {
        public List<string> ToDoItems;
        public List<Category> Categories;
        public List<WordClass> WordClasses;
        public List<EntryList> Definitions;
        private void PopulateDefaultWordClasses()
        {
            WordClasses = new List<WordClass>();
            WordClass n = new WordClass("Noun", "n");
            WordClass v = new WordClass("Verb", "v");
            WordClass a = new WordClass("Adjective", "a");
            WordClasses.Add(n);
            WordClasses.Add(v);
            WordClasses.Add(a);
        }
        private void PopulateDefaultEntryLists()
        {
            Definitions = new List<EntryList>();
            EntryList left = new EntryList("Language 1");
            EntryList right = new EntryList("Language 2");
            Definitions.Add(left);
            Definitions.Add(right);
        }
        public Dictionary()
        {
            ToDoItems = new List<string>();
            Categories = new List<Category>();
            PopulateDefaultWordClasses();
            PopulateDefaultEntryLists();
        }
        public override string ToString()
        {
            return "[Dictionary]";
        }
    }
    public class EntryList
    {
        public string Language { get; set; }
        public List<Entry> Entries;
        public EntryList(string language)
        {
            Language = language;
            Entries = new List<Entry>();
        }
    }
    public class Entry
    {
        public string Term { get; set; }
        public string Definition { get; set; }
        public bool Flagged { get; set; }
        public WordClass WordClass { get; set; }
        public Category Category { get; set; }
        public override string ToString()
        {
            return Term + " (" + WordClass.Abbreviation + ".): " + Definition;
        }
    }
    public class WordClass
    {
        public String Name { get; set; }
        public String Abbreviation { get; set; }
        public String Description { get; set; }
        public Boolean Flagged { get; set; }
        public WordClass(string name, string abbreviation)
        {
            Name = name; Abbreviation = abbreviation;
            Description = ""; Flagged = false;
        }
    }
    public class Category
    {
        public String Name { get; set; }
        public String Description { get; set; }
        public Boolean Flagged { get; set; }
    }
}
