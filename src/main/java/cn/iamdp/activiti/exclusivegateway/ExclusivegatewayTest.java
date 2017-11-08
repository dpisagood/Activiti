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
		Deployment deployment=processEngine.getRepositoryService()//得到流程定义和部署对象相关的service
						.createDeployment()//创建部署对象
						.name("排他网关")
						.addInputStream("exclusivegateway.bpmn", bpmnStream)
						.addInputStream("exclusivegateway.png", pngStream)
						.deploy();//部署完成
		System.out.println(deployment.getId());
		System.out.println(deployment.getName());
		System.out.println(deployment.getKey());
	}
	
	/**
	 * 流程执行
	 */
	@Test
	public void startProcessInstance(){
		String processKey="exclusivegateway";
		ProcessInstance processInstance=processEngine.getRuntimeService()//与正在执行的流程实例和执行对象相关的service
				//通过流程定义的key来启动流程，对应helloworld.bpmn文件中的流程key属性，通过key启动是按照最新的流程进行启动
					.startProcessInstanceByKey(processKey);
		System.out.println("流程实例Id："+processInstance.getId());
		System.out.println("流程定义Id："+processInstance.getProcessDefinitionId());
	}
	
	/**
	 * 查询当前人的任务
	 */
	@Test
	public void findMyProcessTask(){
		List<Task> taskList=processEngine.getTaskService()//正在执行的任务管理相关的service
					.createTaskQuery()//创建任务查询对象
					.taskAssignee("丁鹏")
					.list();
		if(taskList!=null&taskList.size()!=0){
			for(Task t:taskList){
				System.out.println("+++++++++++++++++++++++++");
				System.out.println("任务编号:"+t.getId());
				System.out.println("任务名称:"+t.getName());
				System.out.println("任务创建时间:"+t.getCreateTime());
				System.out.println("任务办理人:"+t.getAssignee() );
				System.out.println("流程实例id:"+t.getProcessInstanceId());
				System.out.println("执行对象id:"+t.getExecutionId());
				System.out.println("流程定义id:"+t.getProcessDefinitionId());
				System.out.println("+++++++++++++++++++++++++");
			}
		}
	}
	
	/**
	 * 完成任务
	 */
	@Test
	public void compelteMyTask(){
		
		//完成任务后设置变量，通过设置变量来决定下一步执行哪一步。变量name和value定义要和流程图中flow中condition一样
		//#{message=='三天之内'}
		//#{message=='大于三天'}
		Map<String,Object> variables=new HashMap<String,Object>();
		variables.put("message",2);
//		variables.put("message", "大于三天");
		processEngine.getTaskService().complete("37505", variables);//通过任务id
		System.out.println("完成任务");
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
