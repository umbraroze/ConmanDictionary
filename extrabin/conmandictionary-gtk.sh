#!/bin/sh
currdir=`dirname $0`
exec java -Dswing.defaultlaf=com.sun.java.swing.plaf.gtk.GTKLookAndFeel \
	-jar "$currdir/conmandictionary.jar" $1

