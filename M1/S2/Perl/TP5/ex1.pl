#!/usr/bin/perl

use warnings;
use strict;

use POSIX qw(strftime);

my $str = strftime('%A', 0, 0, 0, 17, 7, 2000 - 1900);

print("$str\n");