# FlappyBird-CSIT227-Capstone-Seratonin

A cell-based Flappy Bird game built with **Java Swing** (`JTable`) for our CSIT227 Object-Oriented Programming Capstone project.

---

## 🛠️ Prerequisites

Make sure you have the following installed and configured before working on the project:

- **Java Development Kit (JDK 21):** Required to compile and run the project.
- **Java Swing & AWT:** Built into the JDK (used for `JFrame`, `JTable`, `JPanel`, and custom rendering).
- **IntelliJ IDEA (Community or Ultimate):**
  - Ensure the bundled **UI Designer** plugin is enabled (for `.form` files like `TableGameScreen.form`).
  - Set **Project SDK** and **Project Language Level** to **21** (`File > Project Structure > Project`).
- **Git:** Installed and authenticated with your GitHub account.

---

## 🚀 How to Run

1. Open the project folder in **IntelliJ IDEA** (JDK 21).
2. Open [`src/TableGameScreen.java`](src/TableGameScreen.java) and run `TableGameScreen.main()`.

---

## 🤝 How to Collaborate (Team Workflow)

We use a protected two-branch workflow (`main` and `dev`). **Never push or open a PR directly to `main`.**

- **`main`** — Stable production branch. Only the project lead merges `dev` into `main`.
- **`dev`** — Main development branch. All teammates branch off `dev` and open Pull Requests back into `dev`.
- **`<name>/<type>-<short-description>`** — Your personal working branch created from `dev`.

### Naming Conventions ([namingconvention.org/git](https://namingconvention.org/git/))

1. **Branch Names:** `<your-name>/<type>-<kebab-case-description>`
   - `feat`: New feature (e.g., `juan/feat-pipe-scrolling`, `maria/feat-start-dialog`)
   - `fix`: Bug fix (e.g., `juan/fix-pipe-gap-bounds`)
   - `refactor`: Code restructuring (e.g., `maria/refactor-package-structure`)
   - `docs`: Documentation changes (e.g., `juan/docs-claim-tick-03`)
   - `chore`: Build/config updates
2. **Commit Messages:** `<type>(<scope>): <short imperative description>`
   - Examples:
     - `docs(readme): claim TICK-03 and mark sprite slicing complete`
     - `feat(pipe): add continuous leftward pipe scrolling`
     - `fix(collision): check bottom row boundary for 2x2 bird`

---

### Step-by-Step Collaboration Guide & Git Commands

#### Step 1: Sync with the `dev` branch
Always start from an up-to-date `dev` branch using `--rebase`:

```bash
# If you already cloned the repository:
git fetch origin
git checkout dev
git pull --rebase origin dev
```

