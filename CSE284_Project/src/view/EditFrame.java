/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;
import java.text.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import javax.swing.text.MaskFormatter;

import model.*;
public class EditFrame extends JFrame {

    private MainFrame mainFrame;
    private JComboBox<String> comboBoxCapHoc;
    private JTextField txtMaHS, txtTenHS, txtLop, txtDiemTB, txtTruong;
    private JFormattedTextField txtNgaySinh;
    private JRadioButton rbtnNam, rbtnNu;
    private JTextArea txtDiaChi;
    private JComboBox<String> comboHanhKiem;
    private JPanel panelCacTruong;
    private JButton btnSubmit;
    
    HocSinh hocSinh;
    QuanLyHocSinh quanLyHocSinh;
    
    public EditFrame(MainFrame mainFrame, String maHS) {
        this.mainFrame = mainFrame;
        quanLyHocSinh = new  QuanLyHocSinh();
        hocSinh = quanLyHocSinh.getHocSinhByMaHS(maHS);
        setTitle("Thong Tin Hoc Sinh");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        btnSubmit = new JButton("Xac Nhan");
        btnSubmit.setSize(20, 60);

        // Create panel with  GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); //Align the distance between components
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Label and ComboBox level
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Cap hoc"), gbc);
        gbc.gridx = 1;
        comboBoxCapHoc = new JComboBox<>(new String[]{"1", "2", "3"});
        panel.add(comboBoxCapHoc, gbc);

        // School student information fields
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Ma Hoc Sinh"), gbc);
        gbc.gridx = 1;
        txtMaHS = new JTextField(15);
        panel.add(txtMaHS, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Ten Hoc Sinh"), gbc);
        gbc.gridx = 1;
        txtTenHS = new JTextField(15);
        panel.add(txtTenHS, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Lop"), gbc);
        gbc.gridx = 1;
        txtLop = new JTextField(15);
        panel.add(txtLop, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Ngay Sinh"), gbc);
        gbc.gridx = 1;
        try {
            // Format yyyy-MM-dd
            MaskFormatter dateMask = new MaskFormatter("####-##-##");
            dateMask.setPlaceholderCharacter('_'); 
            txtNgaySinh = new JFormattedTextField(dateMask);
            txtNgaySinh.setColumns(15);
            panel.add(txtNgaySinh, gbc);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(new JLabel("Gioi Tinh"), gbc);
        gbc.gridx = 1;
        JPanel panelGioiTinh = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        rbtnNam = new JRadioButton("Nam");
        rbtnNu = new JRadioButton("Nu");
        ButtonGroup groupGioiTinh = new ButtonGroup();
        groupGioiTinh.add(rbtnNam);
        groupGioiTinh.add(rbtnNu);
        panelGioiTinh.add(rbtnNam);
        panelGioiTinh.add(rbtnNu);
        panel.add(panelGioiTinh, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(new JLabel("Dia Chi"), gbc);
        gbc.gridx = 1;
        txtDiaChi = new JTextArea(3, 15);
        JScrollPane scrollPaneDiaChi = new JScrollPane(txtDiaChi);
        panel.add(scrollPaneDiaChi, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        panel.add(new JLabel("Diem Trung Binh"), gbc);
        gbc.gridx = 1;
        txtDiemTB = new JTextField(15);
        panel.add(txtDiemTB, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        panel.add(new JLabel("Hanh Kiem"), gbc);
        gbc.gridx = 1;
        comboHanhKiem = new JComboBox<>(new String[]{"Tot", "Kha", "Trung Binh", "Yeu"});
        panel.add(comboHanhKiem, gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        panel.add(new JLabel("Truong"), gbc);
        gbc.gridx = 1;
        txtTruong = new JTextField(15);
        panel.add(txtTruong, gbc);

        // Panel contains special fields (for different school levels)
        panelCacTruong = new JPanel(new GridLayout(2, 1));
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        panel.add(panelCacTruong, gbc);

    // Event choose CapHoc
    comboBoxCapHoc.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedCapHoc = (String) comboBoxCapHoc.getSelectedItem();
            updateFieldsForCapHoc(selectedCapHoc);
        }
    });
    
    // Event Submit
    btnSubmit.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(comboBoxCapHoc.getSelectedIndex() == 0){
                    hocSinh.setMaHS(txtMaHS.getText());
                    hocSinh.setTenHS(txtTenHS.getText());
                    hocSinh.setLop(txtLop.getText());
                    hocSinh.setNgaySinh(LocalDate.parse(txtNgaySinh.getText()));
                    String gioiTinh;
                    if(rbtnNam.isSelected()){
                        gioiTinh = "Nam";
                    }else{
                        gioiTinh = "Nu";
                    }
                    hocSinh.setGioiTinh(gioiTinh);
                    hocSinh.setDiaChi(txtDiaChi.getText());
                    hocSinh.setDiemTB(Double.parseDouble(txtDiemTB.getText()));
                    hocSinh.setHanhKiem(comboHanhKiem.getSelectedItem().toString());
                    hocSinh.setTruong(txtTruong.getText());
                    JTextField txtHoatDongNgoaiKhoa = (JTextField) panelCacTruong.getComponent(1);
                    String hoatDongNgoaiKhoa = txtHoatDongNgoaiKhoa.getText();
                    ((HocSinhTieuHoc)hocSinh).setHoatDongNgoaiKhoa(hoatDongNgoaiKhoa);
                    mainFrame.addHocSinh((HocSinhTieuHoc)hocSinh);

                }else if (comboBoxCapHoc.getSelectedIndex() == 1){
                    hocSinh.setMaHS(txtMaHS.getText());
                    hocSinh.setTenHS(txtTenHS.getText());
                    hocSinh.setLop(txtLop.getText());
                    hocSinh.setNgaySinh(LocalDate.parse(txtNgaySinh.getText()));
                    String gioiTinh;
                    if(rbtnNam.isSelected()){
                        gioiTinh = "Nam";
                    }else{
                        gioiTinh = "Nu";
                    }
                    hocSinh.setGioiTinh(gioiTinh);
                    hocSinh.setDiaChi(txtDiaChi.getText());
                    hocSinh.setDiemTB(Double.parseDouble(txtDiemTB.getText()));
                    hocSinh.setHanhKiem(comboHanhKiem.getSelectedItem().toString());
                    hocSinh.setTruong(txtTruong.getText());
                    for(Component comp : panelCacTruong.getComponents()){
                        if(comp instanceof JTextField){
                            ((HocSinhTHCS)hocSinh).setHuongNghiep(((JTextField) comp).getText());
                        }
                        if(comp instanceof JRadioButton){
                            JRadioButton rbtn = (JRadioButton)comp;
                            ((HocSinhTHCS)hocSinh).setDoanVien(Boolean.parseBoolean(rbtn.getText()));
                        }
                    }
                        mainFrame.addHocSinh((HocSinhTHCS)hocSinh);
                        
                }else{
                    hocSinh.setMaHS(txtMaHS.getText());
                    hocSinh.setTenHS(txtTenHS.getText());
                    hocSinh.setLop(txtLop.getText());
                    hocSinh.setNgaySinh(LocalDate.parse(txtNgaySinh.getText()));
                    String gioiTinh;
                    if(rbtnNam.isSelected()){
                        gioiTinh = "Nam";
                    }else{
                        gioiTinh = "Nu";
                    }
                    hocSinh.setGioiTinh(gioiTinh);
                    hocSinh.setDiaChi(txtDiaChi.getText());
                    hocSinh.setDiemTB(Double.parseDouble(txtDiemTB.getText()));
                    hocSinh.setHanhKiem(comboHanhKiem.getSelectedItem().toString());
                    hocSinh.setTruong(txtTruong.getText());
                    for(Component comp : panelCacTruong.getComponents()){
                    	if(comp.getName().equals("txtToHop")) {
                    		((HocSinhTHPT)hocSinh).setToHop(((JTextField) comp).getText());
                    	}
                    	if(comp.getName().equals("txtDiemToHop")) {
                    		((HocSinhTHPT)hocSinh).setDiemToHop(Double.parseDouble(((JTextField) comp).getText()));
                    	}
                    	if(comp.getName().equals("txtNguyenVong")) {
                    		((HocSinhTHPT)hocSinh).setNguyenVong(((JTextField) comp).getText());
                    	}
                    	if(comp instanceof JRadioButton){
                            JRadioButton rbtn = (JRadioButton)comp;
                            ((HocSinhTHPT)hocSinh).setDangVien(Boolean.parseBoolean(rbtn.getText()));
                        }
                    }
                }
                new MainFrame().setVisible(true); 
            }    
    });
    
    gbc.gridx = 0;
    gbc.gridy = 11;
    gbc.gridwidth = 2;
    gbc.anchor = GridBagConstraints.CENTER; 
    panel.add(btnSubmit, gbc);

    // Handle Submit
    btnSubmit.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    });
    
    // Default level 1 - TieuHoc
    updateFieldsForCapHoc(String.valueOf(quanLyHocSinh.typeHocSinh(maHS)));
    add(panel);
    
    // Get information of HocSinh to Field Component
    comboBoxCapHoc.setSelectedItem(String.valueOf(quanLyHocSinh.typeHocSinh(maHS)));
    txtMaHS.setText(hocSinh.getMaHS());
    txtTenHS.setText(hocSinh.getTenHS());
    txtLop.setText(hocSinh.getLop());
    txtNgaySinh.setText(hocSinh.getNgaySinh().toString());
    if(hocSinh.getGioiTinh().equals("Nam")) {
    	rbtnNam.setSelected(true);
    }else {
    	rbtnNu.setSelected(true);
    }
    txtDiaChi.setText(hocSinh.getDiaChi());
    txtDiemTB.setText(String.valueOf(hocSinh.getDiemTB()));
    comboHanhKiem.setSelectedItem(hocSinh.getHanhKiem());
    txtTruong.setText(hocSinh.getTruong());
    int capHoc = quanLyHocSinh.typeHocSinh(maHS);
    switch(capHoc) {
    case 1:
    	for(Component comp : panelCacTruong.getComponents()) {
    		if(comp instanceof JTextField)
    		((JTextField)comp).setText(((HocSinhTieuHoc)hocSinh).getHoatDongNgoaiKhoa());
    	}
    	break;
    case 2:
    	boolean stateDoanVien = ((HocSinhTHCS)hocSinh).isDoanVien();
    	for(Component comp : panelCacTruong.getComponents()) {
    	    if(comp instanceof JTextField) {
    	        ((JTextField)comp).setText(((HocSinhTHCS)hocSinh).getHuongNghiep());
    	    }
    	    if(comp instanceof JPanel) {
    	    	for(Component cp : ((JPanel)comp).getComponents()) {
    	    		if(stateDoanVien && ((JRadioButton)cp).getName().equals("rbtnDoanVienTrue")) {
    	    			((JRadioButton)cp).setSelected(true);
    	    		}
    	    		if(!stateDoanVien && ((JRadioButton)cp).getName().equals("rbtnDoanVienFalse")) {
    	    			((JRadioButton)cp).setSelected(true);
    	    		}
    	    	}
    	    }
    	}
    	break;
    case 3:
    	boolean stateDangVien = ((HocSinhTHPT)hocSinh).isDangVien();
    	for(Component comp : panelCacTruong.getComponents()) {
    	    if(comp instanceof JTextField && ((JTextField)comp).getName().equals("txtToHop")) {
    	    	((JTextField)comp).setText(((HocSinhTHPT)hocSinh).getToHop());
    	    }
    	    if(comp instanceof JTextField && ((JTextField)comp).getName().equals("txtDiemToHop"))
    	    	((JTextField)comp).setText(String.valueOf(((HocSinhTHPT)hocSinh).getDiemToHop()));
    	    if(comp instanceof JTextField && ((JTextField)comp).getName().equals("txtNguyenVong")) {
    	    	((JTextField)comp).setText(((HocSinhTHPT)hocSinh).getNguyenVong());
    	    }
    	    if(comp instanceof JPanel) {
    	    	for(Component cp : ((JPanel)comp).getComponents()) {
    	    		if(stateDangVien && ((JRadioButton)cp).getName().equals("rbtnDangVienTrue")) {
    	    			((JRadioButton)cp).setSelected(true);
    	    		}
    	    		if(!stateDangVien && ((JRadioButton)cp).getName().equals("rbtnDangVienFalse")) {
    	    			((JRadioButton)cp).setSelected(true);
    	    		}
    	    	}
    	    } 
    	}
    	break;
    }
}

