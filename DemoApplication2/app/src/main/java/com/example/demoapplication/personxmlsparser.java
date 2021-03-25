/*
package com.example.demoapplication;

import android.util.Xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class personxmlsparser  extends DefaultHandler {

    ArrayList<Person> personArrayList=new ArrayList<>();
    Person person;
    Address address;
    StringBuilder stringBuilder= new StringBuilder();

    public ArrayList<Person> parser(InputStream inputStream) throws IOException, SAXException {
        Xml.parse(inputStream,Xml.Encoding.UTF_8,this);
        return personArrayList;
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);

        if(localName.equalsIgnoreCase("persons"))
            personArrayList=new ArrayList<>();
        else if(localName.equalsIgnoreCase("person"))
        {
            person=new Person();
            person.setId(attributes.getValue("id"));
        }
        else if(localName.equalsIgnoreCase("address"))
            address=new Address();
        stringBuilder.setLength(0);

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if(localName.equalsIgnoreCase("name"))
            person.setName(stringBuilder.toString());
        else if(localName.equalsIgnoreCase("age"))
            person.setAge(stringBuilder.toString());
        else if(localName.equalsIgnoreCase("line1"))
            address.setLine1(stringBuilder.toString());
        else if(localName.equalsIgnoreCase("city"))
        address.setCity(stringBuilder.toString());
        else if(localName.equalsIgnoreCase("state"))
            address.setState(stringBuilder.toString());
        else if(localName.equalsIgnoreCase("zip"))
            address.setZip(stringBuilder.toString());
        else if(localName.equalsIgnoreCase("address"))
            person.setAddress(address);
        else if(localName.equalsIgnoreCase("person"))
            personArrayList.add(person);



    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        stringBuilder.append(ch,start,length);
    }
}
*/
