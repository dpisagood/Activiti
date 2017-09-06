package cn.iamdp.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.junit.Test;

public class HelloWorld {	
	ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
	@Test
	public void deploymentProcess(){
		Deployment deployment=processEngine.getRepositoryService()//�õ����̶���Ͳ��������ص�service
						.createDeployment()//�����������
						.name("hello����")
						.addClasspathResource("diagrams/helloworld.bpmn")//��classpath�м��أ�һ��ֻ�ܼ���һ��
						.addClasspathResource("diagrams/helloworld.png")
						.deploy();//�������
		System.out.println(deployment.getId());
		System.out.println(deployment.getCategory());
	}
}
