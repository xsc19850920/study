package com.sxia.liteflow.cmp;

import com.yomahub.liteflow.core.NodeComponent;
import org.springframework.stereotype.Component;
@Component("c")
public class CCmp extends NodeComponent {

	@Override
	public void process() {
		System.out.println("执行了c");
	}
}
