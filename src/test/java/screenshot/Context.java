package screenshot;

import java.util.HashMap;
import java.util.Map;

public class Context {

	Map<String, Object> mp;

	public Context() {
		mp = new HashMap<>();
	}

	public void setContext(Constants key, Object value) {
		mp.put(key.toString(), value);
	}

	public Object getcontext(Constants key) {
		return mp.get(key.toString());
	}
}
