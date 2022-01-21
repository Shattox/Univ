#!/usr/bin/perl

use strict;
use warnings;

my $requests = 0;
my $errors = 0;
my $volumes = 0;
my %urls = ();
my %ips = ();

open(my $fd, '<', 'access_log') or die("open: $!");

while (defined(my $line = <$fd>)) {
    chomp $line;
    $requests++;
    if (my ($ip, $url, $status, $volume) = $line =~ m/^(.*?) .*?".*? (.*?) .*?" (.*?) (.*?) /) {
        if ($status != 200) {
            $errors++;
        }
        $volumes += $volume;
    }
}

close($fd);

print("$requests\n");
print("$errors\n");
print("$volumes\n");
