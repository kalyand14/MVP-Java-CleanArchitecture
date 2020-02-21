package com.android.basics.core.utils.res;

import com.android.basics.R;
import com.android.basics.core.Mapper;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StringArrayResourceSaxParser implements Parser<Integer, String[]>, Mapper<Map<String, String[]>, Map<Integer, String[]>> {


    private static final String ARRAY_RES_PATH = "app/src/main/res/values/arrays.xml";
    private static final String BACKUP_ARRAY_RES_PATH = "src/main/res/values/arrays.xml";
    private static final Class<R.array> ARRAY_CLASS = R.array.class;
    private onFileParsedListener<Integer, String[]> onFileParsedListener;

    private DefaultHandler handler = new DefaultHandler() {

        private static final String STRING_ARRAY = "STRING-ARRAY";
        private static final String ITEM = "ITEM";
        private static final String NAME = "name";
        private boolean isStringArray;
        private boolean isItem;
        private String name;
        private final List<String> arrayValues = new ArrayList<>();
        private Map<String, String[]> parsedValues = new HashMap<>();

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
                if (qName.equalsIgnoreCase(STRING_ARRAY)) {
                    arrayValues.clear();
                    name = attributes.getValue(NAME);
                    isStringArray = true;
                }
                isItem = qName.equalsIgnoreCase(ITEM);
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {

            if (qName.equalsIgnoreCase(STRING_ARRAY) && name != null) {
                parsedValues.put(name, arrayValues.toArray(new String[0]));
                name = null;
                isStringArray = false;
            }
            isItem = false;
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            if (isStringArray && isItem) {
                arrayValues.add(new String(ch, start, length));
            }

        }
    };

    @Override
    public Map<Integer, String[]> convert(Map<String, String[]> fromObj) {
        Map<Integer, String[]> map = new HashMap<>();
        Field[] fields = ARRAY_CLASS.getFields();
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


    private void parseStringArray(String path) {
        SaxParser saxParser = new SaxParser(new File(path));
        saxParser.setFallBackListener(e -> parseStringArray(BACKUP_ARRAY_RES_PATH));
        saxParser.parse(handler);
    }

    @Override
    public void parse(onFileParsedListener<Integer, String[]> onFileParsedListener) {
        this.onFileParsedListener = onFileParsedListener;
        parseStringArray(ARRAY_RES_PATH);
    }
}
