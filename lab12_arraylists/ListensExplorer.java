package lab12_arraylists;

import lab12_arraylists.mapping.Song;
import lab12_arraylists.types.Converter;
import lab12_arraylists.types.Listen;
import shared.Input;
import shared.Text;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.zip.GZIPInputStream;

import static shared.TextHelpers.println;
import static shared.TextHelpers.text;

public class ListensExplorer {
    static MenuOption[] menu = new MenuOption[]{
        new MenuOption("Show all listens", ListensShowAll::showAll),
        new MenuOption("Show top 50 by listen count", ListensSort::byListenCount),
    };

    public static void main(String[] args) throws IOException {
        var gz = Text.class.getClassLoader().getResourceAsStream("calico32.json.gz");
        assert gz != null;
        var file = new GZIPInputStream(gz);
        var json = Converter.fromJsonString(file.readAllBytes());
        var listens = json.getListens();
        Collections.reverse(listens);

        log("Read %d listens", listens.size());
        println(text("Listens analyzer", listens.size()).bold().randomRainbow());

        for (var listen : listens) {
            Song.fromListen(listen);
        }


        println(text("What would you like to do?"));
        for (int i = 0; i < menu.length; i++) {
            println(text("%2s. %s", Integer.valueOf(i + 1).toString(), menu[i].name()));
        }

        var choice = Input.readInt(1, menu.length);

        menu[choice - 1].action.accept(listens);
    }

    public static void log(Object o) {
        println(text("[").dim(), text(o).dim(), text("]").dim());
    }

    public static void log(String format, Object... args) {
        println(text("[").dim(), text(format, args).dim(), text("]").dim());
    }

    record MenuOption(String name, Consumer<List<Listen>> action) {
    }
}
