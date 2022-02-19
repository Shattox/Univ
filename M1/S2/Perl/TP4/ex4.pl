#!/usr/bin/perl
use warnings;
use strict;
use lib '.';
use Data::Dumper;

use Person;

my $p1 = Person->new(lastName => 'Steve', 'Tea');
my $p2 = Person->new(lastName => 'Jean', 'Coca');
my $s = Soiree->new(capacity => 3);

$s.enter($p1);
$s.enter($p2);
print(Dumper($s));