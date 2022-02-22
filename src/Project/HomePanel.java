package Project;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

@SuppressWarnings("serial")
public class HomePanel extends JPanel {
   private static Connection conn = DBConnect.getConnection();

   private static JPanel home = null;
   private static JPanel menu = null;
   private static JPanel member = null;
   private static JPanel order = null;
   private static JPanel product = null;
   private static JTextField dateTextField;
   private static JTextField memNoTextField;
   private static JTextField textFieldSales;

   public HomePanel() {
      getPanel();
   }
   //패널들 생성하고 메소드를 호출시 패널 반환
   public static JPanel getPanel() {
      if (home == null) {
         home = new JPanel();
         menu = MenuPanel.getPanel();
         member = MemberPanel.getPanel();
         order = OrderPanel.getPanel();
         product = ProductPanel.getPanel();
         home.setBounds(0, 0, 1184, 761);
         home.setPreferredSize(new Dimension(1184,761));
         home.setLayout(null);
         initVisible(1);
         home.setBackground(new Color(255, 0, 0, 0));

         JPanel panel = new JPanel();
         panel.setBackground(UIManager.getColor("Button.disabledShadow"));
         panel.setBounds(199, 115, 786, 246);
         home.add(panel);
         panel.setLayout(null);

         JLabel titleLabel = new JLabel("\uC1FC\uD551\uBAB0 \uC7AC\uACE0 \uAD00\uB9AC \uC2DC\uC2A4\uD15C");
         titleLabel.setBounds(12, 0, 762, 241);
         titleLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 60));
         titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
         panel.add(titleLabel);

         JPanel btnPanel = new JPanel();
         btnPanel.setBackground(UIManager.getColor("Button.disabledShadow"));
         btnPanel.setBounds(199, 371, 786, 207);
         home.add(btnPanel);

