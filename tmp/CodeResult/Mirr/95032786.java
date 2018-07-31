/*
 * Модель Джилса - Аттертона
 */

import java.lang.Math; //Математические функции
import java.util.Locale;
 

public class JAModelClass {
 private double A=0.0;
 private double C=0.0;
 private double K=0.0;
 private double ALPHA=0.0;
 private double MS=0.0;
 private double dt=0.0;
 private int N; //Множитель
 private int step; //Количество шагов
 private int f; //Частота
 private double H_max;
 private double t_max;
 //private double t;
 private double B0=0.0; //Начальное значение индукции
 private double H0=0.0; //Начальное значение напряженности
 private java.util.Vector<Double> M; //Намагниченность
 private java.util.Vector<Double> Time; //Архив времени
 private java.util.Vector<Double> H; //Напряженность
 private java.util.Vector<Double> B; //Индукция
 
 
 public JAModelClass(int LN, double Lt_max, int Lf, double LH_max)
 {
	N=LN;
	t_max=Lt_max;
	f=Lf; 
	H_max=LH_max;
	step=(int)(N); //Количество шагов
	M=new java.util.Vector();
	Time=new java.util.Vector(); //Архив времени
	H=new java.util.Vector(); //Напряженность
	B=new java.util.Vector(); //Индукция
	dt=(double)1/N;
 }
 /*
  * Установка параметра формы безгестерезисной прямой намагничивания
  */
 public void setA(double LA)
 {
  A=LA; 
 }
 
 /*
  * Установка параметра постоянной смещения доменных границы
  */
 public void setC(double LC)
 {
  C=LC;
 }
 
 /*
  * Установка параметра постоянной необратимой деформации доменных границ
  */
 public void setK(double LK)
 {
  K=LK; 
 }
 
 /*
  * Установка параметра эффективности поля
  */
 public void setALPHA(double LALPHA)
 {
  ALPHA=LALPHA;
 }
 
 /*
  * Установка начального значения индукции
  */
 public void setB0(double LB0)
 {
  B0=LB0;
 }
 
 /*
  * Установка начального значения напряженности
  */
 public void setH0(double LH0)
 {
  H0=LH0;
 }
 
 /*
  * Установка намагниченности насыщения
  */
 public void setMS(double LMS)
 {
  MS=LMS;
 }
 
 /*
  * H(t)
  */
 public double H_func(double t)
 {
  double ret_val;
  if (t==0)
   ret_val=H0;
  else
   ret_val=H0+H_max*java.lang.Math.sin(2*java.lang.Math.PI*f*t);
  return ret_val;
 }
 
 
 /*
  * deltaH(t)
  */
 public double deltaH(double t)
 {
  double ret_val;
  ret_val=H_func(t)-H_func(t-dt);
  return ret_val;
 }

/*
 * M_old(t) 
 */
 private double M_old()
 {
   double M_old;
   if (M.size()>0)
    M_old=M.get(M.size()-1);
   else
    M_old=0;
   return M_old; 
 }
 
 /*
  * M(t)
  */
 private double M_func(double t)
 {
  double ret_val=0.0;
  if (t==0)
   ret_val= (B_func(t)/4*java.lang.Math.PI*java.lang.Math.pow(10,-7))-H_func(t);
  else 
   ret_val= M_old()+dM_dH(t)*deltaH(t);
  //System.out.print("t="+Double.toString(t)+";M_old="+Double.toString(M_old())+";M="+Double.toString(ret_val)+";H="+Double.toString(H_func(t))+"\r\n");
  return ret_val;
 }

 /*
  * B(t)
  */
 private double B_func(double t)
 {
  double ret_val;
   if (t==0)
    ret_val=B0;
   else
    ret_val=4*java.lang.Math.PI*java.lang.Math.pow(10,-7)*(H_func(t)+ M_old());	   
  return ret_val;
 }
 
 /*
  * Man(t)
  */
 private double Man(double t)
 {
  double ret_val=0.0;
   ///if ((He(t)/A)<0.1)
   if (t==0)
    ret_val=MS*(He(t)/(3*A));
   else 
	ret_val=MS*(coth(He(t)/A)-(A/He(t)));   
   //System.out.print("t="+Double.toString(t)+"Man ret_val="+Double.toString(ret_val)+"\r\n");
  return ret_val;
 }
  
