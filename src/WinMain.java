

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;

public class WinMain extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the dialog.
	 */
	public WinMain(String uName, String uId, String uPw, String uTel, String uBirth) {
		
		setTitle(uName + "님 안녕하세요.");
		setBounds(100, 100, 660, 557);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		{
			JMenuBar menuBar = new JMenuBar();
			setJMenuBar(menuBar);
			{
				JMenu mnUnit = new JMenu("\uB2E8\uC704\uBCC0\uD658\uAE30");
				menuBar.add(mnUnit);
			}
			
			JMenu mnLotto = new JMenu("Lotto");
			menuBar.add(mnLotto);
			{
				JMenu mnMember = new JMenu("회원관리");
				menuBar.add(mnMember);
				{
					JMenuItem mnModify = new JMenuItem("회원 정보 수정...");
					mnModify.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							WinModify modify = new WinModify(uId, uPw,uName,uTel,uBirth);
							modify.setModal(true);
							modify.setVisible(true);
						}
					});
					
					mnMember.add(mnModify);
				}
				{
					JMenuItem mnDelete = new JMenuItem("회원 탈퇴...");
					mnDelete.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							WinDelete delete = new WinDelete(uId);
							delete.setModal(true);
							delete.setVisible(true);
						}
					});
					mnMember.add(mnDelete);
				}
				{
					JMenuItem mnJoin = new JMenuItem("회원 가입...");
					mnJoin.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							
							WinMemberInsert winMemberInsert = new WinMemberInsert();
							winMemberInsert.setModal(true);
							winMemberInsert.setVisible(true);
							
						}
					});
					mnMember.add(mnJoin);
				}
				{
					JMenuItem mntmNewMenuItem = new JMenuItem("회원 로그인...");
					mntmNewMenuItem.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							Win_login login = new Win_login();
							setVisible(false);
							login.setVisible(true);
							
						}
					});
					mnMember.add(mntmNewMenuItem);
				}
			}
		}
	}
}
