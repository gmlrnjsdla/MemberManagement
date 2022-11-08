import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;

public class WinDelete extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField tfPw;
	private JTextField tfId;

	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the dialog.
	 */
	public WinDelete(String uId) {
		setTitle("회원 탈퇴");
		setBounds(100, 100, 333, 170);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		tfPw = new JTextField();
		tfPw.setBounds(137, 64, 116, 21);
		contentPanel.add(tfPw);
		tfPw.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("비밀번호 확인 : ");
		lblNewLabel.setBounds(37, 67, 88, 15);
		contentPanel.add(lblNewLabel);
		{
			JLabel lblNewLabel_1 = new JLabel("아이디 확인 : ");
			lblNewLabel_1.setBounds(49, 25, 76, 15);
			contentPanel.add(lblNewLabel_1);
		}
		{
			tfId = new JTextField(uId);
			tfId.setEnabled(false);
			tfId.setColumns(10);
			tfId.setBounds(137, 22, 116, 21);
			contentPanel.add(tfId);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("삭제");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							String id = tfId.getText();
							String pw = tfPw.getText();
							
							Class.forName("com.mysql.cj.jdbc.Driver");
							Connection conn = DriverManager.getConnection
									("jdbc:mysql://localhost:3306/sqldb", "root","1234");
							System.out.println("DB Connected...");
							
							String sql = "SELECT pw FROM membertbl WHERE id=?";
							PreparedStatement pstmt = conn.prepareStatement(sql);
							pstmt.setString(1, uId);
							ResultSet rs = pstmt.executeQuery();
							
							if(rs.next()) {
								String dbpw = rs.getString("pw");
								if(pw.equals(dbpw)) {
									int result = JOptionPane.showConfirmDialog(null, "정말 삭제하시겠습니까?");
									if(result == JOptionPane.YES_OPTION) {
										sql = "DELETE FROM membertbl WHERE id=?";
										
										PreparedStatement pstmt1 = conn.prepareStatement(sql);
										pstmt1.setString(1, uId);
										
										pstmt1.executeUpdate();
										JOptionPane.showMessageDialog(null, "삭제되었습니다.");
										setVisible(false);
									}
									else {
										JOptionPane.showMessageDialog(null, "취소되었습니다.");
									}
								}
								else {
									JOptionPane.showMessageDialog(null, "비밀번호가 틀렸습니다.");
									}
							}
							else {
								JOptionPane.showMessageDialog(null, "아이디가 틀렸습니다.");
								}
							
						} catch (Exception e1) {
							e1.printStackTrace();
						} 
						
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
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
