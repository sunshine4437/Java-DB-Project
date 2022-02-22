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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class OrderPanel {
	private static JPanel order = null;
	private static Connection con = DBConnect.getConnection();
	private static PreparedStatement pstmt = null;
	private static ResultSet rs = null;
	private static DefaultTableModel model;
	private static JTable table;
	private static JTextField textOrderNo;
	private static JTextField textID;
	private static JTextField textOrderAmount;
	private static JTextField textOrderDate;
	private static JTextField textProductNo;
	private static JTextField textTotalPrice;
	private static JTextField textSum;
	private static JTextField textFieldSelectCnt;
//	private static JOptionPane dialog = new JOptionPane();
	private static List<Integer> selectRows = new ArrayList<>();
	private static DecimalFormat formatter = new DecimalFormat("###,###,###,###");
	private static StringTokenizer st;

	public OrderPanel() {
		getPanel();
	}

	// 패널들 생성하고 메소드를 호출시 패널 반환
	public static JPanel getPanel() {
		if (order == null) {
			order = new JPanel();
			order.setBackground(new Color(255, 0, 0, 0));
			order.setBounds(0, 100, 1184, 661);
			model = new DefaultTableModel(null, new String[] { "주문 번호", "ID", "판매 날짜", "상품 번호", "수량", "판매 가격" }) {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				public Class<?> getColumnClass(int columnIndex) {
					if (columnIndex == 0 || columnIndex == 3 || columnIndex == 4 || columnIndex == 5)
						return Integer.class;
					else
						return String.class;
				}

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

			table.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
			table.getTableHeader().setReorderingAllowed(false);
			table.setRowHeight(30);
			table.getTableHeader().setFont(new Font("맑은 고딕", Font.PLAIN, 15));
			DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
			centerRenderer.setHorizontalAlignment(JLabel.CENTER);
			for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
				if (i == 5)
					table.getColumnModel().getColumn(i).setCellRenderer(new RightNumberRenderer());
				else
					table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
			}

			// 여러개 선택해서 총 판매액 출력 용도
			table.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
//					int temp = table.getSelectedRow();
					selectRows.clear();
					selectRows.addAll(0,
							Arrays.asList(Arrays.stream(table.getSelectedRows()).boxed().toArray(Integer[]::new)));
					int sum = 0;
					for (Integer i : selectRows) {
						sum += ((int) model.getValueAt(i, 5));
					}
					String sumResult = formatter.format(sum);
					textSum.setText(sumResult);
					textFieldSelectCnt.setText(Integer.toString(table.getSelectedRowCount()));
				}

				public void keyPressed(KeyEvent e) {
					int temp = table.getSelectedRow();
					int idx = 0;
					textOrderNo.setText(Integer.toString((Integer) model.getValueAt(temp, idx++)));
					textID.setText((String) (model.getValueAt(temp, idx++)));
					textOrderDate.setText((String) model.getValueAt(temp, idx++));
					textProductNo.setText(Integer.toString((Integer) model.getValueAt(temp, idx++)));
					textOrderAmount.setText(Integer.toString((Integer) model.getValueAt(temp, idx++)));
					textTotalPrice.setText(Integer.toString((Integer) model.getValueAt(temp, idx++)));
				}
			});

			// 여러개 선택해서 총 판매액 출력 용도
			table.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					selectRows.clear();
					selectRows.addAll(0,
							Arrays.asList(Arrays.stream(table.getSelectedRows()).boxed().toArray(Integer[]::new)));
					int sum = 0;
					for (Integer i : selectRows) {
						sum += ((int) model.getValueAt(i, 5));
					}
					String sumResult = formatter.format(sum);
					textSum.setText(sumResult);
					textFieldSelectCnt.setText(Integer.toString(table.getSelectedRowCount()));
				}

				// 클릭할 때 text에 반환
				@Override
				public void mousePressed(MouseEvent e) {
					int temp = table.getSelectedRow();
					int idx = 0;
					textOrderNo.setText(Integer.toString((Integer) model.getValueAt(temp, idx++)));
					textID.setText((String) (model.getValueAt(temp, idx++)));
					textOrderDate.setText((String) model.getValueAt(temp, idx++));
					textProductNo.setText(Integer.toString((Integer) model.getValueAt(temp, idx++)));
					textOrderAmount.setText(Integer.toString((Integer) model.getValueAt(temp, idx++)));
					textTotalPrice.setText(Integer.toString((Integer) model.getValueAt(temp, idx++)));
				}
			});
			JScrollPane scrollPane = new JScrollPane(table);
			scrollPane.setBounds(12, 10, 1160, 516);
			scrollPane.getViewport().setBackground(Color.WHITE);
			table.setAutoCreateRowSorter(true);
			TableRowSorter<TableModel> tablesorter = new TableRowSorter<TableModel>(table.getModel());
			order.setLayout(null);
			table.setRowSorter(tablesorter);
			scrollPane.setViewportView(table);
			order.add(scrollPane);
			JPanel insertPanel = new JPanel();
			insertPanel.setBackground(UIManager.getColor("Button.disabledShadow"));
			insertPanel.setBounds(12, 536, 991, 120);
			order.add(insertPanel);
			insertPanel.setLayout(null);
			JPanel infoPanel = new JPanel();
			infoPanel.setBackground(UIManager.getColor("Button.disabledShadow"));
			infoPanel.setBounds(0, 0, 992, 120);
			insertPanel.add(infoPanel);
			infoPanel.setLayout(null);
			JPanel infoPanel_1 = new JPanel();
			infoPanel_1.setBackground(UIManager.getColor("Button.disabledShadow"));
			infoPanel_1.setBounds(0, 0, 992, 40);
			infoPanel.add(infoPanel_1);
			infoPanel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			JLabel lblOrderNo = new JLabel("\uC8FC\uBB38 \uBC88\uD638");
			infoPanel_1.add(lblOrderNo);
			lblOrderNo.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
			lblOrderNo.setBackground(Color.BLUE);
			textOrderNo = new JTextField();
			infoPanel_1.add(textOrderNo);
			textOrderNo.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
			textOrderNo.setColumns(10);
			JLabel lblID = new JLabel("ID");
			infoPanel_1.add(lblID);
			lblID.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
			textID = new JTextField();
			infoPanel_1.add(textID);
			textID.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
			textID.setColumns(10);
			JLabel lblOrderDate = new JLabel("\uD310\uB9E4 \uB0A0\uC9DC");
			infoPanel_1.add(lblOrderDate);
			lblOrderDate.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
			textOrderDate = new JTextField();
			infoPanel_1.add(textOrderDate);
			textOrderDate.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
			textOrderDate.setColumns(10);

			JPanel infoPanel_2 = new JPanel();
			infoPanel_2.setBackground(UIManager.getColor("Button.disabledShadow"));
			infoPanel_2.setBounds(0, 40, 992, 40);
			infoPanel.add(infoPanel_2);
			infoPanel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			JLabel lblProductNo = new JLabel("\uC0C1\uD488 \uBC88\uD638");
			infoPanel_2.add(lblProductNo);
			lblProductNo.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
			textProductNo = new JTextField();
			infoPanel_2.add(textProductNo);
			textProductNo.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
			textProductNo.setColumns(10);

			JComboBox<?> comboBoxProductNo = new JComboBox<Object>(new String[] { "단일", "포함", "이상", "이하" });
			comboBoxProductNo.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
			comboBoxProductNo.setBackground(new Color(255, 255, 255));
			infoPanel_2.add(comboBoxProductNo);

			JLabel lblOrderAmount = new JLabel("\uC218\uB7C9");
			infoPanel_2.add(lblOrderAmount);
			lblOrderAmount.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
			textOrderAmount = new JTextField();
			infoPanel_2.add(textOrderAmount);
			textOrderAmount.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
			textOrderAmount.setColumns(10);
			JComboBox<?> comboBoxAmount = new JComboBox<Object>(new String[] { "단일", "포함", "이상", "이하" });
			comboBoxAmount.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
			comboBoxAmount.setBackground(new Color(255, 255, 255));
			infoPanel_2.add(comboBoxAmount);
			JLabel lblTotalPrice = new JLabel("\uD310\uB9E4 \uAE08\uC561");
			infoPanel_2.add(lblTotalPrice);
			lblTotalPrice.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
			lblTotalPrice.setBackground(Color.WHITE);
			textTotalPrice = new JTextField();
			infoPanel_2.add(textTotalPrice);
			textTotalPrice.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
			textTotalPrice.setColumns(10);
			JComboBox<?> comboBoxTotal = new JComboBox<Object>(new String[] { "단일", "포함", "이상", "이하" });
			comboBoxTotal.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
			comboBoxTotal.setBackground(new Color(255, 255, 255));
			infoPanel_2.add(comboBoxTotal);

			JPanel infoPanel_3 = new JPanel();
			infoPanel_3.setBackground(UIManager.getColor("Button.disabledShadow"));
			infoPanel_3.setBounds(0, 78, 992, 42);
			infoPanel.add(infoPanel_3);

			JLabel lblNewLabel = new JLabel("\uD589\uC758 \uAC1C\uC218");
			lblNewLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
			infoPanel_3.add(lblNewLabel);

			textFieldSelectCnt = new JTextField();
			textFieldSelectCnt.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
			textFieldSelectCnt.setEditable(false);
			infoPanel_3.add(textFieldSelectCnt);
			textFieldSelectCnt.setColumns(10);
			JLabel lblSum = new JLabel("\uCD1D \uD310\uB9E4\uC561");
			infoPanel_3.add(lblSum);
			lblSum.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
			lblSum.setBackground(Color.BLUE);
			textSum = new JTextField();
			textSum.setEditable(false);
			infoPanel_3.add(textSum);
			textSum.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
			textSum.setColumns(10);
			JPanel btnPanel = new JPanel();
			btnPanel.setBackground(UIManager.getColor("Button.disabledShadow"));
			btnPanel.setBounds(1003, 536, 169, 120);
			order.add(btnPanel);
			btnPanel.setLayout(null);
			JPanel btnPanel_1 = new JPanel();
			btnPanel_1.setBackground(UIManager.getColor("Button.disabledShadow"));
			btnPanel_1.setBounds(0, 0, 169, 40);
			btnPanel.add(btnPanel_1);
			JButton btnSearch = new RoundedButton("\uAC80\uC0C9");
			btnPanel_1.add(btnSearch);
			btnSearch.setForeground(new Color(255, 255, 255));
			btnSearch.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
			btnSearch.setBackground(new Color(135, 206, 250));
			JButton btnUpdate = new RoundedButton("\uC218\uC815");
			btnPanel_1.add(btnUpdate);
			btnUpdate.setText("\uAC31\uC2E0");
			btnUpdate.setForeground(new Color(255, 255, 255));
			btnUpdate.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
			btnUpdate.setBackground(new Color(135, 206, 250));
			JPanel btnPanel_2 = new JPanel();
			btnPanel_2.setBackground(UIManager.getColor("Button.disabledShadow"));
			btnPanel_2.setBounds(0, 40, 169, 40);
			btnPanel.add(btnPanel_2);
			JButton btnInsert = new RoundedButton("\uC800\uC7A5");
			btnPanel_2.add(btnInsert);
			btnInsert.setText("\uCD94\uAC00");
			btnInsert.setForeground(new Color(255, 255, 255));
			btnInsert.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
			btnInsert.setBackground(new Color(135, 206, 250));
			JButton btnDelete = new RoundedButton("\uC0AD\uC81C");
			btnPanel_2.add(btnDelete);
			btnDelete.setForeground(new Color(255, 255, 255));
			btnDelete.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
			btnDelete.setBackground(new Color(135, 206, 250));
			JPanel btnPanel_3 = new JPanel();
			btnPanel_3.setBackground(UIManager.getColor("Button.disabledShadow"));
			btnPanel_3.setBounds(0, 80, 169, 40);
			btnPanel.add(btnPanel_3);
			JButton btnClear = new RoundedButton("Reset");
			btnPanel_3.add(btnClear);
			btnClear.setText("\uCD08\uAE30\uD654");
			btnClear.setForeground(new Color(255, 255, 255));
			btnClear.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
			btnClear.setBackground(new Color(135, 206, 250));
			// 주문정보
			btnClear.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setClear();
				}
			});
			// 주문정보 저장하기 - ID, 상품번호, 개수, 주문날짜로 입력 / 주문번호는 AI, 가격은 트리거로 자동
			btnInsert.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					insertOrder();
				}
			});

			// 주문정보 데이터 수정 하기
			btnUpdate.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					updateOrder();
				}
			});
			// 주문정보 삭제하기 - 주문번호로 삭제
			btnDelete.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					deleteOrder();
				}
			});
			btnSearch.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					searchOrder(textOrderNo.getText(), textID.getText(), textProductNo.getText(),
							textOrderAmount.getText(), textOrderDate.getText(), textTotalPrice.getText(),
							comboBoxProductNo.getSelectedIndex(), comboBoxAmount.getSelectedIndex(),
							comboBoxTotal.getSelectedIndex());

					textFieldSelectCnt.setText(Integer.toString(model.getRowCount()));
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
			order.add(imageLabel);
		}
		return order;
	}

	// 테이블과 입력 부분을 초기화 기능
	private static void setClear() {
		textOrderNo.setText("");
		textID.setText("");
		textProductNo.setText("");
		textOrderAmount.setText("");
		textOrderDate.setText("");
		textTotalPrice.setText("");
		getTable();
	}

	// DB에서 데이터를 불러와 테이블로 넣는 기능
	public static void getTable() {
		model.setRowCount(0);
		try {
			String query = "SELECT * FROM ordertbl";
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			int sum = 0;
			while (rs.next()) {
				model.addRow(new Object[] { rs.getInt("orderNo"), rs.getString("ID"), rs.getString("orderDate"),
						rs.getInt("productNo"), rs.getInt("orderAmount"), rs.getInt("totalPrice") });
				sum += rs.getInt("totalPrice");
			}
			String sumResult = formatter.format(sum);
			textSum.setText(sumResult);
			textFieldSelectCnt.setText(Integer.toString(model.getRowCount()));
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 주문 정보를 갱신하는 기능
	private static void updateOrder() {
		try {
			String sql = "Select amount from producttbl where productNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(textProductNo.getText()));
			rs = pstmt.executeQuery();
			rs.next();
			if (rs.getInt("amount") >= Integer.parseInt(textOrderAmount.getText())) {
				sql = "UPDATE ordertbl SET ID=?, productNo=?, " + " orderAmount=?, orderDate=? WHERE orderNo = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, textID.getText());
				pstmt.setInt(2, Integer.parseInt(textProductNo.getText()));
				pstmt.setInt(3, Integer.parseInt(textOrderAmount.getText()));
				String date = textOrderDate.getText();
				java.sql.Date sDate = java.sql.Date.valueOf(date);
				pstmt.setDate(4, sDate);
				pstmt.setInt(5, Integer.parseInt(textOrderNo.getText()));
				pstmt.executeUpdate();
				pstmt.close();
				String error = "주문 정보를 갱신했습니다.";
				JLabel lblError = new JLabel(error);
				lblError.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
				JOptionPane.showMessageDialog(null, lblError, "Successful", JOptionPane.PLAIN_MESSAGE);
			} else {
				String error = "주문 정보를 갱신하지 못했습니다.";
				JLabel lblError = new JLabel(error);
				lblError.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
				JOptionPane.showMessageDialog(null, lblError, "Error", JOptionPane.PLAIN_MESSAGE);
			}
			getTable();
		} catch (Exception ex) {
			String error = "주문 정보를 갱신하지 못했습니다.";
			JLabel lblError = new JLabel(error);
			lblError.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
			JOptionPane.showMessageDialog(null, lblError, "Error", JOptionPane.PLAIN_MESSAGE);
		}
	}

	// 주문 정보를 삭제하는 기능
	private static void deleteOrder() {
		String sql = "DELETE FROM ordertbl WHERE orderNo = ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(textOrderNo.getText()));
			pstmt.executeUpdate();
			getTable();
			pstmt.close();
			String error = "주문 정보를 삭제했습니다.";
			JLabel lblError = new JLabel(error);
			lblError.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
			JOptionPane.showMessageDialog(null, lblError, "Successful", JOptionPane.PLAIN_MESSAGE);
		} catch (Exception ex) {
			String error = "주문 정보를 삭제하지 못했습니다.";
			JLabel lblError = new JLabel(error);
			lblError.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
			JOptionPane.showMessageDialog(null, lblError, "Error", JOptionPane.PLAIN_MESSAGE);
		}
	}

	// 주문 정보를 입력하는 기능
	private static void insertOrder() {
		try {
			String sql = "Select amount from producttbl where productNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(textProductNo.getText()));
			rs = pstmt.executeQuery();
			rs.next();
			if (rs.getInt("amount") >= Integer.parseInt(textOrderAmount.getText())) {
				sql = "INSERT INTO ordertbl(ID, productNo, orderAmount, orderDate) VALUES(?, ?, ?, ?)";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, textID.getText());
				pstmt.setInt(2, Integer.parseInt(textProductNo.getText()));
				pstmt.setInt(3, Integer.parseInt(textOrderAmount.getText()));
				String date = textOrderDate.getText();
				java.sql.Date sDate = java.sql.Date.valueOf(date);
				pstmt.setDate(4, sDate);
				pstmt.executeUpdate();
				String error = "주문 정보를 추가했습니다.";
				JLabel lblError = new JLabel(error);
				lblError.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
				JOptionPane.showMessageDialog(null, lblError, "Successful", JOptionPane.PLAIN_MESSAGE);
			} else {
				String error = "주문량이 재고보다 많습니다.";
				JLabel lblError = new JLabel(error);
				lblError.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
				JOptionPane.showMessageDialog(null, lblError, "Error", JOptionPane.PLAIN_MESSAGE);
			}
			getTable();
		} catch (Exception ex) {
			String error = "주문 정보를 추가하지 못했습니다.";
			JLabel lblError = new JLabel(error);
			lblError.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
			JOptionPane.showMessageDialog(null, lblError, "Error", JOptionPane.PLAIN_MESSAGE);
		}
	}

	// 주문 정보를 검색하는 기능
	private static void searchOrder(String orderNo, String ID, String productNo, String orderAmount, String orderDate,
			String totalPrice, int comboProductNo, int comboOrderAmount, int comboTotalPrice) {
		String sql = "SELECT * FROM ordertbl WHERE ";

		if (!orderNo.equals("")) {
			st = new StringTokenizer(orderNo, ",");
			sql = sql + "(";
			while (st.hasMoreTokens()) {
				sql = sql + "Cast(orderNo as char(10)) like ? or ";
				st.nextToken();
			}
			sql = sql.substring(0, sql.length() - 3);
			sql = sql + ")";
			sql = sql + " and ";
		}
		if (!ID.equals("")) {
			if (ID.toUpperCase().equals("ID")) {
				sql = "select m.id, count(orderAmount) as countID, sum(totalPrice) as sumID from membertbl m left outer join ordertbl o on o.id = m.id group by id     ";
			} else {
				st = new StringTokenizer(ID, ",");
				sql = sql + "(";
				while (st.hasMoreTokens()) {
					sql = sql + "ID like ? or ";
					st.nextToken();
				}
				sql = sql.substring(0, sql.length() - 3);
				sql = sql + ")";
				sql = sql + " and ";
			}
		}
		if (!productNo.equals("")) {
			if (productNo.equals("번호")) {
				sql = "Select p.productNo, count(orderAmount) as countPN, sum(totalPrice) as sumPN from producttbl p left outer join ordertbl o on p.productNo = o.productNo group by productNo     ";
			} else {
				st = new StringTokenizer(productNo, ",");
				sql = sql + "(";
				switch (comboProductNo) {
				case (0):
					while (st.hasMoreTokens()) {
						sql = sql + "productNo = ? or ";
						st.nextToken();
					}
					break;
				case (1):
					while (st.hasMoreTokens()) {
						sql = sql + "Cast(productNo as char(10)) like ? or ";
						st.nextToken();
					}
					break;
				case (2):
					while (st.hasMoreTokens()) {
						sql = sql + "productNo >= ? or ";
						st.nextToken();
					}
					break;
				case (3):
					while (st.hasMoreTokens()) {
						sql = sql + "productNo <= ? or ";
						st.nextToken();
					}
					break;
				}
				sql = sql.substring(0, sql.length() - 3);
				sql = sql + ")";
				sql = sql + " and ";
			}
		}
		if (!orderAmount.equals("")) {
			st = new StringTokenizer(orderAmount, ",");
			sql = sql + "(";
			switch (comboOrderAmount) {
			case (0):
				while (st.hasMoreTokens()) {
					sql = sql + "orderAmount = ? or ";
					st.nextToken();
				}
				break;
			case (1):
				while (st.hasMoreTokens()) {
					sql = sql + "Cast(orderAmount as char(10)) like ? or ";
					st.nextToken();
				}
				break;
			case (2):
				while (st.hasMoreTokens()) {
					sql = sql + "orderAmount >= ? or ";
					st.nextToken();
				}
				break;
			case (3):
				while (st.hasMoreTokens()) {
					sql = sql + "orderAmount <= ? or ";
					st.nextToken();
				}
				break;
			}
			sql = sql.substring(0, sql.length() - 3);
			sql = sql + ")";
			sql = sql + " and ";
		}
		if (!orderDate.equals("")) {
			st = new StringTokenizer(orderDate, ",");
			sql = sql + "(";
			while (st.hasMoreTokens()) {
				sql = sql + "orderDate like ? or ";
				st.nextToken();
			}
			sql = sql.substring(0, sql.length() - 3);
			sql = sql + ")";
			sql = sql + " and ";
		}
		if (!totalPrice.equals("")) {
			st = new StringTokenizer(totalPrice, ",");
			sql = sql + "(";
			switch (comboTotalPrice) {
			case (0):
				while (st.hasMoreTokens()) {
					sql = sql + "totalPrice = ? or ";
					st.nextToken();
				}
				break;
			case (1):
				while (st.hasMoreTokens()) {
					sql = sql + "Cast(totalPrice as char(15)) like ? or ";
					st.nextToken();
				}
				break;
			case (2):
				while (st.hasMoreTokens()) {
					sql = sql + "totalPrice >= ? or ";
					st.nextToken();
				}
				break;
			case (3):
				while (st.hasMoreTokens()) {
					sql = sql + "totalPrice <= ? or ";
					st.nextToken();
				}
				break;
			}
			sql = sql.substring(0, sql.length() - 3);
			sql = sql + ")";
			sql = sql + " and ";
		}
		sql = sql.substring(0, sql.length() - 4);
		try {
			pstmt = con.prepareStatement(sql);
			int idx = 1;
			if (!orderNo.equals("")) {
				st = new StringTokenizer(orderNo, ",");
				while (st.hasMoreTokens()) {
					pstmt.setString(idx++, "%" + st.nextToken().trim() + "%");
				}
			}
			if (!ID.equals("")) {
				if (!ID.toUpperCase().equals("ID")) {
					st = new StringTokenizer(ID, ",");
					while (st.hasMoreTokens()) {
						pstmt.setString(idx++, "%" + st.nextToken().trim() + "%");
					}
				}
			}
			if (!productNo.equals("")) {
				if (!productNo.equals("번호")) {
					st = new StringTokenizer(productNo, ",");
					switch (comboProductNo) {
					case (1):
						while (st.hasMoreTokens()) {
							pstmt.setString(idx++, "%" + st.nextToken().trim() + "%");
						}
						break;
					default:
						while (st.hasMoreTokens()) {
							pstmt.setInt(idx++, Integer.parseInt(st.nextToken().trim()));
						}
						break;
					}
				}
			}
			if (!orderAmount.equals("")) {
				st = new StringTokenizer(orderAmount, ",");
				switch (comboOrderAmount) {
				case (1):
					while (st.hasMoreTokens()) {
						pstmt.setString(idx++, "%" + st.nextToken().trim() + "%");
					}
					break;
				default:
					while (st.hasMoreTokens()) {
						pstmt.setInt(idx++, Integer.parseInt(st.nextToken().trim()));
					}
					break;
				}
			}
			if (!orderDate.equals("")) {
				st = new StringTokenizer(orderDate, ",");
				while (st.hasMoreTokens()) {
					pstmt.setString(idx++, "%" + st.nextToken().trim() + "%");
				}
			}
			if (!totalPrice.equals("")) {
				st = new StringTokenizer(totalPrice, ",");
				switch (comboTotalPrice) {
				case (1):
					while (st.hasMoreTokens()) {
						pstmt.setString(idx++, "%" + st.nextToken().trim() + "%");
					}
					break;
				default:
					while (st.hasMoreTokens()) {
						pstmt.setInt(idx++, Integer.parseInt(st.nextToken().trim()));
					}
					break;
				}
			}

			rs = pstmt.executeQuery();
			model.setRowCount(0);
			int sum = 0;
			while (rs.next()) {
				if (productNo.equals("번호")) {
					model.addRow(new Object[] { null, null, null, rs.getInt("productNo"), rs.getInt("countPN"),
							rs.getInt("sumPN") });
					sum += rs.getInt("sumPN");
				} else if (ID.toUpperCase().equals("ID")) {
					model.addRow(new Object[] { null, rs.getString("ID"), null, null, rs.getInt("countID"),
							rs.getInt("sumID") });
					sum += rs.getInt("sumID");
				} else {
					model.addRow(new Object[] { rs.getInt("orderNo"), rs.getString("ID"), rs.getString("orderDate"),
							rs.getInt("productNo"), rs.getInt("orderAmount"), rs.getInt("totalPrice") });
					sum += rs.getInt("totalPrice");
				}
			}

			String sumResult = formatter.format(sum);
			textSum.setText(sumResult);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}