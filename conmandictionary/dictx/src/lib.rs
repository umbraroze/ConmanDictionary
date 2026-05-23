use std::fs::File;
use std::io::Read;
use std::path::PathBuf;
use slotmap::*;
use xmloxide::Document;
use xmloxide::validation::xsd::{parse_xsd, validate_xsd};
use xmloxide::xpath::{evaluate, XPathValue};

new_key_type! {
    pub struct WordClassKey;
    pub struct CategoryKey;
}

#[derive(Debug, Clone)]
pub struct Dictionary {
    pub notepad: Option<String>,
    pub todo_items: Option<Vec<String>>,
    pub word_classes: SlotMap<WordClassKey, WordClass>,
    pub categories: SlotMap<CategoryKey, Category>,
    pub definitions: [EntryList; 2],
}

#[derive(Debug, Clone)]
pub struct EntryList {
    pub language: String,
    pub entries: Vec<Entry>,
}

#[derive(Debug, Clone)]
pub struct Entry {
    pub term: String,
    pub definition: String,
    pub flagged: bool,
    pub word_class: Option<WordClassKey>,
    pub category: Option<CategoryKey>,
}

#[derive(Debug, Clone)]
pub struct WordClass {
    pub name: String,
    pub abbreviation: String,
    pub description: Option<String>,
    pub flagged: bool,
}

#[derive(Debug, Clone)]
pub struct Category {
    pub name: String,
    pub description: Option<String>,
    pub flagged: bool,
}

/// Get the dictx XML schema.
pub fn get_schema() -> &'static str {
    str::from_utf8(include_bytes!("dictx.xsd")).unwrap()
}

impl Dictionary {
    pub fn new() -> Dictionary {
        Dictionary {
            notepad: None,
            todo_items: None,
            word_classes: SlotMap::with_key(),
            categories: SlotMap::with_key(),
            definitions: [
                EntryList {
                    language: String::from("Language A"),
                    entries: vec![],
                },
                EntryList {
                    language: String::from("Language B"),
                    entries: vec![],
                },
            ],
        }
    }
    pub fn load(source: PathBuf) -> Dictionary {
        let mut dictionary = Dictionary::new();
        let file = File::open(source);
        if !file.is_ok() {
            return dictionary; // TODO: Fail handling
        }
        let mut file = file.unwrap();
        let mut source_data: String = String::new();
        let _ = file.read_to_string(&mut source_data);
        println!("{}", source_data);

        let doc = Document::parse_str(&source_data).unwrap();
        println!("{:#?}", doc);
        let root = doc.root_element().unwrap();

        let notepad = evaluate(&doc, root, "string(/dictionarydatabase/notepad)").unwrap();
        println!("Parsed notepad: {}", notepad.to_xpath_string());

        dictionary
    }
    pub fn validate(file: PathBuf) -> bool {
        let schema = parse_xsd(get_schema()).unwrap();
        let doc = xmloxide::Document::parse_file(file).unwrap();
        let result = validate_xsd(&doc, &schema);
        result.is_valid
    }

    pub fn get_mock_document() -> Dictionary {
        let mut dictionary = Dictionary {
            notepad: Some(String::from("This is some random text for the notepad.")),
            todo_items: None,
            word_classes: SlotMap::with_key(),
            categories: SlotMap::with_key(),
            definitions: [
                EntryList {
                    language: String::from("Aybeeseean"),
                    entries: vec![],
                },
                EntryList {
                    language: String::from("English"),
                    entries: vec![],
                },
            ],
        };

        let wc_noun = dictionary.word_classes.insert(WordClass {
            name: String::from("Noun"),
            abbreviation: String::from("n"),
            description: None,
            flagged: false,
        });
        let wc_verb = dictionary.word_classes.insert(WordClass {
            name: String::from("Verb"),
            abbreviation: String::from("v"),
            description: None,
            flagged: false,
        });
        let wc_adjective = dictionary.word_classes.insert(WordClass {
            name: String::from("Adjective"),
            abbreviation: String::from("a"),
            description: None,
            flagged: false,
        });

        dictionary.definitions[0].entries.push(Entry {
            term: String::from("foo"),
            definition: String::from("to pity"),
            word_class: Some(wc_verb),
            category: None,
            flagged: false
        });
        dictionary.definitions[0].entries.push(Entry {
            term: String::from("bah"),
            definition: String::from("bad sigh"),
            word_class: Some(wc_noun),
            category: None,
            flagged: false
        });
        dictionary.definitions[0].entries.push(Entry {
            term: String::from("zzbaz"),
            definition: String::from("annoying"),
            word_class: Some(wc_adjective),
            category: None,
            flagged: false
        });
        dictionary.definitions[1].entries.push(Entry {
            term: String::from("pity"),
            definition: String::from("foo"),
            word_class: Some(wc_verb),
            category: None,
            flagged: false
        });
        dictionary.definitions[1].entries.push(Entry {
            term: String::from("sigh"),
            definition: String::from("bah (bad sigh)"),
            word_class: Some(wc_noun),
            category: None,
            flagged: false
        });
        dictionary.definitions[1].entries.push(Entry {
            term: String::from("annoying"),
            definition: String::from("zzbaz"),
            word_class: Some(wc_adjective),
            category: None,
            flagged: false
        });

        dictionary
    }
}

#[cfg(test)]
mod tests {
    //use super::*;

    #[test]
    fn it_works() {
        assert_eq!(true, true);
    }
}
