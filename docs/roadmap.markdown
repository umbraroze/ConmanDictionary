---
layout: default
title: Roadmap
permalink: /roadmap.html
---

(This should probably be turned into a GitHub Kanban...)

- Get the data model into a workable state ✅
- Get the data saving working via Data Contracts ❎
  - Hey, here's an idea, let's use LINQ instead! Writing
    XML parsers doesn't suck in C#, turns out.
- Beginnings of a command line tool ✅
  - individual operations should be able to be performed via the
    CLI tool, if only for the test/dev purposes.
  - Check out C#'s command line parser options.
    - Turns out Microsoft already has a really nice one! ✅
- Store the XML Schema as a Resource. ✅
- Validate document via XML Schema. ✅
- Saving the document via LINQ ✅
- Loading the document via LINQ 🆙
- Rethink the DictX Schema to arrive at a "1.0" version,
  particularly in regards to the "upcoming features" that were
  never implemented in Java version 🆙
- NUnit tests for
  - validation ✅
  - saving and loading
  - procedural generation
  - document / document element comparison
- Document the DictX file format and the corresponding
  data model somewhere somehow. Particularly the features
  that were never actually implemented in Java. 🆙
- Documentation: UML models. 🆙
