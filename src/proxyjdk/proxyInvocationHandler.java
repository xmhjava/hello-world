package proxyjdk;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class proxyInvocationHandler implements InvocationHandler{
	 proxyDao h;
	 public proxyInvocationHandler(proxyDao h) {
	       this.h=h;
	}

	@Override
	public Object invoke(Method method, Object[] args)  {
		System.out.println("执行方法之前");
		try {
			method.invoke(h, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("执行方法之后");
		return null;
	}

}
