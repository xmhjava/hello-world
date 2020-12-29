package proxyjdk;

public class proxyDaoImpl implements proxyDao{

	@Override
	public void age(int i) {
	System.out.println("###查询年龄大小####");
		
	}

	@Override
	public void name(int i, String name) {
		System.out.println("####查询姓名名称####");
		
	}

}
