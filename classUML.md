# FlappyBird Capstone - Class UML Reference

Updated class list for the current codebase across `flapibirb.controller`, `flapibirb.model`, and `flapibirb.view`.

Visibility: `+` public, `-` private, `#` protected

---

## Relationships

- `Bird` implements `GObject`
- `Pipe` implements `GObject`
- `CoolBird` extends `Bird`
- `Game` extends `JFrame`
- `StartLeaderboardDialog` extends `JDialog`, implements `ActionListener`
- `Bird` has `Point` (`pos`)
- `Game` has `Bird`, `Pipe` (list), `Point` (`cameraPos`), `Scoreboard`, and `GameTime`
- `GameTime` references `Game` and `Bird`
- `Game` creates `CoolBird` and `Pipe`
- `Game` and `Bird` use `SoundManager`

---

## Package: `flapibirb.controller`

### GObject

Header:
```text
<<interface>>
GObject
```

Attributes:
(none)

Constructors and Methods:
```text
+ die(): void
```

---

### GameTime

Header:
```text
GameTime
```

Attributes:
```text
- timer: Timer
- ticks: long
```

Constructors and Methods:
```text
+ GameTime(Game)
+ start(): void
+ isRunning(): boolean
+ stop(): void
```

---

### SoundManager

Header:
```text
SoundManager
```

Attributes:
```text
- clips: Map<String, Clip>
```

Constructors and Methods:
```text
+ load(String, String): void
+ play(String): void
```

---

## Package: `flapibirb.model`

### Point

Header:
```text
<<struct>>
Point
```

Attributes:
```text
+ x: double
+ y: double
```

Constructors and Methods:
```text
+ Point()
+ Point(double, double)
```

---

### Bird

Header:
```text
<<abstract>>
Bird
```

Attributes:
```text
- pos: Point
- name: String
- score: int
- isAlive: boolean
- velocity: double
- gravity: double
- cellSize: int
- canJump: boolean
- baseBirdImage: BufferedImage
- rotationAngle: double
- lastRenderedAngle: int
- upperLeftIcon: ImageIcon
- upperRightIcon: ImageIcon
- lowerLeftIcon: ImageIcon
- lowerRightIcon: ImageIcon
```

Constructors and Methods:
```text
+ Bird(String)
+ Bird(String, Point, int)
- updateRotatedIcons(double): void
+ jump(): void
+ jump(JTable): void
+ updatePhysics(int): void
+ setRotationAngle(double): void
+ getRotationAngle(): double
+ die(): void
+ isFlying(): boolean
+ releaseJump(): void
+ getIconAt(Point): ImageIcon
+ getIconAt(int, int): ImageIcon
+ getRow(): int
+ setRow(int): void
+ getCol(): int
+ setCol(int): void
+ getPos(): Point
+ setPos(Point): void
+ getName(): String
+ getScore(): int
+ setScore(int): void
+ isAlive(): boolean
+ getVelocity(): double
+ setVelocity(double): void
+ getGravity(): double
+ setGravity(double): void
```

---

### CoolBird

Header:
```text
CoolBird
```

Attributes:
(inherited from `Bird`)

Constructors and Methods:
```text
+ CoolBird()
+ CoolBird(String)
+ CoolBird(String, Point, int)
+ jump(): void
+ die(): void
```

---

### Pipe

Header:
```text
Pipe
```

Attributes:
```text
- col: int
- pipeCols: int
- minPipeHeight: int
- gapRows: int
- topPipeHeight: int
- bottomPipeStartRow: int
- pipeTopIcons: ImageIcon[]
- pipeBottomIcons: ImageIcon[]
```

Constructors and Methods:
```text
+ Pipe(int, int, int)
+ randomizeHeight(int): void
+ getIconAt(int, int): ImageIcon
+ getCol(): int
+ setCol(int): void
+ die(): void
```

---

### Scoreboard

Header:
```text
Scoreboard
```

Attributes:
```text
- counter: int
```

Constructors and Methods:
```text
+ getCounter(): int
+ increment(): void
+ reset(): void
+ getScoreText(): String
```

---

## Package: `flapibirb.view`

### Game

Header:
```text
Game
```

Attributes:
```text
- gameTable: JTable
- mainPanel: JPanel
- mainScrollPane: JScrollPane
- rows: int
- cols: int
- cellSize: int
- bird: Bird
- pipes: ArrayList<Pipe>
- cameraPos: Point
- scoreboard: Scoreboard
- gameTime: GameTime
- scoreTimer: Timer
```

Constructors and Methods:
```text
+ Game()
- loadScoreFont(): void
+ initializeTable(): void
+ getGameTable(): JTable
+ getBird(): Bird
+ getScoreboard(): Scoreboard
+ getRows(): int
+ getCols(): int
+ getCameraX(): int
+ setCameraX(int): void
+ getCameraPos(): Point
+ handleObjects(): void
+ addPipe(): void
+ main(String[]): void
```

---

### StartLeaderboardDialog

Header:
```text
StartLeaderboardDialog
```

Attributes:
```text
- pnlMain: JPanel
- startPnl: JPanel
- leaderboardPnl: JPanel
- tfName: JTextField
- birb1rb: JRadioButton
- birb2rb: JRadioButton
- birb3rb: JRadioButton
- birb4rb: JRadioButton
- birb5rb: JRadioButton
- playButton: JButton
- nameLabel: JLabel
- birbChoiceLabel: JLabel
- leaderboardLabel: JLabel
- leaderboardTbl: JTable
- imgUrl: URL
- backgroundImage: Image
- birbGroup: ButtonGroup
```

Constructors and Methods:
```text
+ StartLeaderboardDialog()
+ getPlayerName(): String
+ getSelectedBirb(): String
- loadLeaderboard(DefaultTableModel): void
+ actionPerformed(ActionEvent): void
- createUIComponents(): void
+ main(String[]): void
```

---

## Default Package

### Main

Header:
```text
Main
```

Attributes:
(none)

Constructors and Methods:
```text
+ main(String[]): void
```