    // Update Special Field for HocSinh
    private void updateFieldsForCapHoc(String capHoc) {
        panelCacTruong.removeAll();
        
        if (capHoc.equals("1")) {
            panelCacTruong.add(new JLabel("Ngoai Khoa"));
            JTextField txtNgoaiKhoa = new JTextField();
            panelCacTruong.add(txtNgoaiKhoa);
            
        } else if (capHoc.equals("2")) {
            panelCacTruong.add(new JLabel("Huong Nghiep"));
            JTextField txtHuongNghiep = new JTextField();
            txtHuongNghiep.setName("txtHuongNghiep");
            panelCacTruong.add(txtHuongNghiep);
            
            JPanel panelDoanVien = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
            JRadioButton rbtnDoanVienTrue = new JRadioButton("true");
            JRadioButton rbtnDoanVienFalse = new JRadioButton("false");
            rbtnDoanVienTrue.setName("rbtnDoanVienTrue");
            rbtnDoanVienFalse.setName("rbtnDoanVienFalse");
            panelCacTruong.add(new JLabel("Doan Vien"));
            ButtonGroup groupDoanVien = new ButtonGroup(); // Create GroupButton Doan Vien
            groupDoanVien.add(rbtnDoanVienTrue);
            groupDoanVien.add(rbtnDoanVienFalse);
            panelDoanVien.add(rbtnDoanVienTrue);
            panelDoanVien.add(rbtnDoanVienFalse);
            panelCacTruong.add(panelDoanVien);
            
        } else if (capHoc.equals("3")) {
            panelCacTruong.add(new JLabel("Top Hop"));
            JTextField txtToHop = new JTextField();
            txtToHop.setName("txtToHop");
            panelCacTruong.add(txtToHop);
            
            panelCacTruong.add(new JLabel("Diem To Hop"));
            JTextField txtDiemToHop = new JTextField();
            txtDiemToHop.setName("txtDiemToHop");
            panelCacTruong.add(txtDiemToHop);
            
            JPanel panelDangVien = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
            JRadioButton rbtnDangVienTrue = new JRadioButton("true");
            JRadioButton rbtnDangVienFalse = new JRadioButton("false");
            ButtonGroup groupDangVien = new ButtonGroup(); // Create GroupButton Dang Vien

            // Add radio button to groupDangVien
            groupDangVien.add(rbtnDangVienTrue);
            groupDangVien.add(rbtnDangVienFalse);
            rbtnDangVienTrue.setName("rbtnDangVienTrue");
            rbtnDangVienFalse.setName("rbtnDangVienFalse");
            // Add components to GUI
            panelCacTruong.add(new JLabel("Dang Vien"));
            panelDangVien.add(rbtnDangVienTrue);
            panelDangVien.add(rbtnDangVienFalse);
            panelCacTruong.add(panelDangVien);
            panelCacTruong.add(new JLabel("Nguyen Vong"));
            JTextField txtNguyenVong = new JTextField();
            txtNguyenVong.setName("txtNguyenVong");
            panelCacTruong.add(txtNguyenVong);
        }
        
        panelCacTruong.revalidate();
        panelCacTruong.repaint();
    }
}


