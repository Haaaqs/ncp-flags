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
```yml
# Your discord bot token
token: ""

# The channel ID you want to the bot to post messages in
channel_id: ""

# The message the user receives when you warn him.
warn_message: "&f[&bNCPFlags&f] You have been warned."

# The message the user receives when you kick him.
kick_message: "&f[&bNCPFlags&f] You have been kicked."
```