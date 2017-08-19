import ecs100.*;
import java.util.*;
import java.io.*;
import java.awt.Color;

/** Renders pnm images (pbm, pgm, or ppm) onto the graphics panel
ppm images are the simplest possible colour image format.
 */

public class ImageRenderer implements UIButtonListener{
    public static final        int top = 20;   // top edge of the image
    public static final         int left = 20;  // left edge of the image
    public static final        int pixelSize = 2;  

    String Scomment = "#";
    char comment = Scomment.charAt(0);
    int col, row, dp, scaleDP, bw, r, g, b = 0;
    String token;

    public ImageRenderer(){
        UI.initialise();
        UI.addButton("Render", this);
        UI.addButton("Quit", this);
    }

    public void buttonPerformed(String b){
        UI.clearText();
        UI.clearGraphics();
        if (b.equals("Render")){
            String fname = UIFileChooser.open("Image file to render");
            if (fname != null){
                this.renderImage(fname);
            }
        } else if (b.equals("Quit")){
            UI.quit();
        }
    }

    /**
     * Renders a pnm image file.
     * Asks for the name of the file, then renders the image at position (left, top).
     * Each pixel of the image is rendered by a square of size pixelSize
     * The first three tokens (other than comments) are
     *    the magic number (P1, P2, or P3),
     *    number of columns, (integer)
     *    number of rows,  (integer)
     * ppm and pgm files then have 
     *    colour depth  (integer: range of possible color values)
     * The remaining tokens are the pixel values
     *  (0 or 1 for pbm, single integer for pgm; red, green, and blue integers for ppm)
     * There may be comments anywhere in the file, which start with # and go to the end of the line. Comments should be ignored.
     * The image may be "animated", in which case the file contains a sequence of images
     * (ideally, but not necessarily, the same type and size), which should be rendered
     * in sequence.
     * This method should read the magic number then call the appropriate method for rendering the rest of the image
     */                                
    public void renderImage(String fname){
        String magic;
        try {
            Scanner scan = new Scanner(new File(fname));

            magic = scan.next();

            if (magic.equals("P1")) {
                pbm(scan);
            } else if (magic.equals("P2")) {
                pgm(scan);
            } else if (magic.equals("P3")){
                ppm(scan);
            }

            scan.close();
        } catch(IOException e){UI.println("File reading failed" + e);}
    }

    public void pbm(Scanner file){
        try {
            Scanner scan = file;
            String token = scan.next();

            if (token.charAt(0) == comment) {
                while (token.charAt(0) == comment) {
                    scan.nextLine();
                    token = scan.next();//
                }
            } else {
                col = Integer.parseInt(token);
            }

            token = scan.next();

            if (token.charAt(0) == comment) {
                while (token.charAt(0) == comment) {
                    scan.nextLine();
                    token = scan.next();//
                }
            } else {
                row = Integer.parseInt(token);
            }

            while (scan.hasNext()) {  
                int y = top;
                for (int tempRow=0; tempRow<row; tempRow++){
                    int x = left;
                    for (int tempCol=0; tempCol<col; tempCol++){
                        UI.println ("Rendering iamge....");

                        if(scan.hasNext()) {
                            token = scan.next();
                        }

                        if (token.charAt(0) == comment) {
                            while (token.charAt(0) == comment) {
                                scan.nextLine();
                                token = scan.next();//
                            }
                        } else {
                            bw = Integer.parseInt(token);
                        }

                        if (bw == 1) {
                            UI.setColor(Color.black);
                        } else {
                            UI.setColor(Color.white);
                        }

                        UI.fillRect(x, y, pixelSize, pixelSize, false);

                        x += pixelSize;
                    }
                    y += pixelSize;
                }
                UI.repaintGraphics();

                token = scan.next();
                if (token.equals("P1")) {
                    scan.nextLine();
                    scan.nextLine();
                    UI.sleep (20);
                }
            }
            UI.repaintGraphics();
            UI.println ("Rendering DONE");
        } catch (Exception e) {
            UI.repaintGraphics();
            UI.println ("Rendering DONE");
        }
    }

