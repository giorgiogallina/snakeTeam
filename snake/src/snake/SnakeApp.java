package snake;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Random;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.LineStyleEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;

public class SnakeApp implements Serializable{

	protected Shell shell;
	private Canvas canvas;
	private GC gc;
	
	private Body snk;
	private Body movingHurdle;
	private Rectangle hrdl, hrdl2, hrdl3, hrdl4;
	private char sposta, sp2 = 'k';
	private int xapple;
	private int yapple;
	private int level = 0, conta = 0;
	private boolean flag = false, flag2 = true, flag3 = true, chkMove = true;
		/*flag is for having a break
		 *flag2 is to forbid players to keep playing after having lost the game
		 *flag3 is to show rules only once at the beginning of the game
		 *chkMove is used to avoid problems when pressing two keys too quickly*/
	private int score;
	private int speed;
	
	private Random random = new Random();

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			SnakeApp window = new SnakeApp();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
 
	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			//if (!display.readAndDispatch()) {
			//display.sleep();
		//}
			while (!display.readAndDispatch() && flag && flag2){
				if(movingHurdle != null){
					int a = (int) (Math.random()*100%4);
					if(conta == 0){
						switch(a){
						case 0:
							movingHurdle.move('u');
							break;
						case 1:
							movingHurdle.move('l');
							break;
						case 2:
							movingHurdle.move('d');
							break;
						case 3:
							movingHurdle.move('r');
							break;
						}
					}
					conta++;
					if(conta % 4 == 0)
						conta = 0;
				}
				snk.move(sposta);
				chkMove = true;
				if(sp2 != 'k'){
					sposta = sp2;
					sp2 = 'k';
				}
				if(snk.collision(xapple, yapple)){
					snk.increase(sposta);
					score += 10;
					if(score % 50 == 0 && speed >= 75){
						speed -= 15;
					}
					System.out.println(speed);
					do{
						xapple = random.nextInt(canvas.getBounds().width - snk.getUni()) / snk.getUni() * snk.getUni();
						yapple = random.nextInt(canvas.getBounds().height - snk.getUni()) / snk.getUni() * snk.getUni();
					}while(snk.inBody(new Punto(xapple, yapple)) ||
							(hrdl != null && xapple >= hrdl.x && (xapple < (hrdl.x+hrdl.width) && yapple >= hrdl.y && (yapple < (hrdl.y+hrdl.height)))) ||
							(hrdl2 != null && xapple >= hrdl2.x && (xapple < (hrdl2.x+hrdl2.width) && yapple >= hrdl2.y && (yapple < (hrdl2.y+hrdl2.height)))) ||
							(hrdl3 != null && xapple >= hrdl3.x && (xapple < (hrdl3.x+hrdl3.width) && yapple >= hrdl3.y && (yapple < (hrdl3.y+hrdl4.height)))) ||
							(hrdl4 != null && xapple >= hrdl4.x && (xapple < (hrdl4.x+hrdl4.width) && yapple >= hrdl4.y && (yapple < (hrdl3.y+hrdl4.height)))) );
					if(score % ((Body.level-3)*10) == 0){
						level++;
						
						switch(level){
						case 1:
							break;
						case 2:
							hrdl = new Rectangle(190, 130, 200, 20);
							hrdl2 = new Rectangle(190, 220, 200, 20);
							break;
						case 3:
							hrdl3 = new Rectangle(90, 80, 20, 220);
							hrdl4 = new Rectangle(480, 80, 20, 220);
							break;
						case 4:
							hrdl = null;
							hrdl2 = null;
							hrdl3 = null;
							hrdl4 = null;
							movingHurdle = new Body();
							movingHurdle.move('u');
							movingHurdle.move('l');
							movingHurdle.move('l');
							movingHurdle.move('l');
							break;
						case 5:
							hrdl = new Rectangle(190, 130, 200, 20);
							hrdl2 = new Rectangle(190, 220, 200, 20);
							break;
						case 6:
							hrdl3 = new Rectangle(90, 80, 20, 220);
							hrdl4 = new Rectangle(480, 80, 20, 220);
							break;
						default:
							break;
						}
						
						gc.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
						gc.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
						gc.setFont(new Font(null, "Candara", 22, 1));
						gc.drawText("LEVEL " + level, canvas.getBounds().width/2-50, canvas.getBounds().height/2-30, 1);
						gc.drawRectangle(0, 0, canvas.getBounds().width-1, canvas.getBounds().height-1);
						try {
							Thread.sleep(1500);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				draw();
				if(snk.gameOver() ||
						(hrdl != null && snk.getItem(0).getX() >= hrdl.x && (snk.getItem(0).getX() < (hrdl.x+hrdl.width) && snk.getItem(0).getY() >= hrdl.y && (snk.getItem(0).getY() < (hrdl.y+hrdl.height)))) ||
						(hrdl2 != null && snk.getItem(0).getX() >= hrdl2.x && (snk.getItem(0).getX() < (hrdl2.x+hrdl2.width) && snk.getItem(0).getY() >= hrdl2.y && (snk.getItem(0).getY() < (hrdl2.y+hrdl2.height)))) ||
						(hrdl3 != null && snk.getItem(0).getX() >= hrdl3.x && (snk.getItem(0).getX() < (hrdl3.x+hrdl3.width) && snk.getItem(0).getY() >= hrdl3.y && (snk.getItem(0).getY() < (hrdl3.y+hrdl4.height)))) ||
						(hrdl4 != null && snk.getItem(0).getX() >= hrdl4.x && (snk.getItem(0).getX() < (hrdl4.x+hrdl4.width) && snk.getItem(0).getY() >= hrdl4.y && (snk.getItem(0).getY() < (hrdl3.y+hrdl4.height)))) ||
						(movingHurdle != null && (movingHurdle.inBody(snk.getItem(0)) || snk.inBody(movingHurdle.getItem(0))))){
					flag2 = false;
					gc.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
					gc.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
					gc.setFont(new Font(null, "Candara", 35, 1));
					gc.drawText("GAME OVER", (canvas.getBounds().width-250)/2, canvas.getBounds().height/2-40, 1);
				}
				try {
					Thread.sleep(speed);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void draw(){
		
		gc.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		gc.fillRectangle(0, 0, canvas.getBounds().width, canvas.getBounds().height);
		if(level > 0)
			gc.drawRectangle(0, 0, canvas.getBounds().width-1, canvas.getBounds().height-1);
		gc.setFont(new Font(null, "Candara", 13, 1));
		gc.drawText("Score: "+score+"", canvas.getBounds().width-100, 1);
		gc.setBackground(SWTResourceManager.getColor(255,0,0));
		gc.fillOval(xapple, yapple, Body.uni, Body.uni);
		gc.drawOval(xapple, yapple, Body.uni, Body.uni);
		if(snk != null){
			if(snk.length() > 0){
				gc.setBackground(SWTResourceManager.getColor(0,200,100));
				gc.fillOval(snk.getItemCoordinates(0)[0], snk.getItemCoordinates(0)[1], Body.uni, Body.uni);
				gc.drawOval(snk.getItemCoordinates(0)[0], snk.getItemCoordinates(0)[1], Body.uni, Body.uni);
			}
			gc.setBackground(SWTResourceManager.getColor(0,255,0));
			for(int i = 1; i < snk.length(); i++){
				gc.fillOval(snk.getItemCoordinates(i)[0], snk.getItemCoordinates(i)[1], Body.uni, Body.uni);
				gc.drawOval(snk.getItemCoordinates(i)[0], snk.getItemCoordinates(i)[1], Body.uni, Body.uni);
			}
		}
		if(hrdl != null && hrdl2 != null){
			gc.setBackground(SWTResourceManager.getColor(0,0,255));
				gc.fillRectangle(hrdl);
				gc.drawRectangle(hrdl);
				gc.fillRectangle(hrdl2);
				gc.drawRectangle(hrdl2);
			
		}
		if(hrdl3 != null && hrdl4 != null){
			gc.setBackground(SWTResourceManager.getColor(0,0,255));
				gc.fillRectangle(hrdl3);
				gc.drawRectangle(hrdl3);
				gc.fillRectangle(hrdl4);
				gc.drawRectangle(hrdl4);
		}
		if(movingHurdle != null){
			gc.setBackground(SWTResourceManager.getColor(255,0,100));
			for(int i = 0; i < movingHurdle.length(); i++){
				gc.fillOval(movingHurdle.getItemCoordinates(i)[0], movingHurdle.getItemCoordinates(i)[1], Body.uni, Body.uni);
				gc.drawOval(movingHurdle.getItemCoordinates(i)[0], movingHurdle.getItemCoordinates(i)[1], Body.uni, Body.uni);
			}
		}
	}
	
	private void initialize(){
		Body.uni = 10;
		sposta = 'u';
		sp2 = 'k';
		Punto.xMax = canvas.getBounds().width;
		Punto.yMax = canvas.getBounds().height;
		
		xapple = canvas.getBounds().width / 2;
		yapple = canvas.getBounds().height / 2;
		
		flag = false;
		flag2 = true;
		chkMove = true;
		
		score = 0;
		speed = 190;
		level = 0;
		
		movingHurdle = null;
		snk = new Body();
		hrdl = hrdl2 = hrdl3 = hrdl4 = null;
		draw();
	}
	
	private boolean write(){
		try {
			ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream("snake2.bin"));
			stream.writeObject(snk);
			stream.writeObject(hrdl);
			stream.writeObject(hrdl2);
			stream.writeObject(hrdl3);
			stream.writeObject(hrdl4);
			stream.writeObject(movingHurdle);
			stream.writeObject(sposta);
			stream.writeObject(score);
			stream.writeObject(speed);
			stream.writeObject(level);
			stream.writeObject(xapple);
			stream.writeObject(yapple);
			stream.close();
			return true;
		} catch (Exception e1) {
			return false;
		}
	}
	
	private boolean read(){
		try {
			ObjectInputStream stream = new ObjectInputStream(new FileInputStream("snake2.bin"));
			snk = (Body) stream.readObject();
			hrdl = (Rectangle) stream.readObject();
			hrdl2 = (Rectangle) stream.readObject();
			hrdl3 = (Rectangle) stream.readObject();
			hrdl4 = (Rectangle) stream.readObject();
			movingHurdle = (Body) stream.readObject();
			sposta = (char) stream.readObject();
			score = (int) stream.readObject();
			speed = (int) stream.readObject();
			level = (int) stream.readObject();
			xapple = (int) stream.readObject();
			yapple = (int) stream.readObject();
			flag = false;
			stream.close();
			return true;
		} catch (Exception e1) {
			return false;
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.addShellListener(new ShellAdapter() {
			@Override
			public void shellActivated(ShellEvent e) {
				initialize();
				/*MessageDialog.openInformation(shell, "Instruction",
						"press the arrow keys to move\n"
						+ "press the space bar to stop the game\n"
						+ "press enter to restart\n"
						+ "press 1 or S to save the game\n"
						+ "press 2 or L to start the saved game");*/
			}
		});
		shell.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				//System.out.println(e.keyCode);
				switch(e.keyCode){
				case 115: case 16777265: case 49: case 119:
					//snk.write();
					write();
					break;
				case 108: case 16777266: case 50: case 114:
					//snk.read();
					read();
					draw();
					break;
				case 32:	//space
					flag = !flag;
					break;
				case 13:	//enter
					initialize();
					break;
				case 16777217:
					if(sposta != 'd'){ //per evitare l'inversione dello snake
						flag = true;
						if(chkMove){
							sposta = 'u';
							chkMove = false;
						}else{
							sp2 = 'u';
						}
					}
					break;
				case 16777218:
					if(sposta != 'u'){
						flag = true;
						if(chkMove){
							sposta = 'd';
							chkMove = false;
						}else{
							sp2 = 'd';
						}
					}
					break;
				case 16777219:
					if(sposta != 'r'){
						flag = true;
						if(chkMove){
							sposta = 'l';
							chkMove = false;
						}else{
							sp2 = 'l';
						}
					}
					break;
				case 16777220:
					if(sposta != 'l'){
						flag = true;
						if(chkMove){
							sposta = 'r';
							chkMove = false;
						}else{
							sp2 = 'r';
						}
					}
					break;
				}
			}
		});
		shell.setSize(635, 480);
		shell.setText("Games");
		
		canvas = new Canvas(shell, SWT.NONE);
		canvas.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent arg0) {
				//initialize();
				draw();
				if(flag3){
					flag3 = false;
					MessageDialog.openInformation(shell, "Instruction",
							"press the arrow keys to move\n"
									+ "press the space bar to stop the game\n"
									+ "press enter to restart\n"
									+ "press 1 or S to save the game\n"
									+ "press 2 or L to start the saved game");
				}
			}
			
		});
		canvas.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		canvas.setBounds(10, 34, Body.uni * 60, Body.uni * 40);
		

		gc = new GC(canvas);
		
		/*
		text = new Text(shell, SWT.BORDER | SWT.READ_ONLY);
		text.setBounds(294, 7, 76, 21);
		
		Label lblScore = new Label(shell, SWT.NONE);
		lblScore.setBounds(252, 13, 36, 15);
		lblScore.setText("Score");*/
		
		/*Button btnSalva = new Button(shell, SWT.NONE);
		btnSalva.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				snk.write();
			}
		});
		btnSalva.setBounds(0, 406, 75, 25);
		btnSalva.setText("Salva");
		
		Button btnCarica = new Button(shell, SWT.NONE);
		btnCarica.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				snk.read();
			}
		});
		btnCarica.setBounds(81, 406, 75, 25);
		btnCarica.setText("Carica");*/

	}
}
