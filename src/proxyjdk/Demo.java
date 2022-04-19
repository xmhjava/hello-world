package proxyjdk;

public class Demo {
public static void main(String[] args) {
	//目标对象
	proxyDao daoImpl = new proxyDaoImpl();
	//代理对应
	InvocationHandler proxy = new proxyInvocationHandler(daoImpl);
	Proxy.newProxyInstance(daoImpl.getClass(),proxy);
	//测试结构111000000000000
}
}
