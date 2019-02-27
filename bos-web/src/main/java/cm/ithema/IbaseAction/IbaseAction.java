package cm.ithema.IbaseAction;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

// 表现层代码抽取
public class IbaseAction<T> extends ActionSupport implements ModelDriven{
	
	
	protected T model;

	@Override
	public T getModel() {
		// TODO Auto-generated method stub
		return model;
	}

	public IbaseAction() {
		ParameterizedType para = (ParameterizedType) this.getClass().getGenericSuperclass();
		Type[] tyeps = para.getActualTypeArguments();
		Class entityClass = (Class) tyeps[0];
		try {
			model = (T) entityClass.newInstance();
			System.out.println(model + "对象被创建了！");
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
