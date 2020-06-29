import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EnumSet;
import java.util.Locale;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.Box;
import java.awt.SystemColor;
import java.awt.Toolkit;

import javax.swing.JLayeredPane;
import javax.swing.JTabbedPane;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.DropMode;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

/* ���� Ŭ���� */
public class CafeTest {

	/*SWING*/
	private JFrame frame;
	private JTextField earnings;
	private JTextField amount;
	private JTextField timeField;
	private JTextField payInfoMenu;
	private JTextField payInfoPrice;
	
	static int i, j;
	
	/* �޴� �ν��Ͻ� ���� �ѵ� ���(���� ����!!) */ private final static int menuLength = 12;
	/*[DB]Menu �ν��Ͻ� �迭 ����*/static Menu menu[] = new Menu[menuLength];
	
	
	/* �ֹ����� �ν��Ͻ� ���� �ѵ� */ private static int orderLength = 10;
	/*Order �ν��Ͻ� �迭 ����*/private static Order order[] = new Order[orderLength];
	
	
	/*[DB]���� �Ǹű�� �ν��Ͻ� ����*/static LastRecords record[] = new LastRecords[LastRecords.recordsIndex];
	
	
	/*����ȭ�� �ֹ��޴� �ӽô��� �����Ͱ�����*/private static String tempMenu[] = new String[Order.ordermenuLength];
	/*����ȭ�� �ֹ����� �ӽô��� �����Ͱ�����*/private static int tempQuantity[] = new int[Order.ordermenuLength];
	/*����ȭ�� �ֹ��ݾ� �ӽô��� �����Ͱ�����*/private static int tempPayprice;
	
	
	/*���� �Ǹż��Է�*/ private int nowEarnings;
	/*���� �Ǹ��ֹ���*/ private int nowPays = 0;
	
	
	/* ��Table ����ȭ�� �ֹ�������� �� �̸� */ private static String heading[] = new String[] {"��ȣ","�ֹ� �޴�","���� �ݾ�"};
	/* ��Table ����ȭ�� �ֹ�������� �� */ private static Object[][] orderData = new Object[orderLength][3];
	/* ��Table ����ȭ�� �ֹ�������� ���� */private static JTable table =  new JTable(orderData, heading);
 
	
	/* ��Table ���� �Ǹű�� �� �̸� */ private static String heading_last[] = new String[] {"��¥","�Ǹż���","�Ǹ��ֹ���"};
	/* ��Table ���� �Ǹű�� �� */ private static Object[][] orderData_last = new Object[LastRecords.recordsIndex][3];
	/* ��Table ���� �Ǹű�� ���� */private JTable table_last = new JTable(orderData_last, heading_last);
	
	/* ��Table �޴���� �̸� */ private static String heading_menu[] = new String[] {"�޴� �̸�","����"};
	/* ��Table �޴���� �� */ private  static Object[][] menuData = new Object[menuLength][2];
	/* ��Table �޴���� ���� */private static JTable table_menu = new JTable(menuData, heading_menu);
	/* Table �޴���� ���� 2 */private static JTable table_menu2 = new JTable(menuData, heading_menu);
	
	/* �޴� �߰� �ؽ�Ʈ �ʵ� */
	private JTextField str_newMenuName;
	private JTextField int_newMenuPrice;
	
	/*�ð� ����*/SimpleDateFormat format1 = new SimpleDateFormat("M/dd a hh:mm:ss",new Locale("en", "US"));
	SimpleDateFormat format2 = new SimpleDateFormat("Y-M-dd (H:mm)");
	
	/*�г�*/
	JPanel topBar = new JPanel();
	JPanel bottomMain = new JPanel();
	JPanel deleteMenuBar = new JPanel();
	JPanel lastEarningsBar = new JPanel();
	JPanel setIntoPanel = new JPanel();
	JPanel square = new JPanel();
	/*������ �޴��� ��ȣ ����*/int numtemp;

	/*�޴� ����â�� �޴��̸� �ؽ�Ʈ �ʵ�*/private JTextField reMenuNameField;
	/*�޴� ����â�� ���� �ؽ�Ʈ �ʵ�*/private JTextField reMenuPriceField;
	
	/* ����ȭ�� �޴� ��ư ���� */
	static JButton MenuBtn[] = new JButton[12];
	
	
	/* ����ȭ�鿡 �������� �޴� ��ư �ؽ�Ʈ ���ΰ�ħ */
	public void resetingShowingMenu() {
		for(i=0; i<menuLength; i++) {
			MenuBtn[i].setText("<HTML><body><center>"+menu[i].getMenuName()+"<br>"+(menu[i].getMenuName().equals("")?"":menu[i].getMenuPrice()+"��")+"</center></body></HTML>");
		}
			
	}//end of method
	
	
	/*setMenuBar�� deleteMenuBar���� ���Ǵ� �޴� Table�� ������ ���ΰ�ħ*/
	public void resetingMenuTable() {
		for(i=0; i<menuLength; i++) {
			menuData[i][0] = menu[i].getMenuName();
			menuData[i][1] = menu[i].getMenuName().equals("")?"":menu[i].getMenuPrice()+"��";
		}
	}//end of method
	
	
	/*�޴���� ���� �� ���� ���ΰ�ħ(�г� off/on ����)*/
	public void resetoutMenuTable() {
		deleteMenuBar.setVisible(false);
		deleteMenuBar.setVisible(true);
		
	}//end of method
	
	
	/*bottomMain�� �ֹ� ���� table �� ���ΰ�ħ*/
	public void resetOrderInfoTable() {
		/*�ֹ� ���� ��ü�迭 ����*/
		Order tmp;
		for(i=0; i<orderLength; i++) {
			for(int j=i+1; j<orderLength ;j++) {
				if(order[i].getOrdernum()==0) {
					tmp = order[i];
					order[i] = order[j];
					order[j] = tmp;
				}
			}
		}
		/*������ ���׿� ���Ͽ� ��� ���� ��ħ*/
		for(i=0; i<orderLength; i++) {
			
			String messg = "";
			//if �ֹ��� �޴��� ������
			if(order[i].isFilled()==false) {
				messg="";
			}else if(order[i].getOrderName(1).equals("")) { //�޴��� �������� �ֹ�������
				messg =order[i].getOrderName(0)+"("+order[i].getOrderQuantity(0)+")";
		
			}else if(order[i].getOrderName(1).equals("")==false){ //�޴��� ������ �ֹ�������
				messg =order[i].getOrderName(0)+"("+order[i].getOrderQuantity(0)+"), "+order[i].getOrderName(1)+"("+order[i].getOrderQuantity(1)+")";
				
			}
			orderData[i][0] = (order[i].getOrdernum()==0)?"":(order[i].getOrdernum());
			orderData[i][1] = messg;
			orderData[i][2] = (order[i].getFinalPrice()==0)?"":order[i].getFinalPrice()+"��";
		}
	}//end of method
	
	
	/*�޴� ���� �߰� �޼���*/
	public static int id;
	public void addMenu(String name, int price) {
		
		JLabel label = new JLabel("�� �޴� ["+name+" "+price+"��] ��� �Ϸ�");
		label.setFont(new Font("����", Font.BOLD, 17));
		label.setForeground(Color.MAGENTA);
		
		JLabel label03 = new JLabel("�޴� ���� �ѵ� �ʰ��Դϴ�.");
		label03.setFont(new Font("����", Font.PLAIN, 16));
		
		i=0;
		int flag=0;
		/*�迭�� ���������� ���ִ��� Ȯ���Ͽ� �迭�� �� á���� Ȯ���Ѵ�.*/
		if((menu[menuLength-1].getMenuName()).equals("")) { //�� ������������ i�� ���� �� �ڸ��� ã�´�
			while(flag==0) { //�޴� ��ü �迭���� ���� �� �� ������ ã�´�.
				if((menu[i].getMenuName()).equals("")) {
					/* �޴� ���(�߰�) *///menu[i].reMenu(name, price);
					
					/* DB */
					String sprice=Integer.toString(price);
					String si=Integer.toString(i);
					addMenu_DB addMenu_DB = new addMenu_DB(si,name, sprice);
					selectMenu_DB selectMenuName_DB = new selectMenu_DB(si);
					
					
					flag=1; 
				}else {	i++ ;} //i�� �ε����� ������� ������ i���� �����ϰ� �ٽ� while���� ������.
			}
			JOptionPane.showMessageDialog(null, label); //��ϿϷ� �޽���
		}else {
			JOptionPane.showMessageDialog(null, label03,"��� ����",JOptionPane.OK_OPTION); //��� ���� �޽���
		}
	}//end of method

	
	/*�޴� ��ü�迭 ����*/
	public void reArraymanuTable() {
		resetingMenuTable(); //���� �� �޴�table�� ǥ���� �� ���ΰ�ħ
		Menu temp;
		for(i=0; i<menuLength-1; i++) {
			if(menu[i].getMenuName().equals("") && menu[i+1].getMenuName().equals("")==false) { //�ٷ� ���� �ε����� ��� �ڿ� ��������
					temp = menu[i];
					menu[i] = menu[i+1];
					menu[i+1]=temp;
			}
		}
		
		resetingMenuTable(); //���� �� �޴�table�� ǥ���� �� ���ΰ�ħ
		deleteMenuBar.setVisible(false);
		deleteMenuBar.setVisible(true);
	}//end of method
	
	
	/*�޴� ���ö� ���ΰ�ħ �޼���*/
	public void deletetempMenuSet() {
		for(i=0; i< Order.ordermenuLength; i++) {
			tempMenu[i] = "";
			tempQuantity[i] = 0;
			tempPayprice = 0;
		}
		payInfoMenu.setText("");
		payInfoPrice.setText("");
	}//end of method

	
	/*���� �ֹ��޴� ���� �������� �ش� �޴��� ���� �� ����*/private boolean flagpick;
	
