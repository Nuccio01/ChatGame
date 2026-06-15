# 🎮 ChatGame

A powerful, lightweight, and modern chat game plugin for **Paper** (1.20 - 1.21). 
Keep your server's chat dynamic, active, and your players fully engaged with mini-games!

---

## ✨ Features

* **3 Dynamic Game Types:**
  * 🧮 **Math:** Quick equations generated on the fly (Addition, Subtraction, Multiplication, Division).
  * 🔤 **Word Unscramble:** Shuffles a word from your list and challenges players to decode it.
  * 🎯 **Number Guessing:** Random number challenges from 1 to 100.
* **Anti-Overlap System:** Only 1 game can run at a time to prevent chat spam and confusion.
* **Interactive Admin Menu:** Hover and click support directly inside the Minecraft chat to start or stop games instantly.
* **Global Chat Mute:** Includes a built-in toggle to mute the whole chat during events, with custom bypass permissions.
* **100% Configurable:** Every single message, word list, and color code can be customized inside the `config.yml`.

---

## 🛠️ Commands & Permissions

| Command | Description | Permission |
| :--- | :--- | :--- |
| `/chatgame` | Opens the interactive, clickable chat GUI menu | `chatgame.start` |
| `/chatgame stop` | Force stops the active chat game | `chatgame.start` |
| `/chatgame reload` | Reloads the plugin configuration files | `chatgame.start` |
| `/chatmute` | Toggles global server chat mute on/off | `chatgame.mute` |

> 💡 **Note:** Players with the `chatgame.bypassmute` permission can type in chat even when the global mute is active (perfect for staff members!).

---

## ⚙️ Default Configuration

```yaml
random-words:
  - "computer"
  - "keyboard"
  - "mouse"
  - "sword"
  - "shield"
  - "castle"
  - "dragon"
  - "forest"
  - "magic"
  - "books"
  - "school"
  - "island"

messages:
  chat-muted-broadcast: "&eChat has been &cMUTED &eby &6%player%&e!"
  chat-unmuted-broadcast: "&eChat has been &aUNMUTED &eby &6%player%&e!"
  chat-currently-muted: "&cThe chat is currently muted! You cannot type."
  no-game-running: "&cThere is no active game running right now."
  game-stopped: "&cThe current game was stopped by &e%player%&c!"
  current-answer: "&6The correct answer is: &f%answer%"
  invalid-args: "&cInvalid argument! Use /chatgame to view the menu."
  only-players: "Only players can execute this command."
  game-already-running: "&cThere is already an active game running! Stop it first using /chatgame stop."
  plugin-reloaded: "&aConfiguration reloaded successfully!"

  start-math:
    - "&b&l► CHAT GAME"
    - "&eSolve the expression as fast as possible to win!"
    - "&6&lEXPRESSION: &f%expression%"

  start-guess:
    - "&b&l► CHAT GAME"
    - "&eGuess the number from 1 to 100 to win!"

  start-word:
    - "&b&l► CHAT GAME"
    - "&eUnscramble the letters to guess the word!"
    - "&6&lSCRAMBLED WORD: &f%scrambled%"


  win-math: "&a&l%player% &asolved the expression (&e%answer%&a) in &e%time%s&a!"
  win-guess: "&a&l%player% &aguessed the number &e%answer% &ain &e%time%s&a!"
  win-word: "&a&l%player% &aunscrambled the word &e%answer% &acorrectly in &e%time%s&a!"

Ecco il file `README.md` completo e aggiornato con tutti i tuoi contatti corretti (il tuo tag Discord, il link del server Discord e il tuo username Telegram):

```markdown
# 🎮 ChatGame Pro

A powerful, lightweight, and modern chat game plugin for **Paper** (1.20 - 1.21). 
Keep your server's chat dynamic, active, and your players fully engaged with automatic mini-games!

---

## ✨ Features

* **3 Dynamic Game Types:**
  * 🧮 **Math:** Quick equations generated on the fly (Addition, Subtraction, Multiplication, Division).
  * 🔤 **Word Unscramble:** Shuffles a word from your list and challenges players to decode it.
  * 🎯 **Number Guessing:** Random number challenges from 1 to 100.
* **Anti-Overlap System:** Only 1 game can run at a time to prevent chat spam and confusion.
* **Interactive Admin Menu:** Hover and click support directly inside the Minecraft chat to start or stop games instantly.
* **Global Chat Mute:** Includes a built-in toggle to mute the whole chat during events, with custom bypass permissions.
* **100% Configurable:** Every single message, word list, and color code can be customized inside the `config.yml`.

---

## 🛠️ Commands & Permissions

| Command | Description | Permission |
| :--- | :--- | :--- |
| `/chatgame` | Opens the interactive, clickable chat GUI menu | `chatgame.start` |
| `/chatgame stop` | Force stops the active chat game | `chatgame.start` |
| `/chatgame reload` | Reloads the plugin configuration files | `chatgame.start` |
| `/chatmute` | Toggles global server chat mute on/off | `chatgame.mute` |

> 💡 **Note:** Players with the `chatgame.bypassmute` permission can type in chat even when the global mute is active (perfect for staff members!).

---

```

---

## 📞 Support & Feedback

If you encounter any bugs, need assistance, or want to drop a suggestion, feel free to contact me:

* 💬 **Discord Server:** [Join our Discord](https://discord.gg/BNjayc2c9t)
* 👤 **Discord Developer:** `@nuccioo`
* ✈️ **Telegram:** `@Nuccio01`

---

<p align="center">Made with ❤️ by Nuccio01</p>

```



