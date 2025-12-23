---
layout: default
title: Build instructions
permalink: /build.html
---

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

## Building the C# project

As of now, all I can say "just use `dotnet build` or build the
thing with Visual Studio." ...I'm not sure there's an actual reason
to do that as of now, however!

The 2.0 total C# rewrite is *NOT* yet functional. At all.
Well, there's some code, but it doesn't really do much yet
for the end-user.

## Building the legacy Java code

Hoo boy, this is going to hurt!

If you absolutely need the Java branch, you need to grab it from
the appropriate Git tag (`1.0X_JDESKTOP`) and then build it
using Apache Maven.

The project was developed on Java SE 6, circa 2013. Building the
Java project in this day and age is probably going to be *kinda*
challenging, particularly if you are targeting modern versions of
Java.

I have *not* tried to actually build the project, but based on light
research, the big sticking point is that Java XML Binding (JAXB)
is no longer bundled with OpenJDK.

Trying to forcibly duct-tape in the JAXB class files by hand and
run the thing on modern JRE that ways was... er, an event we
shouldn't speak of no more.

That is the point where I leave the attempt to people who have kept
more up with the recent happenings in Java land.

I'm not saying it's impossible, however - the appropriate JAXB JARs
are apparently still in Maven's repository, and the big reason why
I introduced Maven in the first place was that I needed some way to
fetch the JDesktop App Framework stuff when they were excised from
Java SE the exact same way.
