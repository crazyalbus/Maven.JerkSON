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
                e.printStackTrace();
            }
        }

        return parsedItemList;
    }



    public Item parseSingleItem(String singleItem) throws ItemParseException {

        Map<String, String> kvpairs = itemMap(singleItem.replace("##", ""));

        return new Item(kvpairs.get("name"),
                Double.parseDouble(kvpairs.get("price")),
                kvpairs.get("type"),
                kvpairs.get("expiration"));
    }


    //----------------------------

    //parse whole list into an array of item strings
    private String[] createItemArray(String wholeFile) {
        return parser(wholeFile, "##");
    }


    //naMe:Milk;price:3.23;type:Food;expiration:1/25/2016##
    //parse an item string into a Map of fields
    private Map<String, String> itemMap(String itemString) {
        String[] fieldArray = createFieldInfoArray(itemString);

        Map<String, String> fieldMap = new HashMap<>();
        for (String field : fieldArray) {
            String[] pair = fieldMap(field);
            fieldMap.put(pair[0].toLowerCase(), pair[1].toLowerCase());
        }
        return fieldMap;
    }

    //naMe:Milk;price:3.23;type:Food;expiration:1/25/2016##
    //      to name:Milk price:3.23 type:Food expiration:1/25/2016
    //parse an item into an array of unparsed field strings
    private String[] createFieldInfoArray(String itemArray) {
        return parser(itemArray, ";");
    }
    //name:Milk
    //      to [name, Milk]
    //parse unparsed field to create kv pair
    private String[] fieldMap(String unparsedFieldInfo) {
        return parser(unparsedFieldInfo, "[:@^*%}]");
    }


  //parsing method
    private String[] parser(String string, String regex) {
            return string.split(regex);
    }

}
