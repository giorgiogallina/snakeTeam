package snake;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * 
 * @author Giorgio Gallina & Marco Positello
 *
 */
public class Body implements Serializable{

	private Punto[] snake;
	public static int uni = 10;

	/**
	 * default constructor of this class: it creates a new snake sized four set
	 * upright toward the top of the shell
	 */
	public Body() {
		snake = new Punto[0];
		for (int i = 0; i < 4; i++) {
			increase('U');
		}
	}

	/**
	 * shift the array's elements to the left: the last element is lost whereas
	 * the first appears twice (in the first and in the second position of the
	 * array).
	 */
	private void shiftRight() {
		for (int i = snake.length - 1; i > 0; i--) {
			snake[i] = snake[i - 1];
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
		if (snake.length == 0 || !p.equals(snake[snake.length - 1])) {
			snake = Arrays.copyOf(snake, snake.length + 1);
			shiftRight();
			snake[0] = new Punto(p);
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
			if (snake.length < 1) { // it shouldn't be needed but managing also
									// this case is better because we have to be
									// sure to avoid any exception in runtime
				head = new Punto((Punto.xMax - uni) / 2, (Punto.yMax - uni) / 2 + 3 * uni);
			} else {
				head = new Punto(snake[0].getX(), snake[0].getY() + uni);
			}
			break;
		case 'u':
		case 'U': // up
			if (snake.length < 1) {
				head = new Punto(((Punto.xMax - uni) / 2) / uni * uni, ((Punto.yMax - uni) / 2 - 3 * uni) / uni * uni);
			} else {
				head = new Punto(snake[0].getX(), snake[0].getY() - uni);
			}
			break;
		case 'l':
		case 'L': // left
			if (snake.length < 1) { // it shouldn't be needed but managing also
									// this case is better because we have to be
									// sure to avoid any exception in runtime
				head = new Punto((Punto.xMax - uni) / 2 + 3 * uni, (Punto.yMax - uni) / 2);
			} else {
				head = new Punto(snake[0].getX() - uni, snake[0].getY());
			}
			break;
		case 'r':
		case 'R':
			if (snake.length < 1) { // it shouldn't be needed but managing also
									// this case is better because we have to be
									// sure to avoid any exception in runtime
				head = new Punto((Punto.xMax - uni) / 2 - 3 * uni, (Punto.yMax - uni) / 2);
			} else {
				head = new Punto(snake[0].getX() + uni, snake[0].getY());
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
		if (index < 0 || index >= snake.length)
			return null;
		return new Punto(snake[index]);
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
		if (index < 0 || index >= snake.length)
			return null;
		return new int[] { snake[index].getX(), snake[index].getY() };
	}

	/**
	 * this method returns the number of elements which the snake consist of.
	 * 
	 * @return the array's length
	 */
	public int length() {
		return snake.length;
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
		Punto[] backup = Arrays.copyOf(snake, snake.length);
		shiftRight();
		switch (direction) {
		case 'd':
		case 'D': // down
			if (snake.length < 1) { // it shouldn't be needed but managing also
									// this case is better because we have to be
									// sure to avoid any exception in runtime
				snake[0] = new Punto(((Punto.xMax - uni) / 2) / uni * uni, ((Punto.yMax - uni) / 2 + 3 * uni) / uni * uni);
			} else {
				if (snake[0].getY() + uni == 1)
					return false;
				snake[0] = new Punto(snake[0].getX(), snake[0].getY() + uni);
			}
			break;
		case 'u':
		case 'U': // up
			if (snake.length < 1) { // it shouldn't be needed but managing also
									// this case is better because we have to be
									// sure to avoid any exception in runtime
				snake[0] = new Punto(((Punto.xMax - uni) / 2) / uni * uni, ((Punto.yMax - uni) / 2 - 3 * uni) / uni * uni);
			} else {
				if (snake[0].getY() - uni == 1)
					return false;
				snake[0] = new Punto(snake[0].getX(), snake[0].getY() - uni);
			}
			break;
		case 'l':
		case 'L': // left
			if (snake.length < 1) { // it shouldn't be needed but managing also
									// this case is better because we have to be
									// sure to avoid any exception in runtime
				snake[0] = new Punto(((Punto.xMax - uni) / 2 + 3 * uni) / uni * uni, ((Punto.yMax - uni) / 2) / uni * uni);
			} else {
				if (snake[0].getX() - uni == 1)
					return false;
				snake[0] = new Punto(snake[0].getX() - uni, snake[0].getY());
			}
			break;
		case 'r':
		case 'R':
			if (snake.length < 1) { // it shouldn't be needed but managing also
									// this case is better because we have to be
									// sure to avoid any exception in runtime
				snake[0] = new Punto(((Punto.xMax - uni) / 2 - 3 * uni) / uni * uni, ((Punto.yMax - uni) / 2) / uni * uni);
			} else {
				if (snake[0].getX() + uni == 1)
					return false;
				snake[0] = new Punto(snake[0].getX() + uni, snake[0].getY());
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
		return (snake[0].getX() == x && snake[0].getY() == y);
	}

	public int getUni() {
		return uni;
	}
	
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
	}
	
	private boolean eatYourself(){
		for(int i = 1; i < snake.length; i++)
			if(snake[0].equals(snake[i]))
				return true;
		return false;
	}
	public boolean gameOver(){
		return snake[0].getX() <= -1 || snake[0].getY() <= -1 || eatYourself();
	}
}
