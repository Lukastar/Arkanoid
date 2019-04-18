import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.util.Random;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.Color;

class Kulka extends Ellipse2D.Float
{
   Plansza p;
   double dx,dy,ndx;
   double dlugosc_wektora;
   double temp_wektora;
   int speed = 5;
   double check;
   float last;
   float temp;
   float check2;
   int flaga=15;
   Beleczka z;
   int scorr;
   Kulka(Plansza p,double dx,double dy)
   {
      this.width=10;
      this.height=10;
      this.x=(int)p.b.getX()+((int)p.b.getWidth()/2)-this.width/2;
      this.y=(int)p.b.getY()-(int)p.b.getHeight();


      this.p=p;
      this.dx=dx;
      this.dy=dy;
   }

   void nextKrok()
   {
      if (p.state == p.state.GAME)
      {
      check2 = (float)p.temp;
      for(int i=1;i<15;i++)
      {
          //if (p.)
          if (p.d[i].stan==false)
          {
          //p.bolitem = (p.generator.nextBoolean());
          p.randx = (p.generator.nextInt(332));
          p.randy = (p.generator.nextInt(350));
          temp = (float)p.randy;
          z=new Beleczka((float)p.randx,check2-temp,68,20,true);
          p.d[i] = z;
          /*for(int j=1;j<flaga;j++)
          {
              if (p.d[j].intersects(z))
              {
              }
              else
              {
                  p.d[i] = z;
              }
          }*/
          }
          else if (p.d[i].stan==true && p.d[i].getY()>340){
              p.d[i].stan=false;
              p.life=p.life-1;
              if (p.life==0){
                  //OVRE no lifes
                  p.state = p.state.GAMEOVER;
                  scorr=p.scorr;
                  p.promp();
                  p.reset();

              }
          }
      }

      dlugosc_wektora=Math.sqrt(dx*dx+dy*dy);
      x+=dx;
      y+=dy;

      if(getMinX()<0 || getMaxX()>p.getWidth())  dx=-dx;
      if(getMinY()<0 || getMaxY()>p.getHeight()) dy=-dy;
      /*if((p.b.getY()<(this.y+this.height)) && (((p.b.getX())>this.x)) && ((p.b.getY()-p.b.getHeight())<(this.y-this.height)))
      {
          dx=-dx;
      }*/
      if((p.b.getY()+p.b.getHeight())<this.y)
      {
          p.state = p.state.GAMEOVER;
          scorr=p.scorr;
          p.promp();
          p.reset();

          //p.b.x=100;
          //p.b.y=170;
          /*dx=1;
          dy=-1;
          this.x=(int)p.b.getX()+((int)p.b.getWidth()/2)-this.width/2;
          this.y=(int)p.b.getY()-(int)p.b.getHeight();
          p.scorr = 0;
          p.bonus = 1;
          p.flagg = 0;
          p.gie.interrupt();
          p.life=3;
          for(int i=1;i<15;i++)
          {
              //bolitem = (generator.nextBoolean());
              p.bolitem = true;
              p.randx = (p.generator.nextInt(332));
              p.randy = (p.generator.nextInt(350));
              if (p.temp>p.randy)
              {
                  p.temp=p.randy;
              }
              p.d[i]=new Beleczka(p.randx,p.randy-350,68,20,p.bolitem);
              p.flagg = p.flagg + 1;
              for(int j=1;j<p.flagg;j++)
              {
                  if (p.d[i].intersects(p.d[j]))
                  {
                      p.d[i].stan = false;
                  }
              }
          }*/

      }
      if((p.b.getY()<(this.y+this.height)) && ((p.b.getX()-5)<=this.x && this.x<=(p.b.getX()+p.b.getWidth()+3)))
	  {
        p.bonus=1;

        if(this.x>=p.b.getX()-5 && this.x<p.b.getX()+p.b.getWidth()/2)
		{
			double kata=-(this.x-p.b.getX())/(p.b.getWidth()/2)+1;
			dx=-Math.abs(kata);
                        dy=-Math.sqrt(dlugosc_wektora*dlugosc_wektora-dx*dx);

		}
		else if(this.x>p.b.getX()+p.b.getWidth()/2 && this.x<p.b.getWidth()+p.b.getX())
		{
			double katb=(this.x-p.b.getX())/(p.b.getWidth()/2)-1;
			dx=Math.abs(katb);
                        dy=-Math.sqrt(dlugosc_wektora*dlugosc_wektora-dx*dx);
		}
	  }

      for(int i=1;i<p.d.length;i++)
      {
        if(this.intersects(p.d[i]))
        {
          if(p.d[i].stan)
          {
            if(((this.x+this.width)<p.d[i].getX()+1) || ((this.x)>p.d[i].getMaxX()-1))
            {
              dx=-dx;
            }
            else
            {
              dy=-dy;
            }

            p.scorr=p.scorr+p.bonus;
            p.bonus=p.bonus+1;
            p.d[i].stan=false;

          }
        }
      }
      p.repaint();
   }
   }
}

