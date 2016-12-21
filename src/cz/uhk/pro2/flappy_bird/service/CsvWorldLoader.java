package cz.uhk.pro2.flappy_bird.service;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import cz.uhk.pro2.flappy_bird.game.Tile;
import cz.uhk.pro2.flappy_bird.game.World;
import cz.uhk.pro2.flappy_bird.game.tiles.BonusTile;
import cz.uhk.pro2.flappy_bird.game.tiles.EmptyTile;
import cz.uhk.pro2.flappy_bird.game.tiles.WallTile;

public class CsvWorldLoader implements WorldLoader {
	InputStream is;
	String cell;
	
	public CsvWorldLoader(InputStream is){
		this.is = is;
	}

	@Override
	public World loadLevel() {
		try(BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"))){
			String[] line = br.readLine().split(";");
			int typeCount = Integer.parseInt(line[0]);
			System.out.println("Number of tile types: " + typeCount);
			//skips type definition cells
			Map<String, Tile> tileTypes = new HashMap<>();
			BufferedImage imageOfTheBird = null;
			for(int i = 0; i < typeCount; i++){
				line = br.readLine().split(";");
				String tileShort = line[0];
				String tileType = line[1];
				int spriteStartX = Integer.parseInt(line[2]);
				int spriteStartY = Integer.parseInt(line[3]);
				int spriteSizeX = Integer.parseInt(line[4]);
				int spriteSizeY = Integer.parseInt(line[5]);
				String spriteURL = line[6];
				String referencedTileType = (line.length >= 8) ? line[7] : "";
				if(tileType.equals("Bird")){
					//special birdie's sprite creation
					imageOfTheBird = getImage(spriteStartX, spriteStartY, spriteSizeX, spriteSizeY, spriteURL);
				}else{
					Tile referencedTile = tileTypes.get(referencedTileType);
					Tile tile = createTile(tileType, spriteStartX, spriteStartY, spriteSizeX, spriteSizeY,spriteURL, referencedTile);
					tileTypes.put(tileShort, tile);
				}
			}
			line = br.readLine().split(";");
			int rows = Integer.parseInt(line[0]);
			int columns = Integer.parseInt(line[1]);
			Tile[][] tiles = new Tile[rows][columns];
			System.out.println(rows + "," + columns);
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
			
			World world = new World(tiles, imageOfTheBird);
			return world;
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Nepodporovane kodovani souboru", e);
		} catch (IOException e) {
			throw new RuntimeException("Chyba pri cteni souboru s levelem nebo obrazku", e);
		}
	}

	private Tile createTile(String tileType, int spriteX, int spriteY, int spriteWidth, int spriteHeight, String url, Tile referencedTile) throws IOException {
		// nacist obrazek z URL
		BufferedImage originalImage = ImageIO.read(new URL(url));
		// vyriznout z obrazku sprite podle x,y, a sirka vyska
		BufferedImage croppedImage = originalImage.getSubimage(spriteX, spriteY, spriteWidth, spriteHeight);
		// zvetsime/zmensime sprite tak, aby pasoval do naseho rozmeru dlazdice
		BufferedImage resizedImage = new BufferedImage(Tile.SIZE, Tile.SIZE, BufferedImage.TYPE_INT_ARGB);
		// TODO nastavit parametry pro scaling
		Graphics2D g = (Graphics2D)resizedImage.getGraphics();
		g.drawImage(croppedImage, 0, 0, Tile.SIZE, Tile.SIZE, null);
		// podle typu (clazz) vytvorime instanci patricne tridy
		switch (tileType) {
		case "Wall":
			return new WallTile(resizedImage);
		case "Bonus":
			return new BonusTile(resizedImage, referencedTile);
		case "Empty":
			return new EmptyTile(resizedImage);
		default:
			throw new RuntimeException("Neznama trida dlazdice " + tileType);
		}
	}
	
	private BufferedImage getImage(int spriteStartX, int spriteStartY, int spriteSizeX, int spriteSizeY, String spriteURL)
			throws IOException, MalformedURLException {
		// load picture from URL
		BufferedImage originalImage = ImageIO.read(new URL(spriteURL));
		//cut the sprite by starting x, starting y, width and height
		BufferedImage croppedImage = originalImage.getSubimage(spriteStartX, spriteStartY, spriteSizeX, spriteSizeY);
		//fit the sprite to tile
		BufferedImage resizedImage = new BufferedImage(Tile.SIZE, Tile.SIZE, BufferedImage.TYPE_INT_RGB);
		// TODO nastavit parametry pro scaling
		Graphics2D g = (Graphics2D) resizedImage.getGraphics();
		g.drawImage(croppedImage, 0, 0, Tile.SIZE, Tile.SIZE, null);
		return resizedImage;
	}
}
