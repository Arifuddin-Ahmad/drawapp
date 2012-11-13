#include <stdio.h>
#include "graphics.h"

//Old functions

void drawRect(int x1, int x2, int x3, int x4)
{
  printf("DR %i %i %i %i\n", x1, x2, x3, x4);
}

void drawOval(int x, int y, int width, int height)
{
  printf("DO %i %i %i %i\n",x,y,width,height);
}

void drawLine(int x1, int x2, int x3, int x4)
{
  printf("DL %i %i %i %i\n", x1, x2, x3, x4);
}

void drawString(char* s, int x, int y)
{
  printf("DS %i %i @%s\n",x,y,s);
}


void fillRect(int x1, int x2, int x3, int x4)
{
  printf("FR %i %i %i %i\n", x1, x2, x3, x4);
}



//New Functions
void setScreenDimension(int width,int height)
{
	printf("SD %i %i\n",width,height);
}

void setBorderWidth(int width)
{
	printf("BW %i\n",width);
}

void drawImage(int x,int y,int height,int width,char* file)
{
	printf("DI %i %i %i %i @%s\n",x,y,height,width,file);
}

void setLinearGradient(colour c1,colour c2)
{
  char* colourName1;
  switch(c1)
  {
    case black : colourName1 = "black"; break;
    case blue : colourName1 = "blue"; break;
    case cyan : colourName1 = "cyan"; break;
    case darkgray : colourName1 = "darkgray"; break;
    case gray : colourName1 = "gray"; break;
    case green : colourName1 = "green"; break;
    case lightgray : colourName1 = "lightgray"; break;
    case magenta : colourName1 = "magenta"; break;
    case orange : colourName1 = "orange"; break;
    case pink : colourName1 = "pink"; break;
    case red : colourName1 = "red"; break;
    case white : colourName1 = "white"; break;
    case yellow : colourName1 = "yellow"; break;
  }
  char* colourName2;
   switch(c2)
  {
    case black : colourName2 = "black"; break;
    case blue : colourName2 = "blue"; break;
    case cyan : colourName2 = "cyan"; break;
    case darkgray : colourName2 = "darkgray"; break;
    case gray : colourName2 = "gray"; break;
    case green : colourName2 = "green"; break;
    case lightgray : colourName2 = "lightgray"; break;
    case magenta : colourName2 = "magenta"; break;
    case orange : colourName2 = "orange"; break;
    case pink : colourName2 = "pink"; break;
    case red : colourName2 = "red"; break;
    case white : colourName2 = "white"; break;
    case yellow : colourName2 = "yellow"; break;
  }
  printf("LG %s %s\n", colourName1,colourName2);
  
}

void setBorderColor(colour c)
{
  char* colourName;
  switch(c)
  {
    case black : colourName = "black"; break;
    case blue : colourName = "blue"; break;
    case cyan : colourName = "cyan"; break;
    case darkgray : colourName = "darkgray"; break;
    case gray : colourName = "gray"; break;
    case green : colourName = "green"; break;
    case lightgray : colourName = "lightgray"; break;
    case magenta : colourName = "magenta"; break;
    case orange : colourName = "orange"; break;
    case pink : colourName = "pink"; break;
    case red : colourName = "red"; break;
    case white : colourName = "white"; break;
    case yellow : colourName = "yellow"; break;
  }
  printf("BC %s\n", colourName);
}

void setFillColour(colour c)
{
  char* colourName;
  switch(c)
  {
    case black : colourName = "black"; break;
    case blue : colourName = "blue"; break;
    case cyan : colourName = "cyan"; break;
    case darkgray : colourName = "darkgray"; break;
    case gray : colourName = "gray"; break;
    case green : colourName = "green"; break;
    case lightgray : colourName = "lightgray"; break;
    case magenta : colourName = "magenta"; break;
    case orange : colourName = "orange"; break;
    case pink : colourName = "pink"; break;
    case red : colourName = "red"; break;
    case white : colourName = "white"; break;
    case yellow : colourName = "yellow"; break;
  }
  printf("SC %s\n", colourName);
}