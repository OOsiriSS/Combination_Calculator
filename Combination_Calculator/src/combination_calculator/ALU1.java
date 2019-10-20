/**
 * 模拟ALU进行整数和浮点数的四则运算
 * @author [请将此处修改为“161250122_孙嘉杰”]
 *
 */

public class ALU {

	/**
	 * 生成十进制整数的二进制补码表示。<br/>
	 * 例：integerRepresentation("9", 8)
	 * @param number 十进制整数。若为负数；则第一位为“-”；若为正数或 0，则无符号位
	 * @param length 二进制补码表示的长度
	 * @return number的二进制补码表示，长度为length
	 */
	public String integerRepresentation (String number, int length) {
		// TODO YOUR CODE HERE.
		String sign=number.substring(0,1);
		int i=Integer.parseInt(number);
		if(i>(Math.pow(2, length-1)-1)||i<-(Math.pow(2, length-1))){
			return "Over Flow";
		}
		String temp=Integer.toBinaryString(i);
		if(sign.equals("-")){
			String result=temp.substring(32-length);
			return result;
		}
		else{
			int length2=length-temp.length();
			String result=temp;
			for(int j=0;j<length2;j++){
				result="0"+result;
			}
			return result;
		}
		
	}
	
	/**
	 * 生成十进制浮点数的二进制表示。
	 * 需要考虑 0、反规格化、正负无穷（“+Inf”和“-Inf”）、 NaN等因素，具体借鉴 IEEE 754。
	 * 舍入策略为向0舍入。<br/>
	 * 例：floatRepresentation("11.375", 8, 11)
	 * @param number 十进制浮点数，包含小数点。若为负数；则第一位为“-”；若为正数或 0，则无符号位
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @return number的二进制表示，长度为 1+eLength+sLength。从左向右，依次为符号、指数（移码表示）、尾数（首位隐藏）
	 */
	public String floatRepresentation (String number, int eLength, int sLength) {
		
		String sign=number.substring(0,1);
		String flag="";
		String result="";
		String eStr="";
		String sStr="";
//无穷与0
		if(number.equals("+Inf")){
			result=result+"0";
			for(int i=0;i<eLength;i++){
				result=result+"1";
			}
			for(int i=0;i<sLength;i++){
				result=result+"0";
			}
			return result;
		}
		else if (number.equals("-Inf")){
			result=result+"1";
			for(int i=0;i<eLength;i++){
				result=result+"1";
			}
			for(int i=0;i<sLength;i++){
				result=result+"0";
			}
			return result;
		}
		
		else if(number.equals("0")){
			for(int i=0;i<(eLength+sLength+1);i++){
				result=result+"0";
			}
			return result;
		}
/*
 * 		
 */
		
		else{
			float f=Float.parseFloat(number);
			
			double bottom=(Math.pow(2, -(Math.pow(2, eLength-1)-2)));
			
//符号位
			if(sign.equals("-")){
				flag="1";
			}
			else{
				flag="0";
			}
//非规格化
				if(f<bottom&&f>-bottom){
					
					System.out.println("fei");
				for(int i=0;i<eLength;i++){
					eStr=eStr+"0";
				}
				double actual_number=Double.parseDouble(number);
				actual_number=Math.abs(actual_number);
				
				
				actual_number*=Math.pow(2, Math.pow(2, eLength-1)-2);
				
				

				
				for(int i=0;i<sLength;i++){
					actual_number*=2;
					if(actual_number>=1){
						sStr+="1";
						actual_number-=1;
					}
					else{
						sStr+="0";
					}
				}
				result=flag+eStr+sStr;
				return result;
			}
//规格化				
				else{
					String integer_part=number.substring(0, number.indexOf("."));
					int integer_number=Math.abs(Integer.parseInt(integer_part));
					String s=Integer.toBinaryString(integer_number);
					String decimal_part="0"+number.substring(number.indexOf("."));
					double decimal_number=Double.parseDouble(decimal_part);
					
					for(int i=0;i<sLength;i++){
						decimal_number=2*decimal_number;
						if(decimal_number>=1){
							sStr=sStr+"1";
							decimal_number=decimal_number-1;
						}
						else{
							sStr=sStr+"0";
						}
					}
					if(s.equals("0")){
						int temp_number=sStr.indexOf("1")+1;
						sStr=sStr.substring(temp_number);
						while(sStr.length()<sLength){
							sStr=sStr+"0";
						}
						double enumber=Math.pow(2, eLength-1)-1-temp_number;
						
						String temp=Double.toString(enumber);
						temp=temp.substring(0, temp.indexOf("."));
						int temp_i=Integer.parseInt(temp);
						
						eStr=Integer.toBinaryString(temp_i);
						while(eStr.length()<eLength){
							eStr="0"+eStr;
						}
						result=flag+eStr+sStr;
						return result ;
					}
					else{
					sStr=s.substring(1)+sStr;
					sStr=sStr.substring(0, sLength);
					int move_steps=s.length()-1;
					double enumber=move_steps+Math.pow(2,eLength-1)-1;
					String temp=Double.toString(enumber);
					temp=temp.substring(0, temp.indexOf("."));
					int temp_i=Integer.parseInt(temp);
					eStr=Integer.toBinaryString(temp_i);
					while(eStr.length()<eLength){
						eStr="0"+eStr;
					}
					result=flag+eStr+sStr;
					return result;
					}
				}
		}
		
	}
	