class SilnikKulki extends Thread
{
   Kulka a;
   int speedk;
   SilnikKulki(Kulka a)
   {
      this.a=a;
      this.speedk = a.speed;
      start();
   }

   public void run()
   {
      try
      {
         while(true)
         {
            a.nextKrok();
            speedk = a.speed;
            sleep(speedk);
         }
      }
      catch(InterruptedException e){}
   }
}

class SilnikBelek extends Thread
{
   Plansza p;
   int speedb;
   SilnikBelek(Plansza p)
   {
      this.p=p;
      this.speedb = p.speedbe;
      start();
   }

   public void run()
   {
      try
      {
         while(true)
         {
            p.nextKrok2();
            speedb = p.speedbe;
            sleep(speedb);
         }
      }
      catch(InterruptedException e){}
   }
}

class Belka extends Rectangle2D.Float
{
   Belka(int x)
   {
      this.x=x;
      this.y=350;
      this.width=60;
      this.height=10;
   }

   void setX(int x)
   {
      this.x=x;
   }
}

class Beleczka extends Rectangle2D.Float
{
  boolean stan;
  Beleczka(float x, float y, float w, float h, boolean stan)
  {
     super(x,y,w,h);
     this.stan=stan;
  }
}
class wyniki{
       String[] save;
       String[] save2;
       String readed;
       BufferedReader br;
       FileInputStream fstream;
       private int flaga=0;
       PrintWriter out;
       String temp;
       String temp2;
       String temp3;


       wyniki(){
           this.flaga=flaga;
           this.save=new String[6];
           this.save2=new String[6];
           try{
             this.fstream=new FileInputStream("score.txt");
           }
           catch(FileNotFoundException e){
               System.out.println("Brak pliku");
           }
            this.br=new BufferedReader(new InputStreamReader(fstream));
             for(int j=1;j<save.length;j++){
                 save[j]="None";
            }
       }
       void wczytaj(){
           try{
           while ((readed = br.readLine()) != null){
           flaga=flaga+1;
           /*temp=readed.substring(0,3);
           temp2=readed.substring(4,readed.length());
           readed=temp2+" "+temp;*/
           save[flaga]=readed;
           }
           fstream.close();
           flaga=0;

           }
           catch(IOException e){
                System.out.println("Blad in/out");
           }
       }
       void zapisz(){
           try{
             this.out=new PrintWriter("score.txt");
           }
           catch(FileNotFoundException e){
               System.out.println("Brak pliku");
           }
              for(int i=1;i<save.length;i++){
                  out.println(save[i]);
              }
              out.close();
                         /*try{
           }
           catch(IOException e){
                System.out.println("Blad in/out");
           }*/
       }
}
class Plansza extends JPanel implements MouseMotionListener,MouseListener
{
   Belka b;
   Beleczka re;
   Kulka a;
   SilnikKulki s;
   Menu m = new Menu();
   Beleczka[] d=new Beleczka[15];
   Random generator = new Random();
   State state = State.MENU;
   int scorr = 0;
   int bonus = 1;
   String score;
   String bonuss;
   int randx;
   int randy;
   boolean bolitem;
   int flagg = 0;
   int speedbe = 45;
   SilnikBelek gie;
   int temp=450;
   BufferedImage bcg;
   BufferedImage men2;
   BufferedImage qulka;
   BufferedImage belqa;
   BufferedImage belqi;
   int life=3;
   boolean is=false;
   boolean is2=false;
   boolean is3=false;
   boolean is4=false;
   String[] save=new String[5];
   JTextField prompt;
   String nick;
   String tempor;
   //int tempor2;
   int index;
   String tempstring;
   wyniki zet=new wyniki();



