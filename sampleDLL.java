import java.util.*; 
public class sampleDLL 
{ 
static 
{ 
System.loadLibrary("sampleDLL"); 
} public static void main(String args[]) 
{ 
Scanner scanner = new Scanner(System.in); 
int num1 = 0, num2 = 0; 
System.out.println("DLL Addition Operation"); 
System.out.print("Enter first number: "); 
num1 = scanner.nextInt(); 
System.out.print("Enter second number: "); 
num2 = scanner.nextInt(); 
System.out.println("ADDITION IS: "+new sampleDLL().add(num1,num2)); 
System.out.println("SUBSTACTION IS: "+new sampleDLL().sub(num1,num2)); 
System.out.println("Multiplication IS: "+new sampleDLL().mul(num1,num2)); 
System.out.println("Square IS: "+new sampleDLL().sq(num1,num2));
} 
public native int add(int num1, int num2); 
public native int sub(int num1, int num2); 
public native int mul(int num1, int num2); 
public native int sq(int num1,int num2);
}

/* javac -h . sampleDLL.java
 gcc -c -fPIC -I//usr/lib/jvm/java-8-openjdk-amd64/include -I//usr/lib/jvm/java-8-openjdk-amd64/include/linux sampleDLL.c -o sampleDLL.o
 gcc -shared -fPIC -o libsampleDLL.so sampleDLL.o -lc
 java -Djava.library.path=. sampleDLL
*/
 