         RoundedButton btnMember = new RoundedButton("\uD68C\uC6D0");
         btnMember.setBounds(72, 28, 166, 150);
         btnMember.setForeground(new Color(255, 255, 255));
         btnMember.setBackground(new Color(135, 206, 250));
         btnMember.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               initVisible(2);
            }
         });
         btnPanel.setLayout(null);
         btnMember.setFont(new Font("맑은 고딕", Font.PLAIN, 40));
         btnMember.setPreferredSize(new Dimension(200, 150));
         btnPanel.add(btnMember);

         RoundedButton btnOrder = new RoundedButton("\uC8FC\uBB38");
         btnOrder.setBounds(310, 28, 166, 150);
         btnOrder.setForeground(new Color(255, 255, 255));
         btnOrder.setBackground(new Color(135, 206, 250));
         btnOrder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               initVisible(3);
            }
         });
         btnOrder.setFont(new Font("맑은 고딕", Font.PLAIN, 40));
         btnPanel.add(btnOrder);

         RoundedButton btnProduct = new RoundedButton("\uC0C1\uD488");
         btnProduct.setBounds(548, 28, 166, 150);
         btnProduct.setForeground(new Color(255, 255, 255));
         btnProduct.setBackground(new Color(135, 206, 250));
         btnProduct.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               initVisible(4);
            }
         });
         btnProduct.setFont(new Font("맑은 고딕", Font.PLAIN, 40));
         btnProduct.setPreferredSize(new Dimension(200, 150));
         btnPanel.add(btnProduct);

         JPanel InfoPanel = new JPanel();
         InfoPanel.setBackground(UIManager.getColor("Button.disabledShadow"));
         InfoPanel.setBounds(199, 591, 786, 46);
         home.add(InfoPanel);
         InfoPanel.setLayout(null);

         JLabel dateLabel = new JLabel("\uB0A0\uC9DC");
         dateLabel.setBounds(84, 12, 30, 21);
         dateLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
         InfoPanel.add(dateLabel);

         dateTextField = new JTextField();
         dateTextField.setBounds(119, 9, 136, 27);
         SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월 dd일"); // 현재 날짜
         Date time = new Date();
         String timeString = format.format(time);
         dateTextField.setText(timeString);
         dateTextField.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
         dateTextField.setHorizontalAlignment(SwingConstants.CENTER);
         InfoPanel.add(dateTextField);
         dateTextField.setColumns(10);

         JLabel memNoLabel = new JLabel("\uD68C\uC6D0\uC218");
         memNoLabel.setBounds(260, 12, 45, 21);
         memNoLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
         InfoPanel.add(memNoLabel);

         memNoTextField = new JTextField();
         memNoTextField.setHorizontalAlignment(SwingConstants.RIGHT);

         setMemberNo();

         memNoTextField.setBounds(310, 9, 136, 27);
         memNoTextField.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
         InfoPanel.add(memNoTextField);
         memNoTextField.setColumns(10);

         JLabel salesLabel = new JLabel("\uC774\uB2EC\uC758 \uD310\uB9E4\uAE08\uC561");
         salesLabel.setBounds(451, 12, 110, 21);
         salesLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
         InfoPanel.add(salesLabel);

         textFieldSales = new JTextField();
         textFieldSales.setHorizontalAlignment(SwingConstants.RIGHT);
         textFieldSales.setBounds(566, 9, 136, 27);
         textFieldSales.setAlignmentX(RIGHT_ALIGNMENT);

         textFieldSales.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
         InfoPanel.add(textFieldSales);
         textFieldSales.setColumns(10);

         BufferedImage image = null;
         try {
            image = ImageIO.read(new File("./source/1.jpg"));
         } catch (IOException e1) {
            e1.printStackTrace();
         }
         JLabel imageLabel = new JLabel(new ImageIcon(image));
         imageLabel.setBounds(0, 0, 1184, 761);
         imageLabel.setPreferredSize(new Dimension(1184, 761));
         home.add(imageLabel);

         setSales();
      }
      return home;
   }
   //화면 전환시 패널의 가시성 설정
   public static void initVisible(int n) {
      boolean vHome = false;
      boolean vMenu = false;
      boolean vMember = false;
      boolean vOrder = false;
      boolean vProduct = false;
      switch (n) {
      case 1:
         vHome = true;
         vMenu = false;
         vMember = false;
         vOrder = false;
         vProduct = false;
         break;
      case 2:
         vHome = false;
         vMenu = true;
         vMember = true;
         vOrder = false;
         vProduct = false;
         break;
      case 3:
         vHome = false;
         vMenu = true;
         vMember = false;
         vOrder = true;
         vProduct = false;
         break;
      case 4:
         vHome = false;
         vMenu = true;
         vMember = false;
         vOrder = false;
         vProduct = true;
      }

      home.setVisible(vHome);
      menu.setVisible(vMenu);
      member.setVisible(vMember);
      order.setVisible(vOrder);
      product.setVisible(vProduct);
   }
   // 초기화면에서 회원수 표시 기능
   public static void setMemberNo() {
      String sql = "SELECT count(id) FROM membertbl";
      String r = "";
      try {
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql);
         rs.next();
         r = rs.getString(1);
         if (rs != null)
            rs.close();
         if (stmt != null)
            stmt.close();
      } catch (SQLException e) {
         e.printStackTrace();
      }
      memNoTextField.setText(r + "명");
      if (memNoTextField != null)
         memNoTextField.repaint();
   }
   // 초기화면에서 이달의 판매금액 표시 기능
   public static void setSales() {
      String sql = "select sum(ordertbl.totalprice) from ordertbl where substr(orderdate,6,7) = ?";
      int r = 0;
      String str = "";
      String month = dateTextField.getText().substring(6, 8);
      try {
         PreparedStatement pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, Integer.parseInt(month));
         ResultSet rs = pstmt.executeQuery();
         rs.next();
         r = rs.getInt(1);
         DecimalFormat formatter = new DecimalFormat("###,###,###,###");
         str = formatter.format((Number) r);
         if (rs != null)
            rs.close();
         if (pstmt != null)
            pstmt.close();
      } catch (SQLException e) {
         e.printStackTrace();
      }
      textFieldSales.setText(str + "원");
      if (textFieldSales != null)
         textFieldSales.repaint();

   }
}