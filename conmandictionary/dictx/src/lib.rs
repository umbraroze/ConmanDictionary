use facet::*;
use slotmap::*;

// TODO: How do I make these use Facet?
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

// IDEA: Use hashmap of some description for word classes and categories?

// IDEA: Need a helper function for Dictionary to find a reference to a word class or
//       category by name

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
