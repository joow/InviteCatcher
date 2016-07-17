package org.invitecatcher;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.error.PebbleException;
import com.mitchellbosecke.pebble.template.PebbleTemplate;

import java.io.IOException;
import java.io.StringWriter;
import java.time.Instant;
import java.util.*;

public class OfferFeedService implements OfferListener {
    private List<Submission> offers = new ArrayList<>();

    public String getAtomFeed(String url) {
        try {
            PebbleEngine pebbleEngine = new PebbleEngine.Builder().build();
            PebbleTemplate pebbleTemplate = pebbleEngine.getTemplate("feeds.atom");
            Map<String, Object> context = new HashMap<>();
            context.put("url", url);
            context.put("date", Instant.now());
            context.put("offers", offers);

            StringWriter stringWriter = new StringWriter();
            pebbleTemplate.evaluate(stringWriter, context);
            return stringWriter.toString();
        } catch (PebbleException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void offersAdded(List<Submission> newOffers) {
        offers.addAll(newOffers);
    }
}
