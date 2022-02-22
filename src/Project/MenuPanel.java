package Project;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class MenuPanel {
	private static JPanel menu = null;

	public MenuPanel() {
		getPanel();
	}

	// 패널들 생성하고 메소드를 호출시 패널 반환
	public static JPanel getPanel() {
		if (menu == null) {
			menu = new JPanel();
			menu.setBackground(new Color(255, 0, 0, 0));
			menu.setBounds(0, 0, 1185, 100);
			menu.setLayout(null);

			JPanel panel = new JPanel();
			panel.setBackground(UIManager.getColor("Button.disabledShadow"));
			panel.setBounds(307, 0, 571, 100);
			menu.add(panel);
			panel.setLayout(null);

			RoundedButton btnHome = new RoundedButton("Home");
			btnHome.setBounds(21, 12, 116, 76);
			panel.add(btnHome);
			btnHome.setText("\uCC98\uC74C");
			btnHome.setForeground(SystemColor.text);
			btnHome.setBackground(new Color(135, 206, 250));
			btnHome.setFont(new Font("맑은 고딕", Font.PLAIN, 30));
			btnHome.setPreferredSize(new Dimension(116, 63));
			btnHome.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					HomePanel.initVisible(1);
					HomePanel.setMemberNo();
					HomePanel.setSales();
				}
			});

			RoundedButton btnMember = new RoundedButton("\uBA64\uBC84");
			btnMember.setToolTipText(
					"<html>입력 칸에 입력값, 입력값  형식으로 입력시 여러 개의 값 검색<br>이름 검색할 때 성씨 검색 방법 김__, 이__, 박__ 과 같이 언더바 2개 사용하여 검색</html>");
			btnMember.setText("\uD68C\uC6D0");
			btnMember.setBounds(158, 12, 116, 76);
			panel.add(btnMember);

			btnMember.setForeground(SystemColor.text);
			btnMember.setBackground(new Color(135, 206, 250));
			btnMember.setFont(new Font("맑은 고딕", Font.PLAIN, 30));
			btnMember.setPreferredSize(new Dimension(116, 63));

			btnMember.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					HomePanel.initVisible(2);
					MemberPanel.getTable();
				}
			});

			RoundedButton btnOrder = new RoundedButton("\uC8FC\uBB38");
			btnOrder.setToolTipText("<html>입력 칸에 입력값, 입력값  형식으로 입력시 여러 개의 값 검색<br>날짜 검색시 월 : -11-, 일 : -__-11 같은 방식으로 가능</html>");
			btnOrder.setBounds(295, 12, 116, 76);
			panel.add(btnOrder);
			btnOrder.setForeground(SystemColor.text);
			btnOrder.setBackground(new Color(135, 206, 250));
			btnOrder.setFont(new Font("맑은 고딕", Font.PLAIN, 30));
			btnOrder.setPreferredSize(new Dimension(116, 63));
			btnOrder.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					HomePanel.initVisible(3);
					OrderPanel.getTable();
				}
			});

			RoundedButton btnProduct = new RoundedButton("\uC0C1\uD488");
			btnProduct.setToolTipText("<html>" + "상품명 칸에 상품명 입력시 상품명 중복없이 출력<br>" + "분류 칸에 분류 입력시 분류 중복없이 출력<br>"
					+ "입력 칸에 입력값, 입력값  형식으로 입력시 여러 개의 값 검색<br>" + "</html>");
			btnProduct.setBounds(432, 12, 116, 76);
			panel.add(btnProduct);
			btnProduct.setForeground(SystemColor.text);
			btnProduct.setBackground(new Color(135, 206, 250));
			btnProduct.setFont(new Font("맑은 고딕", Font.PLAIN, 30));
			btnProduct.setPreferredSize(new Dimension(116, 63));
			btnProduct.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					HomePanel.initVisible(4);
					ProductPanel.getTable();
				}
			});
			BufferedImage image = null;
			try {
				image = ImageIO.read(new File("./source/1.jpg"));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			JLabel imageLabel = new JLabel(new ImageIcon(image));
			imageLabel.setBounds(0, 0, 1184, 761);
			imageLabel.setPreferredSize(new Dimension(1184, 661));
			menu.add(imageLabel);

		}
		return menu;
	}
}
