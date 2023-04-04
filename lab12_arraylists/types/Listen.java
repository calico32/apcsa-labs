package lab12_arraylists.types;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.UUID;

@JsonTypeName("listens")
public class Listen {
    private TrackMetadata trackMetadata;
    private long listenedAt;
    private UUID recordingMsid;
    private String userName;
    private long insertedAt;

    @JsonProperty("track_metadata")
    public TrackMetadata getTrackMetadata() {
        return trackMetadata;
    }

    @JsonProperty("track_metadata")
    public void setTrackMetadata(TrackMetadata value) {
        this.trackMetadata = value;
    }

    @JsonProperty("listened_at")
    public long getListenedAt() {
        return listenedAt;
    }

    @JsonProperty("listened_at")
    public void setListenedAt(long value) {
        this.listenedAt = value;
    }

    @JsonProperty("recording_msid")
    public UUID getRecordingMsid() {
        return recordingMsid;
    }

    @JsonProperty("recording_msid")
    public void setRecordingMsid(UUID value) {
        this.recordingMsid = value;
    }

    @JsonProperty("user_name")
    public String getUserName() {
        return userName;
    }

    @JsonProperty("user_name")
    public void setUserName(String value) {
        this.userName = value;
    }

    @JsonProperty("inserted_at")
    public long getInsertedAt() {
        return insertedAt;
    }

    @JsonProperty("inserted_at")
    public void setInsertedAt(long value) {
        this.insertedAt = value;
    }
}
