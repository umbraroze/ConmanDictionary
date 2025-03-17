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
using System.Xml.XPath;

namespace DictionaryDocument
{
    public record Dictionary
    {
        private string _notepad = "";
        public string NotePad {
            get { return _notepad; }
            set
            {
                // Handle the nullitude of the NotePad. NotePad
                // should always have a value, even if it's an empty string.
                if (value == null)
                    _notepad = "";
                else
                    _notepad = value;
            }
        }

        // TODO: Is it a list of strings, tho?
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

        public static Dictionary FromXml(XElement element)
        {
            if (element.Name != "dictionarydatabase")
                throw new XmlException($"Wrong base tag; expected \"dictionarydatabase\", got \"{element.Name}\"");

            //var definitions = EntryList.FromXml(element.Elements("definitions"));

            Dictionary dictionary = new()
            {
                // Parse notepad
                NotePad = element.Element("notepad")?.Value ?? ""
            };

            // Parse todo items
            var todoItems = element.Element("todoitems")?.Elements("todoitem");
            if (todoItems != null)
            {
                dictionary.ToDoItems = [.. todoItems.Select(item => item.Value)];
            }

            // Parse word classes
            var wordClasses = element.Element("wordclasses")?.Elements("class");
            if (wordClasses != null)
            {
                dictionary.WordClasses = [.. wordClasses.Select(wc => WordClass.FromXml(wc))];
            }

            // Parse categories
            var categories = element.Element("categories")?.Elements("category");
            if (categories != null)
            {
                dictionary.Categories = [.. categories.Select(cat => Category.FromXml(cat))];
            }

            // Parse definitions
            var definitionElements = element.Elements("definitions");
            if (definitionElements.Count() == 2)
            {
                dictionary.Definitions = [.. definitionElements.Select(def => EntryList.FromXml(def))];
            }
            else
            {
                throw new XmlException("Expected exactly two definition lists");
            }
            return dictionary;
        }

        public static Dictionary LoadDictx(FileInfo fileName)
        {
            FileStream xmlin = new FileStream(fileName.FullName, FileMode.Open);
            //XmlReader reader = XmlReader.Create(xmlin);
            XElement document = XElement.Load(xmlin); // XElement document = XNode.ReadFrom(reader).Document.Root;
            xmlin.Close();
            return FromXml(document);
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

        public static EntryList FromXml(XElement element)
        {
            if (element.Name != "definitions")
                throw new XmlException($"Wrong base tag; expected \"definitions\", got \"{element.Name}\"");

            EntryList entryList = new EntryList
            {
                Language = element.Attribute("language")?.Value ?? "",
                Entries = [.. element.Elements("entry").Select(entry => Entry.FromXml(entry))]
            };

            return entryList;
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

        public static Entry FromXml(XElement element)
        {
            if (element.Name != "entry")
                throw new XmlException($"Wrong base tag; expected \"entry\", got \"{element.Name}\"");

            Entry entry = new Entry
            {
                Term = element.Element("term")?.Value ?? "",
                Definition = element.Element("definition")?.Value ?? "",
                Flagged = bool.Parse(element.Attribute("flagged")?.Value ?? "false"),
                WordClass = new WordClass { Name = element.Attribute("class")?.Value ?? "" },
                Category = element.Attribute("category") != null ? new Category { Name = element.Attribute("category").Value } : null
            };

            return entry;
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
            if (Flagged)
                e.SetAttributeValue("flagged", true);
            if (Description?.Length > 0)
                e.SetValue(Description);
            return e;
        }
        public static WordClass FromXml(XElement element)
        {
            if (element.Name != "class")
                throw new XmlException($"Wrong base tag; expected \"class\", got \"{element.Name}\"");
            
            WordClass wordClass = new WordClass
            {
                Name = element.Attribute("name")?.Value ?? "",
                Abbreviation = element.Attribute("abbreviation")?.Value ?? "",
                Description = element.Value,
                Flagged = bool.Parse(element.Attribute("flagged")?.Value ?? "false")
            };

            return wordClass;
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
            if (Flagged)
                e.SetAttributeValue("flagged", true);
            if (Description?.Length > 0)
                e.SetValue(Description);
            return e;
        }

        public static Category FromXml(XElement element)
        {
            if (element.Name != "category")
                throw new XmlException($"Wrong base tag; expected \"category\", got \"{element.Name}\"");

            Category category = new Category
            {
                Name = element.Attribute("name")?.Value ?? "",
                Description = element.Value,
                Flagged = bool.Parse(element.Attribute("flagged")?.Value ?? "false")
            };

            return category;
        }
    }
}
