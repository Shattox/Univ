#!/usr/bin/perl

use strict;
use warnings;

my %uid = ();

open(my $fd, '<', 'passwd') or die("open: $!");

while (defined(my $line = <$fd>)) {
    chomp $line;
    my ($login, undef, $uid) = split(/:/, $line);
    $uid{$login} = $uid;
}

close($fd);

# Version sans trie
foreach my $login (keys %uid) {
    print("$login => $uid{$login}\n");
}

# Version avec trie alphabétique
foreach my $login (sort keys %uid) {
    print("$login => $uid{$login}\n");
}

# Version avec trie ordre croissant uid
foreach my $login (sort {$uid{$a} <=> $uid{$b}} keys %uid) {
    print("$login => $uid{$login}\n");
}

# Version avec trie affiner
foreach my $login (sort {$uid{$a} <=> $uid{$b} or $a cmp $b} keys %uid) {
    print("$login => $uid{$login}\n");
}
