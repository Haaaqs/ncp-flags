[![license](https://img.shields.io/github/license/mashape/apistatus.svg) ](LICENSE)

# NCP Flags
Receive NCP flags on your discord server.

### Setup
1) Make your self a Discord bot account. You can find tutorials on the internet.
2) Copy the bot *token* and paste it in the config.
3) Copy the text channel ID of the channel you want to bot to message in and paste it in the config. Make sure its a channel that only STAFF can see.
4) Run the plugin and enjoy.

### Screenshots
![screenshot](https://i.imgur.com/7x94BVC.png) 

* ❗ - Reacting to this emote will warn the user.
* ❌ - Reacting to this emote will kick the player.

### Default config
```yaml
# The prefix used for the messages
prefix: '[&bNCPFlags&r]'

# Your discord bot token
token: ''

# If you want to enable kick & warn emotes
kick_and_warn: true

# The channel ID you want to the bot to post messages in
channel_id: ''

messages:
  # The message the user receives when you warn him.
  warn: You have been warned.
  
  # The message the user receives when you kick him.
  kick: You have been kicked.

```