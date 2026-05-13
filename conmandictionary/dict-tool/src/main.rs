fn main() {
    //println!("{}",dictx::get_schema());

    let dict_document = dictx::get_mock_document();
    println!("{:?}",dict_document);
    println!("Notepad: {}", dict_document.notepad.unwrap());
}
