package Soiree;

use Moose;

use parent qw(Person);

has CAPACITY => (is => 'ro', isa => 'Int', required => 1);
has POTES => (
    is => 'rw',
    isa => 'ArrayRef[Person]'
    default => sub{[]},
    auto_deref => 1,
    traits => ['Array'],
    handless => {
        enter => 'push',
        expulser => 'pop',
        nbPotes => 'count',
    },
);

sub fete {
    my ($self) = @_;

    foreach my $p ($self->potes()) {
        print($p->lastName()."\n");
    }
}

before enter => sub {
    my ($self, $p) = @_;

    print($p->plastName()."\n");
}

after enter => sub {
    my ($self) = @_;

    if ($self->nbPotes > $self->capacity) {
        my $p = $self->expulser();
        print("$p->lastName dehors\n");
    } else {
        print("Welcome !\n");
    }
}

1;
