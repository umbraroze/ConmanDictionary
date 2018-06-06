using System;
using System.Collections.Generic;

namespace DictionaryDocument
{
    public class Dictionary
    {
        public List<String> ToDoItems;
        public List<Category> Categories;
        public List<WordClass> WordClasses;
        public List<EntryList> Definitions;
    }
    public class EntryList
    {
        public String Language { get; set; }
        public List<Entry> Entries;
    }
    public class Entry
    {
        public String Term { get; set; }
        public String Definition { get; set; }
        public Boolean Flagged { get; set; }
        public WordClass WordClass { get; set; }
        public Category Category { get; set; }
    }
    public class WordClass
    {
        public String Name { get; set; }
        public String Description { get; set; }
        public Boolean Flagged { get; set; }
    }
    public class Category
    {
        public String Name { get; set; }
        public String Description { get; set; }
        public Boolean Flagged { get; set; }
    }
}
