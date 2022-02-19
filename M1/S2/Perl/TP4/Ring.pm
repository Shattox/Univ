package Ring;

use warnings;
use strict;
use Math::Trig;

use parent qw(Disk);

sub new {
    my ($class, $x, $y, $r, $ri) = @_;

    my $self = $class->SUPER::new($x, $y, $r);
        
    $self->{RI} = $ri // 0;

    bless($self, $class);

    return $self;
}

sub surface {
    my ($self) = @_;

    return $self->SUPER::surface() - (pi * ($self->{RI} ** 2));
}

sub dump {
    my ($self) = @_;

    return "Ring : ".$self->{X}.", ".$self->{Y}.", ".$self->{R}.", ".$self->{RI};
}

1;