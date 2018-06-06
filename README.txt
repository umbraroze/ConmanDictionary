Conman's Dictionary
===================

Overview
--------

Conman's Dictionary is a dictionary application. It is primarily
geared for armchair linguists who are working on constructed languages
(hence the name). It probably will not be that good if it's used for
any other, more serious and more comprehensive use.

The application has two word lists at a time so you can maintain
translations both ways. Both of the lists have headwords and
translations to another language, word classes, and categorisation
options. You can flag words that need attention.

The application uses its own XML file format, and can export the
dictionary in the plain text format used by dictd.

Conman's Dictionary is distributed under the
GNU General Public License version 3 (http://www.gnu.org/copyleft/gpl.html).
It is developed by Urpo Lankinen (wwwwolf@iki.fi), primarily for the
Avarthrel (http://www.avarthrel.org/) worldbuilding project.

Requirements
------------

Version 2.0 is a complete rewrite of the application in C#,
currently targeting .NET Framework 4.6.
(The original version was a Java application, but unfortunately,
it had become a big mess that resisted my attempts at fixing it.
Also, while I loved Swing, dealing with GUIs in Java is always
pretty darn tricky. We'll see how many headaches XAML will solve.)

Quick build instructions
------------------------

Currently there's no fancy build tool support, you just open this
puppy in Visual Studio and build it up.

