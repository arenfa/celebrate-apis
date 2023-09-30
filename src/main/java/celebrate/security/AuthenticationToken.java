package celebrate.security;

import java.io.Serializable;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

//import org.apache.commons.codec.binary.Base64;

/**
 * @author arenfakhourian
 *
 */
public class AuthenticationToken implements Serializable{
	private static final long serialVersionUID = 1L;
	public static final String USER_NAME = "UserName";
	public static final String CLAIMED_ID = "claimed_id";
	public static final String EXPIRATION_TIME = "expirationTime";
	public static final String TOKEN_ID_PROPERTY = "TokenID";
	private String claimedIdentifier = null;
	private Map<String, String> identityProperties = new HashMap<String, String>();
	private Calendar expirationTime = null;
	private byte[] nonce = null;
	private String identityProviderUrl = null;
	private String tokenID;


	public AuthenticationToken() {
		super();
	}

	/**
	 * Creates the token without performing signature validation.
	 * @param opaqueToken
	 * @throws GException
	 */
	public AuthenticationToken(String opaqueToken) throws Exception {
		super();
		init(opaqueToken, null, false);
	}
	
	public AuthenticationToken(String opaqueToken, PublicKey publicKey) throws Exception {
		super();
		init(opaqueToken, publicKey, true);
	}
	
	private void init(String opaqueToken, PublicKey publicKey, boolean verify) throws Exception {
		if (verify && publicKey == null) {
			//throw new GException("public key is required for validating signature");
		}
		if (opaqueToken == null) {
			//throw new GException(SecurityErrorCode.ERROR_INVALID_TOKEN);
		}
		int sigIndex = opaqueToken.indexOf(",sig=");
		if (sigIndex <= 0) {
			//throw new GException(SecurityErrorCode.ERROR_INVALID_TOKEN);
		}
		String signature = decode(opaqueToken.substring(sigIndex+5));
		String token_without_signature = opaqueToken.substring(0, sigIndex);
		if (verify) {
			boolean verifySuccess = false;
			try {
				Signature sig = Signature.getInstance("SHA1WithRSA");
				sig.initVerify(publicKey);
				sig.update(token_without_signature.getBytes("utf-8"));
				//Base64 urlSafeBase64 = new Base64(true);
				//verifySuccess = sig.verify(urlSafeBase64.decode(signature));
			} catch (Throwable t) {
				//throw new GException(t, "error in validating the token");
			}
			if (!verifySuccess) {
				//throw new GException(SecurityErrorCode.ERROR_INVALID_TOKEN);
			}
		}
		parseToken(token_without_signature);
		long now = new Date().getTime();
		long expirationTime = Long.parseLong(getIdentityProperties().get(EXPIRATION_TIME));
		if (now > expirationTime) {
			//log.trace("Token has expired. Now="+now+", expirationTime in token="+expirationTime);
		}
		Calendar expirationTimeCal = Calendar.getInstance();
		expirationTimeCal.setTimeInMillis(expirationTime);
		this.expirationTime = expirationTimeCal;
	}

	private void parseToken(String tokenWithoutSignature) {
		try {
			String[] tokens = tokenWithoutSignature.split(",");
			for (String token : tokens) {
				String[] keyValue = token.split("=");
				if (keyValue.length == 2) {
					String keyName = decode(keyValue[0]);
					String value = decode(keyValue[1]);
					getIdentityProperties().put(keyName, value);
					if (CLAIMED_ID.equals(keyName)) {
						claimedIdentifier = value;
					}
					if (TOKEN_ID_PROPERTY.equals(keyName)) {
						this.tokenID = value;
					}
				} else {
					//log.error("The token split has undesired length "+keyValue.length+" for token "+token);
				}
			}
		} catch(ArrayIndexOutOfBoundsException e) {
			//log.error("Error parsing token without signture part. token is not constructed properly.");
			//TODO
		}
	}

	public String getClaimedIdentifier() {
		return claimedIdentifier;
	}
	public void setClaimedIdentifier(String claimedIdentifier) {
		this.claimedIdentifier = claimedIdentifier;
	}
	public Map<String, String> getIdentityProperties() {
		return identityProperties;
	}
	public void setIdentityProperties(Map<String, String> identityProperties) {
		this.identityProperties = identityProperties;
	}
	public Calendar getExpirationTime() {
		return expirationTime;
	}
	public void setExpirationTime(Calendar expirationTime) {
		this.expirationTime = expirationTime;
	}
	public byte[] getNonce() {
		return nonce;
	}
	public void setNonce(byte[] nonce) {
		this.nonce = nonce;
	}
	public String getIdentityProviderUrl() {
		return identityProviderUrl;
	}
	public void setIdentityProviderUrl(String identityProviderUrl) {
		this.identityProviderUrl = identityProviderUrl;
	}
	public void setTokenID(String tokenID) {
		this.tokenID = tokenID;
	}
	public String getTokenID() {
		return tokenID;
	}

	public String getOpaqueString(PrivateKey privateKey) throws Exception {
		StringBuffer buffer = new StringBuffer();
		if (tokenID != null) {
			buffer.append(encode(TOKEN_ID_PROPERTY)).append("=").append(encode(tokenID));
			buffer.append(",");
		}
		buffer.append(encode(CLAIMED_ID)).append("=").append(encode(claimedIdentifier));
		//buffer.append(",");
		//buffer.append(encode("OP_Endpoint_URL")).append("=").append(encode(identityProviderUrl));
		buffer.append(",");
		buffer.append(encode("issueTime")).append("=").append(encode("" + System.currentTimeMillis()));
		buffer.append(",");
		buffer.append(encode(EXPIRATION_TIME)).append("=").append(encode("" + expirationTime.getTimeInMillis()));
		/*buffer.append(",");
		try {
			buffer.append(encode("nonce")).append("=").append(encode(new String(nonce, "utf-8")));
		} catch (UnsupportedEncodingException ex) {throw new RuntimeException(ex);}
		*/
		if (identityProperties != null && identityProperties.size() > 0) {
			for (Iterator<String> iter = identityProperties.keySet().iterator(); iter.hasNext();) {
				buffer.append(",");
				String key = iter.next();
				buffer.append(encode(key) + "=").append(encode(identityProperties.get(key)));
			}
		}
		// calculate signature
		String token_without_signature = buffer.toString();

		return token_without_signature;
	}

	/**
	 * Encodes authentication token specific tokens in a manner that can be decoded later.
	 * URLEncoder is not used since the only tokens that need escaping are ',' and '='
	 * @param unencoded
	 * @return
	 */
	private static String encode(String unencoded) {
		if (unencoded == null)
			return null;

		String encoded = unencoded;
		encoded = encoded.replace("%", "%25"); // This needs to be first to avoid double escaping %
		encoded = encoded.replace(",", "%2C");
		encoded = encoded.replace("=", "%3D");
		encoded = encoded.replace(".", "%2E");
		return encoded;
	}

	/**
	 * Decodes authentication token specific tokens
	 * URLDecoder is not used since the only tokens that need unescaping are ',' and '='
	 * @param unencoded
	 * @return
	 */
	private static String decode(String encoded) {
		if (encoded == null)
			return null;

		String decoded = encoded;
		decoded = decoded.replaceAll("%2[Ee]", ".");
		decoded = decoded.replaceAll("%3[Dd]", "=");
		decoded = decoded.replaceAll("%2[Cc]", ",");
		decoded = decoded.replaceAll("%25", "%"); // This needs to be last to avoid misinterpreting the other escaped tokens
		return decoded;
	}

}
