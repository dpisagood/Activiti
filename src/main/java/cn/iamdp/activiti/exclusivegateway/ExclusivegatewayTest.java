package cn.iamdp.activiti.exclusivegateway;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.history.HistoricIdentityLink;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class ExclusivegatewayTest {
	ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
	@Test
	public void deploymentProcess(){
		InputStream bpmnStream=this.getClass().getResourceAsStream("exclusivegateway.bpmn");
		InputStream pngStream=this.getClass().getResourceAsStream("exclusivegateway.png");
		Deployment deployment=processEngine.getRepositoryService()//�õ����̶���Ͳ��������ص�service
						.createDeployment()//�����������
						.name("��������")
						.addInputStream("exclusivegateway.bpmn", bpmnStream)
						.addInputStream("exclusivegateway.png", pngStream)
						.deploy();//�������
		System.out.println(deployment.getId());
		System.out.println(deployment.getName());
		System.out.println(deployment.getKey());
	}
	
	/**
	 * ����ִ��
	 */
	@Test
	public void startProcessInstance(){
		String processKey="exclusivegateway";
		ProcessInstance processInstance=processEngine.getRuntimeService()//������ִ�е�����ʵ����ִ�ж�����ص�service
				//ͨ�����̶����key���������̣���Ӧhelloworld.bpmn�ļ��е�����key���ԣ�ͨ��key�����ǰ������µ����̽�������
					.startProcessInstanceByKey(processKey);
		System.out.println("����ʵ��Id��"+processInstance.getId());
		System.out.println("���̶���Id��"+processInstance.getProcessDefinitionId());
	}
	
	/**
	 * ��ѯ��ǰ�˵�����
	 */
	@Test
	public void findMyProcessTask(){
		List<Task> taskList=processEngine.getTaskService()//����ִ�е����������ص�service
					.createTaskQuery()//���������ѯ����
					.taskAssignee("����")
					.list();
		if(taskList!=null&taskList.size()!=0){
			for(Task t:taskList){
				System.out.println("+++++++++++++++++++++++++");
				System.out.println("������:"+t.getId());
				System.out.println("��������:"+t.getName());
				System.out.println("���񴴽�ʱ��:"+t.getCreateTime());
				System.out.println("���������:"+t.getAssignee() );
				System.out.println("����ʵ��id:"+t.getProcessInstanceId());
				System.out.println("ִ�ж���id:"+t.getExecutionId());
				System.out.println("���̶���id:"+t.getProcessDefinitionId());
				System.out.println("+++++++++++++++++++++++++");
			}
		}
	}
	
	/**
	 * �������
	 */
	@Test
	public void compelteMyTask(){
		
		//�����������ñ�����ͨ�����ñ�����������һ��ִ����һ��������name��value����Ҫ������ͼ��flow��conditionһ��
		//#{message=='����֮��'}
		//#{message=='��������'}
		Map<String,Object> variables=new HashMap<String,Object>();
		variables.put("message",2);
//		variables.put("message", "��������");
		processEngine.getTaskService().complete("37505", variables);//ͨ������id
		System.out.println("�������");
	}
	
	@Test
	public void findHistoryTask(){
		List<HistoricIdentityLink> his=processEngine.getHistoryService().getHistoricIdentityLinksForTask("7502");
		if(his!=null&&his.size()!=0){
			for(HistoricIdentityLink hk:his){
				System.out.println(hk.getProcessInstanceId());
				System.out.println(hk.getUserId());
			}
		}
	}
	
	@Test
	public void deleteProcess(){
		RepositoryService repositoryService=processEngine.getRepositoryService();
		repositoryService.deleteDeployment("1",true);
	}
}
