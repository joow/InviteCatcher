package org.invitecatcher;

import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class OfferRetriever implements Runnable {
    private static final String SUBREDDIT_NAME = "UsenetInvites";
    private static final int SUBMISSIONS_LIMIT = 100;

    private final OfferListener listener;
    private final RedditService redditService;
    private String latestSubmissionId;

    public OfferRetriever(OfferListener listener) {
        this.listener = listener;
        this.redditService = buildRedditService();
    }

    private RedditService buildRedditService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.reddit.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(RedditService.class);
    }

    @Override
    public void run() {
        try {
            List<Submission> submissions = getSubmissions(latestSubmissionId);
            latestSubmissionId = getLatestSubmissionId(submissions, latestSubmissionId);
            System.out.println(String.format("The latest submission id is %s", latestSubmissionId));
            List<Submission> offers = submissions.stream().filter(Submission::isOffer).collect(Collectors.toList());
            if (offers == null || offers.isEmpty()) {
                System.out.println("There is no new offer :(");
            } else {
                listener.offersAdded(offers);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Submission> getSubmissions(String latestSubmissionId) throws IOException {
        String before = latestSubmissionId == null ? "" : "t3_" + latestSubmissionId;

        return redditService
                .getSubmissions(SUBREDDIT_NAME, before, SUBMISSIONS_LIMIT)
                .execute()
                .body()
                .getSubmissions();
    }

    private String getLatestSubmissionId(List<Submission> submissions, String latestSubmissionId) {
        return submissions
                .stream()
                .sorted()
                .findFirst()
                .map(Submission::getId)
                .orElse(latestSubmissionId);
    }
}
