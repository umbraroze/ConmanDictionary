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
