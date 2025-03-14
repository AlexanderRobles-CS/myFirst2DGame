package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.UtilityTool;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][];

    ArrayList<String> fileNames = new ArrayList<>();
    ArrayList<String> collisionStatus = new ArrayList<>();

    public TileManager(GamePanel gp) {
        this.gp = gp;
        
        // READ TILE DATA FILE
        loadTileData("/maps/tileData.txt");
        
        // INITIALIZE THE TILE ARRAY
        tile = new Tile[fileNames.size()];
        getTileImage();
        
        // LOAD MAP SIZE
        loadMapSize("/maps/worldMap.txt");

        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        
        // LOAD MAP DATA
        loadMap("/maps/worldMap.txt");
    }

    public void loadTileData(String path) {
        try (InputStream is = getClass().getResourceAsStream(path);
             BufferedReader br = is != null ? new BufferedReader(new InputStreamReader(is)) : null) {
            if (br == null) {
                System.err.println("Error: Tile data file not found -> " + path);
                return;
            }
            
            String line;
            while ((line = br.readLine()) != null) {
                fileNames.add(line);
                collisionStatus.add(br.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMapSize(String path) {
        try (InputStream is = getClass().getResourceAsStream(path);
             BufferedReader br = is != null ? new BufferedReader(new InputStreamReader(is)) : null) {
            if (br == null) {
                System.err.println("Error: Map size file not found -> " + path);
            }
            
            String line = br.readLine();
            String[] maxTile = line.split(" ");
            
            gp.maxWorldCol = maxTile.length;
            gp.maxWorldRow = maxTile.length;

        } catch (IOException e) {
            System.err.println("Exception while reading map size file.");

        }
    }

    public void getTileImage() {
        for (int i = 0; i < fileNames.size(); i++) {
            String fileName = fileNames.get(i);
            boolean collision = collisionStatus.get(i).equals("true");
            setup(i, fileName, collision);
        }
    }

    public void setup(int index, String imageName, boolean collision) {
        UtilityTool uTool = new UtilityTool();
        try {
            tile[index] = new Tile();
            InputStream is = getClass().getResourceAsStream("/tiles/" + imageName);
            if (is == null) {
                System.err.println("Error: Tile image not found -> " + imageName);
                return;
            }
            tile[index].image = ImageIO.read(is);
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize, false);
            tile[index].collision = collision;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String mapPath) {
        try (InputStream is = getClass().getResourceAsStream(mapPath);
             BufferedReader br = is != null ? new BufferedReader(new InputStreamReader(is)) : null) {
            if (br == null) {
                System.err.println("Error: Map file not found -> " + mapPath);
                return;
            }
            
            int col = 0;
            int row = 0;
            while (row < gp.maxWorldRow) {
                String line = br.readLine();
                if (line == null) break;
                String[] numbers = line.split(" ");
                
                for (col = 0; col < gp.maxWorldCol && col < numbers.length; col++) {
                    mapTileNum[col][row] = Integer.parseInt(numbers[col]);
                }
                row++;
            }
        } catch (IOException e) {
            System.err.println("Map loading failed...");
        }
    }

    public void draw(Graphics2D g2) {
        for (int worldRow = 0; worldRow < gp.maxWorldRow; worldRow++) {
            for (int worldCol = 0; worldCol < gp.maxWorldCol; worldCol++) {
                int tileNum = mapTileNum[worldCol][worldRow];
                int worldX = worldCol * gp.tileSize;
                int worldY = worldRow * gp.tileSize;
                int screenX = worldX - gp.player.worldX + gp.player.screenX;
                int screenY = worldY - gp.player.worldY + gp.player.screenY;
                
                if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                    worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                    worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                    worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
                    g2.drawImage(tile[tileNum].image, screenX, screenY, null);
                }
            }
        }
    }
}