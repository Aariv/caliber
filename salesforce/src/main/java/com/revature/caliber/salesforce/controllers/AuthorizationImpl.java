package com.revature.caliber.salesforce.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.caliber.salesforce.Helper;
import com.revature.caliber.salesforce.interfaces.Authorization;
import com.revature.caliber.salesforce.models.SalesforceToken;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by louislopez on 1/18/17.
 */


@RestController
public class AuthorizationImpl extends Helper implements Authorization {
    @Value("#{systemEnvironment['SALESFORCE_AUTH_URL']}")
    private String authURL;
    @Value("#{systemEnvironment['SALESFORCE_ACCESS_TOKEN_URL']}")
    private String accessTokenURL;
    @Value("#{systemEnvironment['SALESFORCE_CLIENT_ID']}")
    private String clientId;
    @Value("#{systemEnvironment['SALESFORCE_CLIENT_SECRET']}")
    private String clientSecret;
    @Value("#{systemEnvironment['SALESFORCE_REDIRECT_URI']}")
    private String redirectUri;
    private SalesforceToken salesforceToken;
    private HttpClient httpClient;
    private HttpResponse response;

    public AuthorizationImpl() {
        httpClient = HttpClientBuilder.create().build();
    }

    @RequestMapping("/")
    public ModelAndView openAuthURI() {
        return new ModelAndView("redirect:" + authURL +
                "?response_type=code&client_id=" + clientId +
                "&redirect_uri=" + redirectUri);
    }

    @RequestMapping("/authenticated")
    public void generateSalesforceToken(@RequestParam(value = "code") String code) throws IOException {
            HttpPost post = new HttpPost(accessTokenURL);
            List<NameValuePair> parameters = new ArrayList<>();
            parameters.add(new BasicNameValuePair("grant_type", "authorization_code"));
            parameters.add(new BasicNameValuePair("client_secret", clientSecret));
            parameters.add(new BasicNameValuePair("client_id", clientId));
            parameters.add(new BasicNameValuePair("redirect_uri", redirectUri));
            parameters.add(new BasicNameValuePair("code", code));
            post.setEntity(new UrlEncodedFormEntity(parameters));
            response = httpClient.execute(post);
            salesforceToken =
                    new ObjectMapper().readValue(response.getEntity().getContent(), SalesforceToken.class);
    }

    @RequestMapping(value = "/getSalesforceToken", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getSalesforceToken() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(salesforceToken);
    }

    public void setAuthURL(String authURL) {
        this.authURL = authURL;
    }

    public void setAccessTokenURL(String accessTokenURL) {
        this.accessTokenURL = accessTokenURL;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }
}
