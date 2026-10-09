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

Conman's Dictionary 2.1+ is a Java application.
Currently, it is developed and tested on
OpenJDK 25
(specifically, I'm using [Adoptium](https://adoptium.net/) on Windows.)

I'm using [IntelliJ IDEA](https://www.jetbrains.com/idea/), but the build
process probably doesn't have anything IDE-specific in it.

The project uses the [Gradle](https://gradle.org/) build tool.

## Source organisation

I'm bringing back my original Java codebase here.

My odyssey of failure in C# and Rust is temporarily stored in `obsolete` folder.

Currently the old Java GUI code lives in the main `src` folder.

The program is divided into these modules:

* `document`
  * The document data classes. How the data is stored in memory,
    saved to disk as `.dictx` XML files, and loaded from them.
    Also code that validates the structure of `.dictx` documents.
* `conmandictionary`
    * This will eventually be the GUI application.
* ???
    * The command-line application will go into a module of its own
      eventually as well.

### Last "good" build

The last version of the Java desktop app that was known to
build in Java 6 SE JDK can be found via the
`1.0X_JDESKTOP` tag.

This legacy codebase was developed in Java 6 days, and depends on
stuff that has since been moved from stock JDK and JRE to external
dependencies, so it will not build or run on modern Java environments.
