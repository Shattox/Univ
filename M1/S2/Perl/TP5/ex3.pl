#!/usr/bin/perl

use warnings;
use strict;

use IO::Socket;
use threads;
use threads::shared;

my $counter : shared = 1;

my $listen_socket = IO::Socket::INET->new(
    Proto => 'tcp', LocalPort => 2000, Listen => 5, Reuse => 1,
) or die ("$@");

while (my $accept_socket = $listen_socket->accept()) {
    print("New client\n");
    my $thr = threads->new(\&function, $accept_socket);
    $thr->detach();
}

sub function {
    my ($com_socket) = @_;

    $com_socket->send($counter++."\n");
    sleep(5);
    $com_socket->send($counter++."\n");
    close($com_socket);
}