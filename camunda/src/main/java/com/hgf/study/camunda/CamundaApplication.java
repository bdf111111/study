package com.hgf.study.camunda;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.camunda.bpm.spring.boot.starter.event.PostDeployEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.EventListener;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableProcessApplication
public class CamundaApplication {

    public static void main(String[] args) {
        SpringApplication.run(CamundaApplication.class, args);
    }

    @Autowired
    private RuntimeService runtimeService;

    @Resource
    private TaskService taskService;

    @EventListener
    private void processPostDeploy(PostDeployEvent event) {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("order_pay_one_deliveries");
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).list().get(0);
        taskService.complete(task.getId());
        Task task2 = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).list().get(0);
        Map<String, Object> jumpVar = new HashMap<>();
        jumpVar.put("agree", 0);
        taskService.complete(task2.getId(), jumpVar);
        System.out.println(processInstance);
    }

}
