package com.android.basics.core.utils.res;

import com.android.basics.R;
import com.android.basics.core.Mapper;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class PluralsResourceSaxParse implements Parser<Integer, Map<String, String>>, Mapper<Map<String, Map<String, String>>, Map<Integer, Map<String, String>>> {

    private static final String STRING_RES_PATH = "app/src/main/res/values/strings.xml";
    private static final String BACKUP_STRING_RES_PATH = "src/main/res/values/strings.xml";
    private static final Class<R.plurals> PLURALS_CLASS = R.plurals.class;
    private onFileParsedListener<Integer, Map<String, String>> onFileParsedListener;

    @Override
    public void parse(onFileParsedListener<Integer, Map<String, String>> onFileParsedListener) {
        this.onFileParsedListener = onFileParsedListener;
        parsePlurals(STRING_RES_PATH);
    }

    private void parsePlurals(String path) {
        SaxParser saxParser = new SaxParser(new File(path));
        saxParser.setFallBackListener(e -> parsePlurals(BACKUP_STRING_RES_PATH));
        saxParser.parse(handler);
    }

    private final DefaultHandler handler = new DefaultHandler() {

        private static final String PLURALS = "PLURALS";
        private static final String ITEM = "ITEM";
        private static final String QUANTITY = "quantity";
        private static final String NAME = "name";
        private final Map<String, Map<String, String>> parsedValues = new HashMap<>();
        private Map<String, String> items;
        private boolean isPlural;
        private boolean isItem;
        private String name;
        private String quantity;

        @Override
        public void endDocument() throws SAXException {
            super.endDocument();

            if (onFileParsedListener != null) {
                onFileParsedListener.onFileParsedSuccessfully(convert(parsedValues));
            }
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if (attributes != null) {
                if (qName.equalsIgnoreCase(PLURALS)) {
                    name = attributes.getValue(NAME);
                    isPlural = true;
                    items = new HashMap<>();
                } else if (qName.equalsIgnoreCase(ITEM) && isPlural) {
                    quantity = attributes.getValue(QUANTITY);
                    isItem = true;
                }
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {

            if (qName.equalsIgnoreCase(PLURALS) && name != null) {
                parsedValues.put(name, items);
                name = null;
                isPlural = false;
            }
            quantity = null;
            isItem = false;
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            if (isPlural && isItem && quantity != null) {
                items.put(quantity, new String(ch, start, length));
            }

        }
    };

    @Override
    public Map<Integer, Map<String, String>> convert(Map<String, Map<String, String>> fromObj) {
        Map<Integer, Map<String, String>> map = new HashMap<>();
        Field[] fields = PLURALS_CLASS.getFields();
        for (Field f : fields) {
            try {
                if (fromObj.containsKey(f.getName())) {
                    map.put(f.getInt(null), fromObj.get(f.getName()));
                }
            } catch (IllegalAccessException e) {

            }
        }
        return map;
    }
}
