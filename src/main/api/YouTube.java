package api;

import log.Logger;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;

public class YouTube {
    private String title;
    private String artist;
    private String name;

    private static final String SEPERATOR = "-";

    //TODO: Replace this with YouTube Data API
    public YouTube(String url) {
        Logger logger = new Logger("YouTube", Level.SEVERE, Level.SEVERE);
        try {
            title = getTitleFromURL(url);
            assert title != null;
            if (title.contains(SEPERATOR)) {
                artist = title.split(SEPERATOR)[0];
                if (artist.endsWith(" ")) {
                    artist = artist.substring(0, artist.length() - 1);
                }
                name = title.split(SEPERATOR)[1];
                if (name.startsWith(" ")) {
                    name = name.replaceFirst(" ", "");
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.toString());
        }
    }

    public String getTitleFromURL(String youtubeUrl) throws IOException {
        if (youtubeUrl != null) {
            URL embededURL = new URL("http://www.youtube.com/oembed?url=" +
                    youtubeUrl + "&format=json"
            );

            return new JSONObject(IOUtils.toString(embededURL)).getString("title");
        }
        return null;
    }

    public String getTitle() {
        return title;
    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }
}
