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

/* 메인 클래스 */
public class CafeTest {

	/*SWING*/
	private JFrame frame;
	private JTextField earnings;
	private JTextField amount;
	private JTextField timeField;
	private JTextField payInfoMenu;
	private JTextField payInfoPrice;
	
	static int i, j;
	
	/* 메뉴 인스턴스 개수 한도 상수(절대 고정!!) */ private final static int menuLength = 12;
	/*[DB]Menu 인스턴스 배열 생성*/static Menu menu[] = new Menu[menuLength];
	
	
	/* 주문정보 인스턴스 개수 한도 */ private static int orderLength = 10;
	/*Order 인스턴스 배열 생성*/private static Order order[] = new Order[orderLength];
	
	
	/*[DB]지난 판매기록 인스턴스 생성*/static LastRecords record[] = new LastRecords[LastRecords.recordsIndex];
	
	
	/*메인화면 주문메뉴 임시누적 데이터공간▼*/private static String tempMenu[] = new String[Order.ordermenuLength];
	/*메인화면 주문수량 임시누적 데이터공간◆*/private static int tempQuantity[] = new int[Order.ordermenuLength];
	/*메인화면 주문금액 임시누적 데이터공간▲*/private static int tempPayprice;
	
	
	/*누적 판매수입량*/ private int nowEarnings;
	/*누적 판매주문량*/ private int nowPays = 0;
	
	
	/* ★Table 메인화면 주문정보목록 열 이름 */ private static String heading[] = new String[] {"번호","주문 메뉴","결제 금액"};
	/* ★Table 메인화면 주문정보목록 값 */ private static Object[][] orderData = new Object[orderLength][3];
	/* ★Table 메인화면 주문정보목록 정의 */private static JTable table =  new JTable(orderData, heading);
 
	
	/* ★Table 지난 판매기록 열 이름 */ private static String heading_last[] = new String[] {"날짜","판매수입","판매주문수"};
	/* ★Table 지난 판매기록 값 */ private static Object[][] orderData_last = new Object[LastRecords.recordsIndex][3];
	/* ★Table 지난 판매기록 정의 */private JTable table_last = new JTable(orderData_last, heading_last);
	
	/* ★Table 메뉴목록 이름 */ private static String heading_menu[] = new String[] {"메뉴 이름","가격"};
	/* ★Table 메뉴목록 값 */ private  static Object[][] menuData = new Object[menuLength][2];
	/* ★Table 메뉴목록 정의 */private static JTable table_menu = new JTable(menuData, heading_menu);
	/* Table 메뉴목록 정의 2 */private static JTable table_menu2 = new JTable(menuData, heading_menu);
	
	/* 메뉴 추가 텍스트 필드 */
	private JTextField str_newMenuName;
	private JTextField int_newMenuPrice;
	
	
	SimpleDateFormat format2 = new SimpleDateFormat("Y-M-dd (H:mm)");
	
	/*패널*/
	JPanel topBar = new JPanel();
	JPanel bottomMain = new JPanel();
	JPanel deleteMenuBar = new JPanel();
	JPanel lastEarningsBar = new JPanel();
	JPanel setIntoPanel = new JPanel();
	JPanel square = new JPanel();
	/*수정할 메뉴의 번호 저장*/int numtemp;

	/*메뉴 수정창의 메뉴이름 텍스트 필드*/private JTextField reMenuNameField;
	/*메뉴 수정창의 가격 텍스트 필드*/private JTextField reMenuPriceField;
	
	/* 메인화면 메뉴 버튼 정의 */
	static JButton MenuBtn[] = new JButton[12];
	
	
	/* 메인화면에 보여지는 메뉴 버튼 텍스트 새로고침 */
	public void resetingShowingMenu() {
		for(i=0; i<menuLength; i++) {
			MenuBtn[i].setText("<HTML><body><center>"+menu[i].getMenuName()+"<br>"+(menu[i].getMenuName().equals("")?"":menu[i].getMenuPrice()+"원")+"</center></body></HTML>");
		}
			
	}//end of method
	
	
	/*setMenuBar와 deleteMenuBar에서 사용되는 메뉴 Table의 데이터 새로고침*/
	public void resetingMenuTable() {
		for(i=0; i<menuLength; i++) {
			menuData[i][0] = menu[i].getMenuName();
			menuData[i][1] = menu[i].getMenuName().equals("")?"":menu[i].getMenuPrice()+"원";
		}
	}//end of method
	
	
	/*메뉴목록 변경 후 상태 새로고침(패널 off/on 동작)*/
	public void resetoutMenuTable() {
		deleteMenuBar.setVisible(false);
		deleteMenuBar.setVisible(true);
		
	}//end of method
	
	
	/*bottomMain속 주문 정보 table 값 새로고침*/
	public void resetOrderInfoTable() {
		/*주문 정보 객체배열 정렬*/
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
		/*삭제된 사항에 대하여 출력 새로 고침*/
		for(i=0; i<orderLength; i++) {
			
			String messg = "";
			//if 주문한 메뉴가 없을때
			if(order[i].isFilled()==false) {
				messg="";
			}else if(order[i].getOrderName(1).equals("")) { //메뉴를 한종류만 주문했을때
				messg =order[i].getOrderName(0)+"("+order[i].getOrderQuantity(0)+")";
		
			}else if(order[i].getOrderName(1).equals("")==false){ //메뉴를 두종류 주문했을때
				messg =order[i].getOrderName(0)+"("+order[i].getOrderQuantity(0)+"), "+order[i].getOrderName(1)+"("+order[i].getOrderQuantity(1)+")";
				
			}
			orderData[i][0] = (order[i].getOrdernum()==0)?"":(order[i].getOrdernum());
			orderData[i][1] = messg;
			orderData[i][2] = (order[i].getFinalPrice()==0)?"":order[i].getFinalPrice()+"원";
		}
	}//end of method
	
	
	/*메뉴 정보 추가 메서드*/
	public static int id;
	public void addMenu(String name, int price) {
		
		JLabel label = new JLabel("새 메뉴 ["+name+" "+price+"원] 등록 완료");
		label.setFont(new Font("굴림", Font.BOLD, 17));
		label.setForeground(Color.MAGENTA);
		
		JLabel label03 = new JLabel("메뉴 개수 한도 초과입니다.");
		label03.setFont(new Font("굴림", Font.PLAIN, 16));
		
		i=0;
		int flag=0;
		/*배열의 마지막값이 차있는지 확인하여 배열이 꽉 찼는지 확인한다.*/
		if((menu[menuLength-1].getMenuName()).equals("")) { //다 차있지않으면 i를 돌려 빈 자리를 찾는다
			while(flag==0) { //메뉴 객체 배열에서 가장 앞 빈 공간을 찾는다.
				if((menu[i].getMenuName()).equals("")) {
					/* 메뉴 등록(추가) *///menu[i].reMenu(name, price);
					
					/* DB */
					String sprice=Integer.toString(price);
					String si=Integer.toString(i);
					addMenu_DB addMenu_DB = new addMenu_DB(si,name, sprice);
					selectMenu_DB selectMenuName_DB = new selectMenu_DB(si);
					
					
					flag=1; 
				}else {	i++ ;} //i값 인덱스가 비어있지 않으면 i값을 증가하고 다시 while문을 돌린다.
			}
			JOptionPane.showMessageDialog(null, label); //등록완료 메시지
		}else {
			JOptionPane.showMessageDialog(null, label03,"등록 실패",JOptionPane.OK_OPTION); //등록 실패 메시지
		}
	}//end of method

	
	/*메뉴 객체배열 정렬*/
	public void reArraymanuTable() {
		resetingMenuTable(); //정렬 전 메뉴table에 표시할 값 새로고침
		Menu temp;
		for(i=0; i<menuLength-1; i++) {
			if(menu[i].getMenuName().equals("") && menu[i+1].getMenuName().equals("")==false) { //바로 앞의 인덱스가 비고 뒤엔 차있으면
					temp = menu[i];
					menu[i] = menu[i+1];
					menu[i+1]=temp;
			}
		}
		
		resetingMenuTable(); //정렬 후 메뉴table에 표시할 값 새로고침
		deleteMenuBar.setVisible(false);
		deleteMenuBar.setVisible(true);
	}//end of method
	
	
	/*메뉴 선택란 새로고침 메서드*/
	public void deletetempMenuSet() {
		for(i=0; i< Order.ordermenuLength; i++) {
			tempMenu[i] = "";
			tempQuantity[i] = 0;
			tempPayprice = 0;
		}
		payInfoMenu.setText("");
		payInfoPrice.setText("");
	}//end of method

	
	/*현재 주문메뉴 선택 과정에서 해당 메뉴가 선택 된 여부*/private boolean flagpick;
	
