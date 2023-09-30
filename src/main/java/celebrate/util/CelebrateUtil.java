package celebrate.util;

import java.util.UUID;

public class CelebrateUtil {
	
	public static String generateNewGuestId() {
		return UUID.randomUUID().toString();
	}
	
	public static String generateNewToken() {
		return UUID.randomUUID().toString();
	}

}
