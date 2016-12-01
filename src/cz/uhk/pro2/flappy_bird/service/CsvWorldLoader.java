package cz.uhk.pro2.flappy_bird.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import cz.uhk.pro2.flappy_bird.game.Tile;
import cz.uhk.pro2.flappy_bird.game.World;
import cz.uhk.pro2.flappy_bird.game.tiles.WallTile;

public class CsvWorldLoader implements WorldLoader {
	InputStream is;
	String cell;
	
	public CsvWorldLoader(InputStream is){
		this.is = is;
	}

	@Override
	public World loadLevel() {
		try(BufferedReader br = new BufferedReader(new InputStreamReader(is))){
			String[] line = br.readLine().split(";");
			int typeCount = Integer.parseInt(line[0]);
			//skips type definition cells
			Map<String, Tile> tileTypes = new HashMap<>();
			for(int i = 0; i < typeCount; i++){
				line = br.readLine().split(";");
				String tileShort = line[0];
				String tileType = line[1];
				int spriteStartX = Integer.parseInt(line[2]);
				int spriteStartY = Integer.parseInt(line[3]);
				int spriteSizeX = Integer.parseInt(line[4]);
				int spriteSizeY = Integer.parseInt(line[5]);
				String spriteURL = line[6];
				Tile tile = createTile(tileType, spriteStartX, spriteStartY, spriteSizeX, spriteSizeY);
				tileTypes.put(key, value);
			}
			line = br.readLine().split(";");
			int rows = Integer.parseInt(line[0]);
			int columns = Integer.parseInt(line[1]);
			Tile[][] tiles = new Tile[rows][columns];
			System.out.println(rows + "," + columns);
			Tile tile = new WallTile();
			for(int i = 0; i < rows; i++){
				line = br.readLine().split(";");
				for(int j = 0; j < columns; j++){
				//if the cell exists read it, else input empty cell
					cell = j < line.length ? line[j] : "";
					if(!"".equals(cell)){
						tiles[i][j] = tileTypes.get(cell);
					}
				}
			}
			
			World world = new World(tiles);
			return world;
		} catch (IOException e) {
			throw new RuntimeException("Error reading the file", e);
		}
	}

	private Tile createTile(String tileType, int spriteStartX, int spriteStartY, int spriteSizeX, int spriteSizeY) {
		return null;
	}

}
