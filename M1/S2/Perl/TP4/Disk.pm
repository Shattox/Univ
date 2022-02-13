package Disk;

use warnings;
use strict;
use Math::Trig;

sub new {
    my ($class, $x, $y, $r) = @_;

    my $self = {
        X => $x // 0,
        Y => $y // 0,
        R => $r // 1,
    };

    bless($self, $class);

    return $self;
}

sub surface {
    my ($self) = @_;

    return pi * ($self->{R} ** 2);
}

sub dump {
    my ($self) = @_;

    return "Disk : ".$self->{X}.", ".$self->{Y}.", ".$self->{R};
}

1;