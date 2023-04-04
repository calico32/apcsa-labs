package lab12_arraylists.types;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Artist {
    private UUID artistMbid;
    private String artistCreditName;
    private String joinPhrase;

    @JsonProperty("artist_mbid")
    public UUID getArtistMbid() {
        return artistMbid;
    }

    @JsonProperty("artist_mbid")
    public void setArtistMbid(UUID value) {
        this.artistMbid = value;
    }

    @JsonProperty("artist_credit_name")
    public String getArtistCreditName() {
        return artistCreditName;
    }

    @JsonProperty("artist_credit_name")
    public void setArtistCreditName(String value) {
        this.artistCreditName = value;
    }

    @JsonProperty("join_phrase")
    public String getJoinPhrase() {
        return joinPhrase;
    }

    @JsonProperty("join_phrase")
    public void setJoinPhrase(String value) {
        this.joinPhrase = value;
    }
}
