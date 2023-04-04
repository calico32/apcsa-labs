package lab12_arraylists.types;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.UUID;

public class MbidMapping {
    private UUID recordingMbid;
    private UUID releaseMbid;
    private List<UUID> artistMbids;
    private List<Artist> artists;
    private Long caaID;
    private UUID caaReleaseMbid;

    @JsonProperty("recording_mbid")
    public UUID getRecordingMbid() {
        return recordingMbid;
    }

    @JsonProperty("recording_mbid")
    public void setRecordingMbid(UUID value) {
        this.recordingMbid = value;
    }

    @JsonProperty("release_mbid")
    public UUID getReleaseMbid() {
        return releaseMbid;
    }

    @JsonProperty("release_mbid")
    public void setReleaseMbid(UUID value) {
        this.releaseMbid = value;
    }

    @JsonProperty("artist_mbids")
    public List<UUID> getArtistMbids() {
        return artistMbids;
    }

    @JsonProperty("artist_mbids")
    public void setArtistMbids(List<UUID> value) {
        this.artistMbids = value;
    }

    @JsonProperty("artists")
    public List<Artist> getArtists() {
        return artists;
    }

    @JsonProperty("artists")
    public void setArtists(List<Artist> value) {
        this.artists = value;
    }

    @JsonProperty("caa_id")
    public Long getCaaID() {
        return caaID;
    }

    @JsonProperty("caa_id")
    public void setCaaID(Long value) {
        this.caaID = value;
    }

    @JsonProperty("caa_release_mbid")
    public UUID getCaaReleaseMbid() {
        return caaReleaseMbid;
    }

    @JsonProperty("caa_release_mbid")
    public void setCaaReleaseMbid(UUID value) {
        this.caaReleaseMbid = value;
    }
}
