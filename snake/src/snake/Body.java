package snake;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * 
 * @author Giorgio Gallina & Marco Positello
 *
 */
public class Body implements Serializable{
		
	private LinkedList<Punto> snake;
	public static int uni = 10;

	/**
	 * default constructor of this class: it creates a new snake sized four set
	 * upright toward the top of the shell
	 */
	public Body() {
		snake = new LinkedList<Punto>();
		for (int i = 0; i < 4; i++) {
			increase('U');
		}
	}

	/**
	 * this method inserts a new element in the array's first position.
	 * 
	 * @param p
	 *            is the new point to add to the array.
	 * @return the method returns true if everything go properly and the element
	 *         is added, otherwise it returns false.
	 */
	private boolean add(Punto p) {
		if (p == null)
			return false;
		Iterator<Punto> i = snake.iterator();
		Punto punto = new Punto();
		while(i.hasNext()){
			punto = i.next();
		}
		if (snake.isEmpty() || !p.equals(punto)) {
			snake.addFirst(p);
			return true;
		}
		return false;
	}

	/**
	 * when the snake eats an apple, it grows so that we need to add a new piece
	 * of body to him.
	 * 
	 * @param direction
	 *            the direction where the snake is going: D for down, U for up,
	 *            L for left and R for right.
	 * @return it returns true if it has been possible to add a new element to
	 *         the array, otherwise it returns false
	 */
	public boolean increase(char direction) {
		Punto head = null;
		switch (direction) {
		case 'd':
		case 'D': // down
			if (snake.size() < 1) { // it shouldn't be needed but managing also
									// this case is better because we have to be
									// sure to avoid any exception in runtime
				head = new Punto((Punto.xMax - uni) / 2, (Punto.yMax - uni) / 2 + 3 * uni);
			} else {
				head = new Punto(snake.get(0).getX(), snake.get(0).getY() + uni);
			}
			break;
		case 'u':
		case 'U': // up
			if (snake.size() < 1) {
				head = new Punto(((Punto.xMax - uni) / 2) / uni * uni, ((Punto.yMax - uni) / 2 - 3 * uni) / uni * uni);
			} else {
				head = new Punto(snake.get(0).getX(), snake.get(0).getY() - uni);
			}
			break;
		case 'l':
		case 'L': // left
			if (snake.size() < 1) { // it shouldn't be needed but managing also
									// this case is better because we have to be
									// sure to avoid any exception in runtime
				head = new Punto((Punto.xMax - uni) / 2 + 3 * uni, (Punto.yMax - uni) / 2);
			} else {
				head = new Punto(snake.get(0).getX() - uni, snake.get(0).getY());
			}
			break;
		case 'r':
		case 'R':
			if (snake.size() < 1) { // it shouldn't be needed but managing also
									// this case is better because we have to be
									// sure to avoid any exception in runtime
				head = new Punto((Punto.xMax - uni) / 2 - 3 * uni, (Punto.yMax - uni) / 2);
			} else {
				head = new Punto(snake.get(0).getX() + uni, snake.get(0).getY());
			}
			break;
		default:
			break;
		}
		return add(head);
	}

	/**
	 * this method returns the coordinate of a point in the position wanted.
	 * 
	 * @param index
	 *            the index of the array's element needed.
	 * @return an object of the class Punto.
	 */
	public Punto getItem(int index) {
		if (index < 0 || index >= snake.size())
			return null;
		return new Punto(snake.get(index));
	}

	/**
	 * this method returns the coordinate of a point in the position wanted.
	 * 
	 * @param index
	 *            the index of the array's element needed.
	 * @return an array which contains two int values: coordinate x of the point
	 *         and coordinate y.
	 */
	public int[] getItemCoordinates(int index) {
		if (index < 0 || index >= snake.size())
			return null;
		return new int[] { snake.get(index).getX(), snake.get(index).getY() };
	}

	/**
	 * this method returns the number of elements which the snake consist of.
	 * 
	 * @return the array's length
	 */
	public int length() {
		return snake.size();
	}