	/*�޴��� ���������� ����Ǵ� ����ȭ�鿡�� �޴� ���ð��� �޼���*/
	public void pickMenu(int numOfmenu) { //������ �޴��� �ε��� ��ȣ�� �Ű������� ������
		flagpick=false;
		/* �̹� ���õ� �޴����� �Ǵ� */
		for(i=0; i<Order.ordermenuLength; i++) { 
			if(tempMenu[i].equals(menu[numOfmenu].getMenuName())){ //���� ���� ������ �޴��� ù��°�� �����ߴ� �޴��� ������ �̹� �����ߴ� �޴��̹Ƿ�
				tempQuantity[i]++; //�� �޴� ������ ������!
				tempPayprice += menu[numOfmenu].getMenuPrice(); //���ݵ� ������!
				flagpick = true;
				break;
			}
		}
		/* �̹� ���õ� �޴��� �ƴϰ� �޴��� �� ���� �ʾҴٸ� */
		if(flagpick==false && tempMenu[Order.ordermenuLength-1].equals("")) {
			for(i=0; i<Order.ordermenuLength; i++) { 
				if(tempMenu[i].equals("")) { //if �ֹ����� �޴� ����� ó�� ����� �ӽ�����
					tempMenu[i] = menu[numOfmenu].getMenuName();
					tempQuantity[i]++;
					tempPayprice += menu[numOfmenu].getMenuPrice();
					break;
				}
			}
		/* �̹� ���õ� �޴��� �ƴϰ� �޴��� �� á�ٸ� */
		}else if(flagpick==false && tempMenu[Order.ordermenuLength-1].equals("")==false){
			JOptionPane.showMessageDialog(null, "�ֹ� ������ �޴��� ������ "+Order.ordermenuLength+"������ �Դϴ�.","�޴� �ʰ�",JOptionPane.OK_OPTION);
		}
		
		/*�ð� ���ΰ�ħ*/resetTime();
		resetTemporder();	
	}
	
	/* �޴� ���� ���� �޼��� */
	public void deleteMenuin(int n){
		if(menu[n].getMenuName()=="") return; /*�޴��� ��������� �ƹ� ���۵� ���� �ʴ´�.*/
		
		JLabel label103 = new JLabel("["+menu[n].getMenuName()+"] �޴��� �����Ǿ����ϴ�.");
		label103.setFont(new Font("����", Font.BOLD, 17));
		label103.setForeground(Color.MAGENTA);
		
		JOptionPane.showMessageDialog(null, label103, "", JOptionPane.INFORMATION_MESSAGE);
		/*�޴� ����*/menu[n].deleteMenu();
		
		/* DB */
		deleteMenu_DB deleteMenu_DB = new deleteMenu_DB(Integer.toString(n));
		
		/*���� */resetingMenuTable();
		/*���� */reArraymanuTable();
		/*ǥ�� */resetoutMenuTable();
		
		resetTime();
	}
	
	
	/*�ֹ����� mothod*/
	public void payOrder() {
		/* �� ������ �ֺ������� �� �� ������ */
		if(order[orderLength-1].isFilled()==true) {
			JOptionPane.showMessageDialog(null, "�ֹ� ������ �� á���ϴ�.");
			return;
		}
		
		int flag=0;
		int tmpODRnum = 0;
		for(int j=1; j<=orderLength; j++) { //ã�ƾ� �ϴ� ��.
			for(int k=0; k<orderLength; k++) { //���� �ε��� ��ȣ ��������
				if(j==order[k].getOrdernum()) {
					flag=0;
					break;
				}else { flag=1;} //��ġ�� ���� ���ٸ� �� for���� ������ flag�� 1�̰���?
			}
			if(flag==1) { //��ġ�� ���� ���ٸ� �� ���� ä���ϰ� for���� ����������
				tmpODRnum = j;
				break;
			}
		}
		
		
		for(i=0; i<orderLength; i++) {
			/*�� �ֹ������� ������ '���� ������ �� �ֹ����� �ڸ�'�� ã�´�.*/
			if(order[i].isFilled() == false) {
				/*�ֹ����� �߰�*/order[i].reOrder(tempMenu, tempQuantity, tempPayprice, tmpODRnum);
				
				/*�����ݾ��� �Ǹż��Է��� ����*/ nowEarnings += tempPayprice;
				/*�Ǹ��ֹ��� ����*/nowPays++;
				
				/* �޴� ���� �ؽ�Ʈ ���� */
				String messg;
				if(tempMenu[1].equals("")) {
					messg =tempMenu[0]+"("+tempQuantity[0]+")";
				}else {
					messg =tempMenu[0]+"("+tempQuantity[0]+"), "+tempMenu[1]+"("+tempQuantity[1]+")";
				}
				/*���� �Ϸ� �޽���*/
				JLabel paymentLabel = new JLabel("�ֹ���ȣ"+(tmpODRnum)+": "+messg);
				paymentLabel.setFont(new Font("����", Font.BOLD, 17));
				paymentLabel.setForeground(Color.MAGENTA);
				JOptionPane.showMessageDialog(null, paymentLabel,"���� �Ϸ�",JOptionPane.PLAIN_MESSAGE);
				
				orderData[i][0] = tmpODRnum;
				orderData[i][1] = messg;
				orderData[i][2] = tempPayprice+"��";
				
				/*�ֹ������� �߰��ϰ� ����ȭ������ ���ư���!*/
				break;
			}
		}
		
		earnings.setText(Integer.toString(nowEarnings)+"��");
		amount.setText(Integer.toString(nowPays)+"��");
		
		/*�޴� ���� ���� ����*/ deletetempMenuSet();
		topBar.setVisible(false);
		bottomMain.setVisible(false);
		topBar.setVisible(true);
		bottomMain.setVisible(true);
	}
	
	/*�ֹ���� method*/
	public void cancelOrder() {
		/*�ð� ��*/resetTime();
		/*�޴� ���� ���� ����*/deletetempMenuSet();
		topBar.setVisible(false);
		bottomMain.setVisible(false);
		topBar.setVisible(true);
		bottomMain.setVisible(true);
	}
	
	
	selectLastRecords_DB selectLastRecords_DB;
	/* ���� ��� �ҷ��ͼ� table�� ����  */
	public void selectRecord() {
		for(i=0; i<LastRecords.recordsIndex; i++) {
			selectLastRecords_DB = new selectLastRecords_DB(Integer.toString(i));
		}
		lastEarningsBar.setVisible(false);
		lastEarningsBar.setVisible(true);
	}
	
	/*���� ��� �迭 �� ����*/
	public void resetRecord() {
		for(i = 0; i<LastRecords.recordsIndex; i++) {
			//record[i] = new LastRecords();
			record[i].setRecord("", 0, 0);

			orderData_last[i][0] = record[i].getDate();
			orderData_last[i][1] = record[i].getDate().equals("")?"":record[i].getEarnings_record()+"��";
			orderData_last[i][2] = record[i].getDate().equals("")?"":record[i].getSales_record()+"��";
		}
		
		
	}
	
	
	/*���� ��� table �� ���ΰ�ħ �޼���*/
	public void showEarnings() {
		selectRecord();
		for(i=0; i<LastRecords.recordsIndex; i++) {
			orderData_last[i][0] = record[i].getDate();
			orderData_last[i][1] = record[i].getDate().equals("")?"":record[i].getEarnings_record()+"��";
			orderData_last[i][2] = record[i].getDate().equals("")?"":record[i].getSales_record()+"��";
		}	
	}
	
	
	/*�Ǹŷ� ���� ������� ����(�ʱ�ȭ) �޼���*/
	public void resetEarnings() {
		Date time = new Date();
		String timeInfo = format2.format(time);
		
		if(record[LastRecords.recordsIndex-1].getDate() != "") { //������ �� �ڸ��� ������
			record[0] = new LastRecords();//������� �Ǿղ� �����ϱ�
			//�׸��� �Ʒ����� �ڵ带 ���ܿ÷��ִ� ���� ������ ������. �ϴ� �����ϱ� ���� �ڸ����� ����� ����!
			LastRecords temp;
			for(i=0; i<LastRecords.recordsIndex-1; i++) {
				if(record[i].getDate().equals("") && record[i+1].getDate().equals("")==false) { //�ٷ� ���� �ε����� ��� �ڿ� ��������
						temp = record[i];
						record[i] = record[i+1];
						record[i+1]=temp;						
				}
			}
			
			arrayLastRecords_DB arrayLastRecord_DB = new arrayLastRecords_DB();
			
		}//end of if
		
		addLastRecords_DB addLastRecords_DB ; 
		//������ �ڸ��� ��� �������, ���� �� �ڸ��� �־��� ���� table���� ���� ������ �� �ε����� ã�� ���� ����� �����Ѵ�.
		for(i=0; i<LastRecords.recordsIndex; i++) {
			if(record[i].getDate().equals("")) {
				/*�ڡڱ�� ����ڡ�*/record[i].setRecord(timeInfo, nowEarnings, nowPays);
				/* ���ʴ�� ��� ������ ����(��¥,�ڵ�), ���� ����, ���� �ֹ��Ǽ� */
				String sid = Integer.toString(i);
				String snowEarnings = Integer.toString(nowEarnings);
				String snowPays = Integer.toString(nowPays);
				addLastRecords_DB = new addLastRecords_DB(sid,timeInfo, snowEarnings, snowPays);
				/* ���� ������� ������ �� ���� ��������� ���ش�. */
				nowEarnings = 0;
				nowPays = 0;
				/*for������ ��������*/break;
			}
		}
		/*���� ��� table ���ΰ�ħ*/showEarnings();
		/*�ð� ��*/resetTime();
		topBar.setVisible(false); topBar.setVisible(true);  
		lastEarningsBar.setVisible(false); lastEarningsBar.setVisible(true); 
	}//end of method 'resetEarnings()'
	
	
	/* �����ֹ����� ���� ǥ�� */
	public void resetTemporder() {
		String tmp = "";
		for(i=0; i<Order.ordermenuLength; i++) {
			if(tempMenu[i]=="") {
				tmp+="";
			}else {
				if(i==Order.ordermenuLength-1) tmp += ", "; 
				tmp += tempMenu[i]+"("+tempQuantity[i]+")";
			}
		}
		payInfoMenu.setText(tmp);
		payInfoPrice.setText(Integer.toString(tempPayprice));
		bottomMain.setVisible(false);
		bottomMain.setVisible(true);
		
		resetTime();
	}
	
