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
