
// TODO:
// xml parsing: https://crates.io/crates/xml
// xml schema validation: ???
// xml emitting: ???

struct Dictionary {
    notepad: String,
    todo_items: Vec<String>,
    categories: Vec<Category>,
    word_classes: Vec<WordClass>,
    definitions: [EntryList;2]
}

struct EntryList {
    language: String,
    entries: Vec<Entry>
}

struct Entry {
    term: String,
    definition: String,
    flagged: bool,
    wordclass: WordClass, // Reference
    category: Category // Reference, Optional?
}

struct WordClass {
    name: String,
    abbreviation: String,
    description: String,
    flagged: bool
}

struct Category {
    name: String,
    description: String,
    flagged: bool
}


/// Get the dictx XML schema.
pub fn get_schema() -> &'static str {
    return str::from_utf8(include_bytes!("dictx.xsd")).unwrap();
}

#[cfg(test)]
mod tests {
    //use super::*;

    #[test]
    fn it_works() {
        assert_eq!(true, true);
    }
}
