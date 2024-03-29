package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.chess.engine.board.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.board.Move.AttackMove;
import com.chess.engine.board.Move.MajorMove;


public class Rook extends Piece {
	
	private final static int[] CANDIDATE_MOVE_VECTOR_COORDINATES = { -8, -1, 1, 8 };

	public Rook(final Alliance pieceAlliance, final int piecePosition) {
		super(PieceType.ROOK, piecePosition, pieceAlliance);
		
	}
	@Override
	public Collection<Move> calculateLegalMoves(final Board board) {
		final List<Move> legalMoves = new ArrayList<>(); 
		
		for(final int candidateCoordinateOffset: CANDIDATE_MOVE_VECTOR_COORDINATES) {
			
			int candidateDestinationCoordinate = this.piecePosition; 
			
			while(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
				
				if(isFirstColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset) ||
				   isEigthColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset)) {
					break;
				}
				
				candidateDestinationCoordinate += candidateCoordinateOffset;
				
				if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
					final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
					if(!candidateDestinationTile.isTileOccupied()) {
						legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
					} 
						else { 
							final Piece pieceAtDestination = candidateDestinationTile.getPiece(); 
							final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance(); 
						if(this.pieceAlliance != pieceAlliance) {
							legalMoves.add(new AttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
						 }
						break;
					 }
					
					
				}
			}
			
		}
		return Collections.unmodifiableList(legalMoves);
	}
	
	@Override
	public Rook movePiece(Move move) {
		// TODO Auto-generated method stub
		return new Rook(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
	}
	
	@Override 
	public String toString() {
		return PieceType.ROOK.toString();
	}
	
	private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -1);
	}
	private static boolean isEigthColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == 1);
}

}
