package com.asm.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.asm.domain.User;
import com.asm.service.UserService;
import com.asm.util.MD5;
import com.asm.util.ResponseUtil;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;


/**  
* @description:用户action
* @Author: 张毓峰（作者）
* @Version: V1.00 （版本号）
* @Create Date: 2016-4-8 （创建日期）
*/
@SuppressWarnings("serial")
@Controller("userAction")
@Scope("prototype")
public class UserAction extends ActionSupport implements SessionAware,ModelDriven<User> {

	private User user=new User();
	@Autowired
	private UserService userService;
	
	private Map<String, Object> session;
	private List<User> userList;
	private int pageNow = 1;
	private int pageSize = 10;
	private JSONObject rows;
	private String storeId;
	private int state;
	private String uId;
	private String Id;
	private JSONObject data;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	@Override 
	public User getModel() {
		// TODO Auto-generated method stub
		return user;
	}
	


	

	
	
	public JSONObject getData() {
		return data;
	}
	public void setData(JSONObject data) {
		this.data = data;
	}
	

	public String getuId() {
		return uId;
	}







	public void setuId(String uId) {
		this.uId = uId;
	}







	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	
	





	public int getPageNow() {
		return pageNow;
	}

	public void setPageNow(int pageNow) {
		this.pageNow = pageNow;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public JSONObject getRows() {
		return rows;
	}

	public void setRows(JSONObject rows) {
		this.rows = rows;
	}
	public String loginPage() {
		String lastpage=(String) session.get("lastPage");
		User employee=(User) session.get("employee");
		if(employee!=null){
			return "home";
		}
		return "login";
	}
	public String login() {
		HttpServletRequest request = ServletActionContext.getRequest();
		if(user.getUserName()==null||user.getPassword()==null){
			return "login";
		}
		user=userService.checkUserExist(user.getUserName(), MD5.getMD5(user.getPassword().getBytes()));
		if(user==null){
			return "login";
		}
		session.put("user", user);
		return "home";
	}
	@Override
	public void setSession(Map<String, Object> session) {
		// TODO Auto-generated method stub
		this.session = session;
	}
	
	public String homePage(){
		return "userlistpage";
	}
	public String addPage(){
		return "addpage";
	}
	public String CheckUsername() throws Exception {
		boolean flag = false;
		if (userService.checkUserExistByName(user.getUserName())) {
			flag = true;
			ResponseUtil.write1(flag);
		} else {
			ResponseUtil.write1(flag);
		}
		return null;
	}
	public String register() throws Exception{
		System.out.println("sdaf"+user.getUserName());
		boolean flag = true;
		try {
			System.out.println("sdaf"+user.getUserName());
			userService.saveUser(user);
			ResponseUtil.write1(flag);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			flag = false;
			e.printStackTrace();
			ResponseUtil.write1(flag);
		}
		return null;
	}
	public String listUser() {
		System.out.println("begin");
		userList = userService.listUser();
		HashMap<String, Object> maps = new HashMap<String, Object>();
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		for (User  user: userList) {
			HashMap<String, Object> hashMap = new HashMap<String, Object>();
			hashMap.put("userId", user.getUserId());
			hashMap.put("roleId", user.getUserName());
			hashMap.put("deptId", user.getUserName());
			hashMap.put("userName", user.getUserName());
			hashMap.put("state", user.getState());
			hashMap.put("sex", user.getSex());
			list.add(hashMap);
		}
		maps.put("Rows", list);
		System.out.println(maps.size());
		rows = JSONObject.parseObject(JSON.toJSONString(maps));
		System.out.println(rows.toJSONString());
		return "userlist";
	}
	public String remove() throws Exception {
		System.out.println(uId);
		System.out.println(user.getUserId());
		boolean flag = false;
		if (userService.remove(user.getUserId())) {
			flag = true;
			ResponseUtil.write1(flag);
		} else {
			ResponseUtil.write1(flag);
		}
		return null;
	}
	public String listInfo(){
		System.out.println(uId);
		user=userService.findUser(user.getUserId());
		
		return "listuserinfo";
	}
	public String editUser() throws Exception {
		boolean flag = true;
		try {
			System.out.println(user.getUserId());
			userService.updateUser(user);
			ResponseUtil.write1(flag);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			flag = false;
			e.printStackTrace();
			ResponseUtil.write1(flag);
		}
		return null;
	}
	
	
}
