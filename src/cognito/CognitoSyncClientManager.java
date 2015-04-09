/**
 * Copyright 2010-2014 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package cognito;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.cognito.CognitoSyncManager;
import com.amazonaws.regions.Regions;

import android.content.Context;
import android.util.Log;

public class CognitoSyncClientManager{
    /**
     * account id and pool id associated with the app see the readme for details
     * on what to fill these fields in with
     */
   // private static final String IDENTITY_POOL_ID = "us-east-1:d93cca87-9f6b-40cb-aef1-e8920fb66914";
	private static final String IDENTITY_POOL_ID = "us-east-1:7e54d9a5-3ea3-4250-a33e-fdf9bc92c8d7";
    //private static final String AWS_ACCOUNT_ID = "enter your acc";
    /**
     * the role arn to be assumed. You can provide a role arn for unauthorized
     * user and one for authorized.
     */
   // private static final String UNAUTH_ROLE_ARN = "arn:aws:iam::892446706032:role/Cognito_tpadvUnauth_DefaultRole";
   // private static final String AUTH_ROLE_ARN = "arn:aws:iam::892446706032:role/Cognito_tpadvAuth_DefaultRole";
    
    private static CognitoCachingCredentialsProvider provider;
    private static CognitoSyncManager client=null;
    public static void init(Context context) {
    	//provider = new CognitoCachingCredentialsProvider(context,
          //      AWS_ACCOUNT_ID, IDENTITY_POOL_ID, UNAUTH_ROLE_ARN, AUTH_ROLE_ARN,Regions.US_EAST_1);
        //provider = new CognitoCachingCredentialsProvider(context,IDENTITY_POOL_ID,Regions.US_EAST_1);
    	Log.d("LogTag", "my ID get");
    	
    	provider = new CognitoCachingCredentialsProvider(context,IDENTITY_POOL_ID,Regions.US_EAST_1);
    
    //	Log.d("LogTag", "my ID is " + provider.getIdentityId());
    	client = new CognitoSyncManager(context, Regions.US_EAST_1, provider);
    }
    
    public static CognitoSyncManager getInstance()
    {
    	//Log.d("LogTag", "my ID is " + provider.getIdentityId()+"in getinstance");
    	if (client == null) {
            throw new IllegalStateException("client not initialized yet");
        }
    	return client;
    }
    
   

	public static void addLogins(String providerName, String token) {
		// TODO Auto-generated method stub

        Map<String, String> logins = provider.getLogins();
        if (logins == null) {
            logins = new HashMap<String, String>();
        }
        logins.put(providerName, token);
        provider.setLogins(logins);
        provider.refresh();
        Log.d("LogTag", "my ID is " + provider.getIdentityId());
	}
	public static CognitoCachingCredentialsProvider getProvider()
	{
		return provider;
	}
   
}
