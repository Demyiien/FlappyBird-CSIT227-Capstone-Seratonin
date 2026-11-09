# FlappyBird-CSIT227-Capstone-Seratonin

A cell-based Flappy Bird game built with **Java Swing** (`JTable`) for our CSIT227 Object-Oriented Programming Capstone project.

## Overview
Instead of drawing free-form graphics on a traditional canvas, this project uses a `JTable` (`32` rows x `64` columns of `24x24` px cells) as a tile-based game grid (`1536x768` total resolution). Game entities (`Bird` and `Pipe`) are sliced into cell-sized `ImageIcon` tiles and rendered directly inside the table cells using a custom `DefaultTableCellRenderer`.

## Current Features
- **Custom Background:** Renders `assets/images/background.jpg` behind a transparent `JTable` grid.
- **2x2 Subdivided Flappy Bird (`Bird.java`):** Scales `assets/images/flappy-icon.png` to `48x48` px and splits it across 4 adjacent cells (`upper-left`, `upper-right`, `lower-left`, `lower-right`).
- **Dynamic Top & Bottom Pipes (`Pipe.java`, `3x2` Minimum):**
  - Splits `assets/images/pipe.png` into a 3-cell-wide rim (`top`) and a 3-cell-wide repeatable shaft (`bottom`).
  - Generates top and bottom pipes with randomized heights (enforcing a minimum size of `3x2` cells per pipe) separated by an 8-row non-collision gap.

## Project Structure
```text
├── assets/
│   └── images/
│       ├── background.jpg    # Game background image
│       ├── flappy-icon.png   # Flappy Bird sprite (subdivided into 2x2 cells)
│       └── pipe.png          # Pipe sprite (subdivided into 3x1 rim and 3x1 shaft)
├── src/
│   ├── Bird.java             # 2x2 subdivided bird sprite and cell position logic
│   ├── Main.java
│   ├── Pipe.java             # 3x2 pipe spritesheet slicing and random height logic
│   ├── TableGameScreen.form  # IntelliJ Swing UI Designer form
│   └── TableGameScreen.java  # Main game window and JTable grid renderer
└── README.md
```

## How to Run
1. Open the project in **IntelliJ IDEA** (JDK 21).
2. Run [`TableGameScreen.java`](src/TableGameScreen.java) (`TableGameScreen.main()`).
