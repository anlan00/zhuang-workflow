package com.zhuang.workflow.impl;

import com.zhuang.workflow.WorkflowBeansFactory;
import com.zhuang.workflow.common.PageModel;
import com.zhuang.workflow.model.FlowInfoModel;
import com.zhuang.workflow.model.ProcDefModel;
import com.zhuang.workflow.model.TaskInfoModel;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class ActivitiWorkflowQueryManagerTest {

    @Test
    public void getMyTodoListPage() {
        Map<String, Object> map =new HashMap<String, Object>();
        //map.put(ProcessMainVariableNames.PROC_TITLE, "%20170112");
        PageModel<FlowInfoModel> pageModel = WorkflowBeansFactory.getWorkflowQueryManager()
                .getMyTodoListPage("user1",1, 10, map );

        System.out.println(pageModel.getList().size());
        System.out.println(pageModel.getTotalRows());
        System.out.println(pageModel.getPageStartRow());
        System.out.println(pageModel.getHasPreviousPage());
        System.out.println(pageModel.getHasNextPage());

        for (FlowInfoModel flowInfoModel : pageModel.getList()) {
            System.out.println(flowInfoModel);
        }
    }

    @Test
    public void getMyDoneListPage() {
        System.out.println("testGetMyDoneListPage>>>>>>");

        Map<String, Object> map =new HashMap<String, Object>();
        //map.put(ProcessMainVariableNames.PROC_TITLE, "%20170112");
        PageModel<FlowInfoModel> pageModel = WorkflowBeansFactory.getWorkflowQueryManager()
                .getMyDoneListPage("zwb",1, 10, map );

        System.out.println(pageModel.getList().size());
        System.out.println(pageModel.getTotalRows());
        System.out.println(pageModel.getPageStartRow());
        System.out.println(pageModel.getHasPreviousPage());
        System.out.println(pageModel.getHasNextPage());

        for (FlowInfoModel flowInfoModel : pageModel.getList()) {
            System.out.println(flowInfoModel);
        }
        System.out.println("<<<<<<<testGetMyDoneListPage");
    }

    @Test
    public void getHistoryTaskInfoList() {
        List<TaskInfoModel> taskInfoModels = WorkflowBeansFactory.getWorkflowQueryManager()
                .getHistoryTaskInfoList("425037");
        for (TaskInfoModel taskInfoModel :taskInfoModels) {
            System.out.println(taskInfoModel.toString());
        }
        System.out.println("success!");
    }

    @Test
    public void getProcDefList() {
        List<ProcDefModel> procDefModels = WorkflowBeansFactory.getWorkflowQueryManager().getProcDefList();
        for (ProcDefModel procDefModel : procDefModels) {
            System.out.println(procDefModel);
        }
    }
}