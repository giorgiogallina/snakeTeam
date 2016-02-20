package snake;

import java.util.Random;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class SnakeApp {

	protected Shell shell;
	private Canvas canvas;
	
	private Body snk;
	private char sposta;
	private int xapple;
	private int yapple;
	private boolean flag = false, flag2 = true;;
	
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
				snk.move(sposta);
				if(snk.collision(xapple, yapple)){
					snk.increase(sposta);
					
					xapple = random.nextInt(canvas.getBounds().width - snk.getUni()) / snk.getUni() * snk.getUni();
					yapple = random.nextInt(canvas.getBounds().height - snk.getUni()) / snk.getUni() * snk.getUni();
				}
				draw();
				if(snk.gameOver()){
					flag2 = false;
					GC gc = new GC(canvas);
					gc.drawText("GAME OVER", (canvas.getBounds().width-9)/2, canvas.getBounds().height/2-1);
				}
				try {
					Thread.sleep(150);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	private void draw(){
		GC gc = new GC(canvas);
		gc.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		gc.fillRectangle(0, 0, canvas.getBounds().width, canvas.getBounds().height);
		gc.setBackground(SWTResourceManager.getColor(255,0,0));
		gc.fillOval(xapple, yapple, Body.uni, Body.uni);
		gc.drawOval(xapple, yapple, Body.uni, Body.uni);
		gc.setBackground(SWTResourceManager.getColor(0,255,0));
		for(int i = 0; i < snk.length(); i++){
			gc.fillOval(snk.getItemCoordinates(i)[0], snk.getItemCoordinates(i)[1], Body.uni, Body.uni);
			gc.drawOval(snk.getItemCoordinates(i)[0], snk.getItemCoordinates(i)[1], Body.uni, Body.uni);
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch(e.keyCode){
				case 115:
					snk.write();
					break;
				case 108:
					snk.read();
					break;
				case 32:
					flag = !flag;
					break;
				case 16777217:
					if(sposta != 'd'){ //per evitare l'inversione dello snake
						sposta = 'u';
						flag = true;
					}
					break;
				case 16777218:
					if(sposta != 'u'){
						sposta = 'd';
						flag = true;
					}
					break;
				case 16777219:
					if(sposta != 'r'){
						sposta = 'l';
						flag = true;
					}
					break;
				case 16777220:
					if(sposta != 'l'){
						sposta = 'r';
						flag = true;
					}
					break;
				}
			}
		});
		shell.setSize(678, 533);
		shell.setText("Games");
		
		canvas = new Canvas(shell, SWT.NONE);
		canvas.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent arg0) {
				Body.uni = 10;
				sposta = 'u';
				Punto.xMax = canvas.getBounds().width;
				Punto.yMax = canvas.getBounds().height;
				
				xapple = canvas.getBounds().width / 2;
				yapple = canvas.getBounds().height / 2;
				
				snk = new Body();
				draw();
			}
		});
		canvas.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		canvas.setBounds(0, 0, Body.uni * 60, Body.uni * 40);
		
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