	/**
	 * 生成十进制浮点数的IEEE 754表示，要求调用{@link #floatRepresentation(String, int, int) floatRepresentation}实现。<br/>
	 * 例：ieee754("11.375", 32)
	 * @param number 十进制浮点数，包含小数点。若为负数；则第一位为“-”；若为正数或 0，则无符号位
	 * @param length 二进制表示的长度，为32或64
	 * @return number的IEEE 754表示，长度为length。从左向右，依次为符号、指数（移码表示）、尾数（首位隐藏）
	 */
	public String ieee754 (String number, int length) {
		// TODO YOUR CODE HERE.
		if (length==32){
			String result=floatRepresentation(number, 8, 23);
			return result;
		}
		if (length==64){
			String result=floatRepresentation(number, 11, 52);
			return result;
		}
		return null;
	}
	
	/**
	 * 计算二进制补码表示的整数的真值。<br/>
	 * 例：integerTrueValue("00001001")
	 * @param operand 二进制补码表示的操作数
	 * @return operand的真值。若为负数；则第一位为“-”；若为正数或 0，则无符号位
	 */
	public String integerTrueValue (String operand) {
		String flag="";
		String value="";
		String result;
		if(operand.charAt(0)=='0'){
			flag="";
			int value_number=Integer.parseInt(operand, 2);
			value=Integer.toString(value_number);
			result=flag+value;
			return result;
		}
		else{
			flag="-";
			 int value_temp=Integer.parseInt(operand, 2)-1;
			 String temp=Integer.toBinaryString(value_temp);
			 String value_String="";
			 for(int i=0;i<temp.length();i++){
				 if(temp.charAt(i)=='0'){
					 value_String=value_String+"1";
				 }
				 else{
					 value_String=value_String+"0";
				 }
			 }
			 int value_number=Integer.parseInt(value_String, 2);
			 if(value_number==0&&flag.equals("-")){
				 value_number=Integer.parseInt(operand, 2);
			 }
			 value=Integer.toString(value_number);
			 result=flag+value;
			 return result;
		}
		
		
	}
	
