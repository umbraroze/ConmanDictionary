using System.Collections;
using System.Collections.Generic;
using System.ComponentModel;
using System.IO;
using System.Linq;
using System.Xml.Linq;
using System.Xml.Serialization;
using System.Xml.Schema;
using System.Xml;
using System;
using System.Resources;
using System.Data;

namespace DictionaryDocument
{
    public record Dictionary
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
            return $"[Dictionary: {Definitions[0].Language}, {Definitions[1].Language}]";
        }

        public XElement ToXml()
        {
            XElement r = new XElement("dictionarydatabase");
            r.Add(new XElement("notepad", NotePad));
            if(ToDoItems.Count > 0)
            {
                r.Add(new XElement("todoitems", ToDoItems.Select(item => new XElement("todoitem",item))));
            }
            r.Add(new XElement("wordclasses", WordClasses.Select(wc => wc.ToXml())));
            r.Add(new XElement("categories", Categories.Select(cat => cat.ToXml())));
            r.Add(Definitions[0].ToXml()); // Left list
            r.Add(Definitions[1].ToXml()); // Right list
            return r;
        }

        public void SaveDictx(FileInfo fileName)
        {
            if(File.Exists(fileName.FullName))
                File.Delete(fileName.FullName);
            FileStream serout = new FileStream(fileName.FullName, FileMode.OpenOrCreate);
            XmlSerializer ser = new XmlSerializer(typeof(XElement));
            ser.Serialize(serout, ToXml());
            serout.Close();
        }

        public static XmlSchema GetDictxSchema()
        {
            // Grab the schema document from resources.
            string schemaText = DictionaryDocument.Properties.Resources.dictx_schema;

            // Parse the returned string as XML and turn it into a schema.
            XmlReader schemaReader = XmlReader.Create(new StringReader(schemaText));
            XmlSchema schema = XmlSchema.Read(schemaReader,null);

            return schema;
        }

        public static bool ValidateDictx(FileInfo filename)
        {
            XmlReaderSettings dictxSettings = new XmlReaderSettings();
            dictxSettings.Schemas.Add(GetDictxSchema());
            dictxSettings.ValidationType = ValidationType.Schema;
            dictxSettings.ValidationEventHandler += ValidateDictxEventHandler;

            XmlReader dictx = XmlReader.Create(filename.FullName, dictxSettings);

            try
            {
                while (dictx.Read()) { }
            }
            catch(XmlException)
            {
                return false;
            }
            return true;
        }
        private static void ValidateDictxEventHandler(object sender, ValidationEventArgs e)
        {
            if (e.Severity == XmlSeverityType.Warning)
            {
                Console.Error.WriteLine($"DictX validation WARNING: {e.Message}");
            }
            else if (e.Severity == XmlSeverityType.Error)
            {
                Console.Error.WriteLine($"DictX validation ERROR: {e.Message}");
                throw new XmlException(e.Message);
            }
        }
    }

    public record EntryList
    {
        public string Language { get; set; }

        public List<Entry> Entries = new List<Entry>();

        public XElement ToXml()
        {
            return new XElement("definitions",
                new XAttribute("language", Language),
                Entries.Select(entry => entry.ToXml()));
        }
    }

    public record Entry
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
            XElement e = new XElement("entry");
            e.Add(new XElement("term", Term));
            e.Add(new XElement("definition", Definition));
            if(Flagged)
                e.SetAttributeValue("flagged", true);
            e.SetAttributeValue("class", WordClass?.Name);
            if(Category != null)
                e.SetAttributeValue("category", Category.Name);
            return e;
        }
    }

    public record WordClass
    {
        public string Name { get; set; } = "";

        public string Abbreviation { get; set; } = "";

        public string Description { get; set; } = "";

        public bool Flagged { get; set; } = false;
        public XElement ToXml()
        {
            XElement e = new XElement("class");
            e.SetAttributeValue("name", Name);
            e.SetAttributeValue("abbreviation", Abbreviation);
            if(Description.Length > 0)
                e.SetAttributeValue("description", Description);
            if(Flagged)
                e.SetAttributeValue("flagged", true);
            return e;
        }
    }

    public record Category
    {

        public string Name { get; set; }

        public string Description { get; set; }

        [DefaultValue(false)]
        public bool Flagged { get; set; } = false;

        public XElement ToXml()
        {
            XElement e = new XElement("category");
            e.SetAttributeValue("name", Name);
            if(Flagged)
                e.SetAttributeValue("flagged", true);
            // FIXME: Description doesn't actually appear in the schema, though?
            e.Add(new XElement("description", Description));
            return e;
        }
    }
}
