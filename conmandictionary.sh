#!/bin/sh
# $Id: conmandictionary.sh 8 2006-09-28 11:18:23Z wwwwolf $

exec java -Dswing.defaultlaf=com.sun.java.swing.plaf.gtk.GTKLookAndFeel \
	-classpath bin \
	org.beastwithin.conmandictionary.ConmanDictionary $1