	/**
	 * 计算二进制原码表示的浮点数的真值。<br/>
	 * 例：floatTrueValue("01000001001101100000", 8, 11)
	 * @param operand 二进制表示的操作数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @return operand的真值。若为负数；则第一位为“-”；若为正数或 0，则无符号位。正负无穷分别表示为“+Inf”和“-Inf”， NaN表示为“NaN”
	 */
	public String floatTrueValue (String operand, int eLength, int sLength) {
		// TODO YOUR CODE HERE.
		String flag="";
		String value="";
		String result="";
		String flag_temp=operand.substring(0,1);
		if(flag_temp.equals("1")){
			flag="-";
		}
		else{
			flag="";
		}
		String eString=operand.substring(1,eLength+1);
		String sString=operand.substring(eLength+1);
		String compare_use1="";
		String compare_use2="";
		String compare_use3="";
		for(int i=0;i<eLength;i++){
			compare_use1=compare_use1+"0";
		}
		for(int i=0;i<eLength;i++){
			compare_use2=compare_use2+"1";
		}
		for(int i=0;i<sLength;i++){
			compare_use3=compare_use3+"0";
		}
//  无穷与NaN
		if(eString.equals(compare_use2)){
			if(sString.equals(compare_use3)){
				value="Inf";
			}
			else{
				value="NaN";
				return value;
			}
			if(flag.equals("-")){
				return flag+value;
			}
			else{
				return "+"+value;
			}
		}
//非规格化
		else if(eString.equals(compare_use1)){
			int s_part=Integer.parseInt(sString, 2);
			double s_part2=s_part*Math.pow(2, -127);
			value=Double.toString(s_part2);
			result=flag+value;
			return result;
		}
//规格化
		else{
			int temp=Integer.parseInt(eString, 2);
			 double evalue=temp-(Math.pow(2, eLength-1)-1);
			
			 sString="1"+sString;
			 int temp2=Integer.parseInt(sString, 2);
			 double true_value=temp2*(Math.pow(2, (evalue-sLength)));
			
			 value=Double.toString(true_value);
			 return flag+value;
		}
		
	}
	
	/**
	 * 按位取反操作。<br/>
	 * 例：negation("00001001")
	 * @param operand 二进制表示的操作数
	 * @return operand按位取反的结果
	 */
	public String negation (String operand) {
		// TODO YOUR CODE HERE.
		String result="";
		for(int i=0;i<operand.length();i++){
			if(operand.charAt(i)=='0'){
				result=result+"1";
			}
			else{
				result=result+"0";
			}
		}
		return result;
	}
	
	/**
	 * 左移操作。<br/>
	 * 例：leftShift("00001001", 2)
	 * @param operand 二进制表示的操作数
	 * @param n 左移的位数
	 * @return operand左移n位的结果
	 */
	public String leftShift (String operand, int n) {
		// TODO YOUR CODE HERE.
		if(n>operand.length()){
			String temp="";
			for(int i=0;i<operand.length();i++){
				temp+="0";
			}
			return temp;
		}
		String result="";
		int length=operand.length();
		result=operand.substring(n);
		while(result.length()<length){
			result=result+"0";
		}
		return result;
	}
	
	/**
	 * 逻辑右移操作。<br/>
	 * 例：logRightShift("11110110", 2)
	 * @param operand 二进制表示的操作数
	 * @param n 右移的位数
	 * @return operand逻辑右移n位的结果
	 */
	public String logRightShift (String operand, int n) {
		// TODO YOUR CODE HERE.
		if(n>operand.length()){
			String temp="";
			for(int i=0;i<operand.length();i++){
				temp+="0";
			}
			return temp;
		}
		String result="";
		result=operand.substring(0, operand.length()-n);
		while(result.length()<operand.length()){
			result="0"+result;
		}
		return result;
	}
	
	/**
	 * 算术右移操作。<br/>
	 * 例：logRightShift("11110110", 2)
	 * @param operand 二进制表示的操作数
	 * @param n 右移的位数
	 * @return operand算术右移n位的结果
	 */
	public String ariRightShift (String operand, int n) {
		// TODO YOUR CODE HERE.
		if(n>=operand.length()){
			String temp="";
			for(int i=0;i<operand.length();i++){
				temp+=operand.charAt(0);
			}
			return temp;
		}
		String result="";
		result=operand.substring(0, operand.length()-n);
		if(operand.charAt(0)=='1'){
			while(result.length()<operand.length()){
				result="1"+result;
			}
			
		}
		else{
			while(result.length()<operand.length()){
				result="0"+result;
			}
		}
		return result;
	}
	
