#!/usr/bin/perl

use strict;
use warnings;
use Data::Dumper;

sub mygrep {
    my ($ref, @values) = @_;
    my @t;

    foreach my $value (@values) {
        if ($ref->($value)) {
            @t = (@t, $value);
        }
    }
    return @t;
}

sub positif {
    my ($e) = @_;
    return $e > 0;
}

my @t = mygrep \&positif, 43,654,-43,34,32,-23,1,2,-8;

print(join(", ", @t)."\n");

sub mymap {
    my ($ref, @values) = @_;
    my @t;

    foreach my $value (@values) {
        @t = (@t, $ref->($value));
    }

    return @t;
}

sub double {
    my ($e) = @_;
    return $e * 2;
}

my @t2 = mymap \&double, @t;

print(join(", ", @t2)."\n");

sub mysort {
    my ($ref, @values) = @_;

    for (my $i = 0; $i < @values - 1; $i++) {
        for (my $j = $i + 1; $j < @values - 0; $j++) {
            if ($ref->($values[$i], $values[$j]) > 0) {
                my $tmp = $values[$i];
                $values[$i] = $values[$j];
                $values[$j] = $tmp;
            }
        }
    }
    return @values;

}

sub croissant {
    my ($a, $b) = @_;
    return $a <=> $b;
}

my @tt = mysort \&croissant, @t2;

print(join(", ", @tt)."\n");

my @tab = mysort \&croissant, mygrep \&positif, mymap \&double, @tt;

print(join(", ", @tab)."\n");
