import ncst.pgdst.*;

public class Roman{

public static void main(String a[]) throws IOException{

	int value=0;

        SimpleInput sin=new SimpleInput();
        SimpleOutput sout=new SimpleOutput();
	
	String s=new String();

	value=sin.readInt(); 
	
	
	while(value>0)
	{	
	if(value>999)
	{
	s+="M";
	value-=1000;
	}
	
	if(value>899)
	{
	s+="CM";
	value-=900;
	}

	if(value>499)
	{
	s+="D";
	value-=500;
	}
	
	if(value>399)
	{
	s+="CD";
	value-=400;
	}

	if(value>99)
	{
	s+="C";
	value-=100;
	}

	if(value>89)
	{
	s+="XC";
	value-=90;
	}

	if(value>49)
	{
	s+="L";
	value-=50;
	}

	if(value>9)
	{
	s+="X";
	value-=10;
	}

	
	if(value==9)
	{
	s+="IX";
	value-=9;
	}

	if(value>4)
	{
	s+="V";
	value-=5;
	}

	if(value>=1)
	{
	s+="I";
	value-=1;
	}
	}
System.out.println("the Roman..."+s);
}
}
