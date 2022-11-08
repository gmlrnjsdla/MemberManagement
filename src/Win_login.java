

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class Win_login extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField tfId;
	private JTextField tfPw;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Win_login dialog = new Win_login();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Win_login() {
		
		setTitle("Login");
		setBounds(100, 100, 418, 192);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("ID");
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setBounds(43, 43, 57, 15);
			contentPanel.add(lblNewLabel);
		}
		{
			tfId = new JTextField();
			tfId.setBounds(112, 40, 152, 21);
			contentPanel.add(tfId);
			tfId.setColumns(20);
		}
		{
			JLabel lblPw = new JLabel("PW");
			lblPw.setHorizontalAlignment(SwingConstants.CENTER);
			lblPw.setBounds(43, 88, 57, 15);
			contentPanel.add(lblPw);
		}
		{
			tfPw = new JTextField();
			tfPw.setColumns(20);
			tfPw.setBounds(112, 85, 152, 21);
			contentPanel.add(tfPw);
		}
		{
			JButton btnLogin = new JButton("로그인");
			btnLogin.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					try {
						String id = tfId.getText();
						String pw = tfPw.getText();
						
						
						Class.forName("com.mysql.cj.jdbc.Driver");
						Connection conn = DriverManager.getConnection
								("jdbc:mysql://localhost:3306/sqldb", "root","1234");
						System.out.println("DB Connected...");
						
						String sql = "SELECT * FROM membertbl WHERE id=?";
						
						PreparedStatement pstmt = conn.prepareStatement(sql);
						pstmt.setString(1, id);
						
						ResultSet rs = pstmt.executeQuery();
						
						if(rs.next()) {
							String dbpw = rs.getString("pw");
							if(dbpw.equals(pw)) {
								String userId = rs.getString("id");
								String userPw = rs.getString("pw");
								String userName = rs.getString("name");
								String userTel = rs.getString("tel");
								String userBirth = rs.getString("birth");
								JOptionPane.showMessageDialog(null, "로그인 성공");
								WinMain winMain = new WinMain(userName,userId,userPw,userTel,userBirth);
								setVisible(false);
								winMain.setVisible(true);
							}else {
								JOptionPane.showMessageDialog(null, "비밀번호 틀림");
							}
						}else {
							JOptionPane.showMessageDialog(null, "비회원");
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					} 
				}
			});
			btnLogin.setBounds(276, 39, 78, 64);
			contentPanel.add(btnLogin);
		}
		
		JButton btnNewButton = new JButton("회원가입");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WinMemberInsert winMemberInsert = new WinMemberInsert();
				winMemberInsert.setModal(true);
				winMemberInsert.setVisible(true);
			}
		});
		btnNewButton.setBounds(257, 116, 97, 23);
		contentPanel.add(btnNewButton);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
		}
	}
}
