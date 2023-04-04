package lab12_arraylists;

import lab12_arraylists.types.Listen;
import shared.Input;
import shared.TextSegment;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static shared.TextHelpers.println;
import static shared.TextHelpers.text;

public class ListensShowAll {
    public static void showAll(List<Listen> listens) {
        var choice = Input.readYesNo(true,
            text("This is going to generate %d+ lines of text. Continue?", listens.size() * 3).redBright()
        );

        if (!choice) {
            println("Cancelled.");
            return;
        }

        for (var listen : listens) {
            var s = new ArrayList<TextSegment>();

            var iso = DateTimeFormatter.ISO_DATE_TIME;
            var date = LocalDateTime.ofEpochSecond(listen.getListenedAt(), 0, ZoneOffset.UTC);
            s.add(text("%s: ", iso.format(date)).dim());
            s.add(text(listen.getTrackMetadata().getTrackName()).white().bold());
            s.add(text("\n"));
            s.add(text("  by ").dim());
            s.add(text(listen.getTrackMetadata().getArtistName()).white().bold());

            var release = listen.getTrackMetadata().getReleaseName();
            if (release != null) {
                s.add(text(" on ").dim());
                s.add(text(release).white().bold());
            }
            s.add(text("\n"));

            var mapping = listen.getTrackMetadata().getMbidMapping();
            if (mapping != null) {
                var mbid = mapping.getRecordingMbid();
                s.add(text("  mbid ").dim());
                s.add(text(mbid).dim());
                s.add(text("\n"));
            }

            println(s.toArray(TextSegment[]::new));
        }
    }
}
