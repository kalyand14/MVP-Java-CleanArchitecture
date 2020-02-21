package com.android.basics.core.utils.res;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ColorResourceSaxParse implements Parser<String, String> {

    private static final String COLOR_RES_PATH = "app/src/main/res/values/colors.xml";
    private static final String BACKUP_COLOR_RES_PATH = "src/main/res/values/colors.xml";
    private onFileParsedListener<String, String> onFileParsedListener;

    @Override
    public void parse(onFileParsedListener<String, String> onFileParsedListener) {
        this.onFileParsedListener = onFileParsedListener;
        parseColor(COLOR_RES_PATH);
    }

    private void parseColor(String path) {
        SaxParser saxParser = new SaxParser(new File(path));
        saxParser.setFallBackListener(e -> parseColor(BACKUP_COLOR_RES_PATH));
        saxParser.parse(handler);
    }

    private DefaultHandler handler = new DefaultHandler() {

        private static final String NAME = "name";
        private static final String COLOR = "COLOR";
        private String name;
        private boolean isColor;
        private Map<String, String> parsedValues = new HashMap<>();

        @Override
        public void endDocument() throws SAXException {
            super.endDocument();

            if (onFileParsedListener != null) {
                onFileParsedListener.onFileParsedSuccessfully(parsedValues);
            }
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            isColor = qName.equalsIgnoreCase(COLOR);
            if (attributes != null) {
                name = attributes.getValue(NAME);
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            name = null;
            isColor = false;
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            if (isColor && name != null) {
                String value = new String(ch, start, length);
                parsedValues.put(name, value);
            }

        }
    };
}