	/**
	 * this method makes the snake advance.
	 * 
	 * @param direction
	 *            the direction where the snake is going: D for down, U for up,
	 *            L for left and R for right.
	 * @return it returns true if it has been possible to add a new element to
	 *         the array, otherwise it returns false.
	 */
	public boolean move(char direction) {
		LinkedList<Punto> backup = new LinkedList<Punto>();
		Iterator<Punto> i = snake.iterator();
		while(i.hasNext()){
			backup.add((i.next()).clone());
		}
		snake.removeLast();
		switch (direction) {
		case 'd':
		case 'D': // down
			if (snake.size() < 1) { // it shouldn't be needed but managing also
									// this case is better because we have to be
									// sure to avoid any exception in runtime
				snake.set(0, new Punto(((Punto.xMax - uni) / 2) / uni * uni, ((Punto.yMax - uni) / 2 + 3 * uni) / uni * uni));
			} else {
				if (snake.getFirst().getY() + uni == 1)
					return false;
				snake.addFirst(new Punto(snake.getFirst().getX(), snake.getFirst().getY() + uni));
			}
			break;
		case 'u':
		case 'U': // up
			if (snake.size() < 1) { // it shouldn't be needed but managing also
									// this case is better because we have to be
									// sure to avoid any exception in runtime
				snake.set(0, new Punto(((Punto.xMax - uni) / 2) / uni * uni, ((Punto.yMax - uni) / 2 - 3 * uni) / uni * uni));
			} else {
				if (snake.getFirst().getY() - uni == 1)
					return false;
				snake.addFirst(new Punto(snake.getFirst().getX(), snake.getFirst().getY() - uni));
			}
			break;
		case 'l':
		case 'L': // left
			if (snake.size() < 1) { // it shouldn't be needed but managing also
									// this case is better because we have to be
									// sure to avoid any exception in runtime
				snake.set(0, new Punto(((Punto.xMax - uni) / 2 + 3 * uni) / uni * uni, ((Punto.yMax - uni) / 2) / uni * uni));
			} else {
				if (snake.getFirst().getX() - uni == 1)
					return false;
				snake.addFirst(new Punto(snake.getFirst().getX() - uni, snake.getFirst().getY()));
			}
			break;
		case 'r':
		case 'R':
			if (snake.size() < 1) { // it shouldn't be needed but managing also
									// this case is better because we have to be
									// sure to avoid any exception in runtime
				snake.set(0, new Punto(((Punto.xMax - uni) / 2 - 3 * uni) / uni * uni, ((Punto.yMax - uni) / 2) / uni * uni));
			} else {
				if (snake.getFirst().getX() + uni == 1)
					return false;
				snake.addFirst(new Punto(snake.getFirst().getX() + uni, snake.getFirst().getY()));
			}
			break;
		default:
			snake = backup;
			break;
		}
		return true;
	}

	/**
	 * 
	 * @param apple
	 * @return
	 */
	public boolean collision(Punto apple) {
		 return collision(apple.getX(), apple.getY());
	}

	/**
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean collision(int x, int y) {
		return (snake.getFirst().getX() == x && snake.getFirst().getY() == y);
	}

	public int getUni() {
		return uni;
	}
	/*
	public boolean write(){
		try {
			ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream("snake.bin"));
			stream.writeObject(this);
			stream.close();
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean read(){
		try {
			ObjectInputStream stream = new ObjectInputStream(new FileInputStream("snake.bin"));
			Body b = (Body) stream.readObject();
			snake = b.snake;
			uni = b.uni; //serve??
			stream.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}*/
	
	private boolean eatYourself(){
		/*for(int i = 1; i < snake.size(); i++)
			if(snake.getFirst().equals(snake.get(i)))
				return true;
		return false;*/
		return inBody(snake.getFirst());
	}
	public boolean inBody(Punto p){
		for(int i = 1; i < snake.size(); i++)
			if(p.equals(snake.get(i)))
				return true;
		return false;
	}
	public boolean gameOver(){
		return snake.getFirst().getX() <= -1 || snake.getFirst().getY() <= -1 || eatYourself();
	}
}
