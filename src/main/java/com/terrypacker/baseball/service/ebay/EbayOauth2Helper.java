package com.terrypacker.baseball.service.ebay;

import com.ebay.api.client.auth.oauth2.CredentialUtil;
import com.ebay.api.client.auth.oauth2.OAuth2Api;
import com.ebay.api.client.auth.oauth2.model.AccessToken;
import com.ebay.api.client.auth.oauth2.model.Environment;
import com.ebay.api.client.auth.oauth2.model.OAuthResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;

/**
 * @author Terry Packer
 */
@Component
public class EbayOauth2Helper {
    private static final String EBAY_YAML = """
        api.ebay.com:
          appid: %s
          certid: %s
          devid: %s
          redirecturi: %s
        """;
    private static final String EBAY_SANDBOX_YAML = """
        api.sandbox.ebay.com:
          appid: %s
          certid: %s
          devid: %s
          redirecturi: %s
        """;

    private static final List<String> SCOPE_LIST_SANDBOX = Arrays.asList(new String[]{"https://api.ebay.com/oauth/api_scope", "https://api.ebay.com/oauth/api_scope/buy.item.feed"});
    private static final List<String> SCOPE_LIST_PRODUCTION = Arrays.asList(new String[]{"https://api.ebay.com/oauth/api_scope"});

    private final OAuth2Api oauth2Api = new OAuth2Api();
    private final Boolean production;
    private final Environment env;

    private final String sandboxAppId;
    private final String sandBoxCertId;
    private final String sandBoxDevId;
    private final String sandBoxRedirectUri;
    private final String sandboxBrowseUrl;

    private final String appId;
    private final String certId;
    private final String devId;
    private final String redirectUri;
    private final String browseUrl;

    public EbayOauth2Helper(@Value("${ebay.production}") Boolean production,
        @Value("${ebay.api.sandbox.ebay.com.appid}") String sandboxAppId,
        @Value("${ebay.api.sandbox.ebay.com.certid}") String sandBoxCertId,
        @Value("${ebay.api.sandbox.ebay.com.devid}") String sandBoxDevId,
        @Value("${ebay.api.sandbox.ebay.com.redirecturi}") String sandBoxRedirectUri,
        @Value("${ebay.api.sandbox.ebay.com.browse-url}") String sandboxBrowseUrl,
        @Value("${ebay.api.ebay.com.appid}") String appId,
        @Value("${ebay.api.ebay.com.certid}") String certId,
        @Value("${ebay.api.ebay.com.devid}") String devId,
        @Value("${ebay.api.ebay.com.redirecturi}") String redirectUri,
        @Value("${ebay.api.ebay.com.browse-url}") String browseUrl) {
        this.production = production;

        this.sandboxAppId = sandboxAppId;
        this.sandBoxCertId = sandBoxCertId;
        this.sandBoxDevId = sandBoxDevId;
        this.sandBoxRedirectUri = sandBoxRedirectUri;
        this.sandboxBrowseUrl = sandboxBrowseUrl;

        this.appId = appId;
        this.certId = certId;
        this.devId = devId;
        this.redirectUri = redirectUri;
        this.browseUrl = browseUrl;

        String config;
        if(this.production) {
            this.env = Environment.PRODUCTION;
            config = String.format(EBAY_YAML,
                appId, certId, devId, redirectUri);
        }else {
            this.env = Environment.SANDBOX;
            config = String.format(EBAY_SANDBOX_YAML,
                sandboxAppId, sandBoxCertId, sandBoxDevId, sandBoxRedirectUri);
        }
        org.yaml.snakeyaml.Yaml yaml = new Yaml(new SafeConstructor(new LoaderOptions()));
        CredentialUtil.load(config);
    }

    public Optional<AccessToken> getAccessToken() throws IOException {
        OAuthResponse oauth2Response = oauth2Api.getApplicationToken(env, getScopeList());
        return oauth2Response.getAccessToken();
    }

    /**
     * Get the base URL based on sandbox or production settings
     * @return
     */
    public String getBrowseBaseUrl() {
        if(production) {
            return browseUrl;
        }else {
            return sandboxBrowseUrl;
        }
    }

    /**
     * Get scope list based on settings of application
     * @return
     */
    private List<String> getScopeList() {
        if(this.production) {
            return SCOPE_LIST_PRODUCTION;
        }else {
            return SCOPE_LIST_SANDBOX;
        }
    }

}
