#!/usr/bin/perl

use strict;
use warnings;

my @array = (4, -5, 7);
@array = (@array, -2, 3);
my @result = join(', ', @array);
print("@result\n");
@array = (0, -1, @array);
@array[3] = 9;
@array = map { $_ * 2 } @array;
@array = grep { $_ > 0 } @array;
@array = sort { $a <=> $b } @array;
print("@array\n");