package com.epoint.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.epoint.domain.StumarkInfo;
import com.epoint.service.StuMarkInfoService;

/**
 * 
 * @author 冯金星
 *
 */
@WebServlet("/studentscorelist")
public class StudentScoreListAction extends HttpServlet {

	private static final String METHOD = "method";
	private static final String SAVE = "save";
	private static final String DELETE = "delete";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		StuMarkInfoService service = new StuMarkInfoService();
		// 删除学生分数信息
		if (DELETE.equals(request.getParameter(METHOD))) {
			String markId = request.getParameter("markid");
			if (!StringUtils.isEmpty(markId)) {
				String res = service.del(markId);
				response.getWriter().write(res);
			}
			return;
		}

		// 保存学生分数信息
		if (SAVE.equals(request.getParameter(METHOD))) {
			String data = request.getParameter("data");
			if (!StringUtils.isEmpty(data)) {
				StumarkInfo stumarkInfo = JSON.parseObject(data, StumarkInfo.class);
				stumarkInfo.setMarkid(UUID.randomUUID().toString());
				String res = service.save(stumarkInfo);
				response.getWriter().write(res);
			}
			return;
		}

		// 分页获取学生分数列表
		String from = request.getParameter("from");
		String to = request.getParameter("to");
		String namekey = request.getParameter("namekey");
		String pageIndex = request.getParameter("pageIndex");
		String pageSize = request.getParameter("pageSize");
		String stuId = request.getParameter("stuid");
		int start = Integer.valueOf(pageIndex) * Integer.valueOf(pageSize);
		int size = Integer.valueOf(pageSize);
		List<StumarkInfo> list = service.getStuMarkList(start, size, stuId, namekey, from, to);
		int total = service.getTotal(stuId,namekey, from, to);
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