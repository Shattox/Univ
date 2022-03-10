#!/usr/bin/perl

use warnings;
use strict;

use MIME::Lite;

my $message = MIME::Lite->new(
    From    => 'stevechen77000@gmail.com',
    To      => 'stevechen1700@gmail.com',
    Subject => 'Rendez-vous Gare de Lyon',
    Data    => 'Est-ce que c\'est ok pour toi ? :3\n',
);

$message->attach(
    Type => 'image/jpg',
    Encoding => 'base64',
    Path => './gdl.jpg',
);

$message->send();