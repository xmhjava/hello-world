package proxyjdk;

import java.lang.reflect.Method;

public interface InvocationHandler {
	public Object invoke(Method method,Object args[]);
}
