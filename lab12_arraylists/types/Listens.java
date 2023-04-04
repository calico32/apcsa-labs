package lab12_arraylists.types;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Listens {
    private List<Listen> listens;

    @JsonProperty("listens")
    public List<Listen> getListens() {
        return listens;
    }

    @JsonProperty("listens")
    public void setListens(List<Listen> value) {
        this.listens = value;
    }
}
