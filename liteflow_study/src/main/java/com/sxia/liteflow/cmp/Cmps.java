package com.sxia.liteflow.cmp;

import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.annotation.LiteflowMethod;
import com.yomahub.liteflow.core.NodeComponent;
import com.yomahub.liteflow.enums.LiteFlowMethodEnum;
import com.yomahub.liteflow.enums.NodeTypeEnum;

@LiteflowComponent
public class Cmps {

    //普通组件的定义
    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = "e")
    public void processA(NodeComponent bindCmp) {
        System.out.println("执行了e");
    }

    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = "f")
    public void processB(NodeComponent bindCmp) {
        System.out.println("执行了f");
    }

    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = "g")
    public void processC(NodeComponent bindCmp) {
        System.out.println("执行了g");
    }

    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = "h")
    public void processD(NodeComponent bindCmp) {
        System.out.println("执行了h");
    }

    //SWITCH组件的定义
    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS_SWITCH, nodeId = "switch", nodeType = NodeTypeEnum.SWITCH)
    public String processSwitch(NodeComponent bindCmp) {
        System.out.println("执行了switch");
        return "f";
    }
//
//		//IF组件的定义
//		@LiteflowMethod(value = LiteFlowMethodEnum.PROCESS_IF, nodeId = "c", nodeType = NodeTypeEnum.IF)
//		public boolean processC(NodeComponent bindCmp) {
//        ...
//		}
//
//		//FOR组件的定义
//		@LiteflowMethod(value = LiteFlowMethodEnum.PROCESS_FOR, nodeId = "d", nodeType = NodeTypeEnum.FOR)
//		public int processD(NodeComponent bindCmp) {
//        ...
//		}
//
//		//WHILE组件的定义
//		@LiteflowMethod(value = LiteFlowMethodEnum.PROCESS_WHILE, nodeId = "e", nodeType = NodeTypeEnum.WHILE)
//		public int processE(NodeComponent bindCmp) {
//        ...
//		}
//
//		//BREAK组件的定义
//		@LiteflowMethod(value = LiteFlowMethodEnum.PROCESS_BREAK, nodeId = "f", nodeType = NodeTypeEnum.BREAK)
//		public int processF(NodeComponent bindCmp) {
//        ...
//		}

}
