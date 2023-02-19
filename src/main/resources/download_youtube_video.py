import sys
from pytube import YouTube

video = YouTube(sys.argv[1])
audio_stream = video.streams.filter(only_audio=True).get_audio_only()
audio_stream.download(output_path="/tmp/discord-music-bot/", filename_prefix="DMB_")
