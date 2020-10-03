package com.epoint.dao;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.druid.util.StringUtils;
import com.epoint.domain.StumarkInfo;
import com.epoint.utils.JDBCUtil;

/**
 * @author 冯金星
 *
 */
public class StuMarkInfoDao {

	/**
	 * 分页查询学生成绩信息
	 * 
	 * @param pageSize
	 * @param start
	 * @param to
	 * @param from
	 * @param namekey
	 * @return
	 */
	public List<StumarkInfo> getStuMarkList(int start, int pageSize, String stuId, String namekey, String from,
			String to) {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		List<StumarkInfo> res = new ArrayList<StumarkInfo>();
		ResultSet rs = null;

		try {
			int num = 1;
			connection = JDBCUtil.getConnection();
			String sql = "select stumarkinfo.*,studentinfo.stuname from stumarkinfo,studentinfo where stumarkinfo.stuid=studentinfo.stuid";
			if (!StringUtils.isEmpty(stuId)) {
				sql += " and studentinfo.stuid = ? ";
			}
			if (!StringUtils.isEmpty(namekey)) {
				sql += " and studentinfo.stuname like concat(\'%\',?,\'%\') ";
			}
			if (!StringUtils.isEmpty(from)) {
				sql += " and stumarkinfo.adddate>=? ";
			}
			if (!StringUtils.isEmpty(to)) {
				sql += " and stumarkinfo.adddate<=? ";
			}
			sql += " limit ?,?";
			prepareStatement = connection.prepareStatement(sql);
			if (!StringUtils.isEmpty(stuId)) {
				prepareStatement.setObject(num++, stuId);
			}
			if (!StringUtils.isEmpty(namekey)) {
				prepareStatement.setObject(num++, namekey);
			}
			if (!StringUtils.isEmpty(from)) {
				prepareStatement.setObject(num++, from);
			}
			if (!StringUtils.isEmpty(to)) {
				prepareStatement.setObject(num++, to);
			}
			prepareStatement.setObject(num++, start);
			prepareStatement.setObject(num++, pageSize);
			rs = prepareStatement.executeQuery();
			while (rs.next()) {
				StumarkInfo stumarkInfo = new StumarkInfo();
				stumarkInfo.setAdddate((Date) rs.getObject("adddate"));
				stumarkInfo.setBasescore((BigDecimal) rs.getObject("basescore"));
				stumarkInfo.setCoursename((String) rs.getObject("coursename"));
				stumarkInfo.setFinalscore((BigDecimal) rs.getObject("finalscore"));
				stumarkInfo.setMarkid((String) rs.getObject("markid"));
				stumarkInfo.setRemarks((String) rs.getObject("remarks"));
				stumarkInfo.setStuid((String) rs.getObject("stuid"));
				stumarkInfo.setTestscore((BigDecimal) rs.getObject("testscore"));
				stumarkInfo.setStuname((String) rs.getObject("stuname"));
				res.add(stumarkInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(connection, prepareStatement, null);
		}
		return res;
	}

	/**
	 * 
	 * @param stuid
	 * @return 根据markid删除分数
	 */
	public int del(String markid) {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		int res = 0;
		ResultSet rs = null;

		try {
			connection = JDBCUtil.getConnection();
			connection.setAutoCommit(false);
			String sql = "select finalscore,stuid from stumarkinfo where markid = ?";
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setObject(1, markid);
			rs = prepareStatement.executeQuery();
			BigDecimal finalscore = null;
			String stuid = null;
			while (rs.next()) {
				finalscore = (BigDecimal) rs.getObject("finalscore");
				stuid = (String) rs.getObject("stuid");
			}
			JDBCUtil.close(null, prepareStatement, rs);

			sql = "delete from stumarkinfo where markid = ?";
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setObject(1, markid);
			res = prepareStatement.executeUpdate();
			prepareStatement.close();

			sql = "update studentinfo set totalscore = totalscore-? where stuid = ?";
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setObject(1, finalscore.divide(new BigDecimal("10"), RoundingMode.HALF_UP));
			prepareStatement.setObject(2, stuid);
			res = prepareStatement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				if (connection != null) {
					connection.rollback();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				if (connection != null) {
					connection.setAutoCommit(true);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			JDBCUtil.close(connection, prepareStatement, rs);
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
	public int getTotal(String stuId, String namekey, String from, String to) {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		int res = 0;
		ResultSet rs = null;

		try {
			int num = 1;
			connection = JDBCUtil.getConnection();
			String sql = "select count(1) from stumarkinfo,studentinfo where stumarkinfo.stuid=studentinfo.stuid";
			if (!StringUtils.isEmpty(stuId)) {
				sql += " and studentinfo.stuid = ? ";
			}
			if (!StringUtils.isEmpty(namekey)) {
				sql += " and studentinfo.stuname like concat(\'%\',?,\'%\') ";
			}
			if (!StringUtils.isEmpty(from)) {
				sql += " and stumarkinfo.adddate>=? ";
			}
			if (!StringUtils.isEmpty(to)) {
				sql += " and stumarkinfo.adddate<=? ";
			}
			prepareStatement = connection.prepareStatement(sql);
			if (!StringUtils.isEmpty(stuId)) {
				prepareStatement.setObject(num++, stuId);
			}
			if (!StringUtils.isEmpty(namekey)) {
				prepareStatement.setObject(num++, namekey);
			}
			if (!StringUtils.isEmpty(from)) {
				prepareStatement.setObject(num++, from);
			}
			if (!StringUtils.isEmpty(to)) {
				prepareStatement.setObject(num++, to);
			}
			rs = prepareStatement.executeQuery();
			while (rs.next()) {
				res = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(connection, prepareStatement, rs);
		}

		return res;
	}

	/**
	 * 保存成绩信息
	 * 
	 * @param stumarkInfo
	 * @return
	 */
	public int save(StumarkInfo stumarkInfo) {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		int res = 0;
		ResultSet rs = null;

		try {
			connection = JDBCUtil.getConnection();

			connection.setAutoCommit(false);

			String sql = "insert into stumarkinfo(markid,stuid,coursename,"
					+ "basescore,testscore,finalscore,adddate,remarks) values(?,?,?,?,?,?,?,?)";
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setObject(1, stumarkInfo.getMarkid());
			prepareStatement.setObject(2, stumarkInfo.getStuid());
			prepareStatement.setObject(3, stumarkInfo.getCoursename());
			prepareStatement.setObject(4, stumarkInfo.getBasescore());
			prepareStatement.setObject(5, stumarkInfo.getTestscore());
			prepareStatement.setObject(6, stumarkInfo.getFinalscore());
			prepareStatement.setObject(7, stumarkInfo.getAdddate());
			prepareStatement.setObject(8, stumarkInfo.getRemarks());
			res = prepareStatement.executeUpdate();
			prepareStatement.close();
			BigDecimal addScore = stumarkInfo.getFinalscore().divide(new BigDecimal("10"), RoundingMode.HALF_UP);
			sql = "update studentinfo set totalscore = totalscore+? where stuid = ?";
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setObject(1, addScore);
			prepareStatement.setObject(2, stumarkInfo.getStuid());
			res = prepareStatement.executeUpdate();
			connection.commit();
		} catch (Exception e) {// 如果只捕捉SQLException,那么将不会走catch语句
			e.printStackTrace();
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		} finally {
			if (connection != null) {
				try {
					if (connection != null) {
						connection.setAutoCommit(true);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			JDBCUtil.close(connection, prepareStatement, rs);
		}

		return res;
	}

}
