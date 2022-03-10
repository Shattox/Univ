#!/usr/bin/perl

use warnings;
use strict;

use POSIX qw(strftime);

my ($uid, $mtime) = (stat($ENV{HOME}))[4, 9];

my $last_modif = strftime('%Y/%m/%d %H:%M:%S', localtime($mtime);
my $login = (getpwuid($uid))[0];

print("last modif -> $last_modif\n");
print("login -> $login\n");