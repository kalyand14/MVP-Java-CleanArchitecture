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

public class StringResourceSaxParser implements Parser<Integer, String>, Mapper<Map<String, String>, Map<Integer, String>> {

    private static final String STRING_RES_PATH = "app/src/main/res/values/strings.xml";
    private static final String BACKUP_STRING_RES_PATH = "src/main/res/values/strings.xml";
    private static final Class<R.string> STRING_CLASS = R.string.class;
    private onFileParsedListener<Integer, String> onFileParsedListener;

    StringResourceSaxParser() {
    }

    private DefaultHandler handler = new DefaultHandler() {

        private boolean isString;
        private String name;
        private HashMap<String, String> parsedValues = new HashMap<>();

        @Override
        public void endDocument() throws SAXException {
            super.endDocument();
            if (onFileParsedListener != null)
                onFileParsedListener.onFileParsedSuccessfully(convert(parsedValues));
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            isString = qName.equalsIgnoreCase("STRING");
            if (attributes != null) {
                name = attributes.getValue("name");
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            name = null;
            isString = false;
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            if (isString && name != null) {
                String value = new String(ch, start, length);
                parsedValues.put(name, value);
            }

        }
    };

    private void parseString(String path) {
        SaxParser saxParser = new SaxParser(new File(path));
        saxParser.setFallBackListener(e -> parseString(BACKUP_STRING_RES_PATH));
        saxParser.parse(handler);
    }

    @Override
    public void parse(Parser.onFileParsedListener<Integer, String> onFileParsedListener) {
        this.onFileParsedListener = onFileParsedListener;
        parseString(STRING_RES_PATH);
    }

    @Override
    public Map<Integer, String> convert(Map<String, String> fromObj) {
        Map<Integer, String> map = new HashMap<>();
        Field[] fields = STRING_CLASS.getFields();
        for (Field f : fields) {
            try {
                if (fromObj.containsKey(f.getName())) {
                    map.put(f.getInt(null), fromObj.get(f.getName()));
                }
            } catch (Exception e) {

            }
        }
        return map;
    }
}
