package com.javatemplate.fakes;

import org.springframework.social.facebook.api.TestUser;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.connect.FacebookServiceProvider;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;

import java.util.ArrayList;
import java.util.List;

public abstract class FacebookUserTestFake {

    private final List<String> testUserIds = new ArrayList<>();
    private final String appId;
    private final String appSecret;
    protected FacebookTemplate facebookTemplate;


    public FacebookUserTestFake(String appId, String appSecret) {
        this.appId = appId;
        this.appSecret = appSecret;
    }

    public void setup() {
        OAuth2Operations oauth = new FacebookServiceProvider(appId, appSecret, null).getOAuthOperations();
        AccessGrant clientGrant = oauth.authenticateClient();
        facebookTemplate = new FacebookTemplate(clientGrant.getAccessToken(), "", appId);
    }

    public TestUser createTestUser(boolean installed, String permissions, String name) {
        TestUser testUser = facebookTemplate.testUserOperations().createTestUser(installed, permissions, name);
        testUserIds.add(testUser.getId());
        return testUser;
    }

    public void teardown() {
        for (String testUserId : testUserIds) {
            facebookTemplate.testUserOperations().deleteTestUser(testUserId);
        }
    }
}
