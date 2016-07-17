package org.invitecatcher;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubmissionResponse {
    private Data data;

    public List<Submission> getSubmissions() {
        return data.submissions;
    }

    private static class Data {
        @SerializedName("children")
        private List<Submission> submissions;
    }
}
