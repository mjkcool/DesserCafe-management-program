# Cafe management program - Dessert café🧁
## Introduction
#### This is a useful program for simple management📇. It is easily available to all.
* Complete: Jan, 2020
* Making period: 3 months
* Using component: language-java, GUI-java.swing, database-SQLite
* Purpose: for sales activities of persons(ex.students) who do not intend to profit.
<br><br><br>
## 🟢Functions
### Main screen
<img width=600px src=https://user-images.githubusercontent.com/53461080/85945847-5c5df980-b97b-11ea-921e-d654634fed89.PNG><br>
&nbsp;It shows information about menus, orders, on order, earnings and current time. It's completely empty now. Frame size is 956*659.
<br><br>
### 1. Register new menu
<img width=500px src=https://user-images.githubusercontent.com/53461080/86414454-eb15a200-bcfe-11ea-9100-24afd75dd789.PNG><br>
&nbsp;Click the button '메뉴 추가' at the top. Fill in the blank with the menu name and price and click the button '등록하기'.
<br>The limit is 12. Finally, a message of completion pop up. 
<br><br>

### 2. Modify menu
| Click modify button  | Complete |
| ------------- | ------------- |
| <img src=https://user-images.githubusercontent.com/53461080/86427592-802b9180-bd25-11ea-9cb2-b397393fd195.PNG>  | <img src=https://user-images.githubusercontent.com/53461080/86427595-80c42800-bd25-11ea-91f5-8661667febc8.PNG>  |

&nbsp;Click the button '메뉴 추가' on the top of the main screen. Next, click the button '수정' on the menu row you want to modify. a new panel appears to fill the modification information. If you click the button on a row without a menu, nothing will be done.
<br>Fill the menu information you want to modify in the blank and Click the button. The panel disappears and the menu list is updated.
<br><br>

### 3. Remove menu
| Click remove button  | Complete |
| ------------- | ------------- |
| <img src=https://user-images.githubusercontent.com/53461080/86427588-7efa6480-bd25-11ea-96e1-fb5d52a8e3cf.PNG>  | <img src=https://user-images.githubusercontent.com/53461080/86427589-7efa6480-bd25-11ea-84c7-45583480e149.PNG>  |

&nbsp;Click the button '메뉴 삭제' on the top of the main screen. And Click the button '삭제' on the menu row you want to delete. If you click the button on a row without a menu, nothing will be done.
<br>A message of completion pops up when complete.
<br><br>

### 4. Choose menu & Cancel order
| Click menu button  | Click cancel button |
| ------------- | ------------- |
| <img src=https://user-images.githubusercontent.com/53461080/86427591-7f92fb00-bd25-11ea-9e9e-a76000e5b929.PNG>  | <img src=https://user-images.githubusercontent.com/53461080/86464014-93605080-bd69-11ea-96d9-4a3db4c2314e.PNG>  |

&nbsp;You can accumulate the menu(name, price)to the order list by clicking the menu button on the main screen. Then, you will see the information you chose at the bottom of the main screen. There is no limit to the quantity, but there are only up to 2 types of menus that can be ordered. If the limit is exceeded, a warning message pops up.
<br>You can click the button on the bottom right to erase the selected menu information. Then, a message of cancellation pops up after the selection information is cleared
<br><br>

### 5. Register new order
| Click payment button  | Complete |
| ------------- | ------------- |
| <img src=https://user-images.githubusercontent.com/53461080/86427596-815cbe80-bd25-11ea-80fe-e022631796f0.PNG>  | <img src=https://user-images.githubusercontent.com/53461080/86427597-81f55500-bd25-11ea-9e4e-fb01e0d863fb.PNG>  |

&nbsp;Select a menu and click the button on the bottom right to register the information as an order. The selected menu information is erased and added to the order list with number as a message of complete pops up. **The second column has 2 additional orders registered.*
<br><br>

### 6. Process(remove) order
| Click delete button  | Complete |
| ------------- | ------------- |
| <img src=https://user-images.githubusercontent.com/53461080/86464010-922f2380-bd69-11ea-845e-8608b54c0b35.PNG>  | <img src=https://user-images.githubusercontent.com/53461080/86464012-92c7ba00-bd69-11ea-99af-db653f2aef93.PNG>  |

&nbsp;Select the number of the order to be processed as spinner, then click the button 'Del' to delete the order. If the order number does not exist, an explanatory message pops up. If not, the order will be deleted normally as a message of complete pops up.
<br><br>

### 7. Save earnings
<img width=500px src=https://user-images.githubusercontent.com/53461080/86464019-94917d80-bd69-11ea-8a90-2eb0e168bb99.PNG><br>
&nbsp;This movement is the same as the current earnings initialization. Earning is accumulated after the previous initialization with the order informations being registered.
<br>So you can click the button '초기화' at the top to reset the current sales record as soon as save it to the record. There is no limit to do this.
<br><br>

### 8. Show earnings records
<img width=500px src=https://user-images.githubusercontent.com/53461080/86463986-8c394280-bd69-11ea-97e7-262811984834.PNG><br>
&nbsp;If you click button '지난 판매기록 보기', you can see the saved previous earning informations of date, selling price and number of orders. 14 pieces of information are shown. When new information is stored with all 14 of them full, the oldest information is deleted to maintain the number of records. The rows of the table go up one by one every time new information is added.
<br><br>

### 9. Remove all of earnings records
<img width=500px src=https://user-images.githubusercontent.com/53461080/86464016-93f8e700-bd69-11ea-8848-acd92ff7fc0e.PNG><br>
&nbsp;You can delete all records by pressing the button '모든 기록 초기화' on the left. (initializes)
<br><br>
