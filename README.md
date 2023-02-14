# Discord Music Bot
Streams any YouTube video to your private Discord server.

## Steps To Run Local

### Install ngrok to communicate with Discord API.
Your application **must** expose a public endpoint to communicate with Discord.
   1. Install ngrok (https://ngrok.com) and obtain your web token.
   2. Activate the `local` Spring profile. A public URI will be available in the log traces.
   3. Provide this URI to Discord in the Developer Portal. A new URI will be generated each time your run the application.
