#!/usr/bin/perl
use warnings;
use strict;

use Disk;
use Ring;

use Data::Dumper;

my $disk1 = Disk->new(3, 4, 10);
my $disk2 = Disk->new();

print(Dumper($disk1, $disk2));

print($disk1->surface()."\n");
print($disk2->surface()."\n");

print($disk1->dump()."\n");
print($disk2->dump()."\n");

my $ring1 = Ring->new(3, 4, 10, 5);
my $ring2 = Ring->new(3, 4, 10);

print($ring1->surface()."\n");
print($ring2->surface()."\n");

print($ring1->dump()."\n");
print($ring2->dump()."\n");