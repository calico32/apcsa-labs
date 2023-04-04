package lab12_arraylists.mapping;

import lab12_arraylists.types.Listen;

import java.util.*;

public record Artist(String name, UUID mbid) {
    public Artist {
        knownArtists.add(this);
        knownArtistsByName.put(name, this);
        if (mbid != null) {
            knownArtistsByMbid.put(mbid, this);
        }
    }

    public static final HashSet<Artist> knownArtists = new HashSet<>();
    public static final HashMap<String, Artist> knownArtistsByName = new HashMap<>();
    public static final HashMap<UUID, Artist> knownArtistsByMbid = new HashMap<>();

    public static Artist fromListen(Listen listen) {
        var track = listen.getTrackMetadata();
        var mapping = track.getMbidMapping();

        if (knownArtistsByName.containsKey(track.getArtistName())) {
            return knownArtistsByName.get(track.getArtistName());
        }

        if (mapping != null && knownArtistsByMbid.containsKey(mapping.getArtistMbids().get(0))) {
            return knownArtistsByMbid.get(mapping.getArtistMbids().get(0));
        }

        // give up, create new artist
        return new Artist(track.getArtistName(), mapping == null ? null : mapping.getArtistMbids().get(0));
    }
}
