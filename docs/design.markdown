---
layout: page
title: Design and history
permalink: /design.html
---

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
