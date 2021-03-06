package ca.jrvs.apps.twitter.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.JsonParser;
import ca.jrvs.apps.twitter.util.TweetUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Before;
import org.junit.Test;

public class TwitterDaoIntTest {

  private TwitterDao dao;

  @Before
  public void setUp() {
    String CONSUMER_KEY = System.getenv("consumerKey");
    String CONSUMER_SECRET = System.getenv("consumerSecret");
    String ACCESS_TOKEN = System.getenv("accessToken");
    String TOKEN_SECRET = System.getenv("tokenSecret");

    //setup dependency
    HttpHelper twitterHttpHelper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_SECRET,
        ACCESS_TOKEN, TOKEN_SECRET);
    //pass dependency
    dao = new TwitterDao(twitterHttpHelper);
  }

  @Test
  public void shouldCreateNewPost() throws Exception {
    String hashtag = "#abc";
    Tweet actualTweet = TweetUtil.createTweet();
    System.out.println(JsonParser.toJson(actualTweet, true, false));

    Tweet tweet = dao.create(actualTweet);

    assertEquals(actualTweet.getText(), tweet.getText());
    assertNotNull(tweet.getCoordinates());
    assertEquals(2, tweet.getCoordinates().getLongLat().length);
    assertEquals(1, tweet.getCoordinates().getLongLat()[0], 0.0001);
    assertEquals(-1, tweet.getCoordinates().getLongLat()[1], 0.0001);
    assertTrue(hashtag.contains(tweet.getEntities().getHashtags().get(0).getText()));
    System.out.println(JsonParser.toJson(tweet, true, false));
  }

  @Test
  public void shouldFindPostById() throws JsonProcessingException {
    String hashtag = "#abc";
    Tweet postTweet = TweetUtil.createTweet();
    System.out.println(JsonParser.toJson(postTweet, true, false));
    Tweet postedTweet = dao.create(postTweet);
    String id = postedTweet.getIdString();

    Tweet tweet = dao.findById(id);

    assertEquals(postTweet.getText(), tweet.getText());
    assertNotNull(tweet.getCoordinates());
    assertEquals(2, tweet.getCoordinates().getLongLat().length);
    assertEquals(1, tweet.getCoordinates().getLongLat()[0], 0.0001);
    assertEquals(-1, tweet.getCoordinates().getLongLat()[1], 0.0001);
    assertTrue(hashtag.contains(tweet.getEntities().getHashtags().get(0).getText()));
  }

  @Test
  public void shouldDeletePostById() {
    String hashtag = "#abc";
    Tweet postTweet = TweetUtil.createTweet();
    Tweet postedTweet = dao.create(postTweet);
    String id = postedTweet.getIdString();

    Tweet tweet = dao.deleteById(id);

    assertEquals(postTweet.getText(), tweet.getText());
    assertNotNull(tweet.getCoordinates());
    assertEquals(2, tweet.getCoordinates().getLongLat().length);
    assertEquals(1, tweet.getCoordinates().getLongLat()[0], 0.0001);
    assertEquals(-1, tweet.getCoordinates().getLongLat()[1], 0.0001);
    assertTrue(hashtag.contains(tweet.getEntities().getHashtags().get(0).getText()));
  }
}