package com.zhuang.workflow;

import java.util.List;
import java.util.Map;

import com.zhuang.workflow.commons.PageModel;
import com.zhuang.workflow.models.FlowInfoModel;
import com.zhuang.workflow.models.TaskInfoModel;

/**
 * 工作流查询管理器接口
 * 
 * @author zwb
 *
 */
public interface WorkflowQueryManager {

	/**
	 * 我的待办分页查询
	 * 
	 * @param userId
	 *            用户ID
	 * @param pageNo
	 *            第几页
	 * @param pageSize
	 *            一页多少记录
	 * @param conditions
	 *            查询筛选条件
	 * @return
	 */
	PageModel<FlowInfoModel> getMyTodoListPage(String userId, int pageNo, int pageSize, Map<String, Object> conditions);

	/**
	 * 我的已办分页查询
	 * 
	 * @param userId
	 *            用户ID
	 * @param pageNo
	 *            第几页
	 * @param pageSize
	 *            一页多少记录
	 * @param conditions
	 *            查询筛选条件
	 * @return
	 */
	PageModel<FlowInfoModel> getMyDoneListPage(String userId, int pageNo, int pageSize, Map<String, Object> conditions);

	/**
	 * 获取历史审批任务信息列表
	 * @param instanceId
	 * @return
	 */
	List<TaskInfoModel> getHistoryTaskInfoList(String instanceId);
	
}
