using System.Collections.Generic;
using System.ComponentModel;
using System.IO;
using System.Linq;
using System.Xml.Linq;
using System.Xml.Serialization;
using System.Xml.Schema;
using System.Xml;
using System;
using System.Data;
using System.Diagnostics;
using System.Reflection.Metadata;

namespace DictionaryDocument
{
    /**
    <summary>
    <para><c>Dictionary</c> represents a bilingual dictionary (from language A to B, and B to A).</para>
    
    <para>Definitions are stored in <c>Definitions</c>, which must have exactly two
    <c>EntryList</c> objects.
    The <c>EntryList</c> objects contain <c>Entry</c> objects.
    The individual entries refer to word classes stored in <c>WordClasses</c>
    and categories in <c>Categories</c>.</para>
    </summary>
    */
    public record Dictionary : IEquatable<Dictionary>
    {
        private string _notepad = "";
        /// <summary>
        /// A notepad that is meant to store work-in-progress information while working with
        /// new entries. Currently, a plain-text string.
        /// </summary>
        public string NotePad
        {
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

        /// <summary>
        /// Meant to serve as a list of things to do in the dictionary project.
        /// </summary>
        /// <remarks>
        /// TODO: Is it a list of strings, tho? This never had actual real UI
        /// design work behind it as far as I can remember.
        /// </remarks>
        public List<string> ToDoItems;

        /// <summary>
        /// Available categories of words in the dictionary. Referred to by <c>Entry</c> objects.
        /// </summary>
        public List<Category> Categories;

        /// <summary>
        /// Available word classes in the dictionary. Referred to by <c>Entry</c> objects.
        /// </summary>
        public List<WordClass> WordClasses;

        /// <summary>
        /// Definitions of words. The list must contain exactly two <c>EntryList</c> objects.
        /// </summary>
        public List<EntryList> Definitions;

        /// <summary>
        /// <para>Construct an empty dictionary document with default content.</para>
        /// <para>The returned document is initialised to be suitable for use within
        /// an interactive application. The document includes default word classes
        /// (Noun, Verb, Adjective), two language lists ("Language 1" and "Language 2"),
        /// no categories, no to-do items, and an empty notepad.</para>
        /// </summary>
        public Dictionary()
        {
            ToDoItems = [];
            Categories = [];
            // Populate default word classes
            WordClasses =
            [
                new WordClass { Name = "Noun", Abbreviation = "n" },
                new WordClass { Name = "Verb", Abbreviation = "v" },
                new WordClass { Name = "Adjective", Abbreviation = "a" },
            ];
            // Populate default entry lists
            Definitions = [
                new EntryList { Language = "Language 1" },
                new EntryList { Language = "Language 2" }
            ];
        }
        public override string ToString()
        {
#if DEBUG
            return $"[Dictionary: {Definitions[0].Language}, {Definitions[1].Language}]";
#else
            return $"[Dictionary({GetHashCode()}): {Definitions[0].Language}, {Definitions[1].Language}]";
#endif
        }

        /// <summary>
        /// Produce a DictX XML representation of the entire document structure.
        /// See <c>dictx.xsd</c> for the DictX document schema.
        /// </summary>
        /// <returns>XML document root element.</returns>
        public XElement ToXml()
        {
            XElement r = new("dictionarydatabase");
            r.Add(new XElement("notepad", NotePad));
            if (ToDoItems.Count > 0)
            {
                r.Add(new XElement("todoitems", ToDoItems.Select(item => new XElement("todoitem", item))));
            }
            r.Add(new XElement("wordclasses", WordClasses.Select(wc => wc.ToXml())));
            r.Add(new XElement("categories", Categories.Select(cat => cat.ToXml())));
            r.Add(Definitions[0].ToXml()); // Left list
            r.Add(Definitions[1].ToXml()); // Right list
            return r;
        }

        /// <summary>
        /// <para>Produce a DictX XML representation of the entire document structure, and
        /// save it to the specified file.</para>
        /// <para>Target file will be overwritten if it exists.</para>
        /// <para>See <c>dictx.xsd</c> for the DictX document schema.</para>
        /// </summary>
        /// <param name="fileName">
        /// Path where the document will be saved to.
        /// </param>
        public void SaveDictx(FileInfo fileName)
        {
            if (File.Exists(fileName.FullName))
                File.Delete(fileName.FullName);
            using FileStream serout = new(fileName.FullName, FileMode.OpenOrCreate);
            XmlSerializer ser = new(typeof(XElement));
            ser.Serialize(serout, ToXml());
        }

        /// <summary>
        /// Parse a DictX XML object into a <c>Dictionary</c> object.
        /// </summary>
        /// <param name="element">Root element of the DictX document.</param>
        /// <returns>The parsed <c>Dictionary</c> instance.</returns>
        /// <exception cref="XmlException">Thrown if XML document is not valid.</exception>
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
                dictionary.WordClasses = [.. wordClasses.Select(WordClass.FromXml)];
            }

