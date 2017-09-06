package cn.iamdp.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.Test;

public class ActivitTest {
	
	@Test
	public void createTable(){
		ProcessEngineConfiguration pe=ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
		pe.setJdbcUrl("jdbc:mysql://localhost:3306/activiti?createDatabaseIfNotExist=true&useUnicode=true&characterEncoding=utf-8");
		pe.setJdbcDriver("com.mysql.jdbc.Driver");
		pe.setJdbcUsername("root");
		pe.setJdbcPassword("520dp520");
		/**
		 * public static final String DB_SCHEMA_UPDATE_FALSE = "false";//没有表就报错
		 * public static final String DB_SCHEMA_UPDATE_CREATE_DROP = "create-drop";//先删除后创建
		 * public static final String DB_SCHEMA_UPDATE_TRUE = "true";//不存在就创建，有就直接使用
		 */
		pe.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
		ProcessEngine processEngine=pe.buildProcessEngine();
		System.out.println(processEngine);
	}
}
