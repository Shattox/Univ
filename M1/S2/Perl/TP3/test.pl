#!usr/bin/perl

use warnings;
use strict;


my $text = "salut [a] tous je suis [la] héhé";

my $text =~ m/.*(^[]$)$/;