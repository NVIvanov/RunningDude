package ru.ivanov_chkadua.game;

import com.google.gson.stream.JsonReader;
import ru.ivanov_chkadua.sprites.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для генерации набора блоков препятствий из JSON
 * @author Anton_Chkadua
 */
public class BlockReader {

    /**
     * Метод получения списка блоков из входящего потока
     * @param is входящий поток
     * @return список блоков
     * @throws Exception если произошла ошибка считывания из потока.
     */
    public List<SpriteContainer> getBlocksList(InputStream is) throws Exception {
        List<SpriteContainer> blocks = new ArrayList<>();
        JsonReader reader = new JsonReader(new InputStreamReader(is));
        reader.setLenient(true);
        reader.beginArray();
        while (reader.hasNext()) {
            reader.beginObject();
            SpriteContainer block = new SpriteContainer();
            block.setInteractive(true);
            while (reader.hasNext()) {
                Integer[] obstacle = readObstacle(reader);
                if (obstacle[1] == null)
                    throw new IllegalArgumentException();
                block.addChild(getSpriteUsingNumber(obstacle[0]), obstacle[1]);
            }
            reader.endObject();
            blocks.add(block);
        }
        reader.endArray();
        return blocks;
    }

    /**
     * Метод получения списка блоков препятствий из файла.
     * Файл должен быть следующего формата: json-массив обозначает массив блоков,
     * json-объект обозначает блок препятствий. Объект содержит в себе набор пар чисел ключ-значение, где
     * ключ - тип препятствия, значение - смещение следующего препятствия относительно предыдущего (относительно начала,
     * если препятствие первое в списке). Типы препятсвий: 0 - бревно, 1 - дерево (спрайт), 2 - крона дерева (идет в паре с деревом),
     * 3 - снежок, 4 - костер, 5 - куст.
     *
     * @param fileName путь к файлу
     * @return список препятствий
     * @throws IOException если при считывании из файла произошла ошибка
     */
    public List<SpriteContainer> getBlocksList(String fileName) throws Exception {
        try (FileInputStream file = new FileInputStream(fileName)) {
            return getBlocksList(file);
        }
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