   Plansza()
   {
      super();
      addMouseMotionListener(this);
      addMouseListener(this);
      zet.wczytaj();
      for(int i=1;i<15;i++)
      {
          //bolitem = (generator.nextBoolean());
          bolitem = true;
          randx = (generator.nextInt(332));
          randy = (generator.nextInt(350));
          if (temp>randy)
          {
              temp=randy;
          }
          d[i]=new Beleczka(randx,randy-350,68,20,bolitem);
          flagg = flagg + 1;
          for(int j=1;j<flagg;j++)
          {
              if (d[i].intersects(d[j]))
              {
                  d[i].stan = false;
              }
          }

          /*if(i<=5 && i>=1)
          {




           d[i]=new Beleczka(10*i,10,68,20,bolitem);
           //System.out.println(d[i].stan);
          }
          else if(i<=10 && i>=6)
          {
           d[i]=new Beleczka(10*(i-5),40,68,20,bolitem);
           //System.out.println(d[i].stan);
          }
          else if(i<=15 && i>=11)
          {
           d[i]=new Beleczka(10*(i-10),70,68,20,bolitem);
           //System.out.println(d[i].stan);
       }*/
      }
      temp = temp - 450;
      b=new Belka(150);
      a=new Kulka(this,1,-1);
      s=new SilnikKulki(a);
      try
      {
          bcg=ImageIO.read(new File("tlo.png"));
          men2=ImageIO.read(new File("menu2.png"));
          qulka=ImageIO.read(new File("kulka.png"));
          belqa=ImageIO.read(new File("belka.png"));
          belqi=ImageIO.read(new File("belki.png"));
      }
      catch(IOException e)
      {
          System.out.println("Brak pliku");
      }
      //gie=new SilnikBelek(this);

   }
   public static enum State
   {
       MENU,GAME,EXIT,GAMEOVER,SCORE;
   }

   void reset(){
       a.dx=1;
       a.dy=-1;
       a.x=(int)b.getX()+((int)b.getWidth()/2)-a.width/2;
       a.y=(int)b.getY()-(int)b.getHeight();
       scorr = 0;
       bonus = 1;
       flagg = 0;
       gie.interrupt();
       life=3;
       is4=false;
       for(int l=1;l<15;l++)
       {
           //bolitem = (generator.nextBoolean());
           bolitem = true;
           randx = (generator.nextInt(332));
           randy = (generator.nextInt(350));
           if (temp>randy)
           {
               temp=randy;
           }
           d[l]=new Beleczka(randx,randy-350,68,20,bolitem);
           flagg = flagg + 1;
           for(int j=1;j<flagg;j++)
           {
               if (d[l].intersects(d[j]))
               {
                   d[l].stan = false;
               }
           }
       }
   }

   void promp(){
       prompt=new JTextField("Wpisz nick");
       prompt.setBounds(150,200,100,40);
       add(prompt);
   }

   void nextKrok2()
   {
        double x;
        double y;
        for(int j=1;j<d.length;j++)
        {
            x = d[j].getX();
            y = d[j].getY();
            if (d[j].stan)
            {
              //System.out.println("tutaj!");
              d[j] = new Beleczka((float)x, (float)y+1,68,20,true);
            }
        }
        repaint();
   }

