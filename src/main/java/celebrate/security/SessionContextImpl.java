package celebrate.security;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@Scope(value="prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionContextImpl implements SessionContext {
	
	private long userId;

	@Override
	public long getUserId() {
		return this.userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(long userId) {
		this.userId = userId;
	}

}
