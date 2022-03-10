#!/usr/bin/perl

use strict;
use warnings;

use CGI;
use DBI;

my $query = CGI->new();

my $prenom = $query->param('prenom');
my $tel = $query->param('tel');

my $source = 'dbi:Pg:host=sqletud.u-pem.fr;dbname=steve.chen_db';
my $user = 'steve.chen';
my $pass = '2000';

my $base = DBI->connect($source, $user, $pass) or die($DBI::errstr);

my $insert = 'INSERT INTO annuaire(prenom_nom, numero_tel) VALUES(?,?)';

my $req = $base->prepare($insert) or die ($base->errstr());

$req->execute($prenom, $tel) or die ($base->errstr());

my $req3 = $base->prepare('SELECT prenom_nom, numero_tel FROM annuaire') or die ($base->errstr());

$req3->execute() or die ($base->errstr());

print"Content-Type: text/html\n\n"
."<html >\n"
    ."<head >\n"
        ."<title >Ceci  est  mon titre </title >\n"
    ."</head >\n"
    ."<body >\n"
    ."<ul>\n";
    while (my $hrefs = $req3->fetchrow_hashref) {
        print"<li>$hrefs->{prenom_nom} $hrefs->{numero_tel}</li>\n";
    }
    print"</ul>\n"
    ."</body >\n"
."</html >\n";