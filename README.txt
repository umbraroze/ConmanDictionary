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

The project is developed in Java 6 SE, and is known to work on Sun's
JDK.  Possibly might work on OpenJDK/IcedTea, though I haven't tried
it.

The program may *run* on Java 5, but in retrospect I'm probably
deprecating the Java 5 support. PPC OS X fans will probably have to
hope OpenJDK7's PPC port is revived. =)

The project is developed on NetBeans (http://netbeans.org/).
You probably need Ant (http://ant.apache.org/) and
Swing Application Framework (https://appframework.dev.java.net/)
if you're not compiling it on NetBeans, but I don't know the
exact procedures. In future, I'll transforminate the whole thing
to use Maven.

For l10n, I'm using gettext-commons
(http://code.google.com/p/gettext-commons/), which lets
you use standard GNU gettext tools for localising Java software.
This library and its associated Ant tasks are bundled in the "lib"
directory, and you don't need to download them separately.
This tool depends on GNU gettext, which is a standard-issue tool in Linux;
Windows binaries are available in
(http://gnuwin32.sourceforge.net/packages/gettext.htm) and may
require some path-setting. (Win+Pause, Advanced, Environment Variables.
You know the drill.)
