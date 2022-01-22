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
        if (exists($urls{$url})) {
            $urls{$url} ++;
        } else {
            $urls{$url} = 1;
        }
        if (exists($ips{$ip})) {
            $ips{$ip} = [$ips{$ip}[0] + 1, $ips{$ip}[1] + $volume];
        } else {
            $ips{$ip} = [1, $volume];
        }
    }
}

close($fd);

print("Requests : $requests\n");
print("Errors : $errors\n");
print("Volume : $volumes\n\n");

foreach my $url (sort {$urls{$b} <=> $urls{$a}} keys %urls) {
    print("$url : $urls{$url}\n");
}

print("\n");

my $counter = 0;

foreach my $ip (sort {$ips{$b}[0] <=> $ips{$a}[0]} keys %ips) {
    if ($counter++ == 10) { last; }
    my $access = $ips{$ip}[0];
    my $volume = $ips{$ip}[1];
    print("$ip : access=$access volume=$volume\n");
}