package lab12_arraylists;

import lab12_arraylists.mapping.Song;
import lab12_arraylists.types.Listen;
import shared.Input;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static shared.TextHelpers.text;

public class ListensSort {
    public static void byListenCount(List<Listen> listens) {
        var choice = Input.readYesNo(true, text("Sort descending?"));
        var songs = new ArrayList<>(Song.knownSongListens.entrySet().stream().toList());

        if (choice) {
            songs.sort((a, b) -> b.getValue() - a.getValue());
        } else {
            songs.sort(Comparator.comparingInt(Map.Entry::getValue));
        }

        for (var s : songs.subList(0, 10)) {
            var song = s.getKey();
            System.out.printf("%s - %s (%d listens)%n", song.artist().name(), song.name(), Song.knownSongListens.get(song));
        }

    }
}
