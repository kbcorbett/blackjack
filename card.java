/* Authored by Kristoffer corbett 5/16/2019
 * card object holds data for card face, suit, and eventually location of graphic file
*/

/* for use with next revision
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
*/

public class card 
{
	int face; //non numerical usage, 11-jack, 12-queen, 13-king, 14-ace
	int suit; //usage 1-spade, 2-club, 3-heart, 4-diamond
	
	/* for use with next revision
	Image graphic;
	File graphic2;
	*/
	
	//no default constructor, suit and face must be specified. file will be determined by suit and face
	card(int s,int f)
	{
		setFace(f);
		setSuit(s);
		/* for use with next revision
		graphic2 = new File(this.getFaceName() + this.getSuitName() + ".jpg");
        try
        {
		this.setGraphic(ImageIO.read(graphic2));
        }
        catch (Exception e)
        {
        	System.out.println("error loading image file");
        }
        */
		
	}
	
	//default getter
	public int getFace() 
	{
		return face;
	}
	
	//facename getter, in effect converts and integer card value into a string card value
	public String getFaceName() 
	{
		switch(this.face)
		{
		case 2:
			return "two";
		case 3:
			return "three";
		case 4:
			return "four";
		case 5:
			return "five";
		case 6:
			return "six";
		case 7:
			return "seven";
		case 8:
			return "eight";
		case 9:
			return "nine";
		case 10:
			return "ten";
		case 11:
			return "jack";
		case 12:
			return "queen";
		case 13:
			return "king";
		default:
			return "ace";	
		}
	}
	
	//default setter
	public void setFace(int face) {
		this.face = face;
	}
	
	//default getter
	public int getSuit() {
		return suit;
	}
	
	//as above for facename converts an integer suit value into string value
	public String getSuitName() 
	{
		switch(this.suit)
		{
		case 1:
			return "spades";
		case 2:
			return "clubs";
		case 3:
			return "hearts";
		default:
			return "diamonds";
		}
	}
	
	//default setter
	public void setSuit(int suit) {
		this.suit = suit;
	}
	
	/* for use with next revision
	public Image getGraphic() 
	{
		return graphic;
	}
	
	public void setGraphic(Image graphic) 
	{
		this.graphic = graphic;
	}
	*/
	

}
