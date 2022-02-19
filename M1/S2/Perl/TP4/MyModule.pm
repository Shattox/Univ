package MyModule;

use strict;
use warnings;

use parent qw(Exporter);
our @EXPORT = qw(SommeTest TableMult3);

sub SommeTest {
    my ($x, $y, $n) = @_;
    return $x.$y+$x == $n;
}

sub TableMult3 {
    my ($n) = @_;
    my $result = "";

    foreach my $i (1..$n) {
        foreach my $j (1..$n) {
            $result .= sprintf("%5d", $i * $j);
        }
        $result .= sprintf("\n");
    }
    printf("$result");
}