   public void paintComponent(Graphics g)
   {
      if (state == State.GAME)
      {
      if (scorr<10){
        score = "0"+Integer.toString(scorr);
      }
      else{
        score = Integer.toString(scorr);
      }

      bonuss = "x"+Integer.toString(bonus);
      super.paintComponent(g);
      g.drawImage(bcg,0,0,getWidth(),getHeight(),this);
      Graphics2D g2d=(Graphics2D)g;
      Font font3 = new Font("Comic Sans MS", Font.BOLD, 15);
      g.setColor(Color.WHITE);
      g.setFont(font3);
      g2d.drawString("LIFES: ",190,440);
      g2d.drawString("BONUS",335,400);
      g2d.drawString("SCORE",13,400);
      g2d.drawString(score,28,430);
      g2d.drawString(bonuss,355,430);
      g2d.drawString(Integer.toString(life),240,440);
      TexturePaint tp= new TexturePaint(qulka, new Rectangle(0,0,10,10));
      g2d.setPaint(tp);
      g2d.fill(a);
      TexturePaint tp2= new TexturePaint(belqa, new Rectangle(0,0,60,10));
      g2d.setPaint(tp2);
      g2d.fill(b);
      TexturePaint tp3= new TexturePaint(belqi, new Rectangle(0,0,400,450));
      g2d.setPaint(tp3);
      //g2d.fill(re);


      for(int i=1;i<d.length;i++)
      {
        if(d[i].stan)
        {
          g2d.fill(d[i]);
        }

      }
      }
      else if (state == state.SCORE){
          super.paintComponent(g);
          g.drawImage(men2,0,0,getWidth(),getHeight(),this);
          Graphics2D g2d=(Graphics2D)g;
          Font font1 = new Font("Comic Sans MS", Font.BOLD, 37);
          g.setFont(font1);
          g2d.drawString("SCORES",120,150);
          Font font4 = new Font("Comic Sans MS", Font.BOLD, 35);
          g.setColor(Color.WHITE);
          g.setFont(font4);
          g2d.drawString("SCORES",126,150);
          Font font2 = new Font("Comic Sans MS", Font.BOLD, 15);
          g.setFont(font2);
          g.setColor(Color.BLACK);
          g.drawString(zet.save[1],150,200);
          g.drawString(zet.save[2],150,220);
          g.drawString(zet.save[3],150,240);
          g.drawString(zet.save[4],150,260);
          g.drawString(zet.save[5],150,280);
          g2d.drawString("OK",190,330);
          if (is4){
            g2d.draw(m.ok);
          }

      }
      else if (state == state.GAMEOVER){
          super.paintComponent(g);
          g.drawImage(men2,0,0,getWidth(),getHeight(),this);
          Graphics2D g2d=(Graphics2D)g;
          Font font1 = new Font("Comic Sans MS", Font.BOLD, 37);
          g.setFont(font1);
          g2d.drawString("GAMEOVER",94,150);
          Font font4 = new Font("Comic Sans MS", Font.BOLD, 35);
          g.setColor(Color.WHITE);
          g.setFont(font4);
          g2d.drawString("GAMEOVER",100,150);
          Font font2 = new Font("Comic Sans MS", Font.BOLD, 15);
          g.setFont(font2);
          g.setColor(Color.BLACK);
          g2d.drawString("OK",190,330);
          if (is4){
            g2d.draw(m.ok);
          }
      }
      else
      {
        super.paintComponent(g);
        g.drawImage(men2,0,0,getWidth(),getHeight(),this);
        Graphics2D g2d=(Graphics2D)g;
        Font font1 = new Font("Comic Sans MS", Font.BOLD, 37);
        g.setFont(font1);
        g2d.drawString("ARKANOID",94,150);
        Font font4 = new Font("Comic Sans MS", Font.BOLD, 35);
        g.setColor(Color.WHITE);
        g.setFont(font4);
        g2d.drawString("ARKANOID",100,150);
        Font font2 = new Font("Comic Sans MS", Font.BOLD, 15);
        g.setFont(font2);
        g.setColor(Color.BLACK);
        g2d.drawString("START",176,230);
        g2d.drawString("SCORES",171,280);
        g2d.drawString("EXIT",180,330);
        if (is){
          g2d.draw(m.start);
      }
      if (is2){
        g2d.draw(m.exit);
      }
      if (is3){
        g2d.draw(m.scorez);
      }
      }
      repaint();
      //System.out.println("Jestem w planszy");
   }

