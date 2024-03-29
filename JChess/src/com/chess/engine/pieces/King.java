package com.chess.engine.pieces;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import com.chess.engine.board.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.board.Move.AttackMove;
import com.chess.engine.board.Move.MajorMove;


public class King extends Piece {

	private final static int[] CANDIDATE_MOVE_COORDINATES = { -9, -8, -7, -1, 1, 7, 8, 9 };
	
	public King( final Alliance pieceAlliance, final int piecePosition) {
		super(PieceType.KING, piecePosition, pieceAlliance);
		
	}

	@Override
	public Collection<Move> calculateLegalMoves(Board board) {
		
		final List<Move> legalMoves = new ArrayList<>();
			
		for(final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATES) {
			final int candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset;
			
			if(isFirstColumnExclusion(this.piecePosition, currentCandidateOffset) || 
				isEigthColumnExclusion(this.piecePosition, currentCandidateOffset)) {
				continue; 
			}
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
			   }
			}
		}
		return Collections.unmodifiableList(legalMoves);
	}
	@Override
	public King movePiece(Move move) {
		// TODO Auto-generated method stub
		return new King(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
	}
	@Override 
	public String toString() {
		return PieceType.KING.toString();
	}
private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffest) {
		
		return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffest == -9 || candidateOffest == -1 || candidateOffest == 7); 
	}
	
	private static boolean isEigthColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == -7 || candidateOffset == 1 || candidateOffset == 9);
	}
	
}
