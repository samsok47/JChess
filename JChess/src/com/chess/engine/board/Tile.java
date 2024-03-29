package com.chess.engine.board;

import com.chess.engine.pieces.Piece;
import java.util.HashMap;

import java.util.Map;


public abstract class Tile {
	
	protected final int tileCoordinate; 
	//creates all the tiles with a map
	private static final Map<Integer, EmptyTile> EMPTY_TILE_CACHE = createAllPossibleEmptyTiles();
	
	// Getter method to find the tile coordinate.
	public int getTileCoordinate() {
		return this.tileCoordinate;
	}
	
	
	private Tile(int tileCoordinate)  {
		this.tileCoordinate = tileCoordinate; 
	}
	
	/*
	 * Creates all the possible empty tiles
	 * Uses board utilitys NUM_TILES (64) to add all the empty tiles to the emptyTile map.
	 */
	private static Map<Integer, EmptyTile> createAllPossibleEmptyTiles() {
		Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();
			for(int i = 0; i < BoardUtils.NUM_TILES; i++) {
			emptyTileMap.put(i,  new EmptyTile(i));
			} 
			return emptyTileMap;
			}
		
		public static Tile createTile(final int tileCoordinate, final Piece piece) {
				return piece != null ? new OccupiedTile(tileCoordinate, piece) : EMPTY_TILE_CACHE.get(tileCoordinate);
				} 
		public abstract boolean isTileOccupied(); 
		public abstract Piece getPiece();  
		// creates inner class EmptyTile
			public static final class EmptyTile extends Tile {
				
		private EmptyTile(final int coordinate) {
			super(coordinate);
		}
		@Override 
		public String toString() {
			return "-";
			
		}
		@Override 
		public boolean isTileOccupied() {
			return false;
		}
		@Override 
		public Piece getPiece() {
			return null;
		}
		
	}
			// inner class for occupied Tile
			public static final class OccupiedTile extends Tile {
		private final Piece pieceOnTile; 
		
		// creates OccupiedTile
		private OccupiedTile(int tileCoordinate, final Piece pieceOnTile) {
			super(tileCoordinate);
			this.pieceOnTile = pieceOnTile; 
		} 
		@Override
		public String toString() {
			return getPiece().getPieceAlliance().isBlack() ? getPiece().toString().toLowerCase() : getPiece().toString();
		}
		@Override 
		public boolean isTileOccupied() {
			return true; 
		}
		@Override
		public Piece getPiece() {
			return this.pieceOnTile;
		}
		
	}
}