# To-do list for tests

## Tests found in Java version

### DictionaryTest

- validate simple file
- validate complex file
- load simple file
- load complex file

(Dunno if loading is required; validate includes loading)

### DynamicCreationTest

- create a file dynamically, save it
- load dynamically created file
- load dynamically created file, create a file dynamically, compare
  meticulously field by field

(Maybe should have separate object equivalence tests and meticulous equivalence tests?)

### EntryTest

- Compare entries:
  - same object: true
  - separate objects, different values: false
  - separate objects, identical values: true
- Entry list comparisons:
  - Tested lists with same/different lengths and same/differing entries
    (fairly redundant?)

### MergeTest

- loading from `complexfile`, `complexfile2` works,
  merges the result,
  loads `complexfiles_mergedbyhand`,
  works if merged results is equal to merged-by-hand one.
  (Had debug code to save the intermediate file for comparison.)
