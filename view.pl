#!/usr/bin/perl

open(OUTPUT,"$ARGV[0]");
open(FILEOUT,">$ARGV[1]");

while(<OUTPUT>){

chomp;

if($_ =~ /Key: (.+?): Value: (.+?): \/(.+) *=/){
print FILEOUT "$1\t$3\n";
}

}

close FILEOUT;
print "Done\n";