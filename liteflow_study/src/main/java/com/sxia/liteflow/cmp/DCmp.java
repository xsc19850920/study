package com.sxia.liteflow.cmp;

import com.yomahub.liteflow.core.NodeComponent;
import org.springframework.stereotype.Component;
@Component("d")
public class DCmp extends NodeComponent {

	@Override
	public void process() {
		System.out.println("执行了d");
	}
}
