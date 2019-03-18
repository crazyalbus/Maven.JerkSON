package io.zipcoder;

import io.zipcoder.utils.Item;
import io.zipcoder.utils.ItemParseException;

import java.util.*;

public class ItemParser {



    public List<Item> parseItemList(String valueToParse) {

        List<Item> parsedItemList = new ArrayList<>();

        String[] unparsedItemArray = createItemArray(valueToParse);

        for (String unparsedItem : unparsedItemArray) {
            try {
                parsedItemList.add(parseSingleItem(unparsedItem));
            } catch (ItemParseException e) {
//                e.printStackTrace();
            }
        }
        return parsedItemList;
    }



    public Item parseSingleItem(String singleItem) throws ItemParseException {

        String cleanedupItem = singleItem.toLowerCase().replace("##", "");
        Map<String, String> kvpairs = itemMap(cleanedupItem);

        return new Item(
                kvpairs.get("name"),
                Double.parseDouble(kvpairs.get("price")),
                kvpairs.get("type"),
                kvpairs.get("expiration"));
    }




    //parse whole list into an array of item strings
    private String[] createItemArray(String wholeFile) {
        return parser(wholeFile, "##");
    }

    //parse an item string into a Map of fields for that item
    private Map<String, String> itemMap(String itemString) throws ItemParseException {
        String[] fieldArray = createFieldInfoArray(itemString);

        Map<String, String> fieldMap = new HashMap<>();

        for (String field : fieldArray) {
            String[] pair = fieldsToMap(field);
            try {
                fieldMap.put(pair[0], pair[1]);
            } catch(IndexOutOfBoundsException e) {
                throw new ItemParseException();
            }
        }
        return fieldMap;
    }

    //parse an item into an array of unparsed field strings
    //      from naMe:Milk;price:3.23;type:Food;expiration:1/25/2016## to
    //      [name:Milk, price:3.23, type:Food, expiration:1/25/2016]

    private String[] createFieldInfoArray(String itemArray) {
        return parser(itemArray, ";");
    }


    //parse unparsed field to create kv pair
    //    from name:Milk -> [name, Milk]

    private String[] fieldsToMap(String unparsedFieldInfo) {
        return parser(unparsedFieldInfo, "[:@^*%}]");
    }


  //parsing method
    private String[] parser(String string, String regex) {
            return string.split(regex);
    }

}
