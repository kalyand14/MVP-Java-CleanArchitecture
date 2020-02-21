package com.android.basics.core.utils.res;

import org.xml.sax.helpers.DefaultHandler;

import java.io.File;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class SaxParser {

    private File file;
    private onFallBackListener fallBackListener;

    interface onFallBackListener {
        void onFileParseError(Exception e);
    }

    public void setFallBackListener(onFallBackListener fallBackListener) {
        this.fallBackListener = fallBackListener;
    }

    public SaxParser(File file) {
        this.file = file;
    }

    protected void parse(DefaultHandler defaultHandler) {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = null;
        try {
            parser = factory.newSAXParser();
            parser.parse(file, defaultHandler);
        } catch (Exception ex) {
            if (fallBackListener != null) {
                fallBackListener.onFileParseError(ex);
            }
        }

    }

}