	/*메뉴를 선택했을때 실행되는 메인화면에서 메뉴 선택과정 메서드*/
	public void pickMenu(int numOfmenu) { //선택한 메뉴의 인덱스 번호를 매개변수로 가져옴
		flagpick=false;
		/* 이미 선택된 메뉴인지 판단 */
		for(i=0; i<Order.ordermenuLength; i++) { 
			if(tempMenu[i].equals(menu[numOfmenu].getMenuName())){ //내가 지금 선택한 메뉴가 첫번째에 선택했던 메뉴와 같으면 이미 선택했던 메뉴이므로
				tempQuantity[i]++; //그 메뉴 수량만 높이자!
				tempPayprice += menu[numOfmenu].getMenuPrice(); //가격도 누적해!
				flagpick = true;
				break;
			}
		}
		/* 이미 선택된 메뉴가 아니고 메뉴가 꽉 차지 않았다면 */
		if(flagpick==false && tempMenu[Order.ordermenuLength-1].equals("")) {
			for(i=0; i<Order.ordermenuLength; i++) { 
				if(tempMenu[i].equals("")) { //if 주문중인 메뉴 목록중 처음 빈곳에 임시저장
					tempMenu[i] = menu[numOfmenu].getMenuName();
					tempQuantity[i]++;
					tempPayprice += menu[numOfmenu].getMenuPrice();
					break;
				}
			}
		/* 이미 선택된 메뉴가 아니고 메뉴가 꽉 찼다면 */
		}else if(flagpick==false && tempMenu[Order.ordermenuLength-1].equals("")==false){
			JOptionPane.showMessageDialog(null, "주문 가능한 메뉴의 종류는 "+Order.ordermenuLength+"개까지 입니다.","메뉴 초과",JOptionPane.OK_OPTION);
		}
		
		/*시간 새로고침*/
		resetTemporder();	
	}
	
	/* 메뉴 삭제 로직 메서드 */
	public void deleteMenuin(int n){
		if(menu[n].getMenuName()=="") return; /*메뉴가 비어있으면 아무 동작도 하지 않는다.*/
		
		JLabel label103 = new JLabel("["+menu[n].getMenuName()+"] 메뉴가 삭제되었습니다.");
		label103.setFont(new Font("굴림", Font.BOLD, 17));
		label103.setForeground(Color.MAGENTA);
		
		JOptionPane.showMessageDialog(null, label103, "", JOptionPane.INFORMATION_MESSAGE);
		/*메뉴 삭제*/menu[n].deleteMenu();
		
		/* DB */
		deleteMenu_DB deleteMenu_DB = new deleteMenu_DB(Integer.toString(n));
		
		/*적용 */resetingMenuTable();
		/*정렬 */reArraymanuTable();
		/*표시 */resetoutMenuTable();
		
		
	}
	
	
	/*주문결제 mothod*/
	public void payOrder() {
		/* 맨 마지막 주붕정보가 꽉 차 있으면 */
		if(order[orderLength-1].isFilled()==true) {
			JOptionPane.showMessageDialog(null, "주문 정보가 꽉 찼습니다.");
			return;
		}
		
		int flag=0;
		int tmpODRnum = 0;
		for(int j=1; j<=orderLength; j++) { //찾아야 하는 수.
			for(int k=0; k<orderLength; k++) { //비교할 인덱스 번호 돌돌말아
				if(j==order[k].getOrdernum()) {
					flag=0;
					break;
				}else { flag=1;} //겹치는 수가 없다면 이 for문이 끝나면 flag가 1이겠죠?
			}
			if(flag==1) { //겹치는 수가 없다면 이 수를 채택하고 for문을 빠져나가자
				tmpODRnum = j;
				break;
			}
		}
		
		
		for(i=0; i<orderLength; i++) {
			/*새 주문정보를 저장할 '가장 앞쪽의 빈 주문정보 자리'를 찾는다.*/
			if(order[i].isFilled() == false) {
				/*주문정보 추가*/order[i].reOrder(tempMenu, tempQuantity, tempPayprice, tmpODRnum);
				
				/*결제금액을 판매수입량에 누적*/ nowEarnings += tempPayprice;
				/*판매주문량 증가*/nowPays++;
				
				/* 메뉴 정보 텍스트 세팅 */
				String messg;
				if(tempMenu[1].equals("")) {
					messg =tempMenu[0]+"("+tempQuantity[0]+")";
				}else {
					messg =tempMenu[0]+"("+tempQuantity[0]+"), "+tempMenu[1]+"("+tempQuantity[1]+")";
				}
				/*결제 완료 메시지*/
				JLabel paymentLabel = new JLabel("주문번호"+(tmpODRnum)+": "+messg);
				paymentLabel.setFont(new Font("굴림", Font.BOLD, 17));
				paymentLabel.setForeground(Color.MAGENTA);
				JOptionPane.showMessageDialog(null, paymentLabel,"결제 완료",JOptionPane.PLAIN_MESSAGE);
				
				orderData[i][0] = tmpODRnum;
				orderData[i][1] = messg;
				orderData[i][2] = tempPayprice+"원";
				
				/*주문정보를 추가하고 메인화면으로 돌아간다!*/
				break;
			}
		}
		
		earnings.setText(Integer.toString(nowEarnings)+"원");
		amount.setText(Integer.toString(nowPays)+"건");
		
		/*메뉴 선택 정보 삭제*/ deletetempMenuSet();
		topBar.setVisible(false);
		bottomMain.setVisible(false);
		topBar.setVisible(true);
		bottomMain.setVisible(true);
	}
	
	/*주문취소 method*/
	public void cancelOrder() {
		/*시간 셋*/
		/*메뉴 선택 정보 삭제*/deletetempMenuSet();
		topBar.setVisible(false);
		bottomMain.setVisible(false);
		topBar.setVisible(true);
		bottomMain.setVisible(true);
	}
	
	
	selectLastRecords_DB selectLastRecords_DB;
	/* 지난 기록 불러와서 table에 대입  */
	public void selectRecord() {
		for(i=0; i<LastRecords.recordsIndex; i++) {
			selectLastRecords_DB = new selectLastRecords_DB(Integer.toString(i));
		}
		lastEarningsBar.setVisible(false);
		lastEarningsBar.setVisible(true);
	}
	