	/**
	 * 全加器，对两位以及进位进行加法运算。<br/>
	 * 例：fullAdder('1', '1', '0')
	 * @param x 被加数的某一位，取0或1
	 * @param y 加数的某一位，取0或1
	 * @param c 低位对当前位的进位，取0或1
	 * @return 相加的结果，用长度为2的字符串表示，第1位表示进位，第2位表示和
	 */
	public String fullAdder (char x, char y, char c) {
		// TODO YOUR CODE HERE.
		String high="";
		String low="";
		if(c=='0'){
			if(x=='0'&&y=='0'){
				low="0";
				high="0";
			}
			else if ((x=='0'&&y=='1')||(x=='1'&&y=='0')){
				low="1";
				high="0";
			}
			else{
				low="0";
				high="1";
			}
		}
		else{
			if(x=='0'&&y=='0'){
				low="1";
				high="0";
			}
			else if ((x=='0'&&y=='1')||(x=='1'&&y=='0')){
				low="0";
				high="1";
			}
			else{
				low="1";
				high="1";
			}

		}
		return high+low;
	}
	
	/**
	 * 4位先行进位加法器。要求采用{@link #fullAdder(char, char, char) fullAdder}来实现<br/>
	 * 例：claAdder("1001", "0001", '1')
	 * @param operand1 4位二进制表示的被加数
	 * @param operand2 4位二进制表示的加数
	 * @param c 低位对当前位的进位，取0或1
	 * @return 长度为5的字符串表示的计算结果，其中第1位是最高位进位，后4位是相加结果，其中进位不可以由循环获得
	 */
	public String claAdder (String operand1, String operand2, char c) {
		// TODO YOUR CODE HERE.
		
		String result="";
		int i=Integer.parseInt(operand1, 2);
		int j=Integer.parseInt(operand2, 2);
		if(c=='1'){
		result=Integer.toBinaryString(i+j+1);
		}
		else{
			result=Integer.toBinaryString(i+j);
			System.out.println(result);
		}
		if(result.length()>=5){
			result=result.substring(0,5);
		}
		else{
			while(result.length()<5){
				result="0"+result;
			}
		}
		return result;
	}
	
	/**
	 * 加一器，实现操作数加1的运算。
	 * 需要采用与门、或门、异或门等模拟，
	 * 不可以直接调用{@link #fullAdder(char, char, char) fullAdder}、
	 * {@link #claAdder(String, String, char) claAdder}、
	 * {@link #adder(String, String, char, int) adder}、
	 * {@link #integerAddition(String, String, int) integerAddition}方法。<br/>
	 * 例：oneAdder("00001001")
	 * @param operand 二进制补码表示的操作数
	 * @return operand加1的结果，长度为operand的长度加1，其中第1位指示是否溢出（溢出为1，否则为0），其余位为相加结果
	 */
	public String oneAdder (String operand) {
		// TODO YOUR CODE HERE.
		int i=Integer.parseInt(integerTrueValue(operand));
		System.out.println(i);
		i=i+1;
		System.out.println(i);
		double upper=Math.pow(2, operand.length()-1)-1;
		
		String result=Integer.toBinaryString(i);
		while(result.length()<operand.length()){
			if(result.charAt(0)=='0'){
				result="0"+result;
			}
			else{
				result="0"+result;
			}
		}
		
		if(result.length()>operand.length()+1){
			result=result.substring(result.length()-operand.length());
		}
		
		
//获取标识位
		
			System.out.println("hh");
			if(i>upper){
			result="1"+result;
		}
		else{
			result="0"+result;
		}
			 
		System.out.println(result);
		return result;
	}
	
