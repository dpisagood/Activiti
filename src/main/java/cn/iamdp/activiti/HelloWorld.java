package cn.iamdp.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.junit.Test;

public class HelloWorld {	
	ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
	@Test
	public void deploymentProcess(){
		Deployment deployment=processEngine.getRepositoryService()//得到流程定义和部署对象相关的service
						.createDeployment()//创建部署对象
						.name("hello部署")
						.addClasspathResource("diagrams/helloworld.bpmn")//从classpath中加载，一次只能加载一个
						.addClasspathResource("diagrams/helloworld.png")
						.deploy();//部署完成
		System.out.println(deployment.getId());
		System.out.println(deployment.getCategory());
	}
}
