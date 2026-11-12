## 📋 Sprint Backlog & Tickets

The development roadmap follows our agreed architectural design:
1. **Modal Popup System:** A unified popup dialog for Start / Game Over containing player name input + bird selector on the left, and the live leaderboard on the right.
2. **Modular OOP Architecture:** Clean separation across `model`, `view`, `controller`, `data`, and `exception` packages.
3. **Pipe Sprite Slicing & Continuous Scrolling:** Slicing `pipe.png` into cell-sized icons and continuously scrolling pipes leftward.
4. **Data Persistence:** Flat text file storage (`assets/data/leaderboard.txt`) using streams and custom exceptions.

### Ticket Summary Board

| Ticket ID | Title | Module / Package | Priority | Status |
| :--- | :--- | :--- | :--- | :--- |
| **TICK-00** | Grid & Bird Renderer Baseline | `view` | High | ✅ Done |
| **TICK-01** | Modular OOP Architecture Setup | `model`, `controller` | High | 📝 Ready for Dev (Name) |
| **TICK-02** | Bird Physics & Jump Mechanics | `model`, `controller` | High | 🚧 In Progress (Josh) |
| **TICK-03** | Pipe Generation, Slicing & Leftward Movement | `model`, `view` | High | 📝 Ready for Dev (Name) |
| **TICK-04** | Collision Detection & Game Flow Trigger | `controller` | High | 📝 Ready for Dev (Name) |
| **TICK-05** | Unified Start / Leaderboard / Bird Select Dialog | `view`, `model` | High | 🚧 In Progress (Liyah) |
| **TICK-06** | Leaderboard File Handling & Persistence | `data`, `model` | High | 📝 Ready for Dev (Name) |
| **TICK-07** | Custom Exception Hierarchy & Input Defense | `exception` | High | 📝 Ready for Dev (Name) |
| **TICK-08** | In-Game Scoreboard & HUD Integration | `view`, `controller` | Medium | 📝 Ready for Dev (Name) |
| **TICK-09** | Sound Effects & Audio Manager (Optional) | `audio` | Low | 💡 Proposed |

---

### Detailed Sprint Tickets

#### TICK-01: Modular OOP Architecture Setup
* **Package Target:** `flappybird.model`, `flappybird.view`, `flappybird.controller`, `flappybird.data`, `flappybird.exception`
* **Description:** Refactor current standalone screen into modular classes so each responsibility is clean and testable.
* **Acceptance Criteria:**
  - [ ] Package structure created under `src/`.
  - [ ] `Bird` model created (holds coordinates, velocity, skin selection).
  - [ ] `Pipe` model created (holds column position, gap bounds, passed state).
  - [ ] `GameEngine` or `GameController` created to manage game loop timer and tick updates.
  - [ ] `TableGameScreen` acts as the primary View consuming model state.

#### TICK-02: Bird Physics & Jump Mechanics
* **Package Target:** `model.Bird`, `controller.GameController`
* **Description:** Implement gravity, jump impulse, and key listeners on the bird.
* **Acceptance Criteria:**
  - [x] Gravity updates the bird's vertical position every tick.
  - [x] Pressing `SPACE`, `UP`, or `W` triggers upward movement.
  - [ ] Start trigger: Negate / pause gravity until player initiates first jump.
  - [x] Clamp row bounds so the bird cannot fly above row `0` or drop below floor bounds.
  - [x] Flight hang time negates gravity smoothly during jump ascent.

#### TICK-03: Pipe Slicing, Spawning & Leftward Scrolling
* **Package Target:** `model.Pipe`, `model.PipeManager`, `view.TableGameScreen`
* **Description:** Slice `assets/images/pipe.png` into cell sub-images and move pipe columns to the left continuously.
* **Acceptance Criteria:**
  - [x] Load and slice `pipe.png` into cell-sized `ImageIcon`s (top rim, bottom body).
  - [ ] Move active pipes leftward by 1 column every $N$ ticks.
  - [ ] Continuously spawn new pipes with randomized gaps at the right edge of the grid.
  - [ ] Automatically discard pipes that scroll past column `< 0`.

#### TICK-04: Collision Detection & Game Over Trigger
* **Package Target:** `controller.GameController`
* **Description:** Detect when the 2x2 bird collides with any active pipe segment or floor boundary, then pause the loop and show the Game Over dialog.
* **Acceptance Criteria:**
  - [ ] Exact collision check between the bird's 2x2 bounding box and pipe column cells.
  - [ ] Floor / ceiling collision checks.
  - [ ] Instantly stops the timer on collision and triggers the unified popup in "Game Over" mode.

#### TICK-05: Unified Start / Leaderboard / Bird Select Dialog
* **Package Target:** `view.StartLeaderboardDialog`
* **Description:** A single pop-up dialog (`JDialog`) that opens automatically at game launch and on game over.
* **Acceptance Criteria:**
  - [ ] **Left Side:**
    - Player name input (`JTextField`).
    - Bird selector (preview thumbnails / radio buttons to choose bird skin/color).
    - "Start Game" / "Play Again" button.
  - [ ] **Right Side:**
    - Leaderboard table (`JTable`) displaying Top 10 scores (`Rank`, `Name`, `Score`, `Bird`).
  - [ ] Modal blocks gameplay until player enters a valid name and clicks "Start".

#### TICK-06: Leaderboard File Handling & Persistence
* **Package Target:** `data.LeaderboardManager`, `model.ScoreEntry`
* **Description:** Save and load high scores to a local file (`assets/data/leaderboard.txt`).
* **Acceptance Criteria:**
  - [ ] Flat text format: `playerName,score,birdType,date`.
  - [ ] Reads file on startup using `BufferedReader` and populates the leaderboard table.
  - [ ] Appends new scores upon Game Over using `BufferedWriter`.
  - [ ] Sorts scores in descending order (`ScoreEntry` implements `Comparable<ScoreEntry>`).
  - [ ] Auto-creates the data directory and file if it does not exist yet.

#### TICK-07: Custom Exception Hierarchy & Input Defense
* **Package Target:** `exception.*`
* **Description:** OOP custom exception classes to handle invalid inputs, corrupted data, and missing files gracefully.
* **Acceptance Criteria:**
  - [ ] `GameException` (base custom checked/unchecked exception).
  - [ ] `InvalidPlayerNameException` (thrown if name is empty, too short, or has invalid characters).
  - [ ] `LeaderboardDataException` (thrown if a line in the data file cannot be parsed).
  - [ ] `AssetNotFoundException` (thrown if sprites or background images fail to load).
  - [ ] All file streams enclosed in `try-with-resources`.
  - [ ] User-friendly error messages presented via `JOptionPane`.

#### TICK-08: In-Game Scoreboard & HUD Integration
* **Package Target:** `view.TableGameScreen`
* **Description:** Display real-time score overlay while playing.
* **Acceptance Criteria:**
  - [ ] Point scored when a pipe safely clears the bird's horizontal columns.
  - [ ] Retro-style score display overlay at top-center.

#### TICK-09: Sound Effects & Audio Manager (Optional)
* **Package Target:** `audio.SoundManager`
* **Description:** Background music and sound effects for jumps, score increments, and collisions.
* **Acceptance Criteria:**
  - [ ] Jump wing flap sound.
  - [ ] Point score sound.
  - [ ] Collision / hit sound.
