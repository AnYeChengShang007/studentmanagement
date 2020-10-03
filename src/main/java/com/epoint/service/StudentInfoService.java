package com.epoint.service;

import java.util.List;

import com.alibaba.druid.util.StringUtils;
import com.epoint.dao.StudentInfoDao;
import com.epoint.domain.StudentInfo;

/**
 * 
 * @author 冯金星
 *
 */
public class StudentInfoService {

	private StudentInfoDao dao = new StudentInfoDao();

	/**
	 * 查询学生信息
	 * 
	 * @param sortOrder
	 * @param sortField
	 * @param pageSize
	 * @param pageIndex
	 * @param stuname
	 * @param profession
	 * @return
	 */
	public List<StudentInfo> getAllStudentInfo(String pageIndex, String pageSize, String sortField, String sortOrder,
			String profession, String stuname) {
		Integer start = null;
		Integer size = null;
		if (!StringUtils.isEmpty(pageIndex) && !StringUtils.isEmpty(pageSize)) {
			start = Integer.valueOf(pageIndex) * Integer.valueOf(pageSize);
			size = Integer.valueOf(pageSize);
		}
		List<StudentInfo> list = dao.getAllStudentInfo(start, size, sortField, sortOrder, profession, stuname);
		return list;
	}

	/**
	 * @param stuname
	 * @param profession
	 * @return 分页总条数
	 */
	public Integer getTotal(String profession, String stuname) {
		return dao.getTotal(profession, stuname);
	}

	/**
	 * 删除学生信息
	 * 
	 * @param stuid
	 * @return
	 */
	public String del(String stuid) {
		String res = "删除失败";
		Integer num = dao.del(stuid);
		if (num > 0) {
			res = "删除成功";
		}
		return res;
	}

	/**
	 * @return
	 */
	public String getMaxId() {
		return dao.getMaxId();
	}

	/**
	 * @param studentInfo
	 * @return
	 */
	public String save(StudentInfo studentInfo) {
		String res = "添加失败";
		int num = 0;
		num = dao.save(studentInfo);
		if (num > 0) {
			res = "添加成功";
		}
		return res;
	}

	/**
	 * 根据stuid查询学生信息
	 * 
	 * @param stuId
	 * @return
	 */
	public StudentInfo getStudentInfo(String stuId) {
		StudentInfo studentInfo = dao.getStudentInfo(stuId);
		return studentInfo;
	}

	/**
	 * 更新学生信息
	 * 
	 * @param stuId
	 * @return
	 */
	public String updateStudentInfo(StudentInfo studentInfo) {
		String res = "修改失败";
		int num = dao.updateStudentInfo(studentInfo);
		if (num > 0) {
			res = "修改成功";
		}
		return res;
	}

	/**
	 * @param ids
	 * @return
	 */
	public String delBatch(String[] ids) {
		for(int i=0;i<ids.length;i++) {
			dao.del(ids[i]);
		}
		return "删除成功";
	}

}