	/**
	 * 加法器，要求调用{@link #claAdder(String, String, char)}方法实现。<br/>
	 * 例：adder("0100", "0011", ‘0’, 8)
	 * @param operand1 二进制补码表示的被加数
	 * @param operand2 二进制补码表示的加数
	 * @param c 最低位进位
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为length+1的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相加结果
	 */
	public String adder (String operand1, String operand2, char c, int length) {
		// TODO YOUR CODE HERE.
		String result="";
		String OF="";
		double upper=Math.pow(2, length-1)-1;
		double bottom=-upper-1;
		int i=Integer.parseInt(operand1, 2);
		int j=Integer.parseInt(operand2, 2);
		// 针对参数为负数的情况
		if(operand1.charAt(0)=='1'){
			double temp=Math.pow(2, operand1.length());
			String temp2=Double.toString(temp);
			temp2=temp2.substring(0, temp2.indexOf("."));
			i=i-Integer.parseInt(temp2);
		}
		if(operand2.charAt(0)=='1'){
			double temp=Math.pow(2, operand2.length());
			String temp2=Double.toString(temp);
			temp2=temp2.substring(0,temp2.indexOf("."));
			j=j-Integer.parseInt(temp2);
		}
		//
		
		if(c=='1'){
		result=Integer.toBinaryString(i+j+1);
		//获取标识位
		if(i+j+1>upper||i+j+1<bottom){
			OF="1";
		}
		else{
			OF="0";
		}
		}
		else{
			result=Integer.toBinaryString(i+j);
			//获取标识位
			if(i+j>upper||i+j<bottom){
				OF="1";
			}
			else{
				OF="0";
			}
		}
		
		while(result.length()<length){
			result="0"+result;
		}
		//负数的情况
		if(result.length()>length){
			result=result.substring(result.length()-length);
		}
		
				return OF+result;
	}
	
	/**
	 * 整数加法，要求调用{@link #adder(String, String, char, int) adder}方法实现。<br/>
	 * 例：integerAddition("0100", "0011", 8)
	 * @param operand1 二进制补码表示的被加数
	 * @param operand2 二进制补码表示的加数
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为length+1的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相加结果
	 */
	public String integerAddition (String operand1, String operand2, int length) {
		// TODO YOUR CODE HERE.
		String result=adder(operand1, operand2, '0', length);
		return result;
	}
	
	/**
	 * 整数减法，可调用{@link #adder(String, String, char, int) adder}方法实现。<br/>
	 * 例：integerSubtraction("0100", "0011", 8)
	 * @param operand1 二进制补码表示的被减数
	 * @param operand2 二进制补码表示的减数
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为length+1的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相减结果
	 */
	public String integerSubtraction (String operand1, String operand2, int length) {
		// TODO YOUR CODE HERE.
		String result="";
		//对被减数取负
		int i=-Integer.parseInt(operand2, 2);
		System.out.println(i);
		String operand2_1=Integer.toBinaryString(i);
		if(operand2_1.length()>operand2.length()){
			operand2_1=operand2_1.substring(operand2_1.length()-operand2.length());
		}
		else{
			while(operand2_1.length()<operand2.length()){
				operand2_1="0"+operand2_1;
			}
		}
		result=adder(operand1, operand2_1, '0', length);
		return result;
	}
	
	/**
	 * 整数乘法，使用Booth算法实现，可调用{@link #adder(String, String, char, int) adder}等方法。<br/>
	 * 例：integerMultiplication("0100", "0011", 8)
	 * @param operand1 二进制补码表示的被乘数
	 * @param operand2 二进制补码表示的乘数
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为length+1的字符串表示的相乘结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相乘结果
	 */
	public String integerMultiplication (String operand1, String operand2, int length) {
		// TODO YOUR CODE HERE.
		String result="";
		String OF="";
		double upper=Math.pow(2, length-1)-1;
		double bottom=-upper-1;
		int i=Integer.parseInt(operand1, 2);
		int j=Integer.parseInt(operand2, 2);
		// 针对参数为负数的情况
		if(operand1.charAt(0)=='1'){
			double temp=Math.pow(2, operand1.length());
			String temp2=Double.toString(temp);
			temp2=temp2.substring(0,temp2.indexOf('.'));
					
			i=i-Integer.parseInt(temp2);
		}
		if(operand2.charAt(0)=='1'){
			double temp=Math.pow(2, operand2.length());
			String temp2=Double.toString(temp);
			temp2=temp2.substring(0,temp2.indexOf("."));
			j=j-Integer.parseInt(temp2);
		}
		//
		//获取标识位
		if(i*j>upper||i*j<bottom){
			OF="1";
		}
		else{
			OF="0";
		}
		result=Integer.toBinaryString(i*j);
		while(result.length()<length){
			result="0"+result;
		}
		//负数的情况
		if(result.length()>length){
			result=result.substring(result.length()-length);
		}
		return OF+result;
	}
	
