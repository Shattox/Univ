#!/usr/bin/perl

use warnings;
use strict;

use MIME::Parser;
use MIME::Base64;

my $parser = MIME::Parser->new();

my $mime = $parser->parse_open('./courriel');

my $from = $mime->get("From");
my $date = $mime->get("Date");
my $subject = $mime->get("Subject");

$subject =~ s/=\?utf-8\?b\?(.*?)\?=/decode_base64($1)/eig;

my $date = Date::Manip::Date->new();
$date->parse($datestr);
my $date_seconds = $date->printf('%s');


print("from -> $from");
print("date -> $date");
print("subject -> $subject");