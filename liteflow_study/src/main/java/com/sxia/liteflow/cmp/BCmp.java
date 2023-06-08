package com.sxia.liteflow.cmp;

import com.yomahub.liteflow.core.NodeComponent;
import org.springframework.stereotype.Component;
@Component("b")
public class BCmp extends NodeComponent {

	@Override
	public void process() {
		Object data = this.getPrivateDeliveryData();
		System.out.println("获得从上一个组件传递过来的参数:"+data);
		System.out.println("执行了b");
	}
}
