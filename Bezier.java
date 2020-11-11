import java.awt.*;
import java.applet.Applet;


public class Bezier extends Applet{
	Point[] cp; //punctele de control
	int numpoints=20; //numarul de puncte de control
	Image im;
	Graphics img;
	double k=.025;//pasul de timp folosit la desenarea curbei
	int moveflag=numpoints+1;//punctul de control deplasat
	Button restart;
	//TextField t1=new TextField("",20);
	//Button submit=new Button("Submit");
	
	public void init(){
		cp=new Point[numpoints];
		im= createImage(size().width,size().height);
		img=im.getGraphics();
		restart=new Button("Restart");
	    add(restart);
		//add(t1);
		//add(submit);
	}
	
	public void update(Graphics g){paint(g);}
	
	public void paint(Graphics g){
		setBackground(Color.white);
		img.setColor(Color.black);
		img.clearRect(0,0,size().width,size().height);
		//deseneaza punctele de control si poligonul de control
		for(int i=0;i<numpoints;i++){
			img.setColor(Color.red);
			img.fillOval(cp[i].x-2, cp[i].y-2,8,8);
			img.setColor(Color.blue);
			if(numpoints>1&&i<(numpoints-1))
				img.drawLine(cp[i].x,cp[i].y,cp[i+1].x,cp[i+1].y);
		}
		//calculeaza si deseneaza curba Bezier
		if(numpoints==20 && numpoints!=0){
			int x=cp[0].x, y=cp[0].y;
			img.setColor(Color.black);
			for(double t=k;t<=1+k;t+=k){
				Point p=Bernstein(cp,t);
				img.drawLine(x,y,p.x,p.y);
				x=p.x;
				y=p.y;
	        }
	
         }
         g.drawImage(im,0,0,this);
	}
	
	public long Factorial(int n){
		if(n>=1)
			return n*Factorial(n-1);
		else 
			return 1;
	}
	
	public long Combinari(int a, int b){
		if(b!=0 && b!=a)
			return Factorial(a)/(Factorial(b)*Factorial(a-b));
		else
			return 1;
	}
		
	
	
	
	
	
    public Point Bernstein(Point[] p,double t){
	//double x=(1-t)*(1-t)*(1-t)*(1-t)*p1.x+(4*t)*(1-t)*(1-t)*(1-t)*p2.x+(6*t*t)*(1-t)*(1-t)*p3.x+(4*t*t*t)*(1-t)*p4.x+t*t*t*t*p5.x;
	//double y=(1-t)*(1-t)*(1-t)*(1-t)*p1.y+(4*t)*(1-t)*(1-t)*(1-t)*p2.y+(6*t*t)*(1-t)*(1-t)*p3.y+(4*t*t*t)*(1-t)*p4.y+t*t*t*t*p5.y;
	//return new Point((int)Math.round(x),(int)Math.round(y));
	double x=0;
	double y=0;
	for(int q=0;q<numpoints;q++){
		x=x+Combinari(numpoints-1,q)*Math.pow(t,q)*Math.pow(1-t,numpoints-1-q)*p[q].x;
		y=y+Combinari(numpoints-1,q)*Math.pow(t,q)*Math.pow(1-t,numpoints-1-q)*p[q].y;
    }
	return new Point((int)Math.round(x),(int)Math.round(y));
}
public boolean action(Event e, Object o){
	if(e.target==restart){
		numpoints=0;
		repaint();
	return true;}
return false;}

public boolean mouseDown(Event evt, int x, int y){
	Point point=new Point(x,y);
	if(numpoints<20){
		cp[numpoints]=point;
		numpoints++;
	repaint();}
	else for(int i=0;i<numpoints;i++) for(int j=-2;j<3;j++) for(int l=-2;l<3;l++)
	if(point.equals(new Point(cp[i].x+j,cp[i].y+l))) moveflag=i;
return true;
}

public boolean mouseDrag(Event evt, int x, int y){
if(moveflag<numpoints){cp[moveflag].move(x,y);repaint();}return true;
}
public boolean mouseUp(Event evt, int x, int y){moveflag=numpoints+1;return true;}
}

	
		