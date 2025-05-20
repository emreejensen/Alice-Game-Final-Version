# Welcome to Alice's Adventures: A Text-Based Adventure Game!

This game immerses you in a whimsical and haunting adventure inspired by American McGee's Alice: Madness Returns and Alice in Wonderland by Lewis Carroll.

--------------------------------------------------------------------------------
KEY FEATURES

- Dynamic Navigation: Move Alice through rooms using directional commands.
- Room Visitation Tracking: Tracks whether a room has been previously visited.
- Puzzle Solving: Solve puzzles in designated rooms (3 attempts max).
- Inventory & Items: Pick up, inspect, equip, unequip, and use healing or combat items.
- Monster System: Interact with monsters—examine, fight, or ignore.
- Combat Mode: Engage in turn-based combat using attack, heal, or equip strategies.
- Death & Restart Logic: Restart or exit the game after defeat.
- Pop-up Inventory & Help UI: Keep help and inventory windows open during play.
- Fully Text-Driven: Command-line-based gameplay powered by Java and text files.

--------------------------------------------------------------------------------
HOW TO PLAY

1. Launch the program.
2. Use directional commands (n, e, s, w) to navigate through rooms.
3. Watch for repeated room descriptions ("This looks awfully familiar...").
4. Solve puzzles when prompted using the `solve <answer>` command.
5. Use `pickup`, `equip`, `heal`, and more to prepare for combat.
6. Encounter a monster? Use `examine monster`, then decide whether to `attack` or `ignore`.
7. In combat, use `attack`, `equip <item>`, `unequip`, or `heal`.
8. If you lose, choose to `restart` or `exit`.
9. Type `help` at any time to open a pop-up help window.
10. Type `inventory` to view a detailed status screen.

--------------------------------------------------------------------------------
AVAILABLE COMMANDS

n / e / s / w
pickup <item>
drop <item>
inspect <item>
explore
inventory
equip <item>
unequip
heal
examine monster
fight
solve <answer>
help
exit
restart

--------------------------------------------------------------------------------
HOW IT WORKS

All game data is dynamically loaded from these files:

map.txt
Format:
roomID~visited~roomName~description~itemName~puzzleID~n,e,s,w~monsterName

Example:
1~false~Houndsditch Home for Wayward Youth~In the cold, gloomy halls...~Vorpal Blade~0~0,2,3,0~0

item.txt
Format:
itemName~description~attackPoints~healPoints

Example:
Vorpal Blade~The Vorpal Blade is sharp and deadly.~20~0

puzzle.txt
Format:
puzzleID~description~answer

Example:
1~Who has a hat and loves to chat?~mad hatter

monsters.txt
Format:
name~description~health~attackPower~threshold~itemDrop

Example:
Grinning Cheshire~A mischievous cat that fades in and out of view...~90~20~15~Smile Potion

--------------------------------------------------------------------------------
CLASSES & LOGIC

MVC Architecture:
Model - View - Controller design pattern.

Key Classes:
Main.java              → Game entry point
Player.java            → Handles movement, inventory, combat, puzzles
GameMap.java           → Loads all map/item/puzzle/monster data
Room.java              → Stores puzzle, monster, item list, exits, visited
Item.java              → Weapon/healing item details
Puzzle.java            → Riddle and answer with attempt limits
Monster.java           → Health, damage, threshold, drops
GameView.java          → Text-based input/output with GUI features
GameController.java    → All command and gameplay logic
InvalidRoomException.java → Custom error for bad navigation

--------------------------------------------------------------------------------
WONDERLAND DEFAULT MAP

Room ID View:
 _ _ _
| 1 | 2 |
| 3 | 4 |
| 5 | 6 |
| 7 |

Room Name View:
Houndsditch Home | Hatter's Domain
Deluded Depths   | Queensland
Dollhouse        | Infernal Train
Asylum

Items Map:
 _ _ _
| VB|TC|
| 0 | R|
| MD| 0|
| 0 |

Legend:
VB = Vorpal Blade
TC = Teapot Cannon
R  = Roses
MD = Misstitched Dress
0  = No item

Puzzle Map:
 _ _ _
| 0| 0|
|P1| 0|
| 0|P2|
|P3|

Legend:
P1 = Deluded Depths
P2 = Infernal Train
P3 = Asylum

Monster Map:
 _ _ _
| 0|GC|
| 0|FG|
| B| 0|
| 0|

Legend:
GC = Grinning Cheshire
FG = Flamingo Guard
B  = Bandersnatch

--------------------------------------------------------------------------------
THANK YOU & ENJOY PLAYING ALICE'S ADVENTURES!

Created by Emree Jensen
