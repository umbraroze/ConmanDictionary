# Conman's Dictionary

## Overview

Conman's Dictionary is a dictionary application. It is primarily
geared for armchair linguists who are working on constructed languages
(hence the name). It probably will not be that good if it's used for
any other, more serious and more comprehensive use.

The application has two word lists at a time so you can maintain
translations both ways. Both of the lists have headwords and
translations to another language, word classes, and categorisation
options. You can flag words that need attention.

The application uses its own XML file format, and can export the
dictionary in the plain text format used by dictd. (Ostensibly.
I don't actually use dictd, you see; I just need it to be in
plain text format.)

Conman's Dictionary is distributed under the
[GNU General Public License version 3](http://www.gnu.org/copyleft/gpl.html).
It is developed by Rose Midford, primarily for the Avarthrel
worldbuilding project.

## A really important point

This is less of a serious tool designed for a diverse group of
users with a lot of use-cases and user research... and more of
a tool designed for my specific use-cases based solely on "dang,
wouldn't it be neat if it did that" feels.

I've received *zero* user feedback of any kind, as far as I can
remember. I'm pretty sure no one *actually* uses the
application - but if you do, please let me know! Once the C#
version is release-worthy, and hits the same functional threshold
as the Java version had, maybe I can start cautiously advertise
the fact that this thing is a thing and consider that maybe
someone would have further ideas on how to improve it. But
as things stand now, I'm reluctant to go there just yet.

Above all, however, this tool was a way for me to learn Java
development and various related technologies. Now, it will
be a way for me to learn C# development and various related
technologies.

## Requirements

Version 2.0 is a complete rewrite of the application in C#.
It's trying to target the open .NET APIs/platforms whenever
possible.

Aside of the standard C# stuff, here are some choice Nugettable
packages the project has needed so far:

* Avalonia
* ReactiveUI
* System.CommandLine
* NUnit

## Quick build instructions

As of now, all I can say "just use `dotnet build` or build the
thing with Visual Studio." ...I'm not sure there's an actual reason
to do that as of now, however!

The 2.0 total C# rewrite is *NOT* yet functional. At all.
Well, there's some code, but it doesn't really do much yet
for the end-user.

If you absolutely need the Java branch, you need to grab it from
the appropriate Git tag (`1.0X_JDESKTOP`) and then build it
using Apache Maven. It *should* still work. ...Unless things have
changed *really* dramatically in Java land since 2013.

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

All of the legacy Java code is stored in the "obsolete" folder,
and is purely used for reference (and will be gone once the
work to reimplement the code in C# is done).

## History

The application was originally written between 2006 and 2012 as a
cavalcade of me learning about new and interesting Java software
development thingies. New software version control systems!
Build systems! (Ant) Build systems that were complete and utter
overkill for what I was doing! (Maven) Ways to clean up the
separation of app code and data (Swing Document Model)!
New amazing ways to manage data and its XML representation
(Java XML Binding)! New amazing GUI frameworks!...

Unfortunately, I made a really bad bet with the latter one. The
app originally used bog-standard Swing, but NetBeans promised a
bright new future: Matisse interface builder, and a new exciting
Java GUI application system, Swing Application Framework (JSR 296).
...and of course, now that Oracle is managing things, they decided
to straight up drop the nice things.

Now, I *had* a build of the application from early 2012, so I was
not really in any hurry of getting the damn thing back on track
again. So I investigated a few other options, including making the
app build and *vaguely* look like a thing on IntelliJ IDEA, and
trying to make vague sense of JavaFX, which is supposedly the new
and hot way to make Java desktop apps.

Buuuut. The major problem was this:

1. The Java version had been the result of heavy modifications
   over time and thus had a few features that could be politely
   described as "not very clean". Despite the fact that the app
   design was *fairly* sensible, I genuinely couldn't even wrap
   my head around some of the bits.
2. I really wanted to use a stable, sensible GUI API - Swing fit
   that bill. I wanted the GUI to have a stable declarative GUI
   markup language. Swing most clearly didn't fit that bill. I
   wasn't sure if Oracle was even all that interested in keeping
   Java a highly relevant desktop programming language.

Ultimately, I thought that the easiest and finest way to fix the
convoluted and prototypesque mess would be to just rewrite the
app from ground up.

After tossing various ideas, in 2018, I decided to use
C#, .NET Framework and settled on using Avalonia for the UI.
Then, suddenly, after a few more years, in 2023, I decided to
actually start to write some real vaguely working code, because
I've *clearly* procrastinated enough with this project.

So this is the start of version 2.0 - even though technically the
Java version didn't even reach 1.0. This is basically where we
stand now.

