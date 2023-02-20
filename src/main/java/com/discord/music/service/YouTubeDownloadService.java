package com.discord.music.service;

import org.slf4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

@Service
public class YouTubeDownloadService {
    private final Logger logger;

    private static final String command = "python3";
    private static final Resource downloadScript = new ClassPathResource("scripts/download_youtube_video.py");

    public YouTubeDownloadService(Logger logger) {
        this.logger = logger;
    }

    public void downloadYouTubeVideo(String url) {
        try {
            Process process = new ProcessBuilder(command, downloadScript.getFile().getAbsolutePath(), url)
                    .start();
            logProgramOutput(process.getInputStream(), process.getErrorStream());
            process.waitFor(10, TimeUnit.SECONDS);
            logger.info("completed download of video {}", url);
        } catch (IOException e) {
            throw new RuntimeException("could not install video", e);
        } catch (InterruptedException e) {
            throw new RuntimeException("video installation timed out", e);
        }
    }

    private void logProgramOutput(InputStream regular, InputStream error) throws IOException {
        BufferedReader regIn = new BufferedReader(new InputStreamReader(regular));
        BufferedReader errIn = new BufferedReader(new InputStreamReader(error));
        String rLine, eLine;
        while ((rLine = regIn.readLine()) != null) {
            logger.info("<script output>: {}", rLine);
        }
        while ((eLine = errIn.readLine()) != null) {
            logger.error("<script error>: {}", eLine);
        }
    }
}
