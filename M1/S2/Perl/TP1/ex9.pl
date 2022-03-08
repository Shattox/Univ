#!/usr/bin/perl

use strict;
use warnings;

sub Modif {
    my ($text, $old, $new) = @_;

    $text =~ s/$old/$new/g; 

    return $text;
}

my $text = Modif( 'bonjour vous, bonjour', 'bonjour', 'allo');

print($text."\n");
