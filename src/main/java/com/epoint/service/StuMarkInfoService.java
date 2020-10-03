package com.epoint.service;

import java.util.List;

import com.epoint.dao.StuMarkInfoDao;
import com.epoint.domain.StumarkInfo;

/**
 * @author 冯金星
 *
 */
public class StuMarkInfoService {

	StuMarkInfoDao dao = new StuMarkInfoDao();

	/**
	 * 分页查询成绩信息
	 * 
	 * @param to
	 * @param from
	 * @param namekey
	 * @return
	 */
	public List<StumarkInfo> getStuMarkList(int start, int pageSize, String stuId, String namekey, String from,
			String to) {

		return dao.getStuMarkList(start, pageSize, stuId, namekey, from, to);
	}

	/**
	 * 
	 * 根据markid删除成绩信息
	 * 
	 * @param stuid
	 * @return
	 */
	public String del(String markid) {
		String res = "删除失败";
		int num = dao.del(markid);
		if (num > 0) {
			res = "删除成功";
		}
		return res;
	}

	/**
	 * 获取分页总条数
	 * 
	 * @param size
	 * @param start
	 * @param namekey
	 * @param from
	 * @param to
	 * @return
	 */
	public int getTotal(String stuId,String namekey, String from, String to) {
		int res = 0;
		res = dao.getTotal(stuId,namekey, from, to);
		return res;
	}

	/**
	 * 保存学生成绩信息
	 * 
	 * @param stumarkInfo
	 * @return
	 */
	public String save(StumarkInfo stumarkInfo) {
		String res = "保存失败";
		int num = 0;
		num = dao.save(stumarkInfo);
		if (num > 0) {
			res = "保存成功";
		}
		return res;
	}

}
