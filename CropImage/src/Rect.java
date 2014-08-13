

 //Rect.java
 //Definition of class Rect
 import java.awt.Graphics;
 import java.awt.Color;
 
 /**
  *  <b>Rect</b> describes a two-dimensional rectangle.
  *  coordinates increase down and to the right
  *  positions are integers
  */
 
 public class Rect
 {
        int xmin,ymin,xmax,ymax; 
        Color fillcolor = new Color(1,1,1);
 
         // No-argument constructor
         public Rect() { set( 0, 0, 1, 1 ); }
 
 
         // Constructor
         public Rect( int x1, int y1 , int x2, int y2 )
         {
                 set(x1,y1,x2,y2);
         }
 
         // copy constructor
         public Rect( Rect src)
         {
                 set(src.xmin,src.ymin,src.xmax,src.ymax);
         }
 
         public int[] size() { 
                 int[] mySize = new int[2];
                 mySize[0]=xmax-xmin+1;
                 mySize[1]=ymax-ymin+1;
                 return mySize;
         }
         
         
         
         // Set x and y coordinates of Point
         public Rect set( int x1, int y1, int x2, int y2 )
         {
             if (x1>x2) {
                 xmin = x2;
                 xmax = x1;
             }
             else {
                 xmin = x1;
                 xmax = x2;
             }
             if (y1>y2) {
                 ymin = y2;
                 ymax = y1;
             }
             else {
                 ymin = y1;
                 ymax = y2;
             }
                 return this;
         }
 
         public boolean contains(int a, int b){
             if (a>xmax) return false;
             if (a<xmin) return false;
             if (b<ymin) return false;
             if (b>ymax) return false;
             return true;
         }
 
         public boolean contains(Rect r)
         {
             if (r.xmin<xmin) return false;
             if (r.xmax>xmax) return false;
             if (r.ymin<ymin) return false;
             if (r.ymax>ymax) return false;
             return true;
         }
                 
         
         static public Rect bounding(Rect a, Rect b)
         {
             int x1 = Math.min(a.xmin,b.xmin);
             int y1 = Math.min(a.ymin,b.ymin);
             int x2 = Math.max(a.xmax,b.xmax);
             int y2 = Math.max(a.ymax,b.ymax);
             return new Rect(x1,y1,x2,y2);
         }
         
         static public Rect intersection(Rect a, Rect b)
         {
             if (a.xmin > b.xmax) return null;
             if (a.xmax < b.xmin) return null;
             if (a.ymin > b.ymax) return null;
             if (a.ymax < b.ymin) return null;
             int x1 = Math.max(a.xmin,b.xmin);
             int y1 = Math.max(a.ymin,b.ymin);
             int x2 = Math.min(a.xmax,b.xmax);
             int y2 = Math.min(a.ymax,b.ymax);
             return new Rect(x1,y1,x2,y2);
         }
 
         public int area()
         {
                 return (xmax-xmin+1)*(ymax-ymin+1);
         }
 
 
         // multiply point by a scalar
         public Rect scale(double s)
         {
             xmax = xmin + (int) Math.round((xmax-xmin)*s);
             ymax = ymin + (int) Math.round((ymax-ymin)*s);              
             return this;
         }
 
 
         // offset (translate) point by the amount (tx, ty)
         public Rect translate(int tx, int ty)
         {
                 xmin += tx;
                 ymin += ty;
                 xmax += tx;
                 ymax += ty;
                 return this;
         }
 
 
 
         public void setSize(int width, int height)
         {
             if (width<1) width = 1;
             if (height<1) height = 1;
             xmax = xmin + width - 1;
             ymax = ymin + height - 1;
         }
 
         public void fastdraw(Graphics g)
         {
             g.drawRect(xmin,ymin,xmax-xmin+1,ymax-ymin+1);
         }
 
         public void draw(Graphics g)
         {
                 Color oldcolor = g.getColor();
                /* if (fillcolor!=null){
                         g.setColor(fillcolor);
                        g.fillRect(xmin,ymin,xmax-xmin+1,ymax-ymin+1);
                 }*/
                 g.setColor(Color.black);
                 g.drawRect(xmin,ymin,xmax-xmin+1,ymax-ymin+1);
                 g.setColor(oldcolor);
         }
         
        
         // convert the point into a String representation
         public String toString()
         { return String.format("[%d, %d; %d, %d]",xmin,ymin,xmax,ymax); }
 
         static public void  main(String args[])
         {
                 Rect a, b;
                 a = new Rect(20,50,220,250);
                 System.out.println("a = " + a);
                 b = new Rect(1,0,10,20);
                 System.out.println("b = " + b + String.format(" area %d",b.area()));
                 
                 b.translate(2,10);
                 System.out.println("New b location = " + b );
                 b.scale(3.0);
                 System.out.println("New b size (3x) = " + b );
                 b.scale(1.5);
                 System.out.println("New b size (1.5x) = " + b );
                 b.scale((1/1.5));
                 System.out.println("New b size (x/1.5) = " + b );
                 b.scale((1/3.0));
                 System.out.println("New (Original?) b size (x/3) = " + b );
                b.setSize(10, 30);
                 System.out.println("New b size set to (10,30) = " + b );
                 System.out.println("b contains (4,13) = " + b.contains(4,13));
                 System.out.println("b contains (4,43) = " + b.contains(4,43));
                 System.out.println("Intersection of a = " + a + "\n\tand b = " + b);
                 System.out.println("\t= " + intersection(a, b));
                 System.out.println("Bounding Rect of a = " + a + "\n\tand b = " + b);
                 System.out.println("\t= " + bounding(a, b));
                 
                 b.scale(3.0);
                 System.out.println("New b size (3x) = " + b );
                                 
                 System.out.println("Intersection of a = " + a + "\n\tand b = " + b);
                 System.out.println("\t= " + intersection(a, b));
                 System.out.println("Bounding Rect of a = " + a + "\n\tand b = " + b);
                 System.out.println("\t= " + bounding(a, b));
                 
                 
         }
 
 }
