---
layout: default
title: Old Scripts
permalink: /oldscripts.html
---

Long story short: Long ago, I made some *really quick and dirty*
scripts that did various things.

And ever since, the fact that Perl scripts are used in this repository
has haunted the GitHub statistics.

So in the end of 2025, I decided that I should not give false hope to
people who were looking for a hardened Perl expert. I fear someone might
say "ooo, this repository uses Perl -- maybe it was written by a
real Perl monk." Truth is, I've not touched Perl in *ages*.

Plus these scripts really shouldn't be paraded as, you know, *useful*
things. These are pretty useless. But maybe they should be stuck
somewhere just as historical thingies.

So here are the scripts that I originally stuck in the `utils` directory.
If you *really* need these, look at the Markdown source at
`docs/oldscripts.md` and copy-paste this stuff. Or something.
These are pretty useless. I don't think anyone needs these.

Some plain-text Unix dict tools will eventually appear in the C# version,
at least I hope.

## `README`

```text

This directory contains random script files to handle conversions
from obsolete formats, and possibly to work with dictd-style dictionary
files. Note that these were pretty much developed for one-shot dealies
and are not particularly ingenious or well-tested. Unless otherwise
mentioned, the scripts in this directory are in public domain (not GPL3)
and you accept that you're using them entirely on your risk. -wwwwolf

```

## `dict2dictx.pl`

```perl
#!/usr/bin/perl
######################################################################
# This will convert a pair of dictionaries to Conman's Dictionary XML format.
#
# wwwwolf 2006-09-28
######################################################################

use strict;
use warnings;

our (%d);

my $word = '';
my $def = '';
while (<>) {
  chomp;
  next if($_ eq '');
  if(/^\s+/) {
    $def .= "$_\n";
  }
  else {
    if($word eq '') {
      $word = $_;
    } else {
      if(!exists $d{$word}) {
        $d{$word} = [];
      }
      push @{$d{$word}}, $def;
      $word = $_;
      $def = '';
    }
  }
}
if(!exists $d{$word}) {
  $d{$word} = [];
}
push @{$d{$word}}, $def;

foreach $word (sort keys %d) {
  foreach $def (@{$d{$word}}) {
    printf "<entry><term>$word</term><definition>$def</definition></entry>\n";
  }
}

```

## `dictsort.pl`

```perl
#!/usr/bin/perl
######################################################################
# This will sort a dictionary file that is in format
#
#   headline
#        definition blah blah...
#
#   headline
#        definition blah blah...
#
# And take into account that there can be multiple definitions with
# the same headword.
#
# wwwwolf 2006-09-11
######################################################################

use strict;
use warnings;

our (%d);

my $word = '';
my $def = '';
while (<>) {
  chomp;
  next if($_ eq '');
  if(/^\s+/) {
    $def .= "$_\n";
  }
  else {
    if($word eq '') {
      $word = $_;
    } else {
      if(!exists $d{$word}) {
        $d{$word} = [];
      }
      push @{$d{$word}}, $def;
      $word = $_;
      $def = '';
    }
  }
}
if(!exists $d{$word}) {
  $d{$word} = [];
}
push @{$d{$word}}, $def;

foreach $word (sort keys %d) {
  foreach $def (@{$d{$word}}) {
    printf "$word\n$def\n";
  }
}
```

## `reclassify-words.rb`

```ruby
#!/usr/bin/ruby
# Used to clean up old-style adhoc definitions to new-style word classes.

require 'rexml/document'

dictionary = REXML::Document.new(File.open(ARGV[0]))

dictionary.elements.each('//entry') do |e|
  t = e.elements['definition'].text
  if(t =~ /^(n|a|v|pr|p)\.\s+/)
    e.attributes['class'] = case $1
                            when 'n' then 'Noun'
                            when 'a' then 'Adjective'
                            when 'v' then 'Verb'
                            when 'pr' then 'Pronoun'
                            when 'p' then 'Particle'
                            end
    t.gsub!(/^(n|a|v|pr|p)\.\s+/,'')
    e.elements['definition'].text = t
  end
end
puts dictionary
```
