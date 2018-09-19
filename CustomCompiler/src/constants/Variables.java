package constants;
import java.util.HashMap;
import java.util.Map;

import values.IValue;
import values.NumberValue;

public final class Variables {
	
	private static Map<String , IValue> variables;
	private static final IValue ZERO = new NumberValue(0);
	static {
		variables = new HashMap<>();
		initConstants();
	}
	
	public static boolean isExists(String key) {
		return variables.containsKey(key);
	}
	
	public static IValue get(String key) {
		if (!isExists(key)) return ZERO;
		return variables.get(key);
	}
	
	public static void set(String key , IValue value) {
		variables.put(key, value);
	}
	
	private static void initConstants() {
		variables.put("PI", new NumberValue(Math.PI));
		variables.put("ох", new NumberValue(Math.PI));
		variables.put("E", new NumberValue(Math.E));
		variables.put("GOLDEN_RATIO", new NumberValue(1.618));
	}
}
