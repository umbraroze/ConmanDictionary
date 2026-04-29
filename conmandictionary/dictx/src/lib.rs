
// For docs on XML serialisation using facet: https://crates.io/crates/facet-xml

#[derive(Facet,Debug)]
struct Dictionary {
    notepad: String,
    todo_items: Vec<String>,
    categories: Vec<Category>,
    word_classes: Vec<WordClass>,
    definitions: [EntryList;2]
}

#[derive(Facet,Debug)]
#[facet(rename="definitions")]
struct EntryList {
    language: String,
    entries: Vec<Entry>
}

#[derive(Facet,Debug)]
struct Entry {
    term: String,
    definition: String,
    flagged: bool,
    wordclass: &WordClass,
    category: Opt<&Category>
}

#[derive(Facet,Debug)]
struct WordClass {
    name: String,
    abbreviation: String,
    description: String,
    flagged: bool
}

#[derive(Facet,Debug)]
struct Category {
    name: String,
    description: String,
    flagged: bool
}


/* Get the dictx XML schema. */
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
