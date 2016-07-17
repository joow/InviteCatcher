package org.invitecatcher;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RedditService {
    @GET("r/{subreddit}/new.json")
    Call<SubmissionResponse> getSubmissions(@Path("subreddit") String subreddit, @Query("before") String before, @Query("limit") int limit);
}
