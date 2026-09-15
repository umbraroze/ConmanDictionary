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

Conman's Dictionary 2.1+ is a Java 25 application.

Developed in IntelliJ, with Gradle.

## Source organisation

I'm bringing back my original Java codebase here.

My odyssey of failure in C# and Rust is temporarily stored in `obsolete` folder.

### Last "good" build

The last version of the Java desktop app that was known to
build in Java 6 SE JDK can be found via the
`1.0X_JDESKTOP` tag.

This legacy codebase was developed in Java 6 days, and depends on
stuff that has since been moved from stock JDK and JRE to external
dependencies, so it will not build or run on modern Java environments.
