#!/usr/bin/perl

use strict;
use warnings;

my @files = glob("~/.*");

@files = grep { not -x $_ } @files;

@files = sort { -s $a <=> -s $b } @files;

my @sizes = map { -s $_ } @files; 

print(join("\n", @files)."\n");