#### Step 2: Create your branch off `dev`
Create your branch using your name and the [Git naming convention](https://namingconvention.org/git/):

```bash
git checkout -b yourname/feat-short-description
# Example:
# git checkout -b josh/feat-bird-physics
```

#### Step 3: Claim your ticket & update the checklist in `README.md`
1. In the **Ticket Summary Board** below, replace `(Name)` with your name and change the status to `🚧 In Progress (YourName)`:
   - Before: `| **TICK-03** | Pipe Generation, Slicing & Leftward Movement | flapibirb.model, flapibirb.view | High | 📝 Ready for Dev (Name) |`
   - After: `| **TICK-03** | Pipe Generation, Slicing & Leftward Movement | flapibirb.model, flapibirb.view | High | 🚧 In Progress (Juan) |`
2. As you finish items under your ticket's **Acceptance Criteria**, add an `x` inside `[ ]` to mark them done:
   - Before: `- [ ] Move active pipes leftward by 1 column every N ticks.`
   - After: `- [x] Move active pipes leftward by 1 column every N ticks.`
3. Once all items in your ticket are finished, update the board status to `✅ Done (YourName)`.

#### Step 4: Stage & commit your changes
```bash
# Check which files you modified
git status

# Stage your changes
git add .

# Commit following https://namingconvention.org/git/
git commit -m "feat(scope): describe what you implemented"
```

#### Step 5: Rebase onto the latest `dev` before pushing
To prevent unnecessary merge commits or accidentally overwriting a teammate's code that was merged into `dev` while you were working, **always rebase your branch onto `origin/dev` before pushing**:

```bash
# 1. Fetch the latest changes from GitHub
git fetch origin

# 2. Rebase your commits on top of the latest origin/dev
git rebase origin/dev

# If there are any conflicts:
#   - Open the conflicting file(s) and keep both teammates' changes properly
#   - Stage the resolved file(s): git add <filename>
#   - Continue the rebase:        git rebase --continue
#   - (Or cancel if needed):      git rebase --abort

# 3. Push your branch to GitHub
# (Use --force-with-lease if you already pushed this branch before rebasing)
git push -u origin yourname/feat-short-description
# git push --force-with-lease origin yourname/feat-short-description
```

#### Step 6: Open a Pull Request (PR) into `dev`
1. Go to the GitHub repository in your browser.
2. Click **Compare & pull request** on your pushed branch.
3. Set the branches to:
   - **base:** `dev` ← **compare:** `yourname/feat-short-description` *(Make sure base is `dev`, NOT `main`!)*
4. Add a clear PR title (matching our commit convention) and list what you completed.
5. Submit the PR and wait for review and approval. Direct merges without approval are disabled.

---

## 📋 Sprint Backlog & Tickets

The development roadmap follows our agreed architectural design:
1. **Modal Popup System:** A unified popup dialog for Start / Game Over containing player name input + bird selector on the left, and the live leaderboard on the right.
2. **Modular OOP Architecture:** Clean separation across `flapibirb.model`, `flapibirb.view`, `flapibirb.controller`, `flapibirb.data`, and `flapibirb.exception` packages.
3. **Pipe Sprite Slicing & Continuous Scrolling:** Slicing `pipe.png` into cell-sized icons and continuously scrolling pipes leftward.
4. **Data Persistence:** Flat text file storage (`assets/flapibirb.data/leaderboard.txt`) using streams and custom exceptions.

### Ticket Summary Board

| Ticket ID | Title | Module / Package | Priority | Status |
| :--- | :--- | :--- | :--- | :--- |
| **TICK-00** | Grid & Bird Renderer Baseline | `flapibirb.view` | High | ✅ Done |
| **TICK-01** | Modular OOP Architecture Setup | `flapibirb.model`, `flapibirb.controller` | High | 📝 Ready for Dev (Name) |
| **TICK-02** | Bird Physics & Jump Mechanics | `flapibirb.model`, `flapibirb.controller` | High | 🚧 In Progress (Josh) |
| **TICK-03** | Pipe Generation, Slicing & Leftward Movement | `flapibirb.model`, `flapibirb.view` | High | 📝 Ready for Dev (Name) |
| **TICK-04** | Collision Detection & Game Flow Trigger | `flapibirb.controller` | High | 📝 Ready for Dev (Name) |
| **TICK-05** | Unified Start / Leaderboard / Bird Select Dialog | `flapibirb.view`, `flapibirb.model` | High | 📝 Ready for Dev (Name) |
| **TICK-06** | Leaderboard File Handling & Persistence | `flapibirb.data`, `flapibirb.model` | High | 📝 Ready for Dev (Name) |
| **TICK-07** | Custom Exception Hierarchy & Input Defense | `flapibirb.exception` | High | 📝 Ready for Dev (Name) |
| **TICK-08** | In-Game Scoreboard & HUD Integration | `flapibirb.view`, `flapibirb.controller` | Medium | 📝 Ready for Dev (Name) |
| **TICK-09** | Sound Effects & Audio Manager (Optional) | `audio` | Low | 💡 Proposed |

---

### Detailed Sprint Tickets

#### TICK-01: Modular OOP Architecture Setup
* **Package Target:** `flappybird.flapibirb.model`, `flappybird.flapibirb.view`, `flappybird.flapibirb.controller`, `flappybird.flapibirb.data`, `flappybird.flapibirb.exception`
* **Description:** Refactor current standalone screen into modular classes so each responsibility is clean and testable.
* **Acceptance Criteria:**
  - [ ] Package structure created under `src/`.
  - [ ] `Bird` flapibirb.model created (holds coordinates, velocity, skin selection).
  - [ ] `Pipe` flapibirb.model created (holds column position, gap bounds, passed state).
  - [ ] `GameEngine` or `GameController` created to manage game loop timer and tick updates.
  - [ ] `TableGameScreen` acts as the primary View consuming flapibirb.model state.

#### TICK-02: Bird Physics & Jump Mechanics
* **Package Target:** `flapibirb.model.Bird`, `flapibirb.controller.GameController`
* **Description:** Implement gravity, jump impulse, and key listeners on the bird.
* **Acceptance Criteria:**
  - [x] Gravity updates the bird's vertical position every tick.
  - [x] Pressing `SPACE`, `UP`, or `W` triggers upward movement.
  - [ ] Start trigger: Negate / pause gravity until player initiates first jump.
  - [x] Clamp row bounds so the bird cannot fly above row `0` or drop below floor bounds.
  - [x] Flight hang time negates gravity smoothly during jump ascent.

#### TICK-03: Pipe Slicing, Spawning & Leftward Scrolling
* **Package Target:** `flapibirb.model.Pipe`, `flapibirb.model.PipeManager`, `flapibirb.view.TableGameScreen`
* **Description:** Slice `assets/images/pipe.png` into cell sub-images and move pipe columns to the left continuously.
* **Acceptance Criteria:**
  - [x] Load and slice `pipe.png` into cell-sized `ImageIcon`s (top rim, bottom body).
  - [ ] Move active pipes leftward by 1 column every $N$ ticks.
  - [ ] Continuously spawn new pipes with randomized gaps at the right edge of the grid.
  - [ ] Automatically discard pipes that scroll past column `< 0`.

#### TICK-04: Collision Detection & Game Over Trigger
* **Package Target:** `flapibirb.controller.GameController`
* **Description:** Detect when the 2x2 bird collides with any active pipe segment or floor boundary, then pause the loop and show the Game Over dialog.
* **Acceptance Criteria:**
  - [ ] Exact collision check between the bird's 2x2 bounding box and pipe column cells.
  - [ ] Floor / ceiling collision checks.
  - [ ] Instantly stops the timer on collision and triggers the unified popup in "Game Over" mode.

#### TICK-05: Unified Start / Leaderboard / Bird Select Dialog
* **Package Target:** `flapibirb.view.StartLeaderboardDialog`
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
* **Package Target:** `flapibirb.data.LeaderboardManager`, `flapibirb.model.ScoreEntry`
* **Description:** Save and load high scores to a local file (`assets/flapibirb.data/leaderboard.txt`).
* **Acceptance Criteria:**
  - [ ] Flat text format: `playerName,score,birdType,date`.
  - [ ] Reads file on startup using `BufferedReader` and populates the leaderboard table.
  - [ ] Appends new scores upon Game Over using `BufferedWriter`.
  - [ ] Sorts scores in descending order (`ScoreEntry` implements `Comparable<ScoreEntry>`).
  - [ ] Auto-creates the flapibirb.data directory and file if it does not exist yet.

#### TICK-07: Custom Exception Hierarchy & Input Defense
* **Package Target:** `flapibirb.exception.*`
* **Description:** OOP custom flapibirb.exception classes to handle invalid inputs, corrupted flapibirb.data, and missing files gracefully.
* **Acceptance Criteria:**
  - [ ] `GameException` (base custom checked/unchecked flapibirb.exception).
  - [ ] `InvalidPlayerNameException` (thrown if name is empty, too short, or has invalid characters).
  - [ ] `LeaderboardDataException` (thrown if a line in the flapibirb.data file cannot be parsed).
  - [ ] `AssetNotFoundException` (thrown if sprites or background images fail to load).
  - [ ] All file streams enclosed in `try-with-resources`.
  - [ ] User-friendly error messages presented via `JOptionPane`.

#### TICK-08: In-Game Scoreboard & HUD Integration
* **Package Target:** `flapibirb.view.TableGameScreen`
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
