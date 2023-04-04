package lab12_arraylists.types;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class AdditionalInfo {
    private UUID recordingMsid;
    private Long duration;
    private String originURL;
    private String submissionClient;
    private String musicServiceName;
    private String submissionClientVersion;

    @JsonProperty("recording_msid")
    public UUID getRecordingMsid() {
        return recordingMsid;
    }

    @JsonProperty("recording_msid")
    public void setRecordingMsid(UUID value) {
        this.recordingMsid = value;
    }

    @JsonProperty("duration")
    public Long getDuration() {
        return duration;
    }

    @JsonProperty("duration")
    public void setDuration(Long value) {
        this.duration = value;
    }

    @JsonProperty("origin_url")
    public String getOriginURL() {
        return originURL;
    }

    @JsonProperty("origin_url")
    public void setOriginURL(String value) {
        this.originURL = value;
    }

    @JsonProperty("submission_client")
    public String getSubmissionClient() {
        return submissionClient;
    }

    @JsonProperty("submission_client")
    public void setSubmissionClient(String value) {
        this.submissionClient = value;
    }

    @JsonProperty("music_service_name")
    public String getMusicServiceName() {
        return musicServiceName;
    }

    @JsonProperty("music_service_name")
    public void setMusicServiceName(String value) {
        this.musicServiceName = value;
    }

    @JsonProperty("submission_client_version")
    public String getSubmissionClientVersion() {
        return submissionClientVersion;
    }

    @JsonProperty("submission_client_version")
    public void setSubmissionClientVersion(String value) {
        this.submissionClientVersion = value;
    }
}
