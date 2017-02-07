package com.zhuang.workflow.services;

import java.util.List;

import com.zhuang.workflow.models.UserInfoModel;

public interface UserManagementService {
	
	UserInfoModel getUser(String userId);
	
	List<UserInfoModel> getUsersByRoleId(String roleId);
	
	List<UserInfoModel> getUsersByRoleName(String roleName);
	
}
