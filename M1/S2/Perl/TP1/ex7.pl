#!/usr/bin/perl

use strict;
use warnings;

sub Intervalle {
    my ($n, $x) = @_;

    return grep { $_ != $x } (1..$n);
}

my $result = join(', ', Intervalle(10, 4));
print("$result\n");

sub NonMult {
    my ($n, $x) = @_;

    return grep { ($_ % $x) != 0 } (1..$n);
}

my $result = join(', ', NonMult(10, 4));
print("$result\n");