            // Parse categories
            var categories = element.Element("categories")?.Elements("category");
            if (categories != null)
            {
                dictionary.Categories = [.. categories.Select(Category.FromXml)];
            }

            // Parse definitions
            var definitionElements = element.Elements("definitions");
            if (definitionElements.Count() == 2)
            {
                dictionary.Definitions = [.. definitionElements.Select((_) => EntryList.FromXml(_, dictionary))];
            }
            else
            {
                throw new XmlException("Expected exactly two definition lists");
            }
            return dictionary;
        }

        /// <summary>
        /// Load a <c>Dictionary</c> object from a DictX XML file and parse it.
        /// </summary>
        /// <param name="fileName">Source file path.</param>
        /// <returns>The loaded and parsed object.</returns>
        public static Dictionary LoadDictx(FileInfo fileName)
        {
            XElement document;
            using (FileStream xmlin = new(fileName.FullName, FileMode.Open, FileAccess.Read, FileShare.Read))
            {
                document = XElement.Load(xmlin);
            }
            return FromXml(document);
        }

        /// <summary>
        /// Gets the XML Schema for the DictX XML file format.
        /// </summary>
        /// <returns>XML Schema.</returns>
        public static XmlSchema GetDictxSchema()
        {
            // Grab the schema document from resources.
            string schemaText = Properties.Resources.dictx_schema;

            // Parse the returned string as XML and turn it into a schema.
            XmlReader schemaReader = XmlReader.Create(new StringReader(schemaText));
            XmlSchema schema = XmlSchema.Read(schemaReader, null);

            return schema;
        }

