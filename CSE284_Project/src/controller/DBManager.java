package controller;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.*;

public class DBManager {
	/** 
	 * Get all HocSinh in Database
	 * @return ArrayList<HocSinh> dsHocSinh
	 * */
	public ArrayList<HocSinh> getAllHocSinh(){
		ArrayList<HocSinh> dsHocSinh = new ArrayList<HocSinh>();
		Connection connection = JDBCConnection.getJDBCConnection();
		if (connection != null) {
		    try {
		        Statement statement = connection.createStatement();
		        String sql = "SELECT HocSinh.maHS, Ten, Lop, NgaySinh, GioiTinh, DiaChi, DiemTB, HanhKiem, Truong, HocSinhTieuHoc.HoatDongNgoaiKhoa\n"
		        		+ "FROM HocSinh JOIN HocSinhTieuHoc  on HocSinh.maHS = HocSinhTieuHoc.maHS";
		        ResultSet resultSet = statement.executeQuery(sql);
		        while(resultSet.next()) {
		        	HocSinhTieuHoc hs = new HocSinhTieuHoc();
		        	setInforHocSinh(hs, resultSet);
		        	hs.setHoatDongNgoaiKhoa(resultSet.getString("HoatDongNgoaiKhoa"));
		        	dsHocSinh.add(hs);
		        }
		        
		        sql = "SELECT HocSinh.maHS, Ten, Lop, NgaySinh, GioiTinh, DiaChi, DiemTB, HanhKiem, Truong, HuongNghiep, DoanVien\n"
		        		+ "FROM HocSinh  JOIN HocSinhTHCS on HocSinh.maHS = HocSinhTHCS.maHS";
		        resultSet = statement.executeQuery(sql);
		        while(resultSet.next()) {
		        	HocSinhTHCS hs = new HocSinhTHCS();
		        	setInforHocSinh(hs, resultSet);
		        	hs.setHuongNghiep(resultSet.getString("HuongNghiep"));
		        	hs.setDoanVien(resultSet.getBoolean("DoanVien"));
		        	dsHocSinh.add(hs);
		        }
		        
		        sql = "SELECT  HocSinh.maHS, Ten, Lop, NgaySinh, GioiTinh, DiaChi, DiemTB, HanhKiem, Truong, ToHop, DiemToHop, DangVien, NguyenVong\n"
		        		+ "FROM HocSinh JOIN HocSinhTHPT  ON HocSinh.maHS = HocSinhTHPT.maHS";
		        resultSet = statement.executeQuery(sql);
		        while(resultSet.next()) {
		        	HocSinhTHPT hs = new HocSinhTHPT();
		        	setInforHocSinh(hs, resultSet);
		        	hs.setDiemToHop(resultSet.getDouble("DiemToHop"));
		        	hs.setDangVien(resultSet.getBoolean("DangVien"));
		        	hs.setNguyenVong(resultSet.getString("NguyenVong"));
		        	dsHocSinh.add(hs);
		        }
		        resultSet.close();
		        statement.close();
		        connection.close();
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
	}
		return dsHocSinh;
}
	/**
	 * Delete HocSinh by maHS in database
	 * @param String: maHS
	 * */
	public void deleteHocSinh(String maHS) {
	    Connection connection = JDBCConnection.getJDBCConnection();
	    if (connection != null) {
	        try {
	            Statement statement = connection.createStatement();
	            String sql = "DELETE FROM HocSinh WHERE maHS = '" + maHS + "'";
	            int rowsAffected = statement.executeUpdate(sql);
	            if (rowsAffected > 0) {
	                System.out.println("Xóa thành công học sinh có mã: " + maHS);
	            } else {
	                System.out.println("Không tìm thấy học sinh có mã: " + maHS);
	            }
	            statement.close();
	            connection.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}
	/**
	 * Add a HocSinh to database
	 * @param HocSinh hocSinh
	 * */
	public void addHocSinh(HocSinh hocSinh) {
		String capHoc = "";
	    if(hocSinh instanceof HocSinhTieuHoc) capHoc = "1";
	    if(hocSinh instanceof HocSinhTHCS) capHoc = "2";
	    if(hocSinh instanceof HocSinhTHPT) capHoc = "3";
	    Connection connection = JDBCConnection.getJDBCConnection();
	    if (connection != null) {
	        try {
	            Statement statement = connection.createStatement();
	            
	            // Xây dựng câu lệnh SQL cho bảng HocSinh
	            String sql = "INSERT INTO HocSinh (maHS, Ten, Lop, NgaySinh, GioiTinh, DiaChi, DiemTB, HanhKiem, Truong) VALUES ('"
	                         + hocSinh.getMaHS() + "', '"
	                         + hocSinh.getTenHS() + "', '"
	                         + hocSinh.getLop() + "', '"
	                         + hocSinh.getNgaySinh() + "', '"
	                         + hocSinh.getGioiTinh() + "', '"
	                         + hocSinh.getDiaChi() + "', "
	                         + hocSinh.getDiemTB() + ", '"
	                         + hocSinh.getHanhKiem() + "', '"
	                         + hocSinh.getTruong() + "')";
	            
	            // Thực thi câu lệnh SQL cho bảng HocSinh
	            statement.executeUpdate(sql);

	            // Thêm thông tin vào bảng tương ứng dựa trên cấp học (capHoc)
	            if (capHoc.equals("1")) {
	                sql = "INSERT INTO HocSinhTieuHoc (maHS, HoatDongNgoaiKhoa) VALUES ('"
	                      + hocSinh.getMaHS() + "', '"
	                      + ((HocSinhTieuHoc) hocSinh).getHoatDongNgoaiKhoa() + "')";
	            } else if (capHoc.equals("2")) {
	                sql = "INSERT INTO HocSinhTHCS (maHS, HuongNghiep, DoanVien) VALUES ('"
	                      + hocSinh.getMaHS() + "', '"
	                      + ((HocSinhTHCS) hocSinh).getHuongNghiep() + "', "
	                      + ((HocSinhTHCS) hocSinh).isDoanVien() + ")";
	            } else if (capHoc.equals("3")) {
	                sql = "INSERT INTO HocSinhTHPT (maHS, ToHop, DiemToHop, DangVien, NguyenVong) VALUES ('"
	                      + hocSinh.getMaHS() + "', '"
	                      + ((HocSinhTHPT) hocSinh).getToHop() + "', "
	                      + ((HocSinhTHPT) hocSinh).getDiemToHop() + ", "
	                      + ((HocSinhTHPT) hocSinh).isDangVien() + ", '"
	                      + ((HocSinhTHPT) hocSinh).getNguyenVong() + "')";
	            }

	            // Thực thi câu lệnh SQL cho bảng cấp học tương ứng
	            statement.executeUpdate(sql);
	            System.out.println("Add HocSinh successfully!");

	            statement.close();
	            connection.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}
	/**
	 * Update information HocSinh in database
	 * @param HocSinh: hocSinh
	 * */
	public void updateHocSinh(HocSinh hocSinh) {
		deleteHocSinh(hocSinh.getMaHS());
		addHocSinh(hocSinh);
	};
	public void searchHocSinh() {
		
	}
	/**
	 * 
	 * */
	private void setInforHocSinh(HocSinh hs, ResultSet resultSet) {
		try {
		hs.setMaHS(resultSet.getString("maHS"));
    	hs.setTenHS(resultSet.getString("Ten"));
    	hs.setLop(resultSet.getString("Lop"));
    	hs.setNgaySinh(resultSet.getDate("NgaySinh").toLocalDate());
    	hs.setGioiTinh(resultSet.getString("GioiTinh"));
    	hs.setDiaChi(resultSet.getString("DiaChi"));
    	hs.setDiemTB(resultSet.getDouble("DiemTB"));
    	hs.setHanhKiem(resultSet.getString("HanhKiem"));
    	hs.setTruong(resultSet.getString("Truong"));
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
	}
}
	
