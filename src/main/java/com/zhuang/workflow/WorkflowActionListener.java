package com.zhuang.workflow;

import java.util.List;
import java.util.Map;

import com.zhuang.workflow.models.UserInfoModel;

/**
 * 工作流动作监听接口
 * @author zwb
 *
 */
public interface WorkflowActionListener {



	/**
	 * 提交前调用
	 * @param context
	 */
	void beforSubmit(WorkflowEngineContext context);
	
	/**
	 * 提交后调用
	 * @param context
	 */
	void afterSubmit(WorkflowEngineContext context);

	/**
	 * 退回前调用
	 * @param context
	 */
	void beforBack(WorkflowEngineContext context);
	
	/**
	 * 退回后调用
	 * @param context
	 */
	void afterBack(WorkflowEngineContext context);

	/**
	 * 驳回前调用
	 * @param context
	 */
	void beforReject(WorkflowEngineContext context);
	
	/**
	 * 驳回后调用
	 * @param context
	 */
	void afterReject(WorkflowEngineContext context);

	/**
	 * 刪除前调用
	 * @param context
	 */
	void beforDelete(WorkflowEngineContext context);
	
	/**
	 * 刪除后调用
	 * @param context
	 */
	void afterDelete(WorkflowEngineContext context);

	
	/**
	 * 保存操作
	 * @param context
	 */
	void onSave(WorkflowEngineContext context);
	
	/**
	 * 处理下一步处理人
	 * @param nextTaskUsers
	 */
	void onRetrieveNextTaskUsers(List<UserInfoModel> nextTaskUsers, WorkflowEngineContext context);
	
	void onRetrieveFormData(WorkflowEngineContext context);
}
