package ru.ivanov_chkadua.game;

import com.google.gson.stream.JsonReader;
import org.eclipse.swt.graphics.Rectangle;
import ru.ivanov_chkadua.sprites.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class BlockReader {

    public List<Sprite> getBlocksList(String fileName) throws IOException {
        if (fileName != null) {
            try (FileInputStream file = new FileInputStream(fileName)) {
                List<Sprite> blocks = new ArrayList<>();
                JsonReader reader = new JsonReader(new InputStreamReader(file));
                reader.setLenient(true);
                reader.beginArray();
                while (reader.hasNext()) {
                    reader.beginObject();
                    Sprite block = new Sprite(new Rectangle(0, 0, 0, 0));
                    block.setInteractive(true);
                    while (reader.hasNext()) {
                        Integer[] obstacle = readObstacle(reader);
                        if (obstacle[1] == null)
                            throw new IllegalArgumentException();
                        block.addChild(getSpriteUsingNumber(obstacle[0]), obstacle[1]);
                    }
                    reader.endObject();
                    blocks.add(block);
                    System.out.println(block.toString());
                }
                reader.endArray();
                return blocks;
            }
        }
        return null;
    }
    private Integer[] readObstacle(JsonReader reader) throws IOException {
        Integer obstacleType = null, obstacleMargin = null;
        if (reader.hasNext()) obstacleType = Integer.valueOf(reader.nextName());
        if (reader.hasNext()) obstacleMargin = reader.nextInt();
        return new Integer[]{obstacleType, obstacleMargin};
    }

    private Sprite getSpriteUsingNumber(int number) {
        switch (number) {
            case 0:
                return new TreeSprite();
            case 1:
                return new TreeWithBranchSprite();
            case 2:
                return new FloatBranch();
            case 3:
                return new Snowball();
            case 4:
                return new Fire();
            case 5:
                return new Snowdrift();
            default:
                return null;
        }
    }
}
