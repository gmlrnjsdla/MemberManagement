import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.mysql.cj.protocol.Resultset;
import com.mysql.cj.x.protobuf.MysqlxPrepare.Prepare;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPasswordField;

public class WinModify extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField tfId;
	private JTextField tfName;
	private JTextField tfTel;
	private JTextField tfBirth;
	private String filePath = null;
	private JPasswordField tfPw;
	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the dialog.
	 */
	public WinModify(String uId, String uPw, String uName, String uTel, String uBirth) {
		setTitle("회원정보 수정");
		setBounds(100, 100, 364, 365);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(12, 26, 57, 15);
		contentPanel.add(lblNewLabel);
		
		tfId = new JTextField(uId);
		tfId.setEnabled(false);
		tfId.setBounds(81, 23, 116, 21);
		contentPanel.add(tfId);
		tfId.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("PW");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setBounds(12, 74, 57, 15);
		contentPanel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("이름");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2.setBounds(12, 122, 57, 15);
		contentPanel.add(lblNewLabel_2);
		
		tfName = new JTextField(uName);
		tfName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					tfTel.requestFocus();
				}
			}
		});
		tfName.setColumns(10);
		tfName.setBounds(81, 119, 116, 21);
		contentPanel.add(tfName);
		
		JLabel lblNewLabel_3 = new JLabel("전화번호");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_3.setBounds(12, 170, 57, 15);
		contentPanel.add(lblNewLabel_3);
		
		tfTel = new JTextField(uTel);
		
		
		tfTel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					tfBirth.requestFocus();
				}
			}
		});
		tfTel.setColumns(10);
		tfTel.setBounds(81, 167, 116, 21);
		contentPanel.add(tfTel);
		
		JLabel lblNewLabel_4 = new JLabel("생년월일");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_4.setBounds(12, 218, 57, 15);
		contentPanel.add(lblNewLabel_4);
		
		tfBirth = new JTextField(uBirth);
		tfBirth.setColumns(10);
		tfBirth.setBounds(81, 215, 116, 21);
		contentPanel.add(tfBirth);
		
		JButton btnCalendar = new JButton("Calendar..");
		btnCalendar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WinCalendar calendar = new WinCalendar();
				calendar.setModal(true);
				calendar.setVisible(true);
			}
		});
		btnCalendar.setBounds(212, 215, 95, 22);
		contentPanel.add(btnCalendar);
		
		tfPw = new JPasswordField(uPw);
		tfPw.setBounds(81, 71, 116, 21);
		contentPanel.add(tfPw);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnJoin = new JButton("수정 완료");
				btnJoin.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							Class.forName("com.mysql.cj.jdbc.Driver");
							Connection conn = DriverManager.getConnection
									("jdbc:mysql://localhost:3306/sqldb", "root","1234");
							
							String pw = tfPw.getText();
							String name = tfName.getText();
							String tel = tfTel.getText().replaceAll("-", "");
							String birth = tfBirth.getText();
							
							String sql = "UPDATE membertbl SET pw=?, name=?, tel=?, birth=? WHERE id=?"; 
							
							PreparedStatement pstmt = conn.prepareStatement(sql);
							pstmt.setString(1, pw);
							pstmt.setString(2, name);
							pstmt.setString(3, tel);
							pstmt.setString(4, birth);
							pstmt.setString(5, uId);
							
							pstmt.executeUpdate();
							JOptionPane.showMessageDialog(null, "회원정보수정 성공");
							setVisible(false);
							
							
							} catch (Exception e1) {
							e1.printStackTrace();
							}
					}
				});
				btnJoin.setActionCommand("OK");
				buttonPane.add(btnJoin);
				getRootPane().setDefaultButton(btnJoin);
			}
			{
				JButton cancelButton = new JButton("취소");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JOptionPane.showMessageDialog(null, "취소되었습니다.");
						setVisible(false);
						
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
