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

The project is developed in Java 7 SE, and is known to work on
both Oracle Java 7 runtime on Windows and OpenJDK 7 on Linux.

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

Quick build instructions
------------------------

If you just check this thing out of Git, open this thing in NetBeans and try
to compile it, it'll fail. Gettext is funny that way.

Full build instructions will be in Conman's Dictionary wiki at:
https://gitorious.org/conmandictionary/pages/Building
However, since there's no much magic involved, here's he relevant part:

  $ mvn clean gettext:dist install

Then look for all the proper .jars in your Maven directory (in Linux,
this is ~/.m2/ ).

After you've done this, it should be also possible to hack further with
NetBeans. You'll probably need to do the gettext:dist step if you ever mess
with the localisation stuff.

