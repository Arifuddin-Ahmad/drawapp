
package mydrawapp;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.StringTokenizer;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;
import java.util.ArrayList;
import java.io.InputStreamReader;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Ellipse;
public class Parser {
    
    private BufferedReader reader;
    private Group group;
    private Paint colour= null;
    private double width = 500;
    private double height = 300;
    private int strokeWidth = 1;
    private Color strokeColour = Color.BLACK;
    
    public Parser(Reader reader)
    {
        this.group = new Group();
        this.reader = new BufferedReader(reader);
    }
    
    public void parse(boolean demo,boolean stepThrough)
    {
        
        
        ArrayList<String> ins = new ArrayList<String>();
        if(demo == false) //Read instructions from compiled C code
        {
            try
            {
              System.out.println("Reading instructions from compiled C code");
              String line = reader.readLine();
              while (line != null)
              {
                ins.add(line);
                line = reader.readLine();

              }
            }
            catch (IOException e)
            {
              System.out.println("Error:Parse failed while attempt to read instruction");
              return;
            }
        }else{
            ins.add("SD 500 500");
            ins.add("BW 2");
            ins.add("BC gray");
            ins.add("LG blue black");
            ins.add("DL 0 0 100 100");
            ins.add("FR 100 100 200 200");
            ins.add("SF yellow");
            ins.add("DO 400 400 50 70");
            ins.add("DI 200 200 50 50 @C:/Users/DikDik/Pictures/logo/BSURed.jpgr");
            ins.add("BC red");
            ins.add("DS 10 10 @Hello");
            
        }
        if(stepThrough == false)
        {
            for(int i = 0;i<ins.size();i++)
            {
                   int lbl_i = i+1;
                   try
                   {
                       parseLine(ins.get(i));
                       System.out.println("Instruction #" + lbl_i + ": " + ins.get(i) + " successfully executed");
                   }catch (ParseException e)
                   {
                       System.out.println("Error in instruction #" + lbl_i + ": " + ins.get(i));
                   }
            }
        }else{
            InputStreamReader inputStreamReader = new InputStreamReader(System.in); 
            BufferedReader reader = new BufferedReader(inputStreamReader); 
           for(int i = 0;i<ins.size();i++){
               int lbl_i = i+1;
                try
                {
                    
                    parseLine(ins.get(i));
                    System.out.println("Instruction #" + lbl_i + ": " + ins.get(i) + " successfully executed");
                    System.out.println("Click Any Key to execute the next line");
                    try
                    {
                        reader.readLine();
                    }catch(IOException e)
                    {
                        
                    }
                }catch (ParseException e)
                {
                    System.out.println("Error in instruction #" + lbl_i + ": " + ins.get(i));
                    System.out.println("Click Any Key to execute the next line");
                    try
                    {
                        reader.readLine();
                    }catch(IOException e1)
                    {
                        
                    }
                }
           }
        }
    }
    
   
    private void parseLine(String line) throws ParseException
    {
        if (line.length() < 2) return;
        String command = line.substring(0, 2);
        //Old Commands
        if (command.equals("DR")) { drawRect(line.substring(2, line.length())); return; } //Draw Rectangle
        if (command.equals("DO")) { drawOval(line.substring(2, line.length())); return; } //Draw Oval
        if (command.equals("DL")) { drawLine(line.substring(2,line.length())); return; }    //Draw Line
        if (command.equals("DS")) { drawString(line.substring(3, line.length())); return; } //Draw String
        if (command.equals("FR")) { fillRect(line.substring(2, line.length())); return; } //Draw Fill Rectangle
        
        if (command.equals("SF")) { setFillColour(line.substring(3, line.length())); return; } //Set Fill Colour
        if (command.equals("SD")) { setScreenDimension(line.substring(2, line.length())); return; } //Set Screen Dimension
        if (command.equals("DI")) { drawImage(line.substring(2, line.length())); return; } //Draw Image
        if (command.equals("BW")) { setBorderWidth(line.substring(2, line.length())); return; } //Set Border Width
        if (command.equals("BC")) { setBorderColour(line.substring(3, line.length())); return; } //Set Border Color
        if (command.equals("LG")) { setLinearGradient(line.substring(3, line.length())); return; } //Draw Linear Gradient
        
        throw new ParseException("Unknown drawing command");
    }
  
