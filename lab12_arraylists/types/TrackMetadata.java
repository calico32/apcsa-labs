package lab12_arraylists.types;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TrackMetadata {
    private String artistName;
    private String releaseName;
    private AdditionalInfo additionalInfo;
    private String trackName;
    private MbidMapping mbidMapping;

    @JsonProperty("artist_name")
    public String getArtistName() {
        return artistName;
    }

    @JsonProperty("artist_name")
    public void setArtistName(String value) {
        this.artistName = value;
    }

    @JsonProperty("release_name")
    public String getReleaseName() {
        return releaseName;
    }

    @JsonProperty("release_name")
    public void setReleaseName(String value) {
        this.releaseName = value;
    }

    @JsonProperty("additional_info")
    public AdditionalInfo getAdditionalInfo() {
        return additionalInfo;
    }

    @JsonProperty("additional_info")
    public void setAdditionalInfo(AdditionalInfo value) {
        this.additionalInfo = value;
    }

    @JsonProperty("track_name")
    public String getTrackName() {
        return trackName;
    }

    @JsonProperty("track_name")
    public void setTrackName(String value) {
        this.trackName = value;
    }

    @JsonProperty("mbid_mapping")
    public MbidMapping getMbidMapping() {
        return mbidMapping;
    }

    @JsonProperty("mbid_mapping")
    public void setMbidMapping(MbidMapping value) {
        this.mbidMapping = value;
    }
}
