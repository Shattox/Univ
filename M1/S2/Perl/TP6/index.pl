#!/usr/bin/perl

use strict;
use warnings;

print"Content -Type: text/html\n"
."\n"
."<html >\n"
    ."<head >\n"
        ."<title >Ceci  est  mon titre </title >\n"
    ."</head >\n"
    ."<body >\n"
        ."<form method="GET\"action=\"/cgi -bin/programme\">"
            ."Prenom: <input type=\"text\"name=\"prenom\"/><br/>"
            ."<input type=\"reset\"value=\"Effacer\"/><br/>"
            ."<input type=\"submit\"value=\"Envoyer\"/>"
        ."</form>"
    ."</body >\n"
."</html >\n";