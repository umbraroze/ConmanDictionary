
pub fn get_schema() {
    let xsd = include_bytes!("dictx.xsd");
    let _xsd_string = String::from_utf8_lossy(xsd);
    // return xsd_string;
}

pub fn add(left: u64, right: u64) -> u64 {
    left + right
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn it_works() {
        let result = add(2, 2);
        assert_eq!(result, 4);
    }
}
