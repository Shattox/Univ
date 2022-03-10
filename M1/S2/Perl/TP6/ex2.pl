#!/usr/bin/perl

use strict;
use warnings;

use DBI;

my $source = 'dbi:Pg:host=sqletud.u-pem.fr;dbname=steve.chen_db';
my $user = 'steve.chen';
my $pass = '2000';

my $base = DBI->connect($source, $user, $pass) or die($DBI::errstr);

my $table = 'CREATE TABLE IF NOT EXISTS annuaire ('
    .'prenom_nom VARCHAR(40),'
    .'numero_tel VARCHAR(20)'
.');';
my $query = 'INSERT INTO annuaire(prenom_nom, numero_tel) VALUES(?,?)';

my $req = $base->prepare($table) or die ($base->errstr());
my $req2 = $base->prepare($query) or die ($base->errstr());
my $req3 = $base->prepare('SELECT prenom_nom, numero_tel FROM annuaire') or die ($base->errstr());
$req->execute() or die ($base->errstr());

$req2->execute('Steve Chen', '01698949') or die ($base->errstr());
$req2->execute('Dylan Canet', '032641894') or die ($base->errstr());
$req2->execute('Shatox K', '065484968') or die ($base->errstr());
$req2->execute('Unknowsn', '016484951') or die ($base->errstr());

$req3->execute() or die ($base->errstr());

while (my $hrefs = $req3->fetchrow_hashref) {
    print("$hrefs->{prenom_nom} $hrefs->{numero_tel}\n");
}
