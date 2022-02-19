package Person;

use Moose;

with 'Fetard';

has LASTNAME => (is => 'ro', isa => 'Str');

sub delirer {
    print("délire !\n");
}

1;