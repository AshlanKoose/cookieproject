import java.io.*;
import java.nio.file.*;
import java.nio.file.Files;
import java.util.*;
public class Final2 { 
	//global variables
		boolean isSave = false;
		int crash = 1;
		Set<String> saveFile = new HashSet<>();
		File res = new File("cookieresult.txt");
	void main() throws IOException {
		isSaves();
		decider();
	}

private static final Scanner SCAN = new Scanner(System.in);  // Reusable scanner to avoid leaks

/**
 * Prompts the user to save progress in a loop until a valid "yes" or "no" response is entered.
 * If the user chooses "yes", sets isSave to true and notifies that options will save.
 * If the user chooses "no", sets isSave to false and notifies that options will not save.
 * If an invalid response is entered, repeats the prompt and increments the crash counter.
 * If the crash counter exceeds 5, prints "ok bye" and exits the application.
 */
public void isSaves() {
    while (true) {
        if (crash > 5) {
            IO.println("ok bye");
            System.exit(0);
        }
        IO.println("Do you want to save your progress? Yes, no");
        String input = SCAN.nextLine().trim().toLowerCase();
        if ("yes".equals(input)) {
            isSave = true;
            IO.println("Options will save.");
            break;
        } else if ("no".equals(input)) {
            isSave = false;
            IO.println("Options will not save.");
            break;
        } else {
            IO.println("Choose yes or no.");
            crash++;
        }
    }
}
	/**
	 * Decision tree that breanchs to a cookie based on the given options and user yes/no input
	 */
	public void decider() throws IOException{ //a decision tree that gives the user yes or no options, and branches to a cookie based on the options
		//non fruit options
		//believe this or not this is the shortened list of options
		NodeTree<String> crinkle = new NodeTree<>("Chocolate Crinkle Cookies\n");
		NodeTree<String> chocochip = new NodeTree<>("Chocolate Chip Cookies\n");
		NodeTree<String> bdaycake = new NodeTree<>("Birthday Cake Cookies\n");
		NodeTree<String> snickerdoodle = new NodeTree<>("Snickerdoodles\n");
		NodeTree<String> crunchy = new NodeTree<>("Meringues\n");
		NodeTree<String> peanut = new NodeTree<>("Peanut Butter Cookies\n");
		NodeTree<String> sugar = new NodeTree<>("Sugar Cookies\n");
		NodeTree<String> coating = new NodeTree<>("Chocolatey? Yes, no", crinkle, snickerdoodle);
		NodeTree<String> soft = new NodeTree<>("Peanut butter? Yes, no", peanut, sugar);
		NodeTree<String> nfnoaddin = new NodeTree<>("Soft? Yes, no", soft, crunchy);
		NodeTree<String> nfaddin = new NodeTree<>("Chocolatey? Yes, no", chocochip, bdaycake);
		NodeTree<String> nocoating = new NodeTree<>("Add ins? Yes, no", nfaddin, nfnoaddin);
		NodeTree<String> nofruity = new NodeTree<>("Coating? Yes, no", coating, nocoating);
		//fruit options
		NodeTree<String> straw = new NodeTree<>("Strawberry Shortcake Cookies\n");
		NodeTree<String> rasp = new NodeTree<>("White Chocolate Raspberry Cookies\n");
		NodeTree<String> oatmeal = new NodeTree<>("Oatmeal Raisin Cookies\n");
		NodeTree<String> coconut = new NodeTree<>("Coconut Macaroons\n");
		NodeTree<String> orange = new NodeTree<>("Orange Cookies\n");
		NodeTree<String> lemon = new NodeTree<>("Lemon Cookies\n");
		NodeTree<String> nocitrus = new NodeTree<>("Thumbprint Cookies\n");
		NodeTree<String> texture = new NodeTree<>("Coconut? Yes, no", coconut, oatmeal);
		NodeTree<String> notexture = new NodeTree<>("Strawberry? Yes, no", straw, rasp);
		NodeTree<String> addin = new NodeTree<>("Textury? Yes, no", texture, notexture);
		NodeTree<String> citrus = new NodeTree<>("Orange? Yes, no", orange, lemon);
		NodeTree<String> noaddin = new NodeTree<>("Citrusy? Yes, no", citrus, nocitrus);
		NodeTree<String> fruity = new NodeTree<>("Add ins? Yes, no", addin, noaddin);
		NodeTree<String> beginning = new NodeTree<>("Fruity? Yes, no", fruity, nofruity); //question, left, right (root)
		
		decide(beginning);
		if(isSave == true){
			loadSave();
			printSave();
		}
	}
	/**
	 * Gives user option of loading save data into the terminal
	 */
	public void loadSave(){ //loads the save data into the program
		Scanner scan = new Scanner(System.in);
		IO.println("Load save file? Save file must be loaded in order to print any data from it. Yes, no");
		String selection = scan.next();
		if(selection.equalsIgnoreCase("yes")){
			try{
				Files.lines(Paths.get("cookieresult.txt"))
				.forEach(saveFile::add);
				IO.println("Loaded save file");
			}catch (IOException ioe){
				IO.println("Save file does not exist. Use decider at least twice to create a save file.");
				return;
			}
		} else if (selection.equalsIgnoreCase("no")){
			IO.println("Save file not loaded");
		} else {
			IO.println("Choose yes or no.");
			loadSave();
		}
	}
	/**
	 * Gives user option to print the save file data, and adds up how many cookies the user has found
	 */
	public void printSave(){ //prints the save file information, and adds up how many cookies someone has "found"
		Scanner scan = new Scanner(System.in);
		IO.println("Print saved file? Save file must be loaded to print the saved data. Yes, no");
		String selection = scan.next();
		if(selection.equalsIgnoreCase("yes")){
			IO.println(saveFile);
			IO.println(saveFile.size() + "/14 cookies found");
		} else if (selection.equalsIgnoreCase("no")){
			IO.println("Save file not printed");
		} else {
			IO.println("Choose yes or no.");
			printSave();
		}
	}
	/**
	 * Travels along the decision tree based on user input
	 * writes to a file if isSave is true
	 * @param current a NodeTree
	 */
	public void decide(NodeTree current) throws IOException{ //what is used to make the decision tree work. Creates the save file and writes to it
		Scanner scan = new Scanner(System.in);
		NodeTree temp = null;
		IO.println(current.getData());
		if(current.isLeaf()){
			temp = current;
				if(isSave == true){ 
					FileWriter out = new FileWriter(res, true); //I was looking for a way to print to file without overwriting the previous information each time
					out.write(temp.toString());					//and found FileWriter online
					out.close();
				}
			return;
		}
		String selection = scan.next();
		if(selection.equalsIgnoreCase("yes")){
			decide(current.getLeft());
		}else if(selection.equalsIgnoreCase("no")){
			decide(current.getRight());
		}else{
			IO.println("choose yes or no");
			decide(current);
		}
	}
}
class NodeTree<E>  { //I'm gonna be real this is the treenode adt from class from the decisiontree day, I just rewrote decide and added a tostring
private static Scanner scan = new Scanner(System.in);
	private NodeTree left, right;
	private E data;
	
