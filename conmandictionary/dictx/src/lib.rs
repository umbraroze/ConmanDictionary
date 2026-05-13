use facet::*;

#[derive(Facet,Debug,Clone)]
pub struct Dictionary<'a> {
    pub notepad: Option<String>,
    pub todo_items: Option<Vec<String>>,
    pub categories: Vec<Category>,
    pub word_classes: Vec<WordClass>,
    pub definitions: [EntryList<'a>;2]
}

// IDEA: Need a helper function for Dictionary to find a reference to a word class or
//       category by name

#[derive(Facet,Debug,Clone)]
pub struct EntryList<'a> {
    pub language: String,
    pub entries: Vec<Entry<'a>>
}

#[derive(Facet,Debug,Clone)]
pub struct Entry<'a> {
    pub term: String,
    pub definition: String,
    pub flagged: bool,
    pub word_class: Option<&'a WordClass>,
    pub category: Option<&'a Category>
}

#[derive(Facet,Debug,Clone)]
pub struct WordClass {
    pub name: String,
    pub abbreviation: String,
    pub description: Option<String>,
    pub flagged: bool
}

#[derive(Facet,Debug,Clone)]
pub struct Category {
    pub name: String,
    pub description: Option<String>,
    pub flagged: bool
}

/// Get the dictx XML schema.
pub fn get_schema() -> &'static str {
    str::from_utf8(include_bytes!("dictx.xsd")).unwrap()
}

pub fn get_mock_document() -> Dictionary<'static> {
    let dictionary = Dictionary {
        notepad: Some(String::from("This is some random text for the notepad.")),
        todo_items: None,
        categories: vec![],
        word_classes: vec![
          WordClass {
              name: String::from("Noun"),
              abbreviation: String::from("n"),
              description: None,
              flagged: false
          },
            WordClass {
              name: String::from("Verb"),
              abbreviation: String::from("v"),
              description: None,
              flagged: false
          },
            WordClass {
              name: String::from("Adjective"),
              abbreviation: String::from("a"),
              description: None,
              flagged: false
          }
        ],
        definitions: [
            EntryList {
                language: String::from("Aybeeseean"),
                entries: vec![]
            },
            EntryList {
                language: String::from("English"),
                entries: vec![]
            }
        ],
    };
    //let mut defs1 = &dictionary.definitions[0].entries;
    //let mut defs2 = &dictionary.definitions[1].entries;

    /* // FIXME: THIS STUFF BREAKS
    &dictionary.definitions[0].entries.push(Entry {
        term: String::from("foo"),
        definition: String::from("to pity"),
        word_class: Some(&dictionary.word_classes[0]), // THIS BORROW THING BREAKS EVERYTHING
        category: None,
        flagged: false
    });
    */ // FIXME: END OF BREAKING STUFF

    return dictionary;
}

/*
        var d = new Dictionary();

        d.Definitions[0].Language = "Aybeeseean";
        var left = d.Definitions[0].Entries;
        d.Definitions[1].Language = "English";
        var right = d.Definitions[1].Entries;

        d.NotePad = "This is some random text for the notepad.";

        var wcverb = d.WordClasses.Find(x => x.Name.Equals("Verb"));
        var wcnoun = d.WordClasses.Find(x => x.Name.Equals("Noun"));
        var wcadj = d.WordClasses.Find(x => x.Name.Equals("Adjective"));

        left.Add(new Entry
        {
            Term = "foo",
            Definition = "to pity",
            WordClass = wcverb
        });
        left.Add(new Entry
        {
            Term = "bah",
            Definition = "bad sigh",
            WordClass = wcnoun
        });
        left.Add(new Entry
        {
            Term = "zzbaz",
            Definition = "annoying",
            WordClass = wcadj
        });
        right.Add(new Entry
        {
            Term = "pity",
            Definition = "foo",
            WordClass = wcverb
        });
        right.Add(new Entry
        {
            Term = "sigh",
            Definition = "bah (bad sigh)",
            WordClass = wcnoun
        });
        right.Add(new Entry
        {
            Term = "annoying",
            Definition = "zzbaz",
            WordClass = wcadj
        });

        return d;
    }
 */


#[cfg(test)]
mod tests {
    //use super::*;

    #[test]
    fn it_works() {
        assert_eq!(true, true);
    }
}
