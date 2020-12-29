package proxyjdk;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import javax.tools.JavaCompiler.CompilationTask;

import jdk.jfr.events.FileWriteEvent;

public class Proxy {
	// 生成一个动态代理对象伪代码
	public static Object newProxyInstance(Class interfaceClass, InvocationHandler hander) {
		String interfaceName = interfaceClass.getName();
		String interfaceSimpleName = interfaceClass.getSimpleName();
		String enter = "\n";
		String tab = "\t";

		// 生成代码字符串
		String proxyStr1 = "import " + interfaceName + enter + ";import java.lang.reflect.Method;" + enter
				+ "import proxyjdk.InvocationHandler;" + enter + "public class proxy$ implements " + interfaceSimpleName
				+ "{ " + enter + tab + "InvocationHandler handler;" + enter + tab + "public proxy$("
				+ hander.getClass().getName() + " hand){" + enter + tab + "this.handler=hand;" + enter + tab + "}";

		// 生成代码字符串2

		System.out.println(proxyStr1);
		String proxyStr2 = "";

		Method me[] = interfaceClass.getMethods();
		for (Method method : me) {
			String paramStr = ""; // 形参字符串
			String paramName = ""; // 参数名字字符串
			String paramClass = "";// 参数类型字符串
			int i = 1;

			Class parameterTypeClass[] = method.getParameterTypes();
			for (Class class1 : parameterTypeClass) {
				paramStr = class1.getSimpleName() + " args" + i + ",";
				paramName = "args" + i + ",";
				paramClass = class1.getSimpleName() + ".class,";
			}
//				
//				//判断改方法是否有参数，有参数length>0
			if (paramStr.length() > 0) {
				paramStr = paramStr.substring(0, paramStr.length() - 1);
				paramName = paramName.substring(0, paramName.length() - 1);
				paramClass = paramClass.substring(0, paramClass.length() - 1);
			}

			String returnType = method.getReturnType().getSimpleName();
			proxyStr2 = enter + tab + "public " + returnType+" " + method.getName() + "(" + paramStr + ") {" + enter + tab
					+ tab + "try{";

			if (returnType.equals("void")) {
				proxyStr2 += enter + tab + tab + "Method md=" + interfaceSimpleName + ".class.getMethod(\""
						+ method.getName() + "\"" + paramClass + ");";
				proxyStr2 += enter + tab + tab + "handler.invoke(md,new Object[]{" + paramName + "});";
				proxyStr2 += enter + tab + tab + "} catch(Exception e){" + enter + tab + tab + "e.printStackTrace();"
						+ enter + tab + tab + "}";
			}

		}

		proxyStr2 += enter + tab + "}"
				
		+"}"		;
        System.out.println(proxyStr2);
        
        File file=new File("D:\\Users\\Proxy$.java");
        try {
			file.createNewFile();
			FileWriter fw=new FileWriter(file);
			fw.write(proxyStr1+proxyStr2);
			fw.flush();
			
			JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
			StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
			Iterable it = fileManager.getJavaFileObjects(file.getPath());
			CompilationTask t = compiler.getTask(null, fileManager, null, null, null, it);
			//进行编译
			t.call();
			
			
			URL url=new URL("file:/D:\\User\\");
			URL[] urls=new URL[] {url};
			URLClassLoader urlClassLoader = new URLClassLoader(urls);
			Class classProxy = urlClassLoader.loadClass("Proxy$");
			Constructor constructor= classProxy.getConstructor(hander.getClass());
			Object object =constructor.newInstance(hander);
			
			
			
			return object;
			
		} catch (Exception e) {
            e.printStackTrace();
		}
        
		return null;

	}

}