    private void drawOval(String args) throws ParseException
    {
        Ellipse ellipse = new Ellipse();
        int centerX = 0;
        int centerY= 0;
        int radiusX = 0;
        int radiusY = 0;

        StringTokenizer tokenizer = new StringTokenizer(args);
        centerX = getInteger(tokenizer);
        centerY = getInteger(tokenizer);
        radiusX = getInteger(tokenizer);
        radiusY = getInteger(tokenizer);  
        ellipse.setCenterX(centerX);
        ellipse.setCenterY(centerY);
        ellipse.setRadiusX(radiusX);
        ellipse.setRadiusY(radiusY);
        ellipse.setFill(colour);
        ellipse.setStrokeWidth(strokeWidth);
        ellipse.setStroke(strokeColour);
        group.getChildren().add(ellipse);       
    }
    
    private void setLinearGradient(String args) throws ParseException
    {
            
            
            int position = args.indexOf(" ");
            if (position == -1) 
            {throw new ParseException("Missing Parameters"); }   
            String color1 = args.substring(0,position);
            String color2 = args.substring(position+1,args.length());
            Stop[] stops = { new Stop(0, getColor(color1)), new Stop(1, getColor(color2))};
            LinearGradient lg = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
            colour = lg;
    }
    
    private Color getColor(String colourName) throws ParseException
    {
        if (colourName.equals("black")) {return Color.BLACK;}
        if (colourName.equals("blue")) { return Color.BLUE;}
        if (colourName.equals("cyan")) { return Color.CYAN;}
        if (colourName.equals("darkgray")) { return Color.DARKGRAY;}
        if (colourName.equals("gray")) { return Color.GRAY;}
        if (colourName.equals("green")) { return Color.GREEN;}
        if (colourName.equals("lightgray")) { return Color.LIGHTGRAY;}
        if (colourName.equals("magenta")) { return Color.MAGENTA;}
        if (colourName.equals("orange")) { return Color.ORANGE;}
        if (colourName.equals("pink")) { return Color.PINK;}
        if (colourName.equals("red")) { return Color.RED;}
        if (colourName.equals("white")) { return Color.WHITE;}
        if (colourName.equals("yellow")) { return Color.YELLOW;}        
        throw new ParseException("Invalid colour name");        
    }
    
    private void setBorderWidth(String args) throws ParseException
    {
        strokeWidth = 1;
        StringTokenizer tokenizer = new StringTokenizer(args);
        strokeWidth = getInteger(tokenizer);
        this.strokeWidth = strokeWidth;    
    }
    
    private void setBorderColour(String colourName) throws ParseException
    {
        
        if (colourName.equals("black")) { strokeColour = Color.BLACK; return;}
        if (colourName.equals("blue")) { strokeColour  = Color.BLUE; return;}
        if (colourName.equals("cyan")) { strokeColour  = Color.CYAN; return;}
        if (colourName.equals("darkgray")) { strokeColour  = Color.DARKGREY; return;}
        if (colourName.equals("gray")) { strokeColour  = Color.GRAY; return;}
        if (colourName.equals("green")) { strokeColour  = Color.GREEN; return;}
        if (colourName.equals("lightgray")) { strokeColour  = Color.LIGHTGREY; return;}
        if (colourName.equals("magenta")) { strokeColour  = Color.MAGENTA; return;}
        if (colourName.equals("orange")) { strokeColour  = Color.ORANGE; return;}
        if (colourName.equals("pink")) { strokeColour  = Color.PINK; return;}
        if (colourName.equals("red")) { strokeColour  = Color.RED; return;}
        if (colourName.equals("white")) { strokeColour  = Color.WHITE; return;}
        if (colourName.equals("yellow")) { strokeColour =Color.YELLOW; return;}
        
        throw new ParseException("Invalid colour name");        
    }
    
    private void drawImage(String args) throws ParseException
    {
        int x = 0;
        int y = 0;
        int height = 0;
        int width = 0 ;
        String s = "";
        
        StringTokenizer tokenizer = new StringTokenizer(args);
        x = getInteger(tokenizer);
        y = getInteger(tokenizer);
        height = getInteger(tokenizer);
        width = getInteger(tokenizer);
        
        int position = args.indexOf("@");
        if (position == -1) throw new ParseException("Image URL is missing");
        s = args.substring(position+1,args.length());
        
        
        File file = new File(s);
        if (file.exists()== false){
            throw new ParseException("Image File "+s+" does not exist");
        }
        Image image = new Image(file.toURI().toString(),width,height,false,false);
        ImageView iv = new ImageView();
        iv.setImage(image);
        iv.setX(x);
        iv.setSmooth(true);
        iv.setY(y);
        group.getChildren().add(iv);
    }
    
    private void setScreenDimension(String args) throws ParseException
    {
        int width = 0;
        int height = 0 ;
        StringTokenizer tokenizer = new StringTokenizer(args);
        width = getInteger(tokenizer);
        height = getInteger(tokenizer);
        this.width = width;
        this.height = height;
    }
    
