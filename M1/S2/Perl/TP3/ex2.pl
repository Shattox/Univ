#!/usr/bin/perl

use strict;
use warnings;
use Data::Dumper;

my $ref = { Paul => { Tel => '0605958978',
                      Adr => '5 rue Victor Hugo',
                      Enfants => ['Marc'] },
            Steve => { Tel => '0785469585',
                      Adr => '5 rue place des fêtes',
                      Enfants => ['Justine'] },
            Marc => { Tel => '0954652363',
                      Adr => '5 rue général de gaulle',
                      Enfants => ['Paul', 'Marie'] },
        };

# print(Dumper($ref));

foreach my $name (keys % {$ref}) {
    print("$name\n");
    print("Tel : $ref->{$name}{Tel}\n");
    print("Adr : $ref->{$name}{Adr}\n");
    print(join(", ", @{$ref->{$name}{Enfants}})."\n");
    print(@{$ref->{$name}{Enfants}}."\n");
}