package com.epoint.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.druid.util.StringUtils;
import com.epoint.domain.StudentInfo;
import com.epoint.utils.JDBCUtil;

/**
 * 
 * @author 冯金星
 *
 */
public class StudentInfoDao {

	/**
	 * 	查询学生信息
	 * 
	 * @param sortOrder
	 * @param sortField
	 * @param pageSize
	 * @param stuname
	 * @param profession
	 * @param pageIndex
	 * @return
	 */
	public List<StudentInfo> getAllStudentInfo(Integer start, Integer pageSize, String sortField, String sortOrder,
			String profession, String stuname) {

		Connection connection = null;
		PreparedStatement prepareStatement = null;
		List<StudentInfo> res = new ArrayList<StudentInfo>();
		ResultSet rs = null;

		try {
			int num = 1;
			connection = JDBCUtil.getConnection();
			String sql = "select * from studentinfo where 1=1 ";
			if (!StringUtils.isEmpty(stuname)) {
				sql += " and stuname like concat(\'%\',?,\'%\') ";
			}
			if (!StringUtils.isEmpty(profession)) {
				sql += " and profession=? ";
			}
			if (!StringUtils.isEmpty(sortField) && !StringUtils.isEmpty(sortOrder)) {
				sql += " order by " + sortField + "  " + sortOrder;
			}
			if (start != null && pageSize != null) {
				sql += " limit ?,? ";
			}
			prepareStatement = connection.prepareStatement(sql);
			if (!StringUtils.isEmpty(stuname)) {
				prepareStatement.setObject(num++, stuname);
			}
			if (!StringUtils.isEmpty(profession)) {
				prepareStatement.setObject(num++, profession);
			}
			if (start != null && pageSize != null) {
				prepareStatement.setObject(num++, start);
				prepareStatement.setObject(num++, pageSize);
			}
			rs = prepareStatement.executeQuery();

			while (rs.next()) {
				StudentInfo studentInfo = new StudentInfo();
				studentInfo.setStuid((String) rs.getObject("stuid"));
				studentInfo.setIdnum((String) rs.getObject("idnum"));
				studentInfo.setAdddate((Date) rs.getObject("adddate"));
				studentInfo.setNotes((String) rs.getObject("notes"));
				studentInfo.setProfession((String) rs.getObject("profession"));
				studentInfo.setSex((Integer) rs.getObject("sex"));
				studentInfo.setStuname((String) rs.getObject("stuname"));
				studentInfo.setTotalscore((Integer) rs.getObject("totalscore"));
				res.add(studentInfo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(connection, prepareStatement, rs);
		}

		return res;
	}

	/**
	 * @param stuname
	 * @param profession
	 * @return 分页总条数
	 */
	public Integer getTotal(String profession, String stuname) {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		Integer res = null;
		ResultSet rs = null;

		try {
			int num = 1;
			connection = JDBCUtil.getConnection();
			String sql = "select count(1) total from studentinfo where 1=1 ";
			if (!StringUtils.isEmpty(stuname)) {
				sql += " and stuname like concat(\'%\',?,\'%\') ";
			}
			if (!StringUtils.isEmpty(profession)) {
				sql += " and profession=? ";
			}
			prepareStatement = connection.prepareStatement(sql);
			if (!StringUtils.isEmpty(stuname)) {
				prepareStatement.setObject(num++, stuname);
			}
			if (!StringUtils.isEmpty(profession)) {
				prepareStatement.setObject(num++, profession);
			}
			rs = prepareStatement.executeQuery();
			while (rs.next()) {
				res = rs.getInt("total");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(connection, prepareStatement, rs);
		}
		return res;
	}

	/**
	 * 	删除学生信息
	 * 
	 * @param stuid
	 * @return
	 */
	public Integer del(String stuid) {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		Integer res = 0;

		try {
			connection = JDBCUtil.getConnection();
			connection.setAutoCommit(false);
			String sql = "delete from studentinfo where stuid=?";
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setObject(1, stuid);
			res = prepareStatement.executeUpdate();
			prepareStatement.close();

			sql = "delete from stumarkinfo where stuid = ?";
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setObject(1, stuid);
			res = prepareStatement.executeUpdate();
			connection.commit();
		} catch (Exception e) {
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
					connection.setAutoCommit(true);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			JDBCUtil.close(connection, prepareStatement, null);
		}
		return res;
	}

	/**
	 * 获得最大的id
	 * 
	 * @return
	 */
	public String getMaxId() {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		String res = null;
		ResultSet rs = null;

		try {
			connection = JDBCUtil.getConnection();
			String sql = "select LPAD(max(RIGHT(stuid,3))+1,3,0) id from studentinfo";
			prepareStatement = connection.prepareStatement(sql);
			rs = prepareStatement.executeQuery();
			while (rs.next()) {
				res = rs.getString("id");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(connection, prepareStatement, null);
		}
		return res;
	}

	/**
	 * 保存学生信息
	 * 
	 * @param studentInfo
	 * @return
	 */
	public int save(StudentInfo studentInfo) {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		int res = 0;

		try {
			connection = JDBCUtil.getConnection();
			String sql = "insert into studentinfo(stuid,adddate,sex,idnum,totalscore,notes,stuname,profession) values (?,?,?,?,?,?,?,?)";
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setObject(1, studentInfo.getStuid());
			prepareStatement.setObject(2, studentInfo.getAdddate());
			prepareStatement.setObject(3, studentInfo.getSex());
			prepareStatement.setObject(4, studentInfo.getIdnum());
			prepareStatement.setObject(5, studentInfo.getTotalscore());
			prepareStatement.setObject(6, studentInfo.getNotes());
			prepareStatement.setObject(7, studentInfo.getStuname());
			prepareStatement.setObject(8, studentInfo.getProfession());
			res = prepareStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(connection, prepareStatement, null);
		}

		return res;
	}

	/**
	 * 根据stuid获得学生信息
	 * 
	 * @param stuId
	 * @return
	 */
	public StudentInfo getStudentInfo(String stuId) {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		StudentInfo res = null;
		ResultSet rs = null;

		try {
			connection = JDBCUtil.getConnection();
			String sql = "select * from studentinfo where stuid=?";
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setObject(1, stuId);
			rs = prepareStatement.executeQuery();
			while (rs.next()) {
				res = new StudentInfo();
				res.setStuid((String) rs.getObject("stuid"));
				res.setIdnum((String) rs.getObject("idnum"));
				res.setAdddate((Date) rs.getObject("adddate"));
				res.setNotes((String) rs.getObject("notes"));
				res.setProfession((String) rs.getObject("profession"));
				res.setSex((Integer) rs.getObject("sex"));
				res.setStuname((String) rs.getObject("stuname"));
				res.setTotalscore((Integer) rs.getObject("totalscore"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(connection, prepareStatement, rs);
		}
		return res;
	}

	/**
	 * 更新学生信息
	 * 
	 * @param studentInfo
	 * @return
	 */
	public int updateStudentInfo(StudentInfo studentInfo) {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		int res = 0;

		try {
			connection = JDBCUtil.getConnection();
			String sql = "update studentInfo set profession=?,notes=? where stuid=?";
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setObject(1, studentInfo.getProfession());
			prepareStatement.setObject(2, studentInfo.getNotes());
			prepareStatement.setObject(3, studentInfo.getStuid());
			res = prepareStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(connection, prepareStatement, null);
		}

		return res;
	}

}
