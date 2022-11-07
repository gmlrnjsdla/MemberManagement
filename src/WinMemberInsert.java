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



import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPasswordField;

public class WinMemberInsert extends JDialog {

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
	public static void main(String[] args) {
		try {
			WinMemberInsert dialog = new WinMemberInsert();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public WinMemberInsert() {
		setTitle("회원가입");
		setBounds(100, 100, 364, 507);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(12, 26, 57, 15);
		contentPanel.add(lblNewLabel);
		
		tfId = new JTextField();
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
		
		tfName = new JTextField();
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
		
		tfTel = new JTextField();
		
		
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
		
		tfBirth = new JTextField();
		tfBirth.setColumns(10);
		tfBirth.setBounds(81, 215, 116, 21);
		contentPanel.add(tfBirth);
		
		JLabel lblNewLabel_5 = new JLabel("사진");
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_5.setBounds(12, 266, 57, 15);
		contentPanel.add(lblNewLabel_5);
		
		JLabel lblpic = new JLabel("");
		lblpic.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
					JFileChooser chooser = new JFileChooser();
					FileNameExtensionFilter filter = new FileNameExtensionFilter("이미지 파일", "jpg","png","gif");
					chooser.addChoosableFileFilter(filter);
					int ret = chooser.showOpenDialog(null);
					
					if(ret == JFileChooser.APPROVE_OPTION) {
						filePath = chooser.getSelectedFile().getPath();
						filePath = filePath.replaceAll("\\\\", "/");
						ImageIcon image = new ImageIcon(filePath);
						Image img = image.getImage();
						img = img.getScaledInstance(120, 150, Image.SCALE_SMOOTH);
						ImageIcon pic = new ImageIcon(img);
						lblpic.setIcon(pic);
						
					}
				}
				
			}
		});
		lblpic.setToolTipText("더블클릭 후 선택");
		lblpic.setBackground(new Color(255, 255, 255));
		lblpic.setOpaque(true);
		lblpic.setBounds(81, 269, 120, 150);
		
		contentPanel.add(lblpic);
		
		JButton btnNewButton = new JButton("중복확인");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ResultSet rs = null;
					PreparedStatement pstmt = null;
					String id = tfId.getText();
					
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection conn = DriverManager.getConnection
							("jdbc:mysql://localhost:3306/sqldb", "root","1234");
					
					String sql = "SELECT id FROM membertbl WHERE id=?";
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, id);
					rs = pstmt.executeQuery();
					
					if(rs.next()) {
						String dbid = rs.getString("id");
						if(dbid.equals(id));{
							JOptionPane.showMessageDialog(null, "중복된 아이디!");
							tfId.requestFocus();
							tfId.setSelectionStart(0);
							tfId.setSelectionEnd(tfId.getText().length()); //아이디 블럭치기
							}
						
					}else {
						JOptionPane.showMessageDialog(null, "사용가능한 아이디!");
						tfPw.requestFocus();
					}
					
					
					} catch (Exception e1) {
					e1.printStackTrace();
					}
				
			}
		});
		btnNewButton.setBounds(212, 23, 95, 22);
		contentPanel.add(btnNewButton);
		
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
		
		tfPw = new JPasswordField();
		tfPw.setBounds(81, 71, 116, 21);
		contentPanel.add(tfPw);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnJoin = new JButton("회원가입");
				btnJoin.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							Class.forName("com.mysql.cj.jdbc.Driver");
							Connection conn = DriverManager.getConnection
									("jdbc:mysql://localhost:3306/sqldb", "root","1234");
							
							String id = tfId.getText();
							String pw = tfPw.getText();
							String name = tfName.getText();
							String tel = tfTel.getText().replaceAll("-", "");
							String birth = tfBirth.getText();
							
							
							
							Statement stmt = conn.createStatement();
							String sql = "INSERT INTO membertbl(id,pw,name,tel,birth,pic)"
									+ " VALUES('"+id+"','"+pw+"','"+name+"','"+tel+"','"+birth+"','"+filePath+"')";
							
							
							if(stmt.executeUpdate(sql) > 0) {
								JOptionPane.showMessageDialog(null, "회원가입 성공!");
							}
							else {
								JOptionPane.showMessageDialog(null, "회원가입 실패!");
							}
							
							
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
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
