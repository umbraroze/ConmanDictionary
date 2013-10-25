#!/bin/sh
cp $1.java TEMP.java
cp $1.form TEMP.form
cp $2.java $1.java
cp $2.form $1.form
mv TEMP.java $1.java
mv TEMP.form $1.form
