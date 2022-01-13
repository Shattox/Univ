#!/usr/bin/perl

use strict;
use warnings;

sub Fact {
    my ($n) = @_;

    if ($n == 0) {
        return 1;
    }
    return $n * Fact($n - 1);
}

my $fact = Fact($ARGV[0] // 10);

print("$fact\n");

sub Fibo {
    my ($n) = @_;

    my @array = (0, 1);

    foreach my $i (2..$n) {
        @array = (@array, $array[-2] + $array[-1]);
    }
    return @array;
}

my @fibo = Fibo($ARGV[0] // 5);
my $last_fibo = $fibo[-1];

printf("@fibo\n"); # Display all of the elements of the array.
print("$last_fibo\n"); # Display the last element of the array.