    public void pgm(Scanner file){
        try {
            Scanner scan = file;
            String token = scan.next();

            if (token.charAt(0) == comment) {
                while (token.charAt(0) == comment) {
                    scan.nextLine();
                    token = scan.next();//
                }
            } else {
                col = Integer.parseInt(token);
            }

            token = scan.next();

            if (token.charAt(0) == comment) {
                while (token.charAt(0) == comment) {
                    scan.nextLine();
                    token = scan.next();//
                }
            } else {
                row = Integer.parseInt(token);
            }

            token = scan.next();

            if (token.charAt(0) == comment) {
                while (token.charAt(0) == comment) {
                    scan.nextLine();
                    token = scan.next();//
                }
            } else {
                dp = Integer.parseInt(token);
            }

            if (dp > 0  && dp < 255) {
                //    UI.println("DP is " + dp);
                scaleDP = (int) 255/dp;    
            }

            while (scan.hasNext()) {  
                int y = top;
                for (int tempRow=0; tempRow<row; tempRow++){
                    int x = left;
                    for (int tempCol=0; tempCol<col; tempCol++){
                        UI.println ("Rendering iamge....");

                        if(scan.hasNext()) {
                            token = scan.next();
                        }

                        if (token.charAt(0) == comment) {
                            while (token.charAt(0) == comment) {
                                scan.nextLine();
                                token = scan.next();//
                            }
                        } else {
                            bw = Integer.parseInt(token);
                            if (dp != 255) {
                                bw = bw * scaleDP;
                            }
                        }

                        UI.setColor(new Color (bw, bw, bw));

                        UI.fillRect(x, y, pixelSize, pixelSize, false);
                        x += pixelSize;
                    }
                    y += pixelSize;
                }
                UI.repaintGraphics();

                token = scan.next();
                if (token.equals("P2")) {
                    scan.nextLine();
                    scan.nextLine();
                    UI.sleep (20);
                }
            }
            UI.repaintGraphics();
            UI.println ("Rendering DONE");
        } catch (Exception e) {
            UI.repaintGraphics();
            UI.println ("Rendering DONE");
        }
    }

    public void ppm (Scanner file){

        try {
            Scanner scan = file;
            String token = scan.next();

            if (token.charAt(0) == comment) {
                while (token.charAt(0) == comment) {
                    scan.nextLine();
                    token = scan.next();//
                }
            } else {
                col = Integer.parseInt(token);
            }

            token = scan.next();

            if (token.charAt(0) == comment) {
                while (token.charAt(0) == comment) {
                    scan.nextLine();
                    token = scan.next();//
                }
            } else {
                row = Integer.parseInt(token);
            }

            token = scan.next();

            if (token.charAt(0) == comment) {
                while (token.charAt(0) == comment) {
                    scan.nextLine();
                    token = scan.next();//
                }
            } else {
                dp = Integer.parseInt(token);
            }

            if (dp > 0  && dp < 255) {
                //UI.println("DP is " + dp);
                scaleDP = (int) 255/dp;    
            }

            while (scan.hasNextInt()) {  
                int y = top;
                for (int tempRow=0; tempRow<row; tempRow++){
                    int x = left;
                    for (int tempCol=0; tempCol<col; tempCol++){
                        UI.println ("Rendering iamge....");

                        if(scan.hasNext()) {
                            token = scan.next();
                        }

                        if (token.charAt(0) == comment) {
                            while (token.charAt(0) == comment) {
                                scan.nextLine();
                                token = scan.next();//
                            }
                        } else {
                            r = Integer.parseInt(token);
                            if (dp != 255) {
                                r = r * scaleDP;
                            }
                            token = scan.next();
                        }

                        if (token.charAt(0) == comment) {
                            while (token.charAt(0) == comment) {
                                scan.nextLine();
                                token = scan.next();//
                            }
                        } else {
                            g = Integer.parseInt(token);
                            if (dp != 255) {
                                g = g * scaleDP;
                            }
                            token = scan.next();
                        }

                        if (token.charAt(0) == comment) {
                            while (token.charAt(0) == comment) {
                                scan.nextLine();
                                token = scan.next();//
                            }
                        } else {
                            b = Integer.parseInt(token);
                            if (dp != 255) {
                                b = b * scaleDP;
                            }
                        }

                        UI.setColor(new Color (r, g, b));

                        UI.fillRect(x, y, pixelSize, pixelSize, false);

                        x += pixelSize;
                    }
                    y += pixelSize;
                }

                UI.repaintGraphics();
                token = scan.next();
                if (token.equals("P3")) {
                    scan.nextLine();
                    scan.nextLine();
                    UI.sleep (20);
                }
            }
            UI.repaintGraphics();
            UI.println ("Rendering DONE");
        } catch (Exception e) {
            UI.repaintGraphics();
            UI.println ("Rendering DONE");
        }
    }

    public static void main(String[] args){
        UI.setImmediateRepaint(false);
        new ImageRenderer();
    }
}