        /// <summary>
        /// Validates a DictX XML file structure against the DictX XML Schema.
        /// </summary>
        /// <param name="fileName">Path to the file to be validated.</param>
        /// <returns><c>true</c> if the document is valid, <c>false</c> if the document is
        /// invalid or if the validation failed.</returns>
        public static bool ValidateDictx(FileInfo fileName)
        {
            XmlReaderSettings dictxSettings = new();
            dictxSettings.Schemas.Add(GetDictxSchema());
            dictxSettings.ValidationType = ValidationType.Schema;
            dictxSettings.ValidationEventHandler += ValidateDictxEventHandler;

            XmlReader dictx = XmlReader.Create(fileName.FullName, dictxSettings);

            try
            {
                while (dictx.Read()) { }
            }
            catch (XmlException)
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

        /// <summary>
        /// Produces a textual representation of the state of the <c>Dictionary</c>
        /// object to the <c>Debug</c> output.
        /// </summary>
        [Conditional("DEBUG")]
        public void DebugDump()
        {
            // TODO: This isn't complete yet!!!

            Debug.WriteLine("Dictionary");

            // Dump the notepad.
            if (NotePad != null)
            {
                if (NotePad != "")
                    Debug.WriteLine($" - Notepad: {NotePad}");
                else
                    Debug.WriteLine(" - Notepad is empty");
            }
            else
            {
                Debug.WriteLine(" - Notepad is null (somehow?)");
            }

            // Dump the To-Do items.
            if (ToDoItems.Count > 0)
            {
                Debug.WriteLine(" - ToDo items:");
                foreach (var i in ToDoItems)
                {
                    Debug.WriteLine($"   - {i}");
                }
            }
            else
            {
                Debug.WriteLine(" - No ToDo items.");
            }

            // Dump word classes and categories.
            if (WordClasses.Count > 0)
            {
                Debug.WriteLine(" - Word classes:");
                foreach (var i in WordClasses)
                {
                    Debug.WriteLine($"   - {i}");
                }
            }
            else
            {
                Debug.WriteLine(" - No word classes");
            }
            if (Categories.Count > 0)
            {
                Debug.WriteLine(" - Categories:");
                foreach (var i in Categories)
                {
                    Debug.WriteLine($"   - {i}");
                }
            }
            else
            {
                Debug.WriteLine(" - No categories");
            }
        }

        public virtual bool Equals(Dictionary other)
        {
            if (other == null)
            {
                Debug.WriteLine("Dictionary.Equals(): Other is null");
                return false;
            }
            if (NotePad != other.NotePad)
            {
                Debug.WriteLine("Dictionary.Equals(): Notepads don't match");
                return false;
            }
            if (!ToDoItemsEqual(other.ToDoItems))
            {
                Debug.WriteLine("Dictionary.Equals(): ToDoItems don't match");
                return false;
            }
            if (!CategoriesEqual(other.Categories))
            {
                Debug.WriteLine("Dictionary.Equals(): Categories don't match");
                return false;
            }
            if (!WordClassesEqual(other.WordClasses))
            {
                Debug.WriteLine("Dictionary.Equals(): WordClasses don't match");
                return false;
            }
            if (!DefinitionsEqual(other.Definitions))
            {
                Debug.WriteLine("Dictionary.Equals(): Definitions don't match");
                return false;
            }

            return true;
        }

        private bool ToDoItemsEqual(List<string> otherItems)
        {
            if (ToDoItems == null && otherItems == null)
                return true;
            if (ToDoItems == null || otherItems == null)
                return false;
            if (ToDoItems.Count != otherItems.Count)
                return false;

            return ToDoItems.SequenceEqual(otherItems);
        }

        private bool CategoriesEqual(List<Category> otherCategories)
        {
            if (Categories == null && otherCategories == null)
                return true;
            if (Categories == null || otherCategories == null)
                return false;
            if (Categories.Count != otherCategories.Count)
                return false;

            return Categories.SequenceEqual(otherCategories);
        }

        private bool WordClassesEqual(List<WordClass> otherClasses)
        {
            if (WordClasses == null && otherClasses == null)
                return true;
            if (WordClasses == null || otherClasses == null)
                return false;
            if (WordClasses.Count != otherClasses.Count)
                return false;

            return WordClasses.SequenceEqual(otherClasses);
        }

        private bool DefinitionsEqual(List<EntryList> otherDefinitions)
        {
            if (Definitions == null && otherDefinitions == null)
            {
                Debug.WriteLine("Dictionary.DefinitionsEqual(): Both are null");
                return true;
            }
            if (Definitions == null || otherDefinitions == null)
            {
                Debug.WriteLine("Dictionary.DefinitionsEqual(): Either is null");
                return false;
            }
            if (Definitions.Count != otherDefinitions.Count)
            {
                Debug.WriteLine("Dictionary.DefinitionsEqual(): Lists have different lengths");
                return false;
            }

            if (Definitions.SequenceEqual(otherDefinitions))
            {
                return true;
            }
            else
            {
                Debug.WriteLine("Dictionary.DefinitionsEqual(): Sequences don't match");
                Debug.WriteLine("Sequence 1:");
                foreach (var x in Definitions)
                {
                    Debug.WriteLine($" - {x.ToString()}");
                    foreach (var y in x.Entries)
                    {
                        Debug.WriteLine($"   - {y.ToString()}");
                    }
                }
                Debug.WriteLine("Sequence 2:");
                foreach (var x in otherDefinitions)
                {
                    Debug.WriteLine($" - {x.ToString()}");
                    foreach (var y in x.Entries)
                    {
                        Debug.WriteLine($"   - {y.ToString()}");
                    }
                }
                return false;
            }
        }

        public override int GetHashCode()
        {
            HashCode hash = new();
            hash.Add(NotePad);

            if (ToDoItems != null)
            {
                foreach (var item in ToDoItems)
                    hash.Add(item);
            }

            if (Categories != null)
            {
                foreach (var category in Categories)
                    hash.Add(category);
            }

            if (WordClasses != null)
            {
                foreach (var wordClass in WordClasses)
                    hash.Add(wordClass);
            }

            if (Definitions != null)
            {
                foreach (var definition in Definitions)
                    hash.Add(definition);
            }

            return hash.ToHashCode();
        }
    }

    public record EntryList
    {
        public string Language { get; set; }

        public List<Entry> Entries = [];

        public XElement ToXml()
        {
            return new XElement("definitions",
                new XAttribute("language", Language),
                Entries.Select(entry => entry.ToXml()));
        }

        public static EntryList FromXml(XElement element, Dictionary currentDictionary)
        {
            if (element.Name != "definitions")
                throw new XmlException($"Wrong base tag; expected \"definitions\", got \"{element.Name}\"");

            EntryList entryList = new()
            {
                Language = element.Attribute("language")?.Value ?? "",
                Entries = [.. element.Elements("entry").Select((_) => Entry.FromXml(_,currentDictionary))]
            };

            return entryList;
        }

