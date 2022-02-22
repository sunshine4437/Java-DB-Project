package Project;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Frame {
	private static JFrame frame;
	private static List<JPanel> pList = setPanel();

	public static void main(String[] args) {
		frame = new JFrame("쇼핑몰 관리 프로그램");
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setSize(1200, 800);
		frame.setPreferredSize(new Dimension(1200, 800));
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(null);

		addPanel();
	}
	//화면을 구성할 패널들 생성
	private static ArrayList<JPanel> setPanel() {
		ArrayList<JPanel> temp = new ArrayList<>();
		temp.add(HomePanel.getPanel());
		temp.add(MenuPanel.getPanel());
		temp.add(MemberPanel.getPanel());
		temp.add(OrderPanel.getPanel());
		temp.add(ProductPanel.getPanel());
		return temp;
	}
	//화면 패널을 프레임에 추가
	private static void addPanel() {
		for (JPanel panel : pList) {
			frame.getContentPane().add(panel);
		}
	}
}
