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
[Conman's Dictionary home page at GitHub Pages](https://umbraroze.github.io/ConmanDictionary/).

## Dependencies

Conman's Dictionary 2.1+ is a Rust program.

I'm developing this in Windows with regular Rust install
with the `stable-x86_64-pc-windows-gnu` toolchain, so all of the
dependencies will look at MSYS2.

MSYS2 C/C++ packages needed:

- `mingw-w64-ucrt-x86_64-gtk4`
- `mingw-w64-ucrt-x86_64-toolchain`
- `base-devel`

You probably can compile this on Linux too, as long as you have
the regular C/C++ toolchain and the GTK 4 development files and their
dependencies.

## Source organisation

Currently, the application is split in a few different sub-crates:

### conmandictionary

The GUI application, using GTK 4. Doesn't have much yet, unfortunately.

Planned functionality:

- The usual dictionary editing and management commands
- Word class and category editors
- Notepad editor
- Merge dictionaries

### dict-tool

A command-line tool for handing `.dictx` files. Not much here either.
Will be initially used as a development aid and a test tool for
seeing how file handling works.

Some of the planned command-line functionality:

- Command to validate `.dictx` documents against schema
- Merge `.dictx` files
- Convert `.dictx` to plain text `dictd` files

### dictx

Code for handling dictionary data.

- Parse and load `.dictx` files
- Save `.dictx` files
- Validate `.dictx` files against XSD schema

### Legacy stuff

Legacy code is temporarily stored in `obsolete` folder as a
reference for the Rust port project.

#### Java

All of the legacy Java code ("version 1.x") can be found 
in `obsolete/java` for reference.

The last version of the Java desktop app that was known to
build in Java 6 SE JDK can be found via the
`1.0X_JDESKTOP` tag.

This legacy codebase was developed in Java 6 days, and depends on
stuff that has since been moved from stock JDK and JRE to external
dependencies, so it will not build or run on modern Java environments.

#### C#

The "version 2.0" C# code, which really didn't get that far over the
years, is now temporarily stored in `obsolete/csharp`.  Likewise, it
will be gone once the Rust version works adequately.