        public virtual bool Equals(EntryList other)
        {
            if (other == null)
                return false;
            if (Language != other.Language)
                return false;
            if (Entries == null && other.Entries == null)
                return true;
            if (Entries == null || other.Entries == null)
                return false;
            if (Entries.Count != other.Entries.Count)
                return false;

            return Entries.SequenceEqual(other.Entries);
        }
        public override int GetHashCode()
        {
            HashCode hash = new();
            hash.Add(Language);
            if (Entries != null)
            {
                foreach (var item in Entries)
                    hash.Add(item);
            }
            return hash.ToHashCode();
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
            XElement e = new("entry");
            e.Add(new XElement("term", Term));
            e.Add(new XElement("definition", Definition));
            if (Flagged)
                e.SetAttributeValue("flagged", true);
            e.SetAttributeValue("class", WordClass?.Name);
            if (Category != null)
                e.SetAttributeValue("category", Category.Name);
            return e;
        }

        public static Entry FromXml(XElement element, Dictionary currentDictionary)
        {
            if (element.Name != "entry")
                throw new XmlException($"Wrong base tag; expected \"entry\", got \"{element.Name}\"");

            // Grab the wordclass and category references
            var wordclassRef = element.Attribute("class")?.Value;
            var categoryRef = element.Attribute("category")?.Value;

            // Pointing to the entries in currentDictionary.
            WordClass wordClass = null;
            Category category = null;
            // 
            foreach (var wc in currentDictionary.WordClasses)
            {
                if (wc.Name == wordclassRef)
                {
                    Debug.WriteLine($"PARSER: Found wordclass {wordclassRef}");
                    wordClass = wc;
                    break;
                }
            }
            if (wordClass == null)
            {
                throw new XmlException($"Invalid wordclass reference {wordclassRef}");
            }
            // Find the category reference.
            if (categoryRef != null)
            {
                foreach (var cat in currentDictionary.Categories)
                {
                    if (cat.Name == categoryRef)
                    {
                        category = cat;
                        break;
                    }
                }
            }
            // Category is optional so it may be null at this point.
                // Should we emit an 

                Entry entry = new()
            {
                Term = element.Element("term")?.Value ?? "",
                Definition = element.Element("definition")?.Value ?? "",
                Flagged = bool.Parse(element.Attribute("flagged")?.Value ?? "false"),
                WordClass = wordClass,
                Category = category
            };

            return entry;
        }
        
        public virtual bool Equals(Entry other)
        {
            if (other == null)
                return false;
            if (Term == other.Term &&
                Definition == other.Definition &&
                Flagged == other.Flagged &&
                WordClass == other.WordClass &&
                Category == other.Category)
                return true;
            return false;
        }

        public override int GetHashCode() => HashCode.Combine(Term, Definition, Flagged, WordClass, Category);
    }

    public record WordClass : IEquatable<WordClass>
    {
        public string Name { get; set; } = "";

        public string Abbreviation { get; set; } = "";

        public string Description { get; set; } = "";

        public bool Flagged { get; set; } = false;
        public XElement ToXml()
        {
            XElement e = new("class");
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

            WordClass wordClass = new()
            {
                Name = element.Attribute("name")?.Value ?? "",
                Abbreviation = element.Attribute("abbreviation")?.Value ?? "",
                Description = element.Value,
                Flagged = bool.Parse(element.Attribute("flagged")?.Value ?? "false")
            };

            return wordClass;
        }

        public virtual bool Equals(WordClass other)
        {
            if (other == null)
                return false;
            if (Name == other.Name &&
                Abbreviation == other.Abbreviation &&
                Description == other.Description &&
                Flagged == other.Flagged)
                return true;
            return false;
        }
        public override int GetHashCode() => HashCode.Combine(Name, Abbreviation, Description, Flagged);
    }

    public record Category : IEquatable<Category>
    {

        public string Name { get; set; }

        public string Description { get; set; }

        [DefaultValue(false)]
        public bool Flagged { get; set; } = false;

        public XElement ToXml()
        {
            XElement e = new("category");
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

            Category category = new()
            {
                Name = element.Attribute("name")?.Value ?? "",
                Description = element.Value,
                Flagged = bool.Parse(element.Attribute("flagged")?.Value ?? "false")
            };

            return category;
        }

        public virtual bool Equals(WordClass other)
        {
            if (other == null)
                return false;
            if (Name == other.Name &&
                Description == other.Description &&
                Flagged == other.Flagged)
                return true;
            return false;
        }

        public override int GetHashCode() => HashCode.Combine(Name, Description, Flagged);
    }
}
