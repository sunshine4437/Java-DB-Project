package Project;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

public class MemberPanel {
	private static JPanel member = null;
	private static Connection conn = DBConnect.getConnection();
	private static PreparedStatement pstmt;
	private static ResultSet rs;
	private static DefaultTableModel model;
	private static JTable table;
	private static JScrollPane scrollPane;
	private static JTextField textID;
	private static JTextField textPhone;
	private static JTextField textAddress;
	private static JTextField textName;
	private static RoundedButton btnDelete;
	private static RoundedButton btnUpdate;
	private static JPanel infoPanel;
	private static StringTokenizer st;
	private static int select = 0;
	private static JPanel infoPanel_2;
	private static JPanel btnPanel;
	private static JPanel addressPanel;
	private static JPanel btnPanel_1;
	private static JPanel btnPanel_2;
	private static JPanel btnPanel_3;
	private static JTextField textFieldSelectCnt;
//	private static JOptionPane dialog = new JOptionPane();

	public MemberPanel() {
		getPanel();
	}

	// 패널들 생성하고 메소드를 호출시 패널 반환
	public static JPanel getPanel() {
		if (member == null) {
			member = new JPanel();
			member.setBackground(new Color(255, 0, 0, 0));
			member.setBounds(0, 100, 1184, 661);
			member.setLayout(null);
			model = new DefaultTableModel(null, new String[] { "ID", "이름", "전화번호", "주소" }) {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};

			table = new JTable(model) {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
					Component component = super.prepareRenderer(renderer, row, column);
					int rendererWidth = component.getPreferredSize().width;
					TableColumn tableColumn = getColumnModel().getColumn(column);
					tableColumn.setPreferredWidth(
							Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));
					return component;
				}
			};
			table.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					select = table.getSelectedRow();
					int idx = 0;
					textID.setText((String) model.getValueAt(select, idx++));
					textName.setText((String) model.getValueAt(select, idx++));
					textPhone.setText((String) model.getValueAt(select, idx++));
					textAddress.setText((String) model.getValueAt(select, idx++));
					textFieldSelectCnt.setText(Integer.toString(table.getSelectedRowCount()));
				}
			});
			DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
			centerRenderer.setHorizontalAlignment(JLabel.CENTER);
			table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
			table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
			table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
			table.setRowSorter(new TableRowSorter<DefaultTableModel>(model));
			table.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
			table.getTableHeader().setReorderingAllowed(false);
			table.setRowHeight(30);
			table.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					select = table.getSelectedRow();
					int idx = 0;
					textID.setText((String) model.getValueAt(select, idx++));
					textName.setText((String) model.getValueAt(select, idx++));
					textPhone.setText((String) model.getValueAt(select, idx++));
					textAddress.setText((String) model.getValueAt(select, idx++));
				}

				public void mouseReleased(MouseEvent e) {
					textFieldSelectCnt.setText(Integer.toString(table.getSelectedRowCount()));
				}
			});
			table.getTableHeader().setFont(new Font("맑은 고딕", Font.PLAIN, 15));
			scrollPane = new JScrollPane(table);
			scrollPane.getViewport().setBackground(Color.WHITE);
			scrollPane.setBounds(12, 10, 1160, 516);
			member.add(scrollPane);
			infoPanel = new JPanel();
			infoPanel.setBackground(UIManager.getColor("Button.disabledShadow"));
			infoPanel.setBounds(12, 536, 991, 120);
			member.add(infoPanel);
			infoPanel.setLayout(null);
			JPanel infoPanel_1 = new JPanel();
			infoPanel_1.setBackground(UIManager.getColor("Button.disabledShadow"));
			infoPanel_1.setBounds(0, 0, 991, 40);
			infoPanel.add(infoPanel_1);
			infoPanel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			JLabel lblID = new JLabel("ID");
			lblID.setHorizontalAlignment(SwingConstants.CENTER);
			lblID.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
			infoPanel_1.add(lblID);
			textID = new JTextField();
			textID.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
			infoPanel_1.add(textID);
			textID.setColumns(10);
			JLabel ldlName = new JLabel("\uC774\uB984");
			infoPanel_1.add(ldlName);
			ldlName.setHorizontalAlignment(SwingConstants.CENTER);
			ldlName.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
			textName = new JTextField();
			textName.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
			infoPanel_1.add(textName);
			textName.setColumns(10);
			JLabel lblPhone = new JLabel("\uC804\uD654\uBC88\uD638");
			lblPhone.setHorizontalAlignment(SwingConstants.CENTER);
			lblPhone.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
			infoPanel_1.add(lblPhone);
			textPhone = new JTextField();
			textPhone.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
			infoPanel_1.add(textPhone);
			textPhone.setColumns(10);

			infoPanel_2 = new JPanel();
			infoPanel_2.setBackground(UIManager.getColor("Button.disabledShadow"));
			infoPanel_2.setBounds(0, 40, 991, 40);
			infoPanel.add(infoPanel_2);
			infoPanel_2.setLayout(null);

			addressPanel = new JPanel();
			addressPanel.setBackground(UIManager.getColor("Button.disabledShadow"));
			addressPanel.setBounds(112, 0, 767, 40);
			infoPanel_2.add(addressPanel);
			addressPanel.setLayout(null);
			JLabel lblAddress = new JLabel("\uC8FC\uC18C");
			lblAddress.setBounds(42, 9, 30, 21);
			addressPanel.add(lblAddress);
			lblAddress.setHorizontalAlignment(SwingConstants.CENTER);
			lblAddress.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
			textAddress = new JTextField();
			textAddress.setBounds(114, 6, 609, 27);
			addressPanel.add(textAddress);
			textAddress.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
			textAddress.setColumns(10);

			JPanel infoPanel_3 = new JPanel();
			infoPanel_3.setBackground(UIManager.getColor("Button.disabledShadow"));
			infoPanel_3.setBounds(0, 80, 991, 40);
			infoPanel.add(infoPanel_3);
			infoPanel_3.setLayout(null);

			JLabel selectCntLabel = new JLabel("\uD589\uC758 \uAC1C\uC218");
			selectCntLabel.setBounds(390, 9, 70, 21);
			selectCntLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
			infoPanel_3.add(selectCntLabel);

			textFieldSelectCnt = new JTextField();
			textFieldSelectCnt.setEditable(false);
			textFieldSelectCnt.setBounds(465, 6, 136, 27);
			textFieldSelectCnt.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
			infoPanel_3.add(textFieldSelectCnt);
			textFieldSelectCnt.setColumns(10);

			btnPanel = new JPanel();
			btnPanel.setBackground(UIManager.getColor("Button.disabledShadow"));
			btnPanel.setBounds(1003, 536, 169, 120);
			member.add(btnPanel);
			btnPanel.setLayout(null);

			btnPanel_1 = new JPanel();
			btnPanel_1.setBackground(UIManager.getColor("Button.disabledShadow"));
			btnPanel_1.setBounds(0, 0, 169, 40);
			btnPanel.add(btnPanel_1);
			RoundedButton btnSearch = new RoundedButton("\uAC80\uC0C9");
			btnPanel_1.add(btnSearch);
			btnSearch.setForeground(new Color(255, 255, 255));
			btnSearch.setBackground(new Color(135, 206, 250));
			btnSearch.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
			btnSearch.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					searchMember(textID.getText(), textName.getText(), textPhone.getText(), textAddress.getText());
					textFieldSelectCnt.setText(Integer.toString(model.getRowCount()));
				}
			});
			btnUpdate = new RoundedButton("\uAC31\uC2E0");
			btnPanel_1.add(btnUpdate);
			btnUpdate.setForeground(new Color(255, 255, 255));
			btnUpdate.setBackground(new Color(135, 206, 250));
			btnUpdate.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
			btnUpdate.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					updateMember();
				}
			});

			btnPanel_2 = new JPanel();
			btnPanel_2.setBackground(UIManager.getColor("Button.disabledShadow"));
			btnPanel_2.setBounds(0, 40, 169, 40);
			btnPanel.add(btnPanel_2);
			RoundedButton btnInsert = new RoundedButton("\uCD94\uAC00");
			btnPanel_2.add(btnInsert);
			btnInsert.setForeground(new Color(255, 255, 255));
			btnInsert.setBackground(new Color(135, 206, 250));
			btnInsert.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
			btnDelete = new RoundedButton("\uC0AD\uC81C");
			btnPanel_2.add(btnDelete);
			btnDelete.setForeground(new Color(255, 255, 255));
			btnDelete.setBackground(new Color(135, 206, 250));
			btnDelete.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
			btnDelete.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					deleteMember();
				}
			});
			btnInsert.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					insertMember();
				}
			});

			btnPanel_3 = new JPanel();
			btnPanel_3.setBackground(UIManager.getColor("Button.disabledShadow"));
			btnPanel_3.setBounds(0, 80, 169, 40);
			btnPanel.add(btnPanel_3);
			RoundedButton btnClear = new RoundedButton("\uCD08\uAE30\uD654");
			btnPanel_3.add(btnClear);
			btnClear.setForeground(new Color(255, 255, 255));
			btnClear.setBackground(new Color(135, 206, 250));
			btnClear.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
			btnClear.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setClear();
				}
			});
			getTable();
			BufferedImage image = null;
			try {
				image = ImageIO.read(new File("./source/1.jpg"));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			JLabel imageLabel = new JLabel(new ImageIcon(image));
			imageLabel.setBounds(0, -100, 1184, 761);
//			imageLabel.setPreferredSize(new Dimension(1184, 661));
			member.add(imageLabel);
		}
		return member;
	}

	// 테이블과 입력 부분을 초기화 기능
	private static void setClear() {
		textID.setText("");
		textName.setText("");
		textPhone.setText("");
		textAddress.setText("");
		model.setRowCount(0);
		getTable();
	}

	// 회원 정보를 입력하는 기능
	private static void insertMember() {
		String sql = "INSERT INTO membertbl values(?,?,?,?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, textID.getText());
			pstmt.setString(2, textPhone.getText());
			pstmt.setString(3, textAddress.getText());
			pstmt.setString(4, textName.getText());
			pstmt.executeUpdate();
			model.setRowCount(0);
			getTable();

			if (pstmt != null)
				pstmt.close();
			String error = "회원 정보를 추가했습니다.";
			JLabel lblError = new JLabel(error);
			lblError.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
			JOptionPane.showMessageDialog(null, lblError, "Successful", JOptionPane.PLAIN_MESSAGE);
		} catch (SQLException e1) {
			String error = "상품 정보를 추가하지 못했습니다.";
			JLabel lblError = new JLabel(error);
			lblError.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
			JOptionPane.showMessageDialog(null, lblError, "Error", JOptionPane.PLAIN_MESSAGE);
		}
	}

	// 회원 정보를 삭제하는 기능
	private static void deleteMember() {
		String sql = "DELETE FROM membertbl where id = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, textID.getText());
			pstmt.executeUpdate();
			model.setRowCount(0);
			getTable();
			if (pstmt != null)
				pstmt.close();
			String error = "회원 정보를 삭제했습니다.";
			JLabel lblError = new JLabel(error);
			lblError.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
			JOptionPane.showMessageDialog(null, lblError, "Successful", JOptionPane.PLAIN_MESSAGE);
		} catch (SQLException e1) {
			String error = "주문 내역이 있는 회원이라 삭제할 수 없습니다.";
			JLabel lblError = new JLabel(error);
			lblError.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
			JOptionPane.showMessageDialog(null, lblError, "Error", JOptionPane.PLAIN_MESSAGE);
		}
	}

	// 회원 정보를 갱신하는 기능
	private static void updateMember() {
		String sql = "UPDATE membertbl SET id = ?, name = ?, phonenum = ?, address = ? where id = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, textID.getText());
			pstmt.setString(2, textName.getText());
			pstmt.setString(3, textPhone.getText());
			pstmt.setString(4, textAddress.getText());
			pstmt.setString(5, (String) model.getValueAt(select, 0));
			pstmt.executeUpdate();
			model.setRowCount(0);
			getTable();

			if (pstmt != null)
				pstmt.close();
			String error = "회원 정보를 갱신했습니다.";
			JLabel lblError = new JLabel(error);
			lblError.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
			JOptionPane.showMessageDialog(null, lblError, "Successful", JOptionPane.PLAIN_MESSAGE);
		} catch (Exception e1) {
			String error = "회원 정보를 갱신 하지 못했습니다.";
			JLabel lblError = new JLabel(error);
			lblError.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
			JOptionPane.showMessageDialog(null, lblError, "Error", JOptionPane.PLAIN_MESSAGE);
		}
	}

	// DB에서 데이터를 불러와 테이블로 넣는 기능
	public static void getTable() {
		model.setRowCount(0);
		try {
			String sql = "SELECT * FROM membertbl";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				model.addRow(new Object[] { rs.getString("ID"), rs.getString("name"), rs.getString("phonenum"),
						rs.getString("address") });
			}
			textFieldSelectCnt.setText(Integer.toString(model.getRowCount()));
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 회원 정보를 검색하는 기능
	private static void searchMember(String id, String name, String phoneNum, String address) {
		String sql = "SELECT * FROM membertbl WHERE ";
//		String sql = "SELECT * FROM membertbl WHERE id = ? and name = ? and phonenum = ? and address = ?";
		if (!id.equals("")) {
			st = new StringTokenizer(id, ",");
			sql = sql + "(";
			while (st.hasMoreTokens()) {
				sql = sql + "id like ? or ";
				st.nextToken();
			}
			sql = sql.substring(0, sql.length() - 3);
			sql = sql + ")";
			sql = sql + " and ";
		}
		if (!name.equals("")) {
			st = new StringTokenizer(name, ",");
			sql = sql + "(";

			while (st.hasMoreTokens()) {
				sql = sql + "name like ? or ";
				st.nextToken();
			}
			sql = sql.substring(0, sql.length() - 3);
			sql = sql + ")";
			sql = sql + " and ";
		}
		if (!phoneNum.equals("")) {
			st = new StringTokenizer(phoneNum, ",");
			sql = sql + "(";
			while (st.hasMoreTokens()) {
				sql = sql + "phoneNum like ? or ";
				st.nextToken();
			}
			sql = sql.substring(0, sql.length() - 3);
			sql = sql + ")";
			sql = sql + " and ";
		}
		if (!address.equals("")) {
			st = new StringTokenizer(address, ",");
			sql = sql + "(";
			while (st.hasMoreTokens()) {
				sql = sql + "address like ? or ";
				st.nextToken();
			}
			sql = sql.substring(0, sql.length() - 3);
			sql = sql + ")";
			sql = sql + " and ";
		}

		sql = sql.substring(0, sql.length() - 4);

		try {
			int idx = 1;
			pstmt = conn.prepareStatement(sql);
			if (!id.equals("")) {
				st = new StringTokenizer(id, ",");
				while (st.hasMoreTokens()) {
					pstmt.setString(idx++, "%" + st.nextToken().trim() + "%");
				}
			}
			if (!name.equals("")) {
				st = new StringTokenizer(name, ",");
				while (st.hasMoreTokens()) {
					pstmt.setString(idx++, "%" + st.nextToken().trim() + "%");
				}
			}
			if (!phoneNum.equals("")) {
				st = new StringTokenizer(phoneNum, ",");
				while (st.hasMoreTokens()) {
					pstmt.setString(idx++, "%" + st.nextToken().trim() + "%");
				}
			}
			if (!address.equals("")) {
				st = new StringTokenizer(address, ",");
				while (st.hasMoreTokens()) {
					pstmt.setString(idx++, "%" + st.nextToken().trim() + "%");
				}
			}
			System.out.println(sql);
			rs = pstmt.executeQuery();
			model.setRowCount(0);
			while (rs.next()) {
				model.addRow(new Object[] { rs.getString("ID"), rs.getString("name"), rs.getString("phonenum"),
						rs.getString("address") });
			}
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}
