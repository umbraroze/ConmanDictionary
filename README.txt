Conman's Dictionary
===================

Overview
--------

Conman's Dictionary is a dictionary application. Currently, it is best
geared for armchair linguists who are working on constructed languages
(hence the name). It probably will not be that good if it's used for
any other purpose. The program shows two word lists at a time
(Language 1 to Language 2, and vice versa). It uses its own XML file
format, and can export the dictionary in the plain text format used by
dictd.

Conman's Dictionary is distributed under the
GNU General Public License version 3 (http://www.gnu.org/copyleft/gpl.html).
The principal developer is Urpo "WWWWolf" Lankinen (wwwwolf@iki.fi).

Requirements
------------

The project is developed in Java 7 SE, and is known to work on Sun's
JDK.  Possibly might work on OpenJDK/IcedTea 6, though I haven't tried
it. In all likelihood it will run on OpenJDK 7, but I haven't actually
tested it yet there. 

I have deprecated the Java 5 support. This isn't a gigantic problem, but
PPC OS X fans will probably have to hope OpenJDK7's PPC port is revived...

The project is developed on NetBeans (http://netbeans.org/) and uses
Maven (http://maven.apache.org/) for building stuff. Maven should
automatically fetch all of the dependencies. The big major external dependency
is the gettext-commons package (http://code.google.com/p/gettext-commons/),
which lets you use standard GNU gettext tools for localising Java software.
This tool depends on GNU gettext, which is a standard-issue tool in Linux;
Windows binaries are available in
(http://gnuwin32.sourceforge.net/packages/gettext.htm) and may
require some path-setting. (Win+Pause, Advanced, Environment Variables.
You know the drill.)
