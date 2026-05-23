
# TODO and temporary notes

## dictx library

TODO:

- XML libraries:
  - parsing:
    - ~~[xml](https://crates.io/crates/xml)~~
	- ~~[quick-xml](https://crates.io/crates/quick-xml)~~ event-based
	- ~~[xmltree](https://crates.io/crates/xmltree)~~ DOM-based
  	- [xmloxide](https://crates.io/crates/xmloxide) can do DOM-style parsing too!
  - schema validation:
    - ~~[xsd](https://crates.io/crates/xsd)~~  
	  "Presently under heavy construction (last updated 1 year ago)  
	  No documentation???
	- ~~libxml2~~ (via FFI)  
	  Do we *want* to depend on a yet another random C library???  
	- [xmloxide](https://crates.io/crates/xmloxide)  
	  Apparently a Rust rewrite of libxml2. Apparently maintained?
	  Has xsd validation!
  - emitting:
    - ~~[quick-xml](https://crates.io/crates/quick-xml)~~
    - ...can we just do this with xmloxide?
	- ~~xmltree???~~

## dict-tool

- CLI arguments parser: [clap](https://docs.rs/clap/latest/clap/) documentation
