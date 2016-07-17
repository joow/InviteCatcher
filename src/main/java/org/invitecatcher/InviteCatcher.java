package org.invitecatcher;

import spark.Spark;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class InviteCatcher {

    public static void main(String[] args) throws IOException, InterruptedException {
        final OfferFeedService offerFeedService = new OfferFeedService();
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleWithFixedDelay(new OfferRetriever(offerFeedService), 0, 5, TimeUnit.MINUTES);

        Spark.port(getPort());
        Spark.get("/", (req, res) -> offerFeedService.getAtomFeed(req.url()));
    }

    private static int getPort() {
        final String port = System.getenv("PORT");
        if (port == null || port.isEmpty()) {
            return 4567;
        } else {
            return Integer.valueOf(port);
        }
    }
}
