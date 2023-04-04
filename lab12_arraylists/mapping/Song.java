package lab12_arraylists.mapping;

import lab12_arraylists.types.Listen;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;
import java.util.WeakHashMap;

public record Song(String name, UUID mbid, Artist artist) {
    public Song {
        knownSongs.add(this);
        knownSongsByName.put(name, this);
        if (mbid != null) {
            knownSongsByMbid.put(mbid, this);
        }
        knownSongsByArtist.put(artist, this);
    }

    public static final HashSet<Song> knownSongs = new HashSet<>();
    public static final HashMap<String, Song> knownSongsByName = new HashMap<>();
    public static final HashMap<UUID, Song> knownSongsByMbid = new HashMap<>();
    public static final WeakHashMap<Artist, Song> knownSongsByArtist = new WeakHashMap<>();
    public static final HashMap<Song, Integer> knownSongListens = new HashMap<>();


    public static Song fromListen(Listen listen) {
        var track = listen.getTrackMetadata();
        var artist = Artist.fromListen(listen);
        var mapping = track.getMbidMapping();

        if (knownSongsByName.containsKey(track.getTrackName())) {
            var song = knownSongsByName.get(track.getTrackName());
            knownSongListens.put(song, knownSongListens.get(song) + 1);
            return song;
        }

        if (mapping != null && knownSongsByMbid.containsKey(mapping.getRecordingMbid())) {
            var song = knownSongsByMbid.get(mapping.getRecordingMbid());
            knownSongListens.put(song, knownSongListens.get(song) + 1);
            return song;
        }

        // fuzzy match name + artist name
//        if (knownSongs.size() > 0) {
//            var song = FuzzySearch.extractOne(track.getTrackName(), knownSongsByName.keySet());
//            if (song != null && song.getScore() > 80) {
//                var songArtist = knownSongsByName.get(song.getString()).artist();
//                if (songArtist.name().equals(artist.name()) || FuzzySearch.ratio(songArtist.name(), artist.name()) > 80) {
//                    println(text("Fuzzy match song: %s -> %s (%d%%)", track.getTrackName(), song.getString(), song.getScore()));
//                    return knownSongsByName.get(song.getString());
//                }
//            }
//        }

        // give up, create new song
        var song = new Song(track.getTrackName(), mapping == null ? null : mapping.getRecordingMbid(), artist);
        knownSongListens.put(song, 1);
        return song;
    }
}
