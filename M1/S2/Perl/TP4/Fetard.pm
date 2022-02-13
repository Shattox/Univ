package Fetard;

use Moose:Role;

has boisson => (is => 'rw', isa => 'Str');

sub boire {
    my ($self, $boisson) = @_;

    print("boisson préféré : $boisson\n");
    $self->delirer();
}
requires 'delirer';

1;