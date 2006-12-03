#!/bin/sh
# $Id: conmandictionary.sh 12 2006-12-03 13:11:45Z wwwwolf $

declare currdir=`dirname $0`
exec java -Dswing.defaultlaf=com.sun.java.swing.plaf.gtk.GTKLookAndFeel \
	-jar "$currdir/conmandictionary.jar" $1

