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

It *should* still work. ...Unless things have
changed *really* dramatically in Java land since 2013.
In particular, you may need to add JAXB support back in since
it's no longer shipped with Java SE platform. Maybe some
other components have fallen off the wagon along the way.
I've not really kept up with the latest developments in
Java desktop development, so I can't say.
