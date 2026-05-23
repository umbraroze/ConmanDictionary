use std::path::PathBuf;
use dictx::Dictionary;

fn main() {
    //println!("{}",dictx::get_schema());

    let dict_document = Dictionary::get_mock_document();
    println!("{:?}",dict_document);
    println!("Notepad: {}", dict_document.notepad.unwrap());
    let source = PathBuf::from("input.xml");
    if Dictionary::validate(source.clone()) {
        println!("Valid");
    } else {
        println!("Not Valid");
    }
    let _document = Dictionary::load(source.clone());
}
