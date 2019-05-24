# ImageTagger
---
This is a simple Java program I wrote in order to easily classify images by hand for machine learning purposes.
Currently might be buggy. 

## Features
- Moves or copies images to folders mapped to keyboard shortcuts
- Tracks statistics

## Usage
Choose the source folder, all recognized pictures will be loaded from there (non-recursively).
Choose the destination folder, inside which the folders corresponding to the labels will be created.
Move and copy decides what to do with original files.
The labels have default action of Nothing, Delete or Skip.
You can set your own labels, the pictures will be sorted to the folder with corresponding name.
You can use ctrl-z and ctrl-y to undo/redo.

If you chose to copy files and saved the progress, the program will automatically continue where you left of 
if you hadn't modified files in the source folder.

## TODO
- Debbuging
- More keyboard shortcuts
- More supported image formats (webp)