    private void setFillColour(String colourName) throws ParseException
    {
        if (colourName.equals("black")) { colour = Color.BLACK; return;}
        if (colourName.equals("blue")) { colour = Color.BLUE; return;}
        if (colourName.equals("cyan")) { colour = Color.CYAN; return;}
        if (colourName.equals("darkgray")) { colour = Color.DARKGREY; return;}
        if (colourName.equals("gray")) { colour = Color.GRAY; return;}
        if (colourName.equals("green")) { colour = Color.GREEN; return;}
        if (colourName.equals("lightgray")) { colour = Color.LIGHTGREY; return;}
        if (colourName.equals("magenta")) { colour = Color.MAGENTA; return;}
        if (colourName.equals("orange")) { colour = Color.ORANGE; return;}
        if (colourName.equals("pink")) { colour = Color.PINK; return;}
        if (colourName.equals("red")) { colour = Color.RED; return;}
        if (colourName.equals("white")) { colour = Color.WHITE; return;}
        if (colourName.equals("yellow")) { colour=Color.YELLOW; return;}
        throw new ParseException("Invalid colour name");
    }
    
    private void drawString(String args) throws ParseException
    {
        int x = 0;
        int y = 0 ;
        String s = "";
        StringTokenizer tokenizer = new StringTokenizer(args);
        x = getInteger(tokenizer);
        y = getInteger(tokenizer);
        
        int position = args.indexOf("@");
        
        if (position == -1) 
        {
            throw new ParseException("DrawString string is missing");
        }
        s = args.substring(position+1,args.length());
        Text t = new Text (x, y, s);
        t.setFill(colour);
        t.setStrokeWidth(strokeWidth);
        t.setStroke(strokeColour);
        
        group.getChildren().add(t);
    }
    
    private void drawLine(String args) throws ParseException
    {
        int x1 = 0;
        int y1 = 0;
        int x2 = 0;
        int y2 = 0;
        StringTokenizer tokenizer = new StringTokenizer(args);
        x1 = getInteger(tokenizer);
        y1 = getInteger(tokenizer);
        x2 = getInteger(tokenizer);
        y2 = getInteger(tokenizer);
        
        Path path = new Path();
        MoveTo moveTo = new MoveTo();
        moveTo.setX(x1);
        moveTo.setY(y1);

        LineTo lineTo = new LineTo();
        lineTo.setX(x2);
        lineTo.setY(y2);

        path.getElements().add(moveTo);
        path.getElements().add(lineTo);
        path.setStrokeWidth(strokeWidth);
        path.setStroke(strokeColour);
        group.getChildren().add(path);
    }
    
    private void fillRect(String args) throws ParseException
    {
        Rectangle r = new Rectangle();
        int x1 = 0;
        int y1 = 0;
        int x2 = 0;
        int y2 = 0;

        StringTokenizer tokenizer = new StringTokenizer(args);
        x1 = getInteger(tokenizer);
        y1 = getInteger(tokenizer);
        x2 = getInteger(tokenizer);
        y2 = getInteger(tokenizer);  
        r.setX(x1);
        r.setY(y1);
        r.setWidth(x2);
        r.setHeight(y2);
        r.setFill(colour);
        r.setStrokeWidth(strokeWidth);
        r.setStroke(strokeColour);
        group.getChildren().add(r);
    }
    
    private void drawRect(String args) throws ParseException
    {
        Rectangle r = new Rectangle();
        int x1 = 0;
        int y1 = 0;
        int x2 = 0;
        int y2 = 0;

        StringTokenizer tokenizer = new StringTokenizer(args);
        x1 = getInteger(tokenizer);
        y1 = getInteger(tokenizer);
        x2 = getInteger(tokenizer);
        y2 = getInteger(tokenizer);  
        r.setX(x1);
        r.setY(y1);
        r.setWidth(x2);
        r.setHeight(y2);
        r.setFill(null);
        r.setStrokeWidth(strokeWidth);
        r.setStroke(strokeColour);
        group.getChildren().add(r);
    }
    
    public Group getGroup()
    {
        return this.group;
    }
    
    public double getHeight()
    {
        return this.height;
    }
    
    public double getWidth()
    {
        return this.width;
    }
    
  private int getInteger(StringTokenizer tokenizer) throws ParseException
  {
    if (tokenizer.hasMoreTokens()){
      return Integer.parseInt(tokenizer.nextToken());
    }else{
      throw new ParseException("Missing parameter");
    }
  }
}