	/*지난 기록 배열 다 삭제*/
	public void resetRecord() {
		for(i = 0; i<LastRecords.recordsIndex; i++) {
			//record[i] = new LastRecords();
			record[i].setRecord("", 0, 0);

			orderData_last[i][0] = record[i].getDate();
			orderData_last[i][1] = record[i].getDate().equals("")?"":record[i].getEarnings_record()+"원";
			orderData_last[i][2] = record[i].getDate().equals("")?"":record[i].getSales_record()+"건";
		}
		
		
	}
	
	
	/*지난 기록 table 값 새로고침 메서드*/
	public void showEarnings() {
		selectRecord();
		for(i=0; i<LastRecords.recordsIndex; i++) {
			orderData_last[i][0] = record[i].getDate();
			orderData_last[i][1] = record[i].getDate().equals("")?"":record[i].getEarnings_record()+"원";
			orderData_last[i][2] = record[i].getDate().equals("")?"":record[i].getSales_record()+"건";
		}	
	}
	
	
	/*판매량 지난 기록으로 저장(초기화) 메서드*/
	public void resetEarnings() {
		Date time = new Date();
		String timeInfo = format2.format(time);
		
		if(record[LastRecords.recordsIndex-1].getDate() != "") { //저장할 빈 자리가 없으면
			record[0] = new LastRecords();//지난기록 맨앞꺼 삭제하기
			//그리고 아래에서 코드를 땡겨올려주는 정렬 동작을 수행함. 일단 저장하기 전에 자리부터 만들고 보자!
			LastRecords temp;
			for(i=0; i<LastRecords.recordsIndex-1; i++) {
				if(record[i].getDate().equals("") && record[i+1].getDate().equals("")==false) { //바로 앞의 인덱스가 비고 뒤엔 차있으면
						temp = record[i];
						record[i] = record[i+1];
						record[i+1]=temp;						
				}
			}
			
			arrayLastRecords_DB arrayLastRecord_DB = new arrayLastRecords_DB();
			
		}//end of if
		
		addLastRecords_DB addLastRecords_DB ; 
		//위에서 자리를 비게 만들었든, 원래 빈 자리가 있었든 이제 table에서 가장 앞쪽의 빈 인덱스를 찾아 지난 기록을 저장한다.
		for(i=0; i<LastRecords.recordsIndex; i++) {
			if(record[i].getDate().equals("")) {
				/*★★기록 저장★★*/record[i].setRecord(timeInfo, nowEarnings, nowPays);
				/* 차례대로 기록 정보의 제목(날짜,자동), 현재 수입, 현재 주문건수 */
				String sid = Integer.toString(i);
				String snowEarnings = Integer.toString(nowEarnings);
				String snowPays = Integer.toString(nowPays);
				addLastRecords_DB = new addLastRecords_DB(sid,timeInfo, snowEarnings, snowPays);
				/* 지난 기록으로 저장한 후 현재 기록정보는 없앤다. */
				nowEarnings = 0;
				nowPays = 0;
				/*for문에서 빠져나옴*/break;
			}
		}
		/*지난 기록 table 새로고침*/showEarnings();
		/*시간 셋*/
		topBar.setVisible(false); topBar.setVisible(true);  
		lastEarningsBar.setVisible(false); lastEarningsBar.setVisible(true); 
	}//end of method 'resetEarnings()'
	
	
	/* 현재주문중인 내역 표시 */
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
		
		
	}
	
	/* setMenuPanel속 수정 패널 오픈  */
	public void opensetPanel() {
		reMenuNameField.setText("");
		reMenuPriceField.setText("");
		setIntoPanel.setVisible(true); //수정정보 입력 패널 보이기
		
		
	}
	
	
	
	public void pullupDBmenu() {
		/* DB 메뉴 가져오기 */
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
		
		/* 메뉴 버튼 생성 */
		for(i=0; i<menuLength; i++) {
			MenuBtn[i] = new JButton();
		}
		
		/*▼Menu 인스턴스 배열 전체 초기화*/
		for(i=0; i<menuLength; i++) {
			menu[i] = new Menu();
		}
		/*▼Order 인스턴스 배열 전체 초기화*/
		for(i=0; i<orderLength; i++) {
			order[i] = new Order();
		}
		
		
		/* 메뉴 table 값 새로고침 */
		for(i=0; i<menuLength; i++) {
			menuData[i][0] = menu[i].getMenuName();
			menuData[i][1] = menu[i].getMenuName().equals("")?"":menu[i].getMenuPrice()+"원";
		}
		
		/*▼지난 판매기록 배열 전체 초기화*/
		for(i=0; i<LastRecords.recordsIndex; i++) {
			record[i] = new LastRecords();
		}
		
		/* ★메인화면 주분정보 표 초기화 */
		for(i=0; i<orderLength; i++) {
			orderData[i][0] = "";
			orderData[i][1] = "";
			orderData[i][2] = "";
		}
		
		/*메인화면 임시주문공간 초기화*/
		for(i=0; i<Order.ordermenuLength; i++) {
			tempMenu[i] = "";
			tempQuantity[i] = 0;
		}
		tempPayprice = 0;
		
	}
	
	/*시간 출력 스레드 생성*/
	private void addTimeThread() {
		TimeThread th = new TimeThread(timeField, topBar);
		th.start();
	}
	
	/* Create the application. */
	public CafeTest() {
		Main();
	}
	
	/* Initialize the contents of the frame. */
	private void Main() {
		//데이터베이스 테이블 생성
		CreateTables_DB CreateTables_DB = new CreateTables_DB();

		/* 프레임 생성 */
		frame = new JFrame("Dessert Cafe");
		frame.setBounds(100, 100, 956, 659);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		
		/* topBar 상단 패널 속성 */
		topBar.setBackground(new Color(230, 230, 250));
		topBar.setBounds(0, 0, 938, 100);
		frame.getContentPane().add(topBar);
		topBar.setLayout(null);
		topBar.setVisible(true);
		
		/* bottomMain 하단 패널 속성 */
		bottomMain.setBackground(new Color(240, 248, 255));
		bottomMain.setBounds(0, 98, 938, 514);
		frame.getContentPane().add(bottomMain);
		bottomMain.setLayout(null);
		bottomMain.add(table);
		bottomMain.setVisible(true);
		
		/* bottomMain 속 주문정보 table 속성 */
		table.setRowSelectionAllowed(false);
		JScrollPane scrollPane = new JScrollPane(table);
		table.getTableHeader().setReorderingAllowed(false);		
		scrollPane.setEnabled(false);
		scrollPane.setBounds(28, 22, 322, 334);
		scrollPane.setViewportBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(218, 112, 214)));
		bottomMain.add(scrollPane);
		
		/* bottomMain 우편 -  주문이 이뤄지는 패널 선언과 속성 정의 */
		square.setBackground(Color.WHITE);
		square.setBorder(new LineBorder(new Color(128, 128, 128)));
		square.setBounds(364, 22, 546, 467);
		bottomMain.add(square);
		square.setLayout(null);
		JLabel label = new JLabel("\uBA54\uB274 \uC8FC\uBB38 - \uC120\uD0DD \uAC00\uB2A5");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("굴림", Font.PLAIN, 15));
		label.setBounds(17, 12, 153, 18);
		square.add(label);
		JLabel lblNewLabel = new JLabel("\uB2E8\uC704:\uB0B1\uAC1C(\uC794/\uAC1C)");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 15));
		lblNewLabel.setBounds(407, 12, 125, 18);
		square.add(lblNewLabel);
		

		
		
		/* 주문 선택 표시란 라벨과 텍스트필드 */
		JLabel payLabel = new JLabel("\uC8FC\uBB38/\uACB0\uC81C \uC815\uBCF4");
		payLabel.setBackground(new Color(255, 228, 225));
		payLabel.setFont(new Font("굴림", Font.BOLD, 15));
		payLabel.setBounds(7, 439, 110, 18);
		square.add(payLabel);
		/*메뉴 필드 선언*/payInfoMenu = new JTextField();
		payInfoMenu.setBackground(Color.WHITE);
		payInfoMenu.setEditable(false);
		payInfoMenu.setFont(new Font("굴림", Font.PLAIN, 15));
		payInfoMenu.setBounds(115, 430, 238, 37);
		square.add(payInfoMenu);
		payInfoMenu.setColumns(10);
		/*가격 필드 선언*/payInfoPrice = new JTextField();
		payInfoPrice.setBackground(Color.WHITE);
		payInfoPrice.setHorizontalAlignment(SwingConstants.RIGHT);
		payInfoPrice.setEditable(false);
		payInfoPrice.setFont(new Font("굴림", Font.PLAIN, 15));
		payInfoPrice.setBounds(350, 430, 73, 37);
		square.add(payInfoPrice);
		payInfoPrice.setColumns(10);
		
		
		
		/* 결제 버튼 */
		JButton btn_Pay = new JButton("\uACB0\uC81C");
		btn_Pay.setBackground(new Color(255, 140, 0));
		btn_Pay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/* 결제 */
				if(tempMenu[0].equals("")==false) { //첫번째 메뉴선택배열인덱스가 비어있는지 확인하여 주문할 정보가 있는지 확인.
					payOrder();
				}else {
					JOptionPane.showMessageDialog(null, "주문할 정보가 없습니다.");
				}
				/*시간 새로고침*/
			}
		});
		btn_Pay.setFont(new Font("굴림", Font.PLAIN, 14));
		btn_Pay.setBounds(421, 430, 65, 36);
		square.add(btn_Pay);
		
		
		JLabel label03 = new JLabel("주문 진행 취소");
		label03.setFont(new Font("굴림", Font.PLAIN, 16));
		
		/* 주문 취소 버튼 */
		JButton btn_Cancle = new JButton("\uCDE8\uC18C");
		btn_Cancle.setBackground(new Color(238, 232, 170));
		btn_Cancle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tempMenu[0].equals("")==false) { //주문하던 정보가 있을때만 
					cancelOrder();
					JOptionPane.showMessageDialog(null, label03,"",JOptionPane.ERROR_MESSAGE);
				}
				/*시간 셋*/
			}
		});
		btn_Cancle.setFont(new Font("굴림", Font.PLAIN, 14));
		btn_Cancle.setBounds(485, 430, 62, 36);
		square.add(btn_Cancle);
		
		
		
		/* 메뉴 버튼 정의 */
		MenuBtn[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(menu[0].getMenuName().equals("")==false) { //메뉴가 비어있지 않을때 임시주문정보에 저장
					pickMenu(0);
				}
				
			}
		});
		MenuBtn[0].setBackground(new Color(255, 192, 203));
		MenuBtn[0].setFont(new Font("굴림", Font.BOLD, 16));
		MenuBtn[0].setBounds(17, 45, 164, 79);
		square.add(MenuBtn[0]);
		
		MenuBtn[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(menu[1].getMenuName().equals("")==false) { //메뉴가 비어있지 않을때 임시주문정보에 저장
					pickMenu(1);
				}
			}
		});
		MenuBtn[1].setBackground(new Color(255, 192, 203));
		MenuBtn[1].setFont(new Font("굴림", Font.BOLD, 16));
		MenuBtn[1].setBounds(191, 45, 164, 79);
		square.add(MenuBtn[1]);
		
		MenuBtn[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(menu[2].getMenuName().equals("")==false) { //메뉴가 비어있지 않을때 임시주문정보에 저장
					pickMenu(2);
				}
			}
		});
		MenuBtn[2].setBackground(new Color(255, 192, 203));
		MenuBtn[2].setFont(new Font("굴림", Font.BOLD, 16));
		MenuBtn[2].setBounds(365, 45, 164, 79);
		square.add(MenuBtn[2]);
		
		MenuBtn[3].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(menu[3].getMenuName().equals("")==false) { //메뉴가 비어있지 않을때 임시주문정보에 저장
					pickMenu(3);
				}
			}
		});
		MenuBtn[3].setBackground(new Color(255, 192, 203));
		MenuBtn[3].setFont(new Font("굴림", Font.BOLD, 15));
		MenuBtn[3].setBounds(17, 136, 164, 79);
		square.add(MenuBtn[3]);
		MenuBtn[4].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(menu[4].getMenuName().equals("")==false) { //메뉴가 비어있지 않을때 임시주문정보에 저장
					pickMenu(4);
				}
			}
		});		
		MenuBtn[4].setBackground(new Color(255, 192, 203));
		MenuBtn[4].setFont(new Font("굴림", Font.BOLD, 15));
		MenuBtn[4].setBounds(191, 136, 164, 79);
		square.add(MenuBtn[4]);
		
		MenuBtn[5].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(menu[5].getMenuName().equals("")==false) { //메뉴가 비어있지 않을때 임시주문정보에 저장
					pickMenu(5);
				}
			}
		});		
		MenuBtn[5].setBackground(new Color(255, 192, 203));
		MenuBtn[5].setFont(new Font("굴림", Font.BOLD, 15));
		MenuBtn[5].setBounds(365, 136, 164, 79);
		square.add(MenuBtn[5]);
		
		MenuBtn[6].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(menu[6].getMenuName().equals("")==false) { //메뉴가 비어있지 않을때 임시주문정보에 저장
					pickMenu(6);
				}
			}
		});
		
		MenuBtn[6].setBackground(new Color(255, 192, 203));
		MenuBtn[6].setFont(new Font("굴림", Font.BOLD, 15));
		MenuBtn[6].setBounds(17, 227, 164, 79);
		square.add(MenuBtn[6]);
		
		MenuBtn[7].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(menu[7].getMenuName().equals("")==false) { //메뉴가 비어있지 않을때 임시주문정보에 저장
					pickMenu(7);
				}
			}
		});
		MenuBtn[7].setBackground(new Color(255, 192, 203));
		MenuBtn[7].setFont(new Font("굴림", Font.BOLD, 15));
		MenuBtn[7].setBounds(191, 227, 164, 79);
		square.add(MenuBtn[7]);
		
		MenuBtn[8].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(menu[8].getMenuName().equals("")==false) { //메뉴가 비어있지 않을때 임시주문정보에 저장
					pickMenu(8);
				}
			}
		});		
		MenuBtn[8].setBackground(new Color(255, 192, 203));
		MenuBtn[8].setFont(new Font("굴림", Font.BOLD, 15));
		MenuBtn[8].setBounds(365, 227, 164, 79);
		square.add(MenuBtn[8]);
		
		MenuBtn[9].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(menu[9].getMenuName().equals("")==false) { //메뉴가 비어있지 않을때 임시주문정보에 저장
					pickMenu(9);
				}
			}
		});
		
		MenuBtn[9].setBackground(new Color(255, 192, 203));
		MenuBtn[9].setFont(new Font("굴림", Font.BOLD, 15));
		MenuBtn[9].setBounds(17, 318, 164, 79);
		square.add(MenuBtn[9]);
		
		MenuBtn[10].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(menu[10].getMenuName().equals("")==false) { //메뉴가 비어있지 않을때 임시주문정보에 저장
					pickMenu(10);
				}
			}
		});	
		MenuBtn[10].setBackground(new Color(255, 192, 203));
		MenuBtn[10].setFont(new Font("굴림", Font.BOLD, 15));
		MenuBtn[10].setBounds(191, 318, 164, 79);
		square.add(MenuBtn[10]);
		
		MenuBtn[11].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(menu[11].getMenuName().equals("")==false) { //메뉴가 비어있지 않을때 임시주문정보에 저장
					pickMenu(11);
				}
			}
		});		
		MenuBtn[11].setBackground(new Color(255, 192, 203));
		MenuBtn[11].setFont(new Font("굴림", Font.BOLD, 15));
		MenuBtn[11].setBounds(365, 318, 164, 79);
		square.add(MenuBtn[11]);
	
		
		/* 메뉴 추가 패널 선언과 속성 */
		JPanel addMenuBar = new JPanel();
		addMenuBar.setBorder(new LineBorder(Color.GRAY));
		addMenuBar.setBackground(Color.WHITE);
		addMenuBar.setBounds(0, 98, 938, 514);
		frame.getContentPane().add(addMenuBar);
		addMenuBar.setLayout(null);
		JLabel label_1 = new JLabel("\uBA54\uB274 \uCD94\uAC00");
		label_1.setFont(new Font("굴림", Font.BOLD, 18));
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(403, 12, 114, 31);
		addMenuBar.add(label_1);
		addMenuBar.setVisible(false);
		/*구성요소들*/
		JLabel label1_addMenu = new JLabel("\uC0C8 \uBA54\uB274 \uC774\uB984");
		label1_addMenu.setHorizontalAlignment(SwingConstants.CENTER);
		label1_addMenu.setFont(new Font("굴림", Font.PLAIN, 18));
		label1_addMenu.setBounds(219, 110, 120, 31);
		addMenuBar.add(label1_addMenu);
		JLabel label_2 = new JLabel("\uAC00\uACA9");
		label_2.setFont(new Font("굴림", Font.PLAIN, 18));
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setBounds(282, 167, 62, 18);
		addMenuBar.add(label_2);
		/*메뉴이름*/str_newMenuName = new JTextField();
		str_newMenuName.setFont(new Font("굴림", Font.PLAIN, 18));
		str_newMenuName.setBounds(347, 108, 209, 37);
		addMenuBar.add(str_newMenuName);
		str_newMenuName.setColumns(10);
		/*메뉴 가격*/int_newMenuPrice = new JTextField();
		int_newMenuPrice.setFont(new Font("굴림", Font.PLAIN, 18));
		int_newMenuPrice.setColumns(10);
		int_newMenuPrice.setBounds(347, 159, 209, 37);
		addMenuBar.add(int_newMenuPrice);
		JLabel label3_addMenu = new JLabel("\uAC00\uACA9\uC740 \uC22B\uC790\uB9CC \uC785\uB825\uD558\uC138\uC694.");
		label3_addMenu.setFont(new Font("굴림", Font.PLAIN, 13));
		label3_addMenu.setBounds(285, 207, 260, 18);
		addMenuBar.add(label3_addMenu);
		label3_addMenu.setForeground(new Color(205, 92, 92));
		
		/*새 메뉴 등록버튼*/
		JButton setIntoMenu_btn = new JButton("\uB4F1\uB85D\uD558\uAE30");
		setIntoMenu_btn.setBackground(new Color(216, 191, 216));
		setIntoMenu_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JLabel label101 = new JLabel("메뉴 이름으로 공백이 올 수 없습니다.");
				label101.setFont(new Font("굴림", Font.PLAIN, 16));
				label101.setForeground(Color.RED);
				
				/*메뉴 입력 안했을경우 경고창 발생*/
				if(str_newMenuName.getText().equals("")) {
					JOptionPane.showMessageDialog(null, label101,"입력 오류",JOptionPane.WARNING_MESSAGE); 
					return;
				}
				
				JLabel label04 = new JLabel("입력된 가격이 없습니다.");
				label04.setFont(new Font("굴림", Font.PLAIN, 16));
				label04.setForeground(Color.RED);
				
				/*가격 미입력시 경고창 발생*/
				if(int_newMenuPrice.getText().equals("")) {
					JOptionPane.showMessageDialog(null, label04,"입력 오류",JOptionPane.WARNING_MESSAGE); 
					int_newMenuPrice.setText(""); 
					return;
				}
				
				JLabel label05 = new JLabel("가격은 숫자로만 입력하세요.");
				label05.setFont(new Font("굴림", Font.PLAIN, 16));
				label05.setForeground(Color.RED);
				
				/*입력받은 가격이 숫자가 아닐 경우 경고창 발생.*/
				char tmp;
				for(i=0; i<int_newMenuPrice.getText().length(); i++) {
					tmp = int_newMenuPrice.getText().charAt(i);
					if(Character.isDigit(tmp)==false) {
						JOptionPane.showMessageDialog(null, label05,"입력 오류",JOptionPane.WARNING_MESSAGE);
						int_newMenuPrice.setText("");
						return;
					}
				}
				
				/* if 모든 오류가 없으면 메뉴 추가 */
				addMenu(str_newMenuName.getText(), Integer.parseInt(int_newMenuPrice.getText()));
				str_newMenuName.setText("");
				int_newMenuPrice.setText("");
			}
		});
		setIntoMenu_btn.setFont(new Font("굴림", Font.PLAIN, 17));
		setIntoMenu_btn.setBounds(580, 107, 129, 89);
		addMenuBar.add(setIntoMenu_btn);
		
		
		JLabel label_4 = new JLabel("\uBA54\uB274 \uC774\uB984\uC740 8\uC790 \uC774\uD558\uB97C \uAD8C\uC7A5\uD569\uB2C8\uB2E4.");
		label_4.setForeground(new Color(205, 92, 92));
		label_4.setFont(new Font("굴림", Font.PLAIN, 13));
		label_4.setBounds(285, 225, 234, 18);
		addMenuBar.add(label_4);
		
		
		/* 메뉴 삭제 패널 속성 */
		deleteMenuBar.setBackground(Color.WHITE);
		deleteMenuBar.setBounds(0, 98, 938, 514);
		frame.getContentPane().add(deleteMenuBar);
		deleteMenuBar.setLayout(null);
		JLabel label_3 = new JLabel("\uBA54\uB274 \uC0AD\uC81C");
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setFont(new Font("굴림", Font.BOLD, 18));
		label_3.setBounds(403, 12, 114, 31);
		deleteMenuBar.add(label_3);
		deleteMenuBar.setVisible(false);		
		
		
		/* deleteMenuBar속 메뉴 목록 table */
		JScrollPane scrollPane_3 = new JScrollPane(table_menu2);
		scrollPane_3.setEnabled(false);
		scrollPane_3.setBounds(69, 55, 348, 423);
		deleteMenuBar.add(scrollPane_3);
		
		
		/* deleteMenuBar속 메뉴 삭제 버튼 */
		JButton delete_btn01 = new JButton("\uC0AD\uC81C");
		delete_btn01.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				deleteMenuin(0);
			}
		});
		delete_btn01.setFont(new Font("굴림", Font.PLAIN, 16));
		delete_btn01.setBackground(new Color(250, 128, 114));
		delete_btn01.setBounds(420, 82, 73, 28);
		deleteMenuBar.add(delete_btn01);
		
		JButton delete_btn02 = new JButton("\uC0AD\uC81C");
		delete_btn02.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteMenuin(1);
			}
		});
		delete_btn02.setFont(new Font("굴림", Font.PLAIN, 16));
		delete_btn02.setBackground(new Color(250, 128, 114));
		delete_btn02.setBounds(420, 114, 73, 28);
		deleteMenuBar.add(delete_btn02);
		
		JButton delete_btn03 = new JButton("\uC0AD\uC81C");
		delete_btn03.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteMenuin(2);
			}
		});
		delete_btn03.setFont(new Font("굴림", Font.PLAIN, 16));
		delete_btn03.setBackground(new Color(250, 128, 114));
		delete_btn03.setBounds(420, 147, 73, 28);
		deleteMenuBar.add(delete_btn03);
		
		JButton delete_btn04 = new JButton("\uC0AD\uC81C");
		delete_btn04.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteMenuin(3);
			}
		});
		delete_btn04.setFont(new Font("굴림", Font.PLAIN, 16));
		delete_btn04.setBackground(new Color(250, 128, 114));
		delete_btn04.setBounds(420, 180, 73, 28);
		deleteMenuBar.add(delete_btn04);
		
		JButton delete_btn05 = new JButton("\uC0AD\uC81C");
		delete_btn05.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteMenuin(4);
			}
		});
		delete_btn05.setFont(new Font("굴림", Font.PLAIN, 16));
		delete_btn05.setBackground(new Color(250, 128, 114));
		delete_btn05.setBounds(420, 212, 73, 28);
		deleteMenuBar.add(delete_btn05);
		
		JButton delete_btn06 = new JButton("\uC0AD\uC81C");
		delete_btn06.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteMenuin(5);
			}
		});
		delete_btn06.setFont(new Font("굴림", Font.PLAIN, 16));
		delete_btn06.setBackground(new Color(250, 128, 114));
		delete_btn06.setBounds(420, 245, 73, 28);
		deleteMenuBar.add(delete_btn06);
		
		JButton delete_btn07 = new JButton("\uC0AD\uC81C");
		delete_btn07.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteMenuin(6);
			}
		});
		delete_btn07.setFont(new Font("굴림", Font.PLAIN, 16));
		delete_btn07.setBackground(new Color(250, 128, 114));
		delete_btn07.setBounds(420, 277, 73, 28);
		deleteMenuBar.add(delete_btn07);
		
		JButton delete_btn08 = new JButton("\uC0AD\uC81C");
		delete_btn08.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteMenuin(7);
			}
		});
		delete_btn08.setFont(new Font("굴림", Font.PLAIN, 16));
		delete_btn08.setBackground(new Color(250, 128, 114));
		delete_btn08.setBounds(420, 311, 73, 28);
		deleteMenuBar.add(delete_btn08);
		
		JButton delete_btn09 = new JButton("\uC0AD\uC81C");
		delete_btn09.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteMenuin(8);
			}
		});
		delete_btn09.setFont(new Font("굴림", Font.PLAIN, 16));
		delete_btn09.setBackground(new Color(250, 128, 114));
		delete_btn09.setBounds(420, 344, 73, 28);
		deleteMenuBar.add(delete_btn09);
		
		JButton delete_btn10 = new JButton("\uC0AD\uC81C");
		delete_btn10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteMenuin(9);
			}
		});
		delete_btn10.setFont(new Font("굴림", Font.PLAIN, 16));
		delete_btn10.setBackground(new Color(250, 128, 114));
		delete_btn10.setBounds(420, 376, 73, 28);
		deleteMenuBar.add(delete_btn10);
		
		JButton delete_btn11 = new JButton("\uC0AD\uC81C");
		delete_btn11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteMenuin(10);
			}
		});
		delete_btn11.setFont(new Font("굴림", Font.PLAIN, 16));
		delete_btn11.setBackground(new Color(250, 128, 114));
		delete_btn11.setBounds(420, 409, 73, 28);
		deleteMenuBar.add(delete_btn11);
		
		JButton delete_btn12 = new JButton("\uC0AD\uC81C");
		delete_btn12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteMenuin(11);
			}
		});
		delete_btn12.setFont(new Font("굴림", Font.PLAIN, 16));
		delete_btn12.setBackground(new Color(250, 128, 114));
		delete_btn12.setBounds(420, 442, 73, 28);
		deleteMenuBar.add(delete_btn12);

		
		/* 메뉴 수정 패널 속성 */
		JPanel setMenuBar = new JPanel();
		setMenuBar.setBackground(Color.WHITE);
		setMenuBar.setBounds(0, 98, 938, 514);
		frame.getContentPane().add(setMenuBar);
		setMenuBar.setLayout(null);
		JLabel label_setMenu = new JLabel("\uBA54\uB274 \uC218\uC815");
		label_setMenu.setBounds(403, 12, 114, 31);
		label_setMenu.setFont(new Font("굴림", Font.BOLD, 18));
		label_setMenu.setHorizontalAlignment(SwingConstants.CENTER);
		setMenuBar.add(label_setMenu);
		setMenuBar.setVisible(false);
		
		
		/* addMenuBar패널 속 '돌아가기' 버튼 */
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
				/*시간 셋*/
				resetingShowingMenu();
			}
		});
		backto_addMenu.setFont(new Font("굴림", Font.PLAIN, 17));
		backto_addMenu.setBounds(819, 16, 105, 27);
		addMenuBar.add(backto_addMenu);
		
		
		/* 메뉴 수정 패널 속 선택메뉴 수정 패널*/
		setIntoPanel.setBackground(new Color(240, 248, 255));
		setIntoPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(218, 112, 214)));
		setIntoPanel.setBounds(515, 150, 380, 200);
		setMenuBar.add(setIntoPanel);
		setIntoPanel.setLayout(null);
		setIntoPanel.setVisible(false);
		
		
		/* 메뉴 인덱스마다의 수정 버튼 */
		JButton setButton01 = new JButton("수정");
		setButton01.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(menu[0].getMenuName().equals("")) { //빈 메뉴를 선택하면 아무것도 실행하지 않는다.
					setIntoPanel.setVisible(false);
					return;
				}else { //메뉴 수정을 선택
					numtemp=0; //해당 메뉴의 인덱스값을 저장.
					opensetPanel();
				}
			}
		});
		setButton01.setFont(new Font("굴림", Font.PLAIN, 16));
		setButton01.setBackground(new Color(255, 222, 173));
		setButton01.setBounds(420, 82, 69, 28);
		setMenuBar.add(setButton01);
		
		JButton setButton02 = new JButton("수정");
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
		setButton02.setFont(new Font("굴림", Font.PLAIN, 16));
		setButton02.setBackground(new Color(255, 222, 173));
		setButton02.setBounds(420, 114, 69, 28);
		setMenuBar.add(setButton02);
		
		JButton setButton03 = new JButton("수정");
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
		setButton03.setFont(new Font("굴림", Font.PLAIN, 16));
		setButton03.setBackground(new Color(255, 222, 173));
		setButton03.setBounds(420, 147, 69, 28);
		setMenuBar.add(setButton03);
		
		JButton setButton04 = new JButton("수정");
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
		setButton04.setFont(new Font("굴림", Font.PLAIN, 16));
		setButton04.setBackground(new Color(255, 222, 173));
		setButton04.setBounds(420, 180, 69, 28);
		setMenuBar.add(setButton04);
		
		JButton setButton05 = new JButton("수정");
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
		setButton05.setFont(new Font("굴림", Font.PLAIN, 16));
		setButton05.setBackground(new Color(255, 222, 173));
		setButton05.setBounds(420, 212, 69, 28);
		setMenuBar.add(setButton05);
		
		JButton setButton06 = new JButton("수정");
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
		setButton06.setFont(new Font("굴림", Font.PLAIN, 16));
		setButton06.setBackground(new Color(255, 222, 173));
		setButton06.setBounds(420, 245, 69, 28);
		setMenuBar.add(setButton06);
		
		JButton setButton07 = new JButton("수정");
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
		setButton07.setFont(new Font("굴림", Font.PLAIN, 16));
		setButton07.setBackground(new Color(255, 222, 173));
		setButton07.setBounds(420, 277,69, 28);
		setMenuBar.add(setButton07);
		
		JButton setButton08 = new JButton("수정");
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
		setButton08.setFont(new Font("굴림", Font.PLAIN, 16));
		setButton08.setBackground(new Color(255, 222, 173));
		setButton08.setBounds(420, 311, 69, 28);
		setMenuBar.add(setButton08);
		
		JButton setButton09 = new JButton("수정");
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
		setButton09.setFont(new Font("굴림", Font.PLAIN, 16));
		setButton09.setBackground(new Color(255, 222, 173));
		setButton09.setBounds(420, 344, 69, 28);
		setMenuBar.add(setButton09);
		
		JButton setButton10 = new JButton("수정");
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
		setButton10.setFont(new Font("굴림", Font.PLAIN, 16));
		setButton10.setBackground(new Color(255, 222, 173));
		setButton10.setBounds(420, 376, 69, 28);
		setMenuBar.add(setButton10);
		
		JButton setButton11 = new JButton("수정");
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
		setButton11.setFont(new Font("굴림", Font.PLAIN, 16));
		setButton11.setBackground(new Color(255, 222, 173));
		setButton11.setBounds(420, 409, 69, 28);
		setMenuBar.add(setButton11);
		
		JButton setButton12 = new JButton("수정");
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
		setButton12.setFont(new Font("굴림", Font.PLAIN, 16));
		setButton12.setBackground(new Color(255, 222, 173));
		setButton12.setBounds(420, 442, 69, 28);
		setMenuBar.add(setButton12);
		
		
		/*table 일부속성*/
		DefaultTableCellRenderer celAlignCenter = new DefaultTableCellRenderer();
		celAlignCenter.setHorizontalAlignment(JLabel.CENTER);
		DefaultTableCellRenderer celAlignRight = new DefaultTableCellRenderer();
		celAlignRight.setHorizontalAlignment(JLabel.RIGHT);

		
		
		/* 메뉴 table 속성 */
		table_menu2.setPreferredScrollableViewportSize(new Dimension(284, 348));
		table_menu2.setFillsViewportHeight(true);
		table_menu2.getColumn("메뉴 이름").setCellRenderer(celAlignCenter); //align
		table_menu2.getColumn("메뉴 이름").setPreferredWidth(150); //width 
		table_menu2.getColumn("가격").setCellRenderer(celAlignRight);
		table_menu2.getColumn("가격").setPreferredWidth(7);
		table_menu2.setRowHeight(33);
		table_menu2.setEnabled(false);
		table_menu2.setBounds(91, 68, 723, 409);
		table_menu2.getTableHeader().setReorderingAllowed(false);
		table_menu2.setForeground(new Color(128, 0, 128));
		table_menu2.setFont(new Font("굴림", Font.PLAIN, 17));
		
		
		/* 주문정보 table 속성 */
		table.setEnabled(false);
		table.setBounds(2, 33, 50, 359);
		table.setForeground(new Color(128, 0, 128));
		table.setFont(new Font("굴림", Font.PLAIN, 15));
		table.setPreferredScrollableViewportSize(new Dimension(284, 348));
		table.getColumn("번호").setPreferredWidth(5); //width
		table.getColumn("번호").setCellRenderer(celAlignCenter); //align
		table.getColumn("주문 메뉴").setPreferredWidth(200); //width 
		table.getColumn("결제 금액").setPreferredWidth(55); //width
		table.getColumn("결제 금액").setCellRenderer(celAlignRight); //align
		table.setRowHeight(27);
		
		/* 지난기록 패널 속성 */
		lastEarningsBar.setBackground(Color.WHITE);
		lastEarningsBar.setBounds(0, 98, 938, 514);
		frame.getContentPane().add(lastEarningsBar);
		lastEarningsBar.setLayout(null);
		JLabel label_laseEarnings = new JLabel("\uC9C0\uB09C \uD310\uB9E4\uAE30\uB85D [DATABASE]");
		label_laseEarnings.setHorizontalAlignment(SwingConstants.CENTER);
		label_laseEarnings.setFont(new Font("굴림", Font.BOLD, 17));
		label_laseEarnings.setBounds(355, 12, 243, 40);
		lastEarningsBar.add(label_laseEarnings);
		lastEarningsBar.setVisible(false);
		
		/*Table 메뉴 목록*/

		
		
		/* 지난 판매기록 table 속성*/
		table_last.getTableHeader().setReorderingAllowed(false);
		table_last.setForeground(new Color(128, 0, 128));
		table_last.setFont(new Font("굴림", Font.PLAIN, 17));
		table_last.setPreferredScrollableViewportSize(new Dimension(284, 348));
		table_last.setFillsViewportHeight(true);
		table_last.getColumn("날짜").setPreferredWidth(10); //width
		table_last.getColumn("날짜").setCellRenderer(celAlignCenter); //align
		table_last.getColumn("판매수입").setPreferredWidth(150); //width 
		table_last.getColumn("판매수입").setCellRenderer(celAlignRight);
		table_last.getColumn("판매주문수").setPreferredWidth(50); //width
		table_last.getColumn("판매주문수").setCellRenderer(celAlignRight); //align
		table_last.setRowHeight(27);
		table_last.setEnabled(false);
		table_last.setBounds(91, 68, 723, 409);
		lastEarningsBar.add(table_last);
		
		/*메뉴 table 속성*/
		table_menu.setPreferredScrollableViewportSize(new Dimension(284, 348));
		table_menu.setFillsViewportHeight(true);
		table_menu.getColumn("메뉴 이름").setCellRenderer(celAlignCenter); //align
		table_menu.getColumn("메뉴 이름").setPreferredWidth(150); //width 
		table_menu.getColumn("가격").setCellRenderer(celAlignRight);
		table_menu.getColumn("가격").setPreferredWidth(7);
		table_menu.setRowHeight(33);
		table_menu.setEnabled(false);
		table_menu.setBounds(91, 68, 723, 409);
		table_menu.getTableHeader().setReorderingAllowed(false);
		table_menu.setForeground(new Color(128, 0, 128));
		table_menu.setFont(new Font("굴림", Font.PLAIN, 17));
		
		
		/* 지난 판매기록 표 스크롤 */
		JScrollPane scrollPane_1 = new JScrollPane(table_last);
		scrollPane_1.setEnabled(false);
		scrollPane_1.setBounds(105, 68, 723, 409);
		lastEarningsBar.add(scrollPane_1);
		
		
		/* 지난 판매기록을 보여주는 버튼 */
		JButton ShowLastEarnings = new JButton("\uC9C0\uB09C \uD310\uB9E4\uAE30\uB85D \uBCF4\uAE30");
		ShowLastEarnings.setBackground(new Color(216, 191, 216));
		ShowLastEarnings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				/* 디비 정보 table로 불러오기 */selectRecord();
				showEarnings();
				addMenuBar.setVisible(false);
				topBar.setVisible(true);
				bottomMain.setVisible(false);
				lastEarningsBar.setVisible(false);
				lastEarningsBar.setVisible(true);
				deleteMenuBar.setVisible(false);
				setMenuBar.setVisible(false);
				
			}
		});
		ShowLastEarnings.setFont(new Font("굴림", Font.PLAIN, 17));
		ShowLastEarnings.setBounds(14, 13, 204, 32);
		topBar.add(ShowLastEarnings);
		

		/* 지난판매기록 창에서 돌아가기 버튼 */
		JButton backto_laseEarnings = new JButton("\uB3CC\uC544\uAC00\uAE30");
		backto_laseEarnings.setBackground(new Color(238, 232, 170));
		backto_laseEarnings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*시간 셋*/
				addMenuBar.setVisible(false);
				topBar.setVisible(true);
				bottomMain.setVisible(true);
				lastEarningsBar.setVisible(false);
				setMenuBar.setVisible(false);
				deleteMenuBar.setVisible(false);
			}
		});
		backto_laseEarnings.setFont(new Font("굴림", Font.PLAIN, 17));
		backto_laseEarnings.setBounds(819, 20, 105, 27);
		lastEarningsBar.add(backto_laseEarnings);
		
		
		JLabel ques = new JLabel("지난 기록을 모두 삭제하시겠습니까?");
		ques.setFont(new Font("굴림", Font.PLAIN, 16));
		
		/*지난 기록 모두 초기화 버튼*/
		JButton deleteall = new JButton("\uBAA8\uB4E0 \uAE30\uB85D \uCD08\uAE30\uD654");
		deleteall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int result = JOptionPane.showConfirmDialog(null, ques, "저장된 지난 기록", JOptionPane.YES_NO_OPTION);
				if(result==JOptionPane.YES_OPTION) {
					resetRecord();
					resetRecords_DB resetrc = new resetRecords_DB();
					JOptionPane.showMessageDialog(null, "모든 기록을 삭제했습니다.", "지난 기록 초기화", JOptionPane.INFORMATION_MESSAGE);
				}
				lastEarningsBar.setVisible(false); lastEarningsBar.setVisible(true);
			}
		});
		deleteall.setFont(new Font("굴림", Font.PLAIN, 17));
		deleteall.setBackground(SystemColor.control);
		deleteall.setBounds(17, 20, 180, 27);
		lastEarningsBar.add(deleteall);
		
		/* 메뉴수정 창에서 돌아가기 버튼 */
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
				
				/*시간 셋*/
			}
		});
		backto_setMenu.setFont(new Font("굴림", Font.PLAIN, 17));
		setMenuBar.add(backto_setMenu);
		
		
		/* 메뉴 삭제 창에서 돌아가기 버튼 */
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
				
			}
		});
		backto_deleteMenu.setFont(new Font("굴림", Font.PLAIN, 17));
		backto_deleteMenu.setBounds(819, 16, 105, 27);
		deleteMenuBar.add(backto_deleteMenu);
		
		/* 메뉴 수정창의 라벨 */
		JLabel label_5 = new JLabel("\uBA54\uB274 \uC774\uB984\uC740 8\uC790 \uC774\uD558\uB97C \uAD8C\uC7A5\uD569\uB2C8\uB2E4.");
		label_5.setForeground(new Color(205, 92, 92));
		label_5.setFont(new Font("굴림", Font.PLAIN, 13));
		label_5.setBounds(20, 145, 232, 26);
		setIntoPanel.add(label_5);
		
		/* 메뉴 수정창의 '수정하기' 버튼 */
		JButton reMenuBtn = new JButton("\uC218\uC815\uD558\uAE30");
		reMenuBtn.setBackground(new Color(216, 191, 216));
		reMenuBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				/*메뉴 수정 정보 저장 전에 입력 오류 검사*/
				if(reMenuNameField.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "입력된 메뉴 이름이 없습니다.","입력 오류",JOptionPane.WARNING_MESSAGE);  
					return;
				}
				
				/*숫자 입력 안했을경우*/
				if(reMenuPriceField.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "입력된 가격이 없습니다.","입력 오류",JOptionPane.WARNING_MESSAGE); 
					int_newMenuPrice.setText(""); 
					return;
				}
				
				/*입력받은 가격이 숫자가 맞는지 판별*/
				char tmp;
				for(i=0; i<reMenuPriceField.getText().length(); i++) {
					tmp = reMenuPriceField.getText().charAt(i);
					if(Character.isDigit(tmp)==false) {
						JOptionPane.showMessageDialog(null, "가격은 숫자로만 입력하세요.","입력 오류",JOptionPane.WARNING_MESSAGE);
						reMenuPriceField.setText("");
						return;
					}
				}
				
				//모든 경우가 흡족했을^^때
				menu[numtemp].reMenu(reMenuNameField.getText(), Integer.parseInt(reMenuPriceField.getText()));
				//메뉴를 후훗^^수정합니다. reMenu는 메뉴를 수정하는 메서드에용.
				updateMenu_DB updateMenu_DB = new updateMenu_DB(Integer.toString(numtemp), reMenuNameField.getText(), reMenuPriceField.getText());
				
				
				reMenuNameField.setText("");
				reMenuPriceField.setText("");
				/*메뉴 table 값 새로고침*/resetingMenuTable();
				
				JLabel label102 = new JLabel("["+menu[numtemp].getMenuName()+"-"+menu[numtemp].getMenuPrice()+"원]로 수정 완료");
				label102.setFont(new Font("굴림", Font.BOLD, 17));
				label102.setForeground(Color.MAGENTA);
				JOptionPane.showMessageDialog(null, label102, "", JOptionPane.INFORMATION_MESSAGE);
				
				setIntoPanel.setVisible(false);	
				setMenuBar.setVisible(false);
				setMenuBar.setVisible(true);

			}
		});
		reMenuBtn.setFont(new Font("굴림", Font.PLAIN, 18));
		reMenuBtn.setBounds(245, 140, 120, 45);
		setIntoPanel.add(reMenuBtn);
		
		/* 그저..그런 라벨 */
		JLabel label1_setMenuBar = new JLabel("\uC218\uC815\uD560 \uC815\uBCF4");
		label1_setMenuBar.setHorizontalAlignment(SwingConstants.CENTER);
		label1_setMenuBar.setFont(new Font("굴림", Font.BOLD, 18));
		label1_setMenuBar.setBounds(100, 10, 172, 35);
		setIntoPanel.add(label1_setMenuBar);		
		JLabel label2_setMenuBar = new JLabel("\uBA54\uB274 \uC774\uB984");
		label2_setMenuBar.setFont(new Font("굴림", Font.PLAIN, 18));
		label2_setMenuBar.setBounds(20, 53, 86, 35);
		setIntoPanel.add(label2_setMenuBar);	
		JLabel label3_setMenuBar = new JLabel("\uAC00\uACA9");
		label3_setMenuBar.setFont(new Font("굴림", Font.PLAIN, 18));
		label3_setMenuBar.setBounds(20, 95, 128, 41);
		setIntoPanel.add(label3_setMenuBar);
		
		
		/* 메뉴 수정창의 수정정보 필드 */
		reMenuNameField = new JTextField();
		reMenuNameField.setFont(new Font("굴림", Font.PLAIN, 17));
		reMenuNameField.setBounds(117, 55, 190, 35);
		setIntoPanel.add(reMenuNameField);
		
		reMenuNameField.setColumns(10);		
		reMenuPriceField = new JTextField();
		reMenuPriceField.setFont(new Font("굴림", Font.PLAIN, 17));
		reMenuPriceField.setBounds(117, 100, 116, 35);
		setIntoPanel.add(reMenuPriceField);
		reMenuPriceField.setColumns(10);

		
		/* 메뉴 수정창의 메뉴 table 스크롤 정의 */
		JScrollPane scrollPane_2 = new JScrollPane(table_menu);
		scrollPane_2.setBounds(69, 55, 348, 423);
		scrollPane_2.setEnabled(false);
		setMenuBar.add(scrollPane_2);
		

		/* Title라벨 속성 */
		JLabel TitleLabel = new JLabel("Dessert Cafe");
		TitleLabel.setBackground(new Color(255, 192, 203));
		TitleLabel.setForeground(new Color(218, 112, 214));
		TitleLabel.setFont(new Font("Lucida Fax", Font.BOLD, 32));
		TitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		TitleLabel.setBounds(357, 0, 222, 53);
		topBar.add(TitleLabel);
		
	
		/* 현재판매기록 label */
		JLabel showEarnings = new JLabel("\uB204\uC801 \uD310\uB9E4\uC218\uC785/\uC8FC\uBB38\uC218");
		showEarnings.setBackground(Color.WHITE);
		showEarnings.setHorizontalAlignment(SwingConstants.CENTER);
		showEarnings.setFont(new Font("굴림", Font.PLAIN, 17));
		showEarnings.setBounds(14, 59, 168, 29);
		topBar.add(showEarnings);
		
		/* 현재 판매수입이 표시되는 txt필드 */
		earnings = new JTextField();
		earnings.setBackground(Color.WHITE);
		earnings.setEditable(false);
		earnings.setHorizontalAlignment(SwingConstants.RIGHT);
		earnings.setFont(new Font("굴림", Font.PLAIN, 17));
		earnings.setBounds(185, 57, 142, 31);
		topBar.add(earnings);
		earnings.setColumns(10);
		
		/* 현재 주문량이 표시되는 txt필드 */
		amount = new JTextField();
		amount.setBackground(Color.WHITE);
		amount.setEditable(false);
		amount.setHorizontalAlignment(SwingConstants.RIGHT);
		amount.setFont(new Font("굴림", Font.PLAIN, 17));
		amount.setBounds(325, 57, 60, 31);
		topBar.add(amount);
		amount.setColumns(10);
		
		/* 실시간 누적 판매량 표시 */
		earnings.setText(Integer.toString(nowEarnings)+"원");
		amount.setText(Integer.toString(nowPays)+"건");
		
		JLabel label100 = new JLabel("주문 정보를 저장하였습니다.");
		label100.setFont(new Font("굴림", Font.PLAIN, 16));
		
		/* 현재 판매기록을 초기화하는 버튼 */
		JButton resetButton = new JButton("\uCD08\uAE30\uD654");
		resetButton.setBackground(new Color(255, 215, 0));
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectRecord();
				/*기록 초기화 메서드*/resetEarnings();
				earnings.setText(Integer.toString(nowEarnings)+"원");
				amount.setText(Integer.toString(nowPays)+"건");
				JOptionPane.showMessageDialog(null, label100,"초기화",JOptionPane.INFORMATION_MESSAGE);
				resetingMenuTable();
			
			}
		});
		resetButton.setFont(new Font("굴림", Font.PLAIN, 16));
		resetButton.setBounds(389, 59, 87, 27);
		topBar.add(resetButton);
		
		/* 현재 시간을 보여주는 txt필드 */
		timeField = new JTextField();
		timeField.setBackground(Color.WHITE);
		timeField.setFont(new Font("굴림", Font.BOLD, 17));
		timeField.setEditable(false);
		timeField.setHorizontalAlignment(SwingConstants.CENTER);
		timeField.setBounds(761, 14, 163, 32);
		topBar.add(timeField);
		timeField.setColumns(10);
		
		addTimeThread(); //타임스레드 추가
		
		
		/* bottomMain속 메뉴 추가 버튼 */
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
				
			}
		});
		btn_addMenu.setFont(new Font("굴림", Font.PLAIN, 17));
		btn_addMenu.setBounds(586, 56, 110, 31);
		topBar.add(btn_addMenu);
		
		/* bottomMain속 메뉴 수정 버튼 */
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
				
				
			}
		});
		btn_setMenu.setFont(new Font("굴림", Font.PLAIN, 17));
		btn_setMenu.setBounds(700, 56, 110, 31);
		topBar.add(btn_setMenu);
		
		/* bottomMain속 메뉴 삭제 버튼 */
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
				
			}
		});
		btn_deleteMenu.setFont(new Font("굴림", Font.PLAIN, 17));
		btn_deleteMenu.setBounds(814, 56, 110, 31);
		topBar.add(btn_deleteMenu);
		
		/* 주문 정보 삭제 스피너 */
		JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(1, 1, 10, 1));
		spinner.setFont(new Font("굴림", Font.PLAIN, 16));
		spinner.setBounds(215, 364, 49, 24);
		bottomMain.add(spinner);
		/* 주문 정보 삭제 라벨 */
		JLabel lblNo = new JLabel("\uC644\uB8CC\uD55C \uC8FC\uBB38\uC815\uBCF4 NO.");
		lblNo.setBackground(SystemColor.activeCaption);
		lblNo.setFont(new Font("굴림", Font.PLAIN, 16));
		lblNo.setBounds(56, 364, 158, 25);
		bottomMain.add(lblNo);
		/* 주문 정보 삭제 버튼 */
		JButton btn_del_order = new JButton("Del");
		btn_del_order.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(order[(int)(spinner.getValue())-1].isFilled()==true) {//해당 주문정보가 있으면
					for(i=0; i<orderLength; i++) { //값을 찾아 삭제
						if((int)spinner.getValue()==order[i].getOrdernum()) {
							order[i].deleteOrder();
						}
					}
					JOptionPane.showMessageDialog(null, spinner.getValue()+"번 주문을 완료하였습니다.","주문 정보 삭제 완료",JOptionPane.INFORMATION_MESSAGE);
				}else { //해당 주문정보가 없으면
					JOptionPane.showMessageDialog(null, "해당 번호의 주문 정보가 없습니다.","입력 오류",JOptionPane.WARNING_MESSAGE);
				}

				/*주문정보 table 값 새로고침*/resetOrderInfoTable();
				bottomMain.setVisible(false);
				bottomMain.setVisible(true);
				/*시간 셋*/
			}
		});
		btn_del_order.setBackground(new Color(255, 215, 0));
		btn_del_order.setFont(new Font("굴림", Font.PLAIN, 16));
		btn_del_order.setBounds(265, 363, 62, 27);
		bottomMain.add(btn_del_order);
		
		/*DB메뉴 가져오기*/pullupDBmenu();
		/*프로그램 초기 실행시 시간 초기화*/ 
		square.setVisible(false);
		square.setVisible(true);
		/* bottomMain 속 주문정보 table 새로고침 *///resetOrderInfoTable();
		
		
		/* 표 아래 이미지 첨부 */
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

		/*아이콘 이미지 설정*/
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image iconimg = kit.getImage("img/cf.png");
		frame.setIconImage(iconimg);	
	}
}