use facet::*;
use facet_xml::*;

#[derive(Facet,Debug)]
#[facet(rename="dictionarydocument")]
pub struct Dictionary {
    notepad: String,
    todo_items: Vec<String>,
    categories: Vec<Category>,
    #[facet(rename="wordclasses")]
    word_classes: Vec<WordClass>,
    definitions: [EntryList;2]
}

#[derive(Facet,Debug)]
pub struct EntryList {
    language: String,
    entries: Vec<Entry>
}

#[derive(Facet,Debug)]
pub struct Entry {
    term: String,
    definition: String,
    flagged: bool,
    word_class: WordClass, // Reference
    category: Category // Reference, Optional?
}

#[derive(Facet,Debug)]
pub struct WordClass {
    name: String,
    abbreviation: String,
    description: String, // Should be nullable
    flagged: bool
}

#[derive(Facet,Debug)]
pub struct Category {
    name: String,
    description: String, // Should be nullable
    flagged: bool
}


/// Get the dictx XML schema.
pub fn get_schema() -> &'static str {
    str::from_utf8(include_bytes!("dictx.xsd")).unwrap()
}

pub fn get_mock_document() -> Dictionary {
    let mut dictionary = Dictionary{
        notepad: String::from("This is some random text for the notepad."),
        todo_items: vec![],
        categories: vec![],
        word_classes: vec![
          WordClass {
              name: String::from("Noun"),
              abbreviation: String::from("n"),
              description: String::from(""),
              flagged: false
          },
            WordClass {
              name: String::from("Verb"),
              abbreviation: String::from("v"),
              description: String::from(""),
              flagged: false
          },
            WordClass {
              name: String::from("Adjective"),
              abbreviation: String::from("a"),
              description: String::from(""),
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
    return dictionary;

    /*
    definitions[0].entries.push(Entry {
        term: String::from("foo"),
        definition: String::from("to pity"),
        word_class: wordclasses[0],
        category: None,
        flagged: false
    });
     */
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
