
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
