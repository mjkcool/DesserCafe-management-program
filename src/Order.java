import java.util.Arrays;

class Order{	
		/*주문할 수 있는 메뉴 종류의 한도(임시값 저장)*/ 
		static final int ordermenuLength = 2;
		
		/*이 인스턴스가 비어있는지에 대한 여부*/ private boolean isFilled = false;
		/*[sameline▼]주문메뉴 이름 배열*/ private String orderName[] = new String[ordermenuLength];
		/*[sameline◆]주문메뉴 수량 배열*/ private int orderQuantity[] = new int[ordermenuLength];
		/*[sameline▲]결제금액 배열*/ private int finalPrice;
		/*주문(진동벨)번호*/ private int ordernum;
		
		
		/*초기 생성자 method, 주문 정보 삭제 메서드*/
		public Order() {
			for(int i=0; i<ordermenuLength; i++) {
				orderName[i] = "";
				orderQuantity[i] = 0;
			}
			finalPrice = 0;
			ordernum = 0;
			isFilled = false;
		}
		
		/*●◈▶▶주문 정보 추가 메서드◀◀◈●*/
		public void reOrder(String name[], int Quant[], int price, int ordernum) {
			isFilled = true;
			this.orderName[0] = name[0]; this.orderName[1] = name[1];
			this.orderQuantity[0] = Quant[0]; this.orderQuantity[1] = Quant[1];
			this.finalPrice = price; 
			this.ordernum = ordernum; //진동벨 번호
		}
		
		
		/*주문정보 삭제 메서드*/
		public void deleteOrder() {
			for(int i=0; i<ordermenuLength; i++) {
				orderName[i] = "";
				orderQuantity[i] = 0;
			}
			finalPrice = 0;
			ordernum = 0;
			isFilled = false;
		}

		
		/* 정보 차있는 유무 반환 */
		public boolean isFilled() {
			return isFilled;
		}

		/* 주문메뉴 반환 */
		public String getOrderName(int n) {
			return orderName[n];
		}

		/* 주문량 반환 */
		public int getOrderQuantity(int n) {
			return orderQuantity[n];
		}

		/* 총결제금액 반환 */
		public int getFinalPrice() {
			return finalPrice;
		}
		
		/* 주문번호 반환 */
		public int getOrdernum() {
			return ordernum;
		}
		
		/* 주문 번호 수정 */
		public int setOrdernum(int num) {
			return this.ordernum = num;
		}


		@Override
		public String toString() {
			return "Order [isFilled=" + isFilled + ", orderName=" + Arrays.toString(orderName) + ", orderQuantity="
					+ Arrays.toString(orderQuantity) + ", finalPrice=" + finalPrice + ", ordernum=" + ordernum + "]";
		}
		
		
	
}