	/* setMenuPanel�� ���� �г� ����  */
	public void opensetPanel() {
		reMenuNameField.setText("");
		reMenuPriceField.setText("");
		setIntoPanel.setVisible(true); //�������� �Է� �г� ���̱�
		
		resetTime();
	}
	
	
	/*�ð� ��� �޼���*/
	public void resetTime() {
		Date time = new Date();
		String timeStr = format1.format(time);
		timeField.setText(timeStr);
		topBar.setVisible(false);
		topBar.setVisible(true);
	}
	
	
	public void pullupDBmenu() {
		/* DB �޴� �������� */
		for(i=0; i<menuLength; i++) {
			selectMenu_DB selectMenu_DB = new selectMenu_DB(Integer.toString(i));
		}
		resetingShowingMenu();
	}
	
	
	/* Launch the application. */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CafeTest window = new CafeTest();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		/* �޴� ��ư ���� */
		for(i=0; i<menuLength; i++) {
			MenuBtn[i] = new JButton();
		}
		
		/*��Menu �ν��Ͻ� �迭 ��ü �ʱ�ȭ*/
		for(i=0; i<menuLength; i++) {
			menu[i] = new Menu();
		}
		/*��Order �ν��Ͻ� �迭 ��ü �ʱ�ȭ*/
		for(i=0; i<orderLength; i++) {
			order[i] = new Order();
		}
		
		
		/* �޴� table �� ���ΰ�ħ */
		for(i=0; i<menuLength; i++) {
			menuData[i][0] = menu[i].getMenuName();
			menuData[i][1] = menu[i].getMenuName().equals("")?"":menu[i].getMenuPrice()+"��";
		}
		
		/*������ �Ǹű�� �迭 ��ü �ʱ�ȭ*/
		for(i=0; i<LastRecords.recordsIndex; i++) {
			record[i] = new LastRecords();
		}
		
		/* �ڸ���ȭ�� �ֺ����� ǥ �ʱ�ȭ */
		for(i=0; i<orderLength; i++) {
			orderData[i][0] = "";
			orderData[i][1] = "";
			orderData[i][2] = "";
		}
		