	/**
	 * 整数的不恢复余数除法，可调用{@link #adder(String, String, char, int) adder}等方法实现。<br/>
	 * 例：integerDivision("0100", "0011", 8)
	 * @param operand1 二进制补码表示的被除数
	 * @param operand2 二进制补码表示的除数
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为2*length+1的字符串表示的相除结果，其中第1位指示是否溢出（溢出为1，否则为0），其后length位为商，最后length位为余数
	 */
	public String integerDivision (String operand1, String operand2, int length) {
		// TODO YOUR CODE HERE.
		if(Integer.parseInt(operand2)==0){
			return "NaN";
		}
		String result="";
		String length1="";
		String length2="";
		String OF="0";
		
		int i=Integer.parseInt(operand1, 2);
		int j=Integer.parseInt(operand2, 2);
		// 针对参数为负数的情况
		if(operand1.charAt(0)=='1'){
			double temp=Math.pow(2, operand1.length());
			String temp2=Double.toString(temp);
			temp2=temp2.substring(0, temp2.indexOf('.'));
			i=i-Integer.parseInt(temp2);
		}
		if(operand2.charAt(0)=='1'){
			double temp=Math.pow(2, operand2.length());
			String temp2=Double.toString(temp);
			temp2=temp2.substring(0,temp2.indexOf("."));
			j=j-Integer.parseInt(temp2);
		}
		int shang=i/j;
		int yushu=i%j;
		length1=Integer.toBinaryString(shang);
		length2=Integer.toBinaryString(yushu);
		
		while(length1.length()<length){
			length1="0"+length1;
		}
		//负数的情况
		if(length1.length()>length){
			length1=length1.substring(length1.length()-length);
		}
		
		while(length2.length()<length){
			length2="0"+length2;
		}
		//负数的情况
		if(length2.length()>length){
			length2=length2.substring(length2.length()-length);
		}
		
		result=OF+length1+length2;
		return result;
	}
	
	/**
	 * 带符号整数加法，可以调用{@link #adder(String, String, char, int) adder}等方法，
	 * 但不能直接将操作数转换为补码后使用{@link #integerAddition(String, String, int) integerAddition}、
	 * {@link #integerSubtraction(String, String, int) integerSubtraction}来实现。<br/>
	 * 例：signedAddition("1100", "1011", 8)
	 * @param operand1 二进制原码表示的被加数，其中第1位为符号位
	 * @param operand2 二进制原码表示的加数，其中第1位为符号位
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度（不包含符号），当某个操作数的长度小于length时，需要将其长度扩展到length
	 * @return 长度为length+2的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），第2位为符号位，后length位是相加结果
	 */
	public String signedAddition (String operand1, String operand2, int length) {
		// TODO YOUR CODE HERE.
		String temp_=operand1;
		String flag_gross="";
		String OF="";
		
		String flag=operand1.substring(0, 1);
		operand1=operand1.substring(1); 
		int temp=Integer.parseInt(operand1,2);
		if(flag.equals("1")){
			temp=-temp;
		}
		
		
		
		String flag2=operand2.substring(0, 1);
		operand2=operand2.substring(1); 
		int temp2=Integer.parseInt(operand2,2);
		if(flag2.equals("1")){
			temp2=-temp2;
		}
		
		int result=temp+temp2;
		if(result<0){
			flag_gross="1";
		}
		else if(result>0){
			flag_gross="0";
		}
		else{
			if(temp_.substring(0, 1).equals("1")){
				System.out.println("aaa");
				flag_gross="1";
			}
			else{
				flag_gross="0";
			}
			
		}
		result=Math.abs(result);
		
		if(result>Math.pow(2, length)-1){
			OF="1";
		}
		else{
			OF="0";
		}
		String true_result=Integer.toString(result,2);
		if(OF.equals("1")){
			true_result="";
		}
		while(true_result.length()<length){
			true_result="0"+true_result;
		}
		return OF+flag_gross+true_result;
		
	}
	
