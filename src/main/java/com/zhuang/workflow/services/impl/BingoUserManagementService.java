package com.zhuang.workflow.services.impl;

import java.util.ArrayList;
import java.util.List;

import com.zhuang.workflow.models.UserInfoModel;
import com.zhuang.workflow.services.UserManagementService;

public class BingoUserManagementService implements UserManagementService{

	public UserInfoModel getUser(String userId) {
		
		UserInfoModel userInfoModel=new UserInfoModel();
		userInfoModel.setUserId(userId);
		userInfoModel.setUserName("庄伟斌");
		return userInfoModel;
		
	}
	
	public List<UserInfoModel> getUsersByRoleId(String roleId) {

		List<UserInfoModel> userInfoModels=new ArrayList<UserInfoModel>();

		UserInfoModel userInfoModel=new UserInfoModel();
		userInfoModel.setUserId("zwb2");
		userInfoModel.setUserName("庄伟斌2");
		userInfoModels.add(userInfoModel);
		
		return userInfoModels;
	}
	
	public List<UserInfoModel> getUsersByRoleName(String roleName) {

		List<UserInfoModel> userInfoModels=new ArrayList<UserInfoModel>();
		
		return userInfoModels;
	}

}
