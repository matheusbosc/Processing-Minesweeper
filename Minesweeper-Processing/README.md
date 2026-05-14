# Minesweeper

## Files & Classes

### Minesweeper (Class) - Extends PApplet

#### What it does:
- Connect all of the modules together
- Update everything
- Handle initialization and shutdown

---

### GameModule (Interface)

#### What it does:
- Provide shared functions for the modules
- Hold shared variables for modules

---

### TileManager (Class) - Extends PApplet & GameModule

#### What it does:
- Manages the tiles in the game
- Initializes all the tiles
- Generates bomb positions
- Provide utilities for other modules
  - Flood Fill
  - Tile verification

---

### UIManager (Class) - Extends PApplet & GameModule

#### What it does:
- Draw the UI
- Draw the grid
- Handle UI interactions
- Manage game states 
- Holds all UI modules

---

### UIElement.java - UI elements to reuse

#### UIElement (Interface)

##### What it does:
- Provides properties to the ui elements
  - Coordinates (x, y, z, size)
  - Element Name
  - Interactons
  - Is interacted (bool)

#### Button (class) - Extends UIElement & PApplet
- Initializes the interactions
- Handle click action (Do action in the update loop when `is interacted` happens, then set `is interacted` to false)