# NixWelcome

A small Paper plugin that greets players when they join.

## Features
- Private welcome message for the joining player.
- Custom public join message (or disable it by leaving it empty).
- Separate message the first time a player joins.
- `/nixwelcome reload` to reload the config without restarting (permission: `nixwelcome.admin`).
- `&` color codes and the `%player%` placeholder.

## Requirements
- Java 21
- Maven
- Paper 1.21.4

## Build
```bash
mvn clean package
```
The jar ends up in `target/NixWelcome-1.1.0.jar`. Drop it into your server's `plugins` folder and restart once.

## Config
Edit `plugins/NixWelcome/config.yml`, then run `/nixwelcome reload`.
