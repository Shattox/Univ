#!/usr/bin/perl

use strict;
use warnings;

sub SommeTest {
    my ($x, $y, $n) = @_;
    return $x.$y+$x == $n;
}

my $result = SommeTest($ARGV[0], $ARGV[1], $ARGV[2]);

if ($result) {
    print("true\n");
} else {
    print("false\n");
}