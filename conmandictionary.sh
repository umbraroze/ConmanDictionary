#!/bin/sh
# $Id: conmandictionary.sh 7 2006-09-28 11:09:53Z wwwwolf $

exec java -Dswing.defaultlaf=com.sun.java.swing.plaf.gtk.GTKLookAndFeel \
	-classpath bin \
	org.beastwithin.conmandictionary.ConmanDictionary