	/**
	 *sets NodeTree
	 */
	public NodeTree(){this(null, null, null);}
	
	/**
	 * sets NodeTree
	 * @param data a generic
	 */
	public NodeTree(E data){
		this(data, null, null);
	}
	
	/**
	 * sets NodeTree
	 * @param data 
	 * @param left
	 * @param right
	 */
	public NodeTree(E data, NodeTree left, NodeTree right){
		setData(data);
		setLeft(left);
		setRight(right);
	}
	/**
	 * tests to see if a node element is a leaf
	 * @return true if left and right are null, false otherwise
	 */
	public boolean isLeaf(){
		return left == null && right == null;
	}
	/**
	 * setData sets data to a given element
	 * @param data a generic
	 */
	public void setData(E data){this.data = data;}
	/**
	 * setLeft sets left to a given element
	 * @param left left NodeTree child
	 */
	public void setLeft(NodeTree left){this.left = left;}
	/**
	 * setRight sets right to a given element
	 * @param right right NodeTree child
	 */
	public void setRight(NodeTree right){this.right = right;}
	
	/**
	 * getData allows for other classes to see data
	 * @return data
	 */
	 //man am I really gonna do one line javadocs for every one
	public E getData(){return data;}
	/**
	 * getLeft allows for other classes to see left
	 * @return left
	 */
	public NodeTree getLeft(){return left;}
	/**
	 * getRight allows for other classes to see right
	 * @return left
	 */
	public NodeTree getRight(){return right;}
	
	/**
	 * a version of toString(), allows NodeTree to be readable by humans
	 * @return a readable NodeTree element
	 */
	public String toString(){
		if (data == null){
			return "";
		}else{
			return "" + data + "";
		}
	}
}
