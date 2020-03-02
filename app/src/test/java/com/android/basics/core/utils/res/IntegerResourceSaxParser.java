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

public class IntegerResourceSaxParser implements Parser<Integer, Integer>, Mapper<Map<String, Integer>, Map<Integer, Integer>> {

    private static final String INTEGER_RES_PATH = "app/src/main/res/values/integers.xml";
    private static final String BACKUP_INTEGER_RES_PATH = "src/main/res/values/integers.xml";
    private static final Class<R.integer> INTEGER_CLASS = R.integer.class;
    private onFileParsedListener<Integer, Integer> onFileParsedListener;

    @Override
    public Map<Integer, Integer> convert(Map<String, Integer> fromObj) {
        Map<Integer, Integer> map = new HashMap<>();
        Field[] fields = INTEGER_CLASS.getFields();
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

    @Override
    public void parse(onFileParsedListener<Integer, Integer> onFileParsedListener) {
        this.onFileParsedListener = onFileParsedListener;
        parseInteger(INTEGER_RES_PATH);
    }

    private void parseInteger(String path) {
        SaxParser saxParser = new SaxParser(new File(path));
        saxParser.setFallBackListener(e -> parseInteger(BACKUP_INTEGER_RES_PATH));
        saxParser.parse(handler);
    }

    private DefaultHandler handler = new DefaultHandler() {

        private static final String INTEGER = "INTEGER";
        private static final String NAME = "name";
        private boolean isInteger;
        private String name;
        private final Map<String, Integer> parsedValues = new HashMap<>();

        @Override
        public void endDocument() throws SAXException {
            super.endDocument();

            if (onFileParsedListener != null) {
                onFileParsedListener.onFileParsedSuccessfully(convert(parsedValues));
            }
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            isInteger = qName.equalsIgnoreCase(INTEGER);
            if (attributes != null) {
                name = attributes.getValue(NAME);
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            name = null;
            isInteger = false;
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            if (isInteger && name != null) {
                Integer value = Integer.valueOf(new String(ch, start, length));
                parsedValues.put(name, value);
            }

        }
    };
}
