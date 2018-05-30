package com.zhuang.workflow.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.zhuang.workflow.model.UserInfoModel;
import com.zhuang.workflow.service.UserManagementService;

public class DefaultUserManagementService implements UserManagementService{

	public UserInfoModel getUser(String userId) {
		UserInfoModel userInfoModel=new UserInfoModel();
		userInfoModel.setUserId(userId);
		
		if(userId==null)
		{
			userInfoModel.setUserName("");
			
		} else if(userId.equals("zwb"))
		{
			userInfoModel.setUserName("庄伟斌");
			
		}
		else if(userId.equals("admin"))
		{
			userInfoModel.setUserName("管理员");			
		}
		else if(userId.equals("zs"))
		{
			userInfoModel.setUserName("张三");			
		}else if(userId.equals("ls"))
		{
			userInfoModel.setUserName("李四");			
		}else if(userId.equals("wb"))
		{
			userInfoModel.setUserName("王五");			
		}else if(userId.equals("zl"))
		{
			userInfoModel.setUserName("赵六");			
		}
	
		return userInfoModel;
		
	}
	
	public List<UserInfoModel> getUsersByRoleId(String roleId) {

		List<UserInfoModel> userInfoModels=new ArrayList<UserInfoModel>();


		if(roleId.equals("sys"))
		{
			UserInfoModel userInfoModel=new UserInfoModel();
			userInfoModel.setUserId("admin");
			userInfoModel.setUserName("管理员");
			userInfoModels.add(userInfoModel);

			userInfoModel=new UserInfoModel();
			userInfoModel.setUserId("zwb");
			userInfoModel.setUserName("庄伟斌");
			userInfoModels.add(userInfoModel);
			
		}else if(roleId.equals("mgr"))
		{
			UserInfoModel userInfoModel=new UserInfoModel();
			userInfoModel.setUserId("zs");
			userInfoModel.setUserName("张三");
			userInfoModels.add(userInfoModel);
			
		}else if(roleId.equals("def"))
		{
			UserInfoModel userInfoModel=new UserInfoModel();
			userInfoModel.setUserId("ls");
			userInfoModel.setUserName("李四");
			userInfoModels.add(userInfoModel);
			
			userInfoModel=new UserInfoModel();
			userInfoModel.setUserId("wb");
			userInfoModel.setUserName("王五");
			userInfoModels.add(userInfoModel);
			
			userInfoModel=new UserInfoModel();
			userInfoModel.setUserId("zl");
			userInfoModel.setUserName("赵六");
			userInfoModels.add(userInfoModel);
		}
		return userInfoModels;
	}
	
	public List<UserInfoModel> getUsersByRoleName(String roleName) {

		List<UserInfoModel> userInfoModels=new ArrayList<UserInfoModel>();
		
		return userInfoModels;
	}

}
