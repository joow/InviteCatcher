package org.invitecatcher;

import com.google.gson.annotations.SerializedName;

import java.time.Instant;

public class Submission implements Comparable<Submission> {
    private static final String LINK_FLAIR_TEXT_OFFER = "OFFERING";

    private Data data;

    public String getId() {
        return data.id;
    }

    public String getTitle() {
        return data.title;
    }

    public String getText() {
        return data.text;
    }

    public String getUrl() {
        return data.url;
    }

    public Instant getCreatedAt() {
        return Instant.ofEpochSecond(data.created);
    }

    public boolean isOffer() {
        return LINK_FLAIR_TEXT_OFFER.equalsIgnoreCase(data.linkFlairText);
    }

    @Override
    public String toString() {
        return "[id=" + data.id + ", title=" + data.title + ", linkFlairText=" + data.linkFlairText + "]";
    }

    @Override
    public int compareTo(Submission submission) {
        return (int) (submission.data.created - data.created);
    }

    private static class Data {
        private String id;
        private String title;
        @SerializedName("selftext")
        private String text;
        private String url;
        @SerializedName("link_flair_text")
        private String linkFlairText;
        @SerializedName("created_utc")
        private Long created;
    }
}