   public void mouseMoved(MouseEvent e)
   {
	  if(e.getX()<=30)
	  {
		b.setX(0);
	  }
	  else if(e.getX()>=this.getWidth()-30)
	  {
		b.setX(this.getWidth()-(int)b.width);
	  }


	  else
	  {
        b.setX(e.getX()-30);
	  }
      int mx = e.getX();
      int my = e.getY();
      double dmx = mx;
      double dmy = my;

      if (state==state.MENU){
      if (m.start.contains(dmx,dmy))
      {
         is=true;
      }
      else if (m.exit.contains(dmx,dmy))
      {
          is2=true;
      }
      else if (m.scorez.contains(dmx,dmy))
      {
          is3=true;
      }
      else{
          is=false;
          is2=false;
          is3=false;
      }
      }
      else if ((state==state.SCORE) || (state==state.GAMEOVER)){
          if (m.ok.contains(dmx,dmy))
          {
              is4=true;
          }
          else{
              is4=false;
          }
      }
      repaint();
   }
   public void mouseDragged(MouseEvent e)
   {

   }
   public void mouseClicked(MouseEvent e)
   {
       int mx = e.getX();
       int my = e.getY();
       double dmx = mx;
       double dmy = my;
       if (!((state == state.SCORE)||(state==state.GAMEOVER))){
       if (m.start.contains(dmx,dmy))
       {
           state = state.GAME;
           gie = new SilnikBelek(this);
       }
       else if (m.exit.contains(dmx,dmy))
       {
           zet.zapisz();
           System.exit(0);
       }
       else if (m.scorez.contains(dmx,dmy)){
           state = state.SCORE;
       }
       }
       else if ((state==state.SCORE) || (state==state.GAMEOVER)){
           if (m.ok.contains(dmx,dmy)){
               if (state==state.GAMEOVER){

                   for (int je=1;je<zet.save.length;je++){
                       int tempor2=Integer.parseInt(zet.save[je].substring(0,3));
                          System.out.println("tempor "+tempor2);
                       if (a.scorr>tempor2){
                          System.out.println("ind "+zet.save.length);
                          if (a.scorr<10){
                          zet.save[zet.save.length-1]="00"+String.valueOf(a.scorr)+" "+prompt.getText();
                          }
                          else if ((10<=a.scorr)&&(a.scorr<100)){
                          zet.save[zet.save.length-1]="0"+String.valueOf(a.scorr)+" "+prompt.getText();
                          }
                          else{
                          zet.save[zet.save.length-1]=String.valueOf(a.scorr)+" "+prompt.getText();
                          }
                           break;
                       }
                   }

                   for (int eg=1;eg<zet.save.length;eg++){
                       System.out.println("eg "+eg);
                       int tempor2=Integer.parseInt(zet.save[eg].substring(0,3));
                       if (eg==zet.save.length){
                           break;
                       }
                       if (tempor2<a.scorr){
                           tempstring=zet.save[eg];
                           zet.save[eg]=zet.save[zet.save.length-1];
                           zet.save[zet.save.length-1]=tempstring;

                       }
                   }
                   this.remove(prompt);
                   this.validate();
                   this.repaint();
               }
                state = state.MENU;
           }
       }
   }
   public void mouseEntered(MouseEvent e)
   {

   }
   public void mouseExited(MouseEvent e)
   {

   }
   public void mousePressed(MouseEvent e)
   {

   }
   public void mouseReleased(MouseEvent e)
   {

   }
}

class Menu extends Rectangle2D.Float
{
    Plansza p;
    Rectangle2D.Float start;
    Rectangle2D.Float scorez;
    Rectangle2D.Float exit;
    Rectangle2D.Float ok;

    Menu()
    {
        start = new Rectangle2D.Float(100,200,200,45);
        scorez = new Rectangle2D.Float(100,250,200,45);
        exit = new Rectangle2D.Float(100,300,200,45);
        ok = new Rectangle2D.Float(100,300,200,45);
    }
}

public class Program
{
   public static void main(String[] args)
   {
      javax.swing.SwingUtilities.invokeLater(new Runnable()
      {
         public void run()
         {
            Plansza p;

            p=new Plansza();
            p.setPreferredSize(new Dimension(400,450));
            p.setBorder(BorderFactory.createLineBorder(new Color(50,10,150), 5));
            JPanel jp = new JPanel();
            jp.add(p);
            JFrame jf=new JFrame();
            jf.add(jp);

            jf.setTitle("ARKANOID");
            jf.setSize(500,500);
            jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            jf.setVisible(true);
         }
      });
   }
}