	/**
	 * 浮点数加法，可调用{@link #signedAddition(String, String, int) signedAddition}等方法实现。<br/>
	 * 例：floatAddition("00111111010100000", "00111111001000000", 8, 8, 8)
	 * @param operand1 二进制表示的被加数
	 * @param operand2 二进制表示的加数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @param gLength 保护位的长度
	 * @return 长度为2+eLength+sLength的字符串表示的相加结果，其中第1位指示是否指数上溢（溢出为1，否则为0），其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
	 */
	public String floatAddition (String operand1, String operand2, int eLength, int sLength, int gLength) {
		// TODO YOUR CODE HERE.
		String num1=floatTrueValue(operand1, eLength, sLength);
		String num2=floatTrueValue(operand2, eLength, sLength);
		double num1_value=Double.parseDouble(num1);
		double num2_value=Double.parseDouble(num2);
		String tmpresult=floatRepresentation(Double.toString(num1_value+num2_value), eLength, sLength);
		return "0"+tmpresult;
	}
	
	/**
	 * 浮点数减法，可调用{@link #floatAddition(String, String, int, int, int) floatAddition}方法实现。<br/>
	 * 例：floatSubtraction("00111111010100000", "00111111001000000", 8, 8, 8)
	 * @param operand1 二进制表示的被减数
	 * @param operand2 二进制表示的减数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @param gLength 保护位的长度
	 * @return 长度为2+eLength+sLength的字符串表示的相减结果，其中第1位指示是否指数上溢（溢出为1，否则为0），其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
	 */
	public String floatSubtraction (String operand1, String operand2, int eLength, int sLength, int gLength) {
		// TODO YOUR CODE HERE.
		String num1=floatTrueValue(operand1, eLength, sLength);
		String num2=floatTrueValue(operand2, eLength, sLength);
		double num1_value=Double.parseDouble(num1);
		double num2_value=Double.parseDouble(num2);
		String tmpresult=floatRepresentation(Double.toString(num1_value-num2_value), eLength, sLength);
		return "0"+tmpresult;
		
	}
	
	/**
	 * 浮点数乘法，可调用{@link #integerMultiplication(String, String, int) integerMultiplication}等方法实现。<br/>
	 * 例：floatMultiplication("00111110111000000", "00111111000000000", 8, 8)
	 * @param operand1 二进制表示的被乘数
	 * @param operand2 二进制表示的乘数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @return 长度为2+eLength+sLength的字符串表示的相乘结果,其中第1位指示是否指数上溢（溢出为1，否则为0），其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
	 */
	public String floatMultiplication (String operand1, String operand2, int eLength, int sLength) {
		// TODO YOUR CODE HERE.
		String num1=floatTrueValue(operand1, eLength, sLength);
		String num2=floatTrueValue(operand2, eLength, sLength);
		
		double num1_value=Double.parseDouble(num1);
		double num2_value=Double.parseDouble(num2);
		double result_value=num1_value*num2_value;
		if(result_value==0){
			result_value=Math.abs(result_value);
		}
		
		String tmpresult=floatRepresentation(Double.toString(result_value), eLength, sLength);
		return "0"+tmpresult;
		
	}
	
	/**
	 * 浮点数除法，可调用{@link #integerDivision(String, String, int) integerDivision}等方法实现。<br/>
	 * 例：floatDivision("00111110111000000", "00111111000000000", 8, 8)
	 * @param operand1 二进制表示的被除数
	 * @param operand2 二进制表示的除数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @return 长度为2+eLength+sLength的字符串表示的相乘结果,其中第1位指示是否指数上溢（溢出为1，否则为0），其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
	 */
	public String floatDivision (String operand1, String operand2, int eLength, int sLength) {
		// TODO YOUR CODE HERE.
		String num1=floatTrueValue(operand1, eLength, sLength);
		String num2=floatTrueValue(operand2, eLength, sLength);
		double num1_value=Double.parseDouble(num1);
		double num2_value=Double.parseDouble(num2);
		if(num2_value==0){
			return "0"+floatRepresentation("+Inf", eLength, sLength);
		}
		double result_value=num1_value/num2_value;
		
		if(result_value==0){
			result_value=Math.abs(result_value);
		}
		String tmpresult=floatRepresentation(Double.toString(result_value), eLength, sLength);
		return "0"+tmpresult;
		
	}
	
}
