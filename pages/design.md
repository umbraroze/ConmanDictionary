---
layout: default
title: Design and history
permalink: /design.html
---

## History

### The Java Years

The application was originally written between 2006 and 2012 as a
cavalcade of me learning about new and interesting Java software
development thingies.

- New software version control systems!
- New build systems! (Apache Ant)
- Build systems that were complete and utter overkill for what
  I was doing! (Apache Maven)
- Ways to clean up the separation of app code and data
  (Swing Document Model)!
- New amazing ways to manage data and its XML representation
  (Java XML Binding)!
- New amazing GUI frameworks!... oh wait.

Unfortunately, I made a really bad bet with the latter one. The
app originally used bog-standard Swing, but NetBeans promised a
bright new future: Matisse interface builder, and a new exciting
Java GUI application system, Swing Application Framework (JSR 296).

...and of course, now that Oracle is managing things, they decided
to straight up drop the nice things. Actually, it turns out that
later, they even dropped JAXB from the standard JDK.

Now, I *had* a build of the application from early 2012, so I was
not really in any hurry of getting the damn thing back on track
again. Around 2016-2022 I also had a bit of a creative writing lull
where I didn't really work on worldbuilding stuff all that much.

So I investigated a few other options, including trying out other
Java UI frameworks (including JavaFX, which at the time seemed
like the preferred way of building Java desktop apps).

Buuuut. The major problem was this:

1. The Java version had been the result of heavy modifications
   over time and thus had a few features that could be politely
   described as "not very clean". Despite the fact that the app
   design was *fairly* sensible, I genuinely couldn't even wrap
   my head around some of the bits.
2. I really wanted to use a stable, sensible GUI API - Swing fit
   that bill. I wanted the GUI to have a stable declarative GUI
   markup language. Swing most clearly didn't fit that bill.
3. I wasn't sure if Oracle was even all that interested in keeping
   Java a highly relevant desktop programming language. Sure, it's
   still big in server space, and of course Java is kept relevant
   by Android, but aside of a few exceptions, people don't really
   build a whole bunch of big new Java desktop applications
   these days.

Ultimately, I thought that the easiest and finest way to fix the
convoluted and prototypesque mess would be to just rewrite the
app from ground up.

## Move to C# and .NET

After tossing various ideas, in 2018, I decided to use
C#, .NET Framework and settled on using Avalonia for the UI.

Then, suddenly, after a few more years, in 2023, I decided to
actually start to write some real vaguely working code, because
I've *clearly* procrastinated enough with this project.

And in 2025, having not heard myself the previous time,
I decided to actually start stopping procrastinating \[sic\] and
actually work on the darn rewrite project.

Previously, I just kept getting demotivated every time I had to
open up Visual Studio, but as it turns out, apparently C# is
actually a fun language to code in in Visual Studio Code.

So this is the start of version 2.0 - even though technically the
Java version didn't even reach 1.0. This is basically where
we stand now.

## Current big technological choices (as explained by a Java refugee and a C# neophyte)

### XML reading and serialisation

I gave up trying to find a solution similar to JAXB, and instead
use custom XElement / LINQ conversion methods.

Writing custom XML reader was a massive headache in the Java 1.4 to 6
days. So JAXB was pretty much a revelation - your classes can correspond
to the XML schema.

But now, in C#, implementing a custom method was actually faster than
trying to find a binding framework that actually seemed sensible.
LINQ makes custom stuff like this a lot more elegant, and building
stuff out of XElement can be cleanly split into the classes that
represent stuff.

### User interface

Avalonia UI. I've yet to wrap my head around how this thing actually
works on the daily, but it seems to be fairly sensible so far.

ReactiveUI seems to be the solution that I'm looking for if I want a
user interface that meddles with the data.

### Testing

NUnit. It's basically like all testing frameworks. It seems sensible.
