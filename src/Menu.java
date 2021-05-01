/* 메뉴 클래스 */
class Menu{

		/*메뉴 이름*/ private String menuName;
		/*메뉴 가격*/ private int menuPrice;
		
		/*초기 생성자*/
		public Menu() {
			this.menuName = null;
			this.menuPrice = 0;
		}
		
		/*사용자에 의한 수정*/
		public void reMenu(String name, int price){
			this.menuName = name;
			this.menuPrice = price;
		}
		
		/*메뉴 이름 수정 setter method*/
		public void setMenuName(String name) {
			this.menuName = name;
		}
		/*메뉴 가격 수정 setter method*/
		public void setMenuPrice(int price) {
			this.menuPrice = price;
		}
		
		/*메뉴 이름 getter*/
		public String getMenuName() {
			if(menuName!=null) {
				return this.menuName;	
			}else {
				return "";
			}
		}
		/*메뉴 가격 getter*/
		public int getMenuPrice() {
			return this.menuPrice;
		}
		
		/*메뉴 삭제 method*/
		public void deleteMenu() {
			this.menuName = null;
			this.menuPrice = 0;
		}
}