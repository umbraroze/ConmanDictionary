# Conman's Dictionary

## Overview

Conman's Dictionary is a dictionary application. It is primarily
geared for armchair linguists who are working on constructed languages
(hence the name). It probably will not be that good if it's used for
any other, more serious and more comprehensive use.

Conman's Dictionary is distributed under the
[GNU General Public License version 3](http://www.gnu.org/copyleft/gpl.html).
It is developed by Rose Midford, primarily for the Avarthrel
worldbuilding project.

For more background information, design notes, and other
project documentation, please see the
[Conman's Dictionary home page at GitHub Pages](https://umbraroze.github.io/ConmanDictionary/)
(or the `docs` folder, if you checked out this repository via Git).

## Source organisation

Currently, the application is split in a few different packages:

### ConmanDictionary

The GUI application. Doesn't have much yet, unfortunately.

Planned functionality:

* The usual dictionary editing and management commands
* Word class and category editors
* Notepad editor
* Merge dictionaries

### DictTool

A command-line tool for handing `.dictx` files. Not much here either.
Will be initially used as a development aid and a test tool for
seeing how file handling works.

Some of the planned command-line functionality:

* Validate `.dictx` document against schema
* Merge `.dictx` files
* Convert `.dictx` to plain text `dictd` files

### DictionaryDocument (ConmanDictX.dll)

Classes representing the data model of dictionary documents, with
code for saving and loading `.dictx` files, and validating
the `.dictx` document contents against the schema definition.

### Legacy stuff

All of the legacy Java code is (for now!) stored in the
`obsolete` folder, and is purely used for reference
(and will be gone once the work to reimplement the code in C# is done).
Note that the Java code living in `obsolete` folder is not guaranteed
to build at all. Instead, consult the tag `1.0X_JDESKTOP`. It will *also*
probably not build, but it's probably not confusing the matter!
