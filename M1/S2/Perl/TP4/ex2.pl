#!/usr/bin/perl
use warnings;
use strict;

use MyModule;
use MyModule qw(TableMult3);

my $result = TableMult3(5);

print("$result\n");