#!/usr/bin/perl

use strict;
use warnings;

my @t = (0..10);
print("@t\n");
my @s = splice( @t, 0, 5, (5,4,3,2,1) ); # Supprime et renvoie (ādā,āeā)

print("@t\n");
print("@s\n");

@t = qw/ attention 'aux erreurs' stupides /;

print(join(", ", @t)."\n");