package com.zhuang.workflow.service;

import java.util.List;

import com.zhuang.workflow.model.UserInfoModel;

public interface UserManagementService {
	
	UserInfoModel getUser(String userId);
	
	List<UserInfoModel> getUsersByRoleId(String roleId);
	
	List<UserInfoModel> getUsersByRoleName(String roleName);
	
}
