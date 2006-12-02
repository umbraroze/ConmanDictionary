#!/bin/sh
# $Id: conmandictionary.sh 11 2006-12-02 15:27:57Z wwwwolf $

exec java -Dswing.defaultlaf=com.sun.java.swing.plaf.gtk.GTKLookAndFeel \
	-jar conmandictionary.jar $1