 /*
  * He(t)
  */
 private double He(double t)
 {
  double ret_val;
   ret_val=H_func(t)+ALPHA*M_old();
   //System.out.print("t="+Double.toString(t)+"He ret_val="+Double.toString(ret_val)+"\r\n");
  return ret_val;
 }

 /*
  * coth(x)
  */
 private double coth(double x)
 {
  double ret_val;
   //ret_val=java.lang.Math.cosh(x)/java.lang.Math.sinh(x);
  ret_val=1/java.lang.Math.tanh(x);
  return ret_val;
 }
 
 /*
  * Mirr(t)
  */
 private double Mirr(double t)
 {
  double ret_val;
  ret_val=(M_old()-C*Man(t))/(1-C);
 // System.out.print("t="+Double.toString(t)+"Mirr ret_val="+Double.toString(ret_val)+"\r\n");
  return ret_val;
 }
 
 /*
  * dMan_dHe(t)
  */
 private double dMan_dHe(double t)
 {
  double ret_val=0.0;
   //if ((He(t)/A)<0.1)
   if (t==0)
    ret_val=MS*(1/(3*A));   
   else 
    ret_val=(MS/A)*(1-java.lang.Math.pow(coth(He(t)/A),2)+java.lang.Math.pow(A/He(t),2));
  // System.out.print("t="+Double.toString(t)+"dMan_dHe ret_val="+Double.toString(ret_val)+"\r\n");
  return ret_val;
 }
 
 /*
  * dMirr_dHe(t)
  */
 private double dMirr_dHe(double t)
 {
  double ret_val;
   ret_val=(Man(t)-Mirr(t))/(K*sigma(t));
   if (ret_val<0)
	ret_val=0;
 //  System.out.print("t="+Double.toString(t)+"dMirr_dHe ret_val="+Double.toString(ret_val)+"\r\n");
  return ret_val;
 }
 
 /*
  * dM_dH(t)
  */
private double dM_dH(double t)
 {
  double ret_val;
  double a_b;
  double b_b;
  double c_b;
  a_b=(1-C)*dMirr_dHe(t)+C*dMan_dHe(t);
  b_b=1-ALPHA*C*dMan_dHe(t);
  c_b=ALPHA*(1-C)*dMirr_dHe(t);
  ret_val=a_b/(b_b-c_b);
//  System.out.print("t="+Double.toString(t)+"dM_dH ret_val="+Double.toString(ret_val)+"\r\n");
  return ret_val;
 }

 /*
  * sigma(t)
  */
 private double sigma(double t)
 {
  double ret_val;
  ret_val=java.lang.Math.signum(deltaH(t));
  //System.out.print("H_func(t+dt)="+Double.toString(H_func(t))+"; H_func(t)="+Double.toString(H_func(t-dt))+" deltaH="+Double.toString(deltaH(t))+"\r\n");
  //System.out.print("ret_val="+Double.toString(ret_val)+"\r\n");
  return ret_val;
 }

 /*
  * dH(t)
  */
private double dH(double t)
 {
	double ret_val;
	ret_val=1;
   // ret_val=2*java.lang.Math.PI*f*H_max*java.lang.Math.cos(2*java.lang.Math.PI*f*t);
    return ret_val;
 }

 
 /*
  * Решение
  */
 public void solve()
 {
   double t=0.0;
   int j=0;
   //Очистка векторов
   M.clear();
   Time.clear();
   H.clear();
   B.clear();
   
   //Расчет первого значения
   M.add(Double.valueOf(M_func(t)));
   Time.add(Double.valueOf(t));
   H.add(Double.valueOf(H_func(t)));
   B.add(Double.valueOf(B_func(t)));
   j++;
   t=t+dt; 
    
   while (t<t_max)
   {
	t=t+dt; //(double)j/N;
    Time.add(t);
    M.add(Double.valueOf(M_func(t)));
    Time.add(Double.valueOf(t));
    H.add(Double.valueOf(H_func(t)));
    B.add(Double.valueOf(B_func(t)));
    j++;
   }
 }

 
 /*
  *  Результат M
  */
 public java.util.Vector<Double> getM()
 {
  return M;	 
 }
 
 /*
  *  Результат H
  */
 public java.util.Vector<Double> getH()
 {
  return H;	 
 }
 
 /*
  *  Результат B
  */
 public java.util.Vector<Double> getB()
 {
  return B;	 
 }
 
 /*
  *  Результат Time
  */
 public java.util.Vector<Double> getTime()
 {
  return Time;	 
 }

}
