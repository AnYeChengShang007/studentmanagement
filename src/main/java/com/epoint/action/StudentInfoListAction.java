package com.epoint.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.epoint.domain.StudentInfo;
import com.epoint.service.StudentInfoService;

/**
 * 
 * @author Administrator
 *
 */
@WebServlet("/studentinfolistaction")
public class StudentInfoListAction extends HttpServlet {

	private static final String UPDATE = "update";
	private static final String STUDENTINFO = "studentinfo";
	private static final String METHOD = "method";
	private static final String SAVE = "save";
	private static final String DELETE = "delete";
	private static final String GETMAXID = "getmaxid";
	private static final String DELETEBATCH = "deletebatch";
	

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		StudentInfoService service = new StudentInfoService();

		//批量删除学生信息
		if (DELETEBATCH.equals(request.getParameter(METHOD))) {
			String res = "删除失败";
			String data = request.getParameter("idstr");
			if(!StringUtils.isEmpty(data)) {
				String[] ids = data.split(",");
				res = service.delBatch(ids);
			}
			response.getWriter().write(res);
			return;
		}
		// 修改学生详情信息
		if (UPDATE.equals(request.getParameter(METHOD))) {
			String data = request.getParameter("data");
			StudentInfo studentInfo = JSON.parseObject(data, StudentInfo.class);
			String res = service.updateStudentInfo(studentInfo);
			response.getWriter().write(res);
			return;
		}

		// 查询学生详情信息
		if (STUDENTINFO.equals(request.getParameter(METHOD))) {
			String stuId = request.getParameter("stuid");
			StudentInfo studentInfo = service.getStudentInfo(stuId);
			String res = JSON.toJSONStringWithDateFormat(studentInfo, "yyyy-MM-dd");
			response.getWriter().write(res);
			return;
		}

		// 保存学生信息
		if (SAVE.equals(request.getParameter(METHOD))) {
			String data = request.getParameter("data");
			StudentInfo studentInfo = JSON.parseObject(data, StudentInfo.class);
			String res = service.save(studentInfo);
			response.getWriter().write(res);
			return;
		}

		// 获得最大id
		if (GETMAXID.equals(request.getParameter(METHOD))) {
			String res = service.getMaxId();
			if(res == null) {
				res = "001";
			}
			response.getWriter().write(res);
			return;
		}

		// 删除学生信息
		if (DELETE.equals(request.getParameter(METHOD))) {
			String stuid = request.getParameter("stuid");
			String res = service.del(stuid);
			response.getWriter().write(res);
			return;
		}

		// 查询学生信息
		String pageIndex = request.getParameter("pageIndex");
		String pageSize = request.getParameter("pageSize");
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		String profession = request.getParameter("profession");
		String stuname = request.getParameter("stuname");
		List<StudentInfo> list = service.getAllStudentInfo(pageIndex, pageSize, sortField, sortOrder, profession,
				stuname);
		Integer total = service.getTotal(profession, stuname);
		Map<String, Object> map = new HashMap<String, Object>(10);
		map.put("data", list);
		map.put("total", total);
		String res = JSON.toJSONStringWithDateFormat(map, "yyyy-MM-dd");
		response.getWriter().write(res);

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}