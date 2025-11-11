
<p align="center">
<img src="./media/Social-image-rounded.png" alt="team picture" width="75%"></img>
</p>

# Ludus Vitae
This is a simple Java application for [Conway's Game of Life](https://conwaylife.com/wiki/Conway%27s_Game_of_Life) for experimenting with the game.

Also, this project was entirely created without the assistance of AI tools. While useful, I wanted to try writing the code and searching the Oracle documentation myself.

## Features
This application focuses on simplicity over complicated features, but it still includes many features for easier use.

- **Wrapping**: Automatically wraps sides or disable
- **Cell Resizing**: Choose between large, normal, and tiny sizes
- **Board Resizing**: Resize board by dragging the boundary and using the keyboard shortcut
- **Speed**: Several speed levels to hurry evolution or let it slowly develop
- **Editing**: Toggle any cell and pause to do more
- **Save/Load**: Find a cool design? Save it with a memory efficient file
- **Themes**: Tired of black and white? Switch the colors yourself to find something fun

### Keyboard shortcuts
While most functionality is available in the menu bar, there are a few keyboard shortcuts to know.
- **P**: Pause the game, allowing you to edit some more cells
- **R**: Trigger a board resize after dragging the application boundary
- **C**: Clear the board 

## Usage
Download the executable from the [releases](https://github.com/CorbanPendrak/Ludus-Vitae/releases/) for your system and run as normal. Note, for MacOS, check the release instructions for bypassing the signing requirement.

### Other Systems
If your operating system is not listed in the releases, you can either clone and compile the code in this repository or download the .jar file in the releases.

To compile the code, just run these commands. This will also generate your own .jar file, which is like a .zip for .java files. 
```shell
javac ./src/LudusVitae.java
jar cfe LudusVitae.jar src.LudusVitae src
java -jar LudusVitae.jar
```

To run the .jar program from the releases, just do this command.
```shell
java -jar LudusVitae.jar
```

## What's with the name? 
The name comes from Latin, literally meaning the "Game of Life". I previously made a Java application with the same name for Conway's Game of Life after completing the AP Computer Science A and being inspired by what programming could do, but it has unfortunately been lost to the dusty archives of time, which is why I am making sure to use version control this time!

## Roadmap
While this project is released, it has some areas for improvement. Here is the current roadmap, but I am not actively developing this project.

- [ ] Compile platform executables with GitHub workflows
- [ ] Improve print version (originally just used for testing, what about a TUI?)
- [ ] File I/O
  - [ ] Use [RLE](https://conwaylife.com/wiki/Run_Length_Encoded#cite_note-niemiec-1) encryption
  - [ ] Connect with LifeWiki for searching
- [ ] Styling
  - [ ] Color based on # neigbors
  - [ ] Color based on time
- [ ] Marketing
  - [ ] UML Diagrams
  - [ ] Better document code
  - [ ] Icon
  - [ ] Demo video
- [ ] Research
  - [ ] Optimizing memory
  - [ ] Optimising runtime (concurrency?)


