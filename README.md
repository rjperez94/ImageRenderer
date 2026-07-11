# ImageRenderer

## Overview

Digital image files are ubiquitous, and we happily expect our computers to show images in all sorts of contexts. The core information in a digital image is the color of each individual pixel of the image, so an image file must store that information. A colour can be represented by a triple of three numbers, one for each of the red, green, and blue components of the colour, so we can represent an image in a file by storing three numbers for each pixel. Exactly how we do that depends on the format of the image file. A very simple format will just list all the numbers, so that a 100x100 pixel image (which is pretty small) would have 30,000 numbers, three numbers for each of the 10,000 pixels. However, this will end up being a rather large file, and most of the common image file formats (such as jpg, gif, and png) have more complex formats in which the information is encoded and compressed to remove redundancy. Turning the contents of these compressed image files into a coloured image on the screen is a very complicated process, but the files can be very much smaller than when using the simple format.

Netpbm files come in several varieties: ascii text or binary, and color, grey-scale, or black-and-white.

- Every Netpbm format file starts with two characters which are the "magic number" identifying the image file type. Netpbm magic numbers are given in the following table
  <table> 
	<tbody>
		<tr class="foswikiTableOdd">
			<th> name </th>
			<th> type of colour </th>
			<td> <strong>Magic number</strong> (ascii format) </td>
			<th> Magic number* (binary format) </th>
		</tr>
		<tr>
			<th> pbm </th>
			<td> black and white </td>
			<td> P1 </td>
			<td> P4 </td>
		</tr>
		<tr>
			<th> pgm </th>
			<td> grey scale </td>
			<td> P2 </td>
			<td> P5 </td>
		</tr>
		<tr>
			<th> ppm </th>
			<td> color </td>
			<td> P3 </td>
			<td> P6 </td>
		</tr>
	</tbody> </table>
- The next two values in the file are integers specifying the width and height (number of columns and number of rows) of the image
- For pgm and ppm files, the next value is a colour depth - the maximum value the colour values can take; 255 is common

The rest of the image consists of the color value for each pixel, from left to right for the first row, then the second row, etc.

- For ascii pbm files, each pixel value is either 0 (white) or 1 (black).
- For ascii pgm files, each pixel value is an integer between 0 (black) and the maximum value (white)
- For ascii ppm files, each pixel has three integers for the red, green, and blue components.

The files may also contain comments, which should be ignored. A comments starts with a `#` character and lasts to the end of the line.

## Compiling Java files using Eclipse IDE

1. Download this repository as ZIP
2. Create new `Java Project` in `Eclipse`
3. Right click on your `Java Project` --> `Import`
4. Choose `General` --> `Archive File`
5. Put directory where you downloaded ZIP in `From archive file`
6. Put `ProjectName/src` in `Into folder`
7. Click `Finish`

### Linking the UI Library

8. Right click on your `Java Project` --> `Build Path` --> `Add External Archives`
9. Select `ecs100.jar` and link it to the project. That JAR will be in the directory where you downloaded ZIP

## Running the program

1. Right click on your `Java Project` --> `Run As` --> `Java Application` --> `ImageRenderer`
2. Click `Render` and choose a file to render

## Build an executable using IntelliJ IDEA

1. Go to **File** → **Project Structure** → **Artifacts**.
2. Click the green plus (**+**) button, select **JAR**, and choose **From modules with dependencies...**
3. In the **Main Class** field, click the folder icon and select the application's entry point class.
4. Under **JAR files from libraries**, select **extract to the target JAR** (this creates the single Fat JAR).
5. Click **OK**, then click **Apply**.
6. From the top menu bar, go to **Build** → **Build Artifacts...** and click **Build**.
7. The executable jar file will be generated inside the project directory under `out/artifacts/`.

### Run the executable JAR file using the command line:

```bash
java -jar path/to/executable.jar
```

## Live Demo

You can run this application directly in your web browser via the link below:

**[Launch Live Demo](https://rjperez94.github.io/ImageRenderer/)**

### Loading Local Data

If you are trying to pick a file from your physical hard drive, you cannot browse your local folders through the Java window. You must use the bridge upload feature.

1. Look at the very top right of the Java window's title bar for a small **Up Arrow (Upload)** button.
2. Click it to trigger your **native browser file picker** (this one can see your real computer folders).
3. Select your local file. The app will silently drop it into the virtual folder named `/files/uploads/`.
4. Now, inside your Java file picker, type `/files/uploads/` into the file path bar and press **Enter** to find your uploaded file.
