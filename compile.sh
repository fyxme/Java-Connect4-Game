#!/bin/bash

# Remove .class files
Clean_up() {
	echo "Removing old .class files."
	rm *.class > /dev/null 2>&1
}

if [ $# -eq 1 ]
then
	if [ $1 == "clean" ]
	then
		Clean_up
		echo "Clean up process done!"
		exit 1
	fi
fi

# remove old files
Clean_up

# check that all the java files are present
fileList="AI.java Board.java Connect4Game.java GameEventListener.java GameInstance.java GamePanel.java InstructionsPanel.java MainFrame.java MenuPanel.java Move.java OptionsPanel.java Participant.java Player.java Tile.java UIPanel.java"

fileMissing=false

for file in $fileList; do
	if [ ! -f $file ]
		then
		echo $file is missing
		fileMissing=true
	fi
done

# compile all the files
if [ "$fileMissing" = true ]; 
	then
	echo "One or more files missing. Please redownload game."
	exit 1
fi

# check if the files have been compiled
# compile directly from this file
echo "Trying to compile."
javac Connect4Game.java
echo "Finished compiling."