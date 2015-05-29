#!/bin/bash

compiled_using_script=false

# compile directly from this file
Compile_directly() {
	echo "Trying to compile directly."
	javac Connect4Game.java
}

# check if the files are compiled
if [ ! -e "Connect4Game.class" ]
then
	# compile the java files >>
	if [ -e "compile.sh" ]
	then
		echo "Compiling..."
		./compile.sh
		compiled_using_script=true
	else 
		echo "Compile file missing. [File compile.sh missing]"
		Compile_directly
	fi

	# check if compile is a success
	if [ -e "Connect4Game.class" ]
	then
		echo "Compile success"
	else
		if [ "$compiled_using_script" = true ]
		then
			echo "Couldn't compile files using compile script"
			Compile_directly

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
fi

# run the files >>
echo "Starting Game."
java Connect4Game