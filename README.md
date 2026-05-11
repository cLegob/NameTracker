# NameTracker

A basic Minecraft plugin that tracks player username history on your server.

## Features

* Stores usernames players have used on your server
* View a player's previous names
* Search for players by old usernames
* Lightweight YAML storage
* Reload support

## Commands

| Command                      | Description                         | Permission           |
| ---------------------------- | ----------------------------------- | -------------------- |
| `/nametracker <player>`      | View a player's name history        | `nametracker`        |
| `/nametracker lookup <name>` | Search for players by previous name | `nametracker`        |
| `/reloadnametracker`         | Reload the NameTracker database     | `nametracker.reload` |

## Example

```txt
/nametracker Braden
```

Shows all previously stored usernames for `Braden`.

```txt
/nametracker lookup CoolGuy123
```

Searches the database for players who previously used the name `CoolGuy123`.

## Storage

Player name history is stored in:

```txt
plugins/NameTracker/nametracker.yml
```

## Permissions

```txt
nametracker
nametracker.reload
```

## Notes

* Name history only includes usernames seen while the plugin is installed
* Players who never joined while the plugin was enabled will not appear in the database
