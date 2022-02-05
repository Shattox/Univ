#!/usr/bin/perl

use strict;
use warnings;
use Data::Dumper;

sub parse {
    my ($fname) = @_;
    my $ref = {};

    open( my $fd, '<' , $fname ) or die( "open: $!" );

    while( defined( my $line = <$fd> ) ) {
        chomp $line; # Supprime la fin de ligne
        my ($login, $passwd, $uid, $gid, $info, $home, $shell) = split /\:/, $line;
        $ref->{$login} = {passwd => $passwd,
                          uid    => $uid,
                          gid    => $gid,
                          info   => $info,
                          home   => $home,
                          shell  => $shell};
    }
    close( $fd );

    return $ref;
}

sub display1 {
    my ($ref) = @_;

    foreach my $login ( keys( %{$ref} ) ) {
        print("-----------------------------------\n");
        print("login : $login\n");
        print("passwd : $ref->{$login}{passwd}\n");
        print("uid : $ref->{$login}{uid}\n");
        print("gid : $ref->{$login}{gid}\n");
        print("info : $ref->{$login}{info}\n");
        print("home : $ref->{$login}{home}\n");
        print("shell : $ref->{$login}{shell}\n");
    }
}

sub display2 {
    my ($ref) = @_;

    foreach my $login (sort { $a cmp $b } keys %{$ref}) {
        print("-----------------------------------\n");
        print("login : $login\n");
        print("passwd : $ref->{$login}{passwd}\n");
        print("uid : $ref->{$login}{uid}\n");
        print("gid : $ref->{$login}{gid}\n");
        print("info : $ref->{$login}{info}\n");
        print("home : $ref->{$login}{home}\n");
        print("shell : $ref->{$login}{shell}\n");
    }
}

sub display3 {
    my ($ref) = @_;

    foreach my $login (sort { $ref->{$b}{uid} <=> $ref->{$a}{uid} } keys %{$ref}) {
        print("-----------------------------------\n");
        print("login : $login\n");
        print("passwd : $ref->{$login}{passwd}\n");
        print("uid : $ref->{$login}{uid}\n");
        print("gid : $ref->{$login}{gid}\n");
        print("info : $ref->{$login}{info}\n");
        print("home : $ref->{$login}{home}\n");
        print("shell : $ref->{$login}{shell}\n");
    }
}

my $ref = parse('passwd');

# print(Dumper($ref));

# display1($ref);

# display2($ref);

display3($ref);