		/*����ȭ�� �ӽ��ֹ����� �ʱ�ȭ*/
		for(i=0; i<Order.ordermenuLength; i++) {
			tempMenu[i] = "";
			tempQuantity[i] = 0;
		}
		tempPayprice = 0;
		
	}
	
	/* Create the application. */
	public CafeTest() {
		Main();
	}
	
	/* Initialize the contents of the frame. */
	private void Main() {
		//�����ͺ��̽� ���̺� ����
		CreateTables_DB CreateTables_DB = new CreateTables_DB();

		/* ������ ���� */
		frame = new JFrame("Dessert Cafe");
		frame.setBounds(100, 100, 956, 659);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		
		/* topBar ��� �г� �Ӽ� */
		topBar.setBackground(new Color(230, 230, 250));
		topBar.setBounds(0, 0, 938, 100);
		frame.getContentPane().add(topBar);
		topBar.setLayout(null);
		topBar.setVisible(true);
		
		/* bottomMain �ϴ� �г� �Ӽ� */
		bottomMain.setBackground(new Color(240, 248, 255));
		bottomMain.setBounds(0, 98, 938, 514);
		frame.getContentPane().add(bottomMain);
		bottomMain.setLayout(null);
		bottomMain.add(table);
		bottomMain.setVisible(true);
		
		/* bottomMain �� �ֹ����� table �Ӽ� */
		table.setRowSelectionAllowed(false);
		JScrollPane scrollPane = new JScrollPane(table);
		table.getTableHeader().setReorderingAllowed(false);		
		scrollPane.setEnabled(false);
		scrollPane.setBounds(28, 22, 322, 334);
		scrollPane.setViewportBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(218, 112, 214)));
		bottomMain.add(scrollPane);
		
		/* bottomMain ���� -  �ֹ��� �̷����� �г� ����� �Ӽ� ���� */
		square.setBackground(Color.WHITE);
		square.setBorder(new LineBorder(new Color(128, 128, 128)));
		square.setBounds(364, 22, 546, 467);
		bottomMain.add(square);
		square.setLayout(null);
		JLabel label = new JLabel("\uBA54\uB274 \uC8FC\uBB38 - \uC120\uD0DD \uAC00\uB2A5");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("����", Font.PLAIN, 15));
		label.setBounds(17, 12, 153, 18);
		square.add(label);
		JLabel lblNewLabel = new JLabel("\uB2E8\uC704:\uB0B1\uAC1C(\uC794/\uAC1C)");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("����", Font.PLAIN, 15));
		lblNewLabel.setBounds(407, 12, 125, 18);
		square.add(lblNewLabel);
		

		
		
		/* �ֹ� ���� ǥ�ö� �󺧰� �ؽ�Ʈ�ʵ� */
		JLabel payLabel = new JLabel("\uC8FC\uBB38/\uACB0\uC81C \uC815\uBCF4");
		payLabel.setBackground(new Color(255, 228, 225));
		payLabel.setFont(new Font("����", Font.BOLD, 15));
		payLabel.setBounds(7, 439, 110, 18);
		square.add(payLabel);
		/*�޴� �ʵ� ����*/payInfoMenu = new JTextField();
		payInfoMenu.setBackground(Color.WHITE);
		payInfoMenu.setEditable(false);
		payInfoMenu.setFont(new Font("����", Font.PLAIN, 15));
		payInfoMenu.setBounds(115, 430, 238, 37);
		square.add(payInfoMenu);
		payInfoMenu.setColumns(10);
		/*���� �ʵ� ����*/payInfoPrice = new JTextField();
		payInfoPrice.setBackground(Color.WHITE);
		payInfoPrice.setHorizontalAlignment(SwingConstants.RIGHT);
		payInfoPrice.setEditable(false);
		payInfoPrice.setFont(new Font("����", Font.PLAIN, 15));
		payInfoPrice.setBounds(350, 430, 73, 37);
		square.add(payInfoPrice);
		payInfoPrice.setColumns(10);
		
		
		/* ���� ��ư */
		JButton btn_Pay = new JButton("\uACB0\uC81C");
		btn_Pay.setBackground(new Color(255, 140, 0));
		btn_Pay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/* ���� */
				if(tempMenu[0].equals("")==false) { //ù��° �޴����ù迭�ε����� ����ִ��� Ȯ���Ͽ� �ֹ��� ������ �ִ��� Ȯ��.
					payOrder();
				}else {
					JOptionPane.showMessageDialog(null, "�ֹ��� ������ �����ϴ�.");
				}
				/*�ð� ���ΰ�ħ*/resetTime();
			}
		});
		btn_Pay.setFont(new Font("����", Font.PLAIN, 14));
		btn_Pay.setBounds(421, 430, 65, 36);
		square.add(btn_Pay);
		
		
		JLabel label03 = new JLabel("�ֹ� ���� ���");
		label03.setFont(new Font("����", Font.PLAIN, 16));
		
		/* �ֹ� ��� ��ư */
		JButton btn_Cancle = new JButton("\uCDE8\uC18C");
		btn_Cancle.setBackground(new Color(238, 232, 170));
		btn_Cancle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tempMenu[0].equals("")==false) { //�ֹ��ϴ� ������ �������� 
					cancelOrder();
					JOptionPane.showMessageDialog(null, label03,"",JOptionPane.ERROR_MESSAGE);
				}
				/*�ð� ��*/resetTime();
			}
		});
		btn_Cancle.setFont(new Font("����", Font.PLAIN, 14));
		btn_Cancle.setBounds(485, 430, 62, 36);
		square.add(btn_Cancle);
		
		
		
		/* �޴� ��ư ���� */
		MenuBtn[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(menu[0].getMenuName().equals("")==false) { //�޴��� ������� ������ �ӽ��ֹ������� ����
					pickMenu(0);
				}
				
			}
		});
		MenuBtn[0].setBackground(new Color(255, 192, 203));
		MenuBtn[0].setFont(new Font("����", Font.BOLD, 16));
		MenuBtn[0].setBounds(17, 45, 164, 79);
		square.add(MenuBtn[0]);
		
		MenuBtn[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(menu[1].getMenuName().equals("")==false) { //�޴��� ������� ������ �ӽ��ֹ������� ����
					pickMenu(1);
				}
			}
		});
		MenuBtn[1].setBackground(new Color(255, 192, 203));
		MenuBtn[1].setFont(new Font("����", Font.BOLD, 16));
		MenuBtn[1].setBounds(191, 45, 164, 79);
		square.add(MenuBtn[1]);
		
		MenuBtn[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(menu[2].getMenuName().equals("")==false) { //�޴��� ������� ������ �ӽ��ֹ������� ����
					pickMenu(2);
				}
			}
		});
		MenuBtn[2].setBackground(new Color(255, 192, 203));
		MenuBtn[2].setFont(new Font("����", Font.BOLD, 16));
		MenuBtn[2].setBounds(365, 45, 164, 79);
		square.add(MenuBtn[2]);
		
		MenuBtn[3].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(menu[3].getMenuName().equals("")==false) { //�޴��� ������� ������ �ӽ��ֹ������� ����
					pickMenu(3);
				}
			}
		});
		MenuBtn[3].setBackground(new Color(255, 192, 203));
		MenuBtn[3].setFont(new Font("����", Font.BOLD, 15));
		MenuBtn[3].setBounds(17, 136, 164, 79);
		square.add(MenuBtn[3]);
		MenuBtn[4].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(menu[4].getMenuName().equals("")==false) { //�޴��� ������� ������ �ӽ��ֹ������� ����
					pickMenu(4);
				}
			}
		});		
		MenuBtn[4].setBackground(new Color(255, 192, 203));
		MenuBtn[4].setFont(new Font("����", Font.BOLD, 15));
		MenuBtn[4].setBounds(191, 136, 164, 79);
		square.add(MenuBtn[4]);
		
		MenuBtn[5].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(menu[5].getMenuName().equals("")==false) { //�޴��� ������� ������ �ӽ��ֹ������� ����
					pickMenu(5);
				}
			}
		});		
		MenuBtn[5].setBackground(new Color(255, 192, 203));
		MenuBtn[5].setFont(new Font("����", Font.BOLD, 15));
		MenuBtn[5].setBounds(365, 136, 164, 79);
		square.add(MenuBtn[5]);
		
		MenuBtn[6].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(menu[6].getMenuName().equals("")==false) { //�޴��� ������� ������ �ӽ��ֹ������� ����
					pickMenu(6);
				}
			}
		});
		
		MenuBtn[6].setBackground(new Color(255, 192, 203));
		MenuBtn[6].setFont(new Font("����", Font.BOLD, 15));
		MenuBtn[6].setBounds(17, 227, 164, 79);
		square.add(MenuBtn[6]);
		
		MenuBtn[7].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(menu[7].getMenuName().equals("")==false) { //�޴��� ������� ������ �ӽ��ֹ������� ����
					pickMenu(7);
				}
			}
		});
		MenuBtn[7].setBackground(new Color(255, 192, 203));
		MenuBtn[7].setFont(new Font("����", Font.BOLD, 15));
		MenuBtn[7].setBounds(191, 227, 164, 79);
		square.add(MenuBtn[7]);
		
		MenuBtn[8].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(menu[8].getMenuName().equals("")==false) { //�޴��� ������� ������ �ӽ��ֹ������� ����
					pickMenu(8);
				}
			}
		});		
		MenuBtn[8].setBackground(new Color(255, 192, 203));
		MenuBtn[8].setFont(new Font("����", Font.BOLD, 15));
		MenuBtn[8].setBounds(365, 227, 164, 79);
		square.add(MenuBtn[8]);
		
		MenuBtn[9].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(menu[9].getMenuName().equals("")==false) { //�޴��� ������� ������ �ӽ��ֹ������� ����
					pickMenu(9);
				}
			}
		});
		
		MenuBtn[9].setBackground(new Color(255, 192, 203));
		MenuBtn[9].setFont(new Font("����", Font.BOLD, 15));
		MenuBtn[9].setBounds(17, 318, 164, 79);
		square.add(MenuBtn[9]);
		
		MenuBtn[10].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(menu[10].getMenuName().equals("")==false) { //�޴��� ������� ������ �ӽ��ֹ������� ����
					pickMenu(10);
				}
			}
		});	
		MenuBtn[10].setBackground(new Color(255, 192, 203));
		MenuBtn[10].setFont(new Font("����", Font.BOLD, 15));
		MenuBtn[10].setBounds(191, 318, 164, 79);
		square.add(MenuBtn[10]);
		
		MenuBtn[11].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(menu[11].getMenuName().equals("")==false) { //�޴��� ������� ������ �ӽ��ֹ������� ����
					pickMenu(11);
				}
			}
		});		
		MenuBtn[11].setBackground(new Color(255, 192, 203));
		MenuBtn[11].setFont(new Font("����", Font.BOLD, 15));
		MenuBtn[11].setBounds(365, 318, 164, 79);
		square.add(MenuBtn[11]);
	
		
		/* �޴� �߰� �г� ����� �Ӽ� */
		JPanel addMenuBar = new JPanel();
		addMenuBar.setBorder(new LineBorder(Color.GRAY));
		addMenuBar.setBackground(Color.WHITE);
		addMenuBar.setBounds(0, 98, 938, 514);
		frame.getContentPane().add(addMenuBar);
		addMenuBar.setLayout(null);
		JLabel label_1 = new JLabel("\uBA54\uB274 \uCD94\uAC00");
		label_1.setFont(new Font("����", Font.BOLD, 18));
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(403, 12, 114, 31);
		addMenuBar.add(label_1);
		addMenuBar.setVisible(false);
		/*������ҵ�*/
		JLabel label1_addMenu = new JLabel("\uC0C8 \uBA54\uB274 \uC774\uB984");
		label1_addMenu.setHorizontalAlignment(SwingConstants.CENTER);
		label1_addMenu.setFont(new Font("����", Font.PLAIN, 18));
		label1_addMenu.setBounds(219, 110, 120, 31);
		addMenuBar.add(label1_addMenu);
		JLabel label_2 = new JLabel("\uAC00\uACA9");
		label_2.setFont(new Font("����", Font.PLAIN, 18));
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setBounds(282, 167, 62, 18);
		addMenuBar.add(label_2);
		/*�޴��̸�*/str_newMenuName = new JTextField();
		str_newMenuName.setFont(new Font("����", Font.PLAIN, 18));
		str_newMenuName.setBounds(347, 108, 209, 37);
		addMenuBar.add(str_newMenuName);
		str_newMenuName.setColumns(10);
		/*�޴� ����*/int_newMenuPrice = new JTextField();
		int_newMenuPrice.setFont(new Font("����", Font.PLAIN, 18));
		int_newMenuPrice.setColumns(10);
		int_newMenuPrice.setBounds(347, 159, 209, 37);
		addMenuBar.add(int_newMenuPrice);
		JLabel label3_addMenu = new JLabel("\uAC00\uACA9\uC740 \uC22B\uC790\uB9CC \uC785\uB825\uD558\uC138\uC694.");
		label3_addMenu.setFont(new Font("����", Font.PLAIN, 13));
		label3_addMenu.setBounds(285, 207, 260, 18);
		addMenuBar.add(label3_addMenu);
		label3_addMenu.setForeground(new Color(205, 92, 92));
		
		/*�� �޴� ��Ϲ�ư*/
		JButton setIntoMenu_btn = new JButton("\uB4F1\uB85D\uD558\uAE30");
		setIntoMenu_btn.setBackground(new Color(216, 191, 216));
		setIntoMenu_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JLabel label101 = new JLabel("�޴� �̸����� ������ �� �� �����ϴ�.");
				label101.setFont(new Font("����", Font.PLAIN, 16));
				label101.setForeground(Color.RED);
				
				/*�޴� �Է� ��������� ���â �߻�*/
				if(str_newMenuName.getText().equals("")) {
					JOptionPane.showMessageDialog(null, label101,"�Է� ����",JOptionPane.WARNING_MESSAGE); 
					return;
				}
				
				JLabel label04 = new JLabel("�Էµ� ������ �����ϴ�.");
				label04.setFont(new Font("����", Font.PLAIN, 16));
				label04.setForeground(Color.RED);
				
				/*���� ���Է½� ���â �߻�*/
				if(int_newMenuPrice.getText().equals("")) {
					JOptionPane.showMessageDialog(null, label04,"�Է� ����",JOptionPane.WARNING_MESSAGE); 
					int_newMenuPrice.setText(""); 
					return;
				}
				
				JLabel label05 = new JLabel("������ ���ڷθ� �Է��ϼ���.");
				label05.setFont(new Font("����", Font.PLAIN, 16));
				label05.setForeground(Color.RED);
				
				/*�Է¹��� ������ ���ڰ� �ƴ� ��� ���â �߻�.*/
				char tmp;
				for(i=0; i<int_newMenuPrice.getText().length(); i++) {
					tmp = int_newMenuPrice.getText().charAt(i);
					if(Character.isDigit(tmp)==false) {
						JOptionPane.showMessageDialog(null, label05,"�Է� ����",JOptionPane.WARNING_MESSAGE);
						int_newMenuPrice.setText("");
						return;
					}
				}
				
				/* if ��� ������ ������ �޴� �߰� */
				addMenu(str_newMenuName.getText(), Integer.parseInt(int_newMenuPrice.getText()));
				str_newMenuName.setText("");
				int_newMenuPrice.setText("");
			}
		});
		setIntoMenu_btn.setFont(new Font("����", Font.PLAIN, 17));
		setIntoMenu_btn.setBounds(580, 107, 129, 89);
		addMenuBar.add(setIntoMenu_btn);
		
		
		JLabel label_4 = new JLabel("\uBA54\uB274 \uC774\uB984\uC740 8\uC790 \uC774\uD558\uB97C \uAD8C\uC7A5\uD569\uB2C8\uB2E4.");
		label_4.setForeground(new Color(205, 92, 92));
		label_4.setFont(new Font("����", Font.PLAIN, 13));
		label_4.setBounds(285, 225, 234, 18);
		addMenuBar.add(label_4);
		
		
		/* �޴� ���� �г� �Ӽ� */
		deleteMenuBar.setBackground(Color.WHITE);
		deleteMenuBar.setBounds(0, 98, 938, 514);
		frame.getContentPane().add(deleteMenuBar);
		deleteMenuBar.setLayout(null);
		JLabel label_3 = new JLabel("\uBA54\uB274 \uC0AD\uC81C");
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setFont(new Font("����", Font.BOLD, 18));
		label_3.setBounds(403, 12, 114, 31);
		deleteMenuBar.add(label_3);
		deleteMenuBar.setVisible(false);		
		
		
		/* deleteMenuBar�� �޴� ��� table */
		JScrollPane scrollPane_3 = new JScrollPane(table_menu2);
		scrollPane_3.setEnabled(false);
		scrollPane_3.setBounds(69, 55, 348, 423);
		deleteMenuBar.add(scrollPane_3);
		
		
		/* deleteMenuBar�� �޴� ���� ��ư */
		JButton delete_btn01 = new JButton("\uC0AD\uC81C");
		delete_btn01.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				deleteMenuin(0);
			}
		});
		delete_btn01.setFont(new Font("����", Font.PLAIN, 16));
		delete_btn01.setBackground(new Color(250, 128, 114));
		delete_btn01.setBounds(420, 82, 73, 28);
		deleteMenuBar.add(delete_btn01);
		
		JButton delete_btn02 = new JButton("\uC0AD\uC81C");
		delete_btn02.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteMenuin(1);
			}
		});
		delete_btn02.setFont(new Font("����", Font.PLAIN, 16));
		delete_btn02.setBackground(new Color(250, 128, 114));
		delete_btn02.setBounds(420, 114, 73, 28);
		deleteMenuBar.add(delete_btn02);
		
		JButton delete_btn03 = new JButton("\uC0AD\uC81C");
		delete_btn03.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteMenuin(2);
			}
		});
		delete_btn03.setFont(new Font("����", Font.PLAIN, 16));
		delete_btn03.setBackground(new Color(250, 128, 114));
		delete_btn03.setBounds(420, 147, 73, 28);
		deleteMenuBar.add(delete_btn03);
		
		JButton delete_btn04 = new JButton("\uC0AD\uC81C");
		delete_btn04.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteMenuin(3);
			}
		});
		delete_btn04.setFont(new Font("����", Font.PLAIN, 16));
		delete_btn04.setBackground(new Color(250, 128, 114));
		delete_btn04.setBounds(420, 180, 73, 28);
		deleteMenuBar.add(delete_btn04);
		
		JButton delete_btn05 = new JButton("\uC0AD\uC81C");
		delete_btn05.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteMenuin(4);
			}
		});
		delete_btn05.setFont(new Font("����", Font.PLAIN, 16));
		delete_btn05.setBackground(new Color(250, 128, 114));
		delete_btn05.setBounds(420, 212, 73, 28);
		deleteMenuBar.add(delete_btn05);
		
		JButton delete_btn06 = new JButton("\uC0AD\uC81C");
		delete_btn06.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteMenuin(5);
			}
		});
		delete_btn06.setFont(new Font("����", Font.PLAIN, 16));
		delete_btn06.setBackground(new Color(250, 128, 114));
		delete_btn06.setBounds(420, 245, 73, 28);
		deleteMenuBar.add(delete_btn06);
		
		JButton delete_btn07 = new JButton("\uC0AD\uC81C");
		delete_btn07.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteMenuin(6);
			}
		});
		delete_btn07.setFont(new Font("����", Font.PLAIN, 16));
		delete_btn07.setBackground(new Color(250, 128, 114));
		delete_btn07.setBounds(420, 277, 73, 28);
		deleteMenuBar.add(delete_btn07);
		
		JButton delete_btn08 = new JButton("\uC0AD\uC81C");
		delete_btn08.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteMenuin(7);
			}
		});
		delete_btn08.setFont(new Font("����", Font.PLAIN, 16));
		delete_btn08.setBackground(new Color(250, 128, 114));
		delete_btn08.setBounds(420, 311, 73, 28);
		deleteMenuBar.add(delete_btn08);
		
		JButton delete_btn09 = new JButton("\uC0AD\uC81C");
		delete_btn09.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteMenuin(8);
			}
		});
		delete_btn09.setFont(new Font("����", Font.PLAIN, 16));
		delete_btn09.setBackground(new Color(250, 128, 114));
		delete_btn09.setBounds(420, 344, 73, 28);
		deleteMenuBar.add(delete_btn09);
		
		JButton delete_btn10 = new JButton("\uC0AD\uC81C");
		delete_btn10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteMenuin(9);
			}
		});
		delete_btn10.setFont(new Font("����", Font.PLAIN, 16));
		delete_btn10.setBackground(new Color(250, 128, 114));
		delete_btn10.setBounds(420, 376, 73, 28);
		deleteMenuBar.add(delete_btn10);
		
		JButton delete_btn11 = new JButton("\uC0AD\uC81C");
		delete_btn11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteMenuin(10);
			}
		});
		delete_btn11.setFont(new Font("����", Font.PLAIN, 16));
		delete_btn11.setBackground(new Color(250, 128, 114));
		delete_btn11.setBounds(420, 409, 73, 28);
		deleteMenuBar.add(delete_btn11);
		
		JButton delete_btn12 = new JButton("\uC0AD\uC81C");
		delete_btn12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteMenuin(11);
			}
		});
		delete_btn12.setFont(new Font("����", Font.PLAIN, 16));
		delete_btn12.setBackground(new Color(250, 128, 114));
		delete_btn12.setBounds(420, 442, 73, 28);
		deleteMenuBar.add(delete_btn12);

		
		/* �޴� ���� �г� �Ӽ� */
		JPanel setMenuBar = new JPanel();
		setMenuBar.setBackground(Color.WHITE);
		setMenuBar.setBounds(0, 98, 938, 514);
		frame.getContentPane().add(setMenuBar);
		setMenuBar.setLayout(null);
		JLabel label_setMenu = new JLabel("\uBA54\uB274 \uC218\uC815");
		label_setMenu.setBounds(403, 12, 114, 31);
		label_setMenu.setFont(new Font("����", Font.BOLD, 18));
		label_setMenu.setHorizontalAlignment(SwingConstants.CENTER);
		setMenuBar.add(label_setMenu);
		setMenuBar.setVisible(false);
		
		
		/* addMenuBar�г� �� '���ư���' ��ư */
		JButton backto_addMenu = new JButton("\uB3CC\uC544\uAC00\uAE30");
		backto_addMenu.setBackground(new Color(238, 232, 170));
		backto_addMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				str_newMenuName.setText("");
				int_newMenuPrice.setText("");
				addMenuBar.setVisible(false);
				topBar.setVisible(true);
				bottomMain.setVisible(true);
				setMenuBar.setVisible(false);
				deleteMenuBar.setVisible(false);
				lastEarningsBar.setVisible(false);
				/*�ð� ��*/resetTime();
				resetingShowingMenu();
			}
		});
		backto_addMenu.setFont(new Font("����", Font.PLAIN, 17));
		backto_addMenu.setBounds(819, 16, 105, 27);
		addMenuBar.add(backto_addMenu);
		
		
		/* �޴� ���� �г� �� ���ø޴� ���� �г�*/
		setIntoPanel.setBackground(new Color(240, 248, 255));
		setIntoPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(218, 112, 214)));
		setIntoPanel.setBounds(515, 150, 380, 200);
		setMenuBar.add(setIntoPanel);
		setIntoPanel.setLayout(null);
		setIntoPanel.setVisible(false);
		
		
		/* �޴� �ε��������� ���� ��ư */
		JButton setButton01 = new JButton("����");
		setButton01.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(menu[0].getMenuName().equals("")) { //�� �޴��� �����ϸ� �ƹ��͵� �������� �ʴ´�.
					setIntoPanel.setVisible(false);
					return;
				}else { //�޴� ������ ����
					numtemp=0; //�ش� �޴��� �ε������� ����.
					opensetPanel();
				}
			}
		});
		setButton01.setFont(new Font("����", Font.PLAIN, 16));
		setButton01.setBackground(new Color(255, 222, 173));
		setButton01.setBounds(420, 82, 69, 28);
		setMenuBar.add(setButton01);
		
		JButton setButton02 = new JButton("����");
		setButton02.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(menu[1].getMenuName().equals("")) {
					setIntoPanel.setVisible(false);
					return;
				}else {
					numtemp=1;
					opensetPanel();
				}
			}
		});
		setButton02.setFont(new Font("����", Font.PLAIN, 16));
		setButton02.setBackground(new Color(255, 222, 173));
		setButton02.setBounds(420, 114, 69, 28);
		setMenuBar.add(setButton02);
		
		JButton setButton03 = new JButton("����");
		setButton03.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(menu[2].getMenuName().equals("")) {
					setIntoPanel.setVisible(false);
					return;
				}else {
					numtemp=2;
					opensetPanel();
				}
			}
		});
		setButton03.setFont(new Font("����", Font.PLAIN, 16));
		setButton03.setBackground(new Color(255, 222, 173));
		setButton03.setBounds(420, 147, 69, 28);
		setMenuBar.add(setButton03);
		
		JButton setButton04 = new JButton("����");
		setButton04.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(menu[3].getMenuName().equals("")) {
					setIntoPanel.setVisible(false);
					return;
				}else {
					numtemp=3;
					opensetPanel();
				}
			}
		});
		setButton04.setFont(new Font("����", Font.PLAIN, 16));
		setButton04.setBackground(new Color(255, 222, 173));
		setButton04.setBounds(420, 180, 69, 28);
		setMenuBar.add(setButton04);
		
		JButton setButton05 = new JButton("����");
		setButton05.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(menu[4].getMenuName().equals("")) {
					setIntoPanel.setVisible(false);
					return;
				}else {
					numtemp=4;
					opensetPanel();
				}
			}
		});
		setButton05.setFont(new Font("����", Font.PLAIN, 16));
		setButton05.setBackground(new Color(255, 222, 173));
		setButton05.setBounds(420, 212, 69, 28);
		setMenuBar.add(setButton05);
		
		JButton setButton06 = new JButton("����");
		setButton06.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(menu[5].getMenuName().equals("")) {
					setIntoPanel.setVisible(false);
					return;
				}else {
					numtemp=5;
					opensetPanel();
				}
			}
		});
		setButton06.setFont(new Font("����", Font.PLAIN, 16));
		setButton06.setBackground(new Color(255, 222, 173));
		setButton06.setBounds(420, 245, 69, 28);
		setMenuBar.add(setButton06);
		
		JButton setButton07 = new JButton("����");
		setButton07.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(menu[6].getMenuName().equals("")) {
					setIntoPanel.setVisible(false);
					return;
				}else {
					numtemp=6;
					opensetPanel();
				}
			}
		});
		setButton07.setFont(new Font("����", Font.PLAIN, 16));
		setButton07.setBackground(new Color(255, 222, 173));
		setButton07.setBounds(420, 277,69, 28);
		setMenuBar.add(setButton07);
		
		JButton setButton08 = new JButton("����");
		setButton08.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(menu[7].getMenuName().equals("")) {
					setIntoPanel.setVisible(false);
					return;
				}else {
					numtemp=7;
					opensetPanel();
				}
			}
		});
		setButton08.setFont(new Font("����", Font.PLAIN, 16));
		setButton08.setBackground(new Color(255, 222, 173));
		setButton08.setBounds(420, 311, 69, 28);
		setMenuBar.add(setButton08);
		
		JButton setButton09 = new JButton("����");
		setButton09.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(menu[8].getMenuName().equals("")) {
					setIntoPanel.setVisible(false);
					return;
				}else {
					numtemp=8;
					opensetPanel();
				}
			}
		});
		setButton09.setFont(new Font("����", Font.PLAIN, 16));
		setButton09.setBackground(new Color(255, 222, 173));
		setButton09.setBounds(420, 344, 69, 28);
		setMenuBar.add(setButton09);
		
		JButton setButton10 = new JButton("����");
		setButton10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(menu[9].getMenuName().equals("")) {
					setIntoPanel.setVisible(false);
					return;
				}else {
					numtemp=9;
					opensetPanel();
				}
			}
		});
		setButton10.setFont(new Font("����", Font.PLAIN, 16));
		setButton10.setBackground(new Color(255, 222, 173));
		setButton10.setBounds(420, 376, 69, 28);
		setMenuBar.add(setButton10);
		
		JButton setButton11 = new JButton("����");
		setButton11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(menu[10].getMenuName().equals("")) {
					setIntoPanel.setVisible(false);
					return;
				}else {
					numtemp=10;
					opensetPanel();
				}
			}
		});
		setButton11.setFont(new Font("����", Font.PLAIN, 16));
		setButton11.setBackground(new Color(255, 222, 173));
		setButton11.setBounds(420, 409, 69, 28);
		setMenuBar.add(setButton11);
		
		JButton setButton12 = new JButton("����");
		setButton12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(menu[11].getMenuName().equals("")) {
					setIntoPanel.setVisible(false);
					return;
				}else {
					numtemp=11;
					opensetPanel();
				}
			}
		});
		setButton12.setFont(new Font("����", Font.PLAIN, 16));
		setButton12.setBackground(new Color(255, 222, 173));
		setButton12.setBounds(420, 442, 69, 28);
		setMenuBar.add(setButton12);
		
		
		/*table �ϺμӼ�*/
		DefaultTableCellRenderer celAlignCenter = new DefaultTableCellRenderer();
		celAlignCenter.setHorizontalAlignment(JLabel.CENTER);
		DefaultTableCellRenderer celAlignRight = new DefaultTableCellRenderer();
		celAlignRight.setHorizontalAlignment(JLabel.RIGHT);

		
		
		/* �޴� table �Ӽ� */
		table_menu2.setPreferredScrollableViewportSize(new Dimension(284, 348));
		table_menu2.setFillsViewportHeight(true);
		table_menu2.getColumn("�޴� �̸�").setCellRenderer(celAlignCenter); //align
		table_menu2.getColumn("�޴� �̸�").setPreferredWidth(150); //width 
		table_menu2.getColumn("����").setCellRenderer(celAlignRight);
		table_menu2.getColumn("����").setPreferredWidth(7);
		table_menu2.setRowHeight(33);
		table_menu2.setEnabled(false);
		table_menu2.setBounds(91, 68, 723, 409);
		table_menu2.getTableHeader().setReorderingAllowed(false);
		table_menu2.setForeground(new Color(128, 0, 128));
		table_menu2.setFont(new Font("����", Font.PLAIN, 17));
		
		
		/* �ֹ����� table �Ӽ� */
		table.setEnabled(false);
		table.setBounds(2, 33, 50, 359);
		table.setForeground(new Color(128, 0, 128));
		table.setFont(new Font("����", Font.PLAIN, 15));
		table.setPreferredScrollableViewportSize(new Dimension(284, 348));
		table.getColumn("��ȣ").setPreferredWidth(5); //width
		table.getColumn("��ȣ").setCellRenderer(celAlignCenter); //align
		table.getColumn("�ֹ� �޴�").setPreferredWidth(200); //width 
		table.getColumn("���� �ݾ�").setPreferredWidth(55); //width
		table.getColumn("���� �ݾ�").setCellRenderer(celAlignRight); //align
		table.setRowHeight(27);
		
		/* ������� �г� �Ӽ� */
		lastEarningsBar.setBackground(Color.WHITE);
		lastEarningsBar.setBounds(0, 98, 938, 514);
		frame.getContentPane().add(lastEarningsBar);
		lastEarningsBar.setLayout(null);
		JLabel label_laseEarnings = new JLabel("\uC9C0\uB09C \uD310\uB9E4\uAE30\uB85D [DATABASE]");
		label_laseEarnings.setHorizontalAlignment(SwingConstants.CENTER);
		label_laseEarnings.setFont(new Font("����", Font.BOLD, 17));
		label_laseEarnings.setBounds(355, 12, 243, 40);
		lastEarningsBar.add(label_laseEarnings);
		lastEarningsBar.setVisible(false);
		
		/*Table �޴� ���*/

		
		
		/* ���� �Ǹű�� table �Ӽ�*/
		table_last.getTableHeader().setReorderingAllowed(false);
		table_last.setForeground(new Color(128, 0, 128));
		table_last.setFont(new Font("����", Font.PLAIN, 17));
		table_last.setPreferredScrollableViewportSize(new Dimension(284, 348));
		table_last.setFillsViewportHeight(true);
		table_last.getColumn("��¥").setPreferredWidth(10); //width
		table_last.getColumn("��¥").setCellRenderer(celAlignCenter); //align
		table_last.getColumn("�Ǹż���").setPreferredWidth(150); //width 
		table_last.getColumn("�Ǹż���").setCellRenderer(celAlignRight);
		table_last.getColumn("�Ǹ��ֹ���").setPreferredWidth(50); //width
		table_last.getColumn("�Ǹ��ֹ���").setCellRenderer(celAlignRight); //align
		table_last.setRowHeight(27);
		table_last.setEnabled(false);
		table_last.setBounds(91, 68, 723, 409);
		lastEarningsBar.add(table_last);
		
		/*�޴� table �Ӽ�*/
		table_menu.setPreferredScrollableViewportSize(new Dimension(284, 348));
		table_menu.setFillsViewportHeight(true);
		table_menu.getColumn("�޴� �̸�").setCellRenderer(celAlignCenter); //align
		table_menu.getColumn("�޴� �̸�").setPreferredWidth(150); //width 
		table_menu.getColumn("����").setCellRenderer(celAlignRight);
		table_menu.getColumn("����").setPreferredWidth(7);
		table_menu.setRowHeight(33);
		table_menu.setEnabled(false);
		table_menu.setBounds(91, 68, 723, 409);
		table_menu.getTableHeader().setReorderingAllowed(false);
		table_menu.setForeground(new Color(128, 0, 128));
		table_menu.setFont(new Font("����", Font.PLAIN, 17));
		
		
		/* ���� �Ǹű�� ǥ ��ũ�� */
		JScrollPane scrollPane_1 = new JScrollPane(table_last);
		scrollPane_1.setEnabled(false);
		scrollPane_1.setBounds(105, 68, 723, 409);
		lastEarningsBar.add(scrollPane_1);
		
		
		/* ���� �Ǹű���� �����ִ� ��ư */
		JButton ShowLastEarnings = new JButton("\uC9C0\uB09C \uD310\uB9E4\uAE30\uB85D \uBCF4\uAE30");
		ShowLastEarnings.setBackground(new Color(216, 191, 216));
		ShowLastEarnings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				/* ��� ���� table�� �ҷ����� */selectRecord();
				showEarnings();
				addMenuBar.setVisible(false);
				topBar.setVisible(true);
				bottomMain.setVisible(false);
				lastEarningsBar.setVisible(false);
				lastEarningsBar.setVisible(true);
				deleteMenuBar.setVisible(false);
				setMenuBar.setVisible(false);
				resetTime();
			}
		});
		ShowLastEarnings.setFont(new Font("����", Font.PLAIN, 17));
		ShowLastEarnings.setBounds(14, 13, 204, 32);
		topBar.add(ShowLastEarnings);
		

		/* �����Ǹű�� â���� ���ư��� ��ư */
		JButton backto_laseEarnings = new JButton("\uB3CC\uC544\uAC00\uAE30");
		backto_laseEarnings.setBackground(new Color(238, 232, 170));
		backto_laseEarnings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*�ð� ��*/resetTime();
				addMenuBar.setVisible(false);
				topBar.setVisible(true);
				bottomMain.setVisible(true);
				lastEarningsBar.setVisible(false);
				setMenuBar.setVisible(false);
				deleteMenuBar.setVisible(false);
			}
		});
		backto_laseEarnings.setFont(new Font("����", Font.PLAIN, 17));
		backto_laseEarnings.setBounds(819, 20, 105, 27);
		lastEarningsBar.add(backto_laseEarnings);
		
		
		JLabel ques = new JLabel("���� ����� ��� �����Ͻðڽ��ϱ�?");
		ques.setFont(new Font("����", Font.PLAIN, 16));
		
		/*���� ��� ��� �ʱ�ȭ ��ư*/
		JButton deleteall = new JButton("\uBAA8\uB4E0 \uAE30\uB85D \uCD08\uAE30\uD654");
		deleteall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int result = JOptionPane.showConfirmDialog(null, ques, "����� ���� ���", JOptionPane.YES_NO_OPTION);
				if(result==JOptionPane.YES_OPTION) {
					resetRecord();
					resetRecords_DB resetrc = new resetRecords_DB();
					JOptionPane.showMessageDialog(null, "��� ����� �����߽��ϴ�.", "���� ��� �ʱ�ȭ", JOptionPane.INFORMATION_MESSAGE);
				}
				lastEarningsBar.setVisible(false); lastEarningsBar.setVisible(true);
			}
		});
		deleteall.setFont(new Font("����", Font.PLAIN, 17));
		deleteall.setBackground(SystemColor.control);
		deleteall.setBounds(17, 20, 180, 27);
		lastEarningsBar.add(deleteall);
		
		/* �޴����� â���� ���ư��� ��ư */
		JButton backto_setMenu = new JButton("\uB3CC\uC544\uAC00\uAE30");
		backto_setMenu.setBackground(new Color(238, 232, 170));
		backto_setMenu.setBounds(819, 16, 105, 27);
		backto_setMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				resetingShowingMenu();
				addMenuBar.setVisible(false);
				topBar.setVisible(true);
				bottomMain.setVisible(true);
				lastEarningsBar.setVisible(false);
				setMenuBar.setVisible(false);
				deleteMenuBar.setVisible(false);
				
				/*�ð� ��*/resetTime();
			}
		});
		backto_setMenu.setFont(new Font("����", Font.PLAIN, 17));
		setMenuBar.add(backto_setMenu);
		
		
		/* �޴� ���� â���� ���ư��� ��ư */
		JButton backto_deleteMenu = new JButton("\uB3CC\uC544\uAC00\uAE30");
		backto_deleteMenu.setBackground(new Color(238, 232, 170));
		backto_deleteMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetingShowingMenu();
				addMenuBar.setVisible(false);
				topBar.setVisible(true);
				bottomMain.setVisible(true);
				lastEarningsBar.setVisible(false);
				deleteMenuBar.setVisible(false);
				setMenuBar.setVisible(false);
				resetTime();
			}
		});
		backto_deleteMenu.setFont(new Font("����", Font.PLAIN, 17));
		backto_deleteMenu.setBounds(819, 16, 105, 27);
		deleteMenuBar.add(backto_deleteMenu);
		
		/* �޴� ����â�� �� */
		JLabel label_5 = new JLabel("\uBA54\uB274 \uC774\uB984\uC740 8\uC790 \uC774\uD558\uB97C \uAD8C\uC7A5\uD569\uB2C8\uB2E4.");
		label_5.setForeground(new Color(205, 92, 92));
		label_5.setFont(new Font("����", Font.PLAIN, 13));
		label_5.setBounds(20, 145, 232, 26);
		setIntoPanel.add(label_5);
		
		/* �޴� ����â�� '�����ϱ�' ��ư */
		JButton reMenuBtn = new JButton("\uC218\uC815\uD558\uAE30");
		reMenuBtn.setBackground(new Color(216, 191, 216));
		reMenuBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				/*�޴� ���� ���� ���� ���� �Է� ���� �˻�*/
				if(reMenuNameField.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "�Էµ� �޴� �̸��� �����ϴ�.","�Է� ����",JOptionPane.WARNING_MESSAGE);  
					return;
				}
				
				/*���� �Է� ���������*/
				if(reMenuPriceField.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "�Էµ� ������ �����ϴ�.","�Է� ����",JOptionPane.WARNING_MESSAGE); 
					int_newMenuPrice.setText(""); 
					return;
				}
				
				/*�Է¹��� ������ ���ڰ� �´��� �Ǻ�*/
				char tmp;
				for(i=0; i<reMenuPriceField.getText().length(); i++) {
					tmp = reMenuPriceField.getText().charAt(i);
					if(Character.isDigit(tmp)==false) {
						JOptionPane.showMessageDialog(null, "������ ���ڷθ� �Է��ϼ���.","�Է� ����",JOptionPane.WARNING_MESSAGE);
						reMenuPriceField.setText("");
						return;
					}
				}
				
				//��� ��찡 ��������^^��
				menu[numtemp].reMenu(reMenuNameField.getText(), Integer.parseInt(reMenuPriceField.getText()));
				//�޴��� ����^^�����մϴ�. reMenu�� �޴��� �����ϴ� �޼��忡��.
				updateMenu_DB updateMenu_DB = new updateMenu_DB(Integer.toString(numtemp), reMenuNameField.getText(), reMenuPriceField.getText());
				
				
				reMenuNameField.setText("");
				reMenuPriceField.setText("");
				/*�޴� table �� ���ΰ�ħ*/resetingMenuTable();
				
				JLabel label102 = new JLabel("["+menu[numtemp].getMenuName()+"-"+menu[numtemp].getMenuPrice()+"��]�� ���� �Ϸ�");
				label102.setFont(new Font("����", Font.BOLD, 17));
				label102.setForeground(Color.MAGENTA);
				JOptionPane.showMessageDialog(null, label102, "", JOptionPane.INFORMATION_MESSAGE);
				
				setIntoPanel.setVisible(false);	
				setMenuBar.setVisible(false);
				setMenuBar.setVisible(true);

			}
		});
		reMenuBtn.setFont(new Font("����", Font.PLAIN, 18));
		reMenuBtn.setBounds(245, 140, 120, 45);
		setIntoPanel.add(reMenuBtn);
		
		/* ����..�׷� �� */
		JLabel label1_setMenuBar = new JLabel("\uC218\uC815\uD560 \uC815\uBCF4");
		label1_setMenuBar.setHorizontalAlignment(SwingConstants.CENTER);
		label1_setMenuBar.setFont(new Font("����", Font.BOLD, 18));
		label1_setMenuBar.setBounds(100, 10, 172, 35);
		setIntoPanel.add(label1_setMenuBar);		
		JLabel label2_setMenuBar = new JLabel("\uBA54\uB274 \uC774\uB984");
		label2_setMenuBar.setFont(new Font("����", Font.PLAIN, 18));
		label2_setMenuBar.setBounds(20, 53, 86, 35);
		setIntoPanel.add(label2_setMenuBar);	
		JLabel label3_setMenuBar = new JLabel("\uAC00\uACA9");
		label3_setMenuBar.setFont(new Font("����", Font.PLAIN, 18));
		label3_setMenuBar.setBounds(20, 95, 128, 41);
		setIntoPanel.add(label3_setMenuBar);
		
		
		/* �޴� ����â�� �������� �ʵ� */
		reMenuNameField = new JTextField();
		reMenuNameField.setFont(new Font("����", Font.PLAIN, 17));
		reMenuNameField.setBounds(117, 55, 190, 35);
		setIntoPanel.add(reMenuNameField);
		
		reMenuNameField.setColumns(10);		
		reMenuPriceField = new JTextField();
		reMenuPriceField.setFont(new Font("����", Font.PLAIN, 17));
		reMenuPriceField.setBounds(117, 100, 116, 35);
		setIntoPanel.add(reMenuPriceField);
		reMenuPriceField.setColumns(10);

		
		/* �޴� ����â�� �޴� table ��ũ�� ���� */
		JScrollPane scrollPane_2 = new JScrollPane(table_menu);
		scrollPane_2.setBounds(69, 55, 348, 423);
		scrollPane_2.setEnabled(false);
		setMenuBar.add(scrollPane_2);
		

		/* Title�� �Ӽ� */
		JLabel TitleLabel = new JLabel("Dessert Cafe");
		TitleLabel.setBackground(new Color(255, 192, 203));
		TitleLabel.setForeground(new Color(218, 112, 214));
		TitleLabel.setFont(new Font("Lucida Fax", Font.BOLD, 32));
		TitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		TitleLabel.setBounds(357, 0, 222, 53);
		topBar.add(TitleLabel);
		
	
		/* �����Ǹű�� label */
		JLabel showEarnings = new JLabel("\uB204\uC801 \uD310\uB9E4\uC218\uC785/\uC8FC\uBB38\uC218");
		showEarnings.setBackground(Color.WHITE);
		showEarnings.setHorizontalAlignment(SwingConstants.CENTER);
		showEarnings.setFont(new Font("����", Font.PLAIN, 17));
		showEarnings.setBounds(14, 59, 168, 29);
		topBar.add(showEarnings);
		
		/* ���� �Ǹż����� ǥ�õǴ� txt�ʵ� */
		earnings = new JTextField();
		earnings.setBackground(Color.WHITE);
		earnings.setEditable(false);
		earnings.setHorizontalAlignment(SwingConstants.RIGHT);
		earnings.setFont(new Font("����", Font.PLAIN, 17));
		earnings.setBounds(185, 57, 142, 31);
		topBar.add(earnings);
		earnings.setColumns(10);
		
		/* ���� �ֹ����� ǥ�õǴ� txt�ʵ� */
		amount = new JTextField();
		amount.setBackground(Color.WHITE);
		amount.setEditable(false);
		amount.setHorizontalAlignment(SwingConstants.RIGHT);
		amount.setFont(new Font("����", Font.PLAIN, 17));
		amount.setBounds(325, 57, 60, 31);
		topBar.add(amount);
		amount.setColumns(10);
		
		/* �ǽð� ���� �Ǹŷ� ǥ�� */
		earnings.setText(Integer.toString(nowEarnings)+"��");
		amount.setText(Integer.toString(nowPays)+"��");
		
		JLabel label100 = new JLabel("�ֹ� ������ �����Ͽ����ϴ�.");
		label100.setFont(new Font("����", Font.PLAIN, 16));
		
		/* ���� �Ǹű���� �ʱ�ȭ�ϴ� ��ư */
		JButton resetButton = new JButton("\uCD08\uAE30\uD654");
		resetButton.setBackground(new Color(255, 215, 0));
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectRecord();
				/*��� �ʱ�ȭ �޼���*/resetEarnings();
				earnings.setText(Integer.toString(nowEarnings)+"��");
				amount.setText(Integer.toString(nowPays)+"��");
				JOptionPane.showMessageDialog(null, label100,"�ʱ�ȭ",JOptionPane.INFORMATION_MESSAGE);
				resetingMenuTable();
			
			}
		});
		resetButton.setFont(new Font("����", Font.PLAIN, 16));
		resetButton.setBounds(389, 59, 87, 27);
		topBar.add(resetButton);
		
		/* ���� �ð��� �����ִ� txt�ʵ� */
		timeField = new JTextField();
		timeField.setBackground(Color.WHITE);
		timeField.setFont(new Font("����", Font.BOLD, 17));
		timeField.setEditable(false);
		timeField.setHorizontalAlignment(SwingConstants.CENTER);
		timeField.setBounds(761, 14, 163, 32);
		topBar.add(timeField);
		timeField.setColumns(10);
		
		/* bottomMain�� �޴� �߰� ��ư */
		JButton btn_addMenu = new JButton("\uBA54\uB274 \uCD94\uAC00");
		btn_addMenu.setBackground(new Color(175, 238, 238));
		btn_addMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addMenuBar.setVisible(true);
				topBar.setVisible(true);
				bottomMain.setVisible(false);
				lastEarningsBar.setVisible(false);
				deleteMenuBar.setVisible(false);
				setMenuBar.setVisible(false);
				resetTime();
			}
		});
		btn_addMenu.setFont(new Font("����", Font.PLAIN, 17));
		btn_addMenu.setBounds(586, 56, 110, 31);
		topBar.add(btn_addMenu);
		
		/* bottomMain�� �޴� ���� ��ư */
		JButton btn_setMenu = new JButton("\uBA54\uB274 \uC218\uC815");
		btn_setMenu.setBackground(new Color(175, 238, 238));
		btn_setMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetingMenuTable();
				addMenuBar.setVisible(false);
				topBar.setVisible(true);
				bottomMain.setVisible(false);
				lastEarningsBar.setVisible(false);
				setMenuBar.setVisible(true);
				deleteMenuBar.setVisible(false);
				
				resetTime();
			}
		});
		btn_setMenu.setFont(new Font("����", Font.PLAIN, 17));
		btn_setMenu.setBounds(700, 56, 110, 31);
		topBar.add(btn_setMenu);
		
		/* bottomMain�� �޴� ���� ��ư */
		JButton btn_deleteMenu = new JButton("\uBA54\uB274 \uC0AD\uC81C");
		btn_deleteMenu.setBackground(new Color(175, 238, 238));
		btn_deleteMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetingMenuTable();
				addMenuBar.setVisible(false);
				topBar.setVisible(true);
				bottomMain.setVisible(false);
				lastEarningsBar.setVisible(false);
				deleteMenuBar.setVisible(true);
				setMenuBar.setVisible(false);
				resetTime();
			}
		});
		btn_deleteMenu.setFont(new Font("����", Font.PLAIN, 17));
		btn_deleteMenu.setBounds(814, 56, 110, 31);
		topBar.add(btn_deleteMenu);
		
		/* �ֹ� ���� ���� ���ǳ� */
		JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(1, 1, 10, 1));
		spinner.setFont(new Font("����", Font.PLAIN, 16));
		spinner.setBounds(215, 364, 49, 24);
		bottomMain.add(spinner);
		/* �ֹ� ���� ���� �� */
		JLabel lblNo = new JLabel("\uC644\uB8CC\uD55C \uC8FC\uBB38\uC815\uBCF4 NO.");
		lblNo.setBackground(SystemColor.activeCaption);
		lblNo.setFont(new Font("����", Font.PLAIN, 16));
		lblNo.setBounds(56, 364, 158, 25);
		bottomMain.add(lblNo);
		/* �ֹ� ���� ���� ��ư */
		JButton btn_del_order = new JButton("Del");
		btn_del_order.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(order[(int)(spinner.getValue())-1].isFilled()==true) {//�ش� �ֹ������� ������
					for(i=0; i<orderLength; i++) { //���� ã�� ����
						if((int)spinner.getValue()==order[i].getOrdernum()) {
							order[i].deleteOrder();
						}
					}
					JOptionPane.showMessageDialog(null, spinner.getValue()+"�� �ֹ��� �Ϸ��Ͽ����ϴ�.","�ֹ� ���� ���� �Ϸ�",JOptionPane.INFORMATION_MESSAGE);
				}else { //�ش� �ֹ������� ������
					JOptionPane.showMessageDialog(null, "�ش� ��ȣ�� �ֹ� ������ �����ϴ�.","�Է� ����",JOptionPane.WARNING_MESSAGE);
				}

				/*�ֹ����� table �� ���ΰ�ħ*/resetOrderInfoTable();
				bottomMain.setVisible(false);
				bottomMain.setVisible(true);
				/*�ð� ��*/resetTime();
			}
		});
		btn_del_order.setBackground(new Color(255, 215, 0));
		btn_del_order.setFont(new Font("����", Font.PLAIN, 16));
		btn_del_order.setBounds(265, 363, 62, 27);
		bottomMain.add(btn_del_order);
		
		/*DB�޴� ��������*/pullupDBmenu();
		/*���α׷� �ʱ� ����� �ð� �ʱ�ȭ*/ resetTime();
		square.setVisible(false);
		square.setVisible(true);
		/* bottomMain �� �ֹ����� table ���ΰ�ħ *///resetOrderInfoTable();
		
		
		/* ǥ �Ʒ� �̹��� ÷�� */
		Image img = null;
		File sourceimage = new File("img/java.png");
		try {
			img = ImageIO.read(sourceimage);
		} catch (IOException e) {
			
		}
	
		Image imgtop = null;
		File sourceimage2 = new File("img/desert.png");
		try {
			imgtop = ImageIO.read(sourceimage2);
		} catch (IOException e) {
			
		}
		
		Image img01 = null;
		File sourceimage01 = new File("img/pancakes.png");
		try {
			img01 = ImageIO.read(sourceimage01);
		} catch (IOException e) {
			
		}
		
		Image img02 = null;
		File sourceimage02 = new File("img/blueberries.png");
		try {
			img02 = ImageIO.read(sourceimage02);
		} catch (IOException e) {
			
		}
		
		Image img09 = null;
		File sourceimage09 = new File("img/gingerbread.png");
		try {
			img09 = ImageIO.read(sourceimage09);
		} catch (IOException e) {
			
		}
		
		Image img03 = null;
		File sourceimage03 = new File("img/port.png");
		try {
			img03 = ImageIO.read(sourceimage03);
		} catch (IOException e) {
			
		}
		
		Image img04 = null;
		File sourceimage04 = new File("img/milk.png");
		try {
			img04 = ImageIO.read(sourceimage04);
		} catch (IOException e) {
			
		}
		
		Image img05 = null;
		File sourceimage05 = new File("img/cupcake.png");
		try {
			img05 = ImageIO.read(sourceimage05);
		} catch (IOException e) {
			
		}
		
		Image img06 = null;
		File sourceimage06 = new File("img/cake-slice.png");
		try {
			img06 = ImageIO.read(sourceimage06);
		} catch (IOException e) {
			
		}
		
		Image img07 = null;
		File sourceimage07 = new File("img/cafe.png");
		try {
			img07 = ImageIO.read(sourceimage07);
		} catch (IOException e) {
			
		}
		
		Image img08 = null;
		File sourceimage08 = new File("img/food-and-restaurant.png");
		try {
			img08 = ImageIO.read(sourceimage08);
		} catch (IOException e) {
			
		}
		
		Image img10 = null;
		File sourceimage10 = new File("img/peach.png");
		try {
			img10 = ImageIO.read(sourceimage10);
		} catch (IOException e) {
			
		}
		
		Image img11 = null;
		File sourceimage11 = new File("img/cafe02.gif");
		try {
			img11 = ImageIO.read(sourceimage11);
		} catch (IOException e) {
			
		}
		
		JLabel inImgLabel = new JLabel(new ImageIcon(img));
		inImgLabel.setBounds(28, 386, 322, 105);
		bottomMain.add(inImgLabel);
		
		JLabel inimglabel2 = new JLabel(new ImageIcon(imgtop));
		inimglabel2.setBounds(494, 45, 77, 51);
		topBar.add(inimglabel2);
		
		JLabel imgLabel07 = new JLabel(new ImageIcon(img07));
		imgLabel07.setBounds(699, 3, 62, 53);
		topBar.add(imgLabel07);
		
		JLabel imgLabel01 = new JLabel(new ImageIcon(img01));
		imgLabel01.setBounds(14, 377, 135, 125);
		addMenuBar.add(imgLabel01);
		
		JLabel imgLabel02 = new JLabel(new ImageIcon(img09));
		imgLabel02.setBounds(163, 420, 88, 82);
		addMenuBar.add(imgLabel02);
		
		JLabel imgLabel03 = new JLabel(new ImageIcon(img03));
		imgLabel03.setBounds(761, 331, 177, 171);
		addMenuBar.add(imgLabel03);
		
		JLabel imgLabel04 = new JLabel(new ImageIcon(img04));
		imgLabel04.setBounds(665, 390, 114, 112);
		addMenuBar.add(imgLabel04);
		
		JLabel imgLabel05 = new JLabel(new ImageIcon(img05));
		imgLabel05.setBounds(577, 420, 88, 82);
		addMenuBar.add(imgLabel05);
		
		JLabel imgLabel06 = new JLabel(new ImageIcon(img06));
		imgLabel06.setBounds(465, 420, 88, 82);
		addMenuBar.add(imgLabel06);
		
		JLabel imgLabel08 = new JLabel(new ImageIcon(img08));
		imgLabel08.setBounds(781, 269, 143, 69);
		addMenuBar.add(imgLabel08);
		
		JLabel imgLabel10 = new JLabel(new ImageIcon(img10));
		imgLabel10.setBounds(263, 20, 82, 78);
		addMenuBar.add(imgLabel10);
		
		JLabel imgLabel09 = new JLabel(new ImageIcon(img02));
		imgLabel09.setBounds(710, 84, 51, 45);
		addMenuBar.add(imgLabel09);
		
		JLabel imgLabel11 = new JLabel(new ImageIcon(img11));
		imgLabel11.setBounds(770, 120, 100, 100);
		deleteMenuBar.add(imgLabel11);

		/*������ �̹��� ����*/
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image iconimg = kit.getImage("img/cf.png");
		frame.setIconImage(iconimg);	
	}
}
