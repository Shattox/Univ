#!/usr/bin/perl

use strict;
use warnings;

open(my $fd, '<', 'passwd') or die("open: $!");

while (defined(my $line = <$fd>)) {
    chomp $line;
    #if ($line =~ m/^jc:/) {
    #    print("$line\n");
    #}
    #if ($line !~ m/bash$/) {
    #    print("$line\n");
    #}
    #$line =~ s@/home/@/mnt/home/@;
    #print("$line\n");
    #$line =~ s/:.*?:/:/;
    #print("$line\n");  
    #$line =~ s/(.*?):(.*?):/$2:$1/;
    #print("$line\n");
    #$line =~ s/((?:.*?:){2})(.*?):(.*?):/$1$3:$2:/;
    #print("$line\n");
    if (my ($gid) = $line =~ m/(?:.*?:){3}(.*?):/) {
        print("$gid\n");
    }
}

close($fd);

