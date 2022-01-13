#!/usr/bin/perl

use strict;
use warnings;

sub TableMult1 {
    my ($n) = @_;

    for (my $i = 1; $i <= $n; $i++) {
        for (my $j = 1; $j <= $n; $j++) {
            printf("%5d", $i * $j);
        }
        printf("\n");
    }
}

TableMult1(4);
print("\n");

sub TableMult2 {
    my ($n) = @_;

    foreach my $i (1..$n) {
        foreach my $j (1..$n) {
            printf("%5d", $i * $j);
        }
        printf("\n");
    }
}

TableMult2(4);
print("\n");

sub TableMult3 {
    my ($n) = @_;
    my $result = "";

    foreach my $i (1..$n) {
        foreach my $j (1..$n) {
            $result .= sprintf("%5d", $i * $j);
        }
        $result .= sprintf("\n");
    }
    printf("$result");
}

TableMult3($ARGV[0] // 10); # Valeur par défaut 10.