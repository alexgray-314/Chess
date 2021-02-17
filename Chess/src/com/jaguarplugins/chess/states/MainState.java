package com.jaguarplugins.chess.states;

import com.jaguarplugins.chess.pieces.Pawn;
import com.jaguarplugins.chess.pieces.Piece;
import com.jaguarplugins.chess.style.Assets;
import com.jaguarplugins.chess.style.ChessColor;
import com.jaguarplugins.chess.util.Handler;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

public class MainState extends State {

	private Piece[][] board = new Piece[8][8];;
	private Piece heldPiece;
	private double baseX, baseY;
	
	public MainState(Handler handler) {
		super(handler);
		board[0][7] = new Pawn(handler, Assets.PAWN, 0, 7);
	}

	@Override
	public void tick() {

	}

	@Override
	public void render(GraphicsContext g) {

//		BOARD
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				g.setFill(ChessColor.getSquareColor(x, y));
				g.fillRect(x*handler.getWidth()/8, y*handler.getHeight()/8, handler.getWidth()/8, handler.getHeight()/8);
			}
		}
		
//		PIECES
		for (Piece[] column : board) {
			for (Piece p : column) {
				if (p != null) {
					p.render(g);
				}
			}
		}
		
	}

//	EVENTHANDLER
	
	@Override
	public void handle(MouseEvent e) {
		
		if (State.getCurrentState() instanceof MainState) {
			
			if (e.getEventType().equals(MouseEvent.MOUSE_DRAGGED)) {
				
				if (heldPiece != null) {
					heldPiece.setXOffset(e.getSceneX() - baseX);
					heldPiece.setYOffset(e.getSceneY() - baseY);
				}
				
			} else if (e.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
				
				if (heldPiece != null) {
					heldPiece.snap();
					heldPiece = null;
				}
				
			} else if (e.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {

				for (Piece[] column : board) { for (Piece p : column) { if (p != null) {
					if (new Rectangle(e.getSceneX(), e.getSceneY(), 1, 1).intersects(p.getR().getX(), p.getR().getY(), p.getR().getWidth(), p.getR().getHeight())) {
						heldPiece = p;
						baseX = e.getSceneX();
						baseY = e.getSceneY();
						System.out.println("Piece Found!");
					}
				}}}

			}
			
		}
		
	}

}