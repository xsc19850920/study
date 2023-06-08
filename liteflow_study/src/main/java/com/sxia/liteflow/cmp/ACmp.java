package com.sxia.liteflow.cmp;

import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import com.yomahub.liteflow.slot.DefaultContext;
import org.springframework.stereotype.Component;

import java.util.HashSet;
@LiteflowComponent(id="a",name = "普通组件A")
public class ACmp extends NodeComponent {

	@Override
	public void process() {
		System.out.println(this.getName());
		//设置私有变量
//		DefaultContext context = this.getContextBean(DefaultContext.class);
//		context.setData("testSet", new HashSet<>());
		for (int i = 0; i < 5; i++) {
			this.sendPrivateDeliveryData("b",i+1);
		}
		System.out.println("执行了a");
	}
}
