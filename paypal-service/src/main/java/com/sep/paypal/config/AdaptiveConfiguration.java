package com.sep.paypal.config;
import java.util.HashMap;
import java.util.Map;


    // Creates a configuration map containing credentials and other required configuration parameters.

public class AdaptiveConfiguration {
    //davidvuletas_api1.hotmail.com
    //V65H3PXVXTXX7MCL

    //AATvCssphvpBDKimCvTHYRCb92NKApXJg8JZ2r10aowsdO1iuHS6Oqu2
    //APP-80W284485P519543T
    public static final Map<String,String> getAcctAndConfig(){
        Map<String,String> configMap = new HashMap<String,String>();
        configMap.putAll(getConfig());

        // Account Credential
        configMap.put("acct1.UserName", "davidvuletas-facilitator_api1.hotmail.com");
        configMap.put("acct1.Password", "9VDE7D2FNEEV7NNZ");
        configMap.put("acct1.Signature", "A3ejB8ILKLcnXEmI9gnSbvIApl3WAKiiOReECJ1icAFpbFLuYNW0O1sN");
        configMap.put("acct1.AppId", "APP-80W284485P519543T");

        // Sandbox Email Address
        configMap.put("sandbox.EmailAddress", "davidvuletas-facilitator@hotmail.com");

        return configMap;
    }

    public static final Map<String,String> getConfig(){
        Map<String,String> configMap = new HashMap<String,String>();

        // Endpoints are varied depending on whether sandbox OR live is chosen for mode
        configMap.put("mode", "sandbox");

        // These values are defaulted in SDK. If you want to override default values, uncomment it and add your value.
        // configMap.put("http.ConnectionTimeOut", "5000");
        // configMap.put("http.Retry", "2");
        // configMap.put("http.ReadTimeOut", "30000");
        // configMap.put("http.MaxConnection", "100");
        return configMap;
    }
}
