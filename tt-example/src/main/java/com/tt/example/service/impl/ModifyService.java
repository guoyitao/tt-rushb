package com.tt.example.service.impl;


import com.tt.chxframe.annoation.Autowired;
import com.tt.chxframe.annoation.Service;
import com.tt.example.service.IModifyService;
import com.tt.example.service.IQueryService;

/**
 * 增删改业务
 *
 */
@Service
public class ModifyService implements IModifyService {

	@Autowired
	IQueryService queryService;

	/**
	 * 增加
	 */
	@Override
	public String add(String name, String addr) throws Exception {
		throw new Exception("这是Tom老师故意抛的异常！！");
		//return "modifyService add,name=" + name + ",addr=" + addr;
	}

	/**
	 * 修改
	 */
	@Override
	public String edit(Integer id, String name) {
		return "modifyService edit,id=" + id + ",name=" + name;
	}

	/**
	 * 删除
	 */
	@Override
	public String remove(Integer id) {
		return "modifyService id=" + id;
	}

}
