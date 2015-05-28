#!/bin/bash

compiled_using_script=false

# remove all the .class files if they exist >>
if [ -e "Connect4Game.class" ]
then
	echo "Removing old .class files."
	rm *.class > /dev/null 2>&1
fi
# compile the java files >>
if [ -e "compile.sh" ]
then
	echo "Compiling..."
	./compile.sh
	compiled_using_script=true
else 
	echo "Compile file missing. [File compile.sh missing]"
	echo "Trying to compile directly."
	javac Connect4Game.java
fi

# check files have been compiled
if [ -e "Connect4Game.class" ]
then
	echo "Compile success"
else
	if [ "$compiled_using_script" = true ]
	then
		echo "Couldn't compile files using compile script"
		echo "Trying to compile directly."
		javac Connect4Game.java
		if [ -e "Connect4Game.class" ]
		then
			echo "Compile success"
		else
			echo "Couldn't compile files..."
			echo "Stopping run process."
			exit 1
		fi
	else 
		echo "Couldn't compile files..."
		echo "Stopping run process."
		exit 1
	fi
fi

# run the files >>
echo "Starting Game."
